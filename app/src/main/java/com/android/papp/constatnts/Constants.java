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


    public static ArrayList<SpinnerModel> getSessionTypes() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();
        arrayList.add(new SpinnerModel("Video Call"));
        arrayList.add(new SpinnerModel("Audio Call"));
        arrayList.add(new SpinnerModel("One on One"));
        arrayList.add(new SpinnerModel("Chat"));
        return arrayList;

    }

    public static ArrayList<SpinnerModel> getSpeciality() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();
        arrayList.add(new SpinnerModel("Leadership"));
        arrayList.add(new SpinnerModel("SOPS"));
        arrayList.add(new SpinnerModel("Personal Traits"));
        arrayList.add(new SpinnerModel("Swimming"));
        arrayList.add(new SpinnerModel("Cycling"));
        return arrayList;

    }


    public static ArrayList<SpinnerModel> getMedalURL() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();
        arrayList.add(new SpinnerModel("http://icons.iconarchive.com/icons/webalys/kameleon.pics/512/Medal-2-icon.png"));
        arrayList.add(new SpinnerModel("https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.shareicon.net%2Fdownload%2F2017%2F03%2F29%2F881750_sport_512x512.png"));
        arrayList.add(new SpinnerModel("https://image.flaticon.com/icons/png/512/625/625393.png"));
        arrayList.add(new SpinnerModel(""));
        arrayList.add(new SpinnerModel(""));
        arrayList.add(new SpinnerModel(""));
        return arrayList;

    }




}
