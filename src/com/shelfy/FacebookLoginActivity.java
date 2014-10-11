package com.shelfy;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

public class FacebookLoginActivity extends FragmentActivity{
	
    SharedPreferences prefs;
	private UiLifecycleHelper uiHelper;
	
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

	private LoginButton loginBtn;

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

			uiHelper = new UiLifecycleHelper(this, statusCallback);
			uiHelper.onCreate(savedInstanceState);
			
			setContentView(R.layout.facebook_login);
	        
			loginBtn = (LoginButton) findViewById(R.id.authButton);
			loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
				@Override
				public void onUserInfoFetched(GraphUser user) {
					if (user != null) {
						Log.d("Hello", user.getName());
					} else {
						Log.d("Hello", "NOT");
					}
				}
			});        
	}
	 
		private Session.StatusCallback statusCallback = new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (state.isOpened()) {
					
					Intent intent = new Intent(FacebookLoginActivity.this, NewMessagesActivity.class);
					startActivity(intent);
					
				} else if (state.isClosed()) {
					Log.d("FacebookSampleActivity", "Facebook session closed");
				}
			}
		};
		
		public boolean checkPermissions() {
			Session s = Session.getActiveSession();
			if (s != null) {
				return s.getPermissions().contains("publish_actions");
			} else
				return false;
		}

		public void requestPermissions() {
			Session s = Session.getActiveSession();
			if (s != null)
				s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
						this, PERMISSIONS));
		}

		@Override
		public void onResume() {
			super.onResume();
			uiHelper.onResume();
		}

		@Override
		public void onPause() {
			super.onPause();
			uiHelper.onPause();
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			uiHelper.onDestroy();
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			uiHelper.onActivityResult(requestCode, resultCode, data);
		}

		@Override
		public void onSaveInstanceState(Bundle savedState) {
			super.onSaveInstanceState(savedState);
			uiHelper.onSaveInstanceState(savedState);
		}
}
