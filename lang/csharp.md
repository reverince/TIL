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

* 자식 클래스에서 부모 클래스 멤버에 접근하는 데 사용한다


## `where` : [제네릭 형식 제약 조건](https://docs.microsoft.com/ko-kr/dotnet/csharp/language-reference/keywords/where-generic-type-constraint)

```cs
public T genericMethod<T>(T param) where T : new()
{
  return param;
}
```

* `where`으로 `T`의 제약 조건을 지정할 수 있다.

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
