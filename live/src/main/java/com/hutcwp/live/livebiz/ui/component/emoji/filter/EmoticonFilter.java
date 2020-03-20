package com.hutcwp.live.livebiz.ui.component.emoji.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Pair;

import com.hutcwp.live.livebiz.base.util.BasicConfig;
import com.hutcwp.live.livebiz.base.util.MLog;
import com.hutcwp.live.livebiz.ui.component.emoji.CustomImageSpan;
import com.hutcwp.live.livebiz.ui.component.emoji.IEmoticonsDataInterface;
import com.hutcwp.livebiz.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


/**
 * Created by huangjinwen on 2014/6/20.
 */
public class EmoticonFilter extends BaseRichTextFilter {

    private static final String TAG = "EmoticonFilter";

    private static final String[] sSmileCodes;
    private static final int[] sSmileResId;
    private static List<SmileItem> sSmileList;
    private static Set<SmileCompare> sSmileOrderSet;

    public static final String FLAG = "/{";

    private static final String[] nSmileCodes;
    private static final int[] nSmileResId;
    private static List<SmileItem> nSmileList;
    private static Set<SmileCompare> nSmileOrderSet;

    private static final String[] allSmileCodes;
    private static final int[] allSmileResId;
    private static List<SmileItem> allSmileList;
    private static Set<SmileCompare> allSmileOrderSet;

    public static final int SMILE_TYPE_GENERAL = 1;
    public static final int SMILE_TYPE_NEW = 2;

    static {
        sSmileCodes = new String[]{
                "/{wx",
                /* 微笑 */"/{dx",
                /* 大笑 */"/{tp",
                /* 调皮 */ "/{jy",
                /* 惊讶 */"/{pz",
                /* 撇嘴 */"/{fn",
                /* 发怒 */ "/{ng",
                /* 难过 */"/{hk",
                /* 很酷 */"/{kz",
                /* 口罩 */ "/{ot",
                /* 呕吐 */"/{se",
                /* 色 */"/{tx",
                /* 偷笑 */ "/{ka",
                /* 可爱 */"/{by",
                /* 白眼 */"/{am",
                /* 傲慢 */ "/{kun",
                /* 困 */"/{hp",
                /* 害怕 */"/{lh",
                /* 流汗 */ "/{kx",
                /* 开心 */"/{cy",
                /* 抽烟 */"/{ll",
                /* 流泪 */ "/{fd",
                /* 奋斗 */"/{yw",
                /* 疑问 */"/{xu",
                /* 嘘 */ "/{yun",
                /* 晕 */"/{zs",
                /* 衰 */"/{kl",
                /* 骷髅 */ "/{qd",
                /* 敲打 */"/{88",
                /* 拜拜 */"/{dy",
                /* 得意 */ "/{zt",
                /* 猪头 */"/{bz",
                /* 闭嘴 */"/{yb",
                /* 拥抱 */ "/{dai",
                /* 发呆 */"/{sj",
                /* 睡觉 */"/{hx",
                /* 害羞 */ "/{gz",
                /* 鼓掌 */"/{kb",
                /* 抠鼻 */"/{kel",
                /* 可怜 */ "/{qq",
                /* 亲亲 */"/{wq",
                /* 委屈 */"/{yx",
                /* 阴险 */ "/{zk",
                /* 抓狂 */"/{bs",
                /* 鄙视 */"/{bq",
                /* 抱拳 */ "/{ok",
                /* ok */"/{zan",
                /* 赞 */"/{ruo",
                /* 弱 */ "/{ws",
                /* 握手 */"/{sl",
                /* 胜利 */"/{mg",
                /* 玫瑰 */ "/{kw",
                /* 枯萎 */"/{wen",
                /* 吻 */"/{xd",
                /* 心动 */ "/{xs",
                /* 心碎 */"/{lw",
                /* 礼物 */"/{sd",
                /* 闪电 */ "/{zd",
                /* 炸弹 */"/{dao",
                /* 刀 */"/{cc",
                /* 臭臭 */
        };

        sSmileResId = new int[]{
                R.drawable.wx,
                /*微笑*/ R.drawable.dx,
                /*大笑*/ R.drawable.tp,
                /*调皮*/ R.drawable.jy,
                /*惊讶*/ R.drawable.pz,
                /*撇嘴*/ R.drawable.fn,
                /*发怒*/ R.drawable.ng,
                /*难过*/ R.drawable.hk,
                /*很酷*/ R.drawable.kz,
                /*口罩*/ R.drawable.ot,
                /*呕吐*/ R.drawable.se,
                /*色*/ R.drawable.tx,
                /*偷笑*/ R.drawable.ka,
                /*可爱*/ R.drawable.by,
                /*白眼*/ R.drawable.am,
                /*傲慢*/ R.drawable.kun,
                /*困*/ R.drawable.hp,
                /*害怕*/ R.drawable.lh,
                /*流汗*/ R.drawable.kx,
                /*开心*/ R.drawable.cy,
                /*抽烟*/ R.drawable.ll,
                /*流泪*/ R.drawable.fd,
                /*奋斗*/ R.drawable.yw,
                /*疑问*/ R.drawable.xu,
                /*嘘*/ R.drawable.yun,
                /*晕*/ R.drawable.zs,
                /*衰*/ R.drawable.kul,
                /*骷髅*/ R.drawable.qd,
                /*敲打*/ R.drawable.bb,
                /*拜拜*/ R.drawable.dy,
                /*得意*/ R.drawable.zt,
                /*猪头*/ R.drawable.bz,
                /*闭嘴*/ R.drawable.yb,
                /*拥抱*/ R.drawable.dai,
                /*发呆*/ R.drawable.sj,
                /*睡觉*/ R.drawable.hx,
                /*害羞*/ R.drawable.gz,
                /* 鼓掌 */R.drawable.kb,
                /* 抠鼻 */R.drawable.kl,
                /* 可怜 */ R.drawable.qq,
                /* 亲亲 */R.drawable.wq,
                /* 委屈 */R.drawable.yx,
                /* 阴险 */ R.drawable.zk,
                /* 抓狂 */R.drawable.bs,
                /* 鄙视 */R.drawable.bq,
                /* 抱拳 */ R.drawable.ok,
                /* ok */ R.drawable.zan,
                /* 赞 */ R.drawable.ruo,
                /* 弱 */ R.drawable.ws,
                /* 握手 */R.drawable.sl,
                /* 胜利 */R.drawable.mg,
                /* 玫瑰 */ R.drawable.kw,
                /* 枯萎 */R.drawable.wen,
                /* 吻 */R.drawable.xd,
                /* 心动 */ R.drawable.xs,
                /* 心碎 */R.drawable.lw,
                /* 礼物 */R.drawable.sd,
                /* 闪电 */ R.drawable.zd,
                /* 炸弹 */R.drawable.dao,
                /* 刀 */R.drawable.cc,
                /* 臭臭 */
        };

        sSmileList = new ArrayList<SmileItem>(sSmileCodes.length);
        sSmileOrderSet = new TreeSet<SmileCompare>();

        //新表情
        nSmileCodes = new String[]{
                "/{nbq",
                /* 新抱拳 */"/{ncy",
                /* 新抽烟 */"/{nse",
                /* 新色 */ "/{nxd",
                /* 新心动 */"/{nyb",
                /* 新拥抱 */"/{nkb",
                /* 新抠鼻 */ "/{nku",
                /* 新哭 */ "/{not",
                /* 新呕吐 */"/{sjt",
                /* 上箭头 */ "/{xjt",
                /* 下箭头 */"/{zjt",
                /* 左箭头 */"/{yjt",
                /* 右箭头 */ "/{nbs",
                /* 新鄙视 */"/{ndx",
                /* 新大笑 */"/{ngz",
                /* 新鼓掌 */ "/{nhx",
                /* 新害羞 */"/{nlh",
                /* 新流汉 */"/{nzs",
                /* 新衰*/ "/{nsj",
                /* 新睡觉 */"/{ntx",
                /* 新偷笑 */"/{nwx",
                /* 新微笑*/ "/{nwq",
                /* 新委曲 */
        };

        nSmileResId = new int[]{
                R.drawable.bq,
                /*新抱拳*/ R.drawable.cy,
                /*新抽烟*/ R.drawable.se,
                /*新色*/ R.drawable.xd,
                /*新心动*/ R.drawable.yb,
                /*新拥抱*/ R.drawable.kb,
                /*新抠鼻*/ R.drawable.ll,
                /*新哭*/ R.drawable.ot,
                /*新呕吐*/ R.drawable.sjt,
                /*上箭头*/ R.drawable.xjt,
                /*下箭头*/ R.drawable.zjt,
                /*左箭头*/ R.drawable.yjt,
                /*右箭头*/ R.drawable.bs,
                /*新鄙视*/ R.drawable.dx,
                /*新大笑*/ R.drawable.gz,
                /*新鼓掌*/ R.drawable.hx,
                /*新害羞*/ R.drawable.lh,
                /* 新流汉 */R.drawable.zs,
                /* 新衰*/ R.drawable.sj,
                /* 新睡觉 */R.drawable.tx,
                /* 新偷笑 */R.drawable.wx,
                /* 新微笑 */ R.drawable.wq,
                /* 新委曲 */
        };

        nSmileList = new ArrayList<SmileItem>(nSmileCodes.length);
        nSmileOrderSet = new TreeSet<SmileCompare>();

        allSmileCodes = new String[]{
                "/{wx",
                /* 微笑 */"/{dx",
                /* 大笑 */"/{tp",
                /* 调皮 */ "/{jy",
                /* 惊讶 */"/{pz",
                /* 撇嘴 */"/{fn",
                /* 发怒 */ "/{ng",
                /* 难过 */"/{hk",
                /* 很酷 */"/{kz",
                /* 口罩 */ "/{ot",
                /* 呕吐 */"/{se",
                /* 色 */"/{tx",
                /* 偷笑 */ "/{ka",
                /* 可爱 */"/{by",
                /* 白眼 */"/{am",
                /* 傲慢 */ "/{kun",
                /* 困 */"/{hp",
                /* 害怕 */"/{lh",
                /* 流汗 */ "/{kx",
                /* 开心 */"/{cy",
                /* 抽烟 */"/{ll",
                /* 流泪 */ "/{fd",
                /* 奋斗 */"/{yw",
                /* 疑问 */"/{xu",
                /* 嘘 */ "/{yun",
                /* 晕 */"/{zs",
                /* 衰 */"/{kl",
                /* 骷髅 */ "/{qd",
                /* 敲打 */"/{88",
                /* 拜拜 */"/{dy",
                /* 得意 */ "/{zt",
                /* 猪头 */"/{bz",
                /* 闭嘴 */"/{yb",
                /* 拥抱 */ "/{dai",
                /* 发呆 */"/{sj",
                /* 睡觉 */"/{hx",
                /* 害羞 */ "/{gz",
                /* 鼓掌 */"/{kb",
                /* 抠鼻 */"/{kel",
                /* 可怜 */ "/{qq",
                /* 亲亲 */"/{wq",
                /* 委屈 */"/{yx",
                /* 阴险 */ "/{zk",
                /* 抓狂 */"/{bs",
                /* 鄙视 */"/{bq",
                /* 抱拳 */ "/{ok",
                /* ok */"/{zan",
                /* 赞 */"/{ruo",
                /* 弱 */ "/{ws",
                /* 握手 */"/{sl",
                /* 胜利 */"/{mg",
                /* 玫瑰 */ "/{kw",
                /* 枯萎 */"/{wen",
                /* 吻 */"/{xd",
                /* 心动 */ "/{xs",
                /* 心碎 */"/{lw",
                /* 礼物 */"/{sd",
                /* 闪电 */ "/{zd",
                /* 炸弹 */"/{dao",
                /* 刀 */"/{cc",
                /* 臭臭 */ "/{nbq",
                /*新抱拳*/ "/{ncy",
                /*新抽烟 */"/{nse",
                /*新色 */ "/{nxd",
                /*新心动 */"/{nyb",
                /*新拥抱 */"/{nkb",
                /*新抠鼻 */ "/{nku",
                /*新哭 */ "/{not",
                /*新呕吐*/ "/{sjt",
                /* 上箭头 */ "/{xjt",
                /*下箭头*/ "/{zjt",
                /*左箭头 */"/{yjt",
                /*右箭头 */ "/{nbs",
                /*新鄙视 */"/{ndx",
                /*新大笑*/ "/{ngz",
                /*新鼓掌*/ "/{nhx",
                /*新害羞*/ "/{nlh",
                /*新流汉*/ "/{nzs",
                /*新衰*/ "/{nsj",
                /*新睡觉 */"/{ntx",
                /*新偷笑 */"/{nwx",
                /*新微笑*/ "/{nwq",
                /*新委曲 */
        };

        allSmileResId = new int[]{
                R.drawable.wx,
                /*微笑*/ R.drawable.dx,
                /*大笑*/ R.drawable.tp,
                /*调皮*/ R.drawable.jy,
                /*惊讶*/ R.drawable.pz,
                /*撇嘴*/ R.drawable.fn,
                /*发怒*/ R.drawable.ng,
                /*难过*/ R.drawable.hk,
                /*很酷*/ R.drawable.kz,
                /*口罩*/ R.drawable.ot,
                /*呕吐*/ R.drawable.se,
                /*色*/ R.drawable.tx,
                /*偷笑*/ R.drawable.ka,
                /*可爱*/ R.drawable.by,
                /*白眼*/ R.drawable.am,
                /*傲慢*/ R.drawable.kun,
                /*困*/ R.drawable.hp,
                /*害怕*/ R.drawable.lh,
                /*流汗*/ R.drawable.kx,
                /*开心*/ R.drawable.cy,
                /*抽烟*/ R.drawable.ll,
                /*流泪*/ R.drawable.fd,
                /*奋斗*/ R.drawable.yw,
                /*疑问*/ R.drawable.xu,
                /*嘘*/ R.drawable.yun,
                /*晕*/ R.drawable.zs,
                /*衰*/ R.drawable.kul,
                /*骷髅*/ R.drawable.qd,
                /*敲打*/ R.drawable.bb,
                /*拜拜*/ R.drawable.dy,
                /*得意*/ R.drawable.zt,
                /*猪头*/ R.drawable.bz,
                /*闭嘴*/ R.drawable.yb,
                /*拥抱*/ R.drawable.dai,
                /*发呆*/ R.drawable.sj,
                /*睡觉*/ R.drawable.hx,
                /*害羞*/ R.drawable.gz,
                /* 鼓掌 */R.drawable.kb,
                /* 抠鼻 */R.drawable.kl,
                /* 可怜 */ R.drawable.qq,
                /* 亲亲 */R.drawable.wq,
                /* 委屈 */R.drawable.yx,
                /* 阴险 */ R.drawable.zk,
                /* 抓狂 */R.drawable.bs,
                /* 鄙视 */R.drawable.bq,
                /* 抱拳 */ R.drawable.ok,
                /* ok */ R.drawable.zan,
                /* 赞 */ R.drawable.ruo,
                /* 弱 */ R.drawable.ws,
                /* 握手 */R.drawable.sl,
                /* 胜利 */R.drawable.mg,
                /* 玫瑰 */ R.drawable.kw,
                /* 枯萎 */R.drawable.wen,
                /* 吻 */R.drawable.xd,
                /* 心动 */ R.drawable.xs,
                /* 心碎 */R.drawable.lw,
                /* 礼物 */R.drawable.sd,
                /* 闪电 */ R.drawable.zd,
                /* 炸弹 */R.drawable.dao,
                /* 刀 */R.drawable.cc,
                /* 臭臭 */ R.drawable.bq,
                /*新抱拳*/ R.drawable.cy,
                /*新抽烟*/ R.drawable.se,
                /*新色*/ R.drawable.xd,
                /*新心动*/ R.drawable.yb,
                /*新拥抱*/ R.drawable.kb,
                /*新抠鼻*/ R.drawable.ll,
                /*新哭*/ R.drawable.ot,
                /*新呕吐 */ R.drawable.sjt,
                /*上箭头*/ R.drawable.xjt,
                /*下箭头 */R.drawable.zjt,
                /*左箭头*/ R.drawable.yjt,
                /*右箭头*/ R.drawable.bs,
                /*新鄙视*/ R.drawable.dx,
                /*新大笑*/ R.drawable.gz,
                /*新鼓掌*/ R.drawable.hx,
                /*新害羞*/ R.drawable.lh,
                /*新流汉*/ R.drawable.zs,
                /* 新衰*/ R.drawable.sj,
                /*新睡觉*/ R.drawable.tx,
                /*新偷笑*/ R.drawable.wx,
                /*新微笑*/ R.drawable.wq,
                /*新委曲 */
        };

        allSmileList = new ArrayList<SmileItem>(allSmileCodes.length);
        allSmileOrderSet = new TreeSet<SmileCompare>();

    }

    public static String[] getAllSmileCodes() {
        return allSmileCodes;
    }

    /**
     * 获取新表情集合
     *
     * @param context
     * @return
     */
    public static Set<SmileCompare> getNSmileSet(Context context) {
        if (nSmileOrderSet.isEmpty()) {
            createNew(context);
        }
        return new TreeSet<SmileCompare>(nSmileOrderSet);
    }

    /**
     * 获取所有表情
     *
     * @param context
     * @return
     */
    public static List<SmileItem> getAllSmileList(Context context) {
        if (allSmileList.isEmpty()) {
            createAllNew(context);
        }
        return new ArrayList<SmileItem>(allSmileList);
    }

    /**
     * 获取新表情
     *
     * @param context
     * @return
     */
    public static List<SmileItem> getNSmileList(Context context) {
        if (nSmileList.isEmpty()) {
            createNew(context);
        }
        return new ArrayList<SmileItem>(nSmileList);
    }

    public static List<SmileItem> getSmileList(Context context) {
        if (sSmileList.isEmpty()) {
            create(context);
        }
        return new ArrayList<SmileItem>(sSmileList);
    }

    public static class SmileItem implements IEmoticonsDataInterface {
        public String mText;
        public Bitmap mIcon;       //选择框显示
        public Drawable mDrawable; //聊天显示

        public CharSequence getCharSequence(Context c) {
            SpannableString sp = new SpannableString(mText);
            sp.setSpan(new ImageSpan(c, mIcon), 0, mText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sp;
        }

        @Override
        public Bitmap getBitmap() {
            return mIcon;
        }

        @Override
        public String getText() {
            return mText;
        }

        @Override
        public int getAlphaValue() {
            return COMPLETE_OPAQUE;
        }
    }

    public static class SmileCompare implements Comparable<SmileCompare> {
        static SmileCompare sSearch = new SmileCompare();
        static SmileCompare sTemp = new SmileCompare();

        SmileItem mItem;

        public char[] mKey; //去除/{的字符
        public int mKeyBase; //0
        public int mKeyLength; //mKey的长度

        public int compareTo(SmileCompare o) {

            int nTempLen = 0;
            SmileCompare src = this;
            SmileCompare obj = o;

            SmileCompare item = null;

            if (sTemp != null) {
                //Obj = Temp
                if (this == sTemp) {
                    item = o;
                } else {
                    item = this;
                }
            }

            int pos = 0;
            try {
                //如果多线程修改了mKey的内容，这里就会报ArrayIndexOutOfBoundsException maint分支先简单防御7.8livebiz分支我来修复
                while (src.mKeyBase + pos < src.mKey.length && obj.mKeyBase + pos < obj.mKey.length) {
                    if (src.mKey[src.mKeyBase + pos] > obj.mKey[obj.mKeyBase + pos]) {
                        return 1;
                    }

                    if (src.mKey[src.mKeyBase + pos] < obj.mKey[obj.mKeyBase + pos]) {
                        return -1;
                    }

                    pos++;
                }
            } catch (Exception e) {
                //
                MLog.error(TAG, "compile exception : ");
                return 1;
            }

            if ((item != null) && (item.mKeyLength > sTemp.mKeyLength)) {
                if (item == src) {
                    return 1;
                } else {
                    return -1;
                }
            }

            if (sTemp != null) {
                sSearch = item;
                sTemp.mKeyLength = nTempLen;
            } else {
                sSearch = null;
            }

            return 0;
        }
    }

    /**
     * 创建所有表情
     *
     * @param context
     */
    public static synchronized void createAllNew(Context context) {
        int count = allSmileCodes.length;
        if (context == null) {
            return;
        }
        if (!allSmileList.isEmpty()) {
            if (MLog.isLogLevelAboveDebug()) {
                MLog.debug(TAG, "nSmileList is not empty. return.");
            }
            return;
        }

        for (int i = 0; i < count; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), allSmileResId[i]);

            SmileItem s = new SmileItem();
            s.mText = allSmileCodes[i];
            s.mIcon = bmp;
            s.mDrawable = new BitmapDrawable(context.getResources(), s.mIcon);
            int size = context.getResources().getDimensionPixelOffset(R.dimen.emotion_span_size);
            s.mDrawable.setBounds(0, 0, size, size);
            allSmileList.add(s);

            SmileCompare sc = new SmileCompare();
            sc.mItem = s;

            int length = s.mText.length();

            sc.mKeyBase = 0;
            sc.mKeyLength = length - 2;

            sc.mKey = new char[sc.mKeyLength];
            s.mText.getChars(2, length, sc.mKey, 0);
            try {
                allSmileOrderSet.add(sc);
            } catch (Throwable throwable) {
                MLog.error(TAG, "EmoticonFilter createAllNew" + throwable);
            }
        }

        SmileCompare.sTemp = new SmileCompare();
    }

    /**
     * 创建新表情
     *
     * @param context
     */
    public static void createNew(Context context) {
        int count = nSmileCodes.length;
        if (context == null) {
            return;
        }
        if (!nSmileList.isEmpty()) {
            if (MLog.isLogLevelAboveDebug()) {
                MLog.debug(TAG, "nSmileList is not empty. return.");
            }
            return;
        }

        for (int i = 0; i < count; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), nSmileResId[i]);

            SmileItem s = new SmileItem();
            s.mText = nSmileCodes[i];
            s.mIcon = bmp;
            s.mDrawable = new BitmapDrawable(context.getResources(), s.mIcon);
            int size = context.getResources().getDimensionPixelOffset(R.dimen.emotion_span_size);
            s.mDrawable.setBounds(0, 0, size, size);
            nSmileList.add(s);

            SmileCompare sc = new SmileCompare();
            sc.mItem = s;

            int length = s.mText.length();

            sc.mKeyBase = 0;
            sc.mKeyLength = length - 2;

            sc.mKey = new char[sc.mKeyLength];
            s.mText.getChars(2, length, sc.mKey, 0);

            try {
                nSmileOrderSet.add(sc);
            } catch (Throwable throwable) {
                MLog.error(TAG, "EmoticonFilter createNew" + throwable);
            }
        }

        SmileCompare.sTemp = new SmileCompare();
    }

    public static void create(Context context) {
        int count = sSmileCodes.length;
        if (context == null) {
            return;
        }
        if (!sSmileList.isEmpty()) {
            if (MLog.isLogLevelAboveDebug()) {
                MLog.debug(TAG, "sSmileList is not empty. return.");
            }
            return;
        }

        for (int i = 0; i < count; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), sSmileResId[i]);

            SmileItem s = new SmileItem();
            s.mText = sSmileCodes[i];
            s.mIcon = bmp;
            s.mDrawable = new BitmapDrawable(context.getResources(), s.mIcon);
            int size = context.getResources().getDimensionPixelOffset(R.dimen.emotion_span_size);
            s.mDrawable.setBounds(0, 0, size, size);
            sSmileList.add(s);

            SmileCompare sc = new SmileCompare();
            sc.mItem = s;

            int length = s.mText.length();

            sc.mKeyBase = 0;
            sc.mKeyLength = length - 2;

            sc.mKey = new char[sc.mKeyLength];
            s.mText.getChars(2, length, sc.mKey, 0);

            sSmileOrderSet.add(sc);
        }

        SmileCompare.sTemp = new SmileCompare();
    }

    @Override
    public void parseSpannable(Context context, Spannable spannable, int maxWidth) {
        parseSpannable(context, spannable, maxWidth, null);
    }

    @Override
    public void parseSpannable(Context context, Spannable spannable, int maxWidth, int normal) {
        parseSpannableAllNew(context, spannable, maxWidth, normal, null);
    }

    @Override
    public void parseSpannable(Context context, Spannable spannable, int maxWidth, Object tag) {
        //TODO:文本过多时遍历时间过长
        //parseSpannableOrg(context, spannable, maxWidth, tag);
        //parseSpannableNew(context, spannable, maxWidth, tag);
        parseSpannableAllNew(context, spannable, maxWidth, 0, tag);
    }

    public static boolean isContainSmile(String text, Context context) {

        if (nSmileList.isEmpty()) {
            createNew(context);
        }
        String str = text;
        str = str.replace("\r", "\n");

        int span = 0;
        char[] data = null;
        int len = str.length();

        while (true) {

            span = str.indexOf("/{", span);

            if (span == -1) {
                return false;
            }

            if (data == null) {
                data = new char[str.length()];
                str.getChars(0, str.length(), data, 0);
            }

            SmileCompare.sTemp.mKey = data;
            SmileCompare.sTemp.mKeyBase = span + 2;
            SmileCompare.sTemp.mKeyLength = len - span - 2;

            if (!nSmileOrderSet.contains(SmileCompare.sTemp)) {
                span += 2;
                continue;
            } else {
                return true;
            }

        }

    }

    public static boolean isContainNewSmile(String text, Context context) {

        if (allSmileList.isEmpty()) {
            createAllNew(context);
        }
        String str = text;
        str = str.replace("\r", "\n");

        int span = 0;
        char[] data = null;
        int len = str.length();

        while (true) {

            span = str.indexOf("/{", span);

            if (span == -1) {
                return false;
            }

            if (data == null) {
                data = new char[str.length()];
                str.getChars(0, str.length(), data, 0);
            }

            SmileCompare.sTemp.mKey = data;
            SmileCompare.sTemp.mKeyBase = span + 2;
            SmileCompare.sTemp.mKeyLength = len - span - 2;

            if (!allSmileOrderSet.contains(SmileCompare.sTemp)) {
                span += 2;
                continue;
            } else {
                return true;
            }

        }

    }

    public static float measureWidth(
            Context context,
            String text,
            float x,
            float y,
            int width,
            int height,
            Paint paint,
            int smileType
    ) {
        if (smileType == SMILE_TYPE_GENERAL) {
            if (sSmileList.isEmpty()) {
                create(context);
            }
            return measureWidthInternal(context, text, x, y, width, height, paint, sSmileOrderSet);
        } else if (smileType == SMILE_TYPE_NEW) {
            /*if (nSmileList.isEmpty()) {
                createNew(context);
            }
            return measureWidthInternal(context, text, x, y, width, height, paint, nSmileOrderSet);*/

            if (allSmileList.isEmpty()) {
                createAllNew(context);
            }
            return measureWidthInternal(context, text, x, y, width, height, paint, allSmileOrderSet);

        }
        return 0;
    }

    private static float measureWidthInternal(
            Context context,
            String text,
            float x,
            float y,
            int width,
            int height,
            Paint paint,
            Set<SmileCompare> smileCompares
    ) {
        String str = text;
        str = str.replace("\r", "\n");

        int span = 0;
        char[] data = null;
        int lastIndex;
        int len = str.length();
        float returnLen = 0;

        while (true) {
            lastIndex = span;
            span = str.indexOf("/{", span);

            if (span == -1) {
                if (lastIndex < str.length()) {
                    String subStr = str.substring(lastIndex);
                    returnLen += paint.measureText(subStr);
                }
                return returnLen;
            }

            if (data == null) {
                data = new char[str.length()];
                str.getChars(0, str.length(), data, 0);
            }

            SmileCompare.sTemp.mKey = data;
            SmileCompare.sTemp.mKeyBase = span + 2;
            SmileCompare.sTemp.mKeyLength = len - span - 2;

            if (!smileCompares.contains(SmileCompare.sTemp)) {
                //没命中表情，画出text
                span += 2;
                String subStr = str.substring(lastIndex, span);
                float l = paint.measureText(subStr);
                returnLen += l;
                continue;
            }

            //命中表情，首先把文字画出来
            if (lastIndex < span) {
                String subStr = str.substring(lastIndex, span);
                float l = paint.measureText(subStr);
                returnLen += l;
            }
            //把表情画出来
            SmileItem s = SmileCompare.sSearch.mItem;
            int w = s.getBitmap().getWidth() * height / s.getBitmap().getHeight();
            returnLen += w;

            span += s.mText.length();
        }
    }

    public static float parseToBitmap(
            Context context,
            Canvas canvas,
            String text,
            float x,
            float y,
            int width,
            int height,
            Paint paint,
            int smileType
    ) {
        if (smileType == SMILE_TYPE_GENERAL) {
            if (sSmileList.isEmpty()) {
                create(context);
            }
            return parseToBitmapInternal(context, canvas, text, x, y, width, height, paint, sSmileOrderSet);
        } else if (smileType == SMILE_TYPE_NEW) {
            /*if (nSmileList.isEmpty()) {
                createNew(context);
            }
            return parseToBitmapInternal(context, canv
              as, text, x, y, width, height, paint, nSmileOrderSet);*/

            if (allSmileList.isEmpty()) {
                createAllNew(context);
            }
            return parseToBitmapInternal(context, canvas, text, x, y, width, height, paint, allSmileOrderSet);

        }
        return 0;
    }

    public static float parseToBitmapStroke(
            Context context,
            Canvas canvas,
            String text,
            float x,
            float y,
            int width,
            int height,
            Paint paint,
            int smileType
    ) {
        if (smileType == SMILE_TYPE_GENERAL) {
            if (sSmileList.isEmpty()) {
                create(context);
            }
            return parseToBitmapInternalStroke(context, canvas, text, x, y, width, height, paint, sSmileOrderSet);
        } else if (smileType == SMILE_TYPE_NEW) {
            if (allSmileList.isEmpty()) {
                createAllNew(context);
            }
            return parseToBitmapInternalStroke(context, canvas, text, x, y, width, height, paint, allSmileOrderSet);

        }
        return 0;
    }

    private static float parseToBitmapInternalStroke(
            Context context,
            Canvas canvas,
            String text,
            float x,
            float y,
            int width,
            int height,
            Paint paint,
            Set<SmileCompare> smileCompares
    ) {
        String str = text;
        str = str.replace("\r", "\n");

        int span = 0;
        char[] data = null;
        float lastOffset = x;
        int lastIndex;
        int len = str.length();
        float returnLen = 0;

        while (true) {
            lastIndex = span;
            span = str.indexOf("/{", span);

            if (span == -1) {
                if (lastIndex < str.length()) {
                    String subStr = str.substring(lastIndex);

                    Paint strokePaint = new Paint(paint);
                    strokePaint.setShadowLayer(0, 0, 0, Color.BLACK);
                    strokePaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    strokePaint.setStrokeWidth(context.getResources().getDisplayMetrics().density);
                    strokePaint.setColor(Color.BLACK);
                    canvas.drawText(subStr, lastOffset, y, strokePaint);

                    canvas.drawText(subStr, lastOffset, y, paint);
                    returnLen += paint.measureText(subStr);
                }
                return returnLen;
            }

            if (data == null) {
                data = new char[str.length()];
                str.getChars(0, str.length(), data, 0);
            }

            SmileCompare.sTemp.mKey = data;
            SmileCompare.sTemp.mKeyBase = span + 2;
            SmileCompare.sTemp.mKeyLength = len - span - 2;

            if (!smileCompares.contains(SmileCompare.sTemp)) {
                //没命中表情，画出text
                span += 2;
                String subStr = str.substring(lastIndex, span);

                Paint strokePaint = new Paint(paint);
                strokePaint.setShadowLayer(0, 0, 0, Color.BLACK);
                strokePaint.setStyle(Paint.Style.FILL_AND_STROKE);
                strokePaint.setStrokeWidth(context.getResources().getDisplayMetrics().density);
                strokePaint.setColor(Color.BLACK);
                canvas.drawText(subStr, lastOffset, y, strokePaint);
                canvas.drawText(subStr, lastOffset, y, paint);
                float l = paint.measureText(subStr);
                lastOffset += l;
                returnLen += l;
                continue;
            }

            //命中表情，首先把文字画出来
            if (lastIndex < span) {
                String subStr = str.substring(lastIndex, span);

                Paint strokePaint = new Paint(paint);
                strokePaint.setShadowLayer(0, 0, 0, Color.BLACK);
                strokePaint.setStyle(Paint.Style.FILL_AND_STROKE);
                strokePaint.setStrokeWidth(context.getResources().getDisplayMetrics().density);
                strokePaint.setColor(Color.BLACK);
                canvas.drawText(subStr, lastOffset, y, strokePaint);

                canvas.drawText(subStr, lastOffset, y, paint);
                float l = paint.measureText(subStr);
                lastOffset += l;
                returnLen += l;

            }
            //把表情画出来
            SmileItem s = SmileCompare.sSearch.mItem;
            int w = s.getBitmap().getWidth() * height / s.getBitmap().getHeight();
            canvas.drawBitmap(s.mIcon, null, new Rect((int) lastOffset, 0, (int) lastOffset + w, height), paint);
            lastOffset += w;
            returnLen += w;

            span += s.mText.length();
        }
    }

    private static float parseToBitmapInternal(
            Context context,
            Canvas canvas,
            String text,
            float x,
            float y,
            int width,
            int height,
            Paint paint,
            Set<SmileCompare> smileCompares
    ) {
        String str = text;
        str = str.replace("\r", "\n");

        int span = 0;
        char[] data = null;
        float lastOffset = x;
        int lastIndex;
        int len = str.length();
        float returnLen = 0;

        while (true) {
            lastIndex = span;
            span = str.indexOf("/{", span);

            if (span == -1) {
                if (lastIndex < str.length()) {
                    String subStr = str.substring(lastIndex);
                    canvas.drawText(subStr, lastOffset, y, paint);
                    returnLen += paint.measureText(subStr);
                }
                return returnLen;
            }

            if (data == null) {
                data = new char[str.length()];
                str.getChars(0, str.length(), data, 0);
            }

            SmileCompare.sTemp.mKey = data;
            SmileCompare.sTemp.mKeyBase = span + 2;
            SmileCompare.sTemp.mKeyLength = len - span - 2;

            if (!smileCompares.contains(SmileCompare.sTemp)) {
                //没命中表情，画出text
                span += 2;
                String subStr = str.substring(lastIndex, span);
                canvas.drawText(subStr, lastOffset, y, paint);
                float l = paint.measureText(subStr);
                lastOffset += l;
                returnLen += l;
                continue;
            }

            //命中表情，首先把文字画出来
            if (lastIndex < span) {
                String subStr = str.substring(lastIndex, span);
                canvas.drawText(subStr, lastOffset, y, paint);
                float l = paint.measureText(subStr);
                lastOffset += l;
                returnLen += l;
            }
            //把表情画出来
            SmileItem s = SmileCompare.sSearch.mItem;
            int w = s.getBitmap().getWidth() * height / s.getBitmap().getHeight();
            canvas.drawBitmap(s.mIcon, null, new Rect((int) lastOffset, 0, (int) lastOffset + w, height), paint);
            lastOffset += w;
            returnLen += w;

            span += s.mText.length();
        }
    }

    //解析所有的表情
    private void parseSpannableAllNew(
            Context context,
            Spannable spannable,
            int maxWidth,
            int normalWidth,
            Object tag
    ) {
        if (allSmileList.isEmpty()) {
            createAllNew(context);
        }
        String str = String.valueOf(spannable);
        str = str.replace("\r", "\n");

        int span = 0;
        char[] data = null;

        int len = str.length();

        while (true) {
            span = str.indexOf("/{", span);

            if (span == -1) {
                return;
            }

            if (data == null) {
                data = new char[str.length()];
                str.getChars(0, str.length(), data, 0);
            }

            SmileCompare.sTemp.mKey = data;
            SmileCompare.sTemp.mKeyBase = span + 2;
            SmileCompare.sTemp.mKeyLength = len - span - 2;
            if (!allSmileOrderSet.contains(SmileCompare.sTemp)) {
                span += 2;
                continue;
            }

            SmileItem s = SmileCompare.sSearch.mItem;
            Drawable drawable;
            if (normalWidth != Integer.MAX_VALUE && normalWidth > 0 && s.mIcon != null) {
                drawable = new BitmapDrawable(s.mIcon);
                drawable.setBounds(0, 0, normalWidth, normalWidth);
            } else {
                drawable = s.mDrawable;
            }
            CustomImageSpan imageSpan = new CustomImageSpan(drawable);
            setSpannable(imageSpan, spannable, span, span + s.mText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            span += s.mText.length();
        }
    }

    //解析新的表情
    private void parseSpannableNew(Context context, Spannable spannable, int maxWidth, Object tag) {
        if (nSmileList.isEmpty()) {
            createNew(context);
        }
        String str = String.valueOf(spannable);
        str = str.replace("\r", "\n");

        int span = 0;
        char[] data = null;

        int len = str.length();

        while (true) {
            span = str.indexOf("/{", span);

            if (span == -1) {
                return;
            }

            if (data == null) {
                data = new char[str.length()];
                str.getChars(0, str.length(), data, 0);
            }

            SmileCompare.sTemp.mKey = data;
            SmileCompare.sTemp.mKeyBase = span + 2;
            SmileCompare.sTemp.mKeyLength = len - span - 2;

            if (!nSmileOrderSet.contains(SmileCompare.sTemp)) {
                span += 2;
                continue;
            }

            SmileItem s = SmileCompare.sSearch.mItem;

            CustomImageSpan imageSpan = new CustomImageSpan(s.mDrawable);
            setSpannable(imageSpan, spannable, span, span + s.mText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            span += s.mText.length();
        }
    }

    //解析原来的表情
    private void parseSpannableOrg(Context context, Spannable spannable, int maxWidth, Object tag) {
        if (sSmileList.isEmpty()) {
            create(context);
        }
        String str = String.valueOf(spannable);
        str = str.replace("\r", "\n");

        int span = 0;
        char[] data = null;

        int len = str.length();

        while (true) {
            span = str.indexOf("/{", span);

            if (span == -1) {
                return;
            }

            if (data == null) {
                data = new char[str.length()];
                str.getChars(0, str.length(), data, 0);
            }

            SmileCompare.sTemp.mKey = data;
            SmileCompare.sTemp.mKeyBase = span + 2;
            SmileCompare.sTemp.mKeyLength = len - span - 2;

            if (!sSmileOrderSet.contains(SmileCompare.sTemp)) {
                span += 2;
                continue;
            }

            SmileItem s = SmileCompare.sSearch.mItem;
            Drawable drawable;
            if (maxWidth != Integer.MAX_VALUE && maxWidth > 0) {
                Bitmap bitmap = s.mIcon.copy(Bitmap.Config.ARGB_8888, true);
                drawable = new BitmapDrawable(bitmap);
                drawable.setBounds(0, 0, maxWidth, maxWidth);
            } else {
                drawable = s.mDrawable;
            }
            CustomImageSpan imageSpan = new CustomImageSpan(drawable);
            setSpannable(imageSpan, spannable, span, span + s.mText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            span += s.mText.length();
        }
    }

    public static String subSpannableWithLen(String str, int len) {
        if (len <= 0) {
            return str;
        }
        str = str.replace("\r", "\n");
        String tmp = str;
        List<Pair<Integer, String>> list = new ArrayList<Pair<Integer, String>>();
        //找出所有表情符，并记录位置
        for (String target : sSmileCodes) {
            int start = 0;
            int index = tmp.indexOf(target, start);
            while (index != -1) {
                Pair<Integer, String> p = new Pair<Integer, String>(index, target);
                list.add(p);

                if (target.length() > 0) {
                    start = index + target.length();
                    index = tmp.indexOf(target, start);
                } else {
                    break;
                }
            }
        }

        //按索引值由低到高排序
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 1; j < list.size() - i; j++) {
                Pair<Integer, String> pair;
                if ((list.get(j - 1)).first > (list.get(j)).first) {
                    pair = list.get(j - 1);
                    list.set((j - 1), list.get(j));
                    list.set(j, pair);
                }
            }
        }

        return null;
    }

    public static int parseSpannableLengthWithNum(String str, int num) {
        return parseSpannableLengthWithNum(str, num, true);
    }

    public static int parseSpannableLengthWithNum(String str, int num, boolean useOld) {
        if (useOld) {
            try {
                if (sSmileList.isEmpty()) {
                    Context context = BasicConfig.getInstance().getAppContext();
                    if (context != null) {
                        create(context);
                    } else {
                        if (MLog.isLogLevelAboveDebug()) {
                            MLog.debug(
                                    TAG, "EmoticonFilter parseSpannableLength  BasicC" +
                                            "onfig.getInstance().getAppContext() is nulll");
                        }
                        return str.length();
                    }
                }
            } catch (Throwable e) {
                MLog.error(TAG, "EmoticonFilter parseSpannableLength" + e);
                return str.length();
            }
        }

        try {
            if (nSmileList.isEmpty()) {
                Context context = BasicConfig.getInstance().getAppContext();
                if (context != null) {
                    createNew(context);
                } else {
                    if (MLog.isLogLevelAboveDebug()) {
                        MLog.debug(
                                TAG, "EmoticonFilter parseSpannableLength  BasicC" +
                                        "onfig.getInstance().getAppContext() is nulll");
                    }
                    return str.length();
                }
            }
        } catch (Throwable throwable) {
            MLog.error(TAG, "EmoticonFilter parseSpannableLength" + throwable);
            return str.length();
        }

        //int lens = str.length();
        str = str.replace("\r", "\n");
        try {
        /*
        int span = 0;
        char[] data = null;
        int len = str.length();
        while (true) {
            span = str.indexOf("/{", span);
            if (span == -1) {
                break;
            }
            if (data == null) {
                data = new char[str.length()];
                str.getChars(0, str.length(), data, 0);
            }
            SmileCompare.sTemp.mKey = data;
            SmileCompare.sTemp.mKeyBase = span + 2;
            SmileCompare.sTemp.mKeyLength = len - span - 2;
            if(useOld && !sSmileOrderSet.contains(SmileComp
          are.sTemp) && !nSmileOrderSet.contains(SmileCompare.sTemp)) {
                span += 2;
                continue;
            } else if (!nSmileOrderSet.contains(SmileCompare.sTemp)) {
                span += 2;
                continue;
            }

            SmileItem s = SmileCompare.sSearch.mItem;
            span += s.mText.length();
            lens = lens - s.mText.length() + num;//一个表情当做num个字符
        }*/
            //组合新旧表情
            String[] codes = new String[sSmileCodes.length + nSmileCodes.length];
            for (int i = 0; i < nSmileCodes.length; i++) {
                codes[i] = nSmileCodes[i];
            }
            for (int i = nSmileCodes.length; i < sSmileCodes.length + nSmileCodes.length; i++) {
                codes[i] = sSmileCodes[i - nSmileCodes.length];
            }

            if (num > 0) {
                StringBuilder replacement = new StringBuilder();
                for (int j = 0; j < num; j++) {
                    replacement.append("A");
                }
                String strReplacement = replacement.toString();
                for (String target : codes) {
                    if (str.contains(target)) {
                        str = str.replace(target, strReplacement);
                    }
                }
            }
        } catch (Throwable throwable) {
            MLog.error(TAG, "EmoticonFilter parseSpannableLength" + throwable);
            return str.length();
        }
        return str.length();
    }

    /**
     * 将指定长度的字符串取前n个字符返回，余下部分变成省略号，其中：1汉字1个字符，2字母1字符，表情1字符
     */
    public static String briefString(String src, int targetLength) {
        StringBuffer target = new StringBuffer("");
        int length = 0;
        String[] list = src.split("");
        String s;
        for (int i = 0; i < list.length; i++) {
            s = list[i];
            if (s.getBytes().length == 1) {
                target.append(s);
                length++;
            } else if (s.getBytes().length > 1) {
                target.append(s);
                length += 2;
            }
            if (length >= targetLength * 2) {
                if (i == (list.length - 1)) {
                    return target.toString();
                } else {
                    return target.toString() + "...";
                }
            }
        }
        return target.toString();
    }

    public static int parseSpannableLength(String str) {
        return parseSpannableLengthWithNum(str, 1);
    }

    public static int parseSpannableNum(String str) {
        int num = 0;
        try {
            str = str.replace("\r", "\n");
            String tmp = str;
            //找出所有表情符，并记录位置
            for (String target : sSmileCodes) {
                int start = 0;
                int index = tmp.indexOf(target, start);
                while (index != -1) {
                    num++;
                    if (target.length() > 0) {
                        start = index + target.length();
                        index = tmp.indexOf(target, start);
                    } else {
                        break;
                    }
                }
            }
            return num;
        } catch (Throwable throwable) {
            MLog.error(TAG, "EmoticonFilter parseSpannableNum" + throwable);
            return num;
        }
    }

    private static final int MAX_LENGTH = 5;

    public static SmileItem findSmile(String str, int offset) {
        //if (sSmileList.isEmpty()) {
        //YService.asyncRun(new Runnable() {
        //public void run() {
        //create();
        //}
        //});
        //return null;
        //}
        int count = sSmileList.size();
        String t = str.substring(offset, offset + Math.min(MAX_LENGTH, str.length() - offset));
        for (int i = 0; i < count; i++) {

            SmileItem s = sSmileList.get(i);

            if (t.startsWith(s.mText)) {
                return s;
            }
        }

        return null;
    }

    public static SmileItem findSmile(Context context, String smileCode) {
        if (sSmileList.isEmpty()) {
            sSmileList = getSmileList(context);
        }
        int count = sSmileList.size();
        for (int i = 0; i < count; i++) {
            SmileItem s = sSmileList.get(i);
            //if (MLog.isLogLevelAboveDebug()) MLog.debug("smile","code="+s.mText);
            if (smileCode.equals(s.mText)) {
                return s;
            }
        }
        return null;
    }

    public static String replaceEmoticonWithGivenStr(String str, String givenStr) {
        str = str.replace("\r", "\n");
        for (String target : sSmileCodes) {
            str = str.replace(target, givenStr);
        }
        return str;
    }

    public static String replaceEmoticonWithLimited(String str, int num) {
        if (num <= 0) {
            return str;
        }
        str = str.replace("\r", "\n");
        String tmp = str;
        List<Pair<Integer, String>> list = new ArrayList<Pair<Integer, String>>();
        //找出所有表情符，并记录位置
        for (String target : sSmileCodes) {
            int start = 0;
            int index = tmp.indexOf(target, start);
            while (index != -1) {
                Pair<Integer, String> p = new Pair<Integer, String>(index, target);
                list.add(p);

                if (target.length() > 0) {
                    start = index + target.length();
                    index = tmp.indexOf(target, start);
                } else {
                    break;
                }
            }
        }
        //按索引值由低到高排序
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 1; j < list.size() - i; j++) {
                Pair<Integer, String> pair;
                if ((list.get(j - 1)).first > (list.get(j)).first) {
                    pair = list.get(j - 1);
                    list.set((j - 1), list.get(j));
                    list.set(j, pair);
                }
            }
        }
        //截取要显示的部分
        String s = "";
        if (num < list.size()) {
            for (int n = num; n < list.size(); n++) {
                if (n > num) {
                    s += tmp.substring(list.get(n - 1).first + list.get(n - 1).second.length(), list.get(n).first);
                } else {
                    s += tmp.substring(0, list.get(n).first);
                }
            }
            s += tmp.substring(list.get(list.size() - 1).first + list.get(list.size() - 1).second.length());
        }

        if (s != "" && s != null) {
            str = s;
        }
        return str;
    }

    /**
     * 截取限制长度的字符串进行显示，处理边缘的表情
     *
     * @param str
     * @param num
     * @param eachEmoAsNum 每个表情当做几个字符
     * @return
     */
    public static String replaceEmoticonWithLimited2(String str, int num, int eachEmoAsNum) {
        if (num <= 0) {
            return str;
        }
        if (str.length() <= num) {
            return str;
        }
        str = str.replace("\r", "\n");
        String tmp = str;
        List<Pair<Integer, String>> list = new ArrayList<Pair<Integer, String>>();
        //组合新旧表情
        String[] codes = new String[sSmileCodes.length + nSmileCodes.length];
        for (int i = 0; i < sSmileCodes.length; i++) {
            codes[i] = sSmileCodes[i];
        }
        for (int i = sSmileCodes.length; i < sSmileCodes.length + nSmileCodes.length; i++) {
            codes[i] = nSmileCodes[i - sSmileCodes.length];
        }
        //找出所有表情符，并记录位置
        for (String target : codes) {
            int start = 0;
            int index = tmp.indexOf(target, start);
            while (index != -1) {
                Pair<Integer, String> p = new Pair<Integer, String>(index, target);
                list.add(p);

                if (target.length() > 0) {
                    start = index + target.length();
                    index = tmp.indexOf(target, start);
                } else {
                    break;
                }
            }
        }
        //按索引值由低到高排序
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 1; j < list.size() - i; j++) {
                Pair<Integer, String> pair;
                if ((list.get(j - 1)).first > (list.get(j)).first) {
                    pair = list.get(j - 1);
                    list.set((j - 1), list.get(j));
                    list.set(j, pair);
                }
            }
        }

        Map<Integer, Pair<Integer, String>> emoticons = new HashMap<Integer, Pair<Integer, String>>();
        for (int i = 0; i < list.size(); i++) {
            Pair<Integer, String> emoticon = list.get(i);
            emoticons.put(emoticon.first, emoticon);
        }

        //截取要显示的部分
        String s;
        int sumLen = 0;
        int curIndex = 0;
        for (int i = 0; i < tmp.length(); i++) {
            if (emoticons.containsKey(i)) {
                if (sumLen + eachEmoAsNum <= num) { //一个表情当做一个字符
                    sumLen += eachEmoAsNum;
                    i = i + emoticons.get(i).second.length() - 1; //因为循环要++,所以减一
                    curIndex = i;
                    continue;
                } else {
                    break;
                }
            } else {
                if (sumLen + 1 <= num) {
                    sumLen++;
                    curIndex = i;
                } else {
                    break;
                }
            }
        }
        s = tmp.substring(0, curIndex + 1);

        if (s != null && s != "") {
            str = s;
        }
        return str;
    }

}
