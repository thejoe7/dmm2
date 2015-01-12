package com.joewuq.dmm.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joewuq.dmm.CountdownModel;
import com.joewuq.dmm.R;
import com.joewuq.dmm.activity.DetailActivity;
import com.joewuq.dmm.adapter.CountdownCardAdapter;

import org.joda.time.DateTime;

/**
 * Created by Joe Wu on 1/11/15.
 */
public class ArchiveFragment extends RecyclerListFragment implements CountdownCardAdapter.OnItemClickListener {

    public static final String TAG = ArchiveFragment.class.getName();

    private static final int REQUEST_SHOW_COUNTDOWN_DETAIL = 100;

    private CountdownCardAdapter cardAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        cardAdapter = new CountdownCardAdapter(getActivity());
        cardAdapter.setOnItemClickListener(this);
        cardAdapter.add(new CountdownModel().setTitle("Christmas Day").setDate(new DateTime(2015, 12, 25, 0, 0)).setDescription("Merry Christmas to You!"));
        recyclerListView.setAdapter(cardAdapter);

        return view;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_archive;
    }

    @Override
    public void onItemClick(View view, int position) {
        DetailActivity.startActivityForResult(getActivity(), REQUEST_SHOW_COUNTDOWN_DETAIL, cardAdapter.getItem(position));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_SHOW_COUNTDOWN_DETAIL:
                if (resultCode == Activity.RESULT_OK) {
                    String uuid = data.getStringExtra(DetailActivity.EXTRA_COUNTDOWN_UUID);
                    boolean deleted = data.getBooleanExtra(DetailActivity.EXTRA_COUNTDOWN_DELETED, true);
                    if (deleted) {
                        cardAdapter.remove(uuid);
                    } else {
                        cardAdapter.refresh(uuid);
                    }
                }
        }
    }
}
