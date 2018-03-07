package shaolizhi.sunshinebox.ui.album;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.base.BaseActivity;
import shaolizhi.sunshinebox.ui.course.Materials;

public class AlbumActivity extends BaseActivity {

    private final static String MATERIALS = "materials";

    private List<File> data;

    @BindView(R.id.album_act_button1)
    Button lastImageButton;

    @BindView(R.id.album_act_button2)
    Button nextImageButton;

    @BindView(R.id.album_act_imageview1)
    ImageView imageView;

    private List<File> files;

    private List<String> names;

    private int position;

    @Override
    protected int layoutId() {
        return R.layout.activity_album;
    }

    @Override
    protected void created(Bundle bundle) {
        data = new ArrayList<>();
        Materials materials = (Materials) getIntent().getSerializableExtra(MATERIALS);
        if (materials != null) {
            List<Materials.AlbumResource> resourceList = sortResourceList(materials);
            GetFileAndName getFileAndName = new GetFileAndName(resourceList).invoke();
            files = getFileAndName.getFiles();
            names = getFileAndName.getNames();
        }
    }

    @NonNull
    private List<Materials.AlbumResource> sortResourceList(Materials materials) {
        List<Materials.AlbumResource> resourceList = materials.getAlbumResourceList();
        Collections.sort(resourceList, new Comparator<Materials.AlbumResource>() {
            @Override
            public int compare(Materials.AlbumResource o1, Materials.AlbumResource o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });
        return resourceList;
    }

    @Override
    protected void resumed() {
        showImageAndName(0);
    }

    private void showImageAndName(int position) {
        if (files != null) {
            if (!files.isEmpty()) {
                if (position < files.size()) {
                    Glide.with(this).load(files.get(position)).into(imageView);
                }
            }
        }
    }

    public static Intent newIntent(Context packageContext, Materials materials) {
        Intent intent = new Intent(packageContext, AlbumActivity.class);
        intent.putExtra(MATERIALS, materials);
        return intent;
    }

    private class GetFileAndName {
        private List<Materials.AlbumResource> resourceList;
        private List<File> files;
        private List<String> names;

        GetFileAndName(List<Materials.AlbumResource> resourceList) {
            this.resourceList = resourceList;
        }

        List<File> getFiles() {
            return files;
        }

        List<String> getNames() {
            return names;
        }

        GetFileAndName invoke() {
            files = new ArrayList<>();
            names = new ArrayList<>();
            for (Materials.AlbumResource resource : resourceList) {
                File file = new File(resource.getResourceStorageAddress());
                files.add(file);
                names.add(resource.getName());
            }
            return this;
        }
    }
}
