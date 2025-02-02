package co.chatsdk.ui.threads;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.dao.User;
import co.chatsdk.core.events.EventType;
import co.chatsdk.core.events.NetworkEvent;
import co.chatsdk.core.interfaces.ThreadType;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.types.ConnectionType;
import co.chatsdk.core.utils.DisposableList;
import co.chatsdk.core.utils.StringChecker;
import co.chatsdk.ui.R;
import co.chatsdk.ui.main.BaseFragment;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

public abstract class ThreadsFragment extends BaseFragment {

    protected RecyclerView listThreads;
    protected EditText searchField;
    protected ThreadsListAdapter adapter;
    protected String filter;
    protected MenuItem addMenuItem;
    protected MenuItem searchMenuItem;

    private DisposableList disposableList = new DisposableList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        disposableList.add(ChatSDK.events().sourceOnMain()
                .filter(mainEventFilter())
                .subscribe(networkEvent -> {
                    if (tabIsVisible) {
                        reloadData();
                    }
                }));

        disposableList.add(ChatSDK.events().sourceOnMain()
                .filter(NetworkEvent.filterType(EventType.TypingStateChanged))
                .subscribe(networkEvent -> {
                    if (tabIsVisible) {
                        adapter.setTyping(networkEvent.thread, networkEvent.text);
                        adapter.notifyDataSetChanged();
                    }
                }));

        reloadData();

        mainView = inflater.inflate(activityLayout(), null);

        initViews();
        setFabOnClickListener(mainView);
        return mainView;
    }

    private void setFabOnClickListener(View view) {
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatSDK.ui().startSelectContactsActivity(getContext());
            }
        });
    }

    public void onQueryUpdate(String filter) {
        this.filter = filter;
        reloadData();
    }

    protected abstract Predicate<NetworkEvent> mainEventFilter ();

    protected  @LayoutRes int activityLayout () {
        return R.layout.chat_sdk_activity_threads;
    }

    public void initViews() {
        searchField = mainView.findViewById(R.id.search_field);
        listThreads = mainView.findViewById(R.id.list_threads);

        adapter = new ThreadsListAdapter(getActivity());

        listThreads.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        listThreads.setAdapter(adapter);

        Disposable d = adapter.onClickObservable().subscribe(thread -> {
            if(thread.getType() == ThreadType.Private1to1) {
                List<User> users = thread.getUsers();
                for(User u : users) {
                    if(!u.isMe()) {
                        ChatSDK.contact().addContact((User) u, ConnectionType.Contact).subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                        break;
                    }
                }
            }
            ChatSDK.ui().startChatActivityForID(getContext(), thread.getEntityID());
        });
    }

    protected boolean allowThreadCreation () {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate( R.menu.threads_menu_second, menu);

//        if (allowThreadCreation()) {
//            addMenuItem = menu.add(Menu.NONE, R.id.action_chat_sdk_add, 10, getString(R.string.thread_fragment_add_item_text));
//            addMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//            addMenuItem.setIcon(R.drawable.ic_plus);
//
//            searchMenuItem = menu.add(Menu.NONE, R.id.action_search, 9, "Search");
//            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//
//            SearchView searchView = null;
//            if (searchMenuItem != null) {
//                searchView = (SearchView) searchMenuItem.getActionView();
//            }
//            if (searchView != null) {
//                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//            }
//
//        }
    }

    // Override this in the subclass to handle the plus button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();

        if (searchField != null) {
            searchField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filter = searchField.getText().toString();
                    reloadData();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    public void clearData() {
        if (adapter != null) {
            adapter.clearData();
        }
    }

    public void setTabVisibility (boolean isVisible) {
        super.setTabVisibility(isVisible);
        reloadData();
    }

    @Override
    public void reloadData() {
        if (adapter != null) {
            adapter.clearData();
            List<Thread> threads = filter(getThreads());
            adapter.updateThreads(threads);
        }
    }

    protected abstract List<Thread> getThreads ();

    public List<Thread> filter (List<Thread> threads) {
        if (filter == null || filter.isEmpty()) {
            return threads;
        }

        List<Thread> filteredThreads = new ArrayList<>();
        for (Thread t : threads) {
            if (t.getDisplayName() != null && t.getDisplayName().toLowerCase().contains(filter.toLowerCase())) {
                filteredThreads.add(t);
            }
//            else {
//                for (User u : t.getUsers()) {
//                    if (u.getName() != null && u.getName().toLowerCase().contains(filter.toLowerCase())) {
//                        filteredThreads.add(t);
//                        break;
//                    }
//                }
//            }
        }
        return filteredThreads;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        disposableList.dispose();
    }

}
