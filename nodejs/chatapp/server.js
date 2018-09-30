var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var _ = require('lodash');

var port = process.env.PORT || 3000;
var names = {};
var typers = new Set();

const CONNECTED_MESSAGE_WITH = '[SYS] 새로운 유저가 접속했어요: ';
const DISCONNECTED_MESSAGE_WITH = '[SYS] 유저가 접속을 해제했어요: ';
const PLEASE_JOIN_MESSAGE = '[SYS] 새로고침해서 이름을 정해 주세요.';
const USERS_NOTICE_WITH = '[SYS] 참가 중인 유저: ';
const TYPERS_NOTICE_WITH = '[SYS] 입력 중인 유저: ';
const CANNOT_FIND_USER_MESSAGE = '[SYS] 해당 유저를 찾을 수 없어요.';

app.get('/', function(req, res) {
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', function(socket) {
  
  socket.on('join', function(name) {
    names[socket.id] = name;
    console.log('유저 참가: ' + name);
    io.emit('chat message', CONNECTED_MESSAGE_WITH + name);
    updateUsers();
  });
  
  socket.on('disconnect', function() {
    var name = names[socket.id];
    if (name != undefined) {
      console.log('유저 접속 해제: ' + name);
      io.emit('chat message', DISCONNECTED_MESSAGE_WITH + name);
      delete names[socket.id];
      updateUsers();
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
    var name = names[socket.id];
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
  
  socket.on('direct message', function(to, msg) {
    var idx = _.values(names).indexOf(to);
    if (idx > -1) {
      var id = _.keys(names)[idx];
      var from = names[socket.id];
      var msg = '[DM] ' + from + ' → ' + to + ' : ' + msg;
      socket.emit('chat message', msg);
      io.to(id).emit('chat message', msg);
    } else {
      socket.emit('chat message', CANNOT_FIND_USER_MESSAGE);
    }
  });
  
  function updateUsers() {
    var usersJoined = _.values(names).join(', ');
    io.emit('update users', USERS_NOTICE_WITH + usersJoined);
  }
  
  function updateTypers() {
    if (typers.size > 0) {
      var typersJoined = Array.from(typers).join(', ');
      io.emit('update typers', TYPERS_NOTICE_WITH + typersJoined);
    } else {
      io.emit('update typers', '');
    }
  }
  
});

http.listen(port, function() {
  console.log('Server running at https://levnode.run.goorm.io');
});
