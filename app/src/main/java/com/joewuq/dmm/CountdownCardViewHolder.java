package com.joewuq.dmm;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joewuq.dmm.utility.Utility;

import org.joda.time.DateTime;

/**
 * Created by Joe Wu on 1/11/15.
 */
public class CountdownCardViewHolder extends RecyclerView.ViewHolder {

    private CardView cardView;
    private ViewGroup iconContainer;
    private ImageView notificationIcon;
    private ImageView repeatIcon;
    private ViewGroup content;
    private ImageView imageBackground;
    private TextView titleText;
    private TextView countdownText;
    private TextView daysText;
    private TextView dateText;
    private TextView descriptionText;

    public CountdownCardViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView;
        iconContainer = (ViewGroup) itemView.findViewById(R.id.ll_card_icon_container);
        notificationIcon = (ImageView) itemView.findViewById(R.id.iv_card_icon_notification);
        repeatIcon = (ImageView) itemView.findViewById(R.id.iv_card_icon_repeat);
        content = (ViewGroup) itemView.findViewById(R.id.ll_card_content);
        imageBackground = (ImageView) itemView.findViewById(R.id.iv_card_bg);
        titleText = (TextView) itemView.findViewById(R.id.tv_card_title);
        countdownText = (TextView) itemView.findViewById(R.id.tv_card_countdown);
        daysText = (TextView) itemView.findViewById(R.id.tv_card_days);
        dateText = (TextView) itemView.findViewById(R.id.tv_card_date);
        descriptionText = (TextView) itemView.findViewById(R.id.tv_card_description);
    }

    public void bind(Context context, CountdownModel model, boolean showIcons) {
        int color = context.getResources().getColor(Utility.getThemeColorResourceId(model.getThemeColor()));
        int colorDark = context.getResources().getColor(Utility.getThemeDarkColorResourceId(model.getThemeColor()));
        cardView.setCardBackgroundColor(color);


        // disable background image and darkening for now
        imageBackground.setImageResource(0);
        imageBackground.setVisibility(View.GONE);
        content.setBackgroundColor(context.getResources().getColor(R.color.transparent));

        // icon colors
        ColorStateList disabledStateList = new ColorStateList(new int[][] {new int[] {}}, new int[] {colorDark});
        ColorStateList enabledStateList = new ColorStateList(new int[][] {new int[] {}}, new int[] {context.getResources().getColor(R.color.text_light_secondary)});

        // always hide notification icon for now
        notificationIcon.setImageTintList(disabledStateList);
        notificationIcon.setVisibility(View.GONE);

        if (model.getRepeatMode() == CountdownModel.RepeatMode.NONE) {
            repeatIcon.setImageResource(R.drawable.ic_repeat_off_white_18dp);
            repeatIcon.setImageTintList(disabledStateList);
        } else {
            repeatIcon.setImageResource(R.drawable.ic_repeat_on_white_18dp);
            repeatIcon.setImageTintList(enabledStateList);
        }
        iconContainer.setVisibility(showIcons ? View.VISIBLE : View.GONE);

        // setup texts
        DateTime nextDate = model.getNextDate();
        Integer daysDiff = model.getDaysDiff(DateTime.now().withTime(0, 0, 0, 0));

        titleText.setText(model.getTitle());
        dateText.setText(nextDate.toString());

        if (daysDiff < 0) {
            countdownText.setText(String.valueOf(daysDiff));
            daysText.setText(context.getString(R.string.card_days_past));
        } else {
            countdownText.setText(String.valueOf(daysDiff));
            daysText.setText(context.getString(R.string.card_days_left));
        }

        // hide description is it's empty
        String description = model.getDescription();
        descriptionText.setText(description);
        descriptionText.setVisibility(description.equals("") ? View.GONE : View.VISIBLE);
    }

    public CardView getCardView() {
        return cardView;
    }
}
