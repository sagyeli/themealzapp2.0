package com.themealz.themealz;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.carouseldemo.controls.Carousel;
import com.carouseldemo.controls.CarouselAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * An activity representing a single Meal Option detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MainActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link MealOptionDetailFragment}.
 */
public class MealOptionDetailActivity extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_LAST_ITEM_ID = "last_item_id";

    private Context mContext = this;
    private String parentID;

    private Carousel mCarousel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealoption_detail);

        parentID = getIntent().getStringExtra(ARG_ITEM_ID);
        mCarousel = (Carousel) findViewById(R.id.carousel);

        new DataRequestor().execute(parentID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        ((TheMealzApplication) ((AppCompatActivity) mContext).getApplication()).removeFromMealOptionsMap(parentID);

        super.onBackPressed();
    }

    private class DataRequestor extends AsyncTask<String, Void, String> {
        private JSONArray ja;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://themealz.com/api/mealoptions" + (params.length > 0 ? "/children/" + params[0] : ""));
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
                Intent detailIntent = new Intent(mContext, RestaurantsListActivity.class);
                startActivity(detailIntent);

                return;
            }

//            mChart.onResume();

//            ArrayList<Float> slices = new ArrayList<Float>();
            ArrayList<Integer> slices = new ArrayList<Integer>();
            final ArrayList<String> titles = new ArrayList<String>();
            final ArrayList<Boolean> hasRealChildrens = new ArrayList<Boolean>();
            final ArrayList<String> ids = new ArrayList<String>();

            for (int i = 0 ; i < ja.length() ; i++) {
                switch (i % 7) {
                    case 0:
                        slices.add(R.drawable.image01);
                        break;
                    case 1:
                        slices.add(R.drawable.image02);
                        break;
                    case 2:
                        slices.add(R.drawable.image03);
                        break;
                    case 3:
                        slices.add(R.drawable.image04);
                        break;
                    case 4:
                        slices.add(R.drawable.image05);
                        break;
                    case 5:
                        slices.add(R.drawable.image06);
                        break;
                    case 6:
                        slices.add(R.drawable.image07);
                        break;
                    default:
                        slices.add(R.drawable.image01);
                }

//                slices.add(1f / ja.length());
                try {
                    titles.add(ja.getJSONObject(i).has("label") && ja.getJSONObject(i).getString("label").length() > 0 ? ja.getJSONObject(i).getString("label") : ja.getJSONObject(i).getString("name"));
                    hasRealChildrens.add(ja.getJSONObject(i).has("hasRealChildren") ? ja.getJSONObject(i).getBoolean("hasRealChildren") : false);
                    ids.add(ja.getJSONObject(i).getString("_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            mCarousel.setItems(slices, titles);
            mCarousel.setOnItemClickListener(new CarouselAdapter.OnItemClickListener(){
                @Override
                public void onItemClick(CarouselAdapter<?> parent, View view, int position, long id) {
                    ((TheMealzApplication) ((AppCompatActivity) mContext).getApplication()).addToMealOptionsMap(ids.get(position), titles.get(position));

                    Intent detailIntent;
                    if (hasRealChildrens.get(position)) {
                        detailIntent = new Intent(mContext, MealOptionDetailActivity.class);
                        detailIntent.putExtra(ARG_ITEM_ID, ids.get(position));
                    }
                    else {
                        detailIntent = new Intent(mContext, RestaurantsListActivity.class);
                        detailIntent.putExtra(ARG_LAST_ITEM_ID, ids.get(position));
                    }
                    startActivity(detailIntent);
                }
            });
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
