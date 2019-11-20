package com.leifu.commonlib.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.leifu.commonlib.R;

public class DialogConfirmCancel extends Dialog implements View.OnClickListener {


    private int cancelColor = -1;
    private int sureColor = -1;
    private String title;
    private String cancel;
    private String sure;
    TextView mTitle;
    TextView mBtnCancel;
    TextView mBtnConfirm;
    private onDialogListener listener;
    private onDialogConfirmListener listenerConfirm;

    public DialogConfirmCancel(Context context, String title) {
        super(context, R.style.DialogConfirmCancelStyle);
        this.title = title;
    }

    public DialogConfirmCancel(Context context, String title, String cancel, String sure) {
        super(context, R.style.DialogConfirmCancelStyle);
        this.title = title;
        this.cancel = cancel;
        this.sure = sure;
    }

    public DialogConfirmCancel(Context context, String title, String cancel, int cancelColor, String sure, int sureColor) {
        super(context, R.style.DialogConfirmCancelStyle);
        this.title = title;
        this.cancel = cancel;
        this.sure = sure;
        this.cancelColor = cancelColor;
        this.sureColor = sureColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sure_cancel);
        mTitle = findViewById(R.id.title);
        mBtnCancel = findViewById(R.id.btnCancel);
        mBtnConfirm = findViewById(R.id.btnConfirm);
        mBtnCancel.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
        setCanceledOnTouchOutside(false);
//       设置黑暗度
        Window mWindow = getWindow();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.dimAmount = 0.3f;
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mWindow.setAttributes(params);
        if (title != null) {
            mTitle.setText(title);
        }
        if (cancel != null) {
            mBtnCancel.setText(cancel);
        }
        if (cancelColor != -1) {
            mBtnCancel.setTextColor(cancelColor);
        }
        if (sure != null) {
            mBtnConfirm.setText(sure);
        }
        if (sureColor != -1) {
            mBtnConfirm.setTextColor(sureColor);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btnCancel) {
            dismiss();
            if (listener != null) {
                listener.onCancelClick();
            }
        } else if (i == R.id.btnConfirm) {
            dismiss();
            if (listener != null) {
                listener.onConfirmClick();
            }
            if (listenerConfirm != null) {
                listenerConfirm.onConfirmClick();
            }
        }
    }

    public interface onDialogListener {
        void onCancelClick();

        void onConfirmClick();
    }

    public interface onDialogConfirmListener {
        void onConfirmClick();
    }

    public void setOnDialogListener(onDialogListener listener) {
        this.listener = listener;
    }

    public void setOnDialogConfirmListener(onDialogConfirmListener listener) {
        this.listenerConfirm = listener;
    }

}
