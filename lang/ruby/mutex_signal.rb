
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
