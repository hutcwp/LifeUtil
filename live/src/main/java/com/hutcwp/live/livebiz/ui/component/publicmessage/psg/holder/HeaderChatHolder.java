package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.holder;

import android.view.View;
import android.widget.TextView;

import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.BaseChatViewHolder;
import com.hutcwp.livebiz.R;

/**
 * 头部信息
 * @author RyanLee
 */
public class HeaderChatHolder extends BaseChatViewHolder {

    public HeaderChatHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Object obj, int position) {
        TextView tips = (TextView) getView(R.id.tv_header_text_msg);
        tips.setText(tips.getContext().getResources().getString(R.string.test_healthy_live));
    }
}
