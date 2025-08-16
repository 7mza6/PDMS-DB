package Componentes.Order;

import Componentes.Customers.Customer;
import Componentes.Customers.CustomerCRUDS;
import Componentes.DeliveryStaff.DeliveryCRUDS;
import Componentes.DeliveryStaff.DeliveryStaff;
import Componentes.Packages.Package;
import Componentes.Packages.PackageCRUDS;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import raven.toast.Notifications;

public class OrderCRUDS {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3300/PDMS";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static ArrayList<Order> Read() {
        ArrayList<Order> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Orders")) {

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("OrderID"),
                    CustomerCRUDS.ReadOne(rs.getInt("CustomerID")),
                    PackageCRUDS.ReadOne(rs.getLong("PackageID")),
                    rs.getString("Status"),
                    rs.getString("OrderDate"),
                    rs.getFloat("TotalCost")
                );
                order.setDeleveryDate(rs.getString("DeliveryDate"));
                list.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

public static void create(Customer customer, Package packagepdms, String status, String orderDate, String deliveryDate, float totalCost) {
    String sql = "INSERT INTO Orders (CustomerID, PackageID, Status, OrderDate, DeliveryDate, TotalCost) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate orderLocalDate = null;
        LocalDate deliveryLocalDate = null;

        if (orderDate != null && !orderDate.trim().isEmpty() && !orderDate.equals("---")) {
            orderLocalDate = LocalDate.parse(orderDate.replace('/', '-'), inputFormatter);
        }

        if (deliveryDate != null && !deliveryDate.trim().isEmpty() && !deliveryDate.equals("---")) {
            deliveryLocalDate = LocalDate.parse(deliveryDate.replace('/', '-'), inputFormatter);
        }

        pstmt.setInt(1, customer.getCustomerID());
        pstmt.setLong(2, packagepdms.getPackageID());
        pstmt.setString(3, status);
        pstmt.setDate(4, orderLocalDate != null ? java.sql.Date.valueOf(orderLocalDate) : null);
        pstmt.setDate(5, deliveryLocalDate != null ? java.sql.Date.valueOf(deliveryLocalDate) : null);
        pstmt.setFloat(6, totalCost);
        pstmt.executeUpdate();

    } catch (SQLException | DateTimeParseException ex) {
        ex.printStackTrace();
    }
}



    public static void Update(int ID, String[] data) {
        String sql = "UPDATE Orders SET Status = ?, OrderDate = ?, DeliveryDate = ? WHERE OrderID = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate orderLocalDate = null;
        LocalDate deliveryLocalDate = null;
        
        if (data[1] != null && !data[1].trim().equals("") && !data[1].equals("---")) {
        orderLocalDate = LocalDate.parse(data[1].replace('/', '-'), inputFormatter);
        }
        if (data[2] != null && !data[2].trim().equals("") && !data[2].equals("---")) {
            deliveryLocalDate = LocalDate.parse(data[2].replace('/', '-'), inputFormatter);
        }
        

            pstmt.setString(1, data[0]);
            pstmt.setDate(2, orderLocalDate != null ? java.sql.Date.valueOf(orderLocalDate) : null);
            pstmt.setDate(3, deliveryLocalDate != null ? java.sql.Date.valueOf(deliveryLocalDate) : null);
            pstmt.setInt(4, ID);
            pstmt.executeUpdate();

            DeliveryStaff assignedStaff = DeliveryCRUDS.getStaffByOrderId(ID);
            if (assignedStaff != null) {
                DeliveryCRUDS.updateOrderDetails(ID, data[0], data[1], data[2]);
            }

            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Order updated successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Order not found.");
        }
    }

    public static void Remove(int orderId) {
        String sql = "DELETE FROM Orders WHERE OrderID = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<Order> Search(String data, JComboBox Type) {
        ArrayList<Order> result = new ArrayList<>();
        String sql = Type.getSelectedIndex() == 0 ? "SELECT * FROM Orders WHERE Status LIKE ?" : "SELECT * FROM Orders WHERE OrderDate LIKE ?";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + data + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("OrderID"),
                    CustomerCRUDS.ReadOne(rs.getInt("CustomerID")),
                    PackageCRUDS.ReadOne(rs.getLong("PackageID")),
                    rs.getString("Status"),
                    rs.getString("OrderDate"),
                    rs.getFloat("TotalCost")
                );
                order.setDeleveryDate(rs.getString("DeliveryDate"));
                result.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static Order getById(int orderID) {
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Order order = new Order(
                    rs.getInt("OrderID"),
                    CustomerCRUDS.ReadOne(rs.getInt("CustomerID")),
                    PackageCRUDS.ReadOne(rs.getLong("PackageID")),
                    rs.getString("Status"),
                    rs.getString("OrderDate"),
                    rs.getFloat("TotalCost")
                );
                order.setDeleveryDate(rs.getString("DeliveryDate"));
                return order;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Order> getOrdersByCustomerId(int customerId) {
        ArrayList<Order> customerOrders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE CustomerID = ?";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("OrderID"),
                    CustomerCRUDS.ReadOne(rs.getInt("CustomerID")),
                    PackageCRUDS.ReadOne(rs.getLong("PackageID")),
                    rs.getString("Status"),
                    rs.getString("OrderDate"),
                    rs.getFloat("TotalCost")
                );
                order.setDeleveryDate(rs.getString("DeliveryDate"));
                customerOrders.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customerOrders;
    }

    public static ArrayList<Long> getPackageIDsByOrderId(int orderId) {
        ArrayList<Long> packageIDs = new ArrayList<>();
        String sql = "SELECT PackageID FROM Orders WHERE OrderID = ?";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                packageIDs.add(rs.getLong("PackageID"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return packageIDs;
    }

    public static ArrayList<Order> getOrdersByPackageID(long packageID) {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE PackageID = ?";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, packageID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("OrderID"),
                    CustomerCRUDS.ReadOne(rs.getInt("CustomerID")),
                    PackageCRUDS.ReadOne(rs.getLong("PackageID")),
                    rs.getString("Status"),
                    rs.getString("OrderDate"),
                    rs.getFloat("TotalCost")
                );
                order.setDeleveryDate(rs.getString("DeliveryDate"));
                orders.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    public static void UpdatePackage(Package updatedPackage) {
        String sql = "UPDATE Orders SET PackageID = ? WHERE PackageID = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, updatedPackage.getPackageID());
            pstmt.setLong(2, updatedPackage.getPackageID());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public static void updateStatus(int orderId, String status) {
    String sql = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
    try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, status);
        stmt.setInt(2, orderId);
        stmt.executeUpdate();

        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Order status updated successfully.");

    } catch (SQLException e) {
        e.printStackTrace();
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Error updating order status: " + e.getMessage());
    }
}

    
}