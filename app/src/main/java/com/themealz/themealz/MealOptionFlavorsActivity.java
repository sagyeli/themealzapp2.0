package com.themealz.themealz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Switch;
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

public class MealOptionFlavorsActivity extends Activity
        implements View.OnClickListener {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_LAST_ITEM_ID = "last_item_id";
    public static final String HAS_REAL_CHILDREN = "has_real_children";

    private String parentID;
    private Context mContext = this;

    private TableLayout mFlavorsTable;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_option_flavors);

        parentID = getIntent().getStringExtra(ARG_ITEM_ID);

        mFlavorsTable = (TableLayout) findViewById(R.id.flavors_table);
        mSubmitButton = (Button) findViewById(R.id.submit_button);

        mSubmitButton.setOnClickListener(this);

        new DataRequestor().execute("");
    }

    @Override
    public void onClick(View v) {
        Intent detailIntent;

        if (getIntent().getBooleanExtra(HAS_REAL_CHILDREN, false)) {
            detailIntent = new Intent(mContext, MealOptionDetailActivity.class);
            detailIntent.putExtra(ARG_ITEM_ID, parentID);
        }
        else {
            detailIntent = new Intent(mContext, RestaurantsListActivity.class);
            detailIntent.putExtra(ARG_LAST_ITEM_ID, parentID);
        }
        startActivity(detailIntent);
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
                URL url = new URL("http://themealz.com/api/mealOptionFlavors/showRelevantsToMealOption/" + parentID);
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

            for (int i = 0 ; i < ja.length() ; i++) {
                TableRow tr = new TableRow(mContext);
                tr.setId(i);
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                try {
                    final String id = ja.getJSONObject(i).getString("_id");
                    final String name = ja.getJSONObject(i).getString("name");
//                    addItemToRow(Integer.toString(i + 1), tr, new HashMap<String, Object>() {{
//                        put("textSize", 30f);
//                        put("textColor", R.color.default_text_color);
//                    }});
                    addItemToRow(ja.getJSONObject(i).getString("name"), tr, null);
                    Switch mSwitch = new Switch(mContext);
                    mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            TheMealzApplication theMealzApplication = (TheMealzApplication) ((Activity) mContext).getApplication();

                            if(isChecked) {
                                theMealzApplication.addMealOptionFlavor(parentID, id, name);
                            }
                            else {
                                theMealzApplication.removeMealOptionFlavor(parentID, id);
                            }
                        }
                    });
                    tr.addView(mSwitch);

                    mFlavorsTable.addView(tr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
