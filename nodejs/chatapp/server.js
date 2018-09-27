var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

var port = process.env.PORT || 3000
var names = {};

const CONNECTED_MESSAGE = '[SYS] 새로운 유저가 접속했어요: '
const DISCONNECTED_MESSAGE = '[SYS] 유저가 접속을 해제했어요: '

app.get('/', function(req, res) {
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', function(socket) {
  
  socket.on('join', function(name) {
    names[socket.id] = name;
    console.log('유저 접속: ' + name);
    io.emit('chat message', CONNECTED_MESSAGE + name)
  });
  
  socket.on('disconnect', function() {
    name = names[socket.id] || null;
    console.log('유저 접속 해제: ' + name);
    io.emit('chat message', DISCONNECTED_MESSAGE + name)
  });
  
  socket.on('chat message', function(msg) {
    console.log('메시지: ' + msg);
    io.emit('chat message', msg);
  });
});

http.listen(port, function() {
  console.log('Server running at https://levnode.run.goorm.io');
});
