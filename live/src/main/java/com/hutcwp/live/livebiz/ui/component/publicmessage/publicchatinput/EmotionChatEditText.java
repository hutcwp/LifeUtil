package com.hutcwp.live.livebiz.ui.component.publicmessage.publicchatinput;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;


@SuppressLint("AppCompatCustomView")
public class EmotionChatEditText extends EditText {

    private OnSendEnableListener listener;
    private TextWatcher textChangedListener;

    public EmotionChatEditText(Context context) {
        super(context);
        init(context);
    }

    public EmotionChatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmotionChatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmotionChatEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        addTextChangedListener(getTextChangedListener());
    }

    private TextWatcher getTextChangedListener() {
        if (null != textChangedListener) {
            return textChangedListener;
        }
        textChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String msg = editable.toString().trim();
                if (listener != null) {
                    if (TextUtils.isEmpty(msg)) {
                        listener.onSendEnable(false);
                    } else {
                        listener.onSendEnable(true);
                    }
                }
            }
        };
        return textChangedListener;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (text != null) {
            setSelection(getText().length());
        }
    }

    public void setOnSendEnableListener(OnSendEnableListener listener) {
        this.listener = listener;
    }

    public interface OnSendEnableListener {
        void onSendEnable(boolean enable);
    }
}
