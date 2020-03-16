package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util;

import android.content.res.TypedArray;

import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.MyChatMsg;
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.ActivityNewsMsg;
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.GiftMsg;
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.HeaderChatMsg;
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.NormalMsg;
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.SystemNewMsg;
import com.hutcwp.livebiz.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestUtils {
    private static int mRandomGift;

    private static int getRandomType() {
        int maxType = 4;
        int randomType = new Random().nextInt(maxType);

        float systemNewsProbability = 0.7f;
        float giftNewsProbability = 0.8f;
        float activityNewsProbability = 0.4f;

        double randomProbability = Math.random();
        switch (randomType) {
            case MyChatMsg.TYPE_SYSTEM_NEWS:
                if (randomProbability >= systemNewsProbability) {
                    randomType = MyChatMsg.TYPE_NORMAL_TEXT;
                }
                break;
            case MyChatMsg.TYPE_GIFT_MSG:
                if (randomProbability >= giftNewsProbability) {
                    randomType = MyChatMsg.TYPE_NORMAL_TEXT;
                }
                break;
            case MyChatMsg.TYPE_ACTIVITY_NEWS:
                if (randomProbability >= activityNewsProbability) {
                    randomType = MyChatMsg.TYPE_NORMAL_TEXT;
                }
                break;
            default:
                randomType = MyChatMsg.TYPE_NORMAL_TEXT;
                break;
        }
        return randomType;
    }

    public static MyChatMsg getRandomMsg() {
        MyChatMsg msg = new MyChatMsg();
        msg.type = getRandomType();
        msg.headLight = getRandomHeadLight();
        msg.content = getRandomContent();
        msg.systemNews = getRandomSystemNews();
        msg.activityNews = getRandomActivityNews();
        msg.sendUserName = getRandomUserName();
        msg.atUserName = getRandomAtUserName();
        msg.giftName = getRandomGiftName();
        msg.giftRes = getRandomGiftRes();
        msg.giftNum = getRandomGiftNum();
        return msg;
    }


    public static GiftMsg getGiftMsg() {
        GiftMsg msg = new GiftMsg();
        msg.type = getRandomType();
        msg.headLight = getRandomHeadLight();
        msg.content = getRandomContent();
        msg.systemNews = getRandomSystemNews();
        msg.activityNews = getRandomActivityNews();
        msg.sendUserName = getRandomUserName();
        msg.atUserName = getRandomAtUserName();
        msg.giftName = getRandomGiftName();
        msg.giftRes = getRandomGiftRes();
        msg.giftNum = getRandomGiftNum();
        return msg;
    }

    public static HeaderChatMsg getHeaderChatMsg() {
        HeaderChatMsg msg = new HeaderChatMsg();
        msg.type = getRandomType();
        msg.headLight = getRandomHeadLight();
        msg.content = getRandomContent();
        msg.systemNews = getRandomSystemNews();
        msg.activityNews = getRandomActivityNews();
        msg.sendUserName = getRandomUserName();
        msg.atUserName = getRandomAtUserName();
        msg.giftName = getRandomGiftName();
        msg.giftRes = getRandomGiftRes();
        msg.giftNum = getRandomGiftNum();
        return msg;
    }

    public static NormalMsg getNormalMsg() {
        NormalMsg msg = new NormalMsg();
        msg.type = getRandomType();
        msg.headLight = getRandomHeadLight();
        msg.content = getRandomContent();
        msg.systemNews = getRandomSystemNews();
        msg.activityNews = getRandomActivityNews();
        msg.sendUserName = getRandomUserName();
        msg.atUserName = getRandomAtUserName();
        msg.giftName = getRandomGiftName();
        msg.giftRes = getRandomGiftRes();
        msg.giftNum = getRandomGiftNum();
        return msg;
    }

    public static SystemNewMsg getSystemNewMsg() {
        SystemNewMsg msg = new SystemNewMsg();
        msg.type = getRandomType();
        msg.headLight = getRandomHeadLight();
        msg.content = getRandomContent();
        msg.systemNews = getRandomSystemNews();
        msg.activityNews = getRandomActivityNews();
        msg.sendUserName = getRandomUserName();
        msg.atUserName = getRandomAtUserName();
        msg.giftName = getRandomGiftName();
        msg.giftRes = getRandomGiftRes();
        msg.giftNum = getRandomGiftNum();
        return msg;
    }

    public static ActivityNewsMsg getActivityMsg() {
        ActivityNewsMsg msg = new ActivityNewsMsg();
        msg.type = getRandomType();
        msg.headLight = getRandomHeadLight();
        msg.content = getRandomContent();
        msg.systemNews = getRandomSystemNews();
        msg.activityNews = getRandomActivityNews();
        msg.sendUserName = getRandomUserName();
        msg.atUserName = getRandomAtUserName();
        msg.giftName = getRandomGiftName();
        msg.giftRes = getRandomGiftRes();
        msg.giftNum = getRandomGiftNum();
        return msg;
    }

    public static List<MyChatMsg> getRandomMsgList(int size) {
        List<MyChatMsg> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(getRandomMsg());
        }
        return list;
    }

    private static int getRandomGiftRes() {
        TypedArray ar = me.hutcwp.BasicConfig.getApplicationContext().getResources().obtainTypedArray(R.array.test_giftid);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        return resIds[mRandomGift];
    }

    private static String getRandomContent() {
        String[] array = me.hutcwp.BasicConfig.getApplicationContext().getResources().getStringArray(R.array.test_content);
        return array[new Random().nextInt(array.length)];
    }

    private static String getRandomUserName() {
        String[] array = me.hutcwp.BasicConfig.getApplicationContext().getResources().getStringArray(R.array.test_usernames);
        return array[new Random().nextInt(array.length)];
    }

    private static String getRandomAtUserName() {
        float atUserProbability = 0.35f;
        double randomProbability = Math.random();
        if (randomProbability <= atUserProbability) {
            String[] array = me.hutcwp.BasicConfig.getApplicationContext().getResources().getStringArray(R.array.test_atusernames);
            return array[new Random().nextInt(array.length)];
        }
        return null;
    }

    private static String getRandomGiftName() {
        String[] array = me.hutcwp.BasicConfig.getApplicationContext().getResources().getStringArray(R.array.test_giftname);
        mRandomGift = new Random().nextInt(array.length);
        return array[mRandomGift];
    }


    private static String getRandomGiftNum() {
        String[] array = me.hutcwp.BasicConfig.getApplicationContext().getResources().getStringArray(R.array.test_giftnum);
        return array[new Random().nextInt(array.length)];
    }

    private static String getRandomSystemNews() {
        String[] array = me.hutcwp.BasicConfig.getApplicationContext().getResources().getStringArray(R.array.test_system_news);
        return array[new Random().nextInt(array.length)];
    }

    private static int getRandomHeadLight() {
        return new Random().nextInt(3);
    }

    private static String getRandomActivityNews() {
        String[] array = me.hutcwp.BasicConfig.getApplicationContext().getResources().getStringArray(R.array.test_activity_news);
        return array[new Random().nextInt(array.length)];

    }
}
