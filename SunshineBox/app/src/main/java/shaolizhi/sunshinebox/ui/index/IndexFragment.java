package shaolizhi.sunshinebox.ui.index;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import butterknife.BindView;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.base.BaseFragment;
import shaolizhi.sunshinebox.widget.MyRefreshLayout;

/**
 * Created by 邵励治 on 2018/2/12.
 */

public class IndexFragment extends BaseFragment implements IndexContract.View, MyRefreshLayout.OnRefreshListener {

    @BindView(R.id.index_fgm_my_refreshlayout)
    MyRefreshLayout refreshLayout;

    @BindView(R.id.index_fgm_recyclerview)
    RecyclerView recyclerView;

    IndexAdapter indexAdapter;

    IndexContract.Presenter presenter;

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
        //set up recyclerview
        indexAdapter = new IndexAdapter(mActivity);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(indexAdapter);

        //set up refreshlayout
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        Log.e("FUCK", "FUCK");
        refreshLayout.setRefreshing(false);
    }
}
