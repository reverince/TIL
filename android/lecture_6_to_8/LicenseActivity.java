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

public class LicenseActivity extends ActionBarActivity {
	TextView licenseTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		licenseTextView = (TextView)findViewById(R.id.license_text_view);
		String licenseBody = readFromResource(R.raw.gpl);
		licenseTextView.setText(licenseBody);
		
	} // End of onCreate
	
	private String readFromResource(int resId) {
		String text = "", line;
		InputStream is = getResources().openRawResource(resId);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReaader(is));
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
			text = stringBuilder.toString();
			Log.d("LICENSE", "리소스에서 문자열 읽어들임.");
		} catch (IOException ex) {
			ex.printStackTree();
		}
		
		return text;
	}
}
