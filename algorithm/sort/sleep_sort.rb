def sleep_sort(vc, sleep_time=0.1)
  
  ret = []
  threads = []

  vc.each { |n|
    threads << Thread.new { sleep(n * sleep_time); ret << n}
  }

  threads.each { |thr| thr.join }

  ret
end

vc = [2, 5, 9, 3, 4, 8, 7, 1, 6]
1.upto(24) { |i|
  sleep_time = 1.0 / i.to_f
  puts "sleep_time: 1/" + i.to_s + " sec"
  puts "result: [" + sleep_sort(vc, sleep_time)*", " + "]"
}
