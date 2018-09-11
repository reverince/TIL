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
