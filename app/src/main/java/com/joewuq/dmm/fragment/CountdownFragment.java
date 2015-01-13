package com.joewuq.dmm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.joewuq.dmm.R;
import com.joewuq.dmm.CountdownModel;
import com.joewuq.dmm.activity.DetailActivity;
import com.joewuq.dmm.adapter.CountdownCardAdapter;
import com.joewuq.dmm.manager.PreferencesManager;
import com.joewuq.dmm.utility.ThemeColor;
import com.joewuq.dmm.utility.Utility;
import com.melnykov.fab.FloatingActionButton;

import org.joda.time.DateTime;

/**
 * Created by Joe Wu on 12/30/14.
 */
public class CountdownFragment extends RecyclerListFragment implements CountdownCardAdapter.OnItemClickListener {

    public static final String TAG = CountdownFragment.class.getName();

    private CountdownCardAdapter cardAdapter;
    private FloatingActionButton fab;
    private int fabScrollThreshold;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        cardAdapter = new CountdownCardAdapter(getActivity());
        cardAdapter.setOnItemClickListener(this);

        cardAdapter.addObjects(PreferencesManager.getInstance().loadAllCountdownModels(getActivity()));

        recyclerListView.setAdapter(cardAdapter);

        fabScrollThreshold = getResources().getDimensionPixelOffset(R.dimen.fab_scroll_threshold) * 2;
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.startActivity(getActivity(), null);
            }
        });
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                float normal_elevation = getResources().getDimension(R.dimen.fab_elevation_lollipop_normal);
                float lifted_elevation = getResources().getDimension(R.dimen.fab_elevation_lollipop_lifted);
                float from_elevation = action == MotionEvent.ACTION_UP ? lifted_elevation : normal_elevation;
                float to_elevation = action == MotionEvent.ACTION_UP ? normal_elevation : lifted_elevation;
                if (v.getElevation() != to_elevation) {
                    Utility.getElevationAnimator(getActivity(), v, from_elevation, to_elevation, interpolator).start();
                }
                return false;
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
        DetailActivity.startActivity(getActivity(), cardAdapter.getItem(position));
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case REQUEST_SHOW_COUNTDOWN_DETAIL:
//                if (resultCode == Activity.RESULT_OK) {
//                    String uuid = data.getStringExtra(DetailActivity.EXTRA_COUNTDOWN_UUID);
//                    boolean deleted = data.getBooleanExtra(DetailActivity.EXTRA_COUNTDOWN_DELETED, true);
//                    if (deleted) {
//                        cardAdapter.remove(uuid);
//                    } else {
//                        cardAdapter.refresh(uuid);
//                    }
//                }
//        }
//    }

    private void generateDummyData() {
        int i = 1;
        for (ThemeColor c : ThemeColor.values()) {
            CountdownModel model = new CountdownModel().setTitle("Date " + i).setDate(new DateTime(2015, i, 25, 0, 0)).setDescription("Description No." + i).setThemeColor(c);
            PreferencesManager.getInstance().saveCountdownModel(getActivity(), model);
            i ++;
        }
    }
}
