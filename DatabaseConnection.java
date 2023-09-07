package ICS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() {
        try {
            // Connect to the SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:H:\\ics\\ReportSoftware\\sqlite-dll-win64-x64-3430000/mydatabase.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create a DatabaseConnection instance
        DatabaseConnection dbConnection = new DatabaseConnection();

        // Check if the connection is successful
        if (dbConnection.getConnection() != null) {
            System.out.println("Connected to the SQLite database.");

            try {
                // Create a statement for executing SQL queries
                Statement statement = dbConnection.getConnection().createStatement();

                // Example: Create a table
                String createTableSQL = "CREATE TABLE IF NOT EXISTS example_table (id INTEGER PRIMARY KEY, name TEXT);";
                statement.execute(createTableSQL);
                System.out.println("Table created.");

                // Example: Insert data into the table
                String insertDataSQL = "INSERT INTO example_table (name) VALUES ('John');";
                statement.execute(insertDataSQL);
                System.out.println("Data inserted.");

                // Example: Query data from the table
                String queryDataSQL = "SELECT * FROM example_table;";
                var resultSet = statement.executeQuery(queryDataSQL);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    System.out.println("ID: " + id + ", Name: " + name);
                }

                // Close the statement
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Close the database connection when done
            dbConnection.closeConnection();
        } else {
            System.err.println("Failed to connect to the SQLite database.");
        }
    }
}
