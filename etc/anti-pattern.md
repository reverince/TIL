# 안티패턴(Anti-pattern)

실제 많이 사용되는 패턴이지만 비효율적이거나 비생산적인 패턴 _by [위키백과](https://ko.wikipedia.org/wiki/%EC%95%88%ED%8B%B0%ED%8C%A8%ED%84%B4)_
* [영문 위키백과 문서](https://en.wikipedia.org/wiki/Anti-pattern)

## 프로그래밍

* 화물 숭배 프로그래밍
  * 동작 원리를 모르면서 다른 사람이 작성한 코드를 복사해 사용
  * 자체 문서화 코드(self explanatory code)에 주석 달기
  * 컨벤션에 지나치게 집착
  * 가비지 컬렉션으로 알아서 처리되는 객체를 삭제하는 코드 작성
* 보트 닻(Boat Anchor)
  * 필요 없는 시스템 파트 유지
* 예외 코딩(Coding by exception)
  * 예외 처리를 이용한 코딩
  * 예외(Exception)는 **예외**다!
* 하드코딩(Hard code)
  * 데이터를 코드 내부에 직접 입력
  * 가독성은 좋겠으나 유지보수는…
* 라자냐 코드(Lasagna code)
  * 상속 계층이 너무 많은 코드
  * 스파게티 코드의 일종
* 루프-스위치 시퀀스(Loop-switch sequence)
  * 루프 안에 스위치(`switch`, `case`) 사용
* 매직 넘버(Magic numbers)
  * 알고리즘에서 특정 수를 설명 없이 사용
  * 라틴 알파벳 처리에서 `26`, `122` 등
* 매직 스트링(Magic strings)
  * 디버깅 과정에서 정상적인 상황에 입력으로 주어지지 않을 것으로 예상되는 값을 프로그래머가 임의로 지정해 사용
  * 수정되지 않은 채 배포되면 예기치 못한 문제를 일으킬 수 있다.
* 반복 코딩(Repeat yourself)
  * 같은 코드를 반복해서 입력
  * DRY(Don't Repeat Yourself; [중복배제](https://ko.wikipedia.org/wiki/%EC%A4%91%EB%B3%B5%EB%B0%B0%EC%A0%9C))의 반대
* 스파게티 코드(Spaghetti code)
  * 포인터 남용, 다중 `for`문, 콜백 등으로 스파게티처럼 뒤엉킨 코드