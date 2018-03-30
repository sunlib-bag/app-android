package shaolizhi.sunshinebox.ui.index;

import android.app.Activity;
import android.util.Log;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVRole;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.objectbox.Box;
import shaolizhi.sunshinebox.data.API;
import shaolizhi.sunshinebox.data.LessonLCBean;
import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.objectbox.courses.ObjectBoxUtils;
import shaolizhi.sunshinebox.objectbox.courses.Tag;

/**
 * Created by 邵励治 on 2018/2/22.
 * Perfect Code
 */

class IndexModel implements IndexContract.Model {
    private IndexContract.CallBack callBack;
    private Activity activity;
    private Box<Course> courseBox;


    IndexModel(IndexContract.CallBack callBack, Activity activity) {
        this.callBack = callBack;
        this.activity = activity;
    }

    @Override
    public void requestRole() {
        Log.e("FUCK", "requestRole");
        AVUser.getCurrentUser().getRolesInBackground(new AVCallback<List<AVRole>>() {
            @Override
            protected void internalDone0(List<AVRole> avRoles, AVException e) {
                boolean isEditor = false;
                if (e == null) {
                    for (AVRole avRole : avRoles) {
                        if (Objects.equals(avRole.getObjectId(), "5ab6000d17d0096887783cd6") || Objects.equals(avRole.getObjectId(), "5ab6001dac502e57c949a142")) {
                            isEditor = true;
                            break;
                        }
                    }
                    callBack.requestRoleSuccess(isEditor);
                } else {
                    callBack.requestRoleFailure(e);
                }
            }
        });
    }

    @Override
    public void requestDataFromNet(boolean isEditor) {
        if (isEditor) {
            API.getAllEditorLesson(new API.QueryListener() {
                @Override
                public void success(List<LessonLCBean> beanList) {
                    callBack.requestDataFromNetSuccess(beanList);
                }

                @Override
                public void failure(AVException e) {
                    callBack.requestDataFromNetFailure(e);
                }
            });
        } else {
            API.getAllLesson(new API.QueryListener() {
                @Override
                public void success(List<LessonLCBean> beanList) {
                    callBack.requestDataFromNetSuccess(beanList);
                }

                @Override
                public void failure(AVException e) {
                    callBack.requestDataFromNetFailure(e);
                }
            });
        }
    }

    @Override
    public void updateDatabaseCourse(List<LessonLCBean> netData) {
        List<Course> localData = ObjectBoxUtils.getAllCourseOrderByObjectId(activity);
        ObjectBoxUtils.synchronizeCourse(localData, netData, activity);
        callBack.updateDatabaseCourseSuccess();
    }

    @Override
    public void updateDatabaseTag(List<Tag> tagList) {
        Box<Tag> tagBox = ObjectBoxUtils.getTagBox(activity);
        tagBox.removeAll();
        tagBox.put(tagList);
        callBack.updateDatabaseTagSuccess();
    }

    @Override
    public void requestTagFromNet(boolean isEditor) {
        final List<Tag> tagList = new ArrayList<>();
        if (isEditor) {
            API.getEditorLessonByTag(API.HEALTH, new API.QueryListener() {
                @Override
                public void success(List<LessonLCBean> beanList) {
                    List<Tag> tagList1 = createTagList(beanList, API.HEALTH, activity);
                    tagList.addAll(tagList1);
                    API.getEditorLessonByTag(API.LANGUAGE, new API.QueryListener() {
                        @Override
                        public void success(List<LessonLCBean> beanList) {
                            List<Tag> tagList2 = createTagList(beanList, API.LANGUAGE, activity);
                            tagList.addAll(tagList2);
                            API.getEditorLessonByTag(API.SOCIAL, new API.QueryListener() {
                                @Override
                                public void success(List<LessonLCBean> beanList) {
                                    List<Tag> tagList3 = createTagList(beanList, API.SOCIAL, activity);
                                    tagList.addAll(tagList3);
                                    API.getEditorLessonByTag(API.SCIENCE, new API.QueryListener() {
                                        @Override
                                        public void success(List<LessonLCBean> beanList) {
                                            List<Tag> tagList4 = createTagList(beanList, API.SCIENCE, activity);
                                            tagList.addAll(tagList4);
                                            API.getEditorLessonByTag(API.ART, new API.QueryListener() {
                                                @Override
                                                public void success(List<LessonLCBean> beanList) {
                                                    List<Tag> tagList5 = createTagList(beanList, API.ART, activity);
                                                    tagList.addAll(tagList5);
                                                    callBack.requestTagFromNetSuccess(tagList);
                                                }

                                                @Override
                                                public void failure(AVException e) {
                                                    callBack.requestTagFromNetFailure(e);
                                                }
                                            });
                                        }

                                        @Override
                                        public void failure(AVException e) {
                                            callBack.requestTagFromNetFailure(e);
                                        }
                                    });
                                }

                                @Override
                                public void failure(AVException e) {
                                    callBack.requestTagFromNetFailure(e);
                                }
                            });
                        }

                        @Override
                        public void failure(AVException e) {
                            callBack.requestTagFromNetFailure(e);
                        }
                    });
                }

                @Override
                public void failure(AVException e) {
                    callBack.requestTagFromNetFailure(e);
                }
            });
        } else {
            API.getLessonByTag(API.HEALTH, new API.QueryListener() {
                @Override
                public void success(List<LessonLCBean> beanList) {
                    List<Tag> tagList1 = createTagList(beanList, API.HEALTH, activity);
                    tagList.addAll(tagList1);
                    API.getLessonByTag(API.LANGUAGE, new API.QueryListener() {
                        @Override
                        public void success(List<LessonLCBean> beanList) {
                            List<Tag> tagList2 = createTagList(beanList, API.LANGUAGE, activity);
                            tagList.addAll(tagList2);
                            API.getLessonByTag(API.SOCIAL, new API.QueryListener() {
                                @Override
                                public void success(List<LessonLCBean> beanList) {
                                    List<Tag> tagList3 = createTagList(beanList, API.SOCIAL, activity);
                                    tagList.addAll(tagList3);
                                    API.getLessonByTag(API.SCIENCE, new API.QueryListener() {
                                        @Override
                                        public void success(List<LessonLCBean> beanList) {
                                            List<Tag> tagList4 = createTagList(beanList, API.SCIENCE, activity);
                                            tagList.addAll(tagList4);
                                            API.getLessonByTag(API.ART, new API.QueryListener() {
                                                @Override
                                                public void success(List<LessonLCBean> beanList) {
                                                    List<Tag> tagList5 = createTagList(beanList, API.ART, activity);
                                                    tagList.addAll(tagList5);
                                                    callBack.requestTagFromNetSuccess(tagList);
                                                }

                                                @Override
                                                public void failure(AVException e) {
                                                    callBack.requestTagFromNetFailure(e);
                                                }
                                            });
                                        }

                                        @Override
                                        public void failure(AVException e) {
                                            callBack.requestTagFromNetFailure(e);
                                        }
                                    });
                                }

                                @Override
                                public void failure(AVException e) {
                                    callBack.requestTagFromNetFailure(e);
                                }
                            });
                        }

                        @Override
                        public void failure(AVException e) {
                            callBack.requestTagFromNetFailure(e);
                        }
                    });
                }

                @Override
                public void failure(AVException e) {
                    callBack.requestTagFromNetFailure(e);
                }
            });
        }


    }

    private List<Tag> createTagList(List<LessonLCBean> netData, String tag, Activity activity) {
        List<Tag> tagList = new ArrayList<>();
        for (LessonLCBean bean : netData) {
            Long id = ObjectBoxUtils.getCourseIdByObjectId(bean.getObjectId(), activity);
            if (id == 0) {
                //do nothing 避免了在同步数据库后，更新缓存钱，服务器增加数据引起的BUG
            } else {
                Tag tag1 = new Tag();
                tag1.setId(0);
                tag1.setField(tag);
                tag1.course.setTargetId(id);
                tagList.add(tag1);
            }
        }
        return tagList;
    }


    @Override
    public void requestDataFromDatabase(IndexContract.FragmentType fragmentType) {
        List<Course> courseList = ObjectBoxUtils.getCourseList(fragmentType, activity);
        callBack.requestDataFromDatabaseSuccess(courseList);
    }
}
