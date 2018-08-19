var MongoClient = require('mongodb').MongoClient,
    assert = require('assert');
 
// Connection URL
var url = 'mongodb://localhost:27017/mymongodb';
// Use connect method to connect to the server
// current URL string parser is deprecated, so use the new parser
MongoClient.connect(url, { useNewUrlParser: true }, function(err, db) {
  assert.equal(null, err);
  console.log("Connected correctly to the server");
  
  db.close();
});
