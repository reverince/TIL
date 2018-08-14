var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.get('/', function(req, res) {
  res.sendfile('index.html');
});

io.on('connect', function(socket) {
  console.log('User connected');
  
  io.on('connection', function(socket) {
    socket.on('chat message', function(msg) {
      console.log('message: ' + msg);
      io.emit('chat message', msg);
    });
  });
  
  socket.on('disconnect', function(){
    console.log('User disconnected');
  });
});

http.listen(3000, function() {
  console.log('Server running at https://levnode.run.goorm.io');
});
