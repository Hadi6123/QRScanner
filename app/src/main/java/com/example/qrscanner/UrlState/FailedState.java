package com.example.qrscanner.UrlState;

import com.google.mlkit.vision.barcode.Barcode;

public class FailedState implements FetchUrlState{
    @Override
    public String fetchBarcodeUrl(Barcode barcode) {
        return "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSjjoJ0G5uM6SvT9IaTjEo-qIsSKH4tQy8hvFn2KJ40UAXIjP6OQwnXpstX3gv4Se9YYfM&usqp=CAU";
    }
}
