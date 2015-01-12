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
import com.melnykov.fab.FloatingActionButton;

import org.joda.time.DateTime;

/**
 * Created by Joe Wu on 12/30/14.
 */
public class CountdownFragment extends RecyclerListFragment implements CountdownCardAdapter.OnItemClickListener {

    public final static String TAG = CountdownFragment.class.getName();

    private CountdownCardAdapter cardAdapter;
    private FloatingActionButton fab;
    private int fabScrollThreshold;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        cardAdapter = new CountdownCardAdapter(getActivity());
        cardAdapter.setOnItemClickListener(this);
        for (ThemeColor c : ThemeColor.values()) {
            cardAdapter.add(new CountdownModel().setTitle("Christmas Day").setDate(new DateTime(2015, 12, 25, 0, 0)).setDescription("Merry Christmas to You!").setThemeColor(c));
        }
        recyclerListView.setAdapter(cardAdapter);

        fabScrollThreshold = getResources().getDimensionPixelOffset(R.dimen.fab_scroll_threshold) * 2;
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountdownActivity(null);
            }
        });

        return view;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_countdown;
    }

    @Override
    protected void onRecyclerListScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onRecyclerListScrolled(recyclerView, dx, dy);
        boolean isSignificant = Math.abs(dy) > fabScrollThreshold;
        if (isSignificant) {
            if (dy > 0) {
                fab.hide();
            } else {
                fab.show();
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        showCountdownActivity(cardAdapter.getItem(position));
    }

    private void showCountdownActivity(CountdownModel model) {

    }
}
