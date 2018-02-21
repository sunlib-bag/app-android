package shaolizhi.sunshinebox.ui.index;

/**
 * Created by 邵励治 on 2018/2/21.
 */

public class IndexPresenter implements IndexContract.Presenter {

    private IndexContract.View view;

    public IndexPresenter(IndexContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.setUpView();
    }
}
