package shaolizhi.sunshinebox.ui.index;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class IndexActivity extends BaseActivity {

    @OnClick(R.id.index_act_linearlayout1)
    public void clickNursery() {
        nurseryTextView.setTextColor(Color.parseColor("#f09038"));
        musicTextView.setTextColor(Color.parseColor("#666666"));
        readingTextView.setTextColor(Color.parseColor("#666666"));
        gameTextView.setTextColor(Color.parseColor("#666666"));
        courseTypeSwitcher.switchToNursery();
    }

    @OnClick(R.id.index_act_linearlayout2)
    public void clickMusic() {
        nurseryTextView.setTextColor(Color.parseColor("#666666"));
        musicTextView.setTextColor(Color.parseColor("#f09038"));
        readingTextView.setTextColor(Color.parseColor("#666666"));
        gameTextView.setTextColor(Color.parseColor("#666666"));
        courseTypeSwitcher.switchToMusic();
    }

    @OnClick(R.id.index_act_linearlayout3)
    public void clickReading() {
        nurseryTextView.setTextColor(Color.parseColor("#666666"));
        musicTextView.setTextColor(Color.parseColor("#666666"));
        readingTextView.setTextColor(Color.parseColor("#f09038"));
        gameTextView.setTextColor(Color.parseColor("#666666"));
        courseTypeSwitcher.switchToReading();
    }

    @OnClick(R.id.index_act_linearlayout4)
    public void clickGame() {
        nurseryTextView.setTextColor(Color.parseColor("#666666"));
        musicTextView.setTextColor(Color.parseColor("#666666"));
        readingTextView.setTextColor(Color.parseColor("#666666"));
        gameTextView.setTextColor(Color.parseColor("#f09038"));
        courseTypeSwitcher.switchToGame();
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

    IndexFragment indexFragment;

    CourseTypeSwitcher courseTypeSwitcher;

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
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.index_act_framelayout) instanceof IndexFragment) {
            indexFragment = (IndexFragment) fragmentManager.findFragmentById(R.id.index_act_framelayout);
        }
        if (indexFragment == null) {
            indexFragment = IndexFragment.newInstance();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.index_act_framelayout, indexFragment);
            fragmentTransaction.commit();
        }
        courseTypeSwitcher = indexFragment;
    }

    //------------------------------static method-------------------------------------------------//
    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, IndexActivity.class);
    }
}
