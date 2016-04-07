package net.leolink.android.simpleinfinitecarousel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.themealz.themealz.MealOptionDetailActivity;
import com.themealz.themealz.R;

public class MyPagerAdapter extends FragmentPagerAdapter implements
        ViewPager.OnPageChangeListener {

    private MyLinearLayout cur = null;
    private MyLinearLayout next = null;
    private MealOptionDetailActivity context;
    private FragmentManager fm;
    private float scale;

    public MyPagerAdapter(MealOptionDetailActivity context, FragmentManager fm) {
        super(fm);
        this.fm = fm;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        // make the first pager bigger than others
        if (position == MealOptionDetailActivity.FIRST_PAGE)
            scale = MealOptionDetailActivity.BIG_SCALE;
        else
            scale = MealOptionDetailActivity.SMALL_SCALE;

        position = position % MealOptionDetailActivity.PAGES;
        return MyFragment.newInstance(context, position, (String) MealOptionDetailActivity.infos.get(position).get("title"), (String) MealOptionDetailActivity.infos.get(position).get("imageURL"), scale);
    }

    @Override
    public int getCount()
    {
        return MealOptionDetailActivity.PAGES * MealOptionDetailActivity.LOOPS;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels)
    {
        if (positionOffset >= 0f && positionOffset <= 1f)
        {
            cur = getRootView(position);
            cur.setScaleBoth(MealOptionDetailActivity.BIG_SCALE - MealOptionDetailActivity.DIFF_SCALE * positionOffset);

            next = getRootView(position + 1);
            next.setScaleBoth(MealOptionDetailActivity.SMALL_SCALE + MealOptionDetailActivity.DIFF_SCALE * positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {}

    private MyLinearLayout getRootView(int position)
    {
        return (MyLinearLayout)
                fm.findFragmentByTag(this.getFragmentTag(position))
                        .getView().findViewById(R.id.root);
    }

    private String getFragmentTag(int position)
    {
        return "android:switcher:" + context.pager.getId() + ":" + position;
    }
}