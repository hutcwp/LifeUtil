package com.hutcwp.mlive;

import android.Manifest;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yanzhenjie.permission.AndPermission;

import me.hutcwp.constant.Constants;
import me.hutcwp.util.SingleToastUtil;


@Route(path = Constants.RoutePath.MLIVE_CAMERA2)
public class Camera2Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mTakePictureBtn;
    private Camera2Manager manager;

    private TextureView mTextureView;
    private TextureView mTextureView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        manager = new Camera2Manager(this);
        initViews();
        init();
    }

    private void startPreview() {
        mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                manager.setSurfaceTexture(surface);

                mTextureView1.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                    @Override
                    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                        manager.setSurfaceTexture1(surface);
                        manager.startPreview();
                    }

                    @Override
                    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                    }

                    @Override
                    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                        return false;
                    }

                    @Override
                    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                    }
                });
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        });

        //manager.startPreview();
    }

    @Override
    public void onResume() {
        super.onResume();
        startPreview();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.stopPreview();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.camera_take_picture) {
        }
    }

    private void initViews() {
        mTakePictureBtn = findViewById(R.id.camera_take_picture);
        mTakePictureBtn.setOnClickListener(this);

        mTextureView = findViewById(R.id.texture_view);
        mTextureView1 = findViewById(R.id.texture_view1);
    }

    private void init() {
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                    startPreview();

                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    SingleToastUtil.showToast("权限没授权");
                })
                .start();
    }
}
