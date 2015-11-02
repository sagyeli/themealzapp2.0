package com.themealz.themealz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RestaurantsListActivity extends AppCompatActivity
        implements MealOptionListFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);
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
//            // In single-pane mode, simply start the detail activity
//            // for the selected item ID.
//            Intent detailIntent = new Intent(this, MealOptionDetailActivity.class);
//            detailIntent.putExtra(MealOptionDetailFragment.ARG_ITEM_ID, id);
//            startActivity(detailIntent);
//        }
    }
}
