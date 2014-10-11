package com.shelfy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.mirasense.scanditsdk.ScanditSDKAutoAdjustingBarcodePicker;
import com.mirasense.scanditsdk.interfaces.ScanditSDK;
import com.mirasense.scanditsdk.interfaces.ScanditSDKListener;

public class ScanActivity extends Activity implements ScanditSDKListener{
	private ScanditSDK mPicker;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     // Instantiate the default barcode picker
        ScanditSDKAutoAdjustingBarcodePicker picker = new 
        ScanditSDKAutoAdjustingBarcodePicker(this, 
        		"M5utxlErEeSEdrtt3Wnj+7bDqd/wUZjevEJc6gMeaoM", 
        		ScanditSDKAutoAdjustingBarcodePicker.CAMERA_FACING_FRONT) ;
        // Specify the object that will receive the callback events
        picker.getOverlayView().addListener(this);

        setContentView(picker);
        mPicker = picker;
    }
	
	@Override
	protected void onResume() {
	    mPicker.startScanning();
	    super.onResume();
	}
	@Override
	protected void onPause() {
	    mPicker.stopScanning();
	    super.onPause();
	}
	@Override
	public void didCancel() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void didManualSearch(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void didScanBarcode(String arg0, String arg1) {
		// TODO Auto-generated method stub
		Log.d("DID SCAN", "ARG0: "+arg0 + "ARG1: "+arg1);
		
	}
}
