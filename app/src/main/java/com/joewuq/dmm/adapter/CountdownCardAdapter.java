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
import com.joewuq.dmm.utility.ThemeColor;
import com.joewuq.dmm.utility.Utility;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Joe Wu on 1/10/15.
 */
public class CountdownCardAdapter extends RecyclerView.Adapter<CountdownCardAdapter.ViewHolder> {

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

    private Context context;
    private ArrayList<CountdownModel> models;

    public CountdownCardAdapter(Context context) {
        super();
        this.context = context;
        models = new ArrayList<CountdownModel>();
        for (ThemeColor c : ThemeColor.values()) {
            models.add(new CountdownModel().setTitle("Christmas Day").setDate(new DateTime(2015, 12, 25, 0, 0)).setDescription("Merry Christmas to You!").setThemeColor(c));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_countdown_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CountdownModel model = models.get(position);
        int color = context.getResources().getColor(Utility.getThemeColorResourceId(model.getThemeColor()));
        holder.cardView.setCardBackgroundColor(color);

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
    public int getItemCount() {
        return models.size();
    }
}
