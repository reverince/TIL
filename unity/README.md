# Unity

* [유니티 사용자 매뉴얼](https://docs.unity3d.com/Manual/UnityManual.html)


## 스크립트

### 속성

* `[AddComponentMenu("MyScripts/SomeScript")]` : `Add` - `Component` 메뉴에 스크립트 추가

* `[ExecuteInEditMode]` : 편집 모드에서 스크립트 실행

* `[Header("Stats")]` : 인스펙터 변수 소제목 설정

* `[HideInInspector]` : 인스펙터에서 `public` 변수 숨김

* `[Range(-100, 100)]` : 속성의 범위 설정

* `[RequireComponent(typeof(Rigidbody))]` : 해당 컴포넌트 추가

* `[SerializeField]` : 인스펙터에 `private` 변수 표시

* `[Space(10)]` : 인스펙터에서 변수 칸 간격 조절

* `[System.Serializable]` : 인스펙터에 클래스 또는 구조체 표시

### MonoBehaviour

* `Awake()`는 컴포넌트가 비활성화 상태라도 호출된다.

* `Start()`는 `Awake()`와 `Update()` 사이에 호출된다.

* `Update()`는 매 프레임마다 호출된다.
  * 프레임 처리 시간이 다른 프레임보다 길 수 있으므로, 호출 간격이 일정하지 않다.
  * 그래서 `Update()`안의 `Transform.Translate()` 등에 `Time.deltaTime`을 사용하는 것.
  * 비물리 오브젝트 처리에 사용한다.
  
* `FixedUpdate()`는 같은 시간 간격으로 호출된다.
  * 물리 오브젝트 처리에 사용한다.

### Input Manager
`Edit` - `Project Settings` - `Input`

* `GetKey`는 특정 키에만 반응하므로 `GetButton`을 사용하는 것이 좋다.

* `GetAxis` : -1과 1 사이의 `float` 값을 반환한다.
  * `Gravity` : 버튼을 뗐을 때 절대값이 감소하는 속도
  * `Sensitivity` : 버튼을 누를 때 절대값이 증가하는 속도
  * `Snap` : `Positive` 버튼과 `Negative` 버튼이 동시에 눌린 경우 0 반환
  * `Dead` : 미세한 조이스틱 입력 무시

### Transform

* `LookAt(target)` : `target`을 향해 `transform`을 회전한다.


## 컴포넌트

### Box Collider

* `Is Trigger`를 체크하면 `Rigidbody`가 충돌하지 않는다.

### Sprite Renderer

* Sorting Layer
  * 목록 위에서부터 스프라이트를 그린다.
  * 즉 목록 위에 있을수록 화면 뒤에 나타난다.

* Order in Layer
  * 값이 작은 것부터 그린다.
  * 즉 (같은 Sorting Layer에서) 숫자가 작을수록 화면 뒤에 나타난다.


## 플랫폼

* `File` - `Build Settings`

* Platform 선택 후 `Switch Platform`

* `Player Settings...`
  * WebGL은 해상도 지정 가능
  
* Game 뷰 좌측 상단에서 테스트 해상도 지정


## 참고 사항

### 소리를 재생하면서 제거되는 오브젝트 (코인 등)

* 소리 재생 후 즉시 제거하면 소리가 끊긴다. 그렇다고 재생이 끝날 때까지 기다려줄 수도 없는 노릇.

* 렌더러와 콜라이더를 비활성화하고 소리만 남아 재생되게 하면 된다.

```cs
mRenderer.enabled = false;
mCollider2D.enabled = false;

mAudio.Play();
// 오디오 재생이 끝나야 오브젝트 제거
Destroy(gameObject, mAudio.clip.length);
```
