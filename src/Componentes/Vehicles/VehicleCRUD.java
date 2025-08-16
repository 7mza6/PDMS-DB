package Componentes.Vehicles;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleCRUD {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3300/PDMS";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static ArrayList<Vehicle> readVehicles() {
        ArrayList<Vehicle> vehicleList = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                    rs.getInt("ID"),
                    rs.getString("Brand"),
                    rs.getString("Model"),
                    rs.getString("Type"),
                    rs.getString("LicensePlate"),
                    rs.getString("ChassisNumber"),
                    rs.getFloat("Capacity"),
                    rs.getString("Status"),
                    rs.getString("LastServiceDate")
                );
                vehicleList.add(vehicle);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VehicleCRUD.class.getName()).log(Level.SEVERE, "Error reading vehicles", ex);
        }
        return vehicleList;
    }

    public static void createVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO Vehicles (id, Brand, Model, Type, LicensePlate, ChassisNumber, Capacity, Status, LastServiceDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicle.getId());
            pstmt.setString(2, vehicle.getBrand());
            pstmt.setString(3, vehicle.getModel());
            pstmt.setString(4, vehicle.getType());
            pstmt.setString(5, vehicle.getLicensePlate());
            pstmt.setString(6, vehicle.getChassisNumber());
            pstmt.setFloat(7, vehicle.getCapacity());
            pstmt.setString(8, vehicle.getStatus());
            pstmt.setString(9, vehicle.getLastServiceDate());
            pstmt.executeUpdate();
            System.out.println("Vehicle created successfully!");
        } catch (SQLException ex) {
            Logger.getLogger(VehicleCRUD.class.getName()).log(Level.SEVERE, "Error creating vehicle", ex);
        }
    }

    public static Vehicle readVehicle(int id) {
        String sql = "SELECT * FROM Vehicles WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Vehicle(
                    rs.getInt("id"),
                    rs.getString("Brand"),
                    rs.getString("Model"),
                    rs.getString("Type"),
                    rs.getString("LicensePlate"),
                    rs.getString("ChassisNumber"),
                    rs.getFloat("Capacity"),
                    rs.getString("Status"),
                    rs.getString("LastServiceDate")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(VehicleCRUD.class.getName()).log(Level.SEVERE, "Error reading vehicle", ex);
        }
        return null;
    }

    public static void updateVehicle(int id, Vehicle updatedVehicle) {
        String sql = "UPDATE Vehicles SET Brand = ?, Model = ?, Type = ?, LicensePlate = ?, ChassisNumber = ?, Capacity = ?, Status = ?, LastServiceDate = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedVehicle.getBrand());
            pstmt.setString(2, updatedVehicle.getModel());
            pstmt.setString(3, updatedVehicle.getType());
            pstmt.setString(4, updatedVehicle.getLicensePlate());
            pstmt.setString(5, updatedVehicle.getChassisNumber());
            pstmt.setFloat(6, updatedVehicle.getCapacity());
            pstmt.setString(7, updatedVehicle.getStatus());
            pstmt.setString(8, updatedVehicle.getLastServiceDate());
            pstmt.setInt(9, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Vehicle updated successfully.");
            } else {
                System.out.println("Vehicle not found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VehicleCRUD.class.getName()).log(Level.SEVERE, "Error updating vehicle", ex);
        }
    }

    public static void deleteVehicle(int id) {
        String sql = "DELETE FROM Vehicles WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Vehicle deleted successfully.");
            } else {
                System.out.println("Vehicle not found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VehicleCRUD.class.getName()).log(Level.SEVERE, "Error deleting vehicle", ex);
        }
    }
}
