package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    CameraSurfaceView cameraSurfaceView;
    ImageView imageView2;
    FrameLayout container;
    int imageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        cameraSurfaceView = new CameraSurfaceView(this);
        container.addView(cameraSurfaceView);
        imageView2 = findViewById(R.id.imageView2);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imageIndex ==0){
                    capture();
                }else if(imageIndex == 1){
                    imageView2.setVisibility(View.GONE);
                    container.removeView(cameraSurfaceView);
                    container.addView(cameraSurfaceView);
                    container.setVisibility(View.VISIBLE);

                    imageIndex = 0;


                }

            }
        });
        AutoPermissions.Companion.loadAllPermissions(this,101);

    }

    public void capture(){
        cameraSurfaceView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {


                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Matrix matrix = new Matrix();

                matrix.postRotate(90);

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                    container.setVisibility(View.GONE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageBitmap(rotatedBitmap);

                    imageIndex = 1;


                /**
                try {
                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),bitmap,"title",null);
                    Intent Intent = new Intent(getApplicationContext(), ImageActivity.class);
                    Intent.putExtra("image", Uri.parse(path));
                    startActivityForResult(Intent, 101);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();

                }
                 **/

            }
        });
    }

    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }
}