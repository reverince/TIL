
COUNT_MAX = 5

count = 0
threads = []
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
