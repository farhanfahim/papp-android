package com.tekrevol.papp.libraries.table.model;


import com.tekrevol.papp.libraries.table.model.action.Action;
import com.tekrevol.papp.libraries.table.model.action.Action;

public interface ITextRun {
    int getStartPos();
    int getLength();
    int getFontIndex();
    int getBackgroundColor();
    Action getAction();
}
