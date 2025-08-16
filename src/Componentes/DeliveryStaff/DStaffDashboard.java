package Componentes.DeliveryStaff;

import Componentes.Order.Order;
import Componentes.Order.OrderCRUDS;
import static PDMS.theme.theams.Table_style;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import raven.toast.Notifications;

/**
 *
 * @author Raven
 */
public class DStaffDashboard extends javax.swing.JPanel {

    
    public DStaffDashboard() {
        initComponents();
        ShowInTable();
        
    }
    
public static int ID = -1;
    
    
public void ShowInTable() {
    try {
        DefaultTableModel model = (DefaultTableModel) Orders.getModel();
        model.setRowCount(0); // Clear existing rows
        
        // Retrieve the delivery staff by their ID
        DeliveryStaff deliveryStaff = DeliveryCRUDS.getDeliveryStaffById(ID);
        
        if (deliveryStaff == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Delivery Staff not found.");
            return;
        }

        // Get the assigned orders for the delivery staff
        List<Order> assignedOrders = DeliveryCRUDS.getAssignedOrdersByStaffId(ID);

        
        if (assignedOrders == null || assignedOrders.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "No orders assigned to this delivery staff.");
            return;
        }

        // Iterate through the assigned orders and add them to the table
        for (Order order : assignedOrders) {
            if (order != null) {
                int orderID = order.getOrderID();
                String customerName = order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName();
                String packageDescription = order.getPackagepdms().getContentDescription();
                String orderDate = order.getOrderDate();
                String status = order.getStatus();
                String totalCost = order.getTotalCost() + " NIS";

                // Add the row to the table
                model.addRow(new Object[]{orderID, customerName, packageDescription, orderDate, status, totalCost});
            } else {
                System.out.println("An order assigned to Delivery Staff ID " + ID + " is null.");
            }
        }
        
    } catch (Exception e) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Error showing orders: " + e.getMessage());
    }
    Table_style(Orders);
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        UpdateStatus = new javax.swing.JButton();
        Status = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        Orders = new javax.swing.JTable();

        UpdateStatus.setText("Update Status");
        UpdateStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateStatusActionPerformed(evt);
            }
        });

        Status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pendeng", "In Progress", "Delivered", "" }));

        Orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "OrderID", "Customer Name", "Package Description", "Order Date", "Status", "Total Cost"
            }
        ));
        Orders.setRowHeight(40);
        Orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OrdersMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Orders);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UpdateStatus)
                .addGap(7, 7, 7))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpdateStatus))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void OrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OrdersMouseClicked
 
    }//GEN-LAST:event_OrdersMouseClicked

    private void UpdateStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateStatusActionPerformed
    try {
        int selectedRow = Orders.getSelectedRow(); // Get selected row in the Orders table
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Please select an order to update its status.");
            return;
        }

        // Retrieve the order ID from the selected row
        int orderId = (int) Orders.getValueAt(selectedRow, 0);

        // Get the selected status from the combo box
        String newStatus = (String) Status.getSelectedItem();

        // Retrieve the current order to fetch existing data
        Order order = OrderCRUDS.getById(orderId);
        if (order != null) {
         

            OrderCRUDS.updateStatus(orderId,newStatus );

            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Order status updated successfully.");
            ShowInTable(); // Refresh the table to reflect the updated status
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Order not found.");
        }
    } catch (Exception e) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Error updating order status: " + e.getMessage());
    }
    
            ShowInTable();





    }//GEN-LAST:event_UpdateStatusActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable OT;
    private javax.swing.JTable Orders;
    private javax.swing.JComboBox<String> Status;
    private javax.swing.JButton UpdateStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
