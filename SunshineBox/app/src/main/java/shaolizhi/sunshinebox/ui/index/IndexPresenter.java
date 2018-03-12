package shaolizhi.sunshinebox.ui.index;

import android.util.Log;

import com.avos.avoscloud.AVException;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import shaolizhi.sunshinebox.data.LessonLCBean;
import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.objectbox.courses.Tag;

/**
 * Created by 邵励治 on 2018/2/21.
 * Perfect Code
 */

public class IndexPresenter implements IndexContract.Presenter, IndexContract.CallBack {

    private IndexContract.View view;

    private IndexContract.Model model;

    private IndexContract.FragmentType fragmentType;

    IndexPresenter(IndexContract.View view) {
        this.view = view;
        model = new IndexModel(this, view.getFuckingActivity());
    }

    @Override
    public void start() {
        view.setUpView();
        fragmentType = null;
        tryToLoadDataIntoRecyclerView();
    }

    //1:request net
    @Override
    public void tryToLoadDataIntoRecyclerView() {
        fragmentType = view.getFragmentType();
        model.requestDataFromDatabase(fragmentType);
        view.startRefresh();
        model.requestDataFromNet();
    }

    //2:get data from net, update net-data to database
    @Override
    public void requestDataFromNetSuccess(List<LessonLCBean> netData) {
        model.updateDatabaseCourse(netData);
    }

    @Override
    public void requestDataFromNetFailure(AVException e) {
        Log.e("IndexPresenter", "requestDataFromNetFailure:" + e.getMessage());
        view.stopRefresh();
    }

    //3:request database
    @Override
    public void updateDatabaseCourseSuccess() {
        model.requestTagFromNet();
    }


    @Override
    public void requestTagFromNetSuccess(List<Tag> tagList) {
        model.updateDatabaseTag(tagList);
    }

    @Override
    public void requestTagFromNetFailure(AVException e) {
        Log.e("IndexPresenter", "requestTagFromNetFailure:" + e.getMessage());
        view.stopRefresh();
    }

    @Override
    public void updateDatabaseTagSuccess() {
        model.requestDataFromDatabase(fragmentType);
    }

    //4:get data from database, show it in user-interface
    @Override
    public void requestDataFromDatabaseSuccess(List<Course> dataFromDatabase) {
        sortListByCreatedAt(dataFromDatabase);
        view.loadDataToRecyclerView(dataFromDatabase);
        view.stopRefresh();
    }

    private void sortListByCreatedAt(List<Course> dataFromDatabase) {
        Collections.sort(dataFromDatabase, new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                Date date1 = o1.getCreatedAt();
                Date date2 = o2.getCreatedAt();
                Long time1 = date1.getTime();
                Long time2 = date2.getTime();
                return (int) (time1 - time2);
            }
        });
    }
}
