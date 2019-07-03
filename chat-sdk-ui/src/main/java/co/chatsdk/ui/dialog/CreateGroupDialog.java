package co.chatsdk.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import co.chatsdk.ui.R;
import co.chatsdk.ui.utils.ToastHelper;

public class CreateGroupDialog extends Dialog implements View.OnClickListener {

    private final String defaultText;
    public TextView yes, no, tvMessage;
    public DialogCallbacks dialogCallbacks;
    public EditText editName;
    private String message;
    private boolean forUpdateGroupName = false;

    public CreateGroupDialog(Context a, DialogCallbacks dialogCallbacks, String message, boolean forUpdateGroupName, String defaultText) {
        super(a, android.R.style.ThemeOverlay_Material_Dark);
        this.dialogCallbacks = dialogCallbacks;
        this.message = message;
        this.forUpdateGroupName = forUpdateGroupName;
        this.defaultText = defaultText;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fullScreenCode();
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_new_group);
        yes = (TextView) findViewById(R.id.btn_create);
        no = (TextView) findViewById(R.id.btn_cancel);
        editName = (EditText) findViewById(R.id.edit_cat_name);
        tvMessage = (TextView) findViewById(R.id.tv_message);

        if(forUpdateGroupName) {
            tvMessage.setText(getContext().getString(R.string.edit_group_name));
            yes.setText(getContext().getString(R.string.update));
            editName.setText(defaultText);
            editName.setSelection(editName.getText().length());
        }
//        tvMessage.setText(message);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    protected void fullScreenCode() {

        Window window = this.getWindow();
        window.setDimAmount(0.1f);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;

        window.setAttributes(wlp);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_create) {
            if(editName.getText().toString().length() > 0) {
                dialogCallbacks.didTappedOnPositive(editName.getText().toString());
                dismiss();
            } else {
                ToastHelper.show(getContext(), getContext().getString(R.string.error_enter_group_name));
            }
        } else if (i == R.id.btn_cancel) {
            dialogCallbacks.didTappedOnNegative();
            dismiss();
       }
    }


}