package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.holder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.BaseChatViewHolder;
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.MyChatMsg;
import com.hutcwp.livebiz.R;

import androidx.core.content.ContextCompat;
import me.hutcwp.BasicConfig;
import me.hutcwp.util.ResolutionUtils;


/**
 * @author RyanLee
 */
public class ActivityNewsHolder extends BaseChatViewHolder {

    public ActivityNewsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Object obj, int position) {
        MyChatMsg data = (MyChatMsg) obj;
        TextView textView = (TextView) getView(R.id.tv_activity_news);
        Drawable drawableLeft = ContextCompat.getDrawable(textView.getContext(), R.drawable.ic_activity);
        Drawable drawableRight = ContextCompat.getDrawable(textView.getContext(), R.drawable.ic_arrow);

        int int18 = (int) ResolutionUtils.convertDpToPixel(18f, BasicConfig.getApplicationContext());
        int dp12 = (int) ResolutionUtils.convertDpToPixel(12f, BasicConfig.getApplicationContext());
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, int18, int18);

        }
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, dp12, dp12);
        }
        textView.setCompoundDrawables(drawableLeft, null, drawableRight, null);
        textView.setText(data.activityNews);
    }
}
