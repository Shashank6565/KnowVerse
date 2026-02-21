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

            int choice;

            do {
                System.out.println("\n====== TEACHER MANAGEMENT MENU ======");
                System.out.println("1. Add Teacher");
                System.out.println("2. Delete Teacher (by ID)");
                System.out.println("3. Edit Teacher (by ID)");
                System.out.println("4. Search Teacher by ID");
                System.out.println("5. Search Teacher by Name");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    // 1️ ADD TEACHER
                    case 1:
                        System.out.print("Enter Teacher ID: ");
                        String addId = sc.nextLine();
                        System.out.print("Enter Name: ");
                        String addName = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String addEmail = sc.nextLine();

                        String insertQuery =
                                "INSERT INTO teachers_data (ID, Name, Email) VALUES (?, ?, ?)";
                        PreparedStatement psInsert =
                                connection.prepareStatement(insertQuery);
                        psInsert.setString(1, addId);
                        psInsert.setString(2, addName);
                        psInsert.setString(3, addEmail);

                        psInsert.executeUpdate();
                        System.out.println("Teacher added successfully");
                        psInsert.close();
                        break;

                    // 2️ DELETE TEACHER
                    case 2:
                        System.out.print("Enter Teacher ID to delete: ");
                        String delId = sc.nextLine();

                        String deleteQuery =
                                "DELETE FROM teachers_data WHERE ID = ?";
                        PreparedStatement psDelete =
                                connection.prepareStatement(deleteQuery);
                        psDelete.setString(1, delId);

                        int rowsDeleted = psDelete.executeUpdate();
                        System.out.println(
                                rowsDeleted > 0
                                        ? "Teacher deleted"
                                        : "Teacher ID not found"
                        );
                        psDelete.close();
                        break;

                    // 3️ EDIT TEACHER
                    case 3:
                        System.out.print("Enter Teacher ID to edit: ");
                        String editId = sc.nextLine();
                        System.out.print("Enter New Name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter New Email: ");
                        String newEmail = sc.nextLine();

                        String updateQuery =
                                "UPDATE teachers_data SET Name = ?, Email = ? WHERE ID = ?";
                        PreparedStatement psUpdate =
                                connection.prepareStatement(updateQuery);
                        psUpdate.setString(1, newName);
                        psUpdate.setString(2, newEmail);
                        psUpdate.setString(3, editId);

                        int rowsUpdated = psUpdate.executeUpdate();
                        System.out.println(
                                rowsUpdated > 0
                                        ? "Teacher updated"
                                        : "Teacher ID not found"
                        );
                        psUpdate.close();
                        break;

                    // 4️ SEARCH BY ID
                    case 4:
                        System.out.print("Enter Teacher ID: ");
                        String searchId = sc.nextLine();

                        String searchIdQuery =
                                "SELECT * FROM teachers_data WHERE ID = ?";
                        PreparedStatement psId =
                                connection.prepareStatement(searchIdQuery);
                        psId.setString(1, searchId);

                        ResultSet rsId = psId.executeQuery();
                        if (rsId.next()) {
                            System.out.println("Name  : " + rsId.getString("Name"));
                            System.out.println("Email : " + rsId.getString("Email"));
                            System.out.println("ID    : " + rsId.getString("ID"));
                        } else {
                            System.out.println("Teacher not found");
                        }
                        rsId.close();
                        psId.close();
                        break;

                    // 5️ SEARCH BY NAME (YOUR ORIGINAL FEATURE)
                    case 5:
                        System.out.print("Enter teacher name: ");
                        String str = sc.nextLine();

                        String query =
                                "SELECT * FROM teachers_data WHERE Name LIKE ?";
                        PreparedStatement ps =
                                connection.prepareStatement(query);
                        ps.setString(1, "%" + str + "%");

                        ResultSet resultSet = ps.executeQuery();
                        while (resultSet.next()) {
                            System.out.println("Name  : " + resultSet.getString("Name"));
                            System.out.println("Email : " + resultSet.getString("Email"));
                            System.out.println("ID    : " + resultSet.getString("ID"));
                            System.out.println("------------------------------");
                        }
                        resultSet.close();
                        ps.close();
                        break;

                    case 0:
                        System.out.println("Exiting program...");
                        break;

                    default:
                        System.out.println("Invalid choice");
                }

            } while (choice != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
