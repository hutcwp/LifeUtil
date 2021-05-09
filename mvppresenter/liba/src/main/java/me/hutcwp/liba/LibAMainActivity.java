package me.hutcwp.liba;


import android.os.Bundle;

import hut.cwp.annotations.InitAttrConfig;
import hut.cwp.annotations.InitAttrConfigs;
import hut.cwp.core.MvpActivity;

@InitAttrConfigs({
        @InitAttrConfig(component = TestFragment.class, resourceId = R2.id.fragment_lib_content)
})
public class LibAMainActivity  extends MvpActivity<MainPresenter, IMain> implements IMain {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_amain);
    }

    @Override
    public void changeText() {

    }
}
