package net.leolink.android.simpleinfinitecarousel;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themealz.themealz.MealOptionDetailActivity;
import com.themealz.themealz.R;

import java.io.IOException;
import java.net.URL;

public class MyFragment extends Fragment {

    static MealOptionDetailActivity contextActivity;

    public static Fragment newInstance(MealOptionDetailActivity context, int pos, String title, String imageURL, float scale)
    {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putString("title", title);
        b.putString("imageURL", imageURL);
        b.putFloat("scale", scale);

        contextActivity = context;
        return Fragment.instantiate(context, MyFragment.class.getName(), b);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout)
                inflater.inflate(R.layout.mf, container, false);

        final int pos = this.getArguments().getInt("pos");
        String title = this.getArguments().getString("title");
        String imageURL = this.getArguments().getString("imageURL");
        TextView tv = (TextView) l.findViewById(R.id.text);
        tv.setText(title);

        MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.root);
        Button content = (Button) l.findViewById(R.id.content);

        if (imageURL != null && imageURL != "") {
            try {
                URL url = new URL(imageURL);
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                content.setBackground(new BitmapDrawable(getResources(), bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contextActivity.onItemSelected(pos);
            }
        });

        return l;
    }
}