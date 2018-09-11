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
