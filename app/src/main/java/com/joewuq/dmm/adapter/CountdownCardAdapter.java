package com.joewuq.dmm.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joewuq.dmm.CountdownModel;
import com.joewuq.dmm.R;
import com.joewuq.dmm.utility.Utility;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Joe Wu on 1/10/15.
 */
public class CountdownCardAdapter extends BaseAdapter<CountdownModel, CountdownCardAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ViewGroup content;
        ImageView imageBackground;
        TextView titleText;
        TextView countdownText;
        TextView daysText;
        TextView dateText;
        TextView descriptionText;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            content = (ViewGroup) itemView.findViewById(R.id.ll_card_content);
            imageBackground = (ImageView) itemView.findViewById(R.id.iv_card_bg);
            titleText = (TextView) itemView.findViewById(R.id.tv_card_title);
            countdownText = (TextView) itemView.findViewById(R.id.tv_card_countdown);
            daysText = (TextView) itemView.findViewById(R.id.tv_card_days);
            dateText = (TextView) itemView.findViewById(R.id.tv_card_date);
            descriptionText = (TextView) itemView.findViewById(R.id.tv_card_description);
        }
    }

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_countdown_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CountdownModel model = getItem(position);
        int color = context.getResources().getColor(Utility.getThemeColorResourceId(model.getThemeColor()));
        holder.cardView.setCardBackgroundColor(color);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.cardView, position);
                }
            }
        });

        // disable background image and darkening for now
        holder.imageBackground.setImageResource(0);
        holder.imageBackground.setVisibility(View.GONE);
        holder.content.setBackgroundColor(context.getResources().getColor(R.color.transparent));

        // set up subviews
        DateTime nextDate = model.getNextDate();
        Integer daysDiff = model.getDaysDiff(DateTime.now().withTime(0, 0, 0, 0));

        holder.titleText.setText(model.getTitle());
        holder.dateText.setText(nextDate.toString());

        if (daysDiff < 0) {
            holder.countdownText.setText(String.valueOf(daysDiff));
            holder.daysText.setText(context.getString(R.string.card_days_past));
        } else {
            holder.countdownText.setText(String.valueOf(daysDiff));
            holder.daysText.setText(context.getString(R.string.card_days_left));
        }

        // hide description is it's empty
        String description = model.getDescription();
        holder.descriptionText.setText(description);
        holder.descriptionText.setVisibility(description.equals("") ? View.GONE : View.VISIBLE);
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
