var app = require('http').createServer(handler),
    io = require('socket.io').listen(app),
    fs = require('fs');

app.listen(3000);
console.log("Server running at https://levnode.run.goorm.io");

// Node.js

function handler (req, res) {
  fs.readFile(__dirname + '/index.html',
    function (err, data) {
      if (err) {
        res.writeHead(500);
        return res.end('Error loading index.html');
      }
      
      res.writeHead(200);
      res.end(data);
    });
}

// socket.io

// 클라이언트 연결 시 발생하는 이벤트
io.sockets.on('connect', function (socket) {
  // 모든 연결된 클라이언트에게 news 이벤트(json 데이터)를 emit
  socket.emit('news', { hello: 'world' });
  // my other event 이벤트 핸들러
  socket.on('my other event', function (data) {
    // 서버 측 콘솔에 로깅
    console.log(data);
  });
});
