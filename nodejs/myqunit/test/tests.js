QUnit.test( "hello test", function( assert ) {
  assert.ok( 1 == "1", "Hello!" );
});

QUnit.test( "maybe-failed test", function( assert ) {
  assert.ok( 1 === "1", "Can I pass?" );
});
