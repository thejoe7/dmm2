package com.joewuq.dmm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toolbar;

/**
 * Created by joew on 12/18/14.
 */
public abstract class ToolbarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setActionBar(toolbar);
        }
    }

    protected abstract int getLayoutResourceId();
}
