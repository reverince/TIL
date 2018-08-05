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

public class MainActivity extends ActionBarActivity {
	Button editProfileBtn, showLicenseBtn;
	TextView nameTextView;
	EditText memoEditText;
	
	static final String MEMO_FILE = "memo";
	
	// onCreate는 액티비티가 생성될 때만 실행됨
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editProfileBtn = (Button)findViewById(R.id.btn_edit_profile);
		showLicenseBtn = (Button)findViewById(R.id_btn_show_license);
		nameTextView = (TextView)findViewById(R.id.name_text_view);
		memoEditText = (EditText)findViewById(R.id.memo_edit_text);
		
		// 5장 온클릭 이벤트
		editProfileBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
				startActivity(intent);
			}
		});
		// 6장 온클릭 이벤트
		editProfileBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LicenseActivity.class);
				startActivity(intent);
			}
		});
		// 6장 : 온키 이벤트
		memoEditText.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						String memo = memoEditText.getText().toString();
						//내부 저장소에 저장
						writeToFile(MEMO_FILE, memo);
					}
				}
				return false;
			}
		})
		
	} // End of onCreate
	
	// onResume은 액티비티로 돌아올 때마다 실행됨
	@Override
	protected void onResume() {
		
		// 5장 : 셰어드 프리퍼런스 확인
		SharedPreferences shardPreferences = getSharedPreferences(ProfileActivity.PREF_PROFILE, Activity.MODE_PRIVATE);
		String name = shardPreferences.getString(ProfileActivity.PROFILE_NAME, "아무개"); // 저장된 이름이 없으면 기본값 "아무개"
		nameTextView.setText(name);
		
		// 6장 : 내부 저장소
		String memo = readFromFile(getFilesDir(), MEMO_FILE);
		memoEditText.setText(memo);
	}
	
	// 6장 : 내부 저장소에 저장
	private void writeToFile(String filename, String body) {
		try {
			FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(body.getBytes());
			fos.close();
			Log.d("MEMO", "메모가 파일에 저장됨.");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	// 6장 : 내부 저장소에서 로드
	private String readFromFile(File dir, String filename) {
		String text = "", line;
		File file = new File(dir, filename);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
			text = stringBuilder.toString();
			Log.d("MEMO", "파일에서 문자열 읽어들임.");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return text;
	}
}
