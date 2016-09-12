require 'minitest/autorun'
require 'json'

class TestJson < MiniTest::Unit::TestCase
  def setup
    @data = <<EOF
      {
        "Header": {
          "BeginString": "FIXT.1.1",
          "MsgType": "W",
          "MsgSeqNum": "4567",
          "SenderCompID": "SENDER",
          "TargetCompID": "TARGET",
          "SendingTime": "20160802-21:14:38.717"
        },
        "Body": {
          "SecurityIDSource": "8",
          "SecurityID": "ESU6",
          "MDReqID": "789",
          "NoMDEntries": [
            { "MDEntryType": "0", "MDEntryPx": "2179.75", "MDEntrySize": "175", "MDEntryDate": "20160812", "MDEntryTime": "21:14:38.688"},
            { "MDEntryType": "1", "MDEntryPx": "2180.25", "MDEntrySize": "125", "MDEntryDate": "20160812", "MDEntryTime": "21:14:38.688"}
          ]
        },
        "Trailer": {
        }
      }
EOF
  end

  def test_json
    # When the JSON is parsed
    @msg = JSON.parse(@data, symbolize_names: true)
    
    # Then the header fields should be
    assert_equal "FIXT.1.1", @msg[:Header][:BeginString]
    assert_equal "W", @msg[:Header][:MsgType]
    assert_equal "4567", @msg[:Header][:MsgSeqNum]
    assert_equal "SENDER", @msg[:Header][:SenderCompID]
    assert_equal "TARGET", @msg[:Header][:TargetCompID]
    assert_equal "20160802-21:14:38.717", @msg[:Header][:SendingTime]

    # And the body fields should be
    assert_equal "8", @msg[:Body][:SecurityIDSource]
    assert_equal "ESU6", @msg[:Body][:SecurityID]
    assert_equal "789", @msg[:Body][:MDReqID]

    # And the NoMDEntries repeating group should contain two entries
    assert_equal 2, @msg[:Body][:NoMDEntries].size

    # And the first entry should be
    assert_equal "0", @msg[:Body][:NoMDEntries][0][:MDEntryType]
    assert_equal "2179.75", @msg[:Body][:NoMDEntries][0][:MDEntryPx]
    assert_equal "175", @msg[:Body][:NoMDEntries][0][:MDEntrySize]
    assert_equal "20160812", @msg[:Body][:NoMDEntries][0][:MDEntryDate]
    assert_equal "21:14:38.688", @msg[:Body][:NoMDEntries][0][:MDEntryTime]

    # And the second entry should be
    assert_equal "1", @msg[:Body][:NoMDEntries][1][:MDEntryType]
    assert_equal "2180.25", @msg[:Body][:NoMDEntries][1][:MDEntryPx]
    assert_equal "125", @msg[:Body][:NoMDEntries][1][:MDEntrySize]
    assert_equal "20160812", @msg[:Body][:NoMDEntries][1][:MDEntryDate]
    assert_equal "21:14:38.688", @msg[:Body][:NoMDEntries][1][:MDEntryTime]
  end
end
