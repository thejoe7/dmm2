package com.joewuq.dmm.activity;

import android.app.ActivityManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.joewuq.dmm.fragment.ArchiveFragment;
import com.joewuq.dmm.fragment.CountdownFragment;
import com.joewuq.dmm.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ToolbarActivity implements MaterialTabListener {

    public static final String TAG = MainActivity.class.getName();

    private static final String STATE_VIEW_PAGER_POSITION = "VIEW_PAGER_POSITION";

    private TextView toolbarTitle;
    private ViewPager viewPager;
    private MaterialTabHost tabHost;
    private MainViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setup visual effects in Overview/Recent screen
        setTaskDescription(new ActivityManager.TaskDescription(
                getResources().getString(R.string.app_name),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_mono),
                getResources().getColor(R.color.dmm_primary)
        ));

        toolbarTitle = (TextView) findViewById(R.id.tv_toolbar_title);

        tabHost = (MaterialTabHost) findViewById(R.id.th_pager_icons);
        viewPager = (ViewPager) findViewById(R.id.vp_main);
        viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabHost.setSelectedNavigationItem(position);
                toolbarTitle.setText(viewPagerAdapter.getPageTitle(position));
            }
        });

        for (int i = 0; i < viewPagerAdapter.getCount(); i ++) {
            tabHost.addTab(tabHost.newTab().setIcon(viewPagerAdapter.getPageIcon(i)).setTabListener(this));
        }

        // restore activity saved state
        if (savedInstanceState != null) {
            int viewPagerPosition = savedInstanceState.getInt(STATE_VIEW_PAGER_POSITION, 0);
            viewPager.setCurrentItem(viewPagerPosition, true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_VIEW_PAGER_POSITION, viewPager.getCurrentItem());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        final int position = tab.getPosition();
        viewPager.setCurrentItem(position);
        toolbarTitle.setText(viewPagerAdapter.getPageTitle(position));
    }

    @Override
    public void onTabReselected(MaterialTab tab) {
        // do nothing
    }

    @Override
    public void onTabUnselected(MaterialTab tab) {
        // do nothing
    }

    private class MainViewPagerAdapter extends FragmentStatePagerAdapter {

        public MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CountdownFragment();
                case 1:
                    return new ArchiveFragment();
                default:
                    Log.w(TAG, "MainViewPagerAdapter getItem() with invalid postion " + position + ".");
                    return new CountdownFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.toolbar_title_countdown);
                case 1:
                    return getResources().getString(R.string.toolbar_title_archive);
                default:
                    return getResources().getString(R.string.app_name);
            }
        }

        public Drawable getPageIcon(int position) {
            switch (position) {
                case 0:
                    return getResources().getDrawable(R.drawable.ic_today_white_24dp);
                case 1:
                    return getResources().getDrawable(R.drawable.ic_archive_white_24dp);
                default:
                    return null;
            }
        }
    }
}
