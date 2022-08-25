package com.example.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private String url = "https://i.pinimg.com/originals/75/3f/43/753f4372686dd4fcff1c165c292cbb28.jpg";
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }


    public void onClickDownload(View view) {
        DownloadTaskImg downloadTaskImg = new DownloadTaskImg();
        Bitmap bitmap = null;
        try {
            bitmap = downloadTaskImg.execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
    }

    private static class DownloadTaskImg extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {

            URL url = null;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream input = urlConnection.getInputStream();
                Bitmap bitmap = null;
                bitmap = BitmapFactory.decodeStream(input);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}