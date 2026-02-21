package PBL;

import java.sql.*;
import java.util.Scanner;

public class test_class {

    private static final String URL = "jdbc:mysql://localhost:3306/KnowVerse";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "GEUshashanksingh6565";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found");
            return;
        }

        try (
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Scanner sc = new Scanner(System.in);
        ) {

            System.out.print("Enter the name of the teacher you want to search for: ");
            String str = sc.nextLine();

            String query = "SELECT * FROM teachers_data WHERE Name LIKE ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, "%" + str + "%");

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String email = resultSet.getString("Email");
                String id = resultSet.getString("ID");

                System.out.println("Teacher's Name  : " + name);
                System.out.println("Teacher's Email : " + email);
                System.out.println("Teacher's ID    : " + id);
                System.out.println("--------------------------------");
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
