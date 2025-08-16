package Componentes.Users;

import Componentes.Staff.Staff;
import Componentes.Staff.StaffCRUDS;
import Componentes.DeliveryStaff.DeliveryStaff;
import Componentes.DeliveryStaff.DeliveryCRUDS;
import static Componentes.Users.PasswordHasher.hashPassword;

import java.sql.*;
import java.util.ArrayList;

import static PDMS.application.Application.connn;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserCRUDS {

    private static final Connection CONN = connn();

    public static ArrayList<User> readUsers() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Statement stmt = CONN.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("dateOfBirth"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("celPhone"),
                        rs.getString("telPhone"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error reading users: " + e.getMessage());
        }
        return userList;
    }

    public static void addUser(User newUser) {
    String sql = "INSERT INTO Users (id, firstName, lastName, dateOfBirth, gender, address, celPhone, telPhone, email, username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
        newUser.setId(generateNextId());
        stmt.setInt(1, newUser.getId());
        stmt.setString(2, newUser.getFirstName());
        stmt.setString(3, newUser.getLastName());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(newUser.getDateOfBirth(), formatter);
        
        stmt.setDate(4, java.sql.Date.valueOf(localDate));
        stmt.setString(5, newUser.getGender());
        stmt.setString(6, newUser.getAddress());
        stmt.setString(7, newUser.getCelPhone());
        stmt.setString(8, newUser.getTelPhone());
        stmt.setString(9, newUser.getEmail());
        stmt.setString(10, newUser.getUsername());
        stmt.setString(11, hashPassword(newUser.getPassword()));
        stmt.setString(12, newUser.getRole());
        stmt.executeUpdate();
        
        
        if ("Staff".equalsIgnoreCase(newUser.getRole()) && StaffCRUDS.getStaffById(newUser.getId()) == null) {
            Staff staff = new Staff(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getDateOfBirth(), newUser.getGender(), newUser.getAddress(), newUser.getCelPhone(), newUser.getTelPhone(), newUser.getEmail(), newUser.getUsername(), hashPassword(newUser.getPassword()), newUser.getRole(),-1);
            StaffCRUDS.addStaff(staff);
        } else if ("Delevery Staff".equalsIgnoreCase(newUser.getRole()) && DeliveryCRUDS.getDeliveryStaffById(newUser.getId()) == null) {
            DeliveryStaff deliveryStaff = new DeliveryStaff(-1,-1,newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getDateOfBirth(), newUser.getGender(), newUser.getAddress(), newUser.getCelPhone(), newUser.getTelPhone(), newUser.getEmail(), newUser.getUsername(), hashPassword(newUser.getPassword()), newUser.getRole());
            DeliveryCRUDS.addDeliveryStaff(deliveryStaff);
        }
        
        
        System.out.println("User added successfully!");
    } catch (SQLException | DateTimeParseException e) {
        System.out.println("Error adding user: " + e.getMessage());
    }
}

public static void updateUserById(int id, User newUser) {
    String getOldRoleSql = "SELECT role FROM Users WHERE id = ?";
    String updateSql = "UPDATE Users SET firstName = ?, lastName = ?, dateOfBirth = ?, gender = ?, address = ?, celPhone = ?, telPhone = ?, email = ?, username = ?, password = ?, role = ? WHERE id = ?";

    try (PreparedStatement getOldRoleStmt = CONN.prepareStatement(getOldRoleSql)) {
        getOldRoleStmt.setInt(1, id);
        ResultSet rs = getOldRoleStmt.executeQuery();

        String oldRole = null;
        if (rs.next()) {
            oldRole = rs.getString("role");
        }

        try (PreparedStatement stmt = CONN.prepareStatement(updateSql)) {
            stmt.setString(1, newUser.getFirstName());
            stmt.setString(2, newUser.getLastName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(newUser.getDateOfBirth(), formatter);
            stmt.setDate(3, java.sql.Date.valueOf(localDate));
            stmt.setString(4, newUser.getGender());
            stmt.setString(5, newUser.getAddress());
            stmt.setString(6, newUser.getCelPhone());
            stmt.setString(7, newUser.getTelPhone());
            stmt.setString(8, newUser.getEmail());
            stmt.setString(9, newUser.getUsername());
            stmt.setString(10, hashPassword(newUser.getPassword()));
            stmt.setString(11, newUser.getRole());
            stmt.setInt(12, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("User with ID " + id + " updated successfully.");

                // Handle role change
                if (oldRole != null) {
                    // Remove from old role table
                    if (oldRole.equalsIgnoreCase("Staff")) {
                        StaffCRUDS.deleteStaffById(id);
                    } else if (oldRole.equalsIgnoreCase("Delivery Staff")) {
                        DeliveryCRUDS.deleteDeliveryStaffById(id);
                    }

                    // Add to new role table
                    if (newUser.getRole().equalsIgnoreCase("Staff")) {
                        Staff staff = new Staff(
                            newUser.getId(), newUser.getFirstName(), newUser.getLastName(),
                            newUser.getDateOfBirth(), newUser.getGender(), newUser.getAddress(),
                            newUser.getCelPhone(), newUser.getTelPhone(), newUser.getEmail(),
                            newUser.getUsername(), hashPassword(newUser.getPassword()), newUser.getRole(), -1
                        );
                        StaffCRUDS.addStaff(staff);

                    } else if (newUser.getRole().equalsIgnoreCase("Delivery Staff")) {
                                        System.out.println("User with ID " + id + " updated successfully.");

                         DeliveryStaff deliveryStaff = new DeliveryStaff(-1,-1,newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getDateOfBirth(), newUser.getGender(), newUser.getAddress(), newUser.getCelPhone(), newUser.getTelPhone(), newUser.getEmail(), newUser.getUsername(), hashPassword(newUser.getPassword()), newUser.getRole());
                         DeliveryCRUDS.addDeliveryStaff(deliveryStaff);
                    }
                }

            } else {
                System.out.println("User with ID " + id + " not found.");
            }

        }
    } catch (SQLException | DateTimeParseException e) {
        System.out.println("Error updating user: " + e.getMessage());
    }
}



    public static User getUserById(int id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("dateOfBirth"),
                            rs.getString("gender"),
                            rs.getString("address"),
                            rs.getString("celPhone"),
                            rs.getString("telPhone"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }
        return null;
    }

    public static String getRoleByName(String username) {
        String sql = "SELECT role FROM Users WHERE username = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving role: " + e.getMessage());
        }
        return null;
    }

    public static boolean isDuplicateUserName(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE LOWER(username) = LOWER(?)";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking duplicate username: " + e.getMessage());
        }
        return false;
    }

    public static boolean isDuplicateUser(int id) {
        String sql = "SELECT COUNT(*) FROM Users WHERE id = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking duplicate user ID: " + e.getMessage());
        }
        return false;
    }

    public static void deleteUserById(int id) {
        User userToDelete = getUserById(id);
        if (userToDelete != null) {
            String sql = "DELETE FROM Users WHERE id = ?";
            try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();

                if ("Staff".equalsIgnoreCase(userToDelete.getRole())) {
                    StaffCRUDS.deleteStaffById(id);
                } else if ("DeliveryStaff".equalsIgnoreCase(userToDelete.getRole())) {
                    DeliveryCRUDS.deleteDeliveryStaffById(id);
                }

                System.out.println("User with ID " + id + " deleted successfully.");
            } catch (SQLException e) {
                System.out.println("Error deleting user: " + e.getMessage());
            }
        } else {
            System.out.println("User with ID " + id + " not found.");
        }
    }

    private static int generateNextId() {
        String sql = "SELECT MAX(id) FROM Users";
        try (Statement stmt = CONN.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            System.out.println("Error generating next ID: " + e.getMessage());
        }
        return 1;
    }
    
    
    
 

}