package com.example.cosmingtest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private static final int REQUEST_IMAGE_GALLERY = 101;
    public static Activity search_activity;
    //private String image_file_path;
    private Uri photo_uri;
    private Uri gallery_uri;
    CropActivity ca = (CropActivity)CropActivity.crop_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_activity = SearchActivity.this;

        //권한 체크
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();


        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photo_file = null;
                    try {
                        photo_file = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photo_file != null) {
                        photo_uri = FileProvider.getUriForFile(getApplicationContext(),
                                getPackageName(),
                                photo_file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photo_uri);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });

        findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(); //기기 기본 갤러리 접근
                intent.setType("image/jpeg");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
            }
        });

        findViewById(R.id.closebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);//화면전환 애니메이션 삭제
            }
        });


    }

    @Override
    public void onBackPressed() { // 뒤로가기 버튼 눌렀을 때
        super.onBackPressed();
        overridePendingTransition(0, 0);//화면 전환 애니메이션 삭제
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //image_file_path = image.getAbsolutePath();
        return image;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (ca != null) {
                ca.finish(); // 이전의 이미지 편집화면 종료
            }
            Intent intentView = new Intent(getApplicationContext(), CropActivity.class);
            intentView.putExtra("uri", photo_uri.toString());
            startActivity(intentView);
        }
        else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK) {
            if (ca != null) {
                ca.finish(); // 이전의 이미지 편집화면 종료
            }
            gallery_uri = data.getData();
            Intent intentView = new Intent(getApplicationContext(), CropActivity.class);
            intentView.putExtra("uri", gallery_uri.toString());
            startActivity(intentView);
        }
        else {
            Toast.makeText(getApplicationContext(), "이미지를 받아올 수 없습니다.", Toast.LENGTH_SHORT).show();
        }

    }

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부됨", Toast.LENGTH_SHORT).show();
        }
    };
}