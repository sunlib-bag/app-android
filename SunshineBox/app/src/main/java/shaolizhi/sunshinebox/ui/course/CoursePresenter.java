package shaolizhi.sunshinebox.ui.course;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.IOException;

import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.objectbox.courses.Courses;
import shaolizhi.sunshinebox.ui.video.VideoActivity;
import shaolizhi.sunshinebox.utils.IOUtils;

/**
 * 由邵励治于2018/1/4创造.
 */

public class CoursePresenter implements CourseContract.Presenter, CourseContract.CallBack {

    private CourseContract.View view;

    private CourseContract.Model model;

    private String courseId = null;

    private Boolean isAudioDownloading = false;

    private Boolean isVideoDownloading = false;

    private MediaPlayer mediaPlayer;

    CoursePresenter(CourseContract.View view) {
        this.view = view;
        model = new CourseModel(this, view.getActivity());
        try {
            courseId = view.getCourseIdFromIntent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void start() {
        Courses courses = model.getCourseByCourseId(courseId);
        view.setUpView(courses);
        setUpButtonTextWhenThereIsNoDownload();
    }

    private void webViewLoadData(Courses courses) {
        view.setWebViewContent("file://" + courses.getText_storage_address());
        Log.e("WebView's URL: ", courses.getText_storage_address());
    }

    private void setUpButtonTextWhenThereIsNoDownload() {
        Courses courses = null;
        if (courseId != null) {
            courses = model.getCourseByCourseId(courseId);
        }

        if (courses != null) {
            //加载CardView上的内容
            if (courses.getCourse_text() == null) {
                view.setCardViewTextViewVisibility(true);
                view.setWebViewVisibility(false);
            } else {
                view.setCardViewTextViewVisibility(false);
                view.setWebViewVisibility(true);
                if (courses.getIs_text_downloaded()) {
                    webViewLoadData(courses);
                } else {
                    //请求权限
                    checkPermissions();
                    model.requestTextByCourseId(courseId);
                }
            }
            //加载音频内容
            if (courses.getCourse_audio() == null) {
                view.setAudioButtonText(R.string.course_act_string12);
                view.setAudioButtonEnable(false);
            } else {
                if (courses.getIs_audio_downloaded()) {
                    view.setAudioButtonText(R.string.course_act_string2);
                    view.setAudioButtonEnable(true);
                } else {
                    view.setAudioButtonText(R.string.course_act_string6);
                    view.setAudioButtonEnable(true);
                }
            }
            //加载视频内容
            if (courses.getCourse_video() == null) {
                view.setVideoButtonText(R.string.course_act_string13);
                view.setVideoButtonEnable(false);
            } else {
                if (courses.getIs_video_downloaded()) {
                    view.setVideoButtonText(R.string.course_act_string3);
                    view.setVideoButtonEnable(true);
                } else {
                    view.setVideoButtonText(R.string.course_act_string7);
                    view.setVideoButtonEnable(true);
                }
            }
        }
    }

    @Override
    public void tryToPlayAudio() {
        if (isAudioDownloading) {
            Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string11, Snackbar.LENGTH_SHORT).show();
        } else {
            Courses courses = null;
            if (courseId != null) {
                courses = model.getCourseByCourseId(courseId);
            }

            if (courses != null) {
                if (courses.getIs_audio_downloaded()) {
                    //音频已下载
                    playAudio();
                } else {
                    //请求权限
                    checkPermissions();
                    //音频未下载
                    downloadAudio();
                }
            }
        }

    }

    private void playAudio() {
        Courses courses = model.getCourseByCourseId(courseId);
        if (courses != null) {
            Uri uri = Uri.parse(courses.getAudio_storage_address());
            try {
                if (!mediaPlayer.isPlaying()) {
                    //此时没有播放
                    mediaPlayer.setDataSource(String.valueOf(uri));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    view.setAudioButtonText("Stop停止");
                } else {
                    mediaPlayer.reset();
                    view.setAudioButtonText("Play音频");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadAudio() {
        isAudioDownloading = true;
        //弹出SnackBar
        Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string8, Snackbar.LENGTH_SHORT).show();
        IOUtils.createDirectory();
        model.requestAudioByCourseId(courseId);
    }

    @Override
    public void tryToPlayVideo() {
        if (checkPermissions()) {
            if (isVideoDownloading) {
                Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string10, Snackbar.LENGTH_SHORT).show();
            } else {
                Courses courses = null;
                if (courseId != null) {
                    courses = model.getCourseByCourseId(courseId);
                }
                if (courses != null) {
                    if (courses.getIs_video_downloaded()) {
                        //视频已下载
                        playVideo();
                    } else {
                        //视频未下载
                        downloadVideo();
                    }
                }
            }
        }
    }

    @Override
    public void networkChanged(boolean isThereANetworkConnection) {
        if (isAudioDownloading) {
            if (!isThereANetworkConnection) {
                model.cancelAudioDownloadTask();
                isAudioDownloading = false;
                view.setAudioButtonText(R.string.course_act_string6);
            }
        }
        if (isVideoDownloading) {
            if (!isThereANetworkConnection) {
                model.cancelVideoDownloadTask();
                isVideoDownloading = false;
                view.setVideoButtonText(R.string.course_act_string7);
            }
        }
    }

    @Override
    public void goToTheLastLesson() {
        if (isVideoDownloading || isAudioDownloading) {
            Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string18, Snackbar.LENGTH_SHORT).show();
        } else {
            Courses lastCourses = model.getLastCourseByCourseId(courseId);
            if (lastCourses == null) {
                //没有上一节课了
                Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string17, Snackbar.LENGTH_SHORT).show();
            } else {
                //还有上一节课
                courseId = lastCourses.getCourse_id();
                this.start();
            }
        }
    }

    @Override
    public void goToTheNextLesson() {
        if (isVideoDownloading || isAudioDownloading) {
            Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string18, Snackbar.LENGTH_SHORT).show();
        } else {
            Courses nextCourses = model.getNextCourseByCourseId(courseId);
            if (nextCourses == null) {
                //没有下一节课了
                Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string16, Snackbar.LENGTH_SHORT).show();
            } else {
                //还有下一节课
                courseId = nextCourses.getCourse_id();
                this.start();
            }
        }
    }

    @Override
    public void exit() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void updateAudioDownloadProgress(Long percent) {
        view.setAudioButtonText(String.valueOf(percent) + "%");
    }

    @Override
    public void updateVideoDownloadProgress(Long percent) {
        view.setVideoButtonText(String.valueOf(percent) + "%");
    }

    private void playVideo() {
        Courses courses = model.getCourseByCourseId(courseId);
        if (courses != null) {
            //自定义播放器
            Intent intent = VideoActivity.newIntent(view.getActivity(), courses.getVideo_storage_address());
            view.getActivity().startActivity(intent);
            //系统播放器
//            Uri uri = Uri.parse(courses.getVideo_storage_address());
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(uri, "video/mp4");
//            view.getActivity().startActivity(intent);
        }
    }

    private void downloadVideo() {
        isVideoDownloading = true;
        //弹出SnackBar
        Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string8, Snackbar.LENGTH_SHORT).show();
        IOUtils.createDirectory();
        model.requestVideoByCourseId(courseId);
    }

    /**
     * @return true 权限检查通过
     * false  权限检查没有通过
     */
    private boolean checkPermissions() {
        //检查运行时权限
        boolean result1 = ContextCompat.checkSelfPermission(view.getActivity(), Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED;
        boolean result2 = ContextCompat.checkSelfPermission(view.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        //result1 = true 说明缺少权限
        Log.e("result1", String.valueOf(result1));
        Log.e("result2", String.valueOf(result2));

        //有一个为true（缺少权限），就需要申请
        if (result2) {
            ActivityCompat.requestPermissions(view.getActivity(), new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

//        if (result2) {
//            ActivityCompat.requestPermissions(view.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
//        }
        //都为false（拥有权限）了，再返回true
        return !result2;
    }

    @Override
    public void downloadVideoSuccess() {
        isVideoDownloading = false;
        Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string9, Snackbar.LENGTH_SHORT).show();
        view.setVideoButtonText(R.string.course_act_string3);
    }

    @Override
    public void downloadVideoFailure() {
        isVideoDownloading = false;
        view.setVideoButtonText(R.string.course_act_string7);
        Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string15, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void downloadAudioSuccess() {
        isAudioDownloading = false;
        Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string14, Snackbar.LENGTH_SHORT).show();
        view.setAudioButtonText(R.string.course_act_string2);
    }

    @Override
    public void downloadAudioFailure() {
        isAudioDownloading = false;
        view.setAudioButtonText(R.string.course_act_string6);
        Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string15, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void downloadTextSuccess() {
        Courses courses = model.getCourseByCourseId(courseId);
        webViewLoadData(courses);
        Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string20, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void downloadTextFailure() {
        Snackbar.make(view.getCoordinatorLayout(), R.string.course_act_string21, Snackbar.LENGTH_SHORT).show();
    }
}
