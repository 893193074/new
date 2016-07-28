package com.feicuiedu.gitdroid.hotrepo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 作者：yuanchao on 2016/7/27 0027 14:33
 * 邮箱：yuanchao@feicuiedu.com
 */
public class HotRepoAdapter extends FragmentPagerAdapter {
    public HotRepoAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getItem(int position) {
        return new RepoListFragment();
    }

    @Override public int getCount() {
        return 10;
    }

    @Override public CharSequence getPageTitle(int position) {
        return "Java"+position;
    }
}
