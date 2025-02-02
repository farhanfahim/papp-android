package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.FileManager;
import com.tekrevol.papp.models.receiving_model.TaskAttachmentModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;
    private boolean isViewOnly;


    private View itemView = null;


    private Context activity;
    private List<TaskAttachmentModel> arrData;


    public AttachmentAdapter(Context activity, List<TaskAttachmentModel> arrData, OnItemClickListener onItemClickListener, boolean isViewOnly) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        this.isViewOnly = isViewOnly;
    }

    public boolean isViewOnly() {
        return isViewOnly;
    }

    public void setViewOnly(boolean viewOnly) {
        isViewOnly = viewOnly;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_attachment, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        TaskAttachmentModel model = arrData.get(i);


        if (isViewOnly) {
            ImageLoaderHelper.loadImageWithAnimationsByPath(holder.imgAttachment, model.getPath(), false);
            holder.txtFileName.setText(model.getName());
            holder.imgDelete.setVisibility(View.GONE);
            return;
        }

        File file = new File(model.getPath());
        holder.txtFileName.setText(FileManager.getFileNameFromPath(model.getPath()));

        String uri = Uri.fromFile(file).toString();


        if (uri.contains(".jpg") || uri.contains(".jpeg") || uri.contains(".png")) {
            ImageLoader.getInstance().displayImage(uri, holder.imgAttachment);
        } else if (uri.contains(".pdf")) {
            setPdfThumbnail(Uri.parse(uri), holder.imgAttachment);
        }


        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final TaskAttachmentModel model) {
        holder.imgDelete.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, itemView, ""));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgDelete)
        ImageView imgDelete;
        @BindView(R.id.imgAttachment)
        ImageView imgAttachment;
        @BindView(R.id.txtFileName)
        AnyTextView txtFileName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    void setPdfThumbnail(Uri pdfUri, ImageView imageView) {
        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(activity);
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = activity.getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            imageView.setImageBitmap(bmp);
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch (Exception e) {
            //todo with exception
        }
    }
}
