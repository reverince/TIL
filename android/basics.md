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

### 대체 리소스

특정 환경을 지원하기 위해 별도의 리소스를 사용할 수 있다. 화면 크기가 다른 장치를 사용하거나 다른 언어를 사용하는 경우가 해당한다.
런타임에 안드로이드가 장치 설정을 탐지해 앱에 알맞은 리소스를 불러온다.

`res/`에 `<resources_name>-<config_qualifier>` 형태의 폴더를 만들어 배치한다.
`resources_name`은 `drawable` 등의 리소스 종류이고 `config_qualifier`는 해당 리소스가 쓰일 특정 환경을 가리킨다.

대체 리소스의 파일명은 원본 리소스와 같아야 한다.

### 리소스 접근

앱이 컴파일될 때 `R` 클래스가 생성되어 `res/` 폴더의 모든 리소스 ID를 저장한다.

- 코드에서 : `R.<폴더명>.<파일명>`
- XML에서 : `@<폴더명>/<파일명>`

## 액티비티 콜백

- `onCreate()` : 액티비티가 생성되고 바로 호출된다.
- `onStart()` : 액티비티가 처음 유저에게 보여질 때 호출된다.
- `onResume()` : 액티비티가 유저와 상호작용을 시작할 때, 혹은 위에 열린 다른 액티비티에서 복귀할 때 호출된다.
- `onPause()` : `startActivity()` 등으로 위에 다른 액티비티가 열리기 직전에 호출된다.
- `onStop()` : 액티비티가 닫힐 때 호출된다.
- `onDestroy()` : 액티비티가 시스템에 의해 파괴되기 직전에 호출된다.
- `onRestart()` : 액티비티가 닫힌 후 다시 열릴 때 호출된다.

## 인텐트

인텐트는 수행해야 하는 연산에 대한 추상적 내용을 가지고 있는 수동적 자료구조이다.
예컨대 메일을 보내려 할 때 인텐트에 메일 주소, 제목, 내용 등을 담아 다른 액티비티에 전달할 수 있다.

기본 컴포넌트에 인텐트를 전달하는 메소드는 다음과 같다.

- `Context.startActivity()` : 새 액티비티 혹은 기존 액티비티에 인텐트를 전달한다.
- `Context.startService()` : 새 이벤트 혹은 기존 이벤트에 인텐트를 전달한다.
- `Context.sendBroadcast()` : 모든 관련 브로드캐스트 리시버에 인텐트를 전달한다.

### 인텐트 객체

인텐트 객체는 인텐트를 받는 컴포넌트나 안드로이드 시스템에서 사용할 정보들을 가지고 있다.

인텐트 객체는 맡은 역할 혹은 전달하는 대상에 따라 다음과 같은 요소를 갖는다.

- 액션 : 인텐트 객체는 실행하고자 하는 액션을 지정해야 한다. 인텐트 객체의 나머지 구조를 정의한다. ([표준 액션 목록](https://www.tutorialspoint.com/android/android_intent_standard_actions.htm))
- 데이터 : 인텐트 필터에 URI 또는 자료형을 지정한다. (`setData()`, `setType()`, `setDataAndType()`)
- 카테고리 : 액션에 더해 추가적인 정보를 제공한다. ([표준 카테고리 목록](https://www.tutorialspoint.com/android/android_intent_standard_categories.htm))
- 엑스트라 : 인텐트를 다루는 컴포넌트에 전달해야 하는 딕셔너리 구조의 추가 정보. ([표준 엑스트라 목록](https://www.tutorialspoint.com/android/android_intent_standard_extra_data.htm))
- 플래그 : 안드로이드 시스템에 액티비티를 어떻게 열거나 다룰지 안내한다.
- 컴포넌트 이름 : 지정한 컴포넌트로 인텐트를 전달한다. 컴포넌트를 찾지 못하면 안드로이드가 다른 정보를 이용해 적절한 대상을 찾는다.

### 인텐트 유형

- 명시적(Explicit) 인텐트 : 대상 컴포넌트를 지정해, 앱 내의 서로 다른 컴포넌트 사이에 전달되는 인텐트. 버튼을 누르면 다른 액티비티가 열리는 경우 등.
- 묵시적(Implicit) 인텐트 : 대상 컴포넌트를 지정하지 않는 인텐트. 주로 다른 앱의 컴포넌트를 부르고자 할 때 사용한다. 파일 탐색기에서 문서를 여는 경우 등.
