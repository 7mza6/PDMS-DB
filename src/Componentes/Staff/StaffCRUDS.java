package Componentes.Staff;

import Componentes.Users.User;
import Componentes.Users.UserCRUDS;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaffCRUDS {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3300/PDMS";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static ArrayList<Staff> readStaff() {
        ArrayList<Staff> staffList = new ArrayList<>();
        String sql = "SELECT s.id, s.BranchID, u.firstName, u.lastName, u.dateOfBirth, u.gender, u.address, u.celPhone, u.telPhone, u.email, u.username, u.password, u.role FROM Staff s JOIN Users u ON s.id = u.id";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Staff staff = new Staff(
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
                    rs.getString("role"),
                    rs.getInt("BranchID")
                );
                staffList.add(staff);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffCRUDS.class.getName()).log(Level.SEVERE, "Error reading staff", ex);
        }
        return staffList;
    }

public static void addStaff(Staff newStaff) {
    if (!UserCRUDS.isDuplicateUser(newStaff.getId())) {
        User newUser = new User(
            newStaff.getId(),
            newStaff.getFirstName(),
            newStaff.getLastName(),
            newStaff.getDateOfBirth(),
            newStaff.getGender(),
            newStaff.getAddress(),
            newStaff.getCelPhone(),
            newStaff.getTelPhone(),
            newStaff.getEmail(),
            newStaff.getUsername(),
            newStaff.getPassword(),
            newStaff.getRole()
        );
        UserCRUDS.addUser(newUser);
    }

    String sql = "INSERT INTO Staff (id, BranchID) VALUES (?, ?)";
    try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, newStaff.getId());

        if (newStaff.getBranchId() == -1) {
            pstmt.setNull(2, Types.INTEGER);
        } else {
            pstmt.setInt(2, newStaff.getBranchId());
        }

        pstmt.executeUpdate();
        System.out.println("Staff added successfully!");

    } catch (SQLException ex) {
        Logger.getLogger(StaffCRUDS.class.getName()).log(Level.SEVERE, "Error adding staff", ex);
    }
}


    public static void updateStaffById(int id, Staff updatedStaff) {
        User updatedUser = new User(updatedStaff.getId(), updatedStaff.getFirstName(), updatedStaff.getLastName(),
            updatedStaff.getDateOfBirth(), updatedStaff.getGender(), updatedStaff.getAddress(),
            updatedStaff.getCelPhone(), updatedStaff.getTelPhone(), updatedStaff.getEmail(),
            updatedStaff.getUsername(), updatedStaff.getPassword(), updatedStaff.getRole());
        UserCRUDS.updateUserById(id, updatedUser);

        String sql = "UPDATE Staff SET BranchID = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, updatedStaff.getBranchId());
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Staff updated successfully.");
            } else {
                System.out.println("Staff not found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffCRUDS.class.getName()).log(Level.SEVERE, "Error updating staff", ex);
        }
    }

    public static Staff getStaffById(int id) {
        String sql = "SELECT s.id, s.BranchID, u.firstName, u.lastName, u.dateOfBirth, u.gender, u.address, u.celPhone, u.telPhone, u.email, u.username, u.password, u.role FROM Staff s JOIN Users u ON s.id = u.id WHERE s.id = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Staff(
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
                    rs.getString("role"),
                    rs.getInt("BranchID")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffCRUDS.class.getName()).log(Level.SEVERE, "Error reading staff", ex);
        }
        return null;
    }

    public static void deleteStaffById(int id) {
        String sql = "DELETE FROM Staff WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Staff deleted successfully.");
            } else {
                System.out.println("Staff not found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffCRUDS.class.getName()).log(Level.SEVERE, "Error deleting staff", ex);
        }
    }
}
