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





    public static ArrayList<SpinnerModel> getGenderArray() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();
        arrayList.add(new SpinnerModel("Male"));
        arrayList.add(new SpinnerModel("Female"));
        arrayList.add(new SpinnerModel("Unknown"));
        return arrayList;

    }






    public static ArrayList<SpinnerModel> getAgeNumbersArray() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();


        for (int i = 3; i < 120; i++) {
            arrayList.add(new SpinnerModel(i+ " years"));
        }

        return arrayList;

    }
}
