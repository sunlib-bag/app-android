package shaolizhi.sunshinebox.ui.index;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.OnClick;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.objectbox.courses.CoursesUtils;
import shaolizhi.sunshinebox.ui.base.BaseActivity;
import shaolizhi.sunshinebox.utils.UIUtils;

public class IndexActivity extends BaseActivity {

    @BindView(R.id.index_content_act_tablayout)
    TabLayout tabLayout;

    @BindView(R.id.index_content_act_viewpager)
    ViewPager viewPager;

    @BindView(R.id.index_act_drawerlayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.index_content_act_imagebuttton)
    ImageButton drawerLayoutSwitch;

    @BindView(R.id.index_content_act_toolbar)
    Toolbar toolbar;

    IndexFragment rhymesFragment;

    IndexFragment readingFragment;

    IndexFragment musicFragment;

    IndexFragment gameFragment;

    MyAdapter adapter;

    @OnClick(R.id.index_content_act_imagebuttton)
    public void drawerLayoutSwitch() {
        int drawerLockMode = drawerLayout.getDrawerLockMode(GravityCompat.START);
        if (drawerLayout.isDrawerVisible(GravityCompat.START)
                && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void created(Bundle bundle) {
        CoursesUtils.getInstance().printAllObject(CoursesUtils.getInstance().getCoursesBox(this));

        rhymesFragment = IndexFragment.newInstance("rhymes");

        readingFragment = IndexFragment.newInstance("reading");

        musicFragment = IndexFragment.newInstance("music");

        gameFragment = IndexFragment.newInstance("game");

        adapter = new MyAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //隐藏ToolBar标题
            actionBar.setDisplayShowTitleEnabled(false);
        }

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                float angle = 720 * slideOffset;
                drawerLayoutSwitch.setRotation(angle);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }

    @Override
    protected void resumed() {

    }

    private class MyAdapter extends FragmentPagerAdapter {
        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return rhymesFragment;
                case 1:
                    return readingFragment;
                case 2:
                    return musicFragment;
                default:
                    return gameFragment;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.index_act_string8);
                case 1:
                    return getString(R.string.index_act_string9);
                case 2:
                    return getString(R.string.index_act_string10);
                default:
                    return getString(R.string.index_act_string11);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            UIUtils.ifBackOut(this, getString(R.string.index_act_string3));
        }
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, IndexActivity.class);
    }
}
