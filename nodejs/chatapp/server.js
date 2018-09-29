var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

var port = process.env.PORT || 3000;
var names = {};
var typers = new Set();

const CONNECTED_MESSAGE_WITH = '[SYS] 새로운 유저가 접속했어요: ';
const DISCONNECTED_MESSAGE_WITH = '[SYS] 유저가 접속을 해제했어요: ';
const PLEASE_JOIN_MESSAGE = '[SYS] 새로고침해서 이름을 정해 주세요.';
const TYPER_MESSAGE_WITH = '[SYS] 입력중인 유저: ';

app.get('/', function(req, res) {
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', function(socket) {
  
  socket.on('join', function(name) {
    names[socket.id] = name;
    console.log('유저 접속: ' + name);
    io.emit('chat message', CONNECTED_MESSAGE_WITH + name);
  });
  
  socket.on('disconnect', function() {
    name = names[socket.id];
    if (name != undefined) {
      console.log('유저 접속 해제: ' + name);
      io.emit('chat message', DISCONNECTED_MESSAGE_WITH + name);
      delete names[socket.id];
      typers.delete(name);
      updateTypers();
    }
  });
  
  socket.on('chat message', function(msg) {
    if (socket.id in names) {
      console.log('메시지: ' + msg);
      io.emit('chat message', names[socket.id] + ': ' + msg);
    } else {
      console.log('미참가 유저의 채팅 시도: ' + socket.id);
      socket.emit('chat message', PLEASE_JOIN_MESSAGE);
    }
  });
  
  socket.on('typing', function(isTyping) {
    name = names[socket.id];
    if (name != undefined) {
      if (isTyping) {
        if (socket.id in typers) {
          // Do nothing
        } else {
          typers.add(name);
        }
      } else {
        typers.delete(name);
      }
      updateTypers();
    }
  });
  
  function updateTypers() {
    if (typers.size > 0) {
      typersJoined = Array.from(typers).join(', ');
      io.emit('typer notice', TYPER_MESSAGE_WITH + typersJoined)
    } else {
      io.emit('typer notice', '');
    }
  }
  
});

http.listen(port, function() {
  console.log('Server running at https://levnode.run.goorm.io');
});
