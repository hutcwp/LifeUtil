package hut.cwp.core;

import androidx.annotation.NonNull;


public interface MvpInnerDelegateCallback<P extends MvpPresenter<V>, V extends MvpView> {

    @NonNull
    P createPresenter();

    void setPresenter(@NonNull P presenter);

    @NonNull
    P getPresenter();

    V getMvpView();

    @NonNull
    MvpInnerDelegate<P, V> getMvpDelegate();
}
