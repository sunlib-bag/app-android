package shaolizhi.sunshinebox.ui.index;

import android.support.annotation.NonNull;

import java.util.List;

import shaolizhi.sunshinebox.objectbox.courses.Courses;
import shaolizhi.sunshinebox.ui.base.BaseActivity;
import shaolizhi.sunshinebox.ui.base.BaseFragment;

/**
 * 由邵励治于2017/12/27创造.
 */

public class IndexPresenter implements IndexContract.Presenter, IndexContract.CallBack {

    private IndexContract.View view;
    private IndexContract.Model model;

    IndexPresenter(@NonNull IndexContract.View view) {
        this.view = view;
        model = new IndexModel(this, view.getFuckingActivity());
    }

    @Override
    public void start() {
        view.setUpView();
        tryToLoadDataIntoRecyclerView();
    }

    @Override
    public void tryToLoadDataIntoRecyclerView() {
        if (model.isThereAnyDataInTheDatabase(view.getCourseType())) {
            model.requestDataFromNet(view.getCourseType(), model.getMaxModificationTime(view.getCourseType()));
        } else {
            model.requestDataFromNet(view.getCourseType(), "0");
        }
    }

    @Override
    public void requestDataFromNetSuccess(@NonNull IndexBean bean) {
        if (bean.getFlag() != null) {
            switch (bean.getFlag()) {
                case "001":
                    if (bean.getMessage() != null) {
                        if (bean.getMessage().equals("failure")) {
                            if (view instanceof BaseFragment) {
                                ((BaseFragment) view).showToastForRequestResult("402");
                            }
                            if (view instanceof BaseActivity) {
                                ((BaseActivity) view).showToastForRequestResult("402");
                            }
                        } else if (bean.getMessage().equals("success")) {
                            model.storedInTheDatabase(bean);
                            List<Courses> coursesList = model.requestDataFromDatabase(view.getCourseType());
                            view.setDataInAdapter(coursesList);
                            view.setRefresh(false);
                        }
                    }
                    break;
                default:
                    if (view instanceof BaseFragment) {
                        ((BaseFragment) view).showToastForRequestResult(bean.getFlag());
                    }
                    if (view instanceof BaseActivity) {
                        ((BaseActivity) view).showToastForRequestResult(bean.getFlag());
                    }
                    break;
            }
        }
    }

    @Override
    public void requestDataFromNetFailure() {
        if (view instanceof BaseFragment) {
            ((BaseFragment) view).showToastForRequestResult("403");
        }
        if (view instanceof BaseActivity) {
            ((BaseActivity) view).showToastForRequestResult("403");
        }
        List<Courses> coursesList = model.requestDataFromDatabase(view.getCourseType());
        view.setDataInAdapter(coursesList);
        view.setRefresh(false);
    }

}
