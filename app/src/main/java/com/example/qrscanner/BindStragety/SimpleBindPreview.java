package com.example.qrscanner.BindStragety;

import android.util.Size;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.lifecycle.LifecycleOwner;

import com.example.qrscanner.ImageAnalyser.MyImageAnalyzer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleBindPreview implements BindInterface{

    private ExecutorService cameraExecutor;

    public SimpleBindPreview(){
        cameraExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void bindPreview(LifecycleOwner obj, MyImageAnalyzer analyzer, PreviewView previewView, ProcessCameraProvider processCameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        ImageCapture imageCapture;
        imageCapture = new ImageCapture.Builder().build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280,720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(cameraExecutor,analyzer);
        processCameraProvider.unbindAll();
        processCameraProvider.bindToLifecycle(obj, cameraSelector, preview, imageCapture, imageAnalysis);
    }
}
