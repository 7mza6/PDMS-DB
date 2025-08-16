package Componentes.DeliveryStaff;

import Componentes.Customers.CustomerCRUDS;
import Componentes.Order.Order;
import Componentes.Packages.PackageCRUDS;
import Componentes.Users.User;
import Componentes.Users.UserCRUDS;
import java.sql.*;
import java.util.ArrayList;
import raven.toast.Notifications;

import static PDMS.application.Application.connn;
import static com.mysql.cj.conf.PropertyKey.USER;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.jdesktop.swingx.JXLoginPane.SaveMode.PASSWORD;

public class DeliveryCRUDS {

    private static final Connection CONN = connn();

    public static ArrayList<DeliveryStaff> readDeliveryStaff() {
        ArrayList<DeliveryStaff> deliveryList = new ArrayList<>();
        String sql = "SELECT d.*, u.firstName, u.lastName, u.dateOfBirth, u.gender, u.address, u.celPhone, u.telPhone, u.email, u.username, u.password, u.role FROM DeliveryStaff d JOIN Users u ON d.id = u.id";
        try (Statement stmt = CONN.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DeliveryStaff staff = new DeliveryStaff(
                        rs.getInt("vehicleId"),
                        rs.getInt("routeId"),
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
                deliveryList.add(staff);
            }
        } catch (SQLException e) {
            System.out.println("Error reading delivery staff: " + e.getMessage());
        }
        return deliveryList;
    }

public static void addDeliveryStaff(DeliveryStaff newDeliveryStaff) {
    if (!UserCRUDS.isDuplicateUser(newDeliveryStaff.getId())) {
        User newUser = new User(
            newDeliveryStaff.getId(),
            newDeliveryStaff.getFirstName(),
            newDeliveryStaff.getLastName(),
            newDeliveryStaff.getDateOfBirth(),
            newDeliveryStaff.getGender(),
            newDeliveryStaff.getAddress(),
            newDeliveryStaff.getCelPhone(),
            newDeliveryStaff.getTelPhone(),
            newDeliveryStaff.getEmail(),
            newDeliveryStaff.getUsername(),
            newDeliveryStaff.getPassword(),
            newDeliveryStaff.getRole()
        );
        UserCRUDS.addUser(newUser);
    }

    String sql = "INSERT INTO DeliveryStaff (id, vehicleId, routeId) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = CONN.prepareStatement(sql)) {

        stmt.setInt(1, newDeliveryStaff.getId());

        if (newDeliveryStaff.getVehichleId() == -1) {
            stmt.setNull(2, Types.INTEGER);
        } else {
            stmt.setInt(2, newDeliveryStaff.getVehichleId());
        }

        if (newDeliveryStaff.getRoatId() == -1) {
            stmt.setNull(3, Types.INTEGER);
        } else {
            stmt.setInt(3, newDeliveryStaff.getRoatId());
        }

        stmt.executeUpdate();
        System.out.println("Delivery Staff added successfully!");

    } catch (SQLException ex) {
        Logger.getLogger(DeliveryCRUDS.class.getName()).log(Level.SEVERE, "Error adding delivery staff", ex);
    }
}



    public static void updateDeliveryStaffById(int id, DeliveryStaff updatedDeliveryStaff) {
        String sql = "UPDATE DeliveryStaff SET vehicleId = ?, routeId = ? WHERE id = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, updatedDeliveryStaff.getVehichleId());
            stmt.setInt(2, updatedDeliveryStaff.getRoatId());
            stmt.setInt(3, id);
            stmt.executeUpdate();

            User updatedUser = new User(updatedDeliveryStaff.getId(), updatedDeliveryStaff.getFirstName(),
                    updatedDeliveryStaff.getLastName(), updatedDeliveryStaff.getDateOfBirth(),
                    updatedDeliveryStaff.getGender(), updatedDeliveryStaff.getAddress(),
                    updatedDeliveryStaff.getCelPhone(), updatedDeliveryStaff.getTelPhone(),
                    updatedDeliveryStaff.getEmail(), updatedDeliveryStaff.getUsername(),
                    updatedDeliveryStaff.getPassword(), updatedDeliveryStaff.getRole());
            UserCRUDS.updateUserById(updatedDeliveryStaff.getId(), updatedUser);

            System.out.println("Delivery staff updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating delivery staff: " + e.getMessage());
        }
    }

    public static DeliveryStaff getDeliveryStaffById(int id) {
        String sql = "SELECT d.*, u.firstName, u.lastName, u.dateOfBirth, u.gender, u.address, u.celPhone, u.telPhone, u.email, u.username, u.password, u.role FROM DeliveryStaff d JOIN Users u ON d.id = u.id WHERE d.id = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DeliveryStaff(
                            rs.getInt("vehicleId"),
                            rs.getInt("routeId"),
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
            System.out.println("Error retrieving delivery staff: " + e.getMessage());
        }
        return null;
    }

    public static void deleteDeliveryStaffById(int id) {
        String sql = "DELETE FROM DeliveryStaff WHERE id = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Delivery Staff with ID " + id + " deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting delivery staff: " + e.getMessage());
        }
    }

    public static boolean isOrderAssigned(int orderId) {
        // This functionality should be handled via order-delivery relationship in DB
        return false;
    }

    public static void updateOrderDetails(int orderId, String status, String orderDate, String deliveryDate) {
        String sql = "UPDATE Orders SET Status = ?, OrderDate = ?, DeliveryDate = ? WHERE OrderID = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, orderDate);
            stmt.setString(3, deliveryDate);
            stmt.setInt(4, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Error updating order: " + e.getMessage());
        }
    }

    public static DeliveryStaff getStaffByOrderId(int orderId) {
        // This should be implemented based on how orders are linked to delivery staff in the DB
        return null;
    }
    
    
public static void addOrderToAssignedList(int deliveryStaffID,int orderID) {
    String checkSql = "SELECT * FROM AssignedOrders WHERE OrderID = ? AND DeliveryStaffID = ?";
    String insertSql = "INSERT INTO AssignedOrders (OrderID, DeliveryStaffID, AssignedDate) VALUES (?, ?, ?)";

    try (PreparedStatement checkStmt = CONN.prepareStatement(checkSql)) {
        checkStmt.setInt(1, orderID);
        checkStmt.setInt(2, deliveryStaffID);
        ResultSet rs = checkStmt.executeQuery();
        if (rs.next()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "This order is already assigned to this delivery staff.");
            return;
        }
    } catch (SQLException e) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Error checking assignment: " + e.getMessage());
        return;
    }

    try (PreparedStatement insertStmt = CONN.prepareStatement(insertSql)) {
        
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String orderDate = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
        LocalDate orderLocalDate = null;
        if (orderDate != null && !orderDate.trim().isEmpty() && !orderDate.equals("---")) {
            orderLocalDate = LocalDate.parse(orderDate.replace('/', '-'), inputFormatter);
        }
        
        insertStmt.setInt(1, orderID);
        insertStmt.setInt(2, deliveryStaffID);
        insertStmt.setDate(3, new java.sql.Date(System.currentTimeMillis())); 
        insertStmt.executeUpdate();

        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Order assigned successfully.");
    } catch (SQLException e) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Error assigning order: " + e.getMessage());
    }
}

public static ArrayList<Object[]> getAssignedOrders(int deliveryStaffID) {
    ArrayList<Object[]> assignedOrders = new ArrayList<>();

    String sql = "SELECT o.PackageID, o.Status, ao.AssignedDate " +
                 "FROM Orders o " +
                 "JOIN AssignedOrders ao ON o.OrderID = ao.OrderID " +
                 "WHERE ao.DeliveryStaffID = ?";

    try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
                stmt.setInt(1, deliveryStaffID);
        ResultSet rs = stmt.executeQuery();  
        while (rs.next()) {
            long trackingNumber = rs.getLong("PackageID");
            String status = rs.getString("Status");

            String expectedDelivery = "Pending";
            Date assignedDate = rs.getDate("AssignedDate");

            if (assignedDate != null) {
                LocalDate expectedDate = assignedDate.toLocalDate().plusDays(3);
                expectedDelivery = expectedDate.toString();
            }

            assignedOrders.add(new Object[]{trackingNumber, status, expectedDelivery});
        }

   }catch (SQLException e) {
        e.printStackTrace();
    }

    return assignedOrders;
}



public static ArrayList<Order> getAssignedOrdersByStaffId(int deliveryStaffID) {
    ArrayList<Order> assignedOrders = new ArrayList<>();

    String sql = "SELECT o.*" +
                 "FROM Orders o " +
                 "JOIN AssignedOrders ao ON o.OrderID = ao.OrderID " +
                 "WHERE ao.DeliveryStaffID = ?";

    try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
        stmt.setInt(1, deliveryStaffID);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Order order = new Order(
                rs.getInt("OrderID"),
                CustomerCRUDS.ReadOne(rs.getInt("CustomerID")),
                PackageCRUDS.ReadOne(rs.getLong("PackageID")),
                rs.getString("Status"),
                rs.getString("OrderDate"),
                rs.getFloat("TotalCost")
            );
            order.setDeleveryDate(rs.getString("DeliveryDate")); // optional if needed
            assignedOrders.add(order);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return assignedOrders;
}


}
