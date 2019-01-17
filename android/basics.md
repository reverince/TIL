# Android 기초

참조: [Tutorialspoint](https://www.tutorialspoint.com/android/index.htm)


## 컴포넌트

### 기본 컴포넌트

- 액티비티(Activity) : UI를 구술하고 스마트폰 화면상에서 유저 상호작용을 담당한다.
  ```java
  public class MainActivity extends Activity {
  }
  ```
- 서비스(Service) : 백그라운드 작업을 담당한다.
  ```java
  public class MyService extends Service {
  }
  ```
- 브로드캐스트 리시버(Broadcast Receiver) : 안드로이드 OS와 어플리케이션 간의 커뮤니케이션을 담당한다.
  ```java
  public class MyReceiver  extends  BroadcastReceiver {
    public void onReceive(context,intent){}
  }
  ```
- 컨텐트 프로바이더(Content Provider) : 데이터와 데이터 관리 이슈를 담당한다.
  ```java
  public class MyContentProvider extends  ContentProvider {
    public void onCreate(){}
  }
  ```

### 추가 컴포넌트

- 프래그먼트(Fragment) : 액티비티 UI의 일부.
- 뷰(View) : 버튼, 목록 등 화면상에 그려지는 UI 요소.
- 레이아웃(Layout) : 화면 구성과 뷰 형태를 제어하는 뷰 계층.
- 인텐트(Intent) : 컴포넌트를 하나로 묶는 메시지
- 리소스(Resource) : 문자열, 상수, 이미지 등 외부 요소.
- 매니페스트(Manifest) : 어플리케이션 설정(Configuration) 파일.


## 안드로이드 어플리케이션 구조

- `java/` : 프로젝트 `.java` 소스 파일을 관리한다. (`MainActivity.java` 등)
- `res/layout/` : UI를 정의하는 파일을 관리한다. (`activity_main.xml` 등)
- `res/values/` : 문자열이나 색상 정의 등 기타 리소스 XML 파일을 관리한다.
- `manifests/AndroidManifest.xml` : 앱의 기본 속성을 기술하고 각 컴포넌트를 정의한다.
- `build.gradle` : 어플리케이션 ID, 최소 SDK 버전, 타깃 SDK 버전, 버전 코드, 버전명 등을 포함한다.


## 리소스 조직 & 접근

각 리소스는 `res/`의 하위 폴더에서 관리되며, `R.layout`처럼 하위 폴더명과 같은 이름의 클래스로 접근할 수 있다.

- `anim/` : [속성 애니메이션](https://developer.android.com/guide/topics/graphics/prop-animation)을 정의하는 XML 파일을 관리한다.
- `color/` : 색상 목록을 정의하는 XML 파일을 관리한다.
- `drawable/` : PNG, JPG, GIF 등 이미지 파일이나 컴파일된 XML 파일을 관리한다.
- `layout/` : UI 레이아웃을 정의하는 XML 파일을 관리한다.
- `menu/` : 옵션 메뉴 같은 어플리케이션 메뉴를 정의하는 XML 파일을 관리한다.
- `raw/` : raw 상태로 저장해야 하는 임의의 파일을 관리한다. `Resources.openRawResource()`에 리소스 ID(`R.raw.<filename>`)를 지정해 호출해야 한다.
- `values/` : 문자열, 상수 등 간단한 값을 저장하는 XML 파일을 관리한다.
  - 관례적으로 `arrays.xml`, `integers.xml`, `bools.xml`, `colors.xml`, `dimens.xml`, `strings.xml`, `styles.xml` 파일을 사용하며 `R.string` 등 단수형 이름의 클래스로 접근한다.
- `xml/` : 런타임에 `Resources.getXML()`로 접근할 수 있는 임의의 XML 파일을 관리한다.
