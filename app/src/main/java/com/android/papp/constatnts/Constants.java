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


    public static ArrayList<SpinnerModel> getAddDependentsArray2() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();
        arrayList.add(new SpinnerModel("Joe Robert"));
        arrayList.add(new SpinnerModel("Chan Lee"));
        arrayList.add(new SpinnerModel("Jason Brody"));
        arrayList.add(new SpinnerModel("Tom Johns"));
        return arrayList;

    }


    public static ArrayList<SpinnerModel> getTopLEA() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();
        arrayList.add(new SpinnerModel("Alexandra"));
        arrayList.add(new SpinnerModel("Silvester"));
        arrayList.add(new SpinnerModel("Jain Bush"));
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



    public static ArrayList<SpinnerModel> getCategories() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();
        arrayList.add(new SpinnerModel("Police"));
        arrayList.add(new SpinnerModel("Traffic Police"));
        arrayList.add(new SpinnerModel("Sheriff"));
        arrayList.add(new SpinnerModel("Fire fighter"));
        return arrayList;

    }


}
