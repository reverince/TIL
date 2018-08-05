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
	EditText nameEditText;
	Button submitBtn;
	
	public static final String PREF_PROFILE = "pref_profile";
	public static final String PROFILE_NAME = "profile_name";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 위젯 객체 참조
		nameEditText = (EditText)findViewById(R.id.name_edit_text);
		submitBtn = (Button)findViewById(R.id.btn_submit);
		
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
		})
		
	} // End of onCreate
	
}
