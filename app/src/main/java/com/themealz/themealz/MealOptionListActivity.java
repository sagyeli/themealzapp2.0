package com.themealz.themealz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.TextView;


/**
 * An activity representing a list of Meal Options. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MealOptionDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MealOptionListFragment} and the item details
 * (if present) is a {@link MealOptionDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link MealOptionListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class MealOptionListActivity extends AppCompatActivity
        implements MealOptionListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Context mContext = this;

    private TextView mMainTitle;
    private Button pizzaSlice;
    private Button sushiSlice;
    private Button meatSlice;
    private Button falafelSlice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealoption_app_bar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.mealoption_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((MealOptionListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mealoption_list))
                    .setActivateOnItemClick(true);
        }

        mMainTitle = (TextView) findViewById(R.id.main_title);
        pizzaSlice = (Button) findViewById(R.id.pizza_slice);
        sushiSlice = (Button) findViewById(R.id.sushi_slice);
        meatSlice = (Button) findViewById(R.id.meat_slice);
        falafelSlice = (Button) findViewById(R.id.falafel_slice);

        mMainTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
        pizzaSlice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onItemSelected("5613bbe719bd6b4f232e6bfb");
            }
        });
        sushiSlice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onItemSelected("5613b9d519bd6b4f232e6bf1");
            }
        });
        meatSlice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onItemSelected("561af3f2721bc74808fc31a2");
            }
        });
        falafelSlice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onItemSelected("5613bdf519bd6b4f232e6c0d");
            }
        });

        Animation fadeInAnimation = new AlphaAnimation(0, 1);
        fadeInAnimation.setInterpolator(new DecelerateInterpolator());
        fadeInAnimation.setStartOffset(1000);
        fadeInAnimation.setDuration(750);
        mMainTitle.setAnimation(fadeInAnimation);


        Animation marginAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                final float scale = mContext.getResources().getDisplayMetrics().density;
                int factor = (int)((236 * scale + 0.5f) * (1 - interpolatedTime)) + 4;
                ViewGroup.MarginLayoutParams layoutParams;

                layoutParams = (ViewGroup.MarginLayoutParams) pizzaSlice.getLayoutParams();
                layoutParams.setMargins(factor, factor, factor, factor);
                pizzaSlice.setLayoutParams(layoutParams);

                layoutParams = (ViewGroup.MarginLayoutParams) sushiSlice.getLayoutParams();
                layoutParams.setMargins(factor, factor, factor, factor);
                sushiSlice.setLayoutParams(layoutParams);

                layoutParams = (ViewGroup.MarginLayoutParams) meatSlice.getLayoutParams();
                layoutParams.setMargins(factor, factor, factor, factor);
                meatSlice.setLayoutParams(layoutParams);

                layoutParams = (ViewGroup.MarginLayoutParams) falafelSlice.getLayoutParams();
                layoutParams.setMargins(factor, factor, factor, factor);
                falafelSlice.setLayoutParams(layoutParams);
            }
        };
        marginAnimation.setStartOffset(1750);
        marginAnimation.setDuration(1250); // in ms
        findViewById(android.R.id.content).startAnimation(marginAnimation);
    }

    /**
     * Callback method from {@link MealOptionListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
//        if (mTwoPane) {
//            // In two-pane mode, show the detail view in this activity by
//            // adding or replacing the detail fragment using a
//            // fragment transaction.
//            Bundle arguments = new Bundle();
//            arguments.putString(MealOptionDetailFragment.ARG_ITEM_ID, id);
//            MealOptionDetailFragment fragment = new MealOptionDetailFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.mealoption_detail_container, fragment)
//                    .commit();
//
//        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, MealOptionDetailActivity.class);
            detailIntent.putExtra(MealOptionDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
//        }
    }
}
