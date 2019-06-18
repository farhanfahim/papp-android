package com.tekrevol.papp.constatnts;

import com.tekrevol.papp.R;
import com.tekrevol.papp.models.general.SpinnerModel;

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

    public static ArrayList<SpinnerModel> getGenderArray() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();
        arrayList.add(new SpinnerModel("Male"));
        arrayList.add(new SpinnerModel("Female"));
        return arrayList;

    }

    public static ArrayList<SpinnerModel> getSessionTypes() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();
        arrayList.add(new SpinnerModel("All"));
        arrayList.add(new SpinnerModel("Audio Call"));
        arrayList.add(new SpinnerModel("Video Call"));
        arrayList.add(new SpinnerModel("One on One"));
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


    public static ArrayList<SpinnerModel> getSponsorLogo() {
        ArrayList<SpinnerModel> arrayList = new ArrayList<>();
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_adidas));
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_amazon));
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_baltimore));
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_burger_king));
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_foorlocker));
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_gamestop));
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_nike));
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_playstations));
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_target));
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_underarmor));
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_vans));
        arrayList.add(new SpinnerModel("drawable://" + R.drawable.logo_mcdonald));

        return arrayList;

    }


}
