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
import shaolizhi.sunshinebox.utils.ToastUtils;
import shaolizhi.sunshinebox.widget.MyRefreshLayout;

/**
 * Created by 邵励治 on 2018/2/12.
 * Perfect Code
 */

public class IndexFragment extends BaseFragment implements IndexContract.View, MyRefreshLayout.OnRefreshListener, ClearDataHelper {
    @BindView(R.id.index_fgm_my_refreshlayout)
    MyRefreshLayout refreshLayout;

    @BindView(R.id.index_fgm_recyclerview)
    RecyclerView recyclerView;

    private IndexAdapter indexAdapter;

    private IndexContract.Presenter presenter;

    private IndexContract.CourseType courseType;

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

    static IndexFragment newInstance(IndexContract.CourseType courseType) {
        IndexFragment indexFragment = new IndexFragment();
        Bundle args = new Bundle();
        switch (courseType) {
            case NURSERY:
                args.putString("course_type", "nursery");
                break;
            case MUSIC:
                args.putString("course_type", "music");
                break;
            case READING:
                args.putString("course_type", "reading");
                break;
            case GAME:
                args.putString("course_type", "game");
                break;
        }
        indexFragment.setArguments(args);
        return indexFragment;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void created(Bundle bundle) {
        switch (getArguments().getString("course_type")) {
            case "nursery":
                courseType = IndexContract.CourseType.NURSERY;
                break;
            case "music":
                courseType = IndexContract.CourseType.MUSIC;
                break;
            case "reading":
                courseType = IndexContract.CourseType.READING;
                break;
            case "game":
                courseType = IndexContract.CourseType.GAME;
                break;
            default:
                courseType = null;
                break;
        }
    }

    @Override
    protected void resumed() {
        if (presenter == null) {
            presenter = new IndexPresenter(this);
        }
        presenter.start();
    }

    @Override
    public void setUpView() {
        setUpRefreshLayout();
        setUpRecyclerView();
    }

    @Override
    public void onRefresh() {
        if (indexAdapter.isDownloadNow()) {
            stopRefresh();
            ToastUtils.showToast("请等待下载完成后再进行更新操作");
        } else {
            presenter.tryToLoadDataIntoRecyclerView();
        }
    }

    private void setUpRefreshLayout() {
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void clearSuccess() {
        presenter.tryToLoadDataIntoRecyclerView();
    }
}
