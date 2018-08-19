var removeDocument = function(db, callback) {
  // Get the documents collection
  // db is the connected client; should call db()
  var collection = db.db().collection('documents');
  // Remove a document where a : 3
  collection.removeOne({ a : 3 }, function(err, result) {
    assert.equal(err, null);
    assert.equal(1, result.result.n);
    console.log("Removed the document with the field a : 3");
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
  
  removeDocument(db, function() {
    db.close();
  });
});
