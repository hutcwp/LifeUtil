package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.holder;

import android.view.View;
import android.widget.TextView;

import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.BaseChatViewHolder;
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.MyChatMsg;
import com.hutcwp.livebiz.R;

/**
 * 系统消息
 * @author RyanLee
 */
public class SystemNewsHolder extends BaseChatViewHolder {
    public SystemNewsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Object obj, int position) {
        MyChatMsg data = (MyChatMsg) obj;
        TextView tips = (TextView) getView(R.id.tv_system_news_msg);
        tips.setText(data.systemNews);
    }
}
