package com.shelfy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mirasense.scanditsdk.ScanditSDKAutoAdjustingBarcodePicker;
import com.mirasense.scanditsdk.interfaces.ScanditSDKListener;
import com.parse.Parse;

public class NewMessagesActivity extends FragmentActivity implements ScanditSDKListener  {

    SharedPreferences prefs;
    ArrayList<String> newMessages;

    View view;
    private int NUM_PAGES = 5;
    private NonSwipeableViewPager mPager;
    private PagerAdapter mPagerAdapter;
    int counter;
    
    ImageView shelfyViewCreate;
	Uri fileUri;
	ImageView capturedPicture;
	
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTabs();

        Parse.initialize(this, "ktHTWF8XYBLw3oMUjovySWPpJxZ0Jb5eaGyzB4V8",
        		"IcbuvbH9EMPCP9k5e3jOl5qbz0ni4pkUSxWVNPZs");
        
		capturedPicture = (ImageView) findViewById(R.id.imageView1);

        counter = 0;
        newMessages = new ArrayList<String>();
        shelfyViewCreate = (ImageView) findViewById(R.id.add_shelfy);
        shelfyViewCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (isCameraAvailable()) {
            		takePicture();
            	}
            }
        });   
        
     // Instantiate the default barcode picker
        ScanditSDKAutoAdjustingBarcodePicker picker = new ScanditSDKAutoAdjustingBarcodePicker(this,
        		"M5utxlErEeSEdrtt3Wnj+7bDqd/wUZjevEJc6gMeaoM", 1) ;
        // Specify the object that will receive the callback events
        picker.getOverlayView().addListener(this);
        
        // Instantiate a ViewPager and a PagerAdapter.
        /*mPager = (NonSwipeableViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        mPagerAdapter.notifyDataSetChanged();*/
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// check if its for capturing image only
		if (requestCode == 100) {
			if (resultCode == RESULT_OK) {
				// image capture successful
				// displaying it in image view
				displayCapturedImage();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),
						"Image capture cancelled", Toast.LENGTH_SHORT).show();
			} else {
				// failed to capture image
				Toast.makeText(getApplicationContext(), "Image capture failed",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * Creating file uri to store image
	 */
	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/**
	 * returning image / video
	 */
	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"Camera Example");

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("Camera Example", "Oops! Failed create "
						+ "Camera Example" + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == 1) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == 1) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}
	
	private void displayCapturedImage() {

		try {
			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
					options);

			capturedPicture.setImageBitmap(bitmap);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}
	
	protected boolean isCameraAvailable() {

		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// camera is present
			return true;
		} else {
			// camera is absent
			return false;
		}

	}
	
	protected void takePicture() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// create a file to save image
		fileUri = getOutputMediaFileUri(1);

		// specifying path to save image
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		// starting the image capture Intent
		startActivityForResult(intent, 100);

	}
    
    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("New Messages");
        actionBar.setCustomView(R.layout.action_bar_layout);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_skip:
            	Intent intent = new Intent(NewMessagesActivity.this, ScanActivity.class);
            	startActivity(intent);
            	
            	break;
            default:
                break;
        }

        return true;
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            NewMessagesFragment frag = new NewMessagesFragment();

            if (newMessages.size() > 0) {

                    Bundle args = new Bundle();
                    //args.putParcelable("message", newMessages.get(position));
                    frag.setArguments(args);
                    Log.d("COUNTER", "" + counter);

                    return frag;
            }   

            counter++;
            return frag;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void updateMessages() {

        if ((newMessages.size()-mPager.getCurrentItem())==1) {
            Log.d("NEW MESSAGES", "size == 0");
            finish();
        }
        else {
            Log.d("NEW MESSAGES", "size > 0");
            NUM_PAGES = newMessages.size();
            mPagerAdapter.notifyDataSetChanged();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                   /* mPagerAdapter.notifyDataSetChanged();
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    mPagerAdapter.notifyDataSetChanged();

                    if ((newMessages.size()-mPager.getCurrentItem()) > 1) {
                        Log.d("NEW MESSAGES", "CHECK ****");

                        getActionBar().setTitle(String.valueOf(newMessages.size()-mPager.getCurrentItem()) + " Ratings Left");
                    } else if ((newMessages.size()-mPager.getCurrentItem()) == 1) {
                        Log.d("NEW MESSAGES", "CHECK !!!!!!!!");
                        getActionBar().setTitle(String.valueOf(newMessages.size()-mPager.getCurrentItem()) + " Rating Left");
                    }
                    else {
                        finish();
                    }*/
                }
            }, 300);
        }
    }

    
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
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
		
	}
}
