# C# #


## Overloading / Overriding / Hiding

* [함수 오버로딩](https://ko.wikipedia.org/wiki/%ED%95%A8%EC%88%98_%EC%98%A4%EB%B2%84%EB%A1%9C%EB%93%9C)(Function Overloading) : 이름은 같으나 매개변수나 반환 형식이 다른 여러 개의 함수를 만드는 것.

* [연산자 오버로딩](https://ko.wikipedia.org/wiki/%EC%97%B0%EC%82%B0%EC%9E%90_%EC%98%A4%EB%B2%84%EB%A1%9C%EB%94%A9)(Operator Overloading) : 연산자가 피연산자에 대해 수행하는 일을 **재구성하는** 것.

* [메소드 오버라이딩](https://ko.wikipedia.org/wiki/%EB%A9%94%EC%86%8C%EB%93%9C_%EC%98%A4%EB%B2%84%EB%9D%BC%EC%9D%B4%EB%94%A9)(Method Overriding) : 부모 클래스의 메소드를 자식 클래스가 **확장하는** 것.

* [멤버 하이딩](https://docs.microsoft.com/ko-kr/dotnet/csharp/programming-guide/classes-and-structs/knowing-when-to-use-override-and-new-keywords)(Member Hiding) : 부모 클래스의 메소드를 자식 클래스가 **숨기는** (혹은 새로 구성하는) 것.


## `base` : [부모 클래스 멤버 접근](https://docs.microsoft.com/ko-kr/dotnet/csharp/language-reference/keywords/base)

```cs
class Apple : Fruit
{
  public Apple() : base("apple") { }
}
```

* 자식 클래스에서 부모 클래스 멤버에 접근한다.


## `delegate` : [대리자 사용](https://docs.microsoft.com/ko-kr/dotnet/csharp/programming-guide/delegates/using-delegates)(위임)

```cs
delegate void Motion(float time);

void Jump(float time) { ... }
void Walk(float time) { ... }

Motion m = Jump;
m += Walk;

m(1.0f)  // Jump(1.0f); Walk(1.0f);
```

* C++의 함수 포인터처럼 메소드를 캡슐화한다.

* .NET 라이브러리에 이미 정의된 위임형을 사용할 수 있다.
  * 반환이 없는 경우 : `Action<T>`
  * 반환이 있는 경우 : `Func<T, TResult>`


## `enum` : [열거형 형식](https://docs.microsoft.com/ko-kr/dotnet/csharp/programming-guide/enumeration-types)

```cs
enum Direction : short { North, East, South, West };

Direction myDirection = Direction.North;
int directionNumber = (int)myDirection;  // 0
```


## `foreach`

* `foreach`는 실행 중 요소가 추가 혹은 삭제되면 예측하지 못한 결과를 불러일으킬 수 있으므로 읽기 전용 `IEnumerable<T>`와 사용하는 것이 좋다.

  ```cs
  static readonly IEnumerable<string> faces = new[] { "normal", "smile", "angry" };
  ...
  foreach (var f in faces) { ... }
  ```


## `where` : [제네릭 형식 제약 조건](https://docs.microsoft.com/ko-kr/dotnet/csharp/language-reference/keywords/where-generic-type-constraint)

```cs
public T genericMethod<T>(T param) where T : new()
{
  return param;
}
```

* `where`으로 `T`의 제약 조건을 지정할 수 있다.


## `yield`

* `foreach`를 사용하는 경우

  ```cs
  using System;
  using System.Collections;

  class Program {
    public static void Main (string[] args) {
      foreach (int x in Yielder())
      {
        Console.WriteLine(x);
      }
    }

    static IEnumerable Yielder()
    {
      yield return 2;
      yield return 3;
      yield return 5;
    }
  }
  ```

* `IEnumerator`를 사용하는 경우

  ```cs
  using System;
  using System.Collections;

  class Program {
    public static void Main (string[] args) {
      IEnumerator iEnumerator = Yielder();

      while (iEnumerator.MoveNext())
      {
        Console.WriteLine(iEnumerator.Current);
      }
    }

    static IEnumerator Yielder()
    {
      yield return 2;
      yield return 3;
      yield return 5;
    }
  }
  ```

* 유니티에서의 용례

  ```cs
  void Start()
  {
    StartCoroutine("Coroutine");
  }

  IEnumerator Coroutine()
  {
    // 처음 호출됐을 때는 다음 프레임에 Coroutine() 진행
    yield return null;
    // 3초 후에 다시 Coroutine() 진행
    yield return new WaitForSeconds(3);
    // 6초 후에 다시 Coroutine() 진행
    yield return new WaitForSeconds(6);
    // Coroutine() 종료
  }
  ```


## [람다 식](https://docs.microsoft.com/ko-kr/dotnet/csharp/programming-guide/statements-expressions-operators/lambda-expressions)(Lambda Expressions)


## [속성](https://docs.microsoft.com/ko-kr/dotnet/csharp/programming-guide/classes-and-structs/properties)(Properties)

```cs
public class Player
{
  private int experience;
  
  public int Experience
  {
    get
    {
      return experience;
    }
    set
    {
      experience = value;
    }
  }
  
  public int Level
  {
    get
    {
      return experience / 1000;
    }
  }
}
```

* 접근자(getter)와 설정자(setter)를 더 간단하게 구현할 수 있다.

* `get`이나 `set`을 생략해 읽기 전용이나 쓰기 전용으로 만들 수 있다.

### [인덱서](https://docs.microsoft.com/ko-kr/dotnet/csharp/programming-guide/indexers)(Indexers)

```cs
class SampleCollection<T>
{
   private T[] arr = new T[100];
   
   public T this[int i] { get; set; }
}

class Program
{
   static void Main()
   {
      var stringCollection = new SampleCollection<string>();
      stringCollection[0] = "Hello, World";
      Console.WriteLine(stringCollection[0]);
   }
}
```

* 클래스나 구조체의 인스턴스를 배열처럼 인덱싱한다.


## [확장명 메소드](https://docs.microsoft.com/ko-kr/dotnet/csharp/programming-guide/classes-and-structs/extension-methods)(Extension Methods)

```cs
using UnityEngine;
using System.Collections;

// 일반적으로 확장 메소드는 한 static 클래스에 모아 둔다
public static class ExtensionMethods
{
  // 확장 메소드는 static이어야 한다
  // this 키워드 클래스의 확장 메소드가 된다
  public static void ResetTransformation(this Transform trans)
  {
      trans.position = Vector3.zero;
      trans.localRotation = Quaternion.identity;
      trans.localScale = new Vector3(1, 1, 1);
  }
}
```

```cs
using UnityEngine;
using System.Collections;

public class SomeClass : MonoBehaviour 
{
    void Start () {
        // 메소드가 호출된 객체가 자동으로 첫 매개변수가 된다
        transform.ResetTransformation();
    }
}
```
