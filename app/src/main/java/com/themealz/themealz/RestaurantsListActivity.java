package com.themealz.themealz;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
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
import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantsListActivity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String ARG_LAST_ITEM_ID = "last_item_id";

    private RestaurantsListActivity mContext = this;
    private String lastItemID;
    private ArrayList<String> mealIds;
    private ArrayList<Integer> mealPrices;
    private ArrayList<String> mealRestaurantsIds;
    private ArrayList<String> mealRestaurantsNames;

    private TextView mRestaurantsListTitle;
    private TextView mOrderSummary;
    private TextView mOrderSummaryDetails;
    private TableLayout mRestaurantsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);

        lastItemID = getIntent().getStringExtra(ARG_LAST_ITEM_ID);

        mealIds = new ArrayList<String>();
        mealPrices = new ArrayList<Integer>();
        mealRestaurantsIds = new ArrayList<String>();
        mealRestaurantsNames = new ArrayList<String>();

        mRestaurantsListTitle = (TextView) findViewById(R.id.restaurants_list_title);
        mOrderSummary = (TextView) findViewById(R.id.order_summary);
        mOrderSummaryDetails = (TextView) findViewById(R.id.order_summary_details);
        mRestaurantsTable = (TableLayout) findViewById(R.id.restaurants_table);

        mRestaurantsListTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
        mOrderSummary.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));
        mOrderSummaryDetails.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/regular.ttf"));

        mOrderSummaryDetails.setText(TextUtils.join(" + ", ((TheMealzApplication) mContext.getApplication()).getTitlesArray()));

        new DataRequestor().execute("");
    }

    @Override
    public void onClick(View view) {
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        view.setBackgroundResource(backgroundResource);
        typedArray.recycle();

        final int index = ((TableRow) view).getId();
        ((TheMealzApplication) mContext.getApplication()).setSelectedMeal(new HashMap<String, String>(){{
            put("id", mealIds.get(index));
            put("price", mealPrices.get(index).toString());
            put("restaurant_id", mealRestaurantsIds.get(index));
            put("restaurant_name", mealRestaurantsNames.get(index));
        }});

        Intent detailIntent = new Intent(this, OrderSubmission.class);
        startActivity(detailIntent);
    }

    @Override
    public void onBackPressed() {
        ((TheMealzApplication) ((AppCompatActivity) mContext).getApplication()).removeFromMealOptionsMap(lastItemID);

        super.onBackPressed();
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
                URL url = new URL("http://themealz.com/api/restaurantslistsuggestions");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                StringBuilder postData = new StringBuilder();
                postData.append("{ \"mealOptions\": [\"" + TextUtils.join("\",\"", ((TheMealzApplication) mContext.getApplication()).getIdsArray()) + "\"] }");
                postData.append("{ \"mealOptionFlavors\": " + ((TheMealzApplication) mContext.getApplication()).getFlavorsIdsJSON() + " }");
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                urlConnection.setDoOutput(true);
                urlConnection.getOutputStream().write(postDataBytes);
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
                tr.setId(i);
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                try {
                    addItemToRow(Integer.toString(i + 1), tr, new HashMap<String, Object>() {{
                        put("textSize", 30f);
                        put("textColor", R.color.default_text_color);
                    }});

                    mealIds.add(ja.getJSONObject(i).getString("_id"));
                    mealPrices.add(ja.getJSONObject(i).getInt("price"));
                    mealRestaurantsIds.add(ja.getJSONObject(i).getJSONObject("restaurant").getString("_id"));
                    mealRestaurantsNames.add(ja.getJSONObject(i).getJSONObject("restaurant").getString("name"));

                    addItemToRow(ja.getJSONObject(i).getJSONObject("restaurant").getString("name"), tr, null);
                    addItemToRow(new DecimalFormat("##.##").format(ja.getJSONObject(i).getLong("price")) + " ש\"ח", tr, null);
                    addItemToRow(new DecimalFormat("##").format(ja.getJSONObject(i).getLong("timeInMinutes")) + " דק'", tr, null);

                    RatingBar rb = new RatingBar(mContext, null, android.R.attr.ratingBarStyleSmall);
                    LayerDrawable stars = ((LayerDrawable) rb.getProgressDrawable());
                    stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.yellow_dark), PorterDuff.Mode.SRC_ATOP);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 21, getResources().getDisplayMetrics())), 0, 0);
                    rb.setLayoutParams(params);
                    rb.setNumStars(5);
                    rb.setStepSize(0.5f);
                    rb.setRating(ja.getJSONObject(i).getLong("grade"));
                    tr.addView(rb);

                    tr.setOnClickListener(mContext);

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
}
