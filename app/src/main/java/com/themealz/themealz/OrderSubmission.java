package com.themealz.themealz;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderSubmission extends Activity {

    private TextView mMainTitle;
    private ImageView mOrderSubmissionRestaurantLogo;
    private TextView mOrderSubmissionRestaurantName;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_submission);

        mMainTitle = (TextView) findViewById(R.id.main_title);
        mOrderSubmissionRestaurantLogo = (ImageView) findViewById(R.id.order_submission_restaurant_logo);
        mOrderSubmissionRestaurantName = (TextView) findViewById(R.id.order_submission_restaurant_name);
        mSubmitButton = (Button) findViewById(R.id.submit_button);

        mMainTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
        mOrderSubmissionRestaurantName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
        mSubmitButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));

        mOrderSubmissionRestaurantName.setText(((TheMealzApplication) getApplication()).getSelectedMeal().get("restaurant_name"));
    }

}
