
package edu.aku.akuh_health_first.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.jsibbold.zoomage.ZoomageView;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.IndicatorSeekBarType;
import com.warkiz.widget.IndicatorType;
import com.warkiz.widget.TickType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.aku.akuh_health_first.R;
import edu.aku.akuh_health_first.constatnts.AppConstants;
import edu.aku.akuh_health_first.constatnts.WebServiceConstants;
import edu.aku.akuh_health_first.helperclasses.ui.helper.TitleBar;
import edu.aku.akuh_health_first.helperclasses.ui.helper.UIHelper;
import edu.aku.akuh_health_first.libraries.fileloader.FileLoader;
import edu.aku.akuh_health_first.libraries.fileloader.listener.FileRequestListener;
import edu.aku.akuh_health_first.libraries.fileloader.pojo.FileResponse;
import edu.aku.akuh_health_first.libraries.fileloader.request.FileLoadRequest;
import edu.aku.akuh_health_first.libraries.fileloader.request.MultiFileLoadRequest;
import edu.aku.akuh_health_first.managers.retrofit.GsonFactory;
import edu.aku.akuh_health_first.models.PacsDescriptionModel;
import edu.aku.akuh_health_first.models.PacsModel;

public class PacsActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private int progress;
    private PacsDescriptionModel pacsModel;
    private ZoomageView iv;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private TextView tvProgress;
    private ArrayList<String> pacsList;
    TitleBar titleBar;
    IndicatorSeekBar indicatorSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacs);
        bindViews();
//        indicatorSeekbar();
        setTitlebar();


        pacsList = new ArrayList<String>();
        String fromJson = getIntent().getExtras().getString(AppConstants.JSON_STRING_KEY);
        pacsModel = GsonFactory.getSimpleGson().fromJson(fromJson, PacsDescriptionModel.class);
        pacsList = (ArrayList<String>) pacsModel.getStudyDataString();
        final ArrayList uri = new ArrayList<String>();
        for (String stringList : pacsList) {
            uri.add(stringList);
        }


        loadImage(iv, uri.get(0).toString());
        tvProgress.setText(1 + " of " + uri.size());

//        final List<MultiFileLoadRequest> multiFileLoadRequests = new ArrayList<>();
//        for (int i = 0; i < uri.size(); i++) {
//            MultiFileLoadRequest loadRequest = new MultiFileLoadRequest(uri.get(i).toString());
//            multiFileLoadRequests.add(i, loadRequest);
        setListeners(uri);

    }

    private void setTitlebar() {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.showBackButton(this);
        titleBar.setTitle("PACS Viewer");
    }



    private void setListeners(final ArrayList uri/*, final List<MultiFileLoadRequest> multiFileLoadRequests*/) {
//        for (int j = 1; j <= multiFileLoadRequests.size(); j++) {
        progress = uri.size();
        final int arraySize = uri.size();

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progress <= 1) {
                    return;
                } else {
                    loadImage(iv, uri.get(--progress).toString());
                    tvProgress.setText(progress + " of " + arraySize);
                }

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progress >= arraySize) {
                    return;
                } else {
                    loadImage(iv, uri.get(progress++).toString());
                    tvProgress.setText(progress + " of " + arraySize);
                }
            }
        });


        indicatorSeekBar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
//             tvProgress.setText(String.valueOf(progress));

             }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

    }


    private void bindViews() {
        iv = (ZoomageView) findViewById(R.id.image);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        tvProgress = (TextView) findViewById(R.id.tv_progress);
        titleBar = findViewById(R.id.titlebar);
        indicatorSeekBar = findViewById(R.id.indSeekbar);
    }

    private void loadImage(final ImageView iv, String imageUrl) {
        iv.setImageBitmap(null);
        FileLoader.with(this)
                .load(imageUrl)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        Bitmap bitmap = BitmapFactory.decodeFile(response.getDownloadedFile().getPath());
                        iv.setImageBitmap(bitmap);


                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        Log.d(TAG, "onError: " + t.getMessage());
                    }
                });
    }

//    private void indicatorSeekbar() {
////        indicatorSeekBar = new IndicatorSeekBar.Builder(this)
////                .setMax(20)
////                .setMin(0)
//////                .setProgress(35)
////                .setSeekBarType(IndicatorSeekBarType.CONTINUOUS)
////                .setTickType(TickType.OVAL)
//////                .setTickNum(8)
////                .setBackgroundTrackSize(2)//dp size
////                .setProgressTrackSize(3)//dp size
////                .setIndicatorType(IndicatorType.CIRCULAR_BUBBLE)
//////                .setIndicatorColor(Color.parseColor("#19BBDE"))
////                .build();
//
////change build params at the runtime.
//
////        indicatorSeekBar.getBuilder()
////                .setMax(232)
////                .setMin(43)
////                .setTickType(TickType.OVAL)
////                .setTickColor(Color.parseColor("#19BBDE"))
////                .apply();
//    }
}
