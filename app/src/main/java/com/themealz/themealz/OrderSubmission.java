package com.themealz.themealz;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class OrderSubmission extends Activity {

    private TextView mMainTitle;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_submission);

        mMainTitle = (TextView) findViewById(R.id.main_title);
        mSubmitButton = (Button) findViewById(R.id.submit_button);

        mMainTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
        mSubmitButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
    }

}
