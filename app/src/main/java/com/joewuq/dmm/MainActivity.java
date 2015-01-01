package com.joewuq.dmm;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ToolbarActivity implements MaterialTabListener {

    private TextView toolbarTitle;
    private ViewPager viewPager;
    private MaterialTabHost tabHost;
    private MainViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            return new CountdownFragment();
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
                    return getResources().getDrawable(R.drawable.ic_notifications_on_white_24dp);
                case 1:
                    return getResources().getDrawable(R.drawable.ic_notifications_off_white_24dp);
                default:
                    return null;
            }
        }
    }
}
