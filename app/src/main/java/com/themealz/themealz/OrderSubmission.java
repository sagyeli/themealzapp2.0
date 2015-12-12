package com.themealz.themealz;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderSubmission extends Activity {

    private TextView mMainTitle;
    private ImageView mOrderSubmissionRestaurantLogo;
    private TextView mOrderSubmissionRestaurantName;
    private TextView mOrderSubmissionInfo;
    private TextView mOrderSubmissionPriceLabel;
    private TextView mOrderSubmissionPriceValue;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_submission);

        mMainTitle = (TextView) findViewById(R.id.main_title);
        mOrderSubmissionRestaurantLogo = (ImageView) findViewById(R.id.order_submission_restaurant_logo);
        mOrderSubmissionRestaurantName = (TextView) findViewById(R.id.order_submission_restaurant_name);
        mOrderSubmissionInfo = (TextView) findViewById(R.id.order_submission_info);
        mOrderSubmissionPriceLabel = (TextView) findViewById(R.id.order_submission_price_label);
        mOrderSubmissionPriceValue = (TextView) findViewById(R.id.order_submission_price_value);
        mSubmitButton = (Button) findViewById(R.id.submit_button);

        mMainTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
        mOrderSubmissionRestaurantName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
        mOrderSubmissionInfo.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
        mOrderSubmissionPriceLabel.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
        mOrderSubmissionPriceValue.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
        mSubmitButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));

        mOrderSubmissionRestaurantName.setText(((TheMealzApplication) getApplication()).getSelectedMeal().get("restaurant_name"));
        ArrayList<String> info = ((TheMealzApplication) getApplication()).getMealOptionsTitlesArrayList();
        mOrderSubmissionInfo.setText(TextUtils.join("\n", info.toArray(new String[info.size()])));
        mOrderSubmissionPriceValue.setText(((TheMealzApplication) getApplication()).getSelectedMeal().get("price") + " ש\"ח");
    }

}
