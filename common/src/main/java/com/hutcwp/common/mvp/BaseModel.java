package com.hutcwp.common.mvp;

//import com..model.net.NvsServerClient;

/**
 *
 * date   : 2020/8/14
 * desc   :
 * 模型基类
 * Model base class
 */
public abstract class BaseModel implements IModel {

    public BaseModel(){}
    @Override
    public void onClear() {
//        NvsServerClient.getInstance().cancelRequest(this);
    }
}
