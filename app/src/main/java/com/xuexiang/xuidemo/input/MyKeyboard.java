package com.xuexiang.xuidemo.input;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.inputmethodservice.InputMethodService;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.KeyBoardAdapter;
import com.xuexiang.xuidemo.keyboard.IKeyboardView;

import java.util.Arrays;
import java.util.List;

public class MyKeyboard extends InputMethodService {

    private InputConnection ic;
    private String data;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            data = intent.getStringExtra("data");
            System.out.println("!!!!!!data = " + data);
        }
    };

    //@author LiuYe
    String TAG = "PinyinService";
    StringBuffer pinyinBuffer;
    StringBuffer inputBuffer;
    StringBuffer displayBuffer;
    public List<String> candidates;
    public List<String> predicts;
    int candidateNum;
    int predictNum;
    int fixedLen;
    int chosenPredictNum = 0;
    int[] starts;
    boolean allFixed = false;
    KeyBoardAdapter recycleAdapter;
    TextView pinyin;
    /**
     * Remote Pinyin-to-Hanzi decoding engine service.
     */
    private IPinyinDecoderService pinyinDecoderService;
    /**
     * Connection used to bind the decoding service.
     */
    private PinyinDecoderServiceConnection mPinyinDecoderServiceConnection;

    public class PinyinDecoderServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder service) {
            pinyinDecoderService = IPinyinDecoderService.Stub.asInterface(service);
        }

        public void onServiceDisconnected(ComponentName name) {
        }
    }

    private boolean startPinyinDecoderService() {
        Intent serviceIntent = new Intent();
        serviceIntent.setClass(this, PinyinDecoderService.class);

        if (null == mPinyinDecoderServiceConnection) {
            mPinyinDecoderServiceConnection = new PinyinDecoderServiceConnection();
        }

        // Bind service
        if (!bindService(serviceIntent, mPinyinDecoderServiceConnection, Context.BIND_AUTO_CREATE)) {
            return false;
        }
        return true;
    }

    public void resetSearch() {
        try {
            pinyinDecoderService.imResetSearch();
        } catch (RemoteException e) {
            Toast.makeText(this, "输入法内核出现异常,请重启", Toast.LENGTH_LONG).show();
        }
    }

    private void refreshCandidates() {
        try {
            candidateNum = pinyinDecoderService.imSearch(pinyinBuffer.toString().getBytes(), pinyinBuffer.length());
            int fixedLen = pinyinDecoderService.imGetFixedLen();
            candidates = pinyinDecoderService.imGetChoiceList(0, candidateNum, fixedLen);
        } catch (RemoteException e) {
            candidateNum = 0;
            candidates = null;
            Toast.makeText(this, "输入法内核出现异常,请重启", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refreshAdaptors() {
        if (candidates != null) {
            recycleAdapter.data = candidates;
        }
        recycleAdapter.notifyItemChanged(0, candidates.size());
        recycleAdapter.notifyDataSetChanged();
    }

    public void refreshDisplayBuffer() {
        displayBuffer = new StringBuffer(inputBuffer.toString() + getUnfixedPinyinString());
        pinyin.setText(displayBuffer);
    }

    public String getUnfixedPinyinString() {

        if (allFixed) {
            allFixed = false;
            return "";
        }
        System.out.println(Arrays.toString(starts) + ":" + fixedLen);

        return fixedLen == 0 ? pinyinBuffer.toString() : pinyinBuffer.substring(starts[fixedLen + 1]);
    }

    public void chooseCandidateItem(int pos) {
        if (predictNum != 0) {
            //get chosen predict hanzi
            String chosenPredict = predicts.get(pos);
            inputBuffer.append(chosenPredict);
            chosenPredictNum++;
            try {
                predictNum = pinyinDecoderService.imGetPredictsNum(chosenPredict);
                predicts = pinyinDecoderService.imGetPredictList(0, predictNum);
                candidates = predicts;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return;
        } else if (pos >= candidateNum) {
            return;
        }
        try {
            String full = pinyinDecoderService.imGetChoice(pos);
            candidateNum = pinyinDecoderService.imChoose(pos);
            //if all fixed size will be 1
//            if (candidateNum == 1) {
//                allFixed = true;
//            }
            allFixed = true;

            starts = pinyinDecoderService.imGetSplStart();
            pinyinBuffer = new StringBuffer(pinyinDecoderService.imGetPyStr(false));
            fixedLen = pinyinDecoderService.imGetFixedLen();
            //append hanzi
            inputBuffer.append(full);

            if (getUnfixedPinyinString().length() > 0) {
                candidateNum = pinyinDecoderService.imSearch(getUnfixedPinyinString().getBytes(), getUnfixedPinyinString().length());
                fixedLen = pinyinDecoderService.imGetFixedLen();
                candidates = pinyinDecoderService.imGetChoiceList(0, candidateNum, fixedLen);
            } else {
                candidateNum = 0;
                fixedLen = pinyinDecoderService.imGetFixedLen();
                predictNum = pinyinDecoderService.imGetPredictsNum(full);
                predicts = pinyinDecoderService.imGetPredictList(0, predictNum);
                candidates = predicts;
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public void deletedLastPinyin() {
        if (chosenPredictNum > 0) {
            //if need delete chosen predict hanzi before
            inputBuffer.deleteCharAt(inputBuffer.length() - 1);
            chosenPredictNum--;
        } else if (getUnfixedPinyinString().length() == 0) {
            //if need delete hanzi
            //clear fixed hanzi
            fixedLen = 0;
            inputBuffer.setLength(0);
            //reset engine
            try {
                pinyinDecoderService.imResetSearch();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            //if need to delete pinyin char
            if (pinyinBuffer.length() > 0) {
                pinyinBuffer.deleteCharAt(pinyinBuffer.length() - 1);
            }
        }

        if (fixedLen == 0) {
            try {
                String unfixedPinyinString = getUnfixedPinyinString();
                candidateNum = pinyinDecoderService.imSearch(unfixedPinyinString.getBytes(), unfixedPinyinString.length());
                int num = pinyinDecoderService.imGetFixedLen();
                predicts = pinyinDecoderService.imGetChoiceList(0, candidateNum, num);
                starts = pinyinDecoderService.imGetSplStart();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void refreshCandidateList() {
        if (pinyinBuffer.length() == 0) {
            candidates = null;
            return;
        }
        refreshCandidates();
    }

    //增加新输入,不输出拼音,当选择后出现中文
    public void appendLast(char ch) {
        pinyinBuffer.append(ch);
        refreshCandidateList();
        refreshAdaptors();
        refreshDisplayBuffer();
        System.out.println(pinyinBuffer);
        System.out.println(inputBuffer);
        System.out.println(displayBuffer);
        System.out.println(candidates);
        System.out.println(predicts);
    }

    //退格键删除末尾拼音 若已选择单词则退回所有拼音重新显示关联词
    @SuppressLint("NotifyDataSetChanged")
    public void deleteLast() {
        InputConnection connection = getCurrentInputConnection();
        //if no pinyin then delete the text
        //if no word in text then return
        if (displayBuffer.length() == 0) {
            connection.deleteSurroundingText(1, 0);
            return;
        }
        deletedLastPinyin();
        refreshCandidateList();
        refreshAdaptors();
        refreshDisplayBuffer();
        System.out.println(pinyinBuffer);
        System.out.println(inputBuffer);
        System.out.println(displayBuffer);
        System.out.println(candidates);
        System.out.println(predicts);
    }


    //选择关联词
    public void choseCandidate(int pos) {
        if (candidates == null || candidates.size() <= pos) {
            return;
        }
        InputConnection connection = getCurrentInputConnection();
        //get chosen candidate
        String candidate = candidates.get(pos);
        //put to text
        connection.commitText(candidate, candidate.length());
        chooseCandidateItem(pos);
        refreshAdaptors();
        refreshDisplayBuffer();
        System.out.println(pinyinBuffer);
        System.out.println(inputBuffer);
        System.out.println(displayBuffer);
        System.out.println(candidates);
        System.out.println(predicts);
    }

    //提交输入
    public void sendText() {
        InputConnection connection = getCurrentInputConnection();
        connection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
        clearBuffer();
        refreshAdaptors();
        refreshDisplayBuffer();
    }

    //清除buffer
    private void clearBuffer() {
        pinyinBuffer.setLength(0);
        inputBuffer.setLength(0);
        candidates = null;
        resetSearch();
    }

    //

    @Override
    public View onCreateInputView() {
        //start search engine
        startPinyinDecoderService();

        // 通过LayoutInflater加载自定义的输入法布局
        View view = getLayoutInflater().inflate(R.layout.keyboard_layout, null);
        IKeyboardView keyboardView = view.findViewById(R.id.keyboardview);
        IntentFilter filter = new IntentFilter("com.example.action");
        registerReceiver(receiver, filter);

        //@author LiuYe
        //bind the service
        keyboardView.keyboardService = this;
        //init the record buffer
        pinyinBuffer = new StringBuffer();
        inputBuffer = new StringBuffer();

        RecyclerView recyclerView = view.findViewById(R.id.keyboard_words);
        pinyin = view.findViewById(R.id.pinying);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recycleAdapter = new KeyBoardAdapter(candidates, this, getBaseContext());
        recyclerView.setAdapter(recycleAdapter);

        return view;
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        // 在输入法界面显示时，将输入法的高度设置为与屏幕高度相同
        getWindow().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (data != null) {
            ic = getCurrentInputConnection();
            ic.commitText(data, 1);
            data = null;
        }
    }

    @Override
    public void onFinishInputView(boolean finishingInput) {
        super.onFinishInputView(finishingInput);
        // 在输入法界面隐藏时，将输入法的高度设置为默认高度
        getWindow().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        unbindService(mPinyinDecoderServiceConnection);
    }

}
