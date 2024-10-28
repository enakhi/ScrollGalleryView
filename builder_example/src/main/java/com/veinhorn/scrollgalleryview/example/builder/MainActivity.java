package com.veinhorn.scrollgalleryview.example.builder;

import static ogbe.ozioma.com.glideimageloader.dsl.DSL.images;
import static ogbe.ozioma.com.glideimageloader.dsl.DSL.video;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.builder.GallerySettings;


public class MainActivity extends FragmentActivity {
    private ScrollGalleryView galleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        galleryView = ScrollGalleryView
                .from((ScrollGalleryView) findViewById(R.id.scroll_gallery_view))
                .settings(
                        GallerySettings
                                .from(getSupportFragmentManager())
                                .thumbnailSize(100)
                                .enableZoom(true)
                                .build()
                )
                .onImageClickListener(new ScrollGalleryView.OnImageClickListener() {
                    @Override
                    public void onClick(int position) {
                        Toast.makeText(MainActivity.this, "image position = " + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .onImageLongClickListener(new ScrollGalleryView.OnImageLongClickListener() {
                    @Override
                    public void onClick(int position) {
                        Toast.makeText(MainActivity.this, "image position = " + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .add(images(
                        "https://images.freeimages.com/images/large-previews/56d/peacock-1169961.jpg",
                        "https://images.freeimages.com/images/large-previews/866/butterfly-1-1535829.jpg"
                ))
                .add(video("https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/360/Big_Buck_Bunny_360_10s_30MB.mp4", R.mipmap.default_video))
                .build();
    }
}
