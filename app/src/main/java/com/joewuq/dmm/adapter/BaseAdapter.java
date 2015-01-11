package com.joewuq.dmm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe Wu on 1/10/15.
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context context;
    protected ArrayList<T> objects;

    public BaseAdapter(Context context) {
        super();

        this.objects = new ArrayList<T>();
        this.context = context;
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void addObjects(List<T> list) {
        boolean modified = false;
        for (T object : list) {
            if (!objects.contains(object)) {
                objects.add(object);
                modified = true;
            }
        }

        if (modified) {
            sortObjects();
            notifyDataSetChanged();
        }
    }

    public void add(T object) {
        // filter duplicates
        if (!objects.contains(object)) {
            objects.add(object);
            sortObjects();
            notifyDataSetChanged();
        }
    }

    public void remove(T object) {
        objects.remove(object);
        notifyDataSetChanged();
    }

    protected void sortObjects() {
        // derived class should implement this to allow custom sorting
    }

    public T getItem(int position) {
        return objects.get(position);
    }

    public void clear() {
        objects.clear();
    }

}
