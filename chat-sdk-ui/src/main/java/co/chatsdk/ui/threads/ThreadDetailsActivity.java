/*
 * Created by Itzik Braun on 12/3/2015.
 * Copyright (c) 2015 deluge. All rights reserved.
 *
 * Last Modification at: 3/12/15 4:27 PM
 */

package co.chatsdk.ui.threads;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang3.StringUtils;

import co.chatsdk.core.dao.Keys;
import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.dao.ThreadMetaValue;
import co.chatsdk.core.dao.User;
import co.chatsdk.core.events.EventType;
import co.chatsdk.core.events.NetworkEvent;
import co.chatsdk.core.interfaces.ThreadType;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.utils.DisposableList;
import co.chatsdk.core.utils.Strings;
import co.chatsdk.ui.R;
import co.chatsdk.ui.chat.ChatActivity;
import co.chatsdk.ui.contacts.ContactsFragment;
import co.chatsdk.ui.dialog.CreateGroupDialog;
import co.chatsdk.ui.dialog.DialogCallbacks;
import co.chatsdk.ui.helpers.DialogUtils;
import co.chatsdk.ui.helpers.ProfilePictureChooserOnClickListener;
import co.chatsdk.ui.main.BaseActivity;
import co.chatsdk.ui.utils.ToastHelper;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by braunster on 24/11/14.
 */
public class ThreadDetailsActivity extends BaseActivity implements DialogCallbacks {

    /** Set true if you want slide down animation for this context exit. */
    protected boolean animateExit = false;

    protected Thread thread;
    protected SimpleDraweeView threadImageView;
    protected TextView btnLeftGroup;
    protected TextView btnDeleteGroup;

    protected ContactsFragment contactsFragment;
    protected DisposableList disposableList = new DisposableList();

    protected MenuItem settingsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            getDataFromBundle(savedInstanceState);
        }
        else {
            if (getIntent().getExtras() != null) {
                getDataFromBundle(getIntent().getExtras());
            }
            else {
                finish();
            }
        }

        setContentView(activityLayout());

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Chat Detail");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initViews();

        disposableList.add(ChatSDK.events().sourceOnMain()
                .filter(NetworkEvent.threadUsersUpdated())
                .subscribe(networkEvent -> loadData()));

        loadData();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected @LayoutRes int activityLayout() {
        return R.layout.chat_sdk_activity_thread_details;
    }

    protected void initViews() {
        ((TextView) findViewById(R.id.tv_title)).setText(Strings.nameForThread(thread));

        threadImageView = findViewById(R.id.chat_sdk_thread_image_view);
        btnLeftGroup = findViewById(R.id.btn_left_group);
        btnDeleteGroup = findViewById(R.id.btn_delete_group);

        updateMetaData();

        disposableList.add(ChatSDK.events().sourceOnMain()
                .filter(NetworkEvent.filterType(EventType.ThreadMetaUpdated))
                .subscribe(networkEvent -> updateMetaData()));

        setOnclickListener();

        if (thread.typeIs(ThreadType.Group)) {
            showButtonsIfNeeded();
//            btnDeleteGroup.setVisibility(View.VISIBLE);
            btnLeftGroup.setVisibility(View.VISIBLE);
        } else {
            btnDeleteGroup.setVisibility(View.GONE);
            btnLeftGroup.setVisibility(View.GONE);
        }
    }

    private boolean isGroupAdmin() {
        for(User item: thread.getUsers()) {
            if(item.getEntityID().equals(ChatSDK.currentUser().getEntityID())) {
                if(item.getStatus().equals("owner")) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }




    private void showButtonsIfNeeded() {
       ChatSDK.thread().isGroupAdmin(thread)
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<Boolean>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(Boolean aBoolean) {
                      if(aBoolean ) {
                          btnDeleteGroup.setVisibility(View.VISIBLE);
                      } else {
                          btnDeleteGroup.setVisibility(View.INVISIBLE);
                      }

                   }

                   @Override
                   public void onError(Throwable e) {

                   }

                   @Override
                   public void onComplete() {

                   }
               });

//       if(isGroupAdmin()) {
//           btnDeleteGroup.setVisibility(View.VISIBLE);
//       } else {
//           btnDeleteGroup.setVisibility(View.GONE);
//       }
    }

    private void setOnclickListener() {
        btnLeftGroup.setOnClickListener(v -> {
            leftGroup();
        });

        btnDeleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGroup();
            }
        });
    }

    private void deleteGroup() {
        showLoader();
        ChatSDK.thread().deleteThreadPermanently(thread)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        hideLoader();
                        ToastHelper.show(ThreadDetailsActivity.this, getString(R.string.group_deleted));
                        closeActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoader();
                        ToastHelper.show(ThreadDetailsActivity.this, getString(R.string.unable_to_left_group));
                    }
                });
    }

    private void makeAdminToEarliest() {
        if(thread.getUsers().size() > 1) {
            String id = thread.getUsers().get(0).getEntityID();
            ChatSDK.thread().makeAdminToEarliestPerson(thread,id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            hideLoader();
                            closeActivity();
                        }

                        @Override
                        public void onError(Throwable e) {
                            hideLoader();
                            ToastHelper.show(ThreadDetailsActivity.this, getString(R.string.unable_to_make_admin));
                        }
                    });    ;
        }
    }

    ProgressDialog nDialog;
    private void showLoader() {
        nDialog = new ProgressDialog(ThreadDetailsActivity.this);
        nDialog.setMessage("Processing..");
//        nDialog.setTitle(message);
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
    }

    private void hideLoader() {
        nDialog.dismiss();
    }

    private void leftGroup() {
        showLoader();
        DialogUtils.showToastDialog(ThreadDetailsActivity.this, "", getResources().getString(R.string.alert_left_thread), getResources().getString(R.string.left),
                getResources().getString(R.string.cancel), null, () -> {
                    if(thread != null) {
                        ChatSDK.thread().deleteThread(thread)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        ChatSDK.thread().isGroupAdmin(thread)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<Boolean>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(Boolean aBoolean) {
                                                        ToastHelper.show(ThreadDetailsActivity.this, getString(R.string.left_group_successfully));
                                                        if(aBoolean) {
                                                            makeAdminToEarliest();
                                                        } else {
                                                            hideLoader();
                                                            closeActivity();
                                                        }
                                                }

                                                    @Override
                                                    public void onError(Throwable e) {

                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }
                                                });
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        ToastHelper.show(ThreadDetailsActivity.this, getString(R.string.left_group_fail));
                                    }
                                });;
                    } else {
                        ToastHelper.show(ThreadDetailsActivity.this, getString(R.string.left_group_fail));
                    }
                    return null;
                });
    }

    private void closeActivity() {
        getIntent().putExtra(Keys.GROUP_LEFT, true);
        setResult(RESULT_OK,getIntent());
        finish();
    }

    protected void updateMetaData() {
        // TODO: permanently move thread name into meta data
        ThreadMetaValue nameMetaValue = thread.metaValueForKey(Keys.Name);
        if (nameMetaValue != null)
            ((TextView) findViewById(R.id.tv_title)).setText(nameMetaValue.getValue());

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected void loadData () {
        ThreadImageBuilder.load(threadImageView, thread);

        // CoreThread users bundle
        contactsFragment = new ContactsFragment();
        contactsFragment.forThreadDetail = true;
        contactsFragment.setInflateMenu(false);
        contactsFragment.setLoadingMode(ContactsFragment.MODE_LOAD_THREAD_USERS);
        contactsFragment.setExtraData(thread.getEntityID());
        contactsFragment.setClickMode(ContactsFragment.CLICK_MODE_ADD_USER_TO_THREAD);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_thread_users, contactsFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Only if the current user is the admin of this thread.
        if (StringUtils.isNotBlank(thread.getCreatorEntityId()) && thread.getCreatorEntityId().equals(ChatSDK.currentUserID())) {
            //threadImageView.setOnClickListener(ChatSDKIntentClickListener.getPickImageClickListener(this, THREAD_PIC));
            threadImageView.setOnClickListener(new ProfilePictureChooserOnClickListener(this));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        setResult(AppCompatActivity.RESULT_OK);

        finish(); // Finish needs to be called before animate exit
        if (animateExit) {
            overridePendingTransition(R.anim.dummy, R.anim.slide_top_bottom_out);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // TODO: Enable thread images
    }

    @Override
    protected void onStop() {
        disposableList.dispose();
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getDataFromBundle(intent.getExtras());
    }

    protected void getDataFromBundle(Bundle bundle) {
        if (bundle == null) {
            return;
        }

        animateExit = bundle.getBoolean(ChatActivity.ANIMATE_EXIT, animateExit);

        String threadEntityID = bundle.getString(Keys.THREAD_ENTITY_ID);

        if (threadEntityID != null && !threadEntityID.isEmpty()) {
            thread = ChatSDK.db().fetchThreadWithEntityID(threadEntityID);
        }
        else {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Keys.THREAD_ENTITY_ID, thread.getEntityID());
        outState.putBoolean(ChatActivity.ANIMATE_EXIT, animateExit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        settingsItem = menu.add(Menu.NONE, R.id.action_chat_sdk_settings, 12, getString(R.string.action_settings));
        settingsItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        settingsItem.setIcon(R.drawable.edit_transcript_icon);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.action_chat_sdk_settings) {
            new CreateGroupDialog(ThreadDetailsActivity.this,this,"", true, thread.getName()).show();
//          ChatSDK.ui().startPublicThreadEditDetailsActivity(ChatSDK.shared().context(), thread.getEntityID());
        }
        return true;
    }


    @Override
    public void didTappedOnPositive(String text) {
        thread.setName(text);
        thread.setAdmin(true);
        thread.update();
        thread.setMetaValue(Keys.Name, text);
        disposableList.add(ChatSDK.thread().pushThreadMeta(thread).subscribe());
    }

    @Override
    public void didTappedOnNegative() {

    }
}
