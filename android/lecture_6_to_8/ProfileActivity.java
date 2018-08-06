package co.codebasic.android.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Button;

public class ProfileActivity extends ActionBarActivity {
	EditText nameEditText, phoneEditText;
	Button submitBtn, completeBtn;
	
	public static final String PREF_PROFILE = "co.codebasic.pref_profile";
	public static final String PROFILE_NAME = "profile_name";
	public static final String PROFILE_EMAIL = "profile_email";
	public static final String PREF_PROFILE_ID = "profile_id";
	
	private ProfileDbHelper mDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 위젯 객체 참조
		nameEditText = (EditText)findViewById(R.id.name_edit_text);
		phoneEditText = (EditText)findViewById(R.id.phone_edit_text);
		submitBtn = (Button)findViewById(R.id.btn_submit);
		completeBtn = (Button)findViewById(R.id.btn_complete);
		
		// 온클릭 이벤트
		submitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = nameEditText.getText().toString();
				
				// 셰어드 프리퍼런스에 이름 저장
				SharedPreferences sharedPreferences = getSharedPreferences(PREF_PROFILE, Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString(PROFILE_NAME, name);
				editor.apply();
				
				finish();
			}
		});
		completeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// DB에 프로필 저장
				setProfileToDB();
				finish();
			}
		})
		
	} // End of onCreate
	
	private void setProfileToDB() {
		// 프로필 정보 가져오기
		String name = nameEditText.getText().toString();
		String phone = phoneEditText.getText().toString();
		
		// DB에 저장
		long profileId = mDbHelper.setProfile(name, phone);
		
		// SharedPreferences 객체에 프로필 ID 저장
		SharedPreferences sp = getSharedPreferences(PREF_PROFILE, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putLong(PREF_PROFILE_ID, profileId);
		
		Log.d(Activity.class.getCanonicalName(), "프로필 정보 저장");
	}
}
