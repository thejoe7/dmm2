package com.joewuq.dmm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joewuq.dmm.R;

/**
 * Created by Joe Wu on 1/11/15.
 */
public abstract class RecyclerListFragment extends Fragment {

    public static final String TAG = RecyclerListFragment.class.getName();

    protected RecyclerView recyclerListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getLayoutResourceId(), container, false);

        recyclerListView = (RecyclerView) view.findViewById(R.id.rv_list);
        recyclerListView.setHasFixedSize(true);
        recyclerListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerListView.setItemAnimator(new DefaultItemAnimator());

        recyclerListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // set on scroll elevation lifting
                if (getActivity() instanceof ActionBarActivity) {
                    ActionBarActivity activity = (ActionBarActivity) getActivity();
                    int elevation_resource_id = newState == RecyclerView.SCROLL_STATE_IDLE ? R.dimen.toolbar_elevation_normal : R.dimen.toolbar_elevation_lifted;
                    ActionBar bar = activity.getSupportActionBar();
                    if (bar != null) {
                        bar.setElevation(getResources().getDimension(elevation_resource_id));
                    }
                }

                onRecyclerListScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onRecyclerListScrolled(recyclerView, dx, dy);
            }
        });

        return view;
    }

    protected abstract int getLayoutResourceId();

    protected void onRecyclerListScrollStateChanged(RecyclerView recyclerView, int newState) {

    }

    protected void onRecyclerListScrolled(RecyclerView recyclerView, int dx, int dy) {

    }
}
