package com.example.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.pedro.library.AutoPermissions;

import java.io.EOFException;
import java.util.Optional;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder holder;
    Camera camera = null;

    public CameraSurfaceView(Context context) {
        super(context);

        init(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);


        init(context);
    }

    public void init(Context context){
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        try {
            camera = Camera.open();
            camera.setPreviewDisplay(holder);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        camera.setDisplayOrientation(90);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public boolean capture(Camera.PictureCallback callback) {
        if(camera != null){
            camera.takePicture(null, null, callback);
            return true;
        }else {
            return false;
        }
    }
}
