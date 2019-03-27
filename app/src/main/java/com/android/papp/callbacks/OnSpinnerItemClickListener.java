package com.android.papp.callbacks;

import com.android.papp.adapters.SpinnerDialogAdapter;

public interface OnSpinnerItemClickListener {
    void onItemClick(int position, Object object, SpinnerDialogAdapter adapter);
}
