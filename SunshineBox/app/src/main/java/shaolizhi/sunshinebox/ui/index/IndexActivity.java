package shaolizhi.sunshinebox.ui.index;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.OnClick;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.base.BaseActivity;

public class IndexActivity extends BaseActivity {

    @BindView(R.id.index_act_drawerlayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.index_act_imagebuttton1)
    ImageButton drawerLayoutSwitch;

    @BindView(R.id.index_act_toolbar)
    Toolbar toolbar;

    IndexFragment indexFragment;

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
        indexFragment = IndexFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.index_act_framelayout, indexFragment);
        fragmentTransaction.commit();
    }


    //------------------------------static method-------------------------------------------------//
    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, IndexActivity.class);
    }
}
