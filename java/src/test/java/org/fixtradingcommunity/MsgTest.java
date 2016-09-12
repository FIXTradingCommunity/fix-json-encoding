package org.fixtradingcommunity;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import com.google.gson.*;

public class MsgTest {
  static class Header {
    String BeginString;
    String MsgType;
    String MsgSeqNum;
    String SenderCompID;
    String TargetCompID;
    String SendingTime;
  }

  static class Trailer {
  }

  static class NoMDEntries {
    String MDEntryType;
    String MDEntryPx;
    String MDEntrySize;
    String MDEntryDate;
    String MDEntryTime;
  }

  static class MarketDataSnapshotFullRefresh {
    static class Body {
      String SecurityIDSource;
      String SecurityID;
      String MDReqID;
      NoMDEntries[] NoMDEntries;
    }

    Header Header;
    Body Body;
    Trailer Trailer;
  }

  private final static String data = String.join("\n",
    "{",
      "\"Header\": {",
        "\"BeginString\": \"FIXT.1.1\",",
        "\"MsgType\": \"W\",",
        "\"MsgSeqNum\": \"4567\",",
        "\"SenderCompID\": \"SENDER\",",
        "\"TargetCompID\": \"TARGET\",",
        "\"SendingTime\": \"20160802-21:14:38.717\"",
      "},",
      "\"Body\": {",
        "\"SecurityIDSource\": \"8\",",
        "\"SecurityID\": \"ESU6\",",
        "\"MDReqID\": \"789\",",
        "\"NoMDEntries\": [",
          "{ \"MDEntryType\": \"0\", \"MDEntryPx\": \"2179.75\", \"MDEntrySize\": \"175\", \"MDEntryDate\": \"20160812\", \"MDEntryTime\": \"21:14:38.688\"},",
          "{ \"MDEntryType\": \"1\", \"MDEntryPx\": \"2180.25\", \"MDEntrySize\": \"125\", \"MDEntryDate\": \"20160812\", \"MDEntryTime\": \"21:14:38.688\"}",
        "]",
      "},",
      "\"Trailer\": {",
      "}",
    "}"
  );

  @Test
  public void testJson() {
    // When the JSON is parsed
    MarketDataSnapshotFullRefresh msg = new Gson().fromJson(data, MarketDataSnapshotFullRefresh.class);

    // Then the header fields should be
    assertEquals("FIXT.1.1", msg.Header.BeginString);
    assertEquals("W", msg.Header.MsgType);
    assertEquals("4567", msg.Header.MsgSeqNum);
    assertEquals("SENDER", msg.Header.SenderCompID);
    assertEquals("TARGET", msg.Header.TargetCompID);
    assertEquals("20160802-21:14:38.717", msg.Header.SendingTime);

    // And the body fields should be
    assertEquals("8", msg.Body.SecurityIDSource);
    assertEquals("ESU6", msg.Body.SecurityID);
    assertEquals("789", msg.Body.MDReqID);

    // And the NoMDEntries repeating group should contain two entries
    assertEquals(2, msg.Body.NoMDEntries.length);

    // And the first entry should be
    assertEquals("0", msg.Body.NoMDEntries[0].MDEntryType);
    assertEquals("2179.75", msg.Body.NoMDEntries[0].MDEntryPx);
    assertEquals("175", msg.Body.NoMDEntries[0].MDEntrySize);
    assertEquals("20160812", msg.Body.NoMDEntries[0].MDEntryDate);
    assertEquals("21:14:38.688", msg.Body.NoMDEntries[0].MDEntryTime);

    // And the second entry should be
    assertEquals("1", msg.Body.NoMDEntries[1].MDEntryType);
    assertEquals("2180.25", msg.Body.NoMDEntries[1].MDEntryPx);
    assertEquals("125", msg.Body.NoMDEntries[1].MDEntrySize);
    assertEquals("20160812", msg.Body.NoMDEntries[1].MDEntryDate);
    assertEquals("21:14:38.688", msg.Body.NoMDEntries[1].MDEntryTime);
  }
}
