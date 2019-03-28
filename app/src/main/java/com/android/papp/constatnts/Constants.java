package com.android.papp.constatnts;

import com.android.papp.models.SpinnerModel;

import java.util.ArrayList;

public class Constants {



    public static ArrayList<SpinnerModel> getAddDependentsArray() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();
        arrayList.add(new SpinnerModel("Jason Brody"));
        arrayList.add(new SpinnerModel("Tom Johns"));
        return arrayList;

    }
}
