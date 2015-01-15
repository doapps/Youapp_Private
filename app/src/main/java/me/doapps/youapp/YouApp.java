package me.doapps.youapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.shamanland.fab.FloatingActionButton;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import me.doapps.beans.API_DTO;
import me.doapps.beans.Channel_DTO;
import me.doapps.beans.PlayList_DTO;
import me.doapps.datasource.Channel_Datasource;
import me.doapps.datasource.Video_DataSource;
import me.doapps.fragments.Fragment_Menu;
import me.doapps.fragments.Fragment_Video;
import me.doapps.fragments.Fragment_Web;


public class YouApp extends ActionBarActivity {

    private String[] TABS = {"WEB", "CANAL"};

    private DrawerLayout mDrawerLayout;
    private Fragment_Menu mFragmentMenu;
    private CharSequence mTitle;
    private Video_DataSource video_dataSource;
    public ViewPager pager;

    private API_DTO api_dto;
    private PlayList_DTO playList_dto;
    private int flag = 0;

    private String url = "";
    private String lpid;

    public String getLpid() {
        return lpid;
    }

    public void setLpid(String lpid) {
        this.lpid = lpid;
    }

    private ArrayList<PlayList_DTO> playList_dtos;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setPlayList_dto(PlayList_DTO playList_dto) {
        this.playList_dto = playList_dto;
    }

    public PlayList_DTO getPlayList_dto() {
        return playList_dto;
    }

    public void setPlayList_dtos(ArrayList<PlayList_DTO> playList_dtos){
        this.playList_dtos = playList_dtos;
    }
    public ArrayList<PlayList_DTO> getPlayList_dtos(){
        return playList_dtos;
    }

    private InterfaceFirst interfaceFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youapp);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            setUrl(getResources().getString(R.string.url_web));
        } else {
            setUrl(extras.getString("data"));
        }


        mFragmentMenu = (Fragment_Menu) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFragmentMenu.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        /**
         *
         */

        /**
         *
         */
        FloatingActionButton fab_share = (FloatingActionButton) findViewById(R.id.fab_share);
        fab_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/search?q=cristian%20alex%20palomino%20rivera");
                startActivity(Intent.createChooser(intent, "Compartir"));
            }
        });

        FragmentPagerAdapter adapter = new Tab_Adapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        if (pager.getCurrentItem() == 0) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.youapp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/search?q=cristian%20alex%20palomino%20rivera");
            startActivity(Intent.createChooser(intent, "Compartir"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public API_DTO getApi_dto() {
        return api_dto;
    }

    public void setApi_dto(API_DTO api_dto) {
        this.api_dto = api_dto;
    }

    /**
     * Adapter
     */
    class Tab_Adapter extends FragmentPagerAdapter {
        public Tab_Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return Fragment_Web.newInstance();
            } else {
                return Fragment_Video.newInstance();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TABS[position];
        }

        @Override
        public int getCount() {
            return TABS.length;
        }
    }

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    public void setmDrawerLayout(DrawerLayout mDrawerLayout) {
        this.mDrawerLayout = mDrawerLayout;
    }


    /****/
    public interface OnSelectPlayList {
        void onClick();
    }

    private OnSelectPlayList onSelectPlayList;

    public OnSelectPlayList getOnSelectPlayList() {
        return onSelectPlayList;
    }

    public void setOnSelectPlayList(OnSelectPlayList onSelectPlayList) {
        this.onSelectPlayList = onSelectPlayList;
    }


    public interface InterfaceFirst{
        void getFirst(PlayList_DTO playList_dto1);
    }
    public void setInterfaceFirst(InterfaceFirst interfaceFirst){
        this.interfaceFirst = interfaceFirst;
    }

}
