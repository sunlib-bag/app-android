package shaolizhi.sunshinebox.ui.course;

import java.util.List;

/**
 * Created by 邵励治 on 2018/3/1.
 * Perfect Code
 */

public class CourseBean {

    /**
     * id : 5a8f7c2bac502e0032ba4233
     * content : # test
     * #### 图集
     * ___
     * ...
     * .
     * ....
     * ........
     * ..
     * <p>
     * <p>
     * ...
     * <p>
     * <p>
     * 图集
     * //
     * //
     * *粗体文字**
     * author : test
     * materials : [{"id":"5a8f7c49fe88c20038907410","file_index":1,"name":"test","type":"album"},{"id":"5a8eac0517d00900355dc635","file_index":2,"url":"http://ac-CQBviH8f.clouddn.com/e9ae26971f2c77860827.mp3","filename":"5a8eac0517d00900355dc635","type":"audio/mpeg3"},{"url":"http://ac-CQBviH8f.clouddn.com/1c39dd71ef5057372ea2.png","id":"5a8f7c53ac502e0032ba430a","filename":"5a8f7c53ac502e0032ba430a","parent":"5a8f7c49fe88c20038907410","album_index":1,"type":"image/png"},{"url":"http://ac-CQBviH8f.clouddn.com/1e00894f8809b609986e.png","id":"5a8f7c559f54540b4a21d0b5","filename":"5a8f7c559f54540b4a21d0b5","parent":"5a8f7c49fe88c20038907410","album_index":2,"type":"image/png"},{"url":"http://ac-CQBviH8f.clouddn.com/3f4a4cefd5e42f03030b.png","id":"5a8f7c6317d0090035613679","filename":"5a8f7c6317d0090035613679","parent":"5a8f7c49fe88c20038907410","album_index":3,"type":"image/png"},{"url":"http://ac-CQBviH8f.clouddn.com/130ac2beaf77e8583a64.png","id":"5a8f7c6d17d00900356136ae","filename":"5a8f7c6d17d00900356136ae","parent":"5a8f7c49fe88c20038907410","album_index":4,"type":"image/png"},{"url":"http://ac-CQBviH8f.clouddn.com/1e16996a9a4a61f09727.png","id":"5a8f7c71d50eee7c5fa48750","filename":"5a8f7c71d50eee7c5fa48750","parent":"5a8f7c49fe88c20038907410","album_index":5,"type":"image/png"},{"url":"http://ac-CQBviH8f.clouddn.com/7812584aefdebaad9b74.png","id":"5a8f7c7e128fe10037d78924","filename":"5a8f7c7e128fe10037d78924","parent":"5a8f7c49fe88c20038907410","album_index":6,"type":"image/png"}]
     */

    private String id;
    private String content;
    private String author;
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private List<MaterialsBean> materials;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<MaterialsBean> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialsBean> materials) {
        this.materials = materials;
    }

    public static class MaterialsBean {
        /**
         * id : 5a8f7c49fe88c20038907410
         * file_index : 1
         * name : test
         * type : album
         * url : http://ac-CQBviH8f.clouddn.com/e9ae26971f2c77860827.mp3
         * filename : 5a8eac0517d00900355dc635
         * parent : 5a8f7c49fe88c20038907410
         * album_index : 1
         */

        private String id;
        private int file_index;
        private String name;
        private String type;
        private String url;
        private String filename;
        private String parent;
        private int album_index;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getFile_index() {
            return file_index;
        }

        public void setFile_index(int file_index) {
            this.file_index = file_index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public int getAlbum_index() {
            return album_index;
        }

        public void setAlbum_index(int album_index) {
            this.album_index = album_index;
        }
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", source='" + source + '\'' +
                ", materials=" + materials +
                '}';
    }
}
