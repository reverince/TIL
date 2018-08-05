package co.codebasic.android.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;

public class DisplayActivity extends ActionBarActivity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);

		// 2강
		// 보낸 메시지 읽기
		Intent intent = getIntent();
		String receivedMsg = receivedIntent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		// 텍스트 위젯에 메시지 출력
		TextView userMsgTextView = (TextView)findViewById(R.id.user_msg);
		userMsgTextView.setText(receivedMsg);

	} // End of onCreate

}