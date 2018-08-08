# Android

참고 강의 : [국방부오픈소스아카데미](http://osam.oss.kr) APP 개발 과정

## 데이터 저장

* SharedPreferences(셰어드 프리퍼런스) : DB보다 간단하고 편리하다. Editor로 데이터를 수정한다.
* 내부 저장소 : 기본 탑재 저장소. 분리 불가능.
* 외부 저장소 : SD카드 등 확장된 저장소. 내부 탑재지만 외부 저장소인 경우도 있다.
* Sqlite DB

## 기타

* strings.xml : 문자열을 정의해두는 곳.

## 레이아웃

* `android:textSize`
  * `sp`(Scaled Pixels) : (텍스트 전용) 유저 설정에 비례하는 사이즈
* `android:scaleType`
  * `"center"` : 크기 조절 없이 가운데에 놓는다.
  * `"centerCrop"` : 가장자리를 맞춰서 가운데에 놓는다.
* `android:layout_width` / `android:layout_height`
  * `dp`(Density-Independent Pixels) : 화면 크기에 비례하는 사이즈
  * `wrap_content` : 내용 크기에 맞춘다.
  * `match_parent` : 부모 크기에 맞춘다.

#### LinearLayout

* 레이아웃 가중치
  * 가중치 비율에 따라 화면을 나눠 갖는다.
  * 기본 가중치는 0으로, 필요한 만큼만 차지한다.
  * 세로 레이아웃은 `layout_height="0dp"`, 가로 레이아웃은 `layout_width="0dp"`를 적용해야 한다.
  * `layout_weight="1"`과 같이 가중치를 부여한다.

#### RelativeLayout

* [안드로이드 레퍼런스](https://developer.android.com/reference/android/widget/RelativeLayout.LayoutParams)

* 레이아웃을 기준으로 정렬 (`true` / `false`)
  * `android:layout_alignParentTop`
  * `android:layout_alignParentBottom`
  * `android:layout_alignParentLeft`
  * `android:layout_alignParentRight`
  * `android:layout_centerHorizontal`
  * `android:layout_centerVertical`
* 다른 뷰를 기준으로 정렬 (`@id/view_id`)
  * `android:layout_toLeftOf`
  * `android:layout_toRightOf`
  * `android:layout_above`
  * `android:layout_below`

### 여백 (Padding & Margin)

* Padding (`dp`) : 뷰 안쪽(백그라운드) 여백
  * `android:padding`
  * `android:paddingTop` 등

* Margin (`dp`) : 뷰 바깥쪽 여백
  * `android:layout_margin`
  * `android:layout_marginTop` 등
