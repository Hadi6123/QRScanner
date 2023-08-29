package com.example.qrscanner.UrlState;

import com.google.mlkit.vision.barcode.Barcode;

public class SuccessState implements FetchUrlState{
    @Override
    public String fetchBarcodeUrl(Barcode barcode) {
        return barcode.getUrl().getUrl();
    }
}
