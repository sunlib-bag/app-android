package shaolizhi.sunshinebox.ui.index;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.objectbox.courses.Courses;
import shaolizhi.sunshinebox.ui.base.BaseFragment;
import shaolizhi.sunshinebox.widget.MyRefreshLayout;

/**
 * 由邵励治于2017/12/11创造.
 */

public class IndexFragment extends BaseFragment implements MyRefreshLayout.OnRefreshListener, IndexContract.View {

    @BindView(R.id.nursery_rhymes_fgm_my_refreshlayout)
    MyRefreshLayout myRefreshLayout;

    @BindView(R.id.nursery_rhymes_fgm_recyclerview)
    RecyclerView recyclerView;

    IndexContract.Presenter presenter;

    IndexAdapter adapter;

    Activity activity;

    String courseType;

    static IndexFragment newInstance(String courseType) {
        IndexFragment indexFragment = new IndexFragment();
        Bundle args = new Bundle();
        args.putString("course_type", courseType);
        indexFragment.setArguments(args);
        return indexFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
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
        courseType = getArguments().getString("course_type");
        if (presenter == null) {
            presenter = new IndexPresenter(this);
        }
        presenter.start();
    }

    @Override
    public void onRefresh() {
        presenter.tryToLoadDataIntoRecyclerView();
    }

    @Override
    public void setUpView() {
        //set up recyclerview
        adapter = new IndexAdapter(activity);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(adapter);

        //set up refreshlayout
        myRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setRefresh(boolean refresh) {
        if (myRefreshLayout != null) {
            myRefreshLayout.setRefreshing(refresh);
        }
    }

    @Override
    public void setDataInAdapter(@NonNull List<Courses> coursesList) {
        adapter.setCoursesList(coursesList);
    }

    @Override
    public Activity getFuckingActivity() {
        return activity;
    }

    @Override
    public String getCourseType() {
        return courseType;
    }
}
