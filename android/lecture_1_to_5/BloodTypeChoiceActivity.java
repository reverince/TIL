package co.codebasic.android.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;

public class BloodTypeChoiceActivity extends ActionBarActivity {
	// 5강
	Button btnTypeA, btnTypeB, btnTypeO, btnTypeAB;
	public static final String EXTRA_BLOOD_TYPE = "co.codebasic.extra_blood_type";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blood_type_choice);
		
		// 5강
		btnTypeA = (Button)findViewById(R.id.btn_blood_type_A);
		btnTypeB = (Button)findViewById(R.id.btn_blood_type_B);
		btnTypeO = (Button)findViewById(R.id.btn_blood_type_O);
		btnTypeAB = (Button)findViewById(R.id.btn_blood_type_AB);
		
		btnTypeA.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reportBloodType("A");
			}
		});
		btnTypeB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reportBloodType("B");
			}
		});
		btnTypeO.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reportBloodType("O");
			}
		});
		btnTypeAB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reportBloodType("AB");
			}
		});
		
	} // End of onCreate
	
	// 선택된 혈액형 문자열을 엑스트라로 저장하고 액티비티 종료
	private void reportBloodType(String bloodType) {
		Intent intent = new Intent();
		intent.putExtra(EXTRA_BLOOD_TYPE, bloodType);
		setResult(RESULT_OK, intent);
		finish();
	}
	
}