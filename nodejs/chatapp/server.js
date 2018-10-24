var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var _ = require('lodash');

var port = process.env.PORT || 3000;
var names = {};
var user_colors = {};
var typers = new Set();

const SYS = '[SYS]';
const CONNECTED_MESSAGE_WITH = '새로운 유저가 접속했어요: ';
const DISCONNECTED_MESSAGE_WITH = '유저가 접속을 해제했어요: ';
const PLEASE_JOIN_MESSAGE = '새로고침해서 이름을 정해 주세요.';
const USERS_NOTICE_WITH = '참가 중인 유저: ';
const TYPERS_NOTICE_WITH = '입력 중인 유저: ';
const CANNOT_FIND_USER_MESSAGE = '해당 유저를 찾을 수 없어요.';

String.prototype.hashCode = function() {
  var hash = 0, i, chr;
  if (this.length === 0) return hash;
  for (i = 0; i < this.length; i++) {
    chr   = this.charCodeAt(i);
    hash  = ((hash << 5) - hash) + chr;
    hash |= 0;  // Convert to 32bit integer
  }
  return hash;
};

Number.prototype.pad = function(len) {
  var ret = '' + this;
  if (ret.length >= len) {
    return ret.substring(0, len);
  }
  while (ret.length < len) {
    ret = '0' + ret;
  }
  return ret;
}

String.prototype.generateColor = function() {
  var hash = this.hashCode();
  var code = (hash & 0xFFFFFF).pad(6);
  var ret = '#' + code;
  console.log(ret);
  return ret;
}

app.get('/', (req, res) => {
  res.sendFile(__dirname + '/index.html');
});


io.on('connection', (socket) => {
  
  socket.on('join', (name) => {
    names[socket.id] = name;
    user_colors[socket.id] = name.generateColor();
    console.log('유저 참가: ' + name);
    let data = {"name": SYS, "message": CONNECTED_MESSAGE_WITH + name};
    io.emit('chat message', data);
    var msg = data["message"];
    io.emit('toast', msg);
    updateUsers();
  });
  
  socket.on('disconnect', () => {
    var name = names[socket.id];
    if (name != undefined) {
      console.log('유저 접속 해제: ' + name);
      let data = {"name": SYS, "message": DISCONNECTED_MESSAGE_WITH + name};
      io.emit('chat message', data);
      var msg = data["message"];
      io.emit('toast', msg);
      delete names[socket.id];
      updateUsers();
      typers.delete(name);
      updateTypers();
    }
  });
  
  socket.on('chat message', (msg) => {
    if (socket.id in names) {
      var author = names[socket.id];
      var color = user_colors[socket.id];
      console.log(author + ': ' + msg);
      let data = {"name": author, "message": msg, "color": color};
      io.emit('chat message', data);
    } else {
      console.log('미참가 유저의 채팅 시도: ' + socket.id);
      let data = {"name": SYS, "message": PLEASE_JOIN_MESSAGE};
      socket.emit('chat message', data);
    }
  });
  
  socket.on('typing', (isTyping) => {
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
  
  socket.on('direct message', (to, msg) => {
    var idx = _.values(names).indexOf(to);
    if (idx > -1) {
      var id = _.keys(names)[idx];
      var from = names[socket.id];
      var msg = '[DM] ' + from + ' → ' + to + ' : ' + msg;
      let data = {"name": from, "message": msg};
      socket.emit('chat message', data);
      io.to(id).emit('chat message', data);
    } else {
      let data = {"name": SYS, "message": CANNOT_FIND_USER_MESSAGE};
      socket.emit('chat message', data);
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

http.listen(port, () => {
  console.log('Server running at https://levnode.run.goorm.io');
});
