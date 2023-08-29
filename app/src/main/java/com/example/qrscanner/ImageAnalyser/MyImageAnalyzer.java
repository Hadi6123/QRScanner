package com.example.qrscanner.ImageAnalyser;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.fragment.app.FragmentManager;

import com.example.qrscanner.DialogBridge.Bottom_dialog;
import com.example.qrscanner.DialogBridge.DialogInterface;
import com.example.qrscanner.UrlState.FailedState;
import com.example.qrscanner.UrlState.FetchUrlState;
import com.example.qrscanner.UrlState.SuccessState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyImageAnalyzer implements ImageAnalysis.Analyzer {

    private FetchUrlState fetchUrlState;
    private FragmentManager fragmentManager;
    private DialogInterface bd;

    public MyImageAnalyzer(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        bd = new Bottom_dialog();
        this.fetchUrlState = new SuccessState();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void analyze(@NonNull ImageProxy image) {
        scanbarcode(image);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void scanbarcode(ImageProxy image) {
        @SuppressLint("UnsafeOptInUsageError") Image image1 = image.getImage();
        assert image1 != null;
        InputImage inputImage = InputImage.fromMediaImage(image1, image.getImageInfo().getRotationDegrees());
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_QR_CODE,
                                Barcode.FORMAT_AZTEC)
                        .build();
        BarcodeScanner scanner = BarcodeScanning.getClient(options);
        Task<List<Barcode>> result = scanner.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        readerBarcodeData(barcodes);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        readerBarcodeData(null);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<List<Barcode>> task) {
                        image.close();
                    }
                });
    }

    private void readerBarcodeData(List<Barcode> barcodes) {

        if (barcodes == null){
            fetchUrlState = new FailedState();
            return;
        }

        fetchUrlState = new SuccessState();
        for (Barcode barcode : barcodes) {

            int valueType = barcode.getValueType();
            // See API reference for complete list of supported types
            switch (valueType) {
                case Barcode.TYPE_WIFI:
                    break;
                case Barcode.TYPE_URL:
                    if(!bd.isAdded()) {
                        bd.show(fragmentManager,"");
                    }
                    bd.fetchurl(fetchUrlState.fetchBarcodeUrl(barcode));
                    //bd.fetchurl(barcode.getUrl().getUrl());

                    break;
            }
        }
    }
}
