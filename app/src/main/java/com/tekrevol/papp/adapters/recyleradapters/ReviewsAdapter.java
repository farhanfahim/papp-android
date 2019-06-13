package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.helperclasses.DateHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.models.receiving_model.ReviewsModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;



    private Context activity;
    private List<ReviewsModel> arrData;

    public ReviewsAdapter(Context activity, List<ReviewsModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_reviews, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        ReviewsModel model = arrData.get(i);


        ImageLoaderHelper.loadImageWithAnimationsByPath(holder.imgProfile, model.getUser().getUserDetails().getImage(), true);
        holder.txtName.setText(model.getUser().getUserDetails().getFullName());
        holder.ratingbarDeliverySpeed.setRating((float) model.getRating());
        holder.txtReviews.setText(model.getReview());


        String date = DateManager.convertToUserTimeZone(model.getCreatedAt());
        holder.txtDate.setText(DateManager.getDate(date, AppConstants.DISPLAY_DATE_ONLY_FORMAT));
        holder.txtTime.setText(DateManager.getDate(date, AppConstants.DISPLAY_TIME_ONLY_FORMAT));


        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final ReviewsModel model) {
        holder.contParentLayout.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, null));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProfile)
        CircleImageView imgProfile;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.ratingbar)
        AppCompatRatingBar ratingbarDeliverySpeed;
        @BindView(R.id.txtDate)
        AnyTextView txtDate;
        @BindView(R.id.txtTime)
        AnyTextView txtTime;
        @BindView(R.id.txtReviews)
        AnyTextView txtReviews;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
