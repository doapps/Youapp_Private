package me.doapps.fragments;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import java.util.ArrayList;

import me.doapps.adapters.Adapter_Playlist;
import me.doapps.beans.Channel_DTO;
import me.doapps.beans.PlayList_DTO;
import me.doapps.datasource.Channel_Datasource;
import me.doapps.youapps.R;
import me.doapps.youapps.YouApp;

public class Fragment_Menu extends Fragment implements Channel_Datasource.Interface_Playlists {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    private Adapter_Playlist adapter_playlist;
    private Channel_Datasource channel_datasource;


    public Fragment_Menu() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channel_datasource = new Channel_Datasource(getActivity());
        channel_datasource.getPlayList(getString(R.string.channel_id));
        channel_datasource.setInterface_playlists(this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        EasyTracker easyTracker = EasyTracker.getInstance(getActivity());
        easyTracker.send(MapBuilder
                .createEvent("MENU",
                        "CREANDO",
                        "SE CREO EL MENU",
                        null)
                .build());

        mDrawerListView = (ListView) inflater.inflate(
                R.layout.fragment_menu, container, false);

        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlayList_DTO channel_dto = (PlayList_DTO) parent.getAdapter().getItem(position);
                ((YouApp) getActivity()).setPlayList_dto(channel_dto);
                ((YouApp) getActivity()).setLpid(channel_dto.getId());
                ((YouApp) getActivity()).getOnSelectPlayList().onClick();
                mDrawerLayout.closeDrawers();

                EasyTracker easyTracker = EasyTracker.getInstance(getActivity());
                easyTracker.send(MapBuilder
                        .createEvent("MENU",
                                "SELECCION",
                                "PLAYLIST SELECCIONADA : " + channel_dto.getTitle(),
                                null)
                        .build());
            }
        });

        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (((YouApp) getActivity()).pager.getCurrentItem() == 1 || ((YouApp) getActivity()).pager.getCurrentItem() == 0 ) {
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
        }

        /*
        if(item.getItemId() == android.R.id.home){
        }
        */
        return super.onOptionsItemSelected(item);
    }

    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    public static interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(Channel_DTO channel_dto);
    }

    @Override
    public void onFinishLoad(Adapter_Playlist adapter_playlist, ArrayList<PlayList_DTO> playList_dtos) {
        mDrawerListView.setAdapter(adapter_playlist);
        /*if(playList_dtos.size()>0){
            ((YouApp)getActivity()).setPlayList_dto(playList_dtos.get(0));
            ((YouApp)getActivity()).setFlag(1);
        }*/
        try {
            ((YouApp) getActivity()).setPlayList_dto(playList_dtos.get(0));
            Log.e("item", playList_dtos.get(0).getDescription());

        } catch (NullPointerException e) {
            Log.e("Exception first", e.toString());
        }

    }


}
