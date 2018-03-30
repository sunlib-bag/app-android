package shaolizhi.sunshinebox.ui.index;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.objectbox.Box;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.objectbox.courses.ObjectBoxUtils;
import shaolizhi.sunshinebox.ui.base.BaseActivity;
import shaolizhi.sunshinebox.ui.main.MainActivity;
import shaolizhi.sunshinebox.utils.AlertDialogUtils;
import shaolizhi.sunshinebox.widget.NoScrollViewPager;

import static shaolizhi.sunshinebox.R2.id.nav_manage;

public class IndexActivity extends BaseActivity implements NetworkStateHelper, RefreshHelper {

    private boolean isThereANet;

    private Box<Course> courseBox;

    NetworkChangedListener networkChangedListener;

    ClearDataHelper helper1;
    ClearDataHelper helper2;
    ClearDataHelper helper3;
    ClearDataHelper helper4;
    ClearDataHelper helper5;
    ClearDataHelper helper6;
    ClearDataHelper helper7;
    ClearDataHelper helper8;
    ClearDataHelper helper9;

    RefreshListener listener1;
    RefreshListener listener2;
    RefreshListener listener3;
    RefreshListener listener4;
    RefreshListener listener5;
    RefreshListener listener6;
    RefreshListener listener7;
    RefreshListener listener8;
    RefreshListener listener9;


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

    @BindView(R.id.index_act_textview10)
    TextView toolbarNameTextView;

    @OnClick(R.id.index_act_linearlayout1)
    public void clickNursery() {
        makeEveryBodyBlack();
        nurseryTextView.setTextColor(Color.parseColor("#f09038"));
        Glide.with(this).load(R.drawable.ic_nursery_orange).into(nurseryImageView);
        noScrollViewPager.setCurrentItem(0);
    }

    @OnClick(R.id.index_act_linearlayout2)
    public void clickMusic() {
        makeEveryBodyBlack();
        musicTextView.setTextColor(Color.parseColor("#f09038"));
        Glide.with(this).load(R.drawable.ic_music_orange).into(musicImageView);
        noScrollViewPager.setCurrentItem(1);
    }

    @OnClick(R.id.index_act_linearlayout3)
    public void clickReading() {
        makeEveryBodyBlack();
        readingTextView.setTextColor(Color.parseColor("#f09038"));
        Glide.with(this).load(R.drawable.ic_reading_orange).into(readingImageView);
        noScrollViewPager.setCurrentItem(2);
    }

    @OnClick(R.id.index_act_linearlayout4)
    public void clickGame() {
        makeEveryBodyBlack();
        gameTextView.setTextColor(Color.parseColor("#f09038"));
        Glide.with(this).load(R.drawable.ic_game_orange).into(gameImageView);
        noScrollViewPager.setCurrentItem(3);
    }

    @OnClick(R.id.index_act_linearlayout5)
    public void clickHealth() {
        makeEveryBodyBlack();
        healthTextView.setTextColor(Color.parseColor("#f09038"));
        Glide.with(this).load(R.drawable.ic_health_orange).into(healthImageView);
        noScrollViewPager.setCurrentItem(4);
    }

    @OnClick(R.id.index_act_linearlayout6)
    public void clickLanguage() {
        makeEveryBodyBlack();
        languageTextView.setTextColor(Color.parseColor("#f09038"));
        Glide.with(this).load(R.drawable.ic_language_orange).into(languageImageView);
        noScrollViewPager.setCurrentItem(5);
    }

    @OnClick(R.id.index_act_linearlayout7)
    public void clickSocial() {
        makeEveryBodyBlack();
        socialTextView.setTextColor(Color.parseColor("#f09038"));
        Glide.with(this).load(R.drawable.ic_social_orange).into(socialImageView);
        noScrollViewPager.setCurrentItem(6);
    }

    @OnClick(R.id.index_act_linearlayout8)
    public void clickScience() {
        makeEveryBodyBlack();
        scienceTextView.setTextColor(Color.parseColor("#f09038"));
        Glide.with(this).load(R.drawable.ic_science_orange).into(scienceImageView);
        noScrollViewPager.setCurrentItem(7);
    }

    @OnClick(R.id.index_act_linearlayout9)
    public void clickArt() {
        makeEveryBodyBlack();
        artTextView.setTextColor(Color.parseColor("#f09038"));
        Glide.with(this).load(R.drawable.ic_art_orange).into(artImageView);
        noScrollViewPager.setCurrentItem(8);
    }

    @BindView(R.id.index_act_textview1)
    TextView nurseryTextView;

    @BindView(R.id.index_act_textview2)
    TextView musicTextView;

    @BindView(R.id.index_act_textview3)
    TextView readingTextView;

    @BindView(R.id.index_act_textview4)
    TextView gameTextView;

    @BindView(R.id.index_act_textview5)
    TextView healthTextView;

    @BindView(R.id.index_act_textview6)
    TextView languageTextView;

    @BindView(R.id.index_act_textview7)
    TextView socialTextView;

    @BindView(R.id.index_act_textview8)
    TextView scienceTextView;

    @BindView(R.id.index_act_textview9)
    TextView artTextView;

    @BindView(R.id.index_act_imageview1)
    ImageView nurseryImageView;

    @BindView(R.id.index_act_imageview2)
    ImageView musicImageView;

    @BindView(R.id.index_act_imageview3)
    ImageView readingImageView;

    @BindView(R.id.index_act_imageview4)
    ImageView gameImageView;

    @BindView(R.id.index_act_imageview5)
    ImageView healthImageView;

    @BindView(R.id.index_act_imageview6)
    ImageView languageImageView;

    @BindView(R.id.index_act_imageview7)
    ImageView socialImageView;

    @BindView(R.id.index_act_imageview8)
    ImageView scienceImageView;

    @BindView(R.id.index_act_imageview9)
    ImageView artImageView;

    @BindView(R.id.index_act_drawerlayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.index_act_imagebuttton1)
    ImageButton drawerLayoutSwitch;

    @BindView(R.id.index_act_toolbar)
    Toolbar toolbar;

    @BindView(R.id.index_act_navigationview)
    NavigationView navigationView;

    @BindView(R.id.index_act_viewpager)
    NoScrollViewPager noScrollViewPager;

    @BindView(R.id.index_act_textview11)
    TextView refreshTextView;

    @BindView(R.id.index_act_view1)
    View refreshView;

    @OnClick(R.id.index_act_view1)
    public void clickRefreshView() {
        Log.e("IndexActivity", "clickRefreshView");
        switch (noScrollViewPager.getCurrentItem()) {
            case 0:
                listener1.onRefresh();
                break;
            case 1:
                listener2.onRefresh();
                break;
            case 2:
                listener3.onRefresh();
                break;
            case 3:
                listener4.onRefresh();
                break;
            case 4:
                listener5.onRefresh();
                break;
            case 5:
                listener6.onRefresh();
                break;
            case 6:
                listener7.onRefresh();
                break;
            case 7:
                listener8.onRefresh();
                break;
            case 8:
                listener9.onRefresh();
                break;
        }
    }

    @BindView(R.id.index_act_imageview10)
    ImageView refreshImageView;

    IndexFragment nurseryFragment;

    IndexFragment musicFragment;

    IndexFragment readingFragment;

    IndexFragment gameFragment;

    IndexFragment healthFragment;

    IndexFragment languageFragment;

    IndexFragment socialFragment;

    IndexFragment scienceFragment;

    IndexFragment artFragment;

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
        AVAnalytics.onEvent(this, "用户打开应用次数", AVUser.getCurrentUser().getUsername());
        initCourseBox();
        setUpTitle();
        setUpDrawerLayoutSwitchAnimator();
        setUpFragment();
        navigationViewItemClickEvent();
        animator = getAnimator(refreshImageView);
    }

    public static void deleteDirWithFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWithFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    private void navigationViewItemClickEvent() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_send:
                        AlertDialogUtils.showAlertDialog(IndexActivity.this, "确定重置软件", "这将会删除全部的本地文件", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clearDatabaseAndData();
                                refreshIndexFragment();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        break;
                    case nav_manage:
                        clearDatabaseAndData();
                        AVUser.logOut();
                        startActivity(MainActivity.newIntent(IndexActivity.this));
                        IndexActivity.this.finish();
                        break;
                }

                return false;
            }
        });
    }

    private void refreshIndexFragment() {
        helper1.clearSuccess();
        helper2.clearSuccess();
        helper3.clearSuccess();
        helper4.clearSuccess();
        helper5.clearSuccess();
        helper6.clearSuccess();
        helper7.clearSuccess();
        helper8.clearSuccess();
        helper9.clearSuccess();
    }

    private void clearDatabaseAndData() {
        courseBox.removeAll();
        ObjectBoxUtils.getTagBox(this).removeAll();
        File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "SunshineBox_II");
        deleteDirWithFile(file);
        drawerLayout.closeDrawers();
    }

    private void initCourseBox() {
        courseBox = ObjectBoxUtils.getCourseBox(this);
    }

    @Override
    protected void resumed() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkChangeBroadcast netWorkChangeBroadcast = new NetworkChangeBroadcast();
        registerReceiver(netWorkChangeBroadcast, intentFilter);
    }

    //------------------------------private method------------------------------------------------//
    private void makeEveryBodyBlack() {
        nurseryTextView.setTextColor(Color.parseColor("#666666"));
        musicTextView.setTextColor(Color.parseColor("#666666"));
        readingTextView.setTextColor(Color.parseColor("#666666"));
        gameTextView.setTextColor(Color.parseColor("#666666"));
        healthTextView.setTextColor(Color.parseColor("#666666"));
        languageTextView.setTextColor(Color.parseColor("#666666"));
        socialTextView.setTextColor(Color.parseColor("#666666"));
        scienceTextView.setTextColor(Color.parseColor("#666666"));
        artTextView.setTextColor(Color.parseColor("#666666"));
        Glide.with(this).load(R.drawable.ic_nursery_black).into(nurseryImageView);
        Glide.with(this).load(R.drawable.ic_music_black).into(musicImageView);
        Glide.with(this).load(R.drawable.ic_reading_black).into(readingImageView);
        Glide.with(this).load(R.drawable.ic_game_black).into(gameImageView);
        Glide.with(this).load(R.drawable.ic_health_black).into(healthImageView);
        Glide.with(this).load(R.drawable.ic_language_black).into(languageImageView);
        Glide.with(this).load(R.drawable.ic_social_black).into(socialImageView);
        Glide.with(this).load(R.drawable.ic_science_black).into(scienceImageView);
        Glide.with(this).load(R.drawable.ic_art_black).into(artImageView);
    }

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
        nurseryFragment = IndexFragment.newInstance(IndexContract.FragmentType.NURSERY);
        helper1 = nurseryFragment;
        listener1 = nurseryFragment;
        musicFragment = IndexFragment.newInstance(IndexContract.FragmentType.MUSIC);
        helper2 = musicFragment;
        listener2 = musicFragment;
        readingFragment = IndexFragment.newInstance(IndexContract.FragmentType.READING);
        helper3 = readingFragment;
        listener3 = readingFragment;
        gameFragment = IndexFragment.newInstance(IndexContract.FragmentType.GAME);
        helper4 = gameFragment;
        listener4 = gameFragment;
        healthFragment = IndexFragment.newInstance(IndexContract.FragmentType.HEALTHY);
        helper5 = healthFragment;
        listener5 = healthFragment;
        languageFragment = IndexFragment.newInstance(IndexContract.FragmentType.LANGUAGE);
        helper6 = languageFragment;
        listener6 = languageFragment;
        socialFragment = IndexFragment.newInstance(IndexContract.FragmentType.SOCIAL);
        helper7 = socialFragment;
        listener7 = socialFragment;
        scienceFragment = IndexFragment.newInstance(IndexContract.FragmentType.SCIENCE);
        helper8 = scienceFragment;
        listener8 = scienceFragment;
        artFragment = IndexFragment.newInstance(IndexContract.FragmentType.ART);
        helper9 = artFragment;
        listener9 = artFragment;

        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        noScrollViewPager.setAdapter(viewPagerAdapter);
        noScrollViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public boolean getNetworkState() {
        return isThereANet();
    }

    @Override
    public void setNetworkChangedListener(NetworkChangedListener networkChangedListener) {
        this.networkChangedListener = networkChangedListener;
    }

    @Override
    public void startRefresh() {
        Log.e("IndexActivity", "startRefresh");
        refreshView.setEnabled(false);
        refreshTextView.setTextColor(Color.parseColor("#999999"));
        Glide.with(IndexActivity.this).load(R.drawable.refresh_69).into(refreshImageView);
        startRotating();
    }

    @Override
    public void stopRefresh() {
        Log.e("IndexActivity", "stopRefresh");
        refreshView.setEnabled(true);
        refreshTextView.setTextColor(Color.parseColor("#333333"));
        stopRotating();
        Glide.with(IndexActivity.this).load(R.drawable.refresh).into(refreshImageView);
    }

    @Override
    public void netError() {

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
                case 4:
                    return healthFragment;
                case 5:
                    return languageFragment;
                case 6:
                    return socialFragment;
                case 7:
                    return scienceFragment;
                case 8:
                    return artFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 9;
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
            boolean success;
            success = isConnected(IndexActivity.this);
            //如果没有连接成功
            if (!success) {
                setThereANet(false);
                if (networkChangedListener != null) {
                    networkChangedListener.networkChanged(false);
                }
            } else {
                setThereANet(true);
                if (networkChangedListener != null) {
                    networkChangedListener.networkChanged(true);
                }
            }
        }
    }

    ObjectAnimator animator;

    private ObjectAnimator getAnimator(ImageView imageView) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, View.ROTATION, 0, 360);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        return objectAnimator;
    }

    private void startRotating() {
        if (animator.isPaused()) {
            animator.resume();
        } else {
            animator.start();
        }
    }

    private void stopRotating() {
        animator.pause();
    }
}
