package co.chatsdk.core.base;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.commons.lang3.StringUtils;

import co.chatsdk.core.dao.DaoCore;
import co.chatsdk.core.dao.User;
import co.chatsdk.core.handlers.CoreHandler;
import co.chatsdk.core.session.ChatSDK;

import static co.chatsdk.core.session.ChatSDK.PREFS_NAME;

/**
 * Created by benjaminsmiley-andrews on 03/05/2017.
 */

public abstract class AbstractCoreHandler implements CoreHandler {

    private User cachedUser = null;
    protected Context context;

    public static String login_user_id = "login_user_id";

    public User currentBaseUserModel(){
        String entityID = "";
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (settings.contains(login_user_id)) {
            entityID = settings.getString(login_user_id, "0");
        }

        if(cachedUser == null || !cachedUser.getEntityID().equals(entityID)) {
            if (StringUtils.isNotEmpty(entityID)) {
                cachedUser = DaoCore.fetchEntityWithEntityID(User.class, entityID);
            }
            else {
                cachedUser = null;
            }
        }
       return cachedUser;
    }

    @Override
    public void goOnline() {
        if (ChatSDK.lastOnline() != null) {
            ChatSDK.lastOnline().setLastOnline(currentBaseUserModel());
        }
    }
}
