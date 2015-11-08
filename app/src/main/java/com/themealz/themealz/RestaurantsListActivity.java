package com.themealz.themealz;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RestaurantsListActivity extends AppCompatActivity
        implements RestaurantsListFragment.Callbacks {

    private TextView mRestaurantsListTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);


        mRestaurantsListTitle = (TextView) findViewById(R.id.restaurants_list_title);

        mRestaurantsListTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
//        ((RestaurantsListFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.restaurants_list))
//                .setActivateOnItemClick(true);
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Callback method from {@link RestaurantsListFragment.Callbacks}
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
//            // In single-pane mode, simply start the detail activity
//            // for the selected item ID.
//            Intent detailIntent = new Intent(this, MealOptionDetailActivity.class);
//            detailIntent.putExtra(MealOptionDetailFragment.ARG_ITEM_ID, id);
//            startActivity(detailIntent);
//        }
    }
}
