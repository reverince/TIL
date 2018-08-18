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