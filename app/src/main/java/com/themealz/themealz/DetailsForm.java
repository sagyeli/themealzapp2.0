package com.themealz.themealz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DetailsForm extends Activity implements View.OnClickListener {

    Button mSubmitButton;

    TheMealzApplication theMealzApplication;

    String orderMessageText;
    String recipientDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_form);

        overrideFonts(this, findViewById(android.R.id.content));

        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(this);

        theMealzApplication = ((TheMealzApplication) getApplication());

        // This is made for texting purposes only...
//        TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        mTestingPhoneNumber = tMgr.getLine1Number();
        //
    }

    private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/regular.ttf"));
            }
        } catch (Exception e) {
        }
    }

    private void readAllFields(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    readAllFields(context, child);
                }
            } else if (v instanceof EditText) {
                if (recipientDetails != "") {
                    recipientDetails += " ";
                }
                recipientDetails += ((EditText) v).getText();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View view) {
        recipientDetails = "";
        readAllFields(this, findViewById(android.R.id.content));

        new DataRequestor().execute();
    }

    private class DataRequestor extends AsyncTask<String, Void, String> {
        private JSONArray ja;

        @Override
        protected String doInBackground(String... params) {
            ArrayList<String> info = theMealzApplication.getMealOptionsTitlesArrayList();
            orderMessageText =
                    "לכבוד " + theMealzApplication.getSelectedMeal().get("restaurant_name") + "\\n" +
                     "\\n" +
                     "התקבלה ההזמנה הבאה\\n" +
                     TextUtils.join("\\n", info.toArray(new String[info.size()])) + "\\n" +
                     "\\n" +
                     "פרטי המזמין\\n" +
                     recipientDetails;

            try {
                URL url = new URL("http://themealz.com/api/ordermessages");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                StringBuilder postData = new StringBuilder();
                postData.append("{ \"restaurant\": \"" + theMealzApplication.getSelectedMeal().get("restaurant_id") + "\", \"text\": \"" + orderMessageText + "\" }");
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
        protected void onPostExecute(String result) {}

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
