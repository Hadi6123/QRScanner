package com.example.qrscanner.UrlState;

import com.google.mlkit.vision.barcode.Barcode;

public interface FetchUrlState {

    public String fetchBarcodeUrl(Barcode barcode);
}
