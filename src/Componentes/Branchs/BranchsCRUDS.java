package Componentes.Branchs;

import Componentes.Cities.CitiesCRUDS;
import Componentes.Cities.CitiesClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class BranchsCRUDS {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3300/PDMS";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static ArrayList<BranchClass> Read() {
        ArrayList<BranchClass> branchList = new ArrayList<>();
        String sql = "SELECT * FROM Branches";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String location = rs.getString("Location");
                String city = CitiesCRUDS.Search(location);
                LocationData locationData = new LocationData(city, "");
                BranchClass branch = new BranchClass(
                        rs.getInt("BranchID"),
                        rs.getString("BranchName"),
                        locationData
                );
                branchList.add(branch);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BranchsCRUDS.class.getName()).log(Level.SEVERE, "Error reading branches", ex);
        }
        return branchList;
    }

    public static void Create(String[] data) {
        String sql = "INSERT INTO Branches (BranchID, BranchName, Location) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(data[0]));
            pstmt.setString(2, data[1]);
            pstmt.setString(3, CitiesCRUDS.Search(data[2]));
            pstmt.executeUpdate();
            System.out.println("Branch created successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(BranchsCRUDS.class.getName()).log(Level.SEVERE, "Error creating branch", ex);
        }
    }

    public static ArrayList<BranchClass> Search(String cityName) {
        ArrayList<BranchClass> result = new ArrayList<>();
        String sql = "SELECT * FROM Branches WHERE Location = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, CitiesCRUDS.Search(cityName));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String location = rs.getString("Location");
                String city = CitiesCRUDS.Search(location);
                LocationData locationData = new LocationData(city, "");
                BranchClass branch = new BranchClass(
                        rs.getInt("BranchID"),
                        rs.getString("BranchName"),
                        locationData
                );
                result.add(branch);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BranchsCRUDS.class.getName()).log(Level.SEVERE, "Error searching branches", ex);
        }
        return result;
    }

    public static boolean Update(int branchID, String[] data) {
        String sql = "UPDATE Branches SET BranchName = ?, Location = ? WHERE BranchID = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, data[0]);
            pstmt.setString(2, CitiesCRUDS.Search(data[1]));
            pstmt.setInt(3, branchID);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Branch updated successfully.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Branch not found", "Branch Update", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BranchsCRUDS.class.getName()).log(Level.SEVERE, "Error updating branch", ex);
            return false;
        }
    }

    public static boolean Delete(int branchID) {
        String sql = "DELETE FROM Branches WHERE BranchID = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branchID);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Branch deleted successfully!", "Branch Deletion", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Branch not found!", "Branch Deletion", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BranchsCRUDS.class.getName()).log(Level.SEVERE, "Error deleting branch", ex);
            return false;
        }
    }

    public static String getBranchNameById(int branchId) {
        String branchName = "";
        String sql = "SELECT BranchName FROM Branches WHERE BranchID = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branchId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                branchName = rs.getString("BranchName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BranchsCRUDS.class.getName()).log(Level.SEVERE, "Error retrieving branch name", ex);
        }
        return branchName;
    }
}