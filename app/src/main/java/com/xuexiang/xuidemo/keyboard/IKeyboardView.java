package com.xuexiang.xuidemo.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.KeyBoardAdapter;
import com.xuexiang.xuidemo.input.MyKeyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IKeyboardView extends KeyboardView implements KeyboardView.OnKeyboardActionListener {
    //@Author LiuYe
    public MyKeyboard keyboardService;
    public TextView pytextView;
    public List<String> words = new ArrayList<String>();

    public void setKeyboardService(MyKeyboard keyboardService) {
        this.keyboardService = keyboardService;
    }

    //

    /**
     * 数字键盘
     */
    private Keyboard keyboardNumber;
    /**
     * 字母键盘
     */
    private Keyboard keyboardLetter;

    /**
     * 是否发生键盘切换
     */
    private boolean changeLetter = false;

    /**
     * 是否为大写
     */
    private boolean isCapital = false;

    private List<Integer> noLists = new ArrayList<>();

    private int[] arrays = new int[]{Keyboard.KEYCODE_SHIFT, Keyboard.KEYCODE_MODE_CHANGE,
            Keyboard.KEYCODE_CANCEL, Keyboard.KEYCODE_DONE, Keyboard.KEYCODE_DELETE,
            Keyboard.KEYCODE_ALT, 32};

    public IKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.pytextView = findViewById(R.id.pinying);
        init(context);
    }

    public IKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public IKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        keyboardNumber = new Keyboard(getContext(), R.xml.keyboard_num);
        keyboardLetter = new Keyboard(getContext(), R.xml.keyboard_letter);
        //设置一些不需要预览的键位
        for (int i = 0; i < arrays.length; i++) {
            noLists.add(arrays[i]);
        }
        //默认使用字母键盘
        setKeyboard(keyboardLetter);
        //是否启用预览
        setPreviewEnabled(true);
        //键盘动作监听
        setOnKeyboardActionListener(this);

        //
//        words.addAll(Arrays.asList(keyboardService.candidates));
        //words = Arrays.asList(keyboardService.candidates);

    }

    /**
     * 判断是否需要预览Key
     *
     * @param primaryCode keyCode
     */
    private void canShowPreview(int primaryCode) {
        if (noLists.contains(primaryCode)) {
            setPreviewEnabled(false);
        } else {
            setPreviewEnabled(true);
        }
    }

    @Override
    public void onPress(int primaryCode) {
        canShowPreview(primaryCode);
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE://删除
                keyboardService.deleteLast();
                break;
            case Keyboard.KEYCODE_MODE_CHANGE://字母键盘与数字键盘切换
                changeKeyBoard(!changeLetter);
                break;
            case Keyboard.KEYCODE_DONE://完成
                //changeKeyBoard(!changeLetter);
                keyboardService.sendText();
                break;
            case Keyboard.KEYCODE_SHIFT://大小写切换
                changeCapital(!isCapital);
                setKeyboard(keyboardLetter);
                break;
            case 32:
                keyboardService.choseCandidate(0);
                break;
            default:
                char ch = (char) primaryCode;
                keyboardService.appendLast(ch);
                break;
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
    /**
     * 切换键盘类型
     */
    private void changeKeyBoard(boolean b) {
        changeLetter = b;
        if (b) {
            setKeyboard(keyboardLetter);
        } else {
            setKeyboard(keyboardNumber);
        }
    }

    /**
     * 切换键盘大小写
     */
    private void changeCapital(boolean b) {
        isCapital = b;
        List<Keyboard.Key> lists = keyboardLetter.getKeys();
        for (Keyboard.Key key : lists) {
            if (key.label != null && isKey(key.label.toString())) {
                if (isCapital) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                } else {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            } else if (key.label != null && key.label.toString().equals("小写")) {
                key.label = "大写";
            } else if (key.label != null && key.label.toString().equals("大写")) {
                key.label = "小写";
            }
        }
    }

    /**
     * 判断此key是否正确，且存在 * * @param key * @return
     */
    private boolean isKey(String key) {
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        if (lowercase.indexOf(key.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }

}
