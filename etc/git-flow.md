# git-flow

- [A successful Git branching model](https://nvie.com/posts/a-successful-git-branching-model/)

![git-flow 그래프](https://nvie.com/img/git-model@2x.png)

## 브랜치 종류

- 메인 브랜치 (항상 존재)
  - _master_ : 출시 버전
  - _develop_ : 새 버전 개발
- 보조 브랜치 (일시적으로 존재)
  - _feature_ : 새로운 기능 개발
  - _release_ : 새 버전 출시 준비
  - _hotfix_ : 출시 버전에서 발생한 버그 수정

## 흐름

- _master_에는 버전명 태그 부여
- 업데이트는 _develop_에서 진행
- _master_가 가지고 있는 버그는 _hotfix_에서 수정해 _master_와 _develop_ 양쪽에 반영
- 다음 버전에 추가할 _feature_의 기능은 _develop_에 병합
- 다음 버전 기능이 확정되면 _develop_에서 _release_ 브랜칭
  - 이 시점에서 병합되지 않은 _feature_는 다다음 버전 이후 반영
- _release_에서는 _feature_ 추가 없이 버그 수정만 진행
  - _release_에서의 버그 수정은 지속적으로 _develop_에 반영 
  - _release_ 준비가 완료되면 _master_에 병합

## 서브 브랜치

- _feature_
  - _develop_에서 브랜칭되어 _develop_로 병합
  - 네이밍 : _master_, _develop_, _release-*_, _hotfix-*_ 제외하고 알맞게
  - 브랜치 생성
    ```git
    $ git checkout -b myfeature develop
    ```
  - 완성한 _feature_ 병합
    ```git
    $ git checkout develop
    $ git merge --no-ff myfeature
    $ git branch -d myfeature
    $ git push origin develop
    ```
- _release_
  - _develop_에서 브랜칭되어 _develop_ 및 _master_로 병합
  - 네이밍 : _release-*_
  - 브랜치 생성
    ```git
    $ git checkout -b release-1.2 develop
    $ ./bump-version.sh 1.2
    $ git commit -a -m "Bump version number to 1.2"
    ```
  - 완성한 _release_ 병합
    ```git
    $ git checkout master
    $ git merge --no-ff release-1.2
    $ git tag -a 1.2

    $ git checkout develop
    $ git merge --no-ff release-1.2

    $ git branch -d release-1.2
    ```
- _hotfix_
  - _master_에서 브랜칭되어 _develop_ 및 _master_로 병합
  - 네이밍 : _hotfix-*_
  - 브랜치 생성
    ```git
    $ git checkout -b hotfix-1.2.1 master
    $ ./bump-version.sh 1.2.1
    $ git commit -a -m "Bump version number to 1.2.1"
    ```
  - 버그 수정 완료 후 병합
    ```git
    $ git checkout master
    $ git merge --no-ff hotfix-1.2.1
    $ git tag -a 1.2.1

    $ git checkout develop
    $ git merge --no-ff hotfix-1.2.1

    $ git branch -d hotfix-1.2.1
    ```
