package Componentes.Customers;

import Componentes.Packages.PackageCRUDS;
import Componentes.Packages.Package;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import static PDMS.application.Application.connn;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerCRUDS {

    private static final Connection CONN = connn();

    public static ArrayList<Customer> Read() {
        ArrayList<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customers";
        try (Statement stmt = CONN.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("Phone"),
                        rs.getString("DateOfBirth"),
                        rs.getString("Address")
                );
                list.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerCRUDS.class.getName()).log(Level.SEVERE, "Error reading customers from database", ex);
        }
        return list;
    }

    public static Customer ReadOne(int customerID) {
        String sql = "SELECT * FROM Customers WHERE CustomerID = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, customerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("CustomerID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Gender"),
                            rs.getString("Phone"),
                            rs.getString("DateOfBirth"),
                            rs.getString("Address")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerCRUDS.class.getName()).log(Level.SEVERE, "Error reading one customer", ex);
        }
        return null;
    }

public static void Create(String[] data) {
    if (data[3].length() == 10 && IsInt(data[3])) {
        int newID = generateNextID();
        String sql = "INSERT INTO Customers (CustomerID, FirstName, LastName, Gender, Phone, DateOfBirth, Address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, newID);
            stmt.setString(2, data[0]);
            stmt.setString(3, data[1]);
            stmt.setString(4, data[2]);
            stmt.setString(5, data[3]);

            // تحويل تاريخ الميلاد من نص إلى java.sql.Date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(data[4], formatter);
            stmt.setDate(6, java.sql.Date.valueOf(localDate));

            stmt.setString(7, data[5]);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerCRUDS.class.getName()).log(Level.SEVERE, "Error creating customer", ex);
        }
    } else {
        System.out.println("The phone number is not valid");
    }
}

    public static void Update(int customerID, Customer updatedCustomer) {
        String sql = "UPDATE Customers SET FirstName = ?, LastName = ?, Gender = ?, Phone = ?, DateOfBirth = ?, Address = ? WHERE CustomerID = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setString(1, updatedCustomer.getFirstName());
            stmt.setString(2, updatedCustomer.getLastName());
            stmt.setString(3, updatedCustomer.getGender());
            stmt.setString(4, updatedCustomer.getPhone());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(updatedCustomer.getDateOfBirth(), formatter);
            stmt.setDate(5, java.sql.Date.valueOf(localDate));
            stmt.setString(6, updatedCustomer.getAddress());
            stmt.setInt(7, customerID);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ArrayList<Package> packageList = PackageCRUDS.Read();
                for (Package pkg : packageList) {
                    if (pkg.getCustomer().getCustomerID() == customerID) {
                        pkg.setCustomer(updatedCustomer);
                        PackageCRUDS.Update(pkg);
                    }
                }
                System.out.println("Customer and associated packages updated successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerCRUDS.class.getName()).log(Level.SEVERE, "Error updating customer", ex);
        }
    }

    public static void Remove(int customerID) {
    String deletePackagesSql = "DELETE FROM Orders WHERE CustomerID = ?";
    String deleteCustomerSql = "DELETE FROM Customers WHERE CustomerID = ?";

    try (Connection conn = CONN) {
        conn.setAutoCommit(false); // Start transaction

        // Delete related packages/orders
        try (PreparedStatement packageStmt = conn.prepareStatement(deletePackagesSql)) {
            packageStmt.setInt(1, customerID);
            packageStmt.executeUpdate();
        }

        // Delete the customer
        try (PreparedStatement customerStmt = conn.prepareStatement(deleteCustomerSql)) {
            customerStmt.setInt(1, customerID);
            int rows = customerStmt.executeUpdate();

            if (rows > 0) {
                conn.commit(); // Commit transaction if all successful
                System.out.println("Customer and associated packages removed successfully.");
            } else {
                conn.rollback(); // Rollback if customer not found
                System.out.println("Customer not found.");
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(CustomerCRUDS.class.getName()).log(Level.SEVERE, "Error deleting customer", ex);
    }
}


    public static boolean IsInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Customer SearchByID(int customerID) {
        return ReadOne(customerID);
    }

    public static ArrayList<Customer> Search(String data, JComboBox Type) {
        ArrayList<Customer> CList = Read();
        ArrayList<Customer> result = new ArrayList<>();

        if (CList == null || CList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There is no Users", "No Users", JOptionPane.WARNING_MESSAGE);
            return null;
        } else {
            for (Customer c : CList) {
                if (Type.getSelectedIndex() == 0 && String.valueOf(c.getCustomerID()).contains(data)) {
                    result.add(c);
                } else if (Type.getSelectedIndex() == 1 && c.getFirstName().contains(data)) {
                    result.add(c);
                } else if (Type.getSelectedIndex() == 2 && c.getAddress().contains(data)) {
                    result.add(c);
                }
            }
            return result.isEmpty() ? null : result;
        }
    }

    private static int generateNextID() {
        String sql = "SELECT MAX(CustomerID) FROM Customers";
        try (Statement stmt = CONN.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerCRUDS.class.getName()).log(Level.SEVERE, "Error generating next CustomerID", ex);
        }
        return 1;
    }
}
