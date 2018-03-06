package shaolizhi.sunshinebox.ui.course;

import java.util.List;

/**
 * Created by 邵励治 on 2018/3/1.
 * Perfect Code
 */

public class CourseBean {

    /**
     * id : 5a8f7c2bac502e0032ba4233
     * name : 测试本地图片展示
     * version_code : 7
     * tags : ["domain.语言","source.千千树"]
     * source : 千千树
     * content : # test
     #### 图集
     ___
     ...
     .
     ....
     ........
     ..


     ...


     图集
     //
     //
     **粗体文字**
     ![测试本地图片展示.jpg](5a9cfae29f54541f23721f17)
     * author : test
     * materials : [{"url":"http://ac-CQBviH8f.clouddn.com/1c39dd71ef5057372ea2.png","id":"5a8f7c53ac502e0032ba430a","filename":"5a8f7c53ac502e0032ba430a","parent":"5a8f7c49fe88c20038907410","album_index":1,"type":"image/png"},{"url":"http://ac-CQBviH8f.clouddn.com/1e00894f8809b609986e.png","id":"5a8f7c559f54540b4a21d0b5","filename":"5a8f7c559f54540b4a21d0b5","parent":"5a8f7c49fe88c20038907410","album_index":2,"type":"image/png"},{"url":"http://ac-CQBviH8f.clouddn.com/3f4a4cefd5e42f03030b.png","id":"5a8f7c6317d0090035613679","filename":"5a8f7c6317d0090035613679","parent":"5a8f7c49fe88c20038907410","album_index":3,"type":"image/png"},{"url":"http://ac-CQBviH8f.clouddn.com/130ac2beaf77e8583a64.png","id":"5a8f7c6d17d00900356136ae","filename":"5a8f7c6d17d00900356136ae","parent":"5a8f7c49fe88c20038907410","album_index":4,"type":"image/png"},{"url":"http://ac-CQBviH8f.clouddn.com/1e16996a9a4a61f09727.png","id":"5a8f7c71d50eee7c5fa48750","filename":"5a8f7c71d50eee7c5fa48750","parent":"5a8f7c49fe88c20038907410","album_index":5,"type":"image/png"},{"url":"http://ac-CQBviH8f.clouddn.com/7812584aefdebaad9b74.png","id":"5a8f7c7e128fe10037d78924","filename":"5a8f7c7e128fe10037d78924","parent":"5a8f7c49fe88c20038907410","album_index":6,"type":"image/png"},{"id":"5a8f7c49fe88c20038907410","file_index":1,"name":"test","type":"album"},{"id":"5a8eac0517d00900355dc635","file_index":2,"url":"http://ac-CQBviH8f.clouddn.com/e9ae26971f2c77860827.mp3","filename":"5a8eac0517d00900355dc635","type":"audio/mpeg3"},{"id":"5a9cfae29f54541f23721f17","file_index":3,"url":"http://ac-CQBviH8f.clouddn.com/70912058c2bd1f0fa1c4.jpg","filename":"5a9cfae29f54541f23721f17","type":"image/jpeg"}]
     */

    private String id;
    private String name;
    private int version_code;
    private String source;
    private String content;
    private String author;
    private List<String> tags;
    private List<MaterialsBean> materials;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<MaterialsBean> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialsBean> materials) {
        this.materials = materials;
    }

    public static class MaterialsBean {
        /**
         * url : http://ac-CQBviH8f.clouddn.com/1c39dd71ef5057372ea2.png
         * id : 5a8f7c53ac502e0032ba430a
         * filename : 5a8f7c53ac502e0032ba430a
         * parent : 5a8f7c49fe88c20038907410
         * album_index : 1
         * type : image/png
         * file_index : 1
         * name : test
         */

        private String url;
        private String id;
        private String filename;
        private String parent;
        private int album_index;
        private String type;
        private int file_index;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
    }
}
