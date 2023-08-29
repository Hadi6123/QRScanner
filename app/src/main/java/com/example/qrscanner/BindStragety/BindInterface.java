package com.example.qrscanner.BindStragety;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.lifecycle.LifecycleOwner;

import com.example.qrscanner.ImageAnalyser.MyImageAnalyzer;

public interface BindInterface {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void bindPreview(LifecycleOwner obj, MyImageAnalyzer analyzer, PreviewView previewView, ProcessCameraProvider processCameraProvider);
}
