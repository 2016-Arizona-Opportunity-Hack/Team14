package Volunteer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utility.EmailMessageAdapter;
import utility.TextMessageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikoo28 on 10/1/16.
 */
public class VolunteerModule extends Application {

  TextArea broadCastMessageTextArea;

  public void start(Stage primaryStage) throws Exception {

    BorderPane volunteerApplicationPane = new BorderPane();

    volunteerApplicationPane.setTop(broadcastTitleAndTextBox());
    volunteerApplicationPane.setBottom(sendBroadCastsBox());
    volunteerApplicationPane.setLeft(checkListWrapperBox());
    volunteerApplicationPane.setRight(platformsWrapperGridBox());

    primaryStage.setTitle("MILK VOLUNTEER SYSTEM");
    Scene scene = new Scene(volunteerApplicationPane, 700, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
    volunteerApplicationPane.requestFocus();
  }

  private GridPane platformsWrapperGridBox() {
    GridPane platformsWrapperGridPane = new GridPane();

    platformsWrapperGridPane.setHgap(10);
    platformsWrapperGridPane.setVgap(10);
    platformsWrapperGridPane.setPadding(new Insets(0, 10, 0, 10));
    platformsWrapperGridPane.setPrefWidth(400);
    platformsWrapperGridPane.setAlignment(Pos.CENTER);

    // Category in column 1, row 1
    CheckBox neighborsBox = new CheckBox("Neighbors");
    platformsWrapperGridPane.add(neighborsBox, 0, 0);

    CheckBox servicesBox = new CheckBox("Services");
    platformsWrapperGridPane.add(servicesBox, 0, 1);

    CheckBox mentorsBox = new CheckBox("Mentors");
    platformsWrapperGridPane.add(mentorsBox, 1, 0);

    CheckBox otherBox = new CheckBox("Others");
    platformsWrapperGridPane.add(otherBox, 1, 1);

    return platformsWrapperGridPane;
  }

  private ScrollPane checkListWrapperBox() {

    VBox checkListWrapperBox = new VBox();
    checkListWrapperBox.setPadding(new Insets(10));
    checkListWrapperBox.setSpacing(5);

    List<CheckBox> checkBoxList = new ArrayList<CheckBox>();

    CheckBox cb1 = new CheckBox("First");
    CheckBox cb2 = new CheckBox("Second");
    CheckBox cb3 = new CheckBox("Third");
    cb1.setSelected(true);
    cb3.setSelected(true);

    checkBoxList.add(cb1);
    checkBoxList.add(cb2);
    checkBoxList.add(cb3);
    checkBoxList.add(new CheckBox("Fourth"));
    checkBoxList.add(new CheckBox("Fourth"));

    checkListWrapperBox.getChildren().addAll(checkBoxList);

    ScrollPane scrollPane = new ScrollPane(checkListWrapperBox);
    scrollPane.setPrefWidth(300);
    scrollPane.setFitToHeight(true);
    scrollPane.setStyle("-fx-background-color:transparent;");

    return scrollPane;
  }

  private HBox sendBroadCastsBox() {

    HBox sendBroadCastBox = new HBox();
    sendBroadCastBox.setAlignment(Pos.CENTER);
    sendBroadCastBox.setPadding(new Insets(20));
    sendBroadCastBox.setSpacing(10);

    Button submitButton = new Button("SUBMIT");
    submitButton.setPrefSize(100, 20);
    submitButton.setOnAction(new EventHandler<ActionEvent>() {

      public void handle(ActionEvent event) {
        System.out.println("SUBMITTED!!");

        if (broadCastMessageTextArea.getText() != "") {
          // Create Text Message

          String message = broadCastMessageTextArea.getText();
          sendEmails(message);
          sendTextMessages(message);
          broadCastMessageTextArea.clear();
        }
      }
    });

    sendBroadCastBox.getChildren().add(submitButton);

    return sendBroadCastBox;
  }

  private void sendTextMessages(String message) {

    ArrayList<String> toNums = new ArrayList<String>();
    toNums.add("14802269800");
    String from = "12015834652";
    TextMessageAdapter text = new TextMessageAdapter(toNums, from, message);
    text.sendText();
  }

  private void sendEmails(String message) {

    // Create Email Message
    String senderEmail = "milkmoneyone@gmail.com";
    String senderPassword = "milkmoney1";
    String subject = "WHATS UP!";
    ArrayList<String> toEmails = new ArrayList<String>();
    toEmails.add("calebaripley@gmail.com");
    EmailMessageAdapter email = new EmailMessageAdapter(senderEmail, senderPassword, toEmails, subject, message);
    email.sendEmail();

  }

  public HBox broadcastTitleAndTextBox() {

    VBox bannerVerticalWrapper = new VBox();
    bannerVerticalWrapper.setPadding(new Insets(2));
    bannerVerticalWrapper.setSpacing(10);

    Text broadcastMessageTitle = new Text("ENTER BROADCAST MESSAGE");
    broadcastMessageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    broadcastMessageTitle.setFill(Color.WHITE);

    broadCastMessageTextArea = new TextArea();
    broadCastMessageTextArea.setPrefSize(800, 50);
    bannerVerticalWrapper.getChildren().addAll(broadcastMessageTitle, broadCastMessageTextArea);

    HBox broadcastTitleAndMessageWrapper = new HBox();
    broadcastTitleAndMessageWrapper.setPadding(new Insets(5, 5, 5, 5));
    broadcastTitleAndMessageWrapper.setSpacing(3);
    broadcastTitleAndMessageWrapper.setStyle("-fx-background-color: #B34EB3;");

    broadcastTitleAndMessageWrapper.getChildren().addAll(bannerVerticalWrapper);

    return broadcastTitleAndMessageWrapper;
  }
}
