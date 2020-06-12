package com.example.cosmingtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class CropActivity extends AppCompatActivity {

    public static Activity crop_activity;
    byte[] byte_array;
    String img_base64 = "";
    ArrayList<String> read_words = new ArrayList<>();
    ImageView image_view;
    Button re_search;
    Button go_result;
    SearchActivity sa = (SearchActivity)SearchActivity.search_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        crop_activity = CropActivity.this;
        sa.finish();

        image_view = findViewById(R.id.cr_image);
        re_search = findViewById(R.id.re_search);
        go_result = findViewById(R.id.go_result);

        byte_array = getIntent().getByteArrayExtra("image");
        Bitmap bm = BitmapFactory.decodeByteArray(byte_array, 0, byte_array.length);

        image_view.setImageBitmap(bm);
        img_base64 = Base64.encodeToString(byte_array, Base64.DEFAULT);

        re_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search_intent = new Intent(CropActivity.this, SearchActivity.class);
                startActivity(search_intent);
            }
        });

        go_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent result_intent = new Intent(CropActivity.this, ResultActivity.class);
                result_intent.putExtra("image", byte_array);
                startActivity(result_intent);*/
                OCRAsyncTask task = new OCRAsyncTask();
                task.execute();
            }
        });
    }

    private static class OCRAsyncTask extends AsyncTask<Void, Void, String> {
        String OCR_URL = "https://b12841e405a34032b6a5fd63f068b23d.apigw.ntruss.com/custom/v1/1615/eada0214517a4a0bf4b65aaed4d9146974afa129efd07030d26e13f63bba3638/general";
        String OCR_KEY = "VklxeHJhUldVRWdUdE5SeWNDdVFzWmNyZ1NuYVRUWkg=";
        WeakReference activity_reference = new WeakReference(crop_activity);
        CropActivity activity = (CropActivity)activity_reference.get();


        @Override
        protected String doInBackground(Void... params) {
            // 1. 웹 연결
            URL url;
            HttpURLConnection connection = null;

            try {
                url = new URL(OCR_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("X-OCR-SECRET", OCR_KEY);
                connection.connect();
            }
            catch(MalformedURLException | ProtocolException exception) {
                exception.printStackTrace();
            }
            catch(IOException io){
                io.printStackTrace();
            }

            // 2. JSON 객체 생성
            JSONObject data = new JSONObject();

            try {
                JSONObject imageData = new JSONObject();
                imageData.put("format", "jpg");
                imageData.put("name", "sample");
                imageData.put("data", activity.img_base64);

                JSONArray image = new JSONArray();
                image.put(imageData);

                data.put("version", "V2");
                data.put("requestId", "sample");
                data.put("timestamp", 0);
                data.put("lang", "ko");
                data.put("images", image);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // 3. JSON 값 전송
            OutputStreamWriter output;
            BufferedReader input;
            try {
                output = new OutputStreamWriter(connection.getOutputStream());
                output.write(data.toString());
                output.flush();
                output.close();

                input = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                activity.parseJSON(input.readLine());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return ""; //return이 필요함

        }

    }

    void parseJSON(String input) throws JSONException {
        //String inputLine = String(input.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8);

            JSONArray fields = (new JSONObject(input).getJSONArray("images").getJSONObject(0)).getJSONArray("fields");

            for (int i = 0; i < fields.length(); i++) {
                String inferText = (fields.getJSONObject(i)).getString("inferText");
                read_words.add(inferText);
            }


        Intent result_intent = new Intent(CropActivity.this, ResultActivity.class);
        result_intent.putExtra("image", byte_array);
        result_intent.putExtra("read_words", read_words);
        startActivity(result_intent);
    }

}
