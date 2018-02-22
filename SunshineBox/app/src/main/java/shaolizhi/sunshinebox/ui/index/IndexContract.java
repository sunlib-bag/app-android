package shaolizhi.sunshinebox.ui.index;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;

import java.util.List;

import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.ui.base.BasePresenter;
import shaolizhi.sunshinebox.ui.base.BaseView;

/**
 * Created by shaol on 2018/2/21.
 */

class IndexContract {
    enum CourseType {
        NURSERY, MUSIC, READING, GAME
    }

    interface View extends BaseView {

        CourseType getCourseType();

        void refreshRecyclerView(List<Course> courseList);
    }

    interface Presenter extends BasePresenter {
        void tryToLoadDataIntoRecyclerView();
    }

    interface Model {
        void requestDataFromNet(CourseType courseType);

        void requestDataFromDatabase();

        boolean isThereAnyDataInTheDatabase(CourseType courseType);

        void storedInTheDatabase();
    }

    interface CallBack {
        void requestDataFromNetSuccess(List<AVObject> list, CourseType courseType);

        void requestDataFromNetFailure(AVException e);
    }
}
