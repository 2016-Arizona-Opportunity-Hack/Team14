package Volunteer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utility.DatabaseConnection;
import utility.EmailMessageAdapter;
import utility.TextMessageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikoo28 on 10/1/16.
 */
public class VolunteerModule extends Application {

  TextArea broadCastMessageTextArea;
  TextField emailAddressTextField;
  PasswordField emailPasswordTextField;
  TextField subjectField;
  TextField fromField;

  public void start(Stage primaryStage) throws Exception {

    BorderPane volunteerApplicationPane = new BorderPane();

    ArrayList<String> volunteerNameList = DatabaseConnection.getVolunteerName();
    ArrayList<String> volunteerEmailList = DatabaseConnection.volunteerEmailList();
    ArrayList<String> volunteerPhoneList = DatabaseConnection.volunteerPhoneList();
    ArrayList<String> donorEmailList = DatabaseConnection.donorEmailList();
    ArrayList<String> donorPhoneList = DatabaseConnection.donorPhoneList();

    volunteerApplicationPane.setTop(broadcastTitleAndTextBox());
    volunteerApplicationPane.setBottom(sendBroadCastsBox(volunteerEmailList, volunteerPhoneList));
    volunteerApplicationPane.setLeft(checkListWrapperBox(volunteerNameList));
    volunteerApplicationPane.setRight(groupsWrapperGridBox());

    primaryStage.setTitle("MILK VOLUNTEER SYSTEM");
    Scene scene = new Scene(volunteerApplicationPane, 700, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
    volunteerApplicationPane.requestFocus();
  }

  private VBox groupsWrapperGridBox() {

    VBox emailMessageGridBox = new VBox();
    emailMessageGridBox.setPrefWidth(400);

    VBox subjectVBox = new VBox();
    HBox emailHBox = new HBox();
    emailHBox.setPrefWidth(400);
    emailHBox.setPadding(new Insets(10));
    emailHBox.setSpacing(5);
    Label subjectLabel = new Label("SUBJECT: ");
    subjectField = new TextField();
    subjectField.setPrefWidth(268);
    subjectField.setPromptText("Enter subject here");
    emailHBox.getChildren().addAll(subjectLabel, subjectField);

    HBox emailAndPassHBox = new HBox();
    emailAndPassHBox.setPrefWidth(400);
    emailAndPassHBox.setPadding(new Insets(10));
    emailAndPassHBox.setSpacing(5);
    emailAddressTextField = new TextField();
    emailAddressTextField.setPromptText("Enter email address");
    emailPasswordTextField = new PasswordField();
    emailPasswordTextField.setPromptText("Enter Password");
    emailAndPassHBox.getChildren().addAll(emailAddressTextField, emailPasswordTextField);

    subjectVBox.getChildren().addAll(emailHBox, emailAndPassHBox);

    HBox textMessageHBox = new HBox();
    textMessageHBox.setPrefWidth(400);
    textMessageHBox.setPadding(new Insets(10));
    textMessageHBox.setSpacing(5);
    Label fromLabel = new Label("PHONE NUMBER: ");
    fromField = new TextField();
    fromField.setPromptText("Enter number here");
    fromField.setText("12015834652");
    textMessageHBox.getChildren().addAll(fromLabel, fromField);

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

    CheckBox allBox = new CheckBox("All Volunteers");
    platformsWrapperGridPane.add(allBox, 0, 2);

    emailMessageGridBox.getChildren().addAll(subjectVBox, textMessageHBox, platformsWrapperGridPane);

    return emailMessageGridBox;
  }

  private ScrollPane checkListWrapperBox(ArrayList<String> volunteerNameList) {

    VBox checkListWrapperBox = new VBox();
    checkListWrapperBox.setPadding(new Insets(10));
    checkListWrapperBox.setSpacing(5);

    List<CheckBox> checkBoxList = new ArrayList<CheckBox>();

    for (int i = 0; i < volunteerNameList.size(); i++) {
      CheckBox newCb = new CheckBox(volunteerNameList.get(i));
      newCb.setSelected(true);
      checkBoxList.add(newCb);
    }

    checkListWrapperBox.getChildren().addAll(checkBoxList);

    ScrollPane scrollPane = new ScrollPane(checkListWrapperBox);
    scrollPane.setPrefWidth(300);
    scrollPane.setFitToHeight(true);
    scrollPane.setStyle("-fx-background-color:transparent;");

    return scrollPane;
  }

  private HBox sendBroadCastsBox(final ArrayList<String> volunteerEmailList, final ArrayList<String> volunteerPhoneList) {

    HBox sendBroadCastBox = new HBox();
    sendBroadCastBox.setAlignment(Pos.CENTER);
    sendBroadCastBox.setPadding(new Insets(20));
    sendBroadCastBox.setSpacing(10);

    Button submitButton = new Button("SEND");
    submitButton.setPrefSize(100, 20);
    submitButton.setOnAction(new EventHandler<ActionEvent>() {

      public void handle(ActionEvent event) {
//        System.out.println("SUBMITTED!!");

        if (broadCastMessageTextArea.getText() != "") {
          // Create Text Message

          String message = broadCastMessageTextArea.getText();
          String emailAddress = emailAddressTextField.getText();
          String emailPassword = emailPasswordTextField.getText();
          String subject = subjectField.getText();
          String from = fromField.getText();
          sendEmails(emailAddress, emailPassword, subject, message, volunteerEmailList);
          sendTextMessages(from, subject, message, volunteerPhoneList);
          broadCastMessageTextArea.clear();

          // Pop up to confirm message was sent

        }
      }
    });

    sendBroadCastBox.getChildren().add(submitButton);

    return sendBroadCastBox;
  }

  private void sendTextMessages(String from, String subject, String message, ArrayList<String> volunteerPhoneList) {

    ArrayList<String> toNums = new ArrayList<String>();
    toNums.addAll(volunteerPhoneList);
//    String from = "12015834652";
    TextMessageAdapter text = new TextMessageAdapter(toNums, from, subject + ": " + message);
    text.sendText();
  }

  private void sendEmails(String emailAddress, String pass, String subject, String message, ArrayList<String> volunteerEmailList) {

    // Create Email Message
//    String senderEmail = "milkmoneyone@gmail.com";
//    String senderPassword = "milkmoney1";
    ArrayList<String> toEmails = new ArrayList<String>();
    toEmails.addAll(volunteerEmailList);
    EmailMessageAdapter email = new EmailMessageAdapter(emailAddress, pass, toEmails, subject, message);
    email.sendEmail();

  }

  public HBox broadcastTitleAndTextBox() {

    VBox bannerVerticalWrapper = new VBox();
    bannerVerticalWrapper.setPadding(new Insets(2));
    bannerVerticalWrapper.setSpacing(10);

    Text broadcastMessageTitle = new Text("ENTER YOUR MESSAGE");
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
