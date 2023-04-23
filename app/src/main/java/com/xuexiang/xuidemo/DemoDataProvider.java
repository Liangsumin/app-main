package com.xuexiang.xuidemo;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.viewpager.widget.ViewPager;

import com.kunminx.linkage.bean.DefaultGroupedItem;
import com.xuexiang.xaop.annotation.MemoryCache;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.adapter.simple.ExpandableItem;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.banner.transform.DepthTransformer;
import com.xuexiang.xui.widget.banner.transform.FadeSlideTransformer;
import com.xuexiang.xui.widget.banner.transform.FlowTransformer;
import com.xuexiang.xui.widget.banner.transform.RotateDownTransformer;
import com.xuexiang.xui.widget.banner.transform.RotateUpTransformer;
import com.xuexiang.xui.widget.banner.transform.ZoomOutSlideTransformer;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xuidemo.adapter.entity.NewInfo;
import com.xuexiang.xuidemo.adapter.entity.StickyItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 演示数据
 *
 * @author xuexiang
 * @since 2018/11/23 下午5:52
 */
public class DemoDataProvider {

    public static String[] titles = new String[]{
            "伪装者:胡歌演绎'痞子特工'",
            "无心法师:生死离别!月牙遭虐杀",
            "花千骨:尊上沦为花千骨",
            "综艺饭:胖轩偷看夏天洗澡掀波澜",
            "碟中谍4:阿汤哥高塔命悬一线,超越不可能",
    };

    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160323071011277.jpg",//伪装者:胡歌演绎"痞子特工"
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144158380433341332.jpg",//无心法师:生死离别!月牙遭虐杀
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160286644953923.jpg",//花千骨:尊上沦为花千骨
            "http://photocdn.sohu.com/tvmobilemvms/20150902/144115156939164801.jpg",//综艺饭:胖轩偷看夏天洗澡掀波澜
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144159406950245847.jpg",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
    };

    public static List<BannerItem> getBannerList() {
        ArrayList<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = urls[i];
            item.title = titles[i];

            list.add(item);
        }

        return list;
    }

    public static List<AdapterItem> getGridItems(Context context) {
        return getGridItems(context, R.array.grid_titles_entry, R.array.grid_icons_entry);
    }


    private static List<AdapterItem> getGridItems(Context context, int titleArrayId, int iconArrayId) {
        List<AdapterItem> list = new ArrayList<>();
        String[] titles = ResUtils.getStringArray(context, titleArrayId);
        Drawable[] icons = ResUtils.getDrawableArray(context, iconArrayId);
        for (int i = 0; i < titles.length; i++) {
            list.add(new AdapterItem(titles[i], icons[i]));
        }
        return list;
    }

    public static List<Object> getUserGuides() {
        List<Object> list = new ArrayList<>();
        list.add(R.drawable.guide_img_1);
        list.add(R.drawable.guide_img_2);
        list.add(R.drawable.guide_img_3);
        list.add(R.drawable.guide_img_4);
        return list;
    }

    public static Class<? extends ViewPager.PageTransformer>[] transformers = new Class[]{
            DepthTransformer.class,
            FadeSlideTransformer.class,
            FlowTransformer.class,
            RotateDownTransformer.class,
            RotateUpTransformer.class,
            ZoomOutSlideTransformer.class,
    };


    public static String[] dpiItems = new String[]{
            "480 × 800",
            "1080 × 1920",
            "720 × 1280",
    };

    public static AdapterItem[] menuItems = new AdapterItem[]{
            new AdapterItem("登陆", R.drawable.icon_password_login),
            new AdapterItem("筛选", R.drawable.icon_filter),
            new AdapterItem("设置", R.drawable.icon_setting),
    };

    public static ExpandableItem[] expandableItems = new ExpandableItem[]{
            ExpandableItem.of(new AdapterItem("屏幕尺寸", R.drawable.icon_password_login)).addChild(AdapterItem.arrayof(dpiItems)),
            ExpandableItem.of(new AdapterItem("设备亮度", R.drawable.icon_filter)).addChild(menuItems),
            ExpandableItem.of(new AdapterItem("屏幕分辨率", R.drawable.icon_setting)).addChild(AdapterItem.arrayof(dpiItems))
    };

    private static List<String> getUrls() {
        List<String> urls = new ArrayList<>();
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605454727961&di=523c0cbc27edf8c9c4dfc705c395f910&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn%2Fw640h360%2F20180204%2Fe374-fyrhcqy4449849.jpg");
        urls.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=141294069,864810829&fm=26&gp=0.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605454727960&di=cf69e23566394467534c9606e1f65555&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201707%2F03%2F20170703232714_njuTy.jpeg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605454727923&di=6b54cc07f6e1aea9ffbdc85348454d8c&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201203%2F23%2F20120323140617_ZcAw3.jpeg");
        urls.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1107220453,145887914&fm=26&gp=0.jpg");
        urls.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2860640912,1833965317&fm=26&gp=0.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605454900301&di=1168cceb30a3e19b6260a2d7f3ab68f4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201609%2F24%2F20160924221133_w5Ysc.thumb.700_0.jpeg");
        urls.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3933345026,3409802114&fm=26&gp=0.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605454900300&di=47cb3c09f5f02b89942bfda02b4c6c5a&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201611%2F18%2F20161118171843_JGHBz.thumb.700_0.jpeg");
        urls.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2065870676,3251648368&fm=26&gp=0.jpg");

        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605454988368&di=b0002ea6a6ed0d054a57693bb0849076&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170318%2Fa5e5eb349b80486e949e8919278ef172_th.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=3272030875,860665188&fm=21&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=2237658959,3726297486&fm=21&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=3016675040,1510439865&fm=21&gp=0.jpg");
        urls.add("http://photocdn.sohu.com/20160307/mp62252655_1457334772519_2.png");
        urls.add("http://d040779c2cd49.scdn.itc.cn/s_big/pic/20161213/184474627873966848.jpg");
        urls.add("http://ac-QYgvX1CC.clouddn.com/36f0523ee1888a57.jpg");
        urls.add("http://ac-QYgvX1CC.clouddn.com/07915a0154ac4a64.jpg");
        urls.add("http://ac-QYgvX1CC.clouddn.com/9ec4bc44bfaf07ed.jpg");
        urls.add("http://ac-QYgvX1CC.clouddn.com/fa85037f97e8191f.jpg");
        urls.add("http://ac-QYgvX1CC.clouddn.com/de13315600ba1cff.jpg");
        urls.add("http://ac-QYgvX1CC.clouddn.com/9ec4bc44bfaf07ed.jpg");
        urls.add("http://ac-QYgvX1CC.clouddn.com/fa85037f97e8191f.jpg");
        urls.add("ttp://ac-QYgvX1CC.clouddn.com/de13315600ba1cff.jpg");
        urls.add("http://ac-QYgvX1CC.clouddn.com/ad99de83e1e3f7d4.jpg");
        urls.add("http://ac-QYgvX1CC.clouddn.com/15c5c50e941ba6b0.jpg");
        urls.add("http://ac-QYgvX1CC.clouddn.com/eaf1c9d55c5f9afd.jpg");
        urls.add("http://pic44.photophoto.cn/20170802/0017030376585114_b.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0847085702814554_s.jpg");
        urls.add("http://img44.photophoto.cn/20170802/0017030319134956_s.jpg");
        urls.add("http://img44.photophoto.cn/20170731/0838084023987260_s.jpg");
        urls.add("http://img44.photophoto.cn/20170731/0838084009134421_s.jpg");
        urls.add("http://img44.photophoto.cn/20170731/0838084002855326_s.jpg");

        urls.add("http://img44.photophoto.cn/20170731/0847085207211178_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0017030319740534_s.jpg");
        urls.add("http://img44.photophoto.cn/20170731/0838084002855326_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0847085969586424_s.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0014105802293676_s.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0847085242661101_s.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0886088744582079_s.jpg");
        urls.add("http://img44.photophoto.cn/20170801/0017029514287804_s.jpg");
        urls.add("http://img44.photophoto.cn/20170730/0018090594006661_s.jpg");
        urls.add("http://img44.photophoto.cn/20170729/0847085848134910_s.jpg");
        urls.add("http://img44.photophoto.cn/20170729/0847085581124963_s.jpg");
        urls.add("http://img44.photophoto.cn/20170729/0847085226124343_s.jpg");

        urls.add("http://img44.photophoto.cn/20170729/0847085226124343_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0847085200668628_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0847085246003750_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0847085012707934_s.jpg");
        urls.add("http://img44.photophoto.cn/20170729/0005018303330857_s.jpg");
        urls.add("http://img44.photophoto.cn/20170729/0847085231427344_s.jpg");
        urls.add("http://img44.photophoto.cn/20170729/0847085236829578_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0847085729490157_s.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0847085751995287_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0847085729043617_s.jpg");
        urls.add("http://img44.photophoto.cn/20170729/0847085786392651_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0847085761440022_s.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0847085275244570_s.jpg");


        urls.add("http://img44.photophoto.cn/20170722/0847085858434984_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085781987193_s.jpg");
        urls.add("http://img44.photophoto.cn/20170722/0847085707961800_s.jpg");
        urls.add("http://img44.photophoto.cn/20170722/0847085229451104_s.jpg");
        urls.add("http://img44.photophoto.cn/20170720/0847085716198074_s.jpg");
        urls.add("http://img44.photophoto.cn/20170720/0847085769259426_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085717385169_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085757949071_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085789079771_s.jpg");
        urls.add("http://img44.photophoto.cn/20170722/0847085229451104_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085757949071_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0847085265005650_s.jpg");
        urls.add("http://img44.photophoto.cn/20170730/0008118269110532_s.jpg");
        urls.add("http://img44.photophoto.cn/20170731/0008118203762697_s.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0008118269666722_s.jpg");

        urls.add("http://img44.photophoto.cn/20170722/0847085858434984_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085781987193_s.jpg");
        urls.add("http://img44.photophoto.cn/20170722/0847085707961800_s.jpg");
        urls.add("http://img44.photophoto.cn/20170722/0847085229451104_s.jpg");
        urls.add("http://img44.photophoto.cn/20170720/0847085716198074_s.jpg");
        urls.add("http://img44.photophoto.cn/20170720/0847085769259426_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085717385169_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085757949071_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085789079771_s.jpg");
        urls.add("http://img44.photophoto.cn/20170722/0847085229451104_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085757949071_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0847085265005650_s.jpg");
        urls.add("http://img44.photophoto.cn/20170730/0008118269110532_s.jpg");
        urls.add("http://img44.photophoto.cn/20170731/0008118203762697_s.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0008118269666722_s.jpg");
        urls.add("http://img44.photophoto.cn/20170722/0847085858434984_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085781987193_s.jpg");
        urls.add("http://img44.photophoto.cn/20170722/0847085707961800_s.jpg");
        urls.add("http://img44.photophoto.cn/20170722/0847085229451104_s.jpg");
        urls.add("http://img44.photophoto.cn/20170720/0847085716198074_s.jpg");
        urls.add("http://img44.photophoto.cn/20170720/0847085769259426_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085717385169_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085757949071_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085789079771_s.jpg");
        urls.add("http://img44.photophoto.cn/20170722/0847085229451104_s.jpg");
        urls.add("http://img44.photophoto.cn/20170721/0847085757949071_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0847085265005650_s.jpg");
        urls.add("http://img44.photophoto.cn/20170730/0008118269110532_s.jpg");
        urls.add("http://img44.photophoto.cn/20170731/0008118203762697_s.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0008118269666722_s.jpg");

        urls.add("http://img44.photophoto.cn/20170731/0847085207211178_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0017030319740534_s.jpg");
        urls.add("http://img44.photophoto.cn/20170731/0838084002855326_s.jpg");
        urls.add("http://img44.photophoto.cn/20170728/0847085969586424_s.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0014105802293676_s.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0847085242661101_s.jpg");
        urls.add("http://img44.photophoto.cn/20170727/0886088744582079_s.jpg");
        urls.add("http://img44.photophoto.cn/20170801/0017029514287804_s.jpg");
        urls.add("http://img44.photophoto.cn/20170730/0018090594006661_s.jpg");
        urls.add("http://img44.photophoto.cn/20170729/0847085848134910_s.jpg");
        urls.add("http://img44.photophoto.cn/20170729/0847085581124963_s.jpg");
        urls.add("http://img44.photophoto.cn/20170729/0847085226124343_s.jpg");

        return urls;
    }

    /**
     * 拆分集合
     *
     * @param <T>
     * @param resList 要拆分的集合
     * @param count   每个集合的元素个数
     * @return 返回拆分后的各个集合
     */
    public static <T> List<List<T>> split(List<T> resList, int count) {
        if (resList == null || count < 1) {
            return null;
        }
        List<List<T>> ret = new ArrayList<>();
        int size = resList.size();
        if (size <= count) { //数据量不足count指定的大小
            ret.add(resList);
        } else {
            int pre = size / count;
            int last = size % count;
            //前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<>();
                for (int j = 0; j < count; j++) {
                    itemList.add(resList.get(i * count + j));
                }
                ret.add(itemList);
            }
            //last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<>();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * count + i));
                }
                ret.add(itemList);
            }
        }
        return ret;
    }


    @MemoryCache
    public static Collection<String> getDemoData() {
        return Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
    }

    @MemoryCache
    public static Collection<String> getDemoData1() {
        return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18");
    }

    /**
     * 用于占位的空信息
     *
     * @return
     */
    @MemoryCache
    public static List<NewInfo> getEmptyNewInfo() {
        List<NewInfo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new NewInfo());
        }
        return list;
    }

    /**
     * 用于占位的空信息
     *
     * @return
     */
    @MemoryCache
    public static List<NewInfo> getDemoNewInfos() {
        List<NewInfo> list = new ArrayList<>();
        list.add(new NewInfo("网购", "网络购物虽方便 退货退款不轻信")
                .setSummary("2021年4月24日，A女士照常在店内工作时，接到一通自称售后客服的电话，称A女士在抖音上购买的化妆品质量不合格，要向A女士作出赔偿。起初，A女士并未理睬就把电话挂了。后来A女士又接到了此类电话，称要给其赔付，对方还给了A女士一个二维码，A女士登录并填写了个人信息及银行卡信息，填完后对方又给A女士发了一个二维码，扫码显示要支付10000元，对方解释称这个钱是个人信息保障金，等下会连同赔偿金一起返回给A女士，随后，A女士轻信并扫码支付了10000元，但迟迟未收到返钱，A女士意识到被骗，遂报警，共计损失价值约10000元。\n")
                .setDetailUrl("http://mp.weixin.qq.com/mp/homepage?__biz=Mzg2NjA3NDIyMA==&hid=5&sn=bdee5aafe9cc2e0a618d055117c84139&scene=18#wechat_redirect")
                .setImageUrl("https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/463930705a844f638433d1b26273a7cf~tplv-k3u1fbpfcp-watermark.image"));

        list.add(new NewInfo("网贷", "网络贷款套路多 贷款不成反被骗")
                .setSummary("2021年4月5日，D先生（化名）在手机上收到了一条贷款链接短信，D先生抱着试试的心态，根据链接下载了贷款软件“闪银”。注册账号后，对方工作人员以注册信息错误、解冻账户为由要求D先生汇款。于是D先生通过手机银行APP分两次将15000元和50000元转账到对方指定银行卡上，后对方称需要开通会员才可以提现，D先生给对方提供的账户中又转入了15000元，但再次显示提现失败，对方又称款项被冻结，要求D先生继续转账，D先生发觉被骗，遂报警，共计被骗80000元。\n" +
                        "\n" +
                        "温馨提醒：若需要贷款请到银行或正规信贷机构，凡是在贷款发放前要求缴纳保证金、解冻费、做流水账或索要验证码的都是诈骗。\n")
                .setDetailUrl("https://juejin.im/post/5c3ed1dae51d4543805ea48d")
                .setImageUrl("https://user-gold-cdn.xitu.io/2019/1/16/1685563ae5456408?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));

        list.add(new NewInfo("刷单", "虚拟网络需谨慎 网友联系要小心")
                .setSummary("2021年2月7日，Z女士（化名）在家里玩手机时，在“比心”APP上接到一个一起玩游戏的订单，便加了对方QQ。对方通过QQ发来消息让Z女士帮忙刷单，之后就发来二维码订单给Z女士，让Z女士尝试支付。对方称支付订单的钱不会扣款成功的，还是在Z女士账户的。Z女士心想反正已经接了订单，也不会损失钱财，于是Z女士先后刷了三单，分别向对方账户支付了37777元、1元和37777元，但都被扣款了。Z女士立即联系对方，询问情况，对方称稍后财务人员会退还给Z女士，但之后一直没人处理，不再回复消息，Z女士察觉被骗，遂报警，共计损失价值约75555元。\n" +
                        "\n" +
                        "温馨提醒：虚拟游戏世界，不可轻信网络另一边的网友，务必提高警惕，涉及钱财等交易一律是诈骗。")
                .setDetailUrl("https://juejin.im/post/5b480b79e51d45190905ef44")
                .setImageUrl("https://user-gold-cdn.xitu.io/2018/7/13/16492d9b7877dc21?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));

        list.add(new NewInfo("网恋", "你在网恋 他在诈骗")
                .setSummary("2021年3月10日，F女士（化名）在QQ聊天时遇到一位头像阳光帅气的小伙子加其QQ，两人聊得很投缘，便很快以男女朋友的形式交往。接触一段时间后，对方推荐F女士一个网站“艾森信托”称可以赚钱，F女士对“男朋友”的话深信不疑，就通过手机银行转账充值了500元，后又陆续充值了1000元和23000元。起初，该平台是可以提现的，F女士分次共提现1855.14元，但随后平台就不能提现了，共计损失价值约22644.86元。\n" +
                        "\n" +
                        "温馨提醒：天上掉下高富帅、白富美，对你百般体贴，还带你投资理财，让你以为遇见了真爱，其实是骗子设置的陷阱以进行诈骗。")
                .setDetailUrl("https://juejin.im/post/5b6b9b49e51d4576b828978d")
                .setImageUrl("https://user-gold-cdn.xitu.io/2018/8/9/1651c568a7e30e02?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));

        list.add(new NewInfo("中奖", "莫名短信有蹊跷 点赞福利不可信")
                .setSummary("2021年2月19日，y先生（化名）在手机上收到一条点赞送福利的短信，y先生比较好奇就加了对方微信。通过微信聊天，对方让y先生下载密聊app，称只要跟着操作，就可以赚钱。一听可以赚钱，y先生比较心动，就在星际互娱网站上听他们指导操作，试了几次后，确实有钱赚，y先生就相信对方了。但是在不间断的转账之后，y先生发现平台无法提现了，意识到被骗，遂报警，共计损失价值约58044元。\n" +
                        "\n" +
                        "温馨提醒：不要有贪图小便宜和轻轻松松赚大钱的心理，不然就会落入骗子的圈套。")
                .setDetailUrl("https://juejin.im/post/5c6fc0cdf265da2dda694f05")
                .setImageUrl("https://user-gold-cdn.xitu.io/2019/2/22/16914891cd8a950a?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));
        return list;
    }

    /**
     * 用于占位的空信息
     *
     * @return
     */
    @MemoryCache
    public static List<NewInfo> getSpecialDemoNewInfos() {
        List<NewInfo> list = new ArrayList<>();


        list.add(new NewInfo("Flutter", "Flutter学习指南App,一起来玩Flutter吧~")
                .setSummary("Flutter是谷歌的移动UI框架，可以快速在iOS、Android、Web和PC上构建高质量的原生用户界面。 Flutter可以与现有的代码一起工作。在全世界，Flutter正在被越来越多的开发者和组织使用，并且Flutter是完全免费、开源的。同时它也是构建未来的Google Fuchsia应用的主要方式。")
                .setDetailUrl("https://juejin.im/post/5e39a1b8518825497467e4ec")
                .setImageUrl("https://pic4.zhimg.com/v2-1236d741cbb3aabf5a9910a5e4b73e4c_1200x500.jpg"));

        list.add(new NewInfo("Android UI", "XUI 一个简洁而优雅的Android原生UI框架，解放你的双手")
                .setSummary("涵盖绝大部分的UI组件：TextView、Button、EditText、ImageView、Spinner、Picker、Dialog、PopupWindow、ProgressBar、LoadingView、StateLayout、FlowLayout、Switch、Actionbar、TabBar、Banner、GuideView、BadgeView、MarqueeView、WebView、SearchView等一系列的组件和丰富多彩的样式主题。\n")
                .setDetailUrl("https://juejin.im/post/5c3ed1dae51d4543805ea48d")
                .setImageUrl("https://user-gold-cdn.xitu.io/2019/1/16/1685563ae5456408?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));

        list.add(new NewInfo("Android", "XUpdate 一个轻量级、高可用性的Android版本更新框架")
                .setSummary("XUpdate 一个轻量级、高可用性的Android版本更新框架。本框架借鉴了AppUpdate中的部分思想和UI界面，将版本更新中的各部分环节抽离出来，形成了如下几个部分：")
                .setDetailUrl("https://juejin.im/post/5b480b79e51d45190905ef44")
                .setImageUrl("https://user-gold-cdn.xitu.io/2018/7/13/16492d9b7877dc21?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));


        list.add(new NewInfo("Android/HTTP", "XHttp2 一个功能强悍的网络请求库，使用RxJava2 + Retrofit2 + OKHttp进行组装")
                .setSummary("一个功能强悍的网络请求库，使用RxJava2 + Retrofit2 + OKHttp组合进行封装。还不赶紧点击使用说明文档，体验一下吧！")
                .setDetailUrl("https://juejin.im/post/5b6b9b49e51d4576b828978d")
                .setImageUrl("https://user-gold-cdn.xitu.io/2018/8/9/1651c568a7e30e02?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));

        list.add(new NewInfo("源码", "Android源码分析--Android系统启动")
                .setSummary("其实Android系统的启动最主要的内容无非是init、Zygote、SystemServer这三个进程的启动，他们一起构成的铁三角是Android系统的基础。")
                .setDetailUrl("https://juejin.im/post/5c6fc0cdf265da2dda694f05")
                .setImageUrl("https://user-gold-cdn.xitu.io/2019/2/22/16914891cd8a950a?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));

        return list;
    }

    @MemoryCache
    public static List<DefaultGroupedItem> getGroupItems() {
        List<DefaultGroupedItem> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                items.add(new DefaultGroupedItem(true, "菜单" + i / 10));
            } else {
                items.add(new DefaultGroupedItem(new DefaultGroupedItem.ItemInfo("这是标题" + i, "菜单" + i / 10, "这是内容" + i)));
            }
        }
        return items;
    }
    /**
     * 获取时间段
     *
     * @param interval 时间间隔（分钟）
     * @return
     */
    public static String[] getTimePeriod(int interval) {
        return getTimePeriod(24, interval);
    }

    /**
     * 获取时间段
     *
     * @param interval 时间间隔（分钟）
     * @return
     */
    public static String[] getTimePeriod(int totalHour, int interval) {
        String[] time = new String[totalHour * 60 / interval];
        int point, hour, min;
        for (int i = 0; i < time.length; i++) {
            point = i * interval;
            hour = point / 60;
            min = point - hour * 60;
            time[i] = (hour < 9 ? "0" + hour : "" + hour) + ":" + (min < 9 ? "0" + min : "" + min);
        }
        return time;
    }


    /**
     * 获取时间段
     *
     * @param interval 时间间隔（分钟）
     * @return
     */
    public static String[] getTimePeriod(int startHour, int totalHour, int interval) {
        String[] time = new String[totalHour * 60 / interval];
        int point, hour, min;
        for (int i = 0; i < time.length; i++) {
            point = i * interval + startHour * 60;
            hour = point / 60;
            min = point - hour * 60;
            time[i] = (hour < 9 ? "0" + hour : "" + hour) + ":" + (min < 9 ? "0" + min : "" + min);
        }
        return time;
    }

}
