package shaolizhi.sunshinebox.ui.index;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.base.BaseActivity;
import shaolizhi.sunshinebox.widget.NoScrollViewPager;

public class IndexActivity extends BaseActivity {

    private boolean isThereANet;
    private NetworkChangeBroadcast netWorkChangeBroadcast;

    public boolean isThereANet() {
        return isThereANet;
    }

    public void setThereANet(boolean thereANet) {
        isThereANet = thereANet;
        if (thereANet) {
            toolbarNameTextView.setText("阳光盒子");
        } else {
            toolbarNameTextView.setText("阳光盒子（离线模式）");
        }
    }

    @BindView(R.id.index_act_textview5)
    TextView toolbarNameTextView;

    @OnClick(R.id.index_act_linearlayout1)
    public void clickNursery() {
        nurseryTextView.setTextColor(Color.parseColor("#f09038"));
        musicTextView.setTextColor(Color.parseColor("#666666"));
        readingTextView.setTextColor(Color.parseColor("#666666"));
        gameTextView.setTextColor(Color.parseColor("#666666"));
        noScrollViewPager.setCurrentItem(0);
    }

    @OnClick(R.id.index_act_linearlayout2)
    public void clickMusic() {
        nurseryTextView.setTextColor(Color.parseColor("#666666"));
        musicTextView.setTextColor(Color.parseColor("#f09038"));
        readingTextView.setTextColor(Color.parseColor("#666666"));
        gameTextView.setTextColor(Color.parseColor("#666666"));
        noScrollViewPager.setCurrentItem(1);
    }

    @OnClick(R.id.index_act_linearlayout3)
    public void clickReading() {
        nurseryTextView.setTextColor(Color.parseColor("#666666"));
        musicTextView.setTextColor(Color.parseColor("#666666"));
        readingTextView.setTextColor(Color.parseColor("#f09038"));
        gameTextView.setTextColor(Color.parseColor("#666666"));
        noScrollViewPager.setCurrentItem(2);
    }

    @OnClick(R.id.index_act_linearlayout4)
    public void clickGame() {
        nurseryTextView.setTextColor(Color.parseColor("#666666"));
        musicTextView.setTextColor(Color.parseColor("#666666"));
        readingTextView.setTextColor(Color.parseColor("#666666"));
        gameTextView.setTextColor(Color.parseColor("#f09038"));
        noScrollViewPager.setCurrentItem(3);
    }

    @BindView(R.id.index_act_textview1)
    TextView nurseryTextView;

    @BindView(R.id.index_act_textview2)
    TextView musicTextView;

    @BindView(R.id.index_act_textview3)
    TextView readingTextView;

    @BindView(R.id.index_act_textview4)
    TextView gameTextView;

    @BindView(R.id.index_act_drawerlayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.index_act_imagebuttton1)
    ImageButton drawerLayoutSwitch;

    @BindView(R.id.index_act_toolbar)
    Toolbar toolbar;

    @BindView(R.id.index_act_viewpager)
    NoScrollViewPager noScrollViewPager;

    IndexFragment nurseryFragment;

    IndexFragment musicFragment;

    IndexFragment readingFragment;

    IndexFragment gameFragment;


    //-----------------------------click method---------------------------------------------------//
    @OnClick(R.id.index_act_imagebuttton1)

    public void drawerLayoutSwitch() {
        int drawerLockMode = drawerLayout.getDrawerLockMode(GravityCompat.START);
        if (drawerLayout.isDrawerVisible(GravityCompat.START)
                && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    //-----------------------------common method--------------------------------------------------//
    @Override
    protected int layoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void created(Bundle bundle) {
        setUpTitle();
        setUpDrawerLayoutSwitchAnimator();
        setUpFragment();
    }

    @Override
    protected void resumed() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangeBroadcast = new NetworkChangeBroadcast();
        registerReceiver(netWorkChangeBroadcast, intentFilter);
    }

    //------------------------------private method------------------------------------------------//
    private void setUpDrawerLayoutSwitchAnimator() {
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

    private void setUpTitle() {
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //隐藏ToolBar标题
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void setUpFragment() {
        nurseryFragment = IndexFragment.newInstance(IndexContract.CourseType.NURSERY);
        musicFragment = IndexFragment.newInstance(IndexContract.CourseType.MUSIC);
        readingFragment = IndexFragment.newInstance(IndexContract.CourseType.READING);
        gameFragment = IndexFragment.newInstance(IndexContract.CourseType.GAME);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        noScrollViewPager.setAdapter(viewPagerAdapter);
        noScrollViewPager.setOffscreenPageLimit(3);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return nurseryFragment;
                case 1:
                    return musicFragment;
                case 2:
                    return readingFragment;
                case 3:
                    return gameFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    //------------------------------static method-------------------------------------------------//
    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, IndexActivity.class);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    private class NetworkChangeBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean success = false;
//            //获得网络连接服务
//            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
//            //获取wifi连接状态
//            NetworkInfo.State state = null;
//            if (connectivityManager != null) {
//                state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//            }
//            //判断是否正在使用wifi网络
//            if (state == NetworkInfo.State.CONNECTED) {
//                success = true;
//            }
//            //获取4G状态
//            if (connectivityManager != null) {
//                state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
//            }
//            //判断是否在使用4G网络
//            if (state == NetworkInfo.State.CONNECTED) {
//                success = true;
//            }
            success = isConnected(IndexActivity.this);
            //如果没有连接成功
            if (!success) {
                setThereANet(false);
            } else {
                setThereANet(true);
            }
        }
    }
}
