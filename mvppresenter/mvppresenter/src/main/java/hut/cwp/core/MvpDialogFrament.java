package hut.cwp.core;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * MvpDialogFrament
 */
public class MvpDialogFrament<P extends MvpPresenter<V>, V extends MvpView> extends DialogFragment
        implements MvpInnerDelegateCallback<P, V>, MvpView {
    protected P mPresenter;
    private MvpInnerDelegate<P, V> mMvpInnerDelegate;


    @Override
    public P createPresenter() {
        if (mPresenter == null) {
            mPresenter = getMvpDelegate().createPresenter();
        }
        return mPresenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }

    @NonNull
    @Override
    public MvpInnerDelegate<P, V> getMvpDelegate() {
        if (mMvpInnerDelegate == null) {
            mMvpInnerDelegate = new MvpInnerDelegate<>(this);
        }
        return mMvpInnerDelegate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
        getMvpDelegate().attach(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
        getMvpDelegate().detach();
    }
}
