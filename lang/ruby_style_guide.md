# 루비 스타일 가이드

[Ruby Style Guide](https://github.com/styleguide/ruby)

## 일반

* 두 개의 스페이스(soft-tabs)로 들여쓰기한다. (Tab을 눌러 들여쓰는 것은 hard-tabs)

* 각 행은 100자 미만이 좋다.

* 행 끝에 공백이 있어선 안 된다.

* 마지막 코드 뒤에 빈 줄이 있어야 한다.

* 연산자 양쪽, 쉼표 뒤, `{` 양쪽, `}` 앞에 공백을 둔다.

```ruby
sum = 1 + 2
a, b = 1, 2
1 > 2 ? true : false; puts "Hi"
[1, 2, 3].each { |e| puts e }
```
* `!`, `(`, `[` 뒤 혹은 `)`, `]` 앞에는 공백을 두지 않는다.

```ruby
some(arg).other
[1, 2, 3].length
!array.include?(element)
```
* `when`은 `case`와 같은 들여쓰기 단계를 적용한다.

```ruby
case
when song.duration > 120
  puts "Too long!"
when Time.now.hour > 21
  puts "It's too late"
else
  song.play
end
```
* 각 `def` 사이, 논리적 문단 사이에 빈 줄을 둔다.

```ruby
def some_method
  data = initialize(options)

  data.manipulate!

  data.result
end

def some_method
  result
end
```

## 클래스

* 클래스 변수(`@@`)의 사용을 피한다. 상속 시 비직관적이다.

* 싱글턴 메소드를 정의할 때는 `def self.method`를 사용한다. 리팩터링 등으로 클래스명을 바꿀 때 일일이 수정할 필요가 없다.

* 필요한 경우가 아니면 `class << self`의 사용을 피한다.

```ruby
# good
class << self
  attr_accessor :per_page
  alias_method :nwo, :find_by_name_with_owner
end
```

* `public`, `protected`, `private`는 적용할 메소드의 들여쓰기 단계에 맞추고 앞에 한 줄을 비운다.

## 콜렉션

* 문자열 배열을 만들 때는 `%w`를 사용한다.

```ruby
# bad
STATES = ["draft", "open", "closed"]

# good
STATES = %w(draft open closed)
```

* 콜렉션 내 중복을 허용하지 않는 경우 배열(`Array`) 대신 집합(`Set`)을 사용한다.

* 해시의 키로는 문자열이 아닌 심볼을 사용한다.

```ruby
# bad
hash = { "one" => 1, "two" => 2, "three" => 3 }

# good
hash = { one: 1, two: 2, three: 3 }
```

## 예외 처리

* 흐름 제어에 예외 처리를 사용하지 않는다.

```ruby
# bad
begin
  n / d
rescue ZeroDivisionError
  puts "Cannot divide by 0!"
end

# good
if d.zero?
  puts "Cannot divide by 0!"
else
  n / d
end
```

* `Exception`이나 `StandardError` 대신 구체적인 예외를 지정한다.

## 해시

* 키가 심볼일 때는 루비 1.9 신택스를 적용한다.

```ruby
# good
user = {
  login: "defunkt",
  name: "Chris Wanstrath"
}

user = User.create(login: "jane")
link_to("Account", controller: "users", action: "show", id: user)

# bad
user = {
  :login => "defunkt",
  :name => "Chris Wanstrath"
}

user = User.create(:login => "jane")
link_to("Account", :controller => "users", :action => "show", :id => user)
```

* 단, 키 타입이 섞여 있는 경우 해시로켓 스타일을 적용한다.

```ruby
# good
hsh = {
  :user_id => 55,
  "followers-count" => 1000
}

# bad
hsh = {
  user_id: 55,
  "followers-count" => 1000
}
```

## 키워드 인자

```ruby
def remove_member(user, skip_membership_check=false)
  # ...
end

# 'true'가 무엇을 의미하는지 알 수 없음
remove_member(user, true)
```

```ruby
def remove_member(user, skip_membership_check: false)
  # ...
end

# 이제 알 수 있음!
remove_member user, skip_membership_check: true
```

## 이름 짓기

* 변수와 메소드에는 `snake_case`를 적용한다.

* 클래스와 모듈에는 `CamelCase`를 적용한다. 이때 'HTTP' 같은 약어는 그대로 대문자로 적는다. ('Http'처럼 쓰는 자바와는 다르다!)

* 상수에는 `SCREAMING_SNAKE_CASE`를 적용한다.

* 참/거짓을 반환하는 메소드 이름 끝에는 물음표를 붙인다.  (`Array#empty?`)

* 인스턴스나 인자를 수정하는 등 잠재적으로 위험한 메소드 이름 끝에는 느낌표를 붙인다. 느낌표 메소드는 그냥 메소드가 있어야 만들 수 있다. (`Array#sort!`)
