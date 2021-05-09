package hut.cwp.api.mvp;


public interface PresenterBinder<P, V> {

    /**
     * 根据泛型T（xxx$$PresenterBinder）类生成相应的P(代理类)，进行绑定
     * @param v
     * @return
     */
    P bindPresenter(V v);

    /**
     *取消绑定，在activity, fragment的destroy回调中取消绑定（数据绑定），避免内存泄漏
     */
    void unbindPresenter();
}
