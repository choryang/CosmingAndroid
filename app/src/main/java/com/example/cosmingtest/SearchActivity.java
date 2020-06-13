package com.example.cosmingtest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {

    // 이미지 용량 조절
    private final int REQUEST_WIDTH = 512;
    private final int REQUEST_HEIGHT = 512;
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private static final int REQUEST_IMAGE_GALLERY = 101;
    public static Activity search_activity;
    private String image_file_path;
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
        image_file_path = image.getAbsolutePath();
        return image;
    }

    // 이미지 Resize 함수
    private int setSimpleSize(BitmapFactory.Options options, int requestWidth, int requestHeight){
        // 이미지 사이즈를 체크할 원본 이미지 가로/세로 사이즈를 임시 변수에 대입.
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;

        // 원본 이미지 비율인 1로 초기화
        int size = 1;

        // 해상도가 깨지지 않을만한 요구되는 사이즈까지 2의 배수의 값으로 원본 이미지를 나눈다.
        while(requestWidth < originalWidth || requestHeight < originalHeight){
            originalWidth = originalWidth / 2;
            originalHeight = originalHeight / 2;

            size = size * 2;
        }
        return size;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;
        Bitmap temp_bitmap;
        ExifInterface exif;
        int exifOrientation;
        int exifDegree;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            /*if (ca != null) {
                ca.finish(); // 이전의 이미지 편집화면 종료
            }
            // inJustDecodeBounds = true일때
            //bitmap은 반환하지않고, options 변수에만 값이 대입된다.
            BitmapFactory.decodeFile(image_file_path, options);
            options.inSampleSize = setSimpleSize(options, REQUEST_WIDTH, REQUEST_HEIGHT);
            options.inJustDecodeBounds = false;
            temp_bitmap = BitmapFactory.decodeFile(image_file_path, options);
            try {
                exif = new ExifInterface(image_file_path);
                if (exif != null) {
                    exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    exifDegree = exifOrientationToDegrees(exifOrientation);
                } else {
                    exifDegree = 0;
                }
                bitmap = rotate(temp_bitmap, exifDegree);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }
        else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK) {
            if (ca != null) {
                ca.finish(); // 이전의 이미지 편집화면 종료
            }
            try {
                gallery_uri = data.getData();
                InputStream is = getContentResolver().openInputStream(data.getData());
                BitmapFactory.decodeStream(is, null, options);
                options.inSampleSize = setSimpleSize(options, REQUEST_WIDTH, REQUEST_HEIGHT);
                options.inJustDecodeBounds = false;
                is = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(is, null, options);
                is.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "이미지를 받아올 수 없습니다.", Toast.LENGTH_SHORT).show();
        }

        if( bitmap != null ) {
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byte_array = stream.toByteArray();
                Intent intentView = new Intent(getApplicationContext(), CropActivity.class);
                intentView.putExtra("image", byte_array);
                intentView.putExtra("uri", gallery_uri.toString());
                startActivity(intentView);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "이미지를 받아올 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }


    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
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