package shaolizhi.sunshinebox.ui.index;

import java.util.List;

/**
 * 由邵励治于2017/12/28创造.
 */

public class IndexBean {

    /**
     * flag : 001
     * message : success
     * content : {"update":[{"course_id":"17","course_type":"music","course_name":"大黄蜂","course_video":"http://sunshinebox-1255613827.file.myqcloud.com/%E9%9F%B3%E4%B9%90/%E5%A4%A7%E9%BB%84%E8%9C%82/01%E5%A4%A7%E9%BB%84%E8%9C%82.mp4","course_audio":null,"course_text":null,"last_modification_time":"1514293465"},{"course_id":"18","course_type":"music","course_name":"蜂房在这里","course_video":"http://sunshinebox-1255613827.file.myqcloud.com/%E9%9F%B3%E4%B9%90/%E8%9C%82%E6%88%BF%E5%9C%A8%E8%BF%99%E9%87%8C/01%E8%9C%82%E6%88%BF%E5%9C%A8%E8%BF%99%E9%87%8C.mp4","course_audio":"http://sunshinebox-1255613827.file.myqcloud.com/%E9%9F%B3%E4%B9%90/%E8%9C%82%E6%88%BF%E5%9C%A8%E8%BF%99%E9%87%8C/01%E8%9C%82%E6%88%BF%E5%9C%A8%E8%BF%99%E9%87%8C.mp3","course_text":null,"last_modification_time":"1514293474"},{"course_id":"19","course_type":"music","course_name":"两只小小鸟","course_video":"http://sunshinebox-1255613827.file.myqcloud.com/%E9%9F%B3%E4%B9%90/%E4%B8%A4%E5%8F%AA%E5%B0%8F%E5%B0%8F%E9%B8%9F/01%E4%B8%A4%E5%8F%AA%E5%B0%8F%E5%B0%8F%E9%B8%9F%28A%E7%89%88%29.mp4","course_audio":null,"course_text":null,"last_modification_time":"1514293482"}],"delete":[{"course_id":"26","time_deleted":"1514877469"},{"course_id":"30","time_deleted":"1514883354"},{"course_id":"31","time_deleted":"1514883396"},{"course_id":"36","time_deleted":"1514884000"},{"course_id":"37","time_deleted":"1514883998"},{"course_id":"38","time_deleted":"1514883995"},{"course_id":"39","time_deleted":"1514883991"},{"course_id":"40","time_deleted":"1514883987"},{"course_id":"41","time_deleted":"1514883984"},{"course_id":"42","time_deleted":"1514883981"}]}
     */

    private String flag;
    private String message;
    private ContentBean content;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        private List<UpdateBean> update;
        private List<DeleteBean> delete;

        public List<UpdateBean> getUpdate() {
            return update;
        }

        public void setUpdate(List<UpdateBean> update) {
            this.update = update;
        }

        public List<DeleteBean> getDelete() {
            return delete;
        }

        public void setDelete(List<DeleteBean> delete) {
            this.delete = delete;
        }

        public static class UpdateBean {
            /**
             * course_id : 17
             * course_type : music
             * course_name : 大黄蜂
             * course_video : http://sunshinebox-1255613827.file.myqcloud.com/%E9%9F%B3%E4%B9%90/%E5%A4%A7%E9%BB%84%E8%9C%82/01%E5%A4%A7%E9%BB%84%E8%9C%82.mp4
             * course_audio : null
             * course_text : null
             * last_modification_time : 1514293465
             */

            private String course_id;
            private String course_type;
            private String course_name;
            private String course_video;
            private String course_audio;
            private String course_text;
            private String last_modification_time;

            public String getCourse_id() {
                return course_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public String getCourse_type() {
                return course_type;
            }

            public void setCourse_type(String course_type) {
                this.course_type = course_type;
            }

            public String getCourse_name() {
                return course_name;
            }

            public void setCourse_name(String course_name) {
                this.course_name = course_name;
            }

            public String getCourse_video() {
                return course_video;
            }

            public void setCourse_video(String course_video) {
                this.course_video = course_video;
            }

            public String getCourse_audio() {
                return course_audio;
            }

            public void setCourse_audio(String course_audio) {
                this.course_audio = course_audio;
            }

            public String getCourse_text() {
                return course_text;
            }

            public void setCourse_text(String course_text) {
                this.course_text = course_text;
            }

            public String getLast_modification_time() {
                return last_modification_time;
            }

            public void setLast_modification_time(String last_modification_time) {
                this.last_modification_time = last_modification_time;
            }
        }

        public static class DeleteBean {
            /**
             * course_id : 26
             * time_deleted : 1514877469
             */

            private String course_id;
            private String time_deleted;

            public String getCourse_id() {
                return course_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public String getTime_deleted() {
                return time_deleted;
            }

            public void setTime_deleted(String time_deleted) {
                this.time_deleted = time_deleted;
            }
        }
    }
}
