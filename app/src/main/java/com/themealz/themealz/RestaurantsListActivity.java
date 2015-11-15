package com.themealz.themealz;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;

public class RestaurantsListActivity extends AppCompatActivity
        implements RestaurantsListFragment.Callbacks {

    private RestaurantsListActivity mContext = this;

    private TextView mRestaurantsListTitle;
    private TableLayout mRestaurantsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);


        mRestaurantsListTitle = (TextView) findViewById(R.id.restaurants_list_title);
        mRestaurantsTable = (TableLayout) findViewById(R.id.restaurants_table);

        mRestaurantsListTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));

        new DataRequestor().execute("");

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

    private class DataRequestor extends AsyncTask<String, Void, String> {
        private JSONArray ja;

        private void addItemToRow(String text, TableRow tableRow, HashMap properties) {
            TextView tv = new TextView(mContext);
            tv.setText(text);
            tv.setTextColor(Color.WHITE);
            tv.setPadding(10, 10, 10, 10);

            if (properties != null) {
                if (properties.containsKey("textColor")) {
                    tv.setTextColor(getResources().getColor((Integer) properties.get("textColor")));
                }
                if (properties.containsKey("textSize")) {
                    tv.setTextSize((Float) properties.get("textSize"));
                }
            }

            tableRow.addView(tv);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://themealz.com/api/restaurants");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // gets the server json data
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(
                                urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(line);
                }

                ja = new JSONArray(stringBuilder.toString());
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            if (ja == null || ja.length() == 0) {
                return;
            }

//            List<RestaurantItem> items = new ArrayList<RestaurantItem>();
//            mRestaurantsTable.removeAllViews();

            for (int i = 0 ; i < ja.length() ; i++) {
                //                    items.add(new RestaurantItem(ja.getJSONObject(i).getString("_id"), ja.getJSONObject(i).getString("name"), ja.getJSONObject(i).getString("info")));

                TableRow tr = new TableRow(mContext);
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                try {
                    addItemToRow(Integer.toString(i + 1), tr, new HashMap<String, Object>() {{
                        put("textSize", 30f);
                        put("textColor", R.color.default_text_color);
                    }});

                    addItemToRow(ja.getJSONObject(i).getString("name"), tr, null);
                    addItemToRow(new DecimalFormat("##.##").format(Math.random() * 100) + " ש\"ח", tr, null);
                    addItemToRow(new DecimalFormat("##").format(Math.random() * 60) + " דק'", tr, null);

                    RatingBar rb = new RatingBar(mContext, null, android.R.attr.ratingBarStyleSmall);
                    LayerDrawable stars = ((LayerDrawable) rb.getProgressDrawable());
                    stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.yellow_dark), PorterDuff.Mode.SRC_ATOP);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 21, getResources().getDisplayMetrics())), 0, 0);
                    rb.setLayoutParams(params);
                    rb.setNumStars(5);
                    rb.setStepSize(0.5f);
                    rb.setRating(i % 2 == 0 ? 5 : 0);
                    tr.addView(rb);

                    mRestaurantsTable.addView(tr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }




//            mContext.setListAdapter(new ArrayAdapter<RestaurantItem>(
//                    mContext.getActivity(),
//                    R.layout.customized_list_item,
//                    android.R.id.text1,
//                    items));
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    public static class RestaurantItem {
        public String id;
        public String content;
        public String details;

        public RestaurantItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
