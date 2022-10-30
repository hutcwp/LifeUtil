package com.hutcwp.live.livebiz.ui.component.publicmessage;


import com.example.presenter.core.MvpPresenter;

public class PublicMessagePresenter extends MvpPresenter<IPublicMessageComponent> {

    private PublicMessageComponent component;

    public PublicMessagePresenter() {
    }

    @Override
    protected void attachView(IPublicMessageComponent view) {
        super.attachView(view);
        initPresenter();
    }

    private void initPresenter() {
        component = (PublicMessageComponent) getView();
    }


    public void performTest() {
        component.showTest();
    }

    public void performLog() {
        component.showLog();
    }

}
