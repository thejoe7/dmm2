package com.joewuq.dmm;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import it.neokree.materialtabs.MaterialTabHost;


public class MainActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MaterialTabHost tabHost = (MaterialTabHost) this.findViewById(R.id.th_icons);
        tabHost.addTab(tabHost.newTab().setIcon(getResources().getDrawable(R.drawable.ic_notifications_on_white_24dp)));
        tabHost.addTab(tabHost.newTab().setIcon(getResources().getDrawable(R.drawable.ic_notifications_off_white_24dp)));
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
}
