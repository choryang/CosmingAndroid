package com.example.cosmingtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;

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
    String uri;
    Uri image_uri;
    ArrayList<String> read_words = new ArrayList<>();
    LinearLayout crop_btn;
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

        crop_btn = findViewById(R.id.cropbtn);
        image_view = findViewById(R.id.cr_image);
        re_search = findViewById(R.id.re_search);
        go_result = findViewById(R.id.go_result);

        byte_array = getIntent().getByteArrayExtra("image");
        uri = getIntent().getStringExtra("uri");
        image_uri = Uri.parse(uri);
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
                OCRAsyncTask task = new OCRAsyncTask();
                task.execute();
            }
        });

        crop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity(image_uri)
                        .start(CropActivity.this);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of CropImageActivity
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                image_view.setImageURI(result.getUri());
                Toast.makeText(
                        this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
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

        //이전 성분 x좌표
        int prevX = 0;
        int nextX;//현재 성분 x좌표
        int comma = -1;
        StringBuffer prevText = new StringBuffer();
        StringBuffer nextText= new StringBuffer();

        JSONArray fields = (new JSONObject(input).getJSONArray("images").getJSONObject(0)).getJSONArray("fields");

        for (int i = 0; i < fields.length(); i++) {
            String inferText = (fields.getJSONObject(i)).getString("inferText"); // 끝에 ,존재여부 파악을 위한 온전한 성분명 문자열
            String splitText[] = (fields.getJSONObject(i)).getString("inferText").split(",");
            nextX = (fields.getJSONObject(i)).getJSONObject("boundingPoly").getJSONArray("vertices").getJSONObject(2).getInt("x");
            if (splitText.length > 1){// 하나의 성분명 사이에 ,가 포함된 경우
                nextText.replace(0, nextText.length(), splitText[0]);
                for (int j = 1; j < splitText.length; j++) {//,로 다시 이어 붙인다.
                    nextText.append(",");
                    nextText.append(splitText[j]);
                }
            }
            else {//,가 포함되어 있지 않은 경우
                nextText.replace(0, nextText.length(), splitText[0]);
            }

            if (prevX > nextX && comma == 0 && read_words.size() > 1) {//줄바꿈이 생긴 경우
                // 줄바꿈을 기준으로 하나의 성분인 경우 >> 줄바뀜이 일어났고 ,로 구분되지 않았기 때문에 하나의 성분으로 판단
                read_words.remove(prevText.toString());
                prevText.append(nextText.toString()); // 이전의 성분명과 현재 성분명을 이어 붙인다.
                nextText.replace(0, nextText.length(), prevText.toString());
            }

            Log.i("text", nextText.toString());
            read_words.add(nextText.toString());
            prevX = nextX;
            prevText.replace(0, prevText.length(), nextText.toString());

            if (inferText.charAt(inferText.length() - 1) == ',') {
                comma = 1;
            }
            else {
                comma = 0;
            }

        }

        Intent result_intent = new Intent(CropActivity.this, ResultActivity.class);
        result_intent.putExtra("image", byte_array);
        result_intent.putExtra("read_words", read_words);
        startActivity(result_intent);
    }

}