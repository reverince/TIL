var insertDocuments = function(db, callback) {
  // Get the documents collection
  // db is the connected client; should call db()
  var collection = db.db().collection('documents');
  // Insert some documents
  collection.insertMany([
    {a : 1}, {a : 2}, {a : 3}
  ], function(err, result) {
    assert.equal(err, null);
    assert.equal(3, result.result.n);
    assert.equal(3, result.ops.length);
    console.log("Inserted 3 documents into the document collection");
    callback(result);
  });
};

var MongoClient = require('mongodb').MongoClient,
    assert = require('assert');
 
// Connection URL
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
