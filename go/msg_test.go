package msgtest

import (
	"encoding/json"
	"testing"

	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
)

type NoMDEntries struct {
	MDEntryType string
	MDEntryPx   string
	MDEntrySize string
	MDEntryDate string
	MDEntryTime string
}

type MarketDataSnapshotFullRefresh struct {
	BeginString      string
	MsgType          string
	MsgSeqNum        string
	SenderCompID     string
	TargetCompID     string
	SendingTime      string
	SecurityIDSource string
	SecurityID       string
	MDReqID          string
	NoMDEntries      []NoMDEntries
}

var (
	data = []byte(`{
		"BeginString": "FIXT.1.1",
		"MsgType": "W",
		"MsgSeqNum": "4567",
		"SenderCompID": "SENDER",
		"TargetCompID": "TARGET",
		"SendingTime": "20160802-21:14:38.717",
		"SecurityIDSource": "8",
		"SecurityID": "ESU6",
		"MDReqID": "789",
		"NoMDEntries": [
			{ "MDEntryType": "0", "MDEntryPx": "2179.75", "MDEntrySize": "175", "MDEntryDate": "20160812", "MDEntryTime": "21:14:38.688"},
			{ "MDEntryType": "1", "MDEntryPx": "2180.25", "MDEntrySize": "125", "MDEntryDate": "20160812", "MDEntryTime": "21:14:38.688"}
		]
	}`)
)

func TestJSON(t *testing.T) {
	// When the JSON is parsed
	var msg MarketDataSnapshotFullRefresh
	err := json.Unmarshal(data, &msg)
	require.Nil(t, err)

	// Then the header fields should be
	assert.Equal(t, "FIXT.1.1", msg.BeginString)
	assert.Equal(t, "W", msg.MsgType)
	assert.Equal(t, "4567", msg.MsgSeqNum)
	assert.Equal(t, "SENDER", msg.SenderCompID)
	assert.Equal(t, "TARGET", msg.TargetCompID)
	assert.Equal(t, "20160802-21:14:38.717", msg.SendingTime)

	// And the body fields should be
	assert.Equal(t, "8", msg.SecurityIDSource)
	assert.Equal(t, "ESU6", msg.SecurityID)
	assert.Equal(t, "789", msg.MDReqID)

	// And the NoMDEntries repeating group should contain two entries
	assert.Len(t, msg.NoMDEntries, 2)

	// And the first entry should be
	assert.Equal(t, "0", msg.NoMDEntries[0].MDEntryType)
	assert.Equal(t, "2179.75", msg.NoMDEntries[0].MDEntryPx)
	assert.Equal(t, "175", msg.NoMDEntries[0].MDEntrySize)
	assert.Equal(t, "20160812", msg.NoMDEntries[0].MDEntryDate)
	assert.Equal(t, "21:14:38.688", msg.NoMDEntries[0].MDEntryTime)

	// And the second entry should be
	assert.Equal(t, "1", msg.NoMDEntries[1].MDEntryType)
	assert.Equal(t, "2180.25", msg.NoMDEntries[1].MDEntryPx)
	assert.Equal(t, "125", msg.NoMDEntries[1].MDEntrySize)
	assert.Equal(t, "20160812", msg.NoMDEntries[1].MDEntryDate)
	assert.Equal(t, "21:14:38.688", msg.NoMDEntries[1].MDEntryTime)
}
