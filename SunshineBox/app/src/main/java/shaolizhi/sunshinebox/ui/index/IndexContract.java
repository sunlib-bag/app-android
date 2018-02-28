package shaolizhi.sunshinebox.ui.index;

import android.app.Activity;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;

import java.util.List;

import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.ui.base.BasePresenter;
import shaolizhi.sunshinebox.ui.base.BaseView;

/**
 * Created by shaol on 2018/2/21.
 */

interface IndexContract {
    enum CourseType {
        NURSERY, MUSIC, READING, GAME
    }

    interface View extends BaseView {

        CourseType getCourseType();

        void loadDataToRecyclerView(List<Course> courseList);

        void startRefresh();

        void stopRefresh();

        Activity getFuckingActivity();
    }

    interface Presenter extends BasePresenter {
        void tryToLoadDataIntoRecyclerView();
    }

    interface Model {
        void requestDataFromNet(CourseType courseType);

        void updateDatabase(List<AVObject> dataFromNet, CourseType courseType);

        void requestDataFromDatabase(CourseType courseType);
    }

    interface CallBack {
        void requestDataFromNetSuccess(List<AVObject> dataFromNet, CourseType courseType);

        void requestDataFromNetFailure(AVException e);

        void updateDatabaseSuccess();

        void requestDataFromDatabaseSuccess(List<Course> dataFromDatabase);
    }
}
