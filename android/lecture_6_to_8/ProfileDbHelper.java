package co.codebasic.myapplication;

import android.database.sqlite.SQLiteOpenHelper;

public class ProfileDbHelper extends SqLiteOpenHelper {
	public class ProfileContract {
		public class ProfileEntry implements BaseColumns {
			// 객체 생성 방지
			private ProfileEntry(){}
			
			// DB 테이블 정보 설정
			public static final String TABLE_NAME = "profiles";
			public static final String COLUMN_NAME_NAME = "name";
			public static final String COLUMN_NAME_PHONE = "phone";
		}	
	}
	
	/*
	 * DB SQL 명령
	 */
	// profiles 테이블 생성 구문 정의
	private static final String SQL_CREATE_PROFILE_TABLE = "CREATE TABLE " +
		ProfileContract.ProfileEntry.TABLE_NAME + " (" +
		ProfileContract.ProfileEntry._ID + " integer primary key, " +
		ProfileContract.ProfileEntry.COLUMN_NAME_NAME + " text, " +
		ProfileContract.ProfileEntry.COLUMN_NAME_PHONE + " text)";
	
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "profile.db";
	
	public ProfileDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_PROFILE_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public ContentValues getProfile(long rowId) {
		// 정보 없는 경우
		if (rowId == -1) {
			return null;
		}
		
		SQLiteDatabase db = getReadableDatabase();
		if (db == null) {
			throw new SQLitecantOpenDatabaseException();
		}
		
		String table = ProfileContract.ProfileEntry.TABLE_NAME;
		String[] columns = { ProfileContract.ProfileEntry._ID,
							 ProfileContract.ProfileEntry.COLUMN_NAME_NAME,
						     ProfileContract.ProfileEntry.COLUMN_NAME_PHONE };
		// SQL 질의 조건 설정
		String selection = ProfileContract.ProfileEntry._ID + "= ?";
		String[] selectionArgs = {String.valueOf(rowId)};
		// 질의 실행
		Cursor data = db.query(table, columns, selection, selectionArgs, null, null, null);
		
		// 커서에서 정보 추출
		String name = getStringFromCursor(cursor, ProfileContract.ProfileEntry.COLUMN_NAME_NAME);
		String phone = getStringFromCursor(cursor, ProfileContract.ProfileEntry.COLUMN_NAME_PHONE);
		
		// ContentValues에 입력
		ContentValues values = new ContentValues();
		values.put(ProfileContract.ProfileEntry.COLUMN_NAME_NAME, name);
		values.put(ProfileContract.ProfileEntry.COLUMN_NAME_PHONE, phone);
		
		return values;
	}
	
	public long setProfile(String name, String phone) {
		SqLiteDatabase db = getWritableDatabase();
		if (db == null) {
			throw new SQLitecantOpenDatabaseException();
		}
		
		// ContentValues에 입력
		ContentValues values = new ContentValues();
		values.put(ProfileContract.ProfileEntry.COLUMN_NAME_NAME, name);
		values.put(ProfileContract.ProfileEntry.COLUMN_NAME_PHONE, phone);
		
		// DB 테이블에 정보 삽입
		long rowId = db.insert(ProfileContract.ProfileEntry.TABLE_NAME, null, values);
		
		return rowId;
	}
	
	private String getStringFromCursor(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
	}
}
