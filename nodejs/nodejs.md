# Node.js

참고 강의 : [국방부오픈소스아카데미](http://osam.oss.kr) APP 개발 과정 by 허광남

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

