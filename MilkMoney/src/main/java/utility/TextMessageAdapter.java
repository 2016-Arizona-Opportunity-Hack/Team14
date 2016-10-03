package utility;

import com.nexmo.messaging.sdk.NexmoSmsClient;
import com.nexmo.messaging.sdk.SmsSubmissionResult;
import com.nexmo.messaging.sdk.messages.TextMessage;

import java.util.ArrayList;

/**
 * Created by calebripley on 10/1/16.
 */
public class TextMessageAdapter {

  private final String API_KEY = "73fcdb4a";
  private final String API_SECRET = "8d753716e2e4089c";

  private String smsFrom = "12015834652";
  private ArrayList<String> smsTo;
  private String smsText;

  // Constructor
  public TextMessageAdapter(ArrayList<String> to, String from, String message) {
    this.smsFrom = from;
    this.smsText = message;
    this.smsTo = to;
  }

  // Send a message
  public void sendText() {

    // Create a client for submitting to Nexmo

    NexmoSmsClient client = null;
    try {
      client = new NexmoSmsClient(API_KEY, API_SECRET);
    } catch (Exception e) {
      System.err.println("Failed to instantiate a Nexmo Client");
      e.printStackTrace();
      throw new RuntimeException("Failed to instantiate a Nexmo Client");
    }

    for (int i = 0; i < smsTo.size(); i++) {

      if (smsTo.get(i) == null || smsTo.get(i).equalsIgnoreCase("null"))
        continue;
      // Create a Text SMS Message request object ...

      TextMessage message = new TextMessage(smsFrom, smsTo.get(i), smsText);

      // Use the Nexmo client to submit the Text Message ...

      SmsSubmissionResult[] results = null;
      try {
        results = client.submitMessageHttps(message);
      } catch (Exception e) {
        System.err.println("Failed to communicate with the Nexmo Client");
        e.printStackTrace();
        throw new RuntimeException("Failed to communicate with the Nexmo Client");
      }

      // Evaluate the results of the submission attempt ...
      System.out.println("... Message submitted in [ " + results.length + " ] parts");
      for (int j = 0; j < results.length; j++) {
        System.out.println("--------- part [ " + (j + 1) + " ] ------------");
        System.out.println("Status [ " + results[j].getStatus() + " ] ...");
        if (results[j].getStatus() == SmsSubmissionResult.STATUS_OK)
          System.out.println("SUCCESS");
        else if (results[j].getTemporaryError())
          System.out.println("TEMPORARY FAILURE - PLEASE RETRY");
        else
          System.out.println("SUBMISSION FAILED!");
        System.out.println("Message-Id [ " + results[j].getMessageId() + " ] ...");
//        System.out.println("Error-Text [ " + results[j].getErrorText() + " ] ...");

        if (results[j].getMessagePrice() != null)
          System.out.println("Message-Price [ " + results[j].getMessagePrice() + " ] ...");
        if (results[j].getRemainingBalance() != null)
          System.out.println("Remaining-Balance [ " + results[j].getRemainingBalance() + " ] ...");
      }

    }
  }

}
