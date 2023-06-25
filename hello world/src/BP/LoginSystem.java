package BP;

import java.sql.*;
import java.util.Scanner;

public class LoginSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/bp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            createTable(connection); // Create the users table if it doesn't exist

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println(" ");
                System.out.println("Welcome to the Login System!");
                System.out.println("Please choose an option:");
                System.out.println("1. Sign Up");
                System.out.println("2. Log In");
                System.out.println("3. Show Scoreboard");
                System.out.println("4. Exit");

                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (option) {
                    case 1:
                        System.out.print("Enter username: ");
                        String signUpUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String signUpPassword = scanner.nextLine();
                        signUp(signUpUsername, signUpPassword, connection);
                        System.out.println("User created: " + signUpUsername);
                        playMemoryGame();
                        break;
                    case 2:
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        boolean loginSuccess = login(loginUsername, loginPassword, connection);
                        if (loginSuccess) {
                            System.out.println("Login successful: " + loginUsername);
                            playMemoryGame();
                        } else {
                            System.out.println("Invalid username or password");
                        }
                        break;
                    case 3:
                        displayScoreboard(connection);
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            }

            scanner.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) NOT NULL UNIQUE," +
                "password VARCHAR(50) NOT NULL" +
                ")";
        Statement statement = connection.createStatement();
        statement.executeUpdate(createTableQuery);
        statement.close();
    }

    private static void signUp(String username, String password, Connection connection) throws SQLException {
        String insertUserQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertUserQuery);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.executeUpdate();
        statement.close();
        System.out.println("User created: " + username);
    }

    private static boolean login(String username, String password, Connection connection) throws SQLException {
        String selectUserQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(selectUserQuery);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();

        boolean loginSuccess = resultSet.next();

        if (loginSuccess) {
            System.out.println("Login successful: " + username);
        } else {
            System.out.println("Invalid username or password!");
        }

        resultSet.close();
        statement.close();

        return loginSuccess;
    }

    private static void displayScoreboard(Connection connection) throws SQLException {
        String selectScoreboardQuery = "SELECT * FROM users ORDER BY id";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectScoreboardQuery);

        System.out.println("Scoreboard:");
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            System.out.println(username);
        }

        resultSet.close();
        statement.close();
    }

    private static void playMemoryGame() {
        Kaarten.main();
    }
}
