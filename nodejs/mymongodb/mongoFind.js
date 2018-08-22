var findDocuments = function(db, callback) {
  // Get the documents collection
  // db is the connected client; should call db()
  var collection = db.db().collection('documents');
  // Find some documents
  collection.find({}).toArray(function(err, docs) {
    assert.equal(err, null);
    assert.equal(3, docs.length);
    console.log("Found the following documents");
    console.dir(docs);
    callback(docs);
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
  
  findDocuments(db, function() {
    db.close();
  });
});
