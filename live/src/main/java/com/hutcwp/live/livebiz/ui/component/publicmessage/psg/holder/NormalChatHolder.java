package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.holder;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.BaseChatViewHolder;
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.MyChatMsg;
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util.BitmapUtils;
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util.CenteredImageSpan;
import com.hutcwp.livebiz.R;

import androidx.core.content.ContextCompat;
import me.hutcwp.BasicConfig;
import me.hutcwp.util.ResolutionUtils;

/**
 * 普通消息
 *
 * @author RyanLee
 */
public class NormalChatHolder extends BaseChatViewHolder {

    public NormalChatHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Object obj, int position) {
        MyChatMsg data = (MyChatMsg) obj;
        TextView text = (TextView) getView(R.id.tv_normal_text_msg);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (data.headLight > 0) {
            // 设置头灯
            int resId;
            switch (data.headLight) {
                case MyChatMsg.HEAD_LIGHT_VIP:
                    resId = R.drawable.ic_vip;
                    break;
                case MyChatMsg.HEAD_LIGHT_DIAMOND:
                    resId = R.drawable.ic_diamond;
                    break;
                default:
                    return;
            }
            builder.append(" ");
            int imageNewSize = (int) ResolutionUtils.convertDpToPixel(25, BasicConfig.getApplicationContext());
            CenteredImageSpan vipSpan = new CenteredImageSpan(BasicConfig.getApplicationContext(), BitmapUtils.decodeResToBitmap(resId, imageNewSize, imageNewSize));
            builder.setSpan(vipSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        builder.append(data.sendUserName).append(BasicConfig.getApplicationContext().getResources().getString(R.string.str_to));

        // 用户名的长度
        int leftLen = builder.length();
        if (!TextUtils.isEmpty(data.atUserName)) {
            builder.append(BasicConfig.getApplicationContext().getResources().getString(R.string.str_at)).append(data.atUserName).append(" ");
        }
        // 用户名到@的长度
        int nLeftLen = builder.length();
        builder.append(data.content);
        //设置@用户名的颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(BasicConfig.getApplicationContext(), R.color.color_chat_at_username));
        builder.setSpan(colorSpan, leftLen, nLeftLen, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置用户名颜色
        colorSpan = new ForegroundColorSpan(ContextCompat.getColor(BasicConfig.getApplicationContext(), R.color.color_chat_username));
        builder.setSpan(colorSpan, 1, data.sendUserName.length() + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);


        text.setText(builder);
    }
}
