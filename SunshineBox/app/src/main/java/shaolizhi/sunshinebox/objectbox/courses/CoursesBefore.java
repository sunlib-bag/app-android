package shaolizhi.sunshinebox.objectbox.courses;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * 由邵励治于2017/12/27创造.
 */

@Entity
public class CoursesBefore implements Serializable {
    @Id
    long id;

    String course_id;

    String course_type;

    String course_video;

    String course_audio;

    String course_name;

    String course_text;

    Long last_modification_time;

    String video_storage_address;

    String audio_storage_address;

    String text_storage_address;

    Boolean is_audio_downloaded;

    Boolean is_video_downloaded;

    Boolean is_text_downloaded;

    public Boolean getIs_text_downloaded() {
        return is_text_downloaded;
    }

    public void setIs_text_downloaded(Boolean is_text_downloaded) {
        this.is_text_downloaded = is_text_downloaded;
    }

    public String getCourse_text() {
        return course_text;
    }

    public void setCourse_text(String course_text) {
        this.course_text = course_text;
    }

    public String getText_storage_address() {
        return text_storage_address;
    }

    public void setText_storage_address(String text_storage_address) {
        this.text_storage_address = text_storage_address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Long getLast_modification_time() {
        return last_modification_time;
    }

    public void setLast_modification_time(Long last_modification_time) {
        this.last_modification_time = last_modification_time;
    }

    public String getVideo_storage_address() {
        return video_storage_address;
    }

    public void setVideo_storage_address(String video_storage_address) {
        this.video_storage_address = video_storage_address;
    }

    public String getAudio_storage_address() {
        return audio_storage_address;
    }

    public void setAudio_storage_address(String audio_storage_address) {
        this.audio_storage_address = audio_storage_address;
    }

    public Boolean getIs_audio_downloaded() {
        return is_audio_downloaded;
    }

    public void setIs_audio_downloaded(Boolean is_audio_downloaded) {
        this.is_audio_downloaded = is_audio_downloaded;
    }

    public Boolean getIs_video_downloaded() {
        return is_video_downloaded;
    }

    public void setIs_video_downloaded(Boolean is_video_downloaded) {
        this.is_video_downloaded = is_video_downloaded;
    }
}
