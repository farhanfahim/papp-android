package com.android.papp.libraries.kmpautotextview;

import com.android.papp.models.SpinnerModel;

import java.io.Serializable;

public class PopupTextBean implements Serializable {
    public SpinnerModel mTarget;
    public int mStartIndex = -1;
    public int mEndIndex = -1;

    public PopupTextBean(SpinnerModel target) {
        this.mTarget = target;
    }

    public PopupTextBean(SpinnerModel target, int startIndex) {
        this.mTarget = target;
        this.mStartIndex = startIndex;
        if (-1 != startIndex) {
            this.mEndIndex = startIndex + target.getText().length();
        }
    }

    public PopupTextBean(SpinnerModel target, int startIndex, int endIndex) {
        this.mTarget = target;
        this.mStartIndex = startIndex;
        this.mEndIndex = endIndex;
    }
}