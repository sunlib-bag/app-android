package shaolizhi.sunshinebox.ui.course;

import java.util.List;

/**
 * Created by 邵励治 on 2018/3/6.
 * Perfect Code
 */

public class Materials {

    @Override
    public String toString() {
        return "Materials{" +
                "name='" + name + '\'' +
                ", materialType=" + materialType +
                ", resourceStorageAddress='" + resourceStorageAddress + '\'' +
                ", order=" + order +
                ", albumResourceList=" + albumResourceList +
                '}';
    }

    public enum MaterialType {
        VIDEO, AUDIO, ALBUM
    }

    private String name;

    private MaterialType materialType;

    private String resourceStorageAddress;

    private int order;

    public List<AlbumResource> getAlbumResourceList() {
        return albumResourceList;
    }

    public void setAlbumResourceList(List<AlbumResource> albumResourceList) {
        this.albumResourceList = albumResourceList;
    }

    private List<AlbumResource> albumResourceList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public String getResourceStorageAddress() {
        return resourceStorageAddress;
    }

    public void setResourceStorageAddress(String resourceStorageAddress) {
        this.resourceStorageAddress = resourceStorageAddress;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public static class AlbumResource {
        private String name;
        private String resourceStorageAddress;
        private int order;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getResourceStorageAddress() {
            return resourceStorageAddress;
        }

        public void setResourceStorageAddress(String resourceStorageAddress) {
            this.resourceStorageAddress = resourceStorageAddress;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }
}
