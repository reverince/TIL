package co.codebasic.android.myapplication;

import android.support.v7.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Button;

public class FriendsActivity extends ListActivity
	implements LoaderManager.LoaderCallbacks<Cursor> {
	
	SimpleCursorAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME};
		int toViews = {R.id.text1};
		
		mAdapter = new SimpleCursorAdapter(this, R.layout.simple_list_item_1, null, fromColumns, toViews, 0);
		setListAdapter(mAdapter);
		
		// 커서 로더 시작
		getLoaderManager().initLoader(0, null, this);
		
	} // End of onCreate
	
	// DB 비동기적 작업 수행
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME};
		String selection = "((" + ContactsContract.Data.DISPLAY_NAME + " not null) and (" + ContactsContract.Data.DISPLAY_NAME + " != ''))";
		
		// DB 질의 수행 후 결과 커서 반환
		return new CursorLoader(ContactsContract.Data.CONTENT_URI, projection, selection, null, null);
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// 커서가 갱신되는 경우
		mAdapter.swapCursor(data);
	}
	
	@Override
	public void onLoadReset(Loader<Cursor> loader) {
		// 커서 로더가 재설정되는 경우
		mAdapter.swapCursor(null);
	}
}
