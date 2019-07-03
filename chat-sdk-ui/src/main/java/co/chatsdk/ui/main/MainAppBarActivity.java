package co.chatsdk.ui.main;

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
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import co.chatsdk.core.Tab;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.ui.R;
import co.chatsdk.ui.threads.PrivateThreadsFragment;

public class MainAppBarActivity extends MainActivity {
    protected TabLayout tabLayout;
    protected ViewPager viewPager;
    protected PagerAdapterTabs adapter;
    private Menu search_menu;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSearchtollbar();
    }

    protected @LayoutRes
    int activityLayout() {
        return R.layout.chat_sdk_activity_view_pager;
    }

    protected void initViews() {
        setContentView(activityLayout());
        viewPager = findViewById(R.id.pager);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        searchView = findViewById(R.id.search_view);

        // Only creates the adapter if it wasn't initiated already
        if (adapter == null) {
            adapter = new PagerAdapterTabs(getSupportFragmentManager());
        }

        final List<Tab> tabs = adapter.getTabs();
        for (Tab tab : tabs) {
            tabLayout.addTab(tabLayout.newTab().setText(tab.title));
        }

        ((BaseFragment) tabs.get(0).fragment).setTabVisibility(true);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                updateLocalNotificationsForTab();

                // We mark the tab as visible. This lets us be more efficient with updates
                // because we only
                for(int i = 0; i < tabs.size(); i++) {
                    ((BaseFragment) tabs.get(i).fragment).setTabVisibility(i == tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager.setOffscreenPageLimit(3);
    }

    Toolbar toolbar, searchtollbar;
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
                    ((PrivateThreadsFragment) ChatSDK.ui().privateThreadsFragment()).onQueryUpdate("");
                    searchView.clearFocus();
                    tabLayout.setVisibility(View.VISIBLE);
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
                ((PrivateThreadsFragment) ChatSDK.ui().privateThreadsFragment()).onQueryUpdate(query);
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

    public void updateLocalNotificationsForTab () {
        TabLayout.Tab tab = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
        ChatSDK.ui().setLocalNotificationHandler(thread -> showLocalNotificationsForTab(tab));
    }

    public boolean showLocalNotificationsForTab (TabLayout.Tab tab) {
        // Don't show notifications on the threads tabs
        Tab t = adapter.getTabs().get(tab.getPosition());

        Class privateThreadsFragmentClass = ChatSDK.ui().privateThreadsFragment().getClass();

        return !t.fragment.getClass().isAssignableFrom(privateThreadsFragmentClass);
    }

    public void clearData () {
        for(Tab t : adapter.getTabs()) {
            if(t.fragment instanceof BaseFragment) {
                ((BaseFragment) t.fragment).clearData();
            }
        }
    }

    public void reloadData () {
        for(Tab t : adapter.getTabs()) {
            if(t.fragment instanceof BaseFragment) {
                ((BaseFragment) t.fragment).safeReloadData();
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.threads_menu_second, menu);
//
//        MenuItem item = menu.findItem(R.id.action_search);
////        searchView.setMenuItem(item);
//
//
//        return true;
//    }

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
          tabLayout.setVisibility(View.GONE);
          searchView.requestFocus();
          item_search.expandActionView();
            return true;
        } else if (i == R.id.action_settings) {
            Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.threads_menu_second, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.contact_developer) {
//
//            String emailAddress = ChatSDK.config().contactDeveloperEmailAddress;
//            String subject = ChatSDK.config().contactDeveloperEmailSubject;
//            String dialogTitle = ChatSDK.config().contactDeveloperDialogTitle;
//
//            if(StringUtils.isNotEmpty(emailAddress))
//            {
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto", emailAddress, null));
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
//                startActivity(Intent.createChooser(emailIntent, dialogTitle));
//            }
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
