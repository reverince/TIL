# 객체 지향 프로그래밍(OOP)

좋은 설계는 **시스템에 새로운 요구사항이나 변경사항이 있을 때, 영향을 받는 범위가 적은 구조**이다.

## 용어

- 강건성([robustness](https://en.wikipedia.org/wiki/Robustness_(computer_science))): 예기치 못한 오류나 상황에 대응할 수 있는 정도
- 불변 객체(immutable object): 상태를 수정할 수 있는 메소드가 없는 객체

## 객체 간의 관계

- 사용(use-a) 관계: 멤버 변수가 아닌 다른 클래스의 객체를 메소드 인자로 받거나 메소드 내에서 생성하는 경우
- 포함(has-a) 관계: 다른 클래스의 객체를 멤버로 유지하는 경우
  - 부분전체(part-whole) 관계
    - 집합(aggregation): 전체가 제거되어도 부분은 남아 있는 경우
	- 복합(composition): 전체가 제거되면 부분도 함께 제거되어야 하는 경우
  - 연관(associative) 관계

※ 연관 클래스: 두 클래스 간의 관계가 복잡한 경우 활용. (ex. 각 학생들이 수강하는 강의와 각 강의를 수강하는 학생들을 쉽게 관리하기 위한 'enroll' 클래스)

### 상속

#### 상속의 문제점

- 클래스 간의 정적 관계이기 때문에 코드를 수정하지 않는 한 계속 유지됨
- 자식 클래스는 필요와 무관하게 부모의 모든 메소드 상속 (`private` 제외)
- 상속받는 모든 메소드에 대해 다음 중 하나의 조치를 취해야 함 (경우 ii, iii은 [리스코프 치환 원칙](https://ko.wikipedia.org/wiki/%EB%A6%AC%EC%8A%A4%EC%BD%94%ED%94%84_%EC%B9%98%ED%99%98_%EC%9B%90%EC%B9%99)에 위배될 수 있음)
  1. 그대로 사용 (`super()`)
  1. 재정의 (`override`)
  1. 사용할 수 없도록 조치 (ex. 빈 메소드로 재정의)
- 죽음의 다이아몬드(DDD; Deadly Diamond of Death): 둘 이상의 부모 클래스에 같은 메소드가 정의되어 있을 경우 어느 쪽 부모의 메소드를 상속해야 하는지 알 수 없음(undefined)

### 구체화

논리적으로 관련 있는 객체를 묶는 상속과 달리, 논리적으로는 관련이 없지만 같은 일을 하는 객체를 묶음 (ex. 자바의 `interface`)

```java
public static void sort(Comparable<T>[] list);
```

```java
public interface Comparable<T> {
  int compareTo(T other);
}
```

강건성을 지닌 구체화 예시. `Comparable` 인터페이스를 구체화해 `compareTo` 메소드를 구현한 객체들만 정렬할 수 있음

## 설계 원칙 SOLID

### 단일 책임 원칙 (Single Responsibility Principle)

한 클래스는 하나의 책임만 가지고, 수정할 이유는 하나뿐이어야 한다. 즉, 각 클래스는 각자 맡은 한 분야의 역할만을 수행해야 한다.

응집도는 높게, 결합도는 낮게.

- 응집도: 연관된 기능이 모여 있는 정도
- 결합도: 구성 요소가 서로 의존하는 정도

### 개방-폐쇄 원칙 (Open/Closed Principle)

소프트웨어 요소는 확장에 대해서는 개방적이고 수정에 대해서는 폐쇄적이어야 한다.

- 상속보다 포함 관계를 활용하는 것이 OCP 원칙을 지키기 쉽다.

### 리스코프 치환 원칙 (Liskov Substitution Principle)

프로그램의 객체는 프로그램의 정확성을 깨트리지 않으면서 하위 자료형의 객체로 치환할 수 있어야 한다. 즉, 자식 클래스는 언제나 부모 클래스를 대체할 수 있어야 한다.

- 계약에 의한 설계(DBC; Design by Contact)
- 팩토리 패턴

### 인터페이스 분리 원칙 (Interface Segregation Principle)

특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다. 즉, 구체화한 객체에서 사용하지 않는 메소드를 가진 인터페이스는 여러 개의 인터페이스로 분리해야 한다.

### 의존관계 역전 원칙 (Dependency Inversion Principle)

프로그래머는 구체화가 아닌 추상화에 의존해야 한다. 즉, 추상적이고 안정적인 고수준의 클래스는 구체적이고 불안정한 저수준의 클래스에 의존하면 안 된다.
- [의존성 주입](https://ko.wikipedia.org/wiki/%EC%9D%98%EC%A1%B4%EC%84%B1_%EC%A3%BC%EC%9E%85)(DI; Dependency Injection)
- [전략 패턴](https://ko.wikipedia.org/wiki/%EC%A0%84%EB%9E%B5_%ED%8C%A8%ED%84%B4)
- [제어 역전](https://ko.wikipedia.org/wiki/%EC%A0%9C%EC%96%B4_%EB%B0%98%EC%A0%84)(IoC; Inversion of Control)
