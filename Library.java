/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package library;

/**
 *
 * @author roydo
 */
import java.sql.*;
import java.util.Scanner;

public class Library {

    private static final String URL = "jdbc:sqlite:C:\\Users\\roydo\\OneDrive\\Desktop\\JEAD\\Database\\library.db"; // Update with your database URL
    private static final String USER = "admin"; // Update with your username
    private static final String PASSWORD = "admin"; // Update with your password

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to the database.");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                displayMenu();
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> addBook(connection, scanner);
                    case 2 -> updateBook(connection, scanner);
                    case 3 -> deleteBook(connection, scanner);
                    case 4 -> queryBooks(connection, scanner);
                    case 5 -> addMember(connection, scanner);
                    case 6 -> updateMember(connection, scanner);
                    case 7 -> deleteMember(connection, scanner);
                    case 8 -> queryMembers(connection, scanner);
                    case 9 -> recordBorrowing(connection, scanner);
                    case 10 -> recordReturning(connection, scanner);
                    case 11 -> queryTransactions(connection, scanner);
                    case 12 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    /**
     * Displays the menu options.
     */
    private static void displayMenu() {
        System.out.println("\nLibrary Management System");
        System.out.println("1. Add Book");
        System.out.println("2. Update Book");
        System.out.println("3. Delete Book");
        System.out.println("4. Query Books");
        System.out.println("5. Add Member");
        System.out.println("6. Update Member");
        System.out.println("7. Delete Member");
        System.out.println("8. Query Members");
        System.out.println("9. Record Borrowing");
        System.out.println("10. Record Returning");
        System.out.println("11. Query Transactions");
        System.out.println("12. Exit");
    }

    // Books Management Methods
    private static void addBook(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter book title: ");
        scanner.nextLine(); // Consume newline
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter publisher: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter year published: ");
        int yearPublished = scanner.nextInt();
        System.out.print("Enter ISBN: ");
        scanner.nextLine(); // Consume newline
        String isbn = scanner.nextLine();
        System.out.print("Enter available copies: ");
        int availableCopies = scanner.nextInt();

        String sql = "INSERT INTO Books (title, author, publisher, year_published, isbn, available_copies) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, publisher);
            stmt.setInt(4, yearPublished);
            stmt.setString(5, isbn);
            stmt.setInt(6, availableCopies);
            stmt.executeUpdate();
            System.out.println("Book added successfully!");
        }
    }

    

   private static void updateBook(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter book ID to update: ");
        int bookId = scanner.nextInt();
        System.out.print("Enter new title: ");
        scanner.nextLine(); // Consume newline
        String title = scanner.nextLine();
        System.out.print("Enter new author: ");
        String author = scanner.nextLine();
        System.out.print("Enter new publisher: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter new year published: ");
        int yearPublished = scanner.nextInt();
        System.out.print("Enter new ISBN: ");
        scanner.nextLine(); // Consume newline
        String isbn = scanner.nextLine();
        System.out.print("Enter new available copies: ");
        int availableCopies = scanner.nextInt();

        String sql = "UPDATE Books SET title = ?, author = ?, publisher = ?, year_published = ?, isbn = ?, available_copies = ? WHERE book_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, publisher);
            stmt.setInt(4, yearPublished);
            stmt.setString(5, isbn);
            stmt.setInt(6, availableCopies);
            stmt.setInt(7, bookId);
            stmt.executeUpdate();
            System.out.println("Book updated successfully!");
        }
    }
 private static void deleteBook(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter book ID to delete: ");
        int bookId = scanner.nextInt();

        String sql = "DELETE FROM Books WHERE book_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
            System.out.println("Book deleted successfully!");
        }
    }

     private static void queryBooks(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter search term (title/author/publisher/ISBN): ");
        scanner.nextLine(); // Consume newline
        String searchTerm = scanner.nextLine();

        String sql = "SELECT * FROM Books WHERE title LIKE ? OR author LIKE ? OR publisher LIKE ? OR isbn LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String likeTerm = "%" + searchTerm + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);
            stmt.setString(3, likeTerm);
            stmt.setString(4, likeTerm);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("book_id") + ", Title: " + rs.getString("title") +
                            ", Author: " + rs.getString("author") + ", Publisher: " + rs.getString("publisher") +
                            ", Year Published: " + rs.getInt("year_published") + ", ISBN: " + rs.getString("isbn") +
                            ", Available Copies: " + rs.getInt("available_copies"));
                }
            }
        }
    }
      // Members Management Methods
    private static void addMember(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter member name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter membership date (YYYY-MM-DD): ");
        String membershipDate = scanner.nextLine();

        String sql = "INSERT INTO Members (name, email, phone, membership_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setDate(4, Date.valueOf(membershipDate));
            stmt.executeUpdate();
            System.out.println("Member added successfully!");
        }
    }

    private static void updateMember(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter member ID to update: ");
        int memberId = scanner.nextInt();
        System.out.print("Enter new name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter new membership date (YYYY-MM-DD): ");
        String membershipDate = scanner.nextLine();

        String sql = "UPDATE Members SET name = ?, email = ?, phone = ?, membership_date = ? WHERE member_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setDate(4, Date.valueOf(membershipDate));
            stmt.setInt(5, memberId);
            stmt.executeUpdate();
            System.out.println("Member updated successfully!");
        }
    }


       private static void deleteMember(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter member ID to delete: ");
        int memberId = scanner.nextInt();

        String sql = "DELETE FROM Members WHERE member_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            stmt.executeUpdate();
            System.out.println("Member deleted successfully!");
        }
    }

      private static void queryMembers(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter search term (name/email/phone): ");
        scanner.nextLine(); // Consume newline
        String searchTerm = scanner.nextLine();

        String sql = "SELECT * FROM Members WHERE name LIKE ? OR email LIKE ? OR phone LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String likeTerm = "%" + searchTerm + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);
            stmt.setString(3, likeTerm);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("member_id") + ", Name: " + rs.getString("name") +
                            ", Email: " + rs.getString("email") + ", Phone: " + rs.getString("phone") +
                            ", Membership Date: " + rs.getString("membership_date"));
                }
            }
        }
    }
      // Transactions Management Methods
    private static void recordBorrowing(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter member ID: ");
        int memberId = scanner.nextInt();
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        System.out.print("Enter borrowing date (YYYY-MM-DD): ");
        scanner.nextLine(); // Consume newline
        String date = scanner.nextLine();

        String sql = "INSERT INTO Transactions (member_id, book_id, borrow_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            stmt.setInt(2, bookId);
            stmt.setDate(3, Date.valueOf(date));
            stmt.executeUpdate();
            System.out.println("Borrowing recorded successfully!");
        }
    }

   private static void recordReturning(Connection connection, Scanner scanner) throws SQLException {
    System.out.print("Enter transaction ID to update: ");
    int transactionId = scanner.nextInt();
    System.out.print("Enter return date (YYYY-MM-DD): ");
    scanner.nextLine(); // Consume newline
    String returnDate = scanner.nextLine();

    // Update the transaction with the return date and status
    String sql = "UPDATE Transactions SET return_date = ? WHERE transaction_id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setDate(1, Date.valueOf(returnDate)); // Ensure correct date format
        stmt.setInt(2, transactionId);
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            // Update the available copies of the book
            String updateSql = "UPDATE Books SET available_copies = available_copies + 1 WHERE book_id = (SELECT book_id FROM Transactions WHERE transaction_id = ?)";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                updateStmt.setInt(1, transactionId);
                updateStmt.executeUpdate();
            }
            System.out.println("Return recorded successfully!");
        } else {
            System.out.println("Transaction ID not found or already returned.");
        }
    } catch (SQLException e) {
        System.err.println("Error during return: " + e.getMessage());
    }
}

 private static void queryTransactions(Connection connection, Scanner scanner) throws SQLException {
        String sql = "SELECT * FROM Transactions";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Transaction ID: " + rs.getInt("transaction_id") +
                        ", Book ID: " + rs.getInt("book_id") +
                        ", Member ID: " + rs.getInt("member_id") +
                        ", Borrow Date: " + rs.getString("borrow_date") +
                        ", Return Date: " + rs.getString("return_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}