package com.themealz.themealz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class MealOptionFlavorsActivity extends Activity
        implements View.OnClickListener {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_LAST_ITEM_ID = "last_item_id";
    public static final String HAS_REAL_CHILDREN = "has_real_children";

    private Context mContext = this;

    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_option_flavors);

        mSubmitButton = (Button) findViewById(R.id.submit_button);

        mSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent detailIntent;

        if (getIntent().getBooleanExtra(HAS_REAL_CHILDREN, false)) {
            detailIntent = new Intent(mContext, MealOptionDetailActivity.class);
            detailIntent.putExtra(ARG_ITEM_ID, getIntent().getStringExtra(ARG_ITEM_ID));
        }
        else {
            detailIntent = new Intent(mContext, RestaurantsListActivity.class);
            detailIntent.putExtra(ARG_LAST_ITEM_ID, getIntent().getStringExtra(ARG_ITEM_ID));
        }
        startActivity(detailIntent);
    }
}
