# [Node.js] Chat

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

* '입력 중'

* 접속 목록

* DM
