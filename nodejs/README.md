# Node.js

참고 강의 : [국방부오픈소스아카데미](http://osam.oss.kr) APP 개발 과정 _by 허광남_

## 특징

* 자바스크립트 기반
* 비동기 프로그래밍
* 멀티플랫폼
* Server-side
* Desktop Application

#### 장점

* [npm](https://www.npmjs.com/)
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

* 패키지 설치
  * 현재 경로에 `node_modules` 디렉토리가 만들어지고 그 안에 설치된다.
  * `-g` 옵션 : (로컬이 아닌) 전역 설치

```console
npm install lodash
```

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

## [Express](http://expressjs.com/)

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

## [Socket.io](https://socket.io/)

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

## MariaDB

* `apt install mariadb-server`
* `mysql`로 확인
  * `ERROR 2002 (HY000)`가 발생하는 경우 `/etc/init.d`에서 `./mysql start` 실행
  * `mysql -uroot -password`로 콘솔 접속
  * `SET PASSWORD FOR 'root'@'localhost' = PASSWORD('newpass');`로 비밀번호 변경 가능
* mysql 콘솔에서 `javatest` 데이터베이스와 `testdata` 테이블 생성하고 레코드 추가

```sql
create database javatest;
use javatest;
create table testdata (
  id int not null auto_increment primary key,
  foo varchar(25),
  bar int);
insert into testdata values(null, 'hello', 12345);
```

* [node-mysql](https://github.com/mysqljs/mysql) 테스트 코드

```javascript
var mysql      = require('mysql');
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'levdev',
  database : 'javatest'
});

connection.connect();

connection.query('SELECT 1 + 1 AS solution', function (error, results, fields) {
  if (error) throw error;
  console.log('The solution is: ', results[0].solution);
});

connection.end();
```
