# Unity 2D

### 플랫폼

* `File` - `Build Settings`

* Platform 선택 후 `Switch Platform`

* `Player Settings...`
  * WebGL은 해상도 지정 가능
  
* Game 뷰 좌측 상단에서 테스트 해상도 지정

### Box Collider 2D

* `Is Trigger`를 체크하면 `Rigidbody`가 충돌하지 않는다.

### Sprite Renderer

* Sorting Layer
  * 목록 위에서부터 스프라이트를 그린다.
  * 즉 목록 위에 있을수록 화면 뒤에 나타난다.

* Order in Layer
  * 값이 작은 것부터 그린다.
  * 즉 (같은 Sorting Layer에서) 숫자가 작을수록 화면 뒤에 나타난다.

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
