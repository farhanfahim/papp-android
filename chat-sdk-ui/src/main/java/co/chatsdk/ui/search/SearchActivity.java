/*
 * Created by Itzik Braun on 12/3/2015.
 * Copyright (c) 2015 deluge. All rights reserved.
 *
 * Last Modification at: 3/12/15 4:27 PM
 */

package co.chatsdk.ui.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import co.chatsdk.core.dao.User;
import co.chatsdk.core.interfaces.UserListItem;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.types.ConnectionType;
import co.chatsdk.core.types.SearchActivityType;
import co.chatsdk.core.utils.DisposableList;
import co.chatsdk.ui.R;
import co.chatsdk.ui.contacts.UsersListAdapter;
import co.chatsdk.ui.main.BaseActivity;
import io.reactivex.Completable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by braunster on 29/06/14.
 */
public class SearchActivity extends BaseActivity {

    protected FloatingActionButton addContactsButton;
    protected RecyclerView recyclerView;
    protected UsersListAdapter adapter;
    private Menu search_menu;
    private SearchView searchView;

    protected DisposableList disposableList = new DisposableList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(activityLayout());
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setSearchtollbar();
        initViews();


    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    protected @LayoutRes int activityLayout() {
        return R.layout.chat_sdk_activity_search;
    }

    protected void initViews() {
        addContactsButton = findViewById(R.id.chat_sdk_btn_add_contacts);
        recyclerView = findViewById(R.id.chat_sdk_list_search_results);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new UsersListAdapter(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Listening to key press - if they click the ok button on the keyboard
        // we start the search


        adapter.getItemClicks().subscribe(item -> adapter.toggleSelection(item));

        addContactsButton.setOnClickListener(v -> {

            if (adapter.getSelectedCount() == 0)
            {
                showToast(getString(R.string.search_activity_no_contact_selected_toast));
                return;
            }

            ArrayList<Completable> completables = new ArrayList<>();

            for (UserListItem u : adapter.getSelectedUsers()) {
                if (u instanceof User && !((User) u).isMe()) {
                    completables.add(ChatSDK.contact().addContact((User) u, ConnectionType.Contact));
                }
            }

            final ProgressDialog dialog = new ProgressDialog(SearchActivity.this);
            dialog.setMessage(getString(R.string.alert_save_contact));
            dialog.show();

            Completable.merge(completables)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        showToast(adapter.getSelectedCount() + " " + getString(R.string.search_activity_user_added_as_contact_after_count_toast));

                        disposableList.dispose();

                        dialog.dismiss();
                        finish();
                    }, throwable -> {
                        showToast(throwable.getLocalizedMessage());
                        dialog.dismiss();
                    });
        });

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
//                    searchParticipants("");
                    searchView.clearFocus();

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
//                callSearch(newText);

                return true;
            }

            public void callSearch(String query) {
                //Do searching
                searchParticipants(query);
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

    private void searchParticipants(String keyword) {
        if (keyword.isEmpty())
        {
            showToast(getString(R.string.search_activity_no_text_input_toast));
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(SearchActivity.this);
        dialog.setMessage(getString(R.string.search_activity_prog_dialog_init_message));
        dialog.show();

        // Clear the list of users
        adapter.clear();

        final List<UserListItem> users = new ArrayList<>();

        final List<User> existingContacts = ChatSDK.contact().contacts();

        ChatSDK.search().usersForIndex(keyword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposableList.add(d);
                    }

                    @Override
                    public void onNext(@NonNull User user) {

                        if (!existingContacts.contains(user) && !user.isMe()) {
                            users.add(user);
                            adapter.setUsers(users, true);
                            hideSoftKeyboard(SearchActivity.this);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showToast(e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                        if (users.size() == 0) {
                            showToast(getString(R.string.search_activity_no_user_found_toast));
                        }
                    }
                });
    }

    public static void startSearchActivity (final Context context) {
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            final List<SearchActivityType> activities = new ArrayList<>(ChatSDK.ui().getSearchActivities());
            activities.add(new SearchActivityType(ChatSDK.ui().getSearchActivity(), context.getString(R.string.search_with_name)));

            if (activities.size() == 1) {
                ChatSDK.ui().startActivity(context, activities.get(0).className);
                return;
            }

            String [] items = new String [activities.size()];
            int i = 0;

            for (SearchActivityType activity : activities) {
                items[i++] = activity.title;
            }

            builder.setTitle(context.getString(R.string.search)).setItems(items, (dialogInterface, i1) -> {
                // Launch the appropriate context
                ChatSDK.ui().startActivity(context, activities.get(i1).className);
            });

            builder.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.threads_menu_second, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
