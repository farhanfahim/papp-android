package co.chatsdk.ui.chat.viewholder;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import co.chatsdk.core.dao.Keys;
import co.chatsdk.core.dao.Message;
import co.chatsdk.ui.R;
import co.chatsdk.ui.chat.BaseMessageViewHolder;
import co.chatsdk.ui.chat.ImageMessageOnClickHandler;

public class ImageMessageViewHolder extends BaseMessageViewHolder {


    public ImageMessageViewHolder(View itemView, Activity activity) {
        super(itemView, activity);
    }

    @Override
    public void setMessage(Message message) {
        super.setMessage(message);

        setImageHidden(false);
        setBubbleHidden(true);
        setVoiceMessage(true);

        int viewWidth = maxWidth();
        int viewHeight = maxHeight();

        String url = getImageURL();

        if (url != null && url.length() > 0) {
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                    .setResizeOptions(new ResizeOptions(viewWidth, viewHeight))
                    .build();

            messageImageView.setController(
                    Fresco.newDraweeControllerBuilder()
                            .setOldController(messageImageView.getController())
                            .setImageRequest(request)
                            .build());

        } else {
            // Loads the placeholder
            messageImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            messageImageView.setActualImageResource(R.drawable.icn_200_image_message_placeholder);
        }
    }

    @Override
    public void onClick (View v) {
        super.onClick(v);
        if (message != null) {
            ImageMessageOnClickHandler.onClick(activity, v, getImageURL());
        }
    }

    public String getImageURL () {
        return message.stringForKey(Keys.MessageImageURL);
    }
}
