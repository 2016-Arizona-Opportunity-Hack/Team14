package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Sample way to create a database connection
public class DatabaseConnection {

  private static ArrayList<String> volunteerNameString;
  private static ArrayList<String> donorNameString;
  private static  ArrayList<String> donorEmailList;
  private static  ArrayList<String> volunteerEmailList;
  private static  ArrayList<String> volunteerPhoneList;
  private static  ArrayList<String> donorPhoneList;

  public static void init() throws SQLException, ClassNotFoundException {
    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

    Connection conn = DriverManager.getConnection(
            "jdbc:ucanaccess://C:\\Users\\Anshuman\\Desktop\\db.accdb");
    Statement s = conn.createStatement();

		/*Query for selecting Id*/
    ResultSet rsDonorsId = s.executeQuery("SELECT [Id] FROM [Donors] where [Donor] is [TRUE]");
    ResultSet rsVolunteersId = s.executeQuery("SELECT [Id] FROM [Donors] where [Volunteer] is [TRUE]");

    donorNameString = new ArrayList<String>();
    volunteerNameString = new ArrayList<String>();
    volunteerEmailList = new ArrayList<String>();
    donorEmailList = new ArrayList<String>();
    volunteerPhoneList = new ArrayList<String>();
    donorPhoneList = new ArrayList<String>();

    while (rsDonorsId.next()) {

      String temp = rsDonorsId.getString(1);
      int i = Integer.parseInt(temp);
      ResultSet rsDonorsName = s.executeQuery("SELECT [FirstName] FROM [Donors] where [Id]=" + i + " ");

      while (rsDonorsName.next()) {
        donorNameString.add(rsDonorsName.getString(1));
      }
    }

    while (rsVolunteersId.next()) {

      String temp2 = rsVolunteersId.getString(1);
      int i2 = Integer.parseInt(temp2);
      ResultSet rsVolunteersName = s.executeQuery("SELECT [FirstName] FROM [Donors] where [Id]=" + i2 + " ");

      while (rsVolunteersName.next()) {
        volunteerNameString.add(rsVolunteersName.getString(1));
      }
    }

    //		/*Query for selecting Phone number matching to name[Id]*/

		/**/

    ResultSet rsDonorsId2 = s.executeQuery("SELECT [Id] FROM [Donors] where [Donor] is [TRUE]");
    ResultSet rsVolunteersId2 = s.executeQuery("SELECT [Id] FROM [Donors] where [Volunteer] is [TRUE]");

    while (rsDonorsId2.next()) {

      String temp3 = rsDonorsId2.getString(1);
//      System.out.println(temp3);
      int i3 = Integer.parseInt(temp3);
      ResultSet rsDonorPhoneMatch = s.executeQuery("SELECT [MobilePhone] FROM [Donors] where ID=" + i3 + "");
      ResultSet rsDonorMailMatch = s.executeQuery("SELECT [EmailAddress] FROM [Donors] where ID=" + i3 + "");
      while (rsDonorPhoneMatch.next()) {
        donorPhoneList.add(rsDonorPhoneMatch.getString(1));

      }
      while (rsDonorMailMatch.next()) {

        donorEmailList.add(rsDonorMailMatch.getString(1));

      }
    }


    while (rsVolunteersId2.next()) {

      String temp4 = rsVolunteersId2.getString(1);
      int i4 = Integer.parseInt(temp4);
      ResultSet rsVolunteersPhoneMatch = s.executeQuery("SELECT [MobilePhone] FROM [Donors] where ID=" + i4 + "");
      ResultSet rsVolunteersMailMatch = s.executeQuery("SELECT [EmailAddress] FROM [Donors] where ID=" + i4 + "");

      while (rsVolunteersPhoneMatch.next()) {
        volunteerPhoneList.add(rsVolunteersPhoneMatch.getString(1));

      }
      while (rsVolunteersMailMatch.next()) {
        volunteerEmailList.add(rsVolunteersMailMatch.getString(1));

      }

    }





  }

  public static ArrayList<String> getVolunteerName() throws SQLException, ClassNotFoundException {
    init();
    return volunteerNameString;
  }

  public static ArrayList<String> getDonorName() {
    return donorNameString;
  }

  public static ArrayList<String> donorPhoneList() {
    return donorPhoneList;
  }
  public static ArrayList<String> volunteerEmailList() {
    return volunteerEmailList;
  }
  public static ArrayList<String> volunteerPhoneList() {
    return volunteerPhoneList;
  }
  public static ArrayList<String> donorEmailList() {
    return donorEmailList;
  }

}