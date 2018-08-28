# Ruby

### [스레드](https://ruby-doc.org/core-2.5.0/Thread.html)

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

#### 상호 배제 (Mutual Exclusion; Mutex)

* DB의 **원자성** 확보
  * 실행 중인 여러 개의 스레드가 같은 데이터에는 동시에 접근하지 못해야 한다.

###### ruby_counter_no_mutex.rb

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

###### ruby_counter_mutex.rb

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
