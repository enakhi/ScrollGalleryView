package com.veinhorn.example;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.github.ybq.android.spinkit.style.Wave;
import com.veinhorn.example.databinding.ActivityMainBinding;
import com.veinhorn.scrollgalleryview.MediaInfo;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ogbe.ozioma.com.glideimageloader.GlideImageLoader;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;



    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.scrollGalleryView
                .setThumbnailSize(200)
                .setZoom(true)
                .withHiddenThumbnails(false)
                .hideThumbnailsOnClick(true)
                .hideThumbnailsAfter(5000)
                .addOnImageClickListener((position) -> {
                    Log.i(getClass().getName(), "You have clicked on image #" + position);
                })
                .setFragmentManager(getSupportFragmentManager());

        Wave wave = new Wave();
        binding.spinKit.setIndeterminateDrawable(wave);

        ImagesFetcher fetcher = new ImagesFetcher();
        fetcher.execute();
    }

    private class ImagesFetcher extends AsyncTask<Void, Void, List<String>> {
        private static final String SERVER_URL = "https://www.freeimages.com/";
        private static final int IMAGES_COUNT = 6;

        @Override
        protected void onPreExecute() {
            binding.spinKit.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getImageUrls(getImageElements(), IMAGES_COUNT);
            } catch (IOException e) {
                Log.e(getClass().getName(), "Cannot load image urls", e);
            }
            return Collections.emptyList();
        }

        @Override
        protected void onPostExecute(List<String> imageUrls) {
            binding.spinKit.setVisibility(View.INVISIBLE);

            for (String imageUrl : imageUrls) {
                binding.scrollGalleryView.addMedia(
                        MediaInfo.mediaLoader(new GlideImageLoader(imageUrl))
                );
            }
        }

        public Elements getImageElements() throws IOException {
            return Jsoup.connect(SERVER_URL)
                    .get()
                    .getElementById("content")
                    .getElementsByTag("img");
        }

        public List<String> getImageUrls(Elements imageElms, int size) {
            List<String> images = new ArrayList<>();
            int counter = 0;
            for (Element imageElm : imageElms) {
                images.add(imageElm.attr("src")); // .replace("small-previews", "large-previews"));
                if (++counter == size) break;
            }
            return images;
        }
    }
}
