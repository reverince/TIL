# Android UI

참조: [Tutorialspoint](https://www.tutorialspoint.com/android/index.htm)


##  레이아웃

- 뷰(`View`; 컨트롤) : 화면에서 사각 영역을 차지하는 기본 블록. 이미지, 버튼, 텍스트 필드 등의 UI 구성요소(위젯)를 만든다.
- 뷰그룹(`ViewGroup`; 레이아웃) : 레이아웃 속성을 정의하며, 다른 뷰 또는 뷰그룹을 포함하는 콘테이너.

### 레이아웃 종류

- `LinearLayout` : 뷰를 세로 혹은 가로 일렬로 배치한다.
- `RelativeLayout` : 뷰를 서로 다른 뷰와의 상대적인 위치로 배치한다. 
- `TableLayout` : 표처럼 행과 열을 나눠 뷰를 배치한다.
- `AbsoluteLayout` : 뷰의 좌표를 지정해 배치한다.
- `FrameLayout` : 뷰 하나를 표시할 수 있는 스크린상의 자리 표시자.
- `ListView` : 스크롤 가능한 아이템들을 나열한다.
- `GridView` : 스크롤 가능한 아이템들을 그리드로 나열한다.
- `ConstraintLayout` : `RelativeLayout`과 유사. 화면 크기에 유동적으로 뷰를 배치한다.

## 컨트롤

버튼, 체크박스 등 상호작용 가능한 컴포넌트는 인풋 컨트롤이라고 한다.

- `TextView`
- `EditText`
- `Button`
- `CheckBox`
- `ToggleButton`
- `RadioButton`
- `RadioGroup`
- `ProgressBar`
- `Spinner` : 드롭다운 리스트
- `TimePicker`
- `DatePicker`

## 이벤트 핸들링

이벤트는 상호작용 가능한 컴포넌트들을 통해 데이터를 수집할 수 있는 유용한 방법이다.
안드로이드 프레임워크는 이벤트를 선입선출 큐로 관리한다.

이벤트 관리의 세 가지 콘셉트는 다음과 같다.

- 이벤트 리스너(Event Listener) : 콜백 메소드 하나를 포함하는 뷰 클래스의 인터페이스. 리스너가 등록된 뷰가 트리거되면 안드로이드 프레임워크에 의해 메소드가 호출된다.
- 이벤트 리스너 등록(Event Listener Registration) : 이벤트 리스너가 핸들러를 호출할 수 있도록 핸들러가 리스너를 등록하는 과정.
- 이벤트 핸들러(Event Handler) : 발생한 이벤트에 이벤트 리스너가 등록되어 있으면 리스너는 핸들러를 호출한다.
