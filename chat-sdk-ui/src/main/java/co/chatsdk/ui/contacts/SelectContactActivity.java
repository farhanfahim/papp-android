/*
 * Created by Itzik Braun on 12/3/2015.
 * Copyright (c) 2015 deluge. All rights reserved.
 *
 * Last Modification at: 3/12/15 4:27 PM
 */

package co.chatsdk.ui.contacts;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.chatsdk.core.dao.Keys;
import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.dao.User;
import co.chatsdk.core.events.EventType;
import co.chatsdk.core.events.NetworkEvent;
import co.chatsdk.core.interfaces.UserListItem;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.utils.UserListItemConverter;
import co.chatsdk.ui.R;
import co.chatsdk.ui.chat.ChatActivity;
import co.chatsdk.ui.dialog.CreateGroupDialog;
import co.chatsdk.ui.dialog.DialogCallbacks;
import co.chatsdk.ui.main.BaseActivity;
import co.chatsdk.ui.search.SearchActivity;
import co.chatsdk.ui.utils.ToastHelper;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by itzik on 6/17/2014.
 */
public class SelectContactActivity extends BaseActivity implements DialogCallbacks {

    public static final int MODE_NEW_CONVERSATION = 1991;
    public static final int MODE_ADD_TO_CONVERSATION = 1992;

    public static final String MODE = "mode";

    protected RecyclerView contactsRecyclerView;
    protected RecyclerView selectedContactsRecyclerView;
    protected UsersListAdapter adapter;
    protected SelectedUsersListAdapter selectedUsersListAdapter;
    protected FloatingActionButton btnStartChat;
    private Menu search_menu;

    /** Default value - MODE_NEW_CONVERSATION*/
    private int mode = MODE_NEW_CONVERSATION;

    /** For add to conversation mode.*/
    protected String threadEntityID = "";

    /** For add to conversation mode.*/
    protected Thread thread;

    /** Set true if you want slide down animation for this context exit. */
    protected boolean animateExit = false;
    private SearchView searchView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();

        if (savedInstanceState != null)
        {
            getDataFromBundle(savedInstanceState);
        }
        else
        {
            if (getIntent().getExtras() != null)
            {
                getDataFromBundle(getIntent().getExtras());
            }
        }

        // Refresh the list when the contacts change
        ChatSDK.events().sourceOnMain()
                .filter(NetworkEvent.filterContactsChanged())
                .subscribe(networkEvent -> loadData());

        ChatSDK.events().sourceOnMain()
                .filter(NetworkEvent.filterType(EventType.UserMetaUpdated))
                .subscribe(networkEvent -> loadData());

        setContentView(activityLayout());
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setSearchtollbar();
        initViews();
    }

    protected void getDataFromBundle(Bundle bundle){
        mode = bundle.getInt(MODE, mode);
        threadEntityID = bundle.getString(Keys.THREAD_ENTITY_ID, threadEntityID);
        animateExit = bundle.getBoolean(ChatActivity.ANIMATE_EXIT, animateExit);
    }

    protected void initActionBar(){
        ActionBar ab = getSupportActionBar();
        if (ab!=null) {
            ab.setTitle(getString(R.string.pick_friends));

            ab.setHomeButtonEnabled(true);
        }
    }

    Toolbar searchtollbar;
    MenuItem item_search;
    public void setSearchtollbar()
    {
        searchtollbar = (Toolbar) findViewById(R.id.searchtoolbar);
        if (searchtollbar != null) {
            searchtollbar.inflateMenu(R.menu.menu_search);
            search_menu = searchtollbar.getMenu();

            searchtollbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.searchtoolbar,1,true,false);
                    else
                        searchtollbar.setVisibility(View.GONE);
                }
            });

            item_search = search_menu.findItem(R.id.action_search);

            MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(R.id.searchtoolbar,1,true,false);
                    }
                    else
                        searchtollbar.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // Do something when expanded
                    return true;
                }
            });

            initSearchView();


        } else
            Log.d("toolbar", "setSearchtollbar: NULL");
    }

    public void initSearchView()
    {
        searchView = (SearchView) search_menu.findItem(R.id.action_search).getActionView();

        // Enable/Disable Submit button in the keyboard

        searchView.setSubmitButtonEnabled(false);
        // Change search close button image
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        // set hint and the text colors
        searchView.setIconified(false);



        // set the cursor

//        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        try {
//            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
//            mCursorDrawableRes.setAccessible(true);
//            mCursorDrawableRes.set(searchTextView, R.drawable.s); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);

                return true;
            }

            public void callSearch(String query) {
                //Do searching
                Log.i("query", "" + query);

            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow)
    {
        final View myView = findViewById(viewID);

        int width=myView.getWidth();

        if(posFromRight>0)
            width-=(posFromRight*getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material))-(getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)/ 2);
        if(containsOverflow)
            width-=getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx=width;
        int cy=myView.getHeight()/2;

        Animator anim;
        if(isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0,(float)width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float)width, 0);

        anim.setDuration((long)220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isShow)
                {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if(isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Keys.THREAD_ENTITY_ID, threadEntityID);
        outState.putInt(MODE, mode);
        outState.putBoolean(ChatActivity.ANIMATE_EXIT, animateExit);
    }

    protected @LayoutRes int activityLayout() {
        return R.layout.chat_sdk_activity_pick_friends;
    }

    protected void initViews() {
        contactsRecyclerView = findViewById(R.id.chat_sdk_list_contacts);
        selectedContactsRecyclerView = findViewById(R.id.selected_participants);
        btnStartChat = findViewById(R.id.chat_sdk_btn_add_contacts);

        if (mode == MODE_ADD_TO_CONVERSATION) {
//            btnStartChat.setText(getResources().getString(R.string.add_users));
        }

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((TextView) findViewById(R.id.tv_title)).setText(getString(R.string.select_participants));

    }

    protected void initList() {
        boolean enableMultiSelect = ChatSDK.config().groupsEnabled;
        if(adapter == null) {
            adapter = new UsersListAdapter(enableMultiSelect);
            selectedUsersListAdapter = new SelectedUsersListAdapter(enableMultiSelect);

            adapter.getItemClicks().subscribe(item -> {
                if(item instanceof User) {
                    if (ChatSDK.config().groupsEnabled) {
                        adapter.toggleSelection(item);
                    }
                    else {
                        UserListItem user = (UserListItem) item;
                        createAndOpenThread("", (User) user, ChatSDK.currentUser());
                    }

                    loadSelectedUser();
                    selectedUsersListAdapter.notifyDataSetChanged();
                }
            });

            selectedUsersListAdapter.getItemClicks().subscribe(item -> {
                if(item instanceof User) {
                    adapter.selectedUsersPositions.delete(getItemPosition(((User) item).getEntityID()));
                    adapter.notifyDataSetChanged();
                    loadSelectedUser();
                    selectedUsersListAdapter.notifyDataSetChanged();
                }
            });
        }

        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(adapter);

        selectedContactsRecyclerView.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        selectedContactsRecyclerView.setAdapter(selectedUsersListAdapter);

        loadData();


    }

    private int getItemPosition(String entityId) {
        for (int i = 0; i < adapter.getItems().size(); i++) {
            if(adapter.getItem(i) instanceof UserListItem) {
                User user = (User) adapter.getItem(i);
                if(user.getEntityID().equals(entityId)) {
                    return i;
                }
            }
        }

        return 0;
    }

    protected void createAndOpenThread (String name, User... users) {
        createAndOpenThread(name, Arrays.asList(users));
    }

    protected Single<Thread> createAndOpenThread (String name, List<User> users) {
        return ChatSDK.thread().createThread(name, users)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(thread -> {
                    if (thread != null) {
                      ChatSDK.ui().startChatActivityForID(getApplicationContext(), thread.getEntityID());
                    }
                }).doOnError(throwable -> ToastHelper.show(getApplicationContext(), R.string.create_thread_with_users_fail_toast));
    }

    protected void loadData () {
        final List<User> list = ChatSDK.contact().contacts();


        // Removing the users that is already inside the thread.
        if (mode == MODE_ADD_TO_CONVERSATION && !threadEntityID.equals("")){
            thread = ChatSDK.db().fetchThreadWithEntityID(threadEntityID);
            List<User> threadUser = thread.getUsers();
            list.removeAll(threadUser);
        }

        List<UserListItem> items = new ArrayList<>();
        for(User u : list) {
            items.add(u);
        }

        adapter.setUsers(items, true);
    }

    protected void loadSelectedUser() {
        final List<User> list = new ArrayList<>();
        for(int i = 0; i < adapter.selectedUsersPositions.size(); i++) {
            int key = adapter.selectedUsersPositions.keyAt(i);
            // get the object by the key.
            list.add((User) adapter.getItems().get(key));
        }

        List<UserListItem> items = new ArrayList<>();
        for(User u : list) {
            items.add(u);
        }

        selectedContactsRecyclerView.setVisibility(items.size() <= 0 ? View.GONE : View.VISIBLE);

        selectedUsersListAdapter.setUsers(items);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    ArrayList<User> users;
    @Override
    protected void onResume() {
        super.onResume();

        initList();

        btnStartChat.setOnClickListener(v -> {

            if (adapter.getSelectedCount() == 0) {
                showToast(getString(R.string.pick_friends_activity_no_users_selected_toast));
                return;
            }

//            if (mode == MODE_ADD_TO_CONVERSATION) {
//                showProgressDialog( getString(R.string.pick_friends_activity_prog_dialog_add_to_convo_message));
//            }
//            else if (mode == MODE_NEW_CONVERSATION) {
//                showProgressDialog(getString(R.string.pick_friends_activity_prog_dialog_open_new_convo_message));
//            }

            users = new ArrayList<>();

            users.addAll(UserListItemConverter.toUserList(adapter.getSelectedUsers()));

            if (mode == MODE_NEW_CONVERSATION) {
                users.add(ChatSDK.currentUser());
                // If there are more than 2 users then show a dialog to enter the name
                if(users.size() > 2) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectContactActivity.this);
//                    builder.setTitle(getString(R.string.pick_friends_activity_prog_group_name_dialog));

//                    // Set up the input
//                    final EditText input = new EditText(SelectContactActivity.this);
//                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//                    builder.setView(input);

                    // Set up the buttons
//                    builder.setPositiveButton(getString(R.string.create), (dialog, which) -> SelectContactActivity.this.createAndOpenThread(input.getText().toString(), users)
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe((thread, throwable) -> {
//                                dismissProgressDialog();
//                                finish();
//                            }));
//                    builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
//                        dialog.cancel();
//                        dismissProgressDialog();
//                    });
//
//                    builder.show();

                    new CreateGroupDialog(SelectContactActivity.this,this,"", false,"").show();

                }
                else {
                    createAndOpenThread("", users)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((thread, throwable) -> {
                                dismissProgressDialog();
                                finish();
                            });
                }
            }
            else if (mode == MODE_ADD_TO_CONVERSATION){

                ChatSDK.thread().addUsersToThread(thread, users)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            setResult(AppCompatActivity.RESULT_OK);
                            dismissProgressDialog();
                            finish();
                            if (animateExit) {
                                overridePendingTransition(R.anim.dummy, R.anim.slide_top_bottom_out);
                            }
                        }, throwable -> {
                            ChatSDK.logError(throwable);
                            dismissProgressDialog();
                            setResult(AppCompatActivity.RESULT_CANCELED);
                            finish();
                        });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (animateExit) {
            overridePendingTransition(R.anim.dummy, R.anim.slide_top_bottom_out);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_contact, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int i = item.getItemId();
        if (i == R.id.action_search) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                circleReveal(R.id.searchtoolbar, 1, true, true);
            else
                searchtollbar.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            searchView.requestFocus();
            item_search.expandActionView();
            return true;
        } else if (i == R.id.action_settings) {
            Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
            return true;
        }  else if (i == R.id.action_add) {
            SearchActivity.startSearchActivity(SelectContactActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void didTappedOnPositive(String text) {

            if (mode == MODE_ADD_TO_CONVERSATION) {
                showProgressDialog( getString(R.string.pick_friends_activity_prog_dialog_add_to_convo_message));
            }
            else if (mode == MODE_NEW_CONVERSATION) {
                showProgressDialog(getString(R.string.pick_friends_activity_prog_dialog_open_new_convo_message));
            }

        createAndOpenThread(text, users)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((thread, throwable) -> {
                                dismissProgressDialog();
                                finish();
                            });
    }

    @Override
    public void didTappedOnNegative() {

    }
}
