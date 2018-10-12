# Ruby

하위 : [루비 스타일 가이드](./style_guide.md), [레일즈](/rails)

* [Ruby-Doc.org](https://ruby-doc.org)
* [Tutorialspoint Ruby Tutorial](https://www.tutorialspoint.com/ruby/index.htm)


## 트릭

[21 Ruby Tricks You Should Be Using In Your Own Code](http://www.rubyinside.com/21-ruby-tricks-902.html)

#### DRY by Enumerator

```ruby
%w(player room item).each { |x| require_relative x }
```

#### Exploding Enumerables

```ruby
a = %w(a b c d e f g)
b = [2, 3, 5]
a.values_at(*b)  # => ["c", "d", "f"]
```


## 문법

```ruby
=begin
여러 줄 주석
=end

i = 0
# 한 줄 반복문
puts i until (i+=1) >= 10
# 여러 줄 반복문
begin
  puts i
end while (i-=1) >= 1

# 재도전 (retry)
c = "7"
begin
  p c + 3
rescue TypeError
  c = c.to_i
  retry  # begin으로
end

# 별명 (alias)
def a_very_long_method_name
  return true
end
alias a_ a_very_long_method_name
a_()
```


## 배열

```ruby
pois = Array.new(5, "poi")
# => ["poi", "poi", "poi", "poi", "poi"]
growing_pois = Array.new(5) {|i| "poi" * i}
# => ["", "poi", "poipoi", "poipoipoi", "poipoipoipoi"]

poipois = pois.collect {|i| i*2}
# => ["poipoi", "poipoi", "poipoi", "poipoi", "poipoi"]

```


## 변수

```ruby
$gv = 1
@iv = 2
@@cv = 3

def puts_v
  puts "전역 변수 g는 #$g"
  puts "인스턴스 변수 i는 #@i"
  puts "클래스 변수 c는 #@@c"
end
```


## 리터럴 (Literal)

```ruby
0b1011  # 2진수
0127  # 8진수
0x1f  # 16진수
?a  # 'a' 문자 코드
?\n  # newline 코드
1.0e6
```


## [연산자](https://www.tutorialspoint.com/ruby/ruby_operators.htm)

* `.eql?` : 자료형과 값이 같은지 확인한다.
* `.equal?` : 객체 ID가 같은지 확인한다.
* `defined?` : 변수나 메소드 등이 정의되었는지 확인한다.

## 블록 / `Proc` / `lambda`

### [블록](https://www.tutorialspoint.com/ruby/ruby_blocks.htm)

```ruby
def blk
  puts "~블록의 시작~"
  yield "1층"
  puts "~블록의 중간~"
  yield "2층"
  puts "~블록의 끝~"
end

blk { |loc| puts "여긴 #{loc}이에요." }
```
```
~블록의 시작~
여긴 1층이에요.
~블록의 중간~
여긴 2층이에요.
~블록의 끝~
```

#### `BEGIN` / `END`

```ruby
BEGIN {
  # MAIN보다 먼저 실행
}
END {
  # MAIN 이후에 실행
}
# MAIN
...
```

### `Proc`

```ruby
new_prc = Proc.new { puts "Hello, new Proc!" }
new_prc.call()
new_prc.()
new_prc[]

prc = proc {puts "Hello, Kernel#proc!" }
```

### `lambda`

```ruby
lmd = lambda { puts "Hello, lambda!" }
lmd.call()
lmd.()
lmd[]

sweet_lmd = ->{ puts "Hello, ->!" }
```


## 객체

* 접근 제어
  ```ruby
  class Class
    def method
      ...
    end
    private :method
  end
  ```

* 클래스 상수
  ```ruby
  class Class
    CLASSCONST = "This is a constant."
  end

  puts Class::CLASSCONST
  ```


## [예약 변수](https://www.tutorialspoint.com/ruby/ruby_predefined_variables.htm) (Predefined Variable)

* `$!` : 마지막 오류(Exception). `rescue Exception => e`의 `e`처럼 접근할 수 있다.
* `$@` : 마지막 오류의 백트레이스.
* `$/` : 입력 분리자. 기본값은 `\n`.
* `$\` : 출력 분리자. 기본값은 `nil`.
* `$,` : `print()`와 `Array#join` 메소드의 분리자. 기본값은 `nil`.
* `$;` : `String#split`의 분리자. 기본값은 `nil`.
* `$_` : 로컬에서 `gets`나 `readline`으로 입력받은 마지막 값. 기본값은 `nil`.
* `$~` : 로컬에서 `Regex#match`에 의한 마지막 매치 데이터(`MatchData`).
* `$&` : 로컬에서 `Regex#match`에 의한 마지막 매치 문자열.
* `$0` : 지금 실행 중인 루비 프로그램 이름.
* `$$` : 지금 실행 중인 루비 프로그램 PID.
* `$?` : 마지막으로 종료된 프로세스의 종료 상태(exit status).
* `$DEBUG`(`$-d`) : `-debug` 혹은 `-d` 옵션 적용 여부.

## [소켓](https://ruby-doc.org/stdlib-2.5.0/libdoc/socket/rdoc/Socket.html)

###### socket_server.rb

```ruby
require "socket"

port = 3000

server = TCPServer.open(port)
puts "#{port}번 포트에서 소켓 서버 실행 중"

loop {
  # 클라이언트 접속 시도 시 새 스레드를 만들어 수락
  Thread.new(server.accept) { |client|
    puts "클라이언트 접속"
    # 메시지 전송
    client.puts("안녕하세요. 지금 시간을 알려드릴게요.")
    sleep(1)
    client.puts(Time.now.getlocal("+09:00"))
    sleep(1)
    client.puts "또 오세요."
    # 클라이언트 연결 해제
    client.close
    puts "클라이언트 연결 해제"
    }
}
```

###### socket_client.rb

```ruby
require "socket"

s = TCPSocket.open("localhost", 3000)

# 서버 메시지를 콘솔에 출력
while line = s.gets
  puts line.chop
end
# 연결 해제
s.close
```


## [스레드](https://ruby-doc.org/core-2.5.0/Thread.html)

* 프로세스(메인 스레드)가 종료되면 모든 스레드는 완료 여부에 상관없이 삭제(`kill`)당하기 때문에 `join`으로 실행 완료를 기다려줘야 한다.
  ```ruby
  # 새로운 스레드
  thr = Thread.new { puts "Whats the big deal" }

  # 스레드 실행 완료를 기다린다.
  thr.join
  ```

* 여러 개의 스레드를 만드는 경우 배열과 `each`를 사용할 수 있다.
  ```ruby
  threads = []
  threads << Thread.new { puts "Whats the big deal" }
  threads << Thread.new { 3.times { puts "Threads are fun!" } }

  threads.each { |thr| thr.join }
  ```

* `exit`, `status` 등의 메서드를 사용할 수 있다.
  ```ruby
  thr = Thread.new {sleep}

  thr.status  # => "sleep"
  thr.alive?  # => "true"
  thr.stop?   # => "false"
  thr.exit
  thr.status  # => "false"
  thr.alive?  # => "false"
  ```

### 상호 배제 (Mutual Exclusion; Mutex)

* DB의 **원자성** 확보
  * 실행 중인 여러 개의 스레드가 같은 데이터에는 동시에 접근하지 못해야 한다.

###### counter_no_mutex.rb

```ruby
COUNT_MAX = 5

count = 0
threads = []

3.times do |i|
  threads[i] = Thread.new {
    while (count < COUNT_MAX) do
      count_current = count
      sleep(rand)  # 0..1s
      count = count_current + 1
      puts "스레드 #{i} : 카운터 #{count}"
    end
    }
end

threads.each { |t| t.join }
```

```
스레드 0 : 카운터 1
스레드 2 : 카운터 1
스레드 1 : 카운터 1
스레드 2 : 카운터 2
스레드 0 : 카운터 2
스레드 2 : 카운터 3
스레드 1 : 카운터 2
...
스레드 1 : 카운터 5
```

###### counter_mutex.rb

```ruby
...
mutex = Mutex.new

3.times do |i|
  threads[i] = Thread.new {
    while (count < COUNT_MAX) do
      mutex.synchronize do
        count += 1
        puts "스레드 #{i} : 카운터 #{count}"
      end
      sleep(rand)  # 0..1s
    end
    }
end

threads.each { |t| t.join }
```

```
스레드 0 : 카운터 1
스레드 1 : 카운터 2
스레드 1 : 카운터 3
스레드 2 : 카운터 4
스레드 0 : 카운터 5
```

### 교착 상태(Deadlock) 핸들링

###### mutex_signal.rb

```ruby
threads = []
mutex = Mutex.new
cv = ConditionVariable.new

threads[0] = Thread.new {
  mutex.synchronize {
    puts "스레드 0 : 처음으로 실행됐어요"
    sleep(1)
    puts "스레드 0 : cv 신호를 기다릴게요"
    sleep(1)
    cv.wait(mutex)
    puts "스레드 0 : cv 신호를 받고 나왔어요"
    sleep(1)
    }
  }

threads[1] = Thread.new {
  mutex.synchronize {
    puts "스레드 1 : 스레드 0의 상태는 #{threads[0].status}입니다"
    sleep(1)
    puts "스레드 1 : cv 신호를 발신합니다"
    sleep(1)
    cv.signal
    puts "스레드 1 : 마저 끝냅니다"
    sleep(1)
    }
  }

threads.each { |t| t.join }
```

```
스레드 0 : 처음으로 실행됐어요
스레드 0 : cv 신호를 기다릴게요
스레드 1 : 스레드 0의 상태는 sleep입니다
스레드 1 : cv 신호를 발신합니다
스레드 1 : 마저 끝냅니다
스레드 0 : cv 신호를 받고 나왔어요
```
