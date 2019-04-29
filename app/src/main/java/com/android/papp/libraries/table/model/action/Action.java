package com.android.papp.libraries.table.model.action;

import com.android.papp.libraries.table.model.ICellData;

public interface Action {
    boolean onAction(ICellData cell);
}
