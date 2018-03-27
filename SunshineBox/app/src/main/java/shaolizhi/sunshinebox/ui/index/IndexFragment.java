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

/**
 * Created by 邵励治 on 2018/2/12.
 * Perfect Code
 */

public class IndexFragment extends BaseFragment implements IndexContract.View, ClearDataHelper, RefreshListener {
    RefreshHelper refreshHelper;

    @BindView(R.id.index_fgm_recyclerview)
    RecyclerView recyclerView;

    private IndexAdapter indexAdapter;

    private IndexContract.Presenter presenter;

    private IndexContract.FragmentType fragmentType;

    private void setUpRecyclerView() {
        indexAdapter = new IndexAdapter(mActivity);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(indexAdapter);
    }

    @Override
    public IndexContract.FragmentType getFragmentType() {
        return fragmentType;
    }

    @Override
    public void loadDataToRecyclerView(List<Course> courseList) {
        indexAdapter.setCourseData(courseList);
    }

    @Override
    public void startRefresh() {
//        if (refreshLayout != null) {
//            refreshLayout.setRefreshing(true);
//        }
        if (refreshHelper != null) {
            refreshHelper.startRefresh();
        }
    }

    @Override
    public void stopRefresh() {
//        if (refreshLayout != null) {
//            refreshLayout.setRefreshing(false);
//        }
        if (refreshHelper != null) {
            refreshHelper.stopRefresh();
        }
    }

    @Override
    public Activity getFuckingActivity() {
        return (Activity) mActivity;
    }

    static IndexFragment newInstance(IndexContract.FragmentType fragmentType) {
        IndexFragment indexFragment = new IndexFragment();
        Bundle args = new Bundle();
        switch (fragmentType) {
            case NURSERY:
                args.putString("fragment_type", "nursery");
                break;
            case MUSIC:
                args.putString("fragment_type", "music");
                break;
            case READING:
                args.putString("fragment_type", "reading");
                break;
            case GAME:
                args.putString("fragment_type", "game");
                break;
            case HEALTHY:
                args.putString("fragment_type", "healthy");
                break;
            case LANGUAGE:
                args.putString("fragment_type", "language");
                break;
            case SOCIAL:
                args.putString("fragment_type", "social");
                break;
            case SCIENCE:
                args.putString("fragment_type", "science");
                break;
            case ART:
                args.putString("fragment_type", "art");
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
        switch (getArguments().getString("fragment_type")) {
            case "nursery":
                fragmentType = IndexContract.FragmentType.NURSERY;
                break;
            case "music":
                fragmentType = IndexContract.FragmentType.MUSIC;
                break;
            case "reading":
                fragmentType = IndexContract.FragmentType.READING;
                break;
            case "game":
                fragmentType = IndexContract.FragmentType.GAME;
                break;
            case "healthy":
                fragmentType = IndexContract.FragmentType.HEALTHY;
                break;
            case "language":
                fragmentType = IndexContract.FragmentType.LANGUAGE;
                break;
            case "social":
                fragmentType = IndexContract.FragmentType.SOCIAL;
                break;
            case "science":
                fragmentType = IndexContract.FragmentType.SCIENCE;
                break;
            case "art":
                fragmentType = IndexContract.FragmentType.ART;
                break;
            default:
                fragmentType = null;
                break;
        }
        refreshHelper = (RefreshHelper) mActivity;

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


    @Override
    public void clearSuccess() {
        presenter.tryToLoadDataIntoRecyclerView();
    }
}
