package co.chatsdk.ui.chat.handlers;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;

import co.chatsdk.core.base.AbstractMessageViewHolder;
import co.chatsdk.core.dao.Message;
import co.chatsdk.ui.chat.viewholder.VoiceMessageViewHolder;

public class VoiceMessageHandler extends AbstractMessageDisplayHandler {

    private Handler handler = new Handler();

    @Override
    public void updateMessageCellView(Message message, AbstractMessageViewHolder viewHolder, Context context) {

    }

    @Override
    public String displayName(Message message) {
        return "Voice Message";
    }

    @Override
    public AbstractMessageViewHolder newViewHolder(boolean isReply, Activity activity) {
        View row = row(isReply, activity);
        return new VoiceMessageViewHolder(row, activity,handler);
    }
}
