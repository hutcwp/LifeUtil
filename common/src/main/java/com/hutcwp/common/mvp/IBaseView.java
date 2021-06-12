package com.hutcwp.common.mvp;


/**
 * The interface Base view.
 * 基础视图的接口
 */
public interface IBaseView {
    /**
     * 显示对话框
     * show Dialog
     */
    void onShowDialog();

    /**
     * 消失对话框
     * Disappear dialog box
     */
    void onDismissDialog();

    /**
     * 错误返回
     * Error return
     * @param error 错误类型
     */
    void onError(BizError error);
}
