package shaolizhi.sunshinebox.ui.index;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.base.BaseFragment;
import shaolizhi.sunshinebox.widget.MyRefreshLayout;

/**
 * Created by 邵励治 on 2018/2/12.
 */

public class IndexFragment extends BaseFragment implements IndexContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.index_fgm_my_refreshlayout)
    MyRefreshLayout refreshLayout;

    @BindView(R.id.index_fgm_recyclerview)
    RecyclerView recyclerView;

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

    }

    @Override
    public void setUpView() {
        //set up refreshlayout
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }
}
