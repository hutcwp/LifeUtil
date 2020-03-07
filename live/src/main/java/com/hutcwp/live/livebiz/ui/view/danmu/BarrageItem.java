package com.hutcwp.live.livebiz.ui.view.danmu;

import android.content.Context;

import androidx.appcompat.widget.AppCompatTextView;

public class BarrageItem extends AppCompatTextView {

    public BarrageItem(Context context, Barrage barrage) {
        super(context);
        initParams(barrage);
    }

    private void initParams(Barrage barrage) {
        this.setText(barrage.getContent());
        this.setTextSize(barrage.getTextSize());
        this.setTextColor(barrage.getColor());
    }

}
