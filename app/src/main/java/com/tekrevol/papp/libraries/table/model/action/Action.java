package com.tekrevol.papp.libraries.table.model.action;

import com.tekrevol.papp.libraries.table.model.ICellData;

public interface Action {
    boolean onAction(ICellData cell);
}
