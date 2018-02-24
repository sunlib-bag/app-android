package shaolizhi.sunshinebox.ui.index;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;

import java.util.List;

import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.utils.ToastUtils;

/**
 * Created by 邵励治 on 2018/2/21.
 */

public class IndexPresenter implements IndexContract.Presenter, IndexContract.CallBack {

    private IndexContract.View view;

    private IndexContract.Model model;

    private IndexContract.CourseType courseType;

    IndexPresenter(IndexContract.View view) {
        this.view = view;
        model = new IndexModel(this, view.getFuckingActivity());
    }

    @Override
    public void start() {
        view.setUpView();
        courseType = null;
        tryToLoadDataIntoRecyclerView();
    }

    //1:request net
    @Override
    public void tryToLoadDataIntoRecyclerView() {
        courseType = view.getCourseType();
        model.requestDataFromNet(courseType);
    }

    //2:get data from net, update net-data to database
    @Override
    public void requestDataFromNetSuccess(List<AVObject> dataFromNet, IndexContract.CourseType courseType) {
        model.updateDatabase(dataFromNet, courseType);
    }

    @Override
    public void requestDataFromNetFailure(AVException e) {
        ToastUtils.showToast(e.getMessage());
    }

    //3:request database
    @Override
    public void updateDatabaseSuccess() {
        model.requestDataFromDatabase(courseType);
    }

    //4:get data from database, show it in user-interface
    @Override
    public void requestDataFromDatabaseSuccess(List<Course> dataFromDatabase) {
        view.loadDataToRecyclerView(dataFromDatabase);
        view.stopRefresh();
    }
}
