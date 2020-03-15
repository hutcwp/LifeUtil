package com.hutcwp.live.livebiz.ui.component.publicmessage.lib;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hutcwp on 2020-03-10 11:22
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public abstract class BaseChatAdapter<D extends BaseChatMsg> extends RecyclerView.Adapter<BaseChatViewHolder> {

    public abstract void addItem(D chatMsg);

    public abstract void addItemList(List<D> list);

    public abstract void removeItems(int startPos, int endPos);
}

