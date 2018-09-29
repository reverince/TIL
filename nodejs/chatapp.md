# [Socket.io] Chat (`chatapp/`)

https://socket.io/get-started/chat

###### server.js

```js
var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

var port = process.env.PORT || 3000

app.get('/', function(req, res) {
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', function(socket) {
  console.log('유저 접속');
  
  socket.on('disconnect', function() {
    console.log('유저 접속 해제');
  });
  
  socket.on('chat message', function(msg) {
    console.log('메시지: ' + msg);
    io.emit('chat message', msg);
  });
});

http.listen(port, function() {
  console.log('Server running at https://levnode.run.goorm.io');
});
```

###### index.html

```html
...

<script src="/socket.io/socket.io.js"></script>
<script src="https://code.jquery.com/jquery-1.11.1.js"></script>
<script>
  $(function() {
    var socket = io();

    $('form').submit(function() {
      socket.emit('chat message', $('#msg').val());
      $('#msg').val('');
      return false;
    });

    socket.on('chat message', function(msg) {
      $('#messages').append($('<li>').text(msg));
    });

  });
</script>
```


## 숙제

* 유저 접속/접속 해제 시 방에 알리기

  ```js
  const CONNECTED_MESSAGE = '[SYS] 새로운 유저가 접속했어요.'
  const DISCONNECTED_MESSAGE = '[SYS] 유저가 접속을 해제했어요.'

  io.on('connection', function(socket) {
    console.log('유저 접속');
    io.emit('chat message', CONNECTED_MESSAGE)

    socket.on('disconnect', function() {
      console.log('유저 접속 해제');
      io.emit('chat message', DISCONNECTED_MESSAGE)
    });

    ...
  });
  ```

* 닉네임

  ```js
  const CONNECTED_MESSAGE_WITH = '[SYS] 새로운 유저가 접속했어요: ';
  const DISCONNECTED_MESSAGE_WITH = '[SYS] 유저가 접속을 해제했어요: ';
  const PLEASE_JOIN_MESSAGE = '[SYS] 새로고침해서 이름을 정해 주세요.';

  ...

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

  });
  ```

  ```js
    var socket = io();
    var hasJoined = false;

    $('#chat').hide();
    $('#name').focus();

    $('#join').submit(function() {
      var name = $('#name').val();
      if (name != '') {
        socket.emit('join', name);
        $('#join').detach();
        $('#chat').show();
        $('#msg').focus();
        hasJoined = true;
      }
      return false;
    });

    $('#chat').submit(function() {
      if (hasJoined) {
        socket.emit('chat message', $('#msg').val());
        $('#msg').val('');
      } else {
        $('#chat').hide();
        $('#join').show();
        $('#name').focus();
      }
      return false;
    });

    socket.on('chat message', function(msg) {
      $('#messages').append($('<li>').text(msg));
    });
  ```

  * `if (name != undefined)`와 `hasJoined`로 각각 서버와 클라이언트에서 유저의 참가 여부를 확인한다.

* '입력 중'

  ```js
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
  ```

  ```js
  socket.on('typer notice', function(msg) {
    $('#typers').text(msg);
  });

  const TYPING_TIMEOUT = 1000;  // 밀리초
  var typingTimeout;
  $('#msg').on('keyup', function() {
    socket.emit('typing', true);
    if (typingTimeout != undefined) clearTimeout(typingTimeout);
    typingTimeout = setTimeout(stopTyping, TYPING_TIMEOUT);
  });
  function stopTyping() {
      socket.emit('typing', false);
  }
  ```

* 접속 목록

* DM
