package shaolizhi.sunshinebox.ui.phone_number_verify;

/**
 * 由邵励治于2017/11/29创造.
 */

public class SendCaptchaBean {
    /**
     * flag : 001
     * message : success
     * content : null
     */

    private String flag;
    private String message;
    private Object content;

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

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
