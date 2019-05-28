package com.tekrevol.papp.libraries.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.managers.SharedPreferenceManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.Map;

import com.android.papp.R;
import com.tekrevol.papp.constatnts.AppConstants;

/**
 * Created by khanhamza on 08-Mar-17.
 */

public class ImageLoaderHelper {


    /**
     * WITHOUT ANIMATION, WITH HEADER
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void loadImageWithConstantHeadersWithoutAnimation(Context context, ImageView imageView, String url) {
        ImageLoader.getInstance().displayImage(url,
                imageView,
                ImageLoaderHelper.getOptionsSimple(WebServiceConstants
                        .getHeaders(SharedPreferenceManager.getInstance(context).getString(AppConstants.KEY_TOKEN))));
    }

    /**
     * WITH ANIMATION, WITH HEADER
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void loadImageWithConstantHeaders(Context context, ImageView imageView, String url) {
        ImageLoader.getInstance().displayImage(url,
                imageView,
                ImageLoaderHelper.getOptionsWithAnimation(WebServiceConstants
                        .getHeaders(SharedPreferenceManager.getInstance(context).getString(AppConstants.KEY_TOKEN))));
    }


    /**
     * WITHOUT ANIMATION, WITHOUT HEADER
     *
     * @param imageView
     * @param url
     */

    public static void loadImageWithouAnimation(ImageView imageView, String url) {
        ImageLoader.getInstance().displayImage(url,
                imageView,
                ImageLoaderHelper.getOptionsSimple());
    }


    /**
     * WITH ANIMATION, WITHOUT HEADER
     *
     * @param imageView
     * @param url
     */

    public static void loadImageWithAnimations(ImageView imageView, String url) {
        ImageLoader.getInstance().displayImage(url,
                imageView,
                ImageLoaderHelper.getOptionsWithAnimation());
    }



    /**
     * WITHOUT ANIMATION, WITHOUT HEADER, will create URL from path
     *
     * @param imageView
     * @param path
     */

    public static void loadImageWithouAnimationByPath(ImageView imageView, String path) {
        ImageLoader.getInstance().displayImage(getImageURLFromPath(path),
                imageView,
                ImageLoaderHelper.getOptionsSimple());
    }


    /**
     * WITH ANIMATION, WITHOUT HEADER,  will create URL from path
     *
     * @param imageView
     * @param path
     */

    public static void loadImageWithAnimationsByPath(ImageView imageView, String path) {
        ImageLoader.getInstance().displayImage(getImageURLFromPath(path),
                imageView,
                ImageLoaderHelper.getOptionsWithAnimation());
    }




    public static void loadBase64Image(ImageView imageView, String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        if (decodedByte != null) {
            imageView.setImageBitmap(decodedByte);
        } else {
            imageView.setImageResource(R.drawable.profile_placeholder);
        }
    }


    public static String getImageURLFromPath(String path) {
        return WebServiceConstants.IMAGE_BASE_URL + path;
    }


    public static String getImageURLFromPath(String path, String width, String height) {
        return WebServiceConstants.IMAGE_BASE_URL + path + "?w=" + width + "&h=" + height;
    }

    public static DisplayImageOptions getOptionsSimple() {

        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.color.base_dark_gray)
                .showImageOnFail(R.drawable.profile_placeholder)
                .showImageOnLoading(R.drawable.profile_placeholder)
                .build();
    }


    public static DisplayImageOptions getOptionsSimple(Map<String, String> headers) {

        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.color.base_dark_gray)
                .showImageOnFail(R.drawable.profile_placeholder)
                .showImageOnLoading(R.drawable.profile_placeholder)
                .extraForDownloader(headers)
                .build();
    }

    public static DisplayImageOptions getOptionsWithAnimation() {

        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.color.base_dark_gray)
                .showImageOnFail(R.drawable.profile_placeholder)
                .showImageOnLoading(R.drawable.profile_placeholder)
                .imageScaleType(ImageScaleType.EXACTLY).displayer(new FadeInBitmapDisplayer(200)).build();
    }


    public static DisplayImageOptions getOptionsWithAnimation(Map<String, String> headers) {

        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.color.base_dark_gray)
                .showImageOnFail(R.drawable.profile_placeholder)
                .showImageOnLoading(R.drawable.profile_placeholder)
                .extraForDownloader(headers)
                .imageScaleType(ImageScaleType.EXACTLY).displayer(new FadeInBitmapDisplayer(200)).build();
    }
}
