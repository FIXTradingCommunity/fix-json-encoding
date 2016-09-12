const assert = require('assert');

const data = '{' +
  '  "BeginString": "FIXT.1.1",' +
  '  "MsgType": "W",' +
  '  "MsgSeqNum": "4567",' +
  '  "SenderCompID": "SENDER",' +
  '  "TargetCompID": "TARGET",' +
  '  "SendingTime": "20160802-21:14:38.717",' +
  '  "SecurityIDSource": "8",' +
  '  "SecurityID": "ESU6",' +
  '  "MDReqID": "789",' +
  '  "NoMDEntries": [' +
  '    { "MDEntryType": "0", "MDEntryPx": "2179.75", "MDEntrySize": "175", "MDEntryDate": "20160812", "MDEntryTime": "21:14:38.688"},' +
  '    { "MDEntryType": "1", "MDEntryPx": "2180.25", "MDEntrySize": "125", "MDEntryDate": "20160812", "MDEntryTime": "21:14:38.688"}' +
  '  ]' +
  '}';

// When the JSON is parsed
var msg = JSON.parse(data);

// Then the header fields should be
assert.equal(msg.BeginString, "FIXT.1.1");
assert.equal(msg.MsgType, "W");
assert.equal(msg.MsgSeqNum, "4567");
assert.equal(msg.SenderCompID, "SENDER");
assert.equal(msg.TargetCompID, "TARGET");
assert.equal(msg.SendingTime, "20160802-21:14:38.717");

// And the body fields should be
assert.equal(msg.SecurityIDSource, "8");
assert.equal(msg.SecurityID, "ESU6");
assert.equal(msg.MDReqID, "789");

// And the NoMDEntries repeating group should contain two entries
assert.equal(msg.NoMDEntries.length, 2);

// And the first entry should be
assert.equal(msg.NoMDEntries[0].MDEntryType, "0");
assert.equal(msg.NoMDEntries[0].MDEntryPx, "2179.75");
assert.equal(msg.NoMDEntries[0].MDEntrySize, "175");
assert.equal(msg.NoMDEntries[0].MDEntryDate, "20160812");
assert.equal(msg.NoMDEntries[0].MDEntryTime, "21:14:38.688");

// And the second entry should be
assert.equal(msg.NoMDEntries[1].MDEntryType, "1");
assert.equal(msg.NoMDEntries[1].MDEntryPx, "2180.25");
assert.equal(msg.NoMDEntries[1].MDEntrySize, "125");
assert.equal(msg.NoMDEntries[1].MDEntryDate, "20160812");
assert.equal(msg.NoMDEntries[1].MDEntryTime, "21:14:38.688");
