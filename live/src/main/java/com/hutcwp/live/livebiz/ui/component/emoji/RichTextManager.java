package com.hutcwp.live.livebiz.ui.component.emoji;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;

import com.hutcwp.live.livebiz.ui.component.emoji.filter.BaseRichTextFilter;
import com.hutcwp.live.livebiz.ui.component.emoji.filter.EmoticonFilter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjinwen on 2014/6/19.
 */
public class RichTextManager {

    public static enum Feature {
        EMOTICON(0),
        CHANNELAIRTICKET(1),
        GROUPTICKET(2),
        IMAGE(3),
        VOICE(4),
        VIPEMOTICON(5),
        NUMBER(6),
        NOBLEEMOTION(7),
        NOBLEGIFEMOTION(8);

        private int value;

        private Feature(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    private Map<Feature, BaseRichTextFilter> filterMap;
    private static RichTextManager instance;

    public static synchronized RichTextManager getInstance() {
        if (instance == null) {
            instance = new RichTextManager();
        }
        return instance;
    }

    public RichTextManager() {
        filterMap = new HashMap<Feature, BaseRichTextFilter>();
//        filterMap.put(Feature.IMAGE, new ImageFilter());
//        filterMap.put(Feature.CHANNELAIRTICKET, new ChannelTicketFilter(R.drawable.feijipiao_bg));
//        filterMap.put(Feature.GROUPTICKET, new YGroupTicketFilter(R.drawable.feijipiao_bg));
        filterMap.put(Feature.EMOTICON, new EmoticonFilter());
        //filterMap.put(Feature.VIPEMOTICON, new VipEmoticonFilter());
//        filterMap.put(Feature.VOICE, new ImVoiceFilter());
    }

    public void addFilterFeature(BaseRichTextFilter filter) {
        filterMap.put(Feature.NOBLEEMOTION, filter);
    }

    public BaseRichTextFilter getFilterFeature(Feature feature) {
        return filterMap.get(feature);
    }

    public void addGifFilterFeature(BaseRichTextFilter filter) {
        filterMap.put(Feature.NOBLEGIFEMOTION, filter);
    }

    public void removeGifFilterFeature() {
        filterMap.remove(Feature.NOBLEGIFEMOTION);
    }

    public Spannable getSpannableString(Context context, CharSequence charSequence, List<Feature> featureList) {
        Spannable spannable;
        if (charSequence instanceof Spannable) {
            spannable = (Spannable) charSequence;
        } else {
            spannable = new SpannableString(charSequence);
        }
        for (Feature feature : featureList) {
            BaseRichTextFilter filter = filterMap.get(feature);
            if (filter != null) {
                filter.parseSpannable(context, spannable, Integer.MAX_VALUE);
            }
        }
        return spannable;
    }

    /**
     * 增加表情与TextView的文字大小适配(仅表情可用)
     *
     * @param context
     * @param charSequence
     * @param featureList
     * @param normalWidth  期望得到的大小 默认为 Integer.MAX_VALUE 即图片原始大小
     * @return
     */
    public Spannable getSpannableString(
            Context context, CharSequence charSequence, List<Feature> featureList, int normalWidth) {
        Spannable spannable;
        if (charSequence instanceof Spannable) {
            spannable = (Spannable) charSequence;
        } else {
            spannable = new SpannableString(charSequence);
        }
        for (Feature feature : featureList) {
            BaseRichTextFilter filter = filterMap.get(feature);
            if (filter != null) {
                filter.parseSpannable(
                        context, spannable, normalWidth > 0 ? normalWidth : Integer.MAX_VALUE, normalWidth);
            }
        }
        return spannable;
    }

    public void filterAll(Context context, CharSequence charSequence, int maxWidth) {
        filterAll(context, charSequence, maxWidth, null);
    }

    public void filterAll(Context context, CharSequence charSequence, int maxWidth, Object tag) {
        Spannable spannable;
        if (charSequence instanceof Spannable) {
            spannable = (Spannable) charSequence;
        } else {
            spannable = new SpannableString(charSequence);
        }
        Iterator iterator = filterMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            BaseRichTextFilter filter = (BaseRichTextFilter) entry.getValue();
            if (maxWidth <= 0) {
                maxWidth = Integer.MAX_VALUE;
            }
            if (tag == null) {
                filter.parseSpannable(context, spannable, maxWidth);
            } else {
                filter.parseSpannable(context, spannable, maxWidth, tag);
            }
        }
    }

    public void setSpanClickListener(Feature feature, BaseRichTextFilter.OnSpanClickListener listener) {
        setSpanClickListener(feature, listener, "");
    }

    public void setSpanClickListener(
            Feature feature, BaseRichTextFilter.OnSpanClickListener listener, String currentContext) {
        BaseRichTextFilter filter = filterMap.get(feature);
        if (filter != null) {
            filter.setSpanClickListener(listener, currentContext);
        }
    }

    public void clearSpanClickListener(Feature feature) {
        clearSpanClickListener(feature, "");
    }

    public void clearSpanClickListener(Feature feature, String currentContext) {
        BaseRichTextFilter filter = filterMap.get(feature);
        if (filter != null) {
            filter.clearSpanClickListener(currentContext);
        }
    }

}
