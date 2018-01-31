package shaolizhi.sunshinebox.ui.course;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;

import shaolizhi.sunshinebox.objectbox.courses.Courses;
import shaolizhi.sunshinebox.ui.base.BasePresenter;
import shaolizhi.sunshinebox.ui.base.BaseView;

/**
 * 由邵励治于2018/1/4创造.
 */

class CourseContract {
    interface View extends BaseView {
        String getCourseIdFromIntent() throws Exception;

        void setAudioButtonEnable(Boolean enable);

        void setVideoButtonEnable(Boolean enable);

        void setAudioButtonText(String text);

        void setAudioButtonText(int resId);

        void setVideoButtonText(String text);

        void setVideoButtonText(int resId);

        CoordinatorLayout getCoordinatorLayout();

        Activity getActivity();

        void setUpView(Courses course);

        void setCardViewTextViewVisibility(boolean isVisible);

        void setWebViewVisibility(boolean isVisible);

        void setWebViewContent(String url);
    }

    interface Presenter extends BasePresenter {
        void tryToPlayAudio();

        void tryToPlayVideo();

        void networkChanged(boolean isThereANetworkConnection);

        void goToTheLastLesson();

        void goToTheNextLesson();

        void exit();
    }

    interface Model {
        Courses getCourseByCourseId(String courseId);

        void requestVideoByCourseId(String courseId);

        void requestAudioByCourseId(String courseId);

        void requestTextByCourseId(String courseId);

        void cancelAudioDownloadTask();

        void cancelVideoDownloadTask();

        Courses getNextCourseByCourseId(String courseId);

        Courses getLastCourseByCourseId(String courseId);
    }

    interface CallBack {
        void downloadVideoSuccess();

        void downloadVideoFailure();

        void downloadAudioSuccess();

        void downloadAudioFailure();

        void downloadTextSuccess();

        void downloadTextFailure();

        void updateAudioDownloadProgress(Long percent);

        void updateVideoDownloadProgress(Long percent);
    }
}
