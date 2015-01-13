package com.joewuq.dmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joewuq.dmm.CountdownCardViewHolder;
import com.joewuq.dmm.CountdownModel;
import com.joewuq.dmm.R;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Joe Wu on 1/10/15.
 */
public class CountdownCardAdapter extends BaseAdapter<CountdownModel, CountdownCardViewHolder> {

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public CountdownCardAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public CountdownCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_countdown_card, parent, false);
        return new CountdownCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CountdownCardViewHolder holder, final int position) {
        CountdownModel model = getItem(position);
        holder.bind(context, model, true);
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.getCardView(), position);
                }
            }
        });
    }

    @Override
    protected void sortObjects() {
        Collections.sort(objects, new Comparator<CountdownModel>() {
            @Override
            public int compare(CountdownModel lhs, CountdownModel rhs) {
                return lhs.getNextDate().compareTo(rhs.getNextDate());
            }
        });
    }

    public CountdownModel getItem(String uuid) {
        for (CountdownModel o : objects) {
            if (o.getUuid().equals(uuid)) {
                return o;
            }
        }
        return null;
    }

    public void remove(String uuid) {
        CountdownModel object = getItem(uuid);
        if (object != null) {
            remove(object);
        }
    }

    public void refresh(String uuid) {
        CountdownModel model = getItem(uuid);
        if (model == null) {
            // TODO: add new model into this.objects
        } else {
            // TODO: update model from the preference
        }
        notifyDataSetChanged();
    }

}
