# Android

참고 강의 : [국방부오픈소스아카데미](http://osam.oss.kr) APP 개발 과정

- Android Studio 시작 메뉴의 Quick Start 탭에서 `Import an Android code sample`로 다양한 예시 프로젝트를 확인할 수 있다.


## 기타

- strings.xml : 문자열을 정의해두는 곳.


## 레이아웃

- `android:textSize`
  - `sp`(Scaled Pixels) : (텍스트 전용) 유저 설정에 비례하는 사이즈
- `android:scaleType`
  - `"center"` : 크기 조절 없이 가운데에 놓는다.
  - `"centerCrop"` : 가장자리를 맞춰서 가운데에 놓는다.
- `android:layout_width` / `android:layout_height`
  - `dp`(Density-Independent Pixels) : 화면 크기에 비례하는 사이즈
  - `wrap_content` : 내용 크기에 맞춘다.
  - `match_parent` : 부모 크기에 맞춘다.

#### LinearLayout

- 레이아웃 가중치
  - 가중치 비율에 따라 화면을 나눠 갖는다.
  - 기본 가중치는 0으로, 필요한 만큼만 차지한다.
  - 세로 레이아웃은 `layout_height="0dp"`, 가로 레이아웃은 `layout_width="0dp"`를 적용해야 한다.
  - `layout_weight="1"`과 같이 가중치를 부여한다.

#### RelativeLayout

- [안드로이드 레퍼런스](https://developer.android.com/reference/android/widget/RelativeLayout.LayoutParams)

- 레이아웃을 기준으로 정렬 (`true` / `false`)
  - `android:layout_alignParentTop`
  - `android:layout_alignParentBottom`
  - `android:layout_alignParentLeft`
  - `android:layout_alignParentRight`
  - `android:layout_centerHorizontal`
  - `android:layout_centerVertical`
- 다른 뷰를 기준으로 정렬 (`@id/view_id`)
  - `android:layout_toLeftOf`
  - `android:layout_toRightOf`
  - `android:layout_above`
  - `android:layout_below`

### 여백 (Padding & Margin)

- Padding (`dp`) : 뷰 안쪽(백그라운드) 여백
  - `android:padding`
  - `android:paddingTop` 등

- Margin (`dp`) : 뷰 바깥쪽 여백
  - `android:layout_margin`
  - `android:layout_marginTop` 등


## 데이터 저장

- SharedPreferences(셰어드 프리퍼런스) : DB보다 간단하고 편리하다. Editor로 데이터를 수정한다.
- 내부 저장소 : 기본 탑재 저장소. 분리 불가능.
- 외부 저장소 : SD카드 등 확장된 저장소. 내부 탑재지만 외부 저장소인 경우도 있다.
- Sqlite DB


## 백그라운드 작업

오래 걸리는 작업(0.3초 이상)을 UI 스레드에서 수행하면 화면이 정지하거나 지연되어 표시되므로 백그라운드 작업으로 수행한다.

- `AsyncTask` : 짧은 시간 내 완료되는 작업
- `Service` : 비교적 긴 시간이 필요한 작업
- `Loader` : DB 읽기


## 인터넷 입출력

### 앱 권한 설정

###### AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest ...>
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  <uses-permission android:name="android.permission.INTERNET" />
  ...
</manifest>
```

### 네트워크 연결 상태 확인

###### MainActivity.java

```java
...
boolean isNetworkConnected() {
  ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
  
  NetworkInfo networkInfo = manager.getActiveNetworkInfo();
  if (networkInfo != null && networkInfo.isConnected()) {
    return true;
  }
  
  return false;
}
```

### 이미지 다운로드

###### MainActivity.java

```java
...
private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
  
  @Override
  protected Bitmap doInBackground(String... params) {
    try {
    
      URL url = new URL(params[0]);
      // HTTP 클라이언트 정의
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      // 통신 개시
      connection.connect();
      int responseCode = connection.getResponseCode();
      if (responseCode != 200) {
        Log.d(DownloadImageTask.class.getSimpleName(), "이미지 다운로드 실패");
        return null;
      }
      
      InputStream is = connection.getInputStream();
      // 스트림을 비트맵으로 변환
      Bitmap imageBitmap = BitmapFactory.decodeStream(is);
      is.close();
      
      return imageBitmap;
      
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  @Override
  protected void onPostExecute(Bitmap bitmap) {
    // 다운로드가 완료되면 이미지 표시
    downloadedImageView.setImageBitmap(bitmap);
  }
}
```


## GPS

### Google Play 서비스 설정

1. SDK 관리자에서 `Google Play services` 설치

1. `build.gradle`(Module: app) 수정
  ```xml
  ...
  dependencies {
    ...
    compile 'com.google.android.gms:play-services:7.5.0'
  }
  ```

- `Sync Project with Gradle Files`

### 앱 권한 설정

###### AndroidManifets.xml

```xml
...
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

### 구현

###### MainActivity.java

```java
...
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
  private static final String TAG = MainActivity.class.getCanonicalName();
  
  protected GoogleApiClient mGoogleApiClient;
  protected Location mLastLocation;
  ...
  protected synchronized void buildGoogleApiClient() {
    mGoogleApiClient = new GoogleApiClient.Builder(this)
    .addConnectionCallbacks(this)
    .addOnConnectionFailedListener(this)
    .addApi(LocationServices.API)
    .build();
  }
  
  @Override
  public void onConnected(Bundle bundle) {
    // 마지막 위치 가져오기
    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    if (mLastLocation != null) {
      // 위도와 경도 가져오기
      double la = mLastLocation.getLatitude();
      double lo = mLastLocation.getLongitude();
    } else {
      String locationErrorMsg = "위치 정보를 찾을 수 없습니다."
      Toast.makeText(this, locationErrorMsg, Toast.LENGTH_LONG).show();
    }
  }
  
  @Override
  public void onConnectionSuspended(int i) {
    Log.i(TAG, "Connection suspended");
    mGoogleApiClient.connect();
  }
  
  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
  Log.i(TAG, "Connection failed with error code " + connectionResult.getErrorCode());
  }
}
```
