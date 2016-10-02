package utility;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Anshuman on 10/2/2016.
 */
public class Test {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ArrayList<String> volunteerName = DatabaseConnection.getVolunteerName();
        for (String s : volunteerName) {
            System.out.println(s);
        }

        ArrayList<String> emailList = DatabaseConnection.donorEmailList();
        for (String s : emailList) {
            System.out.println(s);
        }


    }

}
