package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 */
public class ChatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<SpinnerModel> arrData;


    final int TYPE_DAY_HEADER = 0;
    final int TYPE_RECIEVING_MESSAGE = 1;
    final int TYPE_SENDING_MESSAGE = 2;

    public ChatsAdapter(Context activity, List<SpinnerModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_SENDING_MESSAGE) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_message_snd, parent, false);
            return new SendMsgViewHolder(view);
        } else if (viewType == TYPE_RECIEVING_MESSAGE) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_message_rcv, parent, false);
            return new RcvMsgViewHolder(view);
        } else {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_day_chat, parent, false);
            return new DayHeaderViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i) {


    }


    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return TYPE_DAY_HEADER;
            case 1:
                return TYPE_RECIEVING_MESSAGE;
            case 2:
                return TYPE_SENDING_MESSAGE;
            case 3:
                return TYPE_SENDING_MESSAGE;
            default:
                return TYPE_RECIEVING_MESSAGE;
        }
    }

    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class DayHeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDay)
        AnyTextView txtDay;


        DayHeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class SendMsgViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtMessageSnd)
        AnyTextView txtMessageSnd;
        @BindView(R.id.txtTimeSnd)
        AnyTextView txtTimeSnd;
        @BindView(R.id.imgProfileSnd)
        CircleImageView imgProfileSnd;


        SendMsgViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class RcvMsgViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProfileRcv)
        CircleImageView imgProfileRcv;
        @BindView(R.id.txtMessageRcv)
        AnyTextView txtMessageRcv;
        @BindView(R.id.txtTimeRcv)
        AnyTextView txtTimeRcv;


        RcvMsgViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

