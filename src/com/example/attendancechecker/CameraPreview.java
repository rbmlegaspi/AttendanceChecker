package com.example.attendancechecker;

import java.io.IOException;


import android.util.Log;
import android.view.*;
import android.widget.Toast;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.content.Context;
import android.content.pm.PackageManager;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    public static Camera mCamera;
    public static int cameraId = 0;
    private static final String TAG = "Preview";
    private Context context;
    
    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        context = this.context;
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
        	mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    	if (mCamera != null) {
            /*
              Call stopPreview() to stop updating the preview surface.
            */
    		stopPreviewAndFreeCamera();
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
    
    private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}
  
   
    
  public Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
  private void stopPreviewAndFreeCamera() {

	    if (mCamera != null) {
	        /*
	          Call stopPreview() to stop updating the preview surface.
	        */
	        mCamera.stopPreview();
	    
	        /*
	          Important: Call release() to release the camera for use by other applications. 
	          Applications should release the camera immediately in onPause() (and re-open() it in
	          onResume()).
	        */
	        mCamera.release();
	        mCamera = null;
	    }
	}
}
