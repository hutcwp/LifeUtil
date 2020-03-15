package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.mutiltype;

import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.BaseChatMsg;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;


/**
 * Created by hutcwp on 2020-03-13 18:13
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public abstract class BaseChatAdapter2<D extends BaseChatMsg> extends MultiTypeAdapter {

    public abstract void addItem(D chatMsg);

    public abstract void addItemList(List<D> list);

    public abstract void removeItems(int startPos, int endPos);
}
