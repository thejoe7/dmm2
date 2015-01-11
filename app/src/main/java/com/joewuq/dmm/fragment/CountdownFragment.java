package com.joewuq.dmm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joewuq.dmm.DividerItemDecoration;
import com.joewuq.dmm.R;
import com.joewuq.dmm.adapter.CountdownCardAdapter;

/**
 * Created by Joe Wu on 12/30/14.
 */
public class CountdownFragment extends Fragment {

    private RecyclerView recyclerView;
    private CountdownCardAdapter cardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_countdown, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);
        // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        cardAdapter = new CountdownCardAdapter(getActivity());
        recyclerView.setAdapter(cardAdapter);

        return view;
    }
}
