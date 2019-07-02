package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.huannguyen.swipeablerv.SWItemDelegate;
import io.huannguyen.swipeablerv.adapter.DelegateSWAdapter;

/**
 */
public class ChatListAdapter extends DelegateSWAdapter<SpinnerModel> {

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<SpinnerModel> arrData;

    public ChatListAdapter(Context activity, List<SpinnerModel> arrData, OnItemClickListener onItemClickListener, SWItemDelegate swItemDelegate) {
        super(swItemDelegate);
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_chats, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SpinnerModel model = arrData.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;

        setListener(viewHolder, model);
    }


    private void setListener(final ViewHolder holder, final SpinnerModel model) {
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
        @BindView(R.id.imgStatusOnline)
        CircleImageView imgStatusOnline;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.txtMessage)
        AnyTextView txtMessage;
        @BindView(R.id.txtDate)
        AnyTextView txtDate;
        @BindView(R.id.txtTime)
        AnyTextView txtTime;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
