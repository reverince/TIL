# Node.js

참고 강의 : [국방부오픈소스아카데미](http://osam.oss.kr) APP 개발 과정 _by 허광남_


## 특징

* 자바스크립트 기반
* 비동기 프로그래밍
* 멀티플랫폼
* Server-side
* Desktop Application

#### 장점

* [npm](https://www.npmjs.com)
* 성능 개선
  * [Rails와의 비교](https://medium.com/@TechMagic/nodejs-vs-ruby-on-rails-comparison-2017-which-is-the-best-for-web-development-9aae7a3f08bf)
* C/C++ 애드온
* 서버와 클라이언트에서 같은 언어를 사용한다.

#### 단점

* 싱글스레드로 인한 CPU 병목
* 예외 처리를 실수하면 서버가 다운된다.
* Callback
* 불안정한 API


## 비동기 프로그래밍

* 기존 코드와 달리 콜백 함수를 같이 넘겨주는 형태
* 이벤트 핸들링에 많이 쓰이는 기법
* 이벤트 루프를 사용해 싱글스레드에서 동작

#### blocking / non-blocking

* IO 비용

| IO 종류 | 비용 |
| - | -: |
| L1-cache | 3 사이클 |
| L2-cache | 14 사이클 |
| RAM | 250 사이클 |
| Disk | 41,000,000 사이클 |
| Network | 240,000,000 사이클 |

* 동기식은 응답이 와야 이후 코드가 실행된다. 대신 코드가 간단하다.
  ```javascript
  var result = db.query("SELECT ...");
  // use result
  ```

* 비동기식은 응답을 기다리지 않아도 된다. 대신 코드가 복잡해진다.
  ```javascript
  db.query("SELECT ...", function (result) {
    // use result
  });
  ```


## 개발 환경

* Node.js 설치 : [구름 IDE](https://ide.goorm.io/) ([설치방법](https://nodejs.org/ko/download/package-manager/#debian-ubuntu-linux))
  ```console
  curl -sL https://deb.nodesource.com/setup_8.x | sudo -E bash -
  sudo apt-get install -y nodejs
  ```

* URL / 포트 / 실행 설정
  * `프로젝트` - `실행 URL과 포트` : 사용할 URL(`https://example.run.goorm.io`)과 포트(일반적으로 `8000`) 등록
  * `프로젝트` - `빌드/실행 설정` : 실행 방식 `Server` 선택

* 실행
  ```console
  node nodejs/main.js
  ```

#### npm

* 패키지 설치 : `npm install lodash`
  * 현재 경로에 `node_modules` 디렉토리가 만들어지고 그 안에 설치된다.
  * `-g` 옵션 : (로컬이 아닌) 전역 설치

* `package.json`
  * 현재 경로에 `package.json` 파일이 있으면 이 파일에 명시된 버전의 패키지를 자동으로 설치한다.
  * `npm init`으로 만들 수 있다.
  * `npm install --save` 옵션으로 `package.json`에 패키지를 자동으로 추가할 수 있다.
  * `"scripts"` 안의 페어는 `npm start`처럼 실행할 수 있다.
  * Rails의 `Gemfile`?

  ```
  {
    "name": "demo-app",
    "version": "1.0.0",
    "dependencies": {
      "lodash": "^4.17.10"
    },
    "scripts": {
      "start": "node index.js"
    }
  }
```


## 디버깅

#### 크롬 개발자 도구 (F12)

* Source 탭
  * 줄번호를 클릭해 중단점(break point)을 지정할 수 있다.
* Console 탭
  * 스크립트에 정의된 변수에 접근하거나 함수를 사용할 수 있다.

#### Node.js 디버깅

* `node debug`
  * 콘솔에서 `cont`, `next` 명령으로 디버깅 진행

* [node-inspector](https://github.com/node-inspector/node-inspector)
  * `npm install -g node-inspector`
  * `node-debug main.js`
  * 브라우저에서 디버깅 진행


## [Express](http://expressjs.com)

* Node.js 웹 애플리케이션 프레임워크

#### 프로젝트 작성

* 수동
  ```console
  mkdir myexpress
  cd myexpress
  npm init
  npm install express --save

  node app.js
  ```

* express-generator (myexpress/)
  ```console
  npm install -g express generator
  express myexpress
  cd myexpress
  npm install

  DEBUG=myexpress:* npm start
  ```

#### 기본적인 라우팅, POST 메소드를 이용한 로그인 폼 (loginapp/)

###### app.js

```javascript
...
var bodyParser = require('body-parser');
...

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
```

* 크롬 개발자 도구 Network 탭에서 패킷을 확인할 수 있다.
  ```
  ▼ General
    Request URL: https://.../login
    Request Method: POST
    Status Code: 200 OK

  ...

  ▼ Form Data
    id: tester
    pw: 1234
  ```


## [Socket.io](https://socket.io)

* 실시간, 양방향, 이벤트 기반 엔진
* 모든 플랫폼, 브라우저, 디바이스에서 동작하며 안정성과 속도에 중점을 두었다.

###### server.js

```javascript
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
```

###### index.html

```html
<script src="/socket.io/socket.io.js"></script>
<script>
  var socket = io.connect('https://levnode.run.goorm.io');
  // news 이벤트 핸들러
  socket.on('news', function (data) {
    // 클라이언트 측 콘솔에 로깅
    console.log(data);
    // 클라이언트에서 socket으로 my other event 이벤트 emit
    socket.emit('my other event', { my: 'data'});
  });
</script>
```


## [Heroku](https://heroku.com)에 Deploy

[Getting Started on Heroku with Node.js](https://devcenter.heroku.com/articles/getting-started-with-nodejs)

1. `sudo snap install heroku --classic`
  * snap 설치가 안 된 경우 `sudo apt update`, `sudo apt install snapd`
  * 안되면 `wget https://toolbelt.heroku.com/install-ubuntu.sh | sh`, `chmod +x install_ubuntu.sh`, sh 파일 실행

1. `heroku login` : heroku 계정으로 로그인

1. `git clone https://github.com/heroku/node-js-getting-started.git`

1. 경로에서 `heroku create`

1. `git push heroku master` : 디플로이

1. `heroku ps:scale web=1` : 프로세스 스케일을 웹 1개로 설정

1. `heroku open` : 페이지 열기

1. `npm install`

1. `heroku local web -p 3000` : 로컬 3000번 포트에서 웹 앱 실행


* `heroku logs --tail` : 실시간 로그 확인


## [MariaDB](https://mariadb.com)

1. `apt install mariadb-server`

1. `mysql`로 확인
  * `ERROR 2002 (HY000)`가 발생하는 경우 `/etc/init.d/mysql start`로 실행

#### mysql 작업

1. utf-8 인코딩을 위해 `/etc/mysql/my.cnf`에 다음 추가
  ```
  [client]
  default-character-set = utf8

  [mysql]
  default-character-set = utf8

  [mysqld]
  collation-server = utf8_unicode_ci
  init-connect = "SET NAMES utf8"
  character-set-server = utf8
  ```

1. `service mysql restart`로 mysql 재시작

1. `mysql -p`로 콘솔 접속
  * `SET PASSWORD FOR 'root'@'localhost' = PASSWORD('newpass');`로 비밀번호 변경 가능

1. mysql 콘솔에서 `javatest` 데이터베이스와 `testdata` 테이블 생성하고 레코드 추가
  ```sql
  create database javatest;
  use javatest;
  create table testdata (
    id int not null auto_increment primary key,
    foo varchar(25),
    bar int);
  insert into testdata values(null, 'hello', 12345);
  ```

#### [node-mysql](https://github.com/mysqljs/mysql)

* `npm install --save mysql`

###### mymariadb/mysql-query.js

```javascript
var mysql      = require('mysql');
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'levdev',
  database : 'javatest'
});

connection.connect();

// 쿼리 테이블명, 필드명에 억음 부호(`; grave accent)를 사용할 수 있음
connection.query('SELECT * FROM `testdata` WHERE `foo` = ?', ['hello'], function (error, results, fields) {
  // error will be an Error if one occurred during the query
  if (error) {
    throw error;
  }
  // results will contain the results of the query
  console.log(results[0].bar);
  // fields will contain information about the returned results fields (if any)
  for (var f in fields) {
    console.log(fields[f].name);
  }
});

connection.end();
```

#### node-bbs/

* `git clone https://github.com/kenu/node-bbs.git`

* name과 message 필드는 반드시 입력하도록 설정
  ```javascript
  // 저장하기
  function save(message, res) {
      var resultObj = {};
      // name이나 message 필드가 입력되지 않은 경우
      if (message.name === '' || message.message === '') {
          resultObj.success = false;
      }
      else {
          var query = connection.query('INSERT INTO board SET ?', message, function (err, result) {
          var resultObj = {};
          if (err) {
              resultObj.success = false;
              console.log(err);
          } else {
              resultObj.success = true;
              resultObj.id = result.insertId;
          }
      });
      res.send(JSON.stringify(resultObj));
      console.log(query.sql);
      }
  }
  ```


## [MongoDB](https://www.mongodb.com)

* [NoSQL](https://ko.wikipedia.org/wiki/NoSQL) 데이터베이스
* JSON과 유사한 '도큐먼트'(≒레코드)를 컬렉션(≒테이블)에 저장하는 DB 시스템


1. [설치](https://docs.mongodb.com/master/tutorial/install-mongodb-on-ubuntu/)

1. `mkdir /data`, `mkdir /data/db`로 DB 폴더 생성

1. `mongod`로 서버 실행

1. `mongo`로 클라이언트 실행

1. 클라이언트에서 `use test`로 `test` DB 선택 (없으면 자동으로 만들어짐)


* `show dbs`로 DB 목록 확인
* `show collections`로 컬렉션 목록 확인


* `db.dropDatabase()`로 현재 DB 삭제
* `db.inventory.drop()`으로 `inventory` 컬렉션 삭제

#### CRUD

* Insert
  ```js  
  db.inventory.insertOne(
   { item: "canvas", qty: 100, tags: ["cotton"], size: { h: 28, w: 35.5, uom: "cm" } }
  )
  
  db.inventory.insertMany([
   { item: "journal", qty: 25, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
   { item: "notebook", qty: 50, size: { h: 8.5, w: 11, uom: "in" }, status: "A" },
   { item: "paper", qty: 100, size: { h: 8.5, w: 11, uom: "in" }, status: "D" },
   { item: "planner", qty: 75, size: { h: 22.85, w: 30, uom: "cm" }, status: "D" },
   { item: "postcard", qty: 45, size: { h: 10, w: 15.25, uom: "cm" }, status: "A" }
  ]);
  ```

* Query
  ```js
  // SELECT * FROM inventory
  db.inventory.find( {} )
  
  // SELECT * FROM inventory LIMIT 3
  db.inventory.find( {} ).limit(3)
  
  // SELECT item FROM inventory
  db.inventory.find( {}, {"item": 1} )
  // SELECT item, qty FROM inventory
  db.inventory.find( {}, {"size": 0} )
  
  // SELECT * FROM inventory WHERE status = "D"
  db.inventory.find( { status: "D" } )
  
  // SELECT * FROM inventory WHERE status in ("A", "D")
  db.inventory.find( { status: { $in: [ "A", "D" ] } } )
  
  // SELECT * FROM inventory WHERE status = "A" OR qty < 30
  db.inventory.find( { $or: [ { status: "A" }, { qty: { $lt: 30 } } ] } )
  ```

* Update
  ```js
  // 조건에 맞는 첫 도큐먼트 수정
  db.inventory.updateOne(
   { item: "paper" },
   {
     $set: { "size.uom": "cm", status: "P" },
     $currentDate: { lastModified: true }
   }
  )
  
  // 조건에 맞는 모든 도큐먼트 수정
  db.inventory.updateMany(
   { "qty": { $lt: 50 } },
   {
     $set: { "size.uom": "in", status: "P" },
     $currentDate: { lastModified: true }
   }
  )
  
  // 조건에 맞는 첫 도큐먼트 덮어쓰기
  db.inventory.replaceOne(
   { item: "paper" },
   { item: "paper", instock: [ { warehouse: "A", qty: 60 }, { warehouse: "B", qty: 40 } ] }
  )
  ```

* Remove
  ```js
  // 조건에 맞는 첫 도큐먼트 삭제
  db.inventory.deleteOne( { status: "D" } )
  
  // 조건에 맞는 모든 도큐먼트 삭제
  db.inventory.deleteMany({ status : "A" })
  ```

#### mymongodb/

* `npm install --save mongodb`

###### mongoInsert.js

```js
var insertDocuments = function(db, callback) {
  // Get the documents collection
  // db is the connected client; should call db()
  var collection = db.db().collection('documents');
  // Insert some documents
  collection.insertMany([
    {a : 1}, {a : 2}, {a : 3}
  ], function(err, result) {
    assert.equal(err, null);
    // assert that 3 documents are inserted
    assert.equal(3, result.result.n);  // { ok: 1, n: 3 }
    assert.equal(3, result.ops.length);  // [ { a: 1, _id: ...}, {...}, {...} ]
    console.log(result.result);
    console.log(result.ops);
    console.log("Inserted 3 documents into the documents collection");
    callback(result);
  });
};

var MongoClient = require('mongodb').MongoClient,
    assert = require('assert');
 
// Connection URL
// > use mymongodb
var url = 'mongodb://localhost:27017/mymongodb';
// Use connect method to connect to the server
// current URL string parser is deprecated; use the new parser
MongoClient.connect(url, { useNewUrlParser: true }, function(err, db) {
  assert.equal(null, err);
  console.log("Connected correctly to the server");
  
  insertDocuments(db, function() {
    db.close();
  });
});

```


## 테스트 프레임워크

#### [QUnit](https://qunitjs.com)

1. `npm install -g qunit`

1. `test` 폴더 아래 js 파일 생성
  ```js
  QUnit.test( "hello test", function( assert ) {
    assert.ok( 1 == "1", "Hello!" );
  });

  QUnit.test( "maybe-failed test", function( assert ) {
    assert.ok( 1 === "1", "Can I pass?" );
  });
  ```

1. 터미널에서 `qunit` 실행
  ```console
  TAP version 13
  ok 1 hello test
  not ok 2 failed test
    ---
    message: "Can I pass?"
    severity: failed
    actual: false
    expected: true
    stack     at Object.<anonymous> (/...)
    ...
  1..2
  # pass 1
  # skip 0
  # todo 0
  # fail 1
  ```

## 클러스터링

#### [PM2](http://pm2.keymetrics.io)

* `npm install -g pm2`


* `pm2 start app.js -i N` : `N`개의 워커를 시작한다. (0이면 CPU 코어 개수만큼)
* `pm2 scale app +N` : `N`개의 워커를 추가로 시작한다.
* `pm2 stop all` : 모든 워커를 정지한다.
* `pm2 delete all` : 모든 워커를 삭제한다.
* `pm2 ls` : 워커들의 상태를 보여준다.
* `pm2 reload app` : 워커를 차례로 재시작한다.
  * 서버를 셧다운하지 않고도 변경 사항을 반영할 수 있다.
