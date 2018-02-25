package shaolizhi.sunshinebox.ui.index;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

    private Box<Course> courseBox;


    IndexModel(IndexContract.CallBack callBack, Activity activity) {
        this.callBack = callBack;
        initObjectBox(activity);
    }

    //--------------------------------Drawers：衣柜-------------------------------------------------//
    @Override
    public void requestDataFromNet(final IndexContract.CourseType courseType) {
        final AVQuery<AVObject> query1 = getQueryPublished();
        final AVQuery<AVObject> query2 = getQuerySubject(courseType);
        AVQuery<AVObject> query = getQueryOrderByDescendingObjectId(query1, query2);
        queryToLeanCloud(courseType, query);
    }

    private void requestDataFromNetResult(List<AVObject> list, AVException e, IndexContract.CourseType courseType) {
        if (e == null) {
            callBack.requestDataFromNetSuccess(list, courseType);
        } else {
            callBack.requestDataFromNetFailure(e);
        }
    }

    @Override
    public void updateDatabase(List<AVObject> dataFromNet, IndexContract.CourseType courseType) {
        //获取CourseType类型的数据库中数据
        List<Course> dataFromDatabase = getDataFromDatabase(courseType);
        SynchronizeDatabase synchronizeDatabase = new SynchronizeDatabase(dataFromNet, dataFromDatabase, courseType).invoke();
        List<Course> deleteList = synchronizeDatabase.getDeleteList();
        List<Course> updateList = synchronizeDatabase.getUpdateList();
        List<Course> insertList = synchronizeDatabase.getInsertList();
        courseBox.remove(deleteList);
        courseBox.put(updateList);
        courseBox.put(insertList);
    }

    @Override
    public void requestDataFromDatabase(IndexContract.CourseType courseType) {

    }

    //--------------------------------Dirty Socks：臭袜子--------------------------------------------//
    //for requestDataFromNet
    private void queryToLeanCloud(final IndexContract.CourseType courseType, AVQuery<AVObject> query) {
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                requestDataFromNetResult(list, e, courseType);
            }
        });
    }

    //for requestDataFromNet
    @NonNull
    private AVQuery<AVObject> getQueryOrderByDescendingObjectId(AVQuery<AVObject> query1, AVQuery<AVObject> query2) {
        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(query1, query2));
        query.orderByDescending("objectId");
        query.include("package");
        return query;
    }

    //for requestDataFromNet
    @NonNull
    private AVQuery<AVObject> getQuerySubject(IndexContract.CourseType courseType) {
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
        return query2;
    }

    //for requestDataFromNet
    @NonNull
    private AVQuery<AVObject> getQueryPublished() {
        final AVQuery<AVObject> query1 = new AVQuery<>("Lesson");
        query1.whereEqualTo("isPublished", true);
        return query1;
    }

    //for updateDatabase
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

    private void initObjectBox(Activity activity) {
        CourseUtils courseUtils = CourseUtils.getInstance();
        courseBox = courseUtils.getCourseBox(activity);
    }

    private class SynchronizeDatabase {
        private List<AVObject> dataFromNet;
        private List<Course> dataFromDatabase;
        private IndexContract.CourseType courseType;
        private List<Course> insertList;
        private List<Course> deleteList;
        private List<Course> updateList;

        SynchronizeDatabase(List<AVObject> dataFromNet, List<Course> dataFromDatabase, IndexContract.CourseType courseType) {
            this.dataFromNet = dataFromNet;
            this.dataFromDatabase = dataFromDatabase;
            this.courseType = courseType;
        }

        List<Course> getInsertList() {
            return insertList;
        }

        List<Course> getDeleteList() {
            return deleteList;
        }

        List<Course> getUpdateList() {
            return updateList;
        }

        SynchronizeDatabase invoke() {
            //将dataFromDatabase按objectId升序排列
            sortCourseListByObjectId(dataFromDatabase);
            insertList = new ArrayList<>();
            deleteList = new ArrayList<>();
            updateList = new ArrayList<>();
            for (Course course : dataFromDatabase) {
                //course.getObjectId < minObjectIdFromNet
                for (AVObject avObject : dataFromNet) {
                    if (course.getObjectId().compareTo(avObject.getObjectId()) == 0) {
                        //database中的数据在net中找到了
                        //通过对比versionCode来判断需不需要加入更新列表
                        if (course.getVersionCode() != avObject.getNumber("version_code").intValue()) {
                            course.setCourseName(avObject.getString("name"));
                            switch (course.getSituation()) {
                                case 0:
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    course.setSituation(1);
                                    break;
                                default:
                                    break;
                            }
                            course.setVersionCode(avObject.getNumber("version_code").intValue());
                            course.setResourcePackageUrl(avObject.getAVFile("package").getUrl());
                            updateList.add(course);
                        }
                        break;
                    }
                    if (course.getObjectId().compareTo(avObject.getObjectId()) < 0) {
                        deleteList.add(course);
                        break;
                    }
                }
            }

            for (AVObject avObject : dataFromNet) {
                for (Course course : dataFromDatabase) {
                    if (avObject.getObjectId().compareTo(course.getObjectId()) < 0) {
                        insertList.add(createCourseForDatabase(getSubjectByCourseType(courseType), avObject));
                        break;
                    }
                }
            }
            return this;
        }

        private void sortCourseListByObjectId(List<Course> dataFromDatabase) {
            Collections.sort(dataFromDatabase, new Comparator<Course>() {
                @Override
                public int compare(Course o1, Course o2) {
                    return o1.getObjectId().compareTo(o2.getObjectId());
                }
            });
        }

        @NonNull
        private Course createCourseForDatabase(String subject, AVObject avObject) {
            Course course = new Course();
            course.setId(0);
            course.setObjectId(avObject.getObjectId());
            course.setSubject(subject);
            course.setSituation(0);
            course.setVersionCode(avObject.getNumber("version_code").intValue());
            course.setResourcePackageUrl(avObject.getAVFile("package").getUrl());
            course.setResourceStorageAddress(null);
            return course;
        }

        private String getSubjectByCourseType(IndexContract.CourseType courseType) {
            switch (courseType) {
                case NURSERY:
                    return "nursery";
                case GAME:
                    return "game";
                case READING:
                    return "reading";
                case MUSIC:
                    return "music";
                default:
                    return null;
            }
        }
    }
}
