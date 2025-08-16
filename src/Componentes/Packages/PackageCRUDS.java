package Componentes.Packages;

import Componentes.Customers.Customer;
import Componentes.Order.Order;
import Componentes.Order.OrderCRUDS;
import Componentes.Branchs.BranchClass;
import Componentes.Branchs.LocationData;
import Componentes.Customers.CustomerCRUDS;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class PackageCRUDS {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3300/PDMS";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static ArrayList<Package> Read() {
        ArrayList<Package> list = new ArrayList<>();
        String sql = "SELECT * FROM Packages";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = CustomerCRUDS.ReadOne(rs.getInt("CustomerID"));
                LocationData location = new LocationData(rs.getString("Branch"), "");
                BranchClass branch = new BranchClass(0, rs.getString("Branch"), location);
                Package pkg = new Package(
                    rs.getLong("PackageID"),
                    rs.getFloat("Weight"),
                    rs.getString("ContentDescription"),
                    rs.getBoolean("Fragile"),
                    rs.getString("ReciverName"),
                    rs.getString("ReciverPhone"),
                    rs.getString("Location"),
                    rs.getString("ReciverAddress"),
                    branch,
                    customer
                );
                list.add(pkg);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageCRUDS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static boolean Create(Package newPackage) {
        if (newPackage.getReciverPhone().length() != 10 || !IsInt(newPackage.getReciverPhone())) {
            JOptionPane.showMessageDialog(null, "Phone number is not valid", "Phone Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO Packages (PackageID, Weight, ContentDescription, Fragile, ReciverName, ReciverPhone, Location, ReciverAddress, Branch, CustomerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, newPackage.getPackageID());
            pstmt.setFloat(2, newPackage.getWeight());
            pstmt.setString(3, newPackage.getContentDescription());
            pstmt.setBoolean(4, newPackage.getFragile());
            pstmt.setString(5, newPackage.getReciverName());
            pstmt.setString(6, newPackage.getReciverPhone());
            pstmt.setString(7, newPackage.getLocation());
            pstmt.setString(8, newPackage.getReciveraddress());
            pstmt.setString(9, newPackage.getBranch().getLocationdata().getCity());
            pstmt.setInt(10, newPackage.getCustomer().getCustomerID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PackageCRUDS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean Update(Package updatedPackage) {
        if (updatedPackage.getReciverPhone().length() != 10 || !IsInt(updatedPackage.getReciverPhone())) {
            return false;
        }

        String sql = "UPDATE Packages SET Weight = ?, ContentDescription = ?, Fragile = ?, ReciverName = ?, ReciverPhone = ?, Location = ?, ReciverAddress = ?, Branch = ?, CustomerID = ? WHERE PackageID = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setFloat(1, updatedPackage.getWeight());
            pstmt.setString(2, updatedPackage.getContentDescription());
            pstmt.setBoolean(3, updatedPackage.getFragile());
            pstmt.setString(4, updatedPackage.getReciverName());
            pstmt.setString(5, updatedPackage.getReciverPhone());
            pstmt.setString(6, updatedPackage.getLocation());
            pstmt.setString(7, updatedPackage.getReciveraddress());
            pstmt.setString(8, updatedPackage.getBranch().getLocationdata().getCity());
            pstmt.setInt(9, updatedPackage.getCustomer().getCustomerID());
            pstmt.setLong(10, updatedPackage.getPackageID());
            pstmt.executeUpdate();

            ArrayList<Order> orders = OrderCRUDS.getOrdersByPackageID(updatedPackage.getPackageID());
            for (Order order : orders) {
                order.setPackagepdms(updatedPackage);
                OrderCRUDS.UpdatePackage(updatedPackage);
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PackageCRUDS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static void Remove(long packageID) {
        String sql = "DELETE FROM Packages WHERE PackageID = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, packageID);
            pstmt.executeUpdate();

            ArrayList<Order> orders = OrderCRUDS.getOrdersByPackageID(packageID);
            for (Order order : orders) {
                OrderCRUDS.Remove(order.getOrderID());
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageCRUDS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Package ReadOne(long packageID) {
        String sql = "SELECT * FROM Packages WHERE PackageID = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, packageID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Customer customer = CustomerCRUDS.ReadOne(rs.getInt("CustomerID"));
                LocationData location = new LocationData(rs.getString("Branch"), "");
                BranchClass branch = new BranchClass(0, rs.getString("Branch"), location);
                return new Package(
                    rs.getLong("PackageID"),
                    rs.getFloat("Weight"),
                    rs.getString("ContentDescription"),
                    rs.getBoolean("Fragile"),
                    rs.getString("ReciverName"),
                    rs.getString("ReciverPhone"),
                    rs.getString("Location"),
                    rs.getString("ReciverAddress"),
                    branch,
                    customer
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageCRUDS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ArrayList<Package> SearchByID(long ID) {
        ArrayList<Package> all = Read();
        ArrayList<Package> result = new ArrayList<>();
        for (Package p : all) {
            if (p.getPackageID() == ID) {
                result.add(p);
            }
        }
        return result;
    }

    public static ArrayList<Package> getPackageByCity(String city) {
        ArrayList<Package> result = new ArrayList<>();
        String sql = "SELECT * FROM Packages WHERE Branch = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Customer customer = CustomerCRUDS.ReadOne(rs.getInt("CustomerID"));
                LocationData location = new LocationData(rs.getString("Branch"), rs.getString("Street"));
                BranchClass branch = new BranchClass(0, rs.getString("Branch"), location);
                Package pkg = new Package(
                    rs.getLong("PackageID"),
                    rs.getFloat("Weight"),
                    rs.getString("ContentDescription"),
                    rs.getBoolean("Fragile"),
                    rs.getString("ReciverName"),
                    rs.getString("ReciverPhone"),
                    rs.getString("Location"),
                    rs.getString("ReciverAddress"),
                    branch,
                    customer
                );
                result.add(pkg);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageCRUDS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static boolean IsInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
