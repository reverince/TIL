var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var _ = require('lodash');
var mongoClient = require('mongodb').MongoClient;
var assert = require('assert');

var port = process.env.PORT || 3000;
var db_url = 'mongodb://localhost:27017/q4u';

var db;
mongoClient.connect(db_url, { useNewUrlParser: true }, (err, _db) => {
  assert.equal(null, err);
  console.log("MongoDB 서버 접속");
  db = _db;
});


function insertUser(db, username, password) {
  var collection = db.db().collection('users');
  collection.insertOne({
    "username": username,
    "password": password
  }, (err, result) => {
    assert.equal(null, err);
    console.log(result.result);
    console.log(result.ops);
  });
}

var findAllUsernames = (db, callback) => {
  var collection = db.db().collection('users');
  collection.find({}).toArray((err, docs) => {
    assert.equal(err, null);
    console.log('findUsers 호출');
    console.log(docs);
    var usernames = docs.map((doc) => {return doc.username})
    console.log(usernames);
    callback(usernames);
  });
};

app.get('/', (req, res) => {
  res.sendFile(__dirname + '/index.html');
});

app.get('/register', (req, res) => {
  res.sendFile(__dirname + '/register.html');
});

app.get('/users', (req, res) => {
   res.sendFile(__dirname + '/users.html');
});

app.post('/users_p', (req, res) => {
   console.log('app.post /users_p 호출');
   var inputData;

   req.on('data', (data) => {
     inputData = JSON.parse(data);
   });

   req.on('end', () => {
     console.log('user_id : '+inputData.user_id+' , name : '+inputData.name);
   });

   res.write("OK!");
   res.end();
});


io.on('connection', (socket) => {
  console.log('접속');
  
  socket.on('register', (username, password) => {
    insertUser(db, username, password);
  });
    
  socket.on('disconnect', () => {
    console.log('접속 해제');
  });
  
  socket.on('users', () => {
    var usernames;
    findAllUsernames(db, (_usernames) => {
      usernames = _usernames;
      socket.emit('usernames', usernames);
    });
  });
  
});

http.listen(port, () => {
  console.log('Server running at https://levnode.run.goorm.io');
});
