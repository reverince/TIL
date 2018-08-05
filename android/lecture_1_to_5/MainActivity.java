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
	// 2강
	public static String EXTRA_MESSAGE = "co.codebasic.extra_message";
	EditText messsageEditText;
	// 3강 이벤트 처리
	EditText itemEditText;
	ListView todoListView;
	// 4강 인텐트
	Button btn1, btn2, btnDialNumber;
	EditText editText;
	// 5강 브로드캐스트 리시버
	Button btnBloodTypeQuery;
	public static int BLOOD_TYPE_CHOICE = 0;
	TextView bloodTypeTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 2강
		messageEditText = (EditText)findViewById(R.id.edit_message);
		
		// 3강 이벤트 처리
		itemEditText = (EditText)findViewById(R.id.todo_item_edit);
		todoListView = (ListView)findViewById(R.id.todo_list_view);
		// 리스트 초기화 및 리스트뷰와 연결
		ArrayList<String> itemList = new ArrayList<String>();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
		todoListView.setAdapter(adapter);
		itemList.add("안드로이드");
		itemList.add("iOS");
		// 사용자 입력값 추가 이벤트
		itemEditText.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						String item = itemEditText.getText().toString();
						itemList.add(0, item); // 입력값을 리스트 맨 위에 추가
						adapter.notifyDataSetChanged(); // 어댑터 갱신
						itemEditText.setText("");
					}
				}
				return false;
			}
		});
		
		// 4강 인텐트
		btn1 = (Button)findViewById(R.id.btn_start_activity_1);
		btn2 = (Button)findViewById(R.id.btn_start_activity_1);
		btnDialNumber = (Button)findViewById(R.id.btn_dial_number);
		editText = (EditText)findViewById(R.id.edit_text);
		// 온클릭 이벤트
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
				startActivity(intent);
			}
		});
		// 전화 액션 온클릭 이벤트
		btnDialNumber.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				// 전화번호 지정
				intent.setData(Uri.parse("tel:010-1234-5678"));
				startActivity(intent);
			}
		});
		
		// 5강 브로드캐스트 리시버
		btnBloodTypeQuery = (Button)findViewById(R.id.btn_blood_type_query);
		bloodTypeTextView = (TextView)findViewById(R.id.blood_type_text_view);
		// 온클릭 이벤트
		btnBloodTypeQuery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, BloodTypeChoiceActivity.class);
				startActivityForResult(intent, BLOOD_TYPE_CHOICE);
			}
		});
		
	} // End of onCreate
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 결과를 반환한 액티비티 식별
		if (requestCode = BLOOD_TYPE_CHOICE) {
			if (resultCode = RESULT_OK) {
				String bloodType = data.getStringExtra(BloodTypeChoiceActivity.EXTRA_BLOOD_TYPE);
				bloodTypeTextView.setText(bloodType);
			}
		}
	}
	
	// 2강: 같은 액티비티 안에서 메시지 출력
	public void showMessage(View view) {
		// 사용자가 입력한 텍스트 읽기
		String msg = messageEditText.getText().toString();
		// 입력된 텍스트 삭제
		messageEditText.setText("");
		// 화면 하단에 메시지 출력
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	// 2강: 다른 액티비티에서 메시지 출력
	public void sendMessage(View view) {
		// 전환할 액티비티 설정
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		// 사용자 입력 텍스트 읽기
		String msg = messageEditText.getText().toString();
		// 액티비티에 보낼 메시지 담기
		intent.putExtra(EXTRA_MESSAGE, msg);
		// 액티비티 시작
		startActivity(intent);
	}
	
}
