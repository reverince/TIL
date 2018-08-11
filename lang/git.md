# Git

[Learn Git Branching](https://learngitbranching.js.org/) by Peter Cottle

* 여기서 커밋은 아래(앞)에서 위(뒤, top)로 쌓이는 방식으로 묘사한다.
* `C1`, `C2` 등은 커밋의 해시값으로 한다.

### 기본

* 델타(delta) : 두 버전 간의 차이

* 커밋(commit) : 델타를 저장하는 것, 혹은 저장된 델타
  * `git commit --amend` : 직전(top) 커밋 수정

* 브랜치(branch) : 하나의 커밋과 그 부모 커밋들을 포함하는 작업 내역
  * `git branch bugFix` : 새 브랜치 `bugFix` 만들기
  * `git checkout bugFix` : 작업할 브랜치 선택
  * `git branch -f master` : `master` 브랜치가 가리키는 커밋을 강제로 바꾼다.

* 태그(tag) : 특정 커밋들을 영구적인 이정표로 표시
  * `git tag v1 C1` : `C1` 커밋을 `v1`이라는 이름으로 태그
  * 브랜치와 달리 커밋이 생성되어도 절대 움직이지 않는다.
  * 체크아웃은 가능하지만 커밋하면 `HEAD`가 분리된다.
  * `git describe bugFix` : 가까운 태그를 이용해 `bugFix`의 위치 표현
    * 출력값 `v1_2_gC3` : `v1` 태그로부터 `2`칸 떨어진 해시값 `C3`의 커밋이라는 뜻

* 머지(merge) : 두 브랜치 통합
  * 부모 쪽에 있는 브랜치에서 자식 쪽으로 합쳐야 한다.
  * `git merge master` : 현재 브랜치와 `master` 브랜치 통합

* 리베이스(rebase) : 두 브랜치가 나뉘기 전 공통 커밋에서 현재 브랜치까지의 델타를 차례로 만들어 저장
  * `bugFix`에서 `git rebase master` : `master` 브랜치 뒤에 달린다.

* HEAD : 현재 체크아웃된(작업 중인) 커밋.
  * `HEAD` -> `master` -> `C1`일 때 `git checkout C1`하면 `HEAD` -> `C1`
  * 해시값은 다른 해시값과 구분 가능해지는 글자수까지만 입력해도 된다.

* 상대 참조
  * `^` : 한 커밋 앞으로 이동
    * `master^^`은 마스터의 부모의 부모 커밋
  * `~n` : `n` 커밋 앞으로 이동
    * `~3` == `^^^`
  * `git branch master HEAD~3` : `master`가 가리키는 커밋을 `HEAD`의 3커밋 앞으로 바꾼다.
  * `HEAD`는 브랜치가 아니다. 따라서 `git branch -f HEAD HEAD^` 대신 `git checkout HEAD^`라고 입력해야 한다.

* 리셋(reset) : 커밋하지 않았던 것처럼 되돌린다.
  * `git reset HEAD^` : 현재 브랜치를 1커밋 앞으로 리셋
  * 각자 작업하는 로컬 브랜치에서는 유용하지만 다른 사람이 작업하는 리모트 브랜치에서는 사용할 수 없다.

* 리버트(revert) : 반대 내용으로 커밋한다.
  * `git revert HEAD` : `HEAD`가 가리키는 커밋의 델타와 완전히 반대되는 내용을 커밋
  * 리모트 브랜치에서도 사용할 수 있다.

### 커밋 옮기기

* 체리픽(cherry-pick) : 지정한 커밋들만 지금 브랜치 아래로 커밋
  * `git cherry-pick C1 C3` : (현재 가리키고 있는 커밋이 아닌) `C1`, `C3` 커밋을 현재 브랜치 위에 커밋

* 인터렉티브 리베이스(rebase -i) : `vim`과 `pick` 등을 이용해 지정한 커밋들만 지금 브랜치 아래로 커밋
  * `git rebase -i HEAD~3` : `HEAD`가 가리키는 커밋부터 아래로 총 3개의 커밋을 이용해 인터렉티브 리베이스. 그 아래의 커밋에서부터 브랜치 라인이 나뉜다.
  * 해시값을 몰라도 사용할 수 있다.

#### 이전 커밋의 수정이 필요한 경우

* 인터렉티브 리베이스
  1. `git rebase -i HEAD~2`로 수정할 커밋을 맨 위로 옮기기
  1. `git commit --amend`로 커밋 수정
  1. `git rebase -i HEAD~2`로 다시 커밋 원위치

  위치를 바꾸어대다 보니 리베이스 과정에서 충돌이 일어날 수 있다.

* 체리픽
  1. `git cherry-pick C1`로 수정할 커밋 체리픽
  1. `git commmit --amend`로 커밋 수정
  1. `git cherry-pick C2 C3`로 이후 커밋 체리픽
