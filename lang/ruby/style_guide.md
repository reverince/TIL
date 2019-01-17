# 루비 스타일 가이드

상위 : [루비](../ruby)

[Ruby Style Guide](https://github.com/styleguide/ruby) _by GitHub_

## 일반

- 두 개의 스페이스(soft-tabs)로 들여쓰기한다. (Tab을 눌러 들여쓰는 것은 hard-tabs)

- 각 행은 100자 미만이 좋다.

- 절대 행 끝에 공백이 있어선 안 된다.

- 마지막 코드 뒤에 빈 줄이 있어야 한다.

- 연산자 양쪽, 쉼표 뒤, `{` 양쪽, `}` 앞에 공백을 둔다.

```ruby
sum = 1 + 2
a, b = 1, 2
1 > 2 ? true : false; puts "Hi"
[1, 2, 3].each { |e| puts e }
```
- `!`, `(`, `[` 뒤 혹은 `)`, `]` 앞에는 공백을 두지 않는다.

```ruby
some(arg).other
[1, 2, 3].length
!array.include?(element)
```
- `when`은 `case`와 같은 들여쓰기 단계를 적용한다.

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
- 각 `def` 사이, 논리적 문단 사이에 빈 줄을 둔다.

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

- 클래스 변수(`@@`)의 사용을 피한다. 상속 시 비직관적이다.

- 싱글턴 메소드를 정의할 때는 `def self.method`를 사용한다. 리팩터링 등으로 클래스명을 바꿀 때 일일이 수정할 필요가 없다.

- 필요한 경우가 아니면 `class << self`의 사용을 피한다.

```ruby
# good
class << self
  attr_accessor :per_page
  alias_method :nwo, :find_by_name_with_owner
end
```

- `public`, `protected`, `private`는 적용할 메소드의 들여쓰기 단계에 맞추고 앞에 한 줄을 비운다.

## 컬렉션

- 문자열 배열을 만들 때는 `%w`를 사용한다.

```ruby
# bad
STATES = ["draft", "open", "closed"]

# good
STATES = %w(draft open closed)
```

- 컬렉션 내 중복을 허용하지 않는 경우 배열(`Array`) 대신 집합(`Set`)을 사용한다.

- 해시의 키로는 문자열이 아닌 심볼을 사용한다.

```ruby
# bad
hash = { "one" => 1, "two" => 2, "three" => 3 }

# good
hash = { one: 1, two: 2, three: 3 }
```

## 예외 처리

- 흐름 제어에 예외 처리를 사용하지 않는다.

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

- `Exception`이나 `StandardError` 대신 구체적인 예외를 지정한다.

## 해시

- 키가 심볼일 때는 루비 1.9 신택스를 적용한다.

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

- 단, 키 타입이 섞여 있는 경우 해시로켓 스타일을 적용한다.

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

- 변수와 메소드에는 `snake_case`를 적용한다.

- 클래스와 모듈에는 `CamelCase`를 적용한다. 이때 'HTTP' 같은 약어는 그대로 대문자로 적는다. ('Http'처럼 쓰는 자바와는 다르다!)

- 상수에는 `SCREAMING_SNAKE_CASE`를 적용한다.

- 참/거짓을 반환하는 메소드 이름 끝에는 물음표를 붙인다.  (`Array#empty?`)

- 인스턴스나 인자를 수정하는 등 잠재적으로 위험한 메소드 이름 끝에는 느낌표를 붙인다. 느낌표 메소드는 그냥 메소드가 있어야 만들 수 있다. (`Array#sort!`)

## %

- `%w`는 자유롭게 사용한다.

- `%()`는 보간법(interpolation)과 큰따옴표를 모두 사용하는 한 줄 문자열에 사용한다. 여러 줄 문자열에는 heredoc을 사용한다.

```ruby
# bad (보간법 없음, 이스케이프 시퀀스로도 충분)
%(<div class="text">Some text</div>)

# bad (큰따옴표가 없음)
%(This is #{quality} style)

# bad (여러 줄이므로 heredoc 사용)
%(<div>\n<span class="big">#{exclamation}</span>\n</div>)

# good (보간법, 큰따옴표, 한 줄)
%(<tr><td class="name">#{name}</td>)

# heredoc 예시
<<-HEREDOC
  Nice to meet you, #{user_name}!
  This message is diplayed in multiple lines.
  See you.
HEREDOC
```

- `%r`은 두 개 이상의 `/`를 사용하는 정규표현식에만 사용한다.

## 정규표현식

- `$1`은 가독성이 떨어지므로 네임드 그룹(`?<>`)을 사용한다.

```ruby
# bad
/(regexp)/ =~ string

# '$1'이 뭐지?
process $1

# good
/(?<meaningful_var>regexp)/ =~ string

process meaningful_var
```

- `^`과 `$`는 문자열 전체가 아닌 **줄**의 시작과 끝을 나타낸다. 문자열의 시작과 끝은 `\A`와 `\Z`를 사용해야 한다.

- 복잡한 정규표현식에는 `x`를 이용해 주석을 달 수 있다. 공백은 무시된다.

```ruby
regexp = %r{
  (group)       # 첫 그룹
  \s            # 공백은 이렇게
  (?:alt1|alt2) # 선택
}x
```

## 문자열

- 연결(concatenation)보다는 보간법(interpolation)을 사용한다.

```ruby
# bad
email_with_name = user.name + " <" + user.email + ">"

# good
email_with_name = "#{user.name} <#{user.email}>"
```

- 보간법과 확장 문자(escaped character; `/n`, …)를 사용할 때도 바꿀 필요가 없는 `"`를 사용한다. `'`는 문자열에서 `"`보다 많이 나온다.

- 되도록 `+`보다 `<<`를 이용해 문자열을 연결한다. `+` 연산자는 새 문자열 객체를 잔뜩 만들어서 `<<`보다 항상 느리다.

## 구문

- 받는 인자가 없는 `def`에는 괄호를 치지 않는다.

- 반드시 `for` 대신 `each`의 반복자(iterator)를 사용한다. `for` 안에서 정의된 변수는 바깥에서 접근이 가능하다.

- 절대 여러 줄 `if`와 `unless`에서 `then`을 사용하지 않는다.

- 직관적인 경우가 아니면 삼항 연산자(`?:`)를 사용하지 않는다. 단, 한 줄의 `if/then/else/end` 구문 대신 삼항 연산자를 사용한다.

- 삼항 연산자를 중첩(nesting)해서, 혹은 여러 줄로 사용하지 않는다. 이때는 `if`나 `unless`를 사용한다.

- `and`, `or` 대신 `&&`, `||`를 사용한다.

- `if`의 실행 내용(body)이 한 줄인 경우 `if`를 뒤에 붙여 사용한다.

```ruby
# bad
if some_condition
  do_something
end

# good
do_something if some_condition
```

- 절대 `unless`와 `else`를 같이 사용하지 않는다. 더 직관적인 `if`와 `else`의 조합으로 대체할 수 있다.

- `if`, `unless`, `while`의 조건문에 괄호를 치지 않는다.

- `do...end`의 블록 내용이 한 줄이라면 `{...}`를 대신 사용한다.

- `return`을 굳이 적을 필요가 없다면 적지 않는다.

- 메소드 인자에 기본값을 지정할 경우 `=` 양쪽에 공백을 둔다.

- `=` 연산자의 반환값을 바로 이용해도 된다.

```ruby
# bad
if (v = array.grep(/foo/)) ...

# good
if v = array.grep(/foo/) ...

# also good - 올바른 우선순위
if (v = next_value) == "hello" ...
```

- 변수 초기화에 `||=`는 자유롭게 사용한다. **단**, `true`는 사용하지 않는다.

```ruby
# name이 nil 혹은 false일 때만 Matsu를 대입한다.
name ||= "Matsu"

# bad - enabled가 false여도 true가 되어 버린다.
enabled ||= true

# good
enabled = true if enabled.nil?
```

- Perl 스타일의 스페셜 변수(`$0`, `$`, …)를 사용하지 않는다. 가독성이 떨어지므로 한 줄 코드 등 특수한 경우가 아니면 `$PROGRAM_NAME` 등 이름을 사용한다.

- 절대 메소드 이름과 여는 괄호 사이에 공백을 두지 않는다.

- 메소드 첫 인자가 여는 괄호로 시작하면 `f((3 + 2) + 1)`처럼 모든 인자를 괄호로 묶는다.

- 사용하지 않는 블록 매개 변수(parameter)는 `_`로 표시한다.

```ruby
# bad
result = hash.map { |k, v| v + 1 }

# good
result = hash.map { |_, v| v + 1 }
```

- 자료형 검사에 `===`를 사용하지 않는다. `String === "hi"`는 참이지만 `"hi" === String`은 거짓이다. 가독성이 떨어진다. 대신 `is_a?`나 `kind_of?`를 사용할 수 있다. 자료형을 엄격하게 검사하는 코드는 리팩터링하는 것이 좋다.
