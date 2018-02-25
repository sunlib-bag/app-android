package shaolizhi.sunshinebox.ui.index;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.ui.base.BaseFragment;
import shaolizhi.sunshinebox.widget.MyRefreshLayout;

/**
 * Created by 邵励治 on 2018/2/12.
 */

public class IndexFragment extends BaseFragment implements IndexContract.View, MyRefreshLayout.OnRefreshListener, CourseTypeSwitcher {
    @BindView(R.id.index_fgm_my_refreshlayout)
    MyRefreshLayout refreshLayout;

    @BindView(R.id.index_fgm_recyclerview)
    RecyclerView recyclerView;

    private IndexAdapter indexAdapter;

    private IndexContract.Presenter presenter;

    private IndexContract.CourseType courseType;

    @Override
    public void switchToNursery() {
        courseType = IndexContract.CourseType.NURSERY;
        presenter.tryToLoadDataIntoRecyclerView();
    }

    @Override
    public void switchToMusic() {
        courseType = IndexContract.CourseType.MUSIC;
        presenter.tryToLoadDataIntoRecyclerView();
    }

    @Override
    public void switchToReading() {
        courseType = IndexContract.CourseType.READING;
        presenter.tryToLoadDataIntoRecyclerView();
    }

    @Override
    public void switchToGame() {
        courseType = IndexContract.CourseType.GAME;
        presenter.tryToLoadDataIntoRecyclerView();
    }

    private void setUpRecyclerView() {
        indexAdapter = new IndexAdapter(mActivity);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(indexAdapter);
    }

    @Override
    public IndexContract.CourseType getCourseType() {
        return courseType;
    }

    @Override
    public void loadDataToRecyclerView(List<Course> courseList) {
        indexAdapter.setCourseData(courseList);
    }

    @Override
    public void startRefresh() {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void stopRefresh() {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public Activity getFuckingActivity() {
        return (Activity) mActivity;
    }


    static IndexFragment newInstance() {
        return new IndexFragment();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void created(Bundle bundle) {

    }

    @Override
    protected void resumed() {
        presenter = new IndexPresenter(this);
        presenter.start();
    }

    @Override
    public void setUpView() {
        setUpRefreshLayout();
        setUpCourseType();
        setUpRecyclerView();
    }

    @Override
    public void onRefresh() {
        presenter.tryToLoadDataIntoRecyclerView();
    }

    private void setUpCourseType() {
        if (courseType == null) {
            courseType = IndexContract.CourseType.NURSERY;
        }
    }

    private void setUpRefreshLayout() {
        refreshLayout.setOnRefreshListener(this);
    }

}
