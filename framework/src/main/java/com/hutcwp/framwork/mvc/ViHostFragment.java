//package com.hutcwp.framwork.mvc;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import androidx.annotation.Nullable;
//import androidx.databinding.DataBindingUtil;
//import androidx.databinding.ViewDataBinding;
//import androidx.fragment.app.Fragment;
//
///**
// * Description:
// * <p>
// * Created by n24314 on 2022/10/21. E-mail: caiwenpeng@corp.netease.com
// */
//public abstract class ViHostFragment<V extends ViewDataBinding> extends Fragment implements LayoutProvider {
//
//    /**
//     * View 创建之后就不会为空
//     */
//    public V binding;
//
//
//
//    @Nullable
//    @Override
//    public View onCreateView(
//        LayoutInflater inflater,
//        @Nullable ViewGroup container,
//        @Nullable Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false, null);
//        return binding.getRoot();
//    }
//
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        binding.setLifecycleOwner(this);
////        dispatcher.dispatchViewCreated();
//    }
//
//
//    public V getBinding() {
//        return binding;
//    }
//
//}
