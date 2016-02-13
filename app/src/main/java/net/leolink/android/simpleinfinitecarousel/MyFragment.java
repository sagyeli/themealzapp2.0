package net.leolink.android.simpleinfinitecarousel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themealz.themealz.MealOptionDetailActivity;
import com.themealz.themealz.R;

public class MyFragment extends Fragment {

    public static Fragment newInstance(MealOptionDetailActivity context, int pos, String title, float scale)
    {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putString("title", title);
        b.putFloat("scale", scale);
        return Fragment.instantiate(context, MyFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout)
                inflater.inflate(R.layout.mf, container, false);

        int pos = this.getArguments().getInt("pos");
        String title = this.getArguments().getString("title");
        TextView tv = (TextView) l.findViewById(R.id.text);
        tv.setText(title);

        MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.root);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);

        return l;
    }
}