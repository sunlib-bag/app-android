package shaolizhi.sunshinebox.ui.index;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shaolizhi.sunshinebox.data.ApiService;
import shaolizhi.sunshinebox.objectbox.courses.Courses;
import shaolizhi.sunshinebox.objectbox.courses.CoursesUtils;
import shaolizhi.sunshinebox.objectbox.courses.Courses_;
import shaolizhi.sunshinebox.utils.ServiceGenerator;

/**
 * 由邵励治于2017/12/27创造.
 */

public class IndexModel implements IndexContract.Model {

    private IndexContract.CallBack callBack;

    private CoursesUtils coursesUtils;

    private Box<Courses> coursesBox;

    IndexModel(@NonNull IndexContract.CallBack callBack, @NonNull Activity activity) {
        this.callBack = callBack;
        //get coursesUtils
        coursesUtils = CoursesUtils.getInstance();
        //get courses-box
        coursesBox = coursesUtils.getCoursesBox(activity);
    }

    @Override
    public Boolean isThereAnyDataInTheDatabase(@NonNull String courseType) {
        return coursesUtils.isCoursesBoxHasData(coursesBox, courseType);
    }

    @Override
    public void requestDataFromNet(@NonNull String courseType, @NonNull String maxLastModificationTime) {
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        Call<IndexBean> call = apiService.getIndexDataAPI(courseType, maxLastModificationTime);
        call.enqueue(new Callback<IndexBean>() {
            @Override
            public void onResponse(@NonNull Call<IndexBean> call, @NonNull Response<IndexBean> response) {
                IndexBean bean = response.body();
                if (bean != null) {
                    callBack.requestDataFromNetSuccess(bean);
                }
            }

            //没有联网
            @Override
            public void onFailure(@NonNull Call<IndexBean> call, @NonNull Throwable t) {
                callBack.requestDataFromNetFailure();
            }
        });
    }

    @Override
    public List<Courses> requestDataFromDatabase(@NonNull String courseType) {
        QueryBuilder<Courses> builder = coursesBox.query();
        Query<Courses> query = builder.equal(Courses_.course_type, courseType).build();
        return query.find();
    }

    /**
     * 将网络中的数据存储到数据库中，
     * 已存在的 UPDATE
     * 不存在的 INSERT
     * delete中的 DELETE
     *
     * @param bean 传入的网络中的数据
     */
    @Override
    public void storedInTheDatabase(@NonNull IndexBean bean) {

        //将delete中返回的数据从数据库中删除，course_id存在的删除，不存在的啥都不做
        List<IndexBean.ContentBean.DeleteBean> deleteBeans = bean.getContent().getDelete();
        for (IndexBean.ContentBean.DeleteBean item : deleteBeans) {
            deleteCourseWithCourseId(item.getCourse_id());
        }

        //将update中返回的数据存入数据库，course_id已存在的UPDATE，不存在的INSERT
        List<IndexBean.ContentBean.UpdateBean> updateBeans = bean.getContent().getUpdate();
        for (IndexBean.ContentBean.UpdateBean item : updateBeans) {
            Long id = getIdFromDatabase(item.getCourse_id());
            Courses courses = new Courses();
            courses.setId(id);
            courses.setCourse_name(item.getCourse_name());
            courses.setCourse_id(item.getCourse_id());
            courses.setCourse_type(item.getCourse_type());
            courses.setCourse_video(item.getCourse_video());
            courses.setCourse_audio(item.getCourse_audio());
            courses.setCourse_text(item.getCourse_text());
            courses.setLast_modification_time(Long.parseLong(item.getLast_modification_time()));
            courses.setVideo_storage_address(null);
            courses.setAudio_storage_address(null);
            courses.setText_storage_address(null);
            courses.setIs_audio_downloaded(false);
            courses.setIs_video_downloaded(false);
            courses.setIs_text_downloaded(false);
            coursesBox.put(courses);
            if (id == 0) {
                Log.e("ObjectBox", "Inserted new courses, ID:" + courses.getId());
            } else {
                Log.e("ObjectBox", "Updated a courses, ID:" + courses.getId());
            }
        }
    }

    /**
     * 查询course_id是否在数据库中存在
     *
     * @param courseId 要查询的course_id
     * @return 若存在返回Id，若不存在返回0
     */
    private long getIdFromDatabase(@NonNull String courseId) {
        QueryBuilder<Courses> builder = coursesBox.query();
        Query query = builder.equal(Courses_.course_id, courseId).build();
        List coursesList = query.find();
        if (coursesList.size() == 0) {
            return 0;
        } else {
            Object object = coursesList.get(0);
            if (object instanceof Courses) {
                return ((Courses) object).getId();
            }
        }
        return 0;
    }

    private void deleteCourseWithCourseId(String courseId) {
        if (getIdFromDatabase(courseId) != 0) {
            QueryBuilder<Courses> builder = coursesBox.query();
            builder.equal(Courses_.course_id, courseId);
            Query<Courses> query = builder.build();
            Courses courses = query.findUnique();
            if (courses != null) {
                coursesBox.remove(courses);
            }
        }
    }

    @Override
    public String getMaxModificationTime(@NonNull String courseType) {
        QueryBuilder<Courses> builder = coursesBox.query();
        builder.equal(Courses_.course_type, courseType);
        Query<Courses> query = builder.build();
        return String.valueOf(query.max(Courses_.last_modification_time));
    }
}
