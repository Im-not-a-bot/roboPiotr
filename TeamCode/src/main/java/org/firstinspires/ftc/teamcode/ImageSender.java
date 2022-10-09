package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageFormat;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureRequest;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSequenceId;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSession;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCharacteristics;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraException;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraFrame;
import org.firstinspires.ftc.robotcore.internal.collections.EvictingBlockingQueue;
import org.firstinspires.ftc.robotcore.internal.system.ContinuationSynchronizer;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ImageSender {

/*
    /** Do something with the frame
    public void onNewFrame(Bitmap frame) {
        saveBitmap(frame);
        frame.recycle(); // not strictly necessary, but helpful
    }

    //----------------------------------------------------------------------------------------------
    // Camera operations
    //----------------------------------------------------------------------------------------------

    public void initializeFrameQueue(int capacity) {
        // The frame queue will automatically throw away bitmap frames if they are not processed
        // quickly by the OpMode. This avoids a buildup of frames in memory
        frameQueue = new EvictingBlockingQueue<Bitmap>(new ArrayBlockingQueue<Bitmap>(capacity));
        frameQueue.setEvictAction(new Consumer<Bitmap>() {
            @Override public void accept(Bitmap frame) {
                // RobotLog.ii(TAG, "frame recycled w/o processing");
                frame.recycle(); // not strictly necessary, but helpful
            }
        });
    }

    public void openCamera() {
        if (camera != null) return; // be idempotent

        Deadline deadline = new Deadline(Integer.MAX_VALUE, TimeUnit.SECONDS);
        camera = cameraManager.requestPermissionAndOpenCamera(deadline, cameraName, null);
        if (camera == null) {
            error("camera not found or permission to use not granted: %s", cameraName);
        }
    }

    public void startCamera() {
        if (cameraCaptureSession != null) return; // be idempotent

        // YUY2 is supported by all Webcams, per the USB Webcam standard: See "USB Device Class Definition
        // for Video Devices: Uncompressed Payload, Table 2-1". Further, often this is the *only*
        // image format supported by a camera


        final int imageFormat = ImageFormat.YUY2;

        // Verify that the image is supported, and fetch size and desired frame rate if so
        CameraCharacteristics cameraCharacteristics = cameraName.getCameraCharacteristics();
        if (!contains(cameraCharacteristics.getAndroidFormats(), imageFormat)) {
            error("image format not supported");
            return;
        }
        final Size size = cameraCharacteristics.getDefaultSize(imageFormat);
        final int fps = cameraCharacteristics.getMaxFramesPerSecond(imageFormat, size);

        // Some of the logic below runs asynchronously on other threads. Use of the synchronizer
        // here allows us to wait in this method until all that asynchrony completes before returning.
        final ContinuationSynchronizer<CameraCaptureSession> synchronizer = new ContinuationSynchronizer<>();
        try {
            // Create a session in which requests to capture frames can be made
            camera.createCaptureSession(Continuation.create(callbackHandler, new CameraCaptureSession.StateCallbackDefault() {
                @Override public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        // The session is ready to go. Start requesting frames
                        final CameraCaptureRequest captureRequest = camera.createCaptureRequest(imageFormat, size, fps);
                        session.startCapture(captureRequest,
                                new CameraCaptureSession.CaptureCallback() {
                                    @Override public void onNewFrame(@NonNull CameraCaptureSession session, @NonNull CameraCaptureRequest request, @NonNull CameraFrame cameraFrame) {
                                        // A new frame is available. The frame data has <em>not</em> been copied for us, and we can only access it
                                        //for the duration of the callback. So we copy here manually.
                                        Bitmap bmp = captureRequest.createEmptyBitmap();
                                        cameraFrame.copyToBitmap(bmp);
                                        frameQueue.offer(bmp);
                                    }
                                },
                                Continuation.create(callbackHandler, new CameraCaptureSession.StatusCallback() {
                                    @Override public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession session, CameraCaptureSequenceId cameraCaptureSequenceId, long lastFrameNumber) {
                                        RobotLog.ii(TAG, "capture sequence %s reports completed: lastFrame=%d", cameraCaptureSequenceId, lastFrameNumber);
                                    }
                                })
                        );
                        synchronizer.finish(session);
                    } catch (CameraException |RuntimeException e) {
                        RobotLog.ee(TAG, e, "exception starting capture");
                        error("exception starting capture");
                        session.close();
                        synchronizer.finish(null);
                    }
                }
            }));
        } catch (CameraException|RuntimeException e) {
            RobotLog.ee(TAG, e, "exception starting camera");
            error("exception starting camera");
            synchronizer.finish(null);
        }

        // Wait for all the asynchrony to complete
        try {
            synchronizer.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Retrieve the created session. This will be null on error.
        cameraCaptureSession = synchronizer.getValue();
    }

    public void stopCamera() {
        if (cameraCaptureSession != null) {
            cameraCaptureSession.stopCapture();
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }
    }

    public void closeCamera() {
        stopCamera();
        if (camera != null) {
            camera.close();
            camera = null;
        }
    }

    //----------------------------------------------------------------------------------------------
    // Utilities
    //----------------------------------------------------------------------------------------------

    public void error(String msg) {
        telemetry.log().add(msg);
        telemetry.update();
    }
    public void error(String format, Object...args) {
        telemetry.log().add(format, args);
        telemetry.update();
    }

    public boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) return true;
        }
        return false;
    }

    public void saveBitmap(Bitmap bitmap) {


        int[] pixels = new int[bitmap.getHeight()*bitmap.getWidth()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        telemetry.addLine(String.valueOf(pixels.length));
        Bitmap t1=createContrast(bitmap,1);


        boolean[] imgdat = new boolean[bitmap.getWidth()* bitmap.getHeight()];

        byte[] data = new byte[(imgdat.length-(imgdat.length%8))/8 +2];


        for(int y=0;y<t1.getHeight();y++){for(int x=0;x<t1.getWidth();x++){
            if(bitmap.getPixel(x,y)== Color.WHITE){imgdat[y* bitmap.getWidth()+x]=true;
            }
        }}
        telemetry.addLine("orig. arr. l: "+imgdat.length);
        for(int i=0;i< (imgdat.length-(imgdat.length % 8))/8;i++){
            data[i]=ConvertBoolArrayToByte(Arrays.copyOfRange(imgdat, i*8, (i+1)*8));
        }

        //--------------encodes image width info into last 2 bytes of array
        data[data.length-2]= (byte)(((short)bitmap.getWidth()) & 0xff);
        data[data.length-1] = (byte)(((short) bitmap.getWidth() >> 8) & 0xff);
        telemetry.addLine("mod. arr. l: "+data.length);

        telemetry.addLine("sending to 192.168.1.9 (Peat's laptop)");
        telemetry.addLine("If it is not connected, it will send to last connected device");

        s.data=data;

        telemetry.addLine("image sent");

        telemetry.update();

//        File file = new File(captureDirectory, String.format(Locale.getDefault(), "webcam-frame.jpg"));
//        try {
//            try (FileOutputStream outputStream = new FileOutputStream(file)) {
//                t1.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                telemetry.log().add("captured %s", file.getName());
//            }
//        } catch (IOException e) {
//            RobotLog.ee(TAG, e, "exception in saveBitmap()");
//            error("exception saving %s", file.getName());
//        }


    }


    public static Bitmap createContrast(Bitmap src, double value) {
// image size
        int width = src.getWidth();
        int height = src.getHeight();
// create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
// color information
        int A, R, G, B;
        int pixel;
// get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

// scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.red(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.red(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        return bmOut;}

    private static byte ConvertBoolArrayToByte(boolean[] source)
    {
        byte result = 0;
        // This assumes the array never contains more than 8 elements!
        int index = 8 - source.length;

        // Loop through the array
        for (boolean b : source)
        {
            // if the element is 'true' set the bit at that position
            if (b)
                result |= (byte)(1 << (7 - index));

            index++;
        }

        return result;
    }
    */
}

