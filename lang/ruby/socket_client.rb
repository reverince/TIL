require "socket"

s = TCPSocket.open("localhost", 3000)

# 서버 메시지를 콘솔에 출력
while line = s.gets
  puts line.chop
end
# 연결 해제
s.close
