package hut.cwp.core;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

/**
 * A Activity that uses an {@link MvpPresenter} to implement a Model-View-Presenter
 */

public class MvpActivity<P extends MvpPresenter<V>, V extends MvpView> extends FragmentActivity
    implements MvpInnerDelegateCallback<P, V>, MvpView{

    protected P mPresenter;
    private MvpInnerDelegate<P, V> mMvpInnerDelegate;

    public P createPresenter(){
        if(mPresenter == null){
            mPresenter = getMvpDelegate().createPresenter();
        }
        return mPresenter;
    }

    @NonNull
    public P getPresenter(){
        return mPresenter;
    }

    public void setPresenter(@NonNull P presenter){
        mPresenter = presenter;
    }

    @NonNull
    public V getMvpView() {
        return (V)this;
    }

    @Override
    public MvpInnerDelegate<P, V> getMvpDelegate() {
        if(mMvpInnerDelegate == null){
            mMvpInnerDelegate = new MvpInnerDelegate<>(this);
        }
        return mMvpInnerDelegate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
        getMvpDelegate().attach(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
        getMvpDelegate().detach();
    }
}
