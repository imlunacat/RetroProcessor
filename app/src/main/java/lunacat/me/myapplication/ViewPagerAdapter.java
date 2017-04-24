package lunacat.me.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import lunacat.me.myapplication.fragments.TextFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> mFragments;
    public ViewPagerAdapter(FragmentManager fm, String[] texts) {
        super(fm);

        mFragments = new ArrayList<>(texts.length);
        for(String t: texts) {
            mFragments.add(TextFragment.newInstance(t));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
