package shaolizhi.sunshinebox.ui.index;

import android.app.Activity;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.Arrays;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.objectbox.courses.CourseUtils;
import shaolizhi.sunshinebox.objectbox.courses.Course_;

/**
 * Created by 邵励治 on 2018/2/22.
 */

class IndexModel implements IndexContract.Model {
    private IndexContract.CallBack callBack;

    private CourseUtils courseUtils;

    private Box<Course> courseBox;


    IndexModel(IndexContract.CallBack callBack, Activity activity) {
        this.callBack = callBack;
        initObjectBox(activity);
    }


    //request data from net
    private void requestDataFromNetResult(List<AVObject> list, AVException e, IndexContract.CourseType courseType) {
        if (e == null) {
            callBack.requestDataFromNetSuccess(list, courseType);
        } else {
            callBack.requestDataFromNetFailure(e);
        }
    }

    /**
     * 查询objectId是否在数据库中
     *
     * @param objectId 要查询的objectId
     * @return 若存在返回ObjectBox的id，若不存在返回0
     */
    private long getIdFromDatabase(String objectId) {
        QueryBuilder<Course> builder = courseBox.query();
        Query<Course> query = builder.equal(Course_.objectId, objectId).build();
        List<Course> dataFromDatabase = query.find();
        if (dataFromDatabase.size() == 0) {
            return 0;
        } else {
            Course course = dataFromDatabase.get(0);
            return course.getId();
        }
    }

    /**
     * 按照objectId删除数据库中内容
     *
     * @param objectId
     */
    private void deleteCourseWithObjectId(String objectId) {
        if (getIdFromDatabase(objectId) != 0) {
            QueryBuilder<Course> builder = courseBox.query();
            builder.equal(Course_.objectId, objectId);
            Query<Course> query = builder.build();
            Course course = query.findUnique();
            if (course != null) {
                courseBox.remove(course);
            }
        }
    }

    @Override
    public void requestDataFromNet(final IndexContract.CourseType courseType) {
        final AVQuery<AVObject> query1 = new AVQuery<>("Lesson");
        query1.whereEqualTo("isPublished", true);
        final AVQuery<AVObject> query2 = new AVQuery<>("Lesson");
        switch (courseType) {
            case NURSERY:
                query2.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a701c8c1b69e6003c534903"));
                break;
            case MUSIC:
                query2.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a741bcb2f301e003be904ed"));
                break;
            case READING:
                query2.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a701c82d50eee00444134b2"));
                break;
            case GAME:
                query2.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a8e908dac502e0032b6225d"));
                break;
        }
        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(query1, query2));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                requestDataFromNetResult(list, e, courseType);
            }
        });
    }

    @Override
    public void updateDatabase(List<AVObject> dataFromNet, IndexContract.CourseType courseType) {
        //将ObjectBox中有的，DataFromNet中没有的数据删除
        List<Course> dataFromDatabase = getDataFromDatabase(courseType);

        List<Course> dataNeedToDelete = dataFromDatabase;

        for (AVObject avObject : dataFromNet) {

        }


    }

    private List<Course> getDataFromDatabase(IndexContract.CourseType courseType) {
        QueryBuilder<Course> builder = courseBox.query();
        Query<Course> query;
        switch (courseType) {
            case NURSERY:
                query = builder.equal(Course_.subject, "nursery").build();
                break;
            case MUSIC:
                query = builder.equal(Course_.subject, "music").build();
                break;
            case READING:
                query = builder.equal(Course_.subject, "reading").build();
                break;
            case GAME:
                query = builder.equal(Course_.subject, "game").build();
                break;
            default:
                query = null;
                break;
        }
        return query.find();
    }

    @Override
    public void requestDataFromDatabase(IndexContract.CourseType courseType) {

    }

    private void initObjectBox(Activity activity) {
        courseUtils = CourseUtils.getInstance();
        courseBox = courseUtils.getCourseBox(activity);
    }

}
