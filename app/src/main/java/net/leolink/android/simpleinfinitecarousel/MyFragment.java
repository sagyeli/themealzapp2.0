package net.leolink.android.simpleinfinitecarousel;

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

import static com.themealz.themealz.R.drawable.meal01;
import static com.themealz.themealz.R.drawable.meal02;
import static com.themealz.themealz.R.drawable.meal03;

public class MyFragment extends Fragment {

    static MealOptionDetailActivity contextActivity;

    public static Fragment newInstance(MealOptionDetailActivity context, int pos, String title, float scale)
    {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putString("title", title);
        b.putFloat("scale", scale);

        contextActivity = context;
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

        final int pos = this.getArguments().getInt("pos");
        String title = this.getArguments().getString("title");
        TextView tv = (TextView) l.findViewById(R.id.text);
        tv.setText(title);

        MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.root);
        Button content = (Button) l.findViewById(R.id.content);
        switch (pos) {
            case 0:
                content.setBackgroundResource(meal01);
                break;
            case 1:
                content.setBackgroundResource(meal02);
                break;
            case 2:
                content.setBackgroundResource(meal03);
                break;
//            case 3:
//                content.setBackgroundResource(meal04);
//                break;
//            case 4:
//                content.setBackgroundResource(meal05);
//                break;
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