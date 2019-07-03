package co.chatsdk.core.utils;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.chatsdk.core.R;
import co.chatsdk.core.dao.Message;
import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.dao.User;
import co.chatsdk.core.interfaces.MessageDisplayHandler;
import co.chatsdk.core.interfaces.ThreadType;
import co.chatsdk.core.session.ChatSDK;

/**
 * Created by benjaminsmiley-andrews on 07/06/2017.
 */

public class Strings {

    public static String t (Context context, int resId) {
        return context.getString(resId);
    }

    public static String t (int resId) {
        return t(ChatSDK.shared().context(), resId);
    }

    public static String payloadAsString (Message message) {
        MessageDisplayHandler handler =  ChatSDK.ui().getMessageHandler(message.getMessageType());
        if (handler != null) {
            return handler.displayName(message);
        }
        return t(R.string.unknown_message);
    }

    public static String dateTime (Date date) {
        return getDisplayableTime(date.getTime());
    }

    public static String getDisplayableTime(long delta)
    {
        long difference=0;
        Long mDate = java.lang.System.currentTimeMillis();

        if(mDate > delta)
        {
            difference= mDate - delta;
            final long seconds = difference/1000;
            final long minutes = seconds/60;
            final long hours = minutes/60;
            final long days = hours/24;
            final long months = days/31;
            final long years = days/365;

            if (seconds <= 0)
            {
                return "Just now";
            }
            else if (seconds < 60)
            {
//                return seconds == 1 ? "a second ago" : seconds + " seconds ago";
                return "Just now";
            }
            else if (seconds < 120)
            {
                return "a minute ago";
            }
            else if (seconds < 2700) // 45 * 60
            {
                return minutes + " minutes ago";
            }
            else if (seconds < 5400) // 90 * 60
            {
                return "an hour ago";
            }
            else if (seconds < 86400) // 24 * 60 * 60
            {
                return hours + " hours ago";
            }
            else if (seconds < 172800) // 48 * 60 * 60
            {
                return "Yesterday";
            }
            else if (seconds < 2592000) // 30 * 24 * 60 * 60
            {
                return days + " days ago";
            }
            else if (seconds < 31104000) // 12 * 30 * 24 * 60 * 60
            {
                return months <= 1 ? "one month ago" : days + " months ago";
            }
            else
            {

                return years <= 1 ? "one year ago" : years + " years ago";
            }
        }
        return null;
    }

    public static String date (Date date) {
        return new SimpleDateFormat("dd/MM/yy").format(date);
    }

    public static String nameForThread (Thread thread) {

        if (StringUtils.isNotEmpty(thread.getDisplayName())) {
            return thread.getDisplayName();
        }

        // Due to the bundle printing when the app execute on debug this sometime is null.
        User currentUser = ChatSDK.currentUser();
        String name = "";

        if (thread.typeIs(ThreadType.Private)) {

            for (User user : thread.getUsers()){
                if (!user.getId().equals(currentUser.getId())) {
                    String n = user.getName();

                    if (StringUtils.isNotEmpty(n)) {
                        name += (!name.equals("") ? ", " : "") + n;
                    }
                }
            }
        }

        if(name.length() == 0) {
            name = Strings.t(R.string.no_name);
        }
        return name;
    }

}
