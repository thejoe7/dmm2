package com.joewuq.dmm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joewuq.dmm.R;
import com.joewuq.dmm.CountdownModel;
import com.joewuq.dmm.adapter.CountdownCardAdapter;
import com.joewuq.dmm.utility.ThemeColor;

import org.joda.time.DateTime;

/**
 * Created by Joe Wu on 12/30/14.
 */
public class CountdownFragment extends Fragment implements CountdownCardAdapter.OnItemClickListener {

    public final static String TAG = CountdownFragment.class.getName();

    private RecyclerView recyclerView;
    private CountdownCardAdapter cardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_countdown, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        cardAdapter = new CountdownCardAdapter(getActivity());
        cardAdapter.setOnItemClickListener(this);
        for (ThemeColor c : ThemeColor.values()) {
            cardAdapter.add(new CountdownModel().setTitle("Christmas Day").setDate(new DateTime(2015, 12, 25, 0, 0)).setDescription("Merry Christmas to You!").setThemeColor(c));
        }
        recyclerView.setAdapter(cardAdapter);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                ActionBarActivity activity = (ActionBarActivity) getActivity();
                int elevation_resource_id = newState == RecyclerView.SCROLL_STATE_IDLE ? R.dimen.toolbar_elevation_normal : R.dimen.toolbar_elevation_lifted;
                activity.getSupportActionBar().setElevation(getResources().getDimension(elevation_resource_id));
            }
        });

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
