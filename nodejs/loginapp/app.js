var express = require('express');
var bodyParser = require('body-parser'); // html의 <body>를 파싱해 body.id처럼 사용할 수 있음
var app = express();

app.use(bodyParser.json()); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true })); // for parsing application/x-www-form-urlencoded

app.get('/', function (req, res) {
  // send는 html을 직접 보내주는 방식
  res.send('Hello World!');
});

// https://.../login
app.get('/login', function (req, res) {
  // sendFile은 경로의 html이나 jade 파일을 보내주는 방식
  res.sendFile(__dirname + '/login.html');
});

app.post('/login', function (req, res) {
  var id = req.body.id; // request html body에서 name="id"의 값을 찾아옴
  var pw = req.body.pw;
  var result = login(id, pw);
  res.send(result);
});

function login(id, pw) {
  return 'user' === id && 'pass' === pw;
}

app.listen(3000, () => console.log('Server running at https://levnode.run.goorm.io'));
