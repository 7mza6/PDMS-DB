package Componentes.Order;

import Componentes.Customers.Customer;
import Componentes.Customers.CustomerCRUDS;
import Componentes.DeliveryStaff.DeliveryCRUDS;
import Componentes.DeliveryStaff.DeliveryStaff;
import Componentes.Packages.PackageCRUDS;
import Componentes.Packages.Package;
import Componentes.Route.Route;
import Componentes.Route.RouteCRUD;
import Componentes.Vehicles.VehicleCRUD;
import static PDMS.theme.theams.Table_style;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import raven.cellButton.TableActionCellEditor;
import raven.cellButton.TableActionCellRender;
import raven.cellButton.TableActionEvent;
import raven.toast.Notifications;



public class OrdersScreen extends javax.swing.JPanel {

    char x; //save button 

    public OrdersScreen() {
        initComponents();
        ShowInTable();
        fillComboBox(customer);
        OT.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender(false, true));
        loadTableEvents();
        OT.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(loadTableEvents(), false, true));
        AutoCompleteDecorator.decorate(customer);
    }

    private void fillComboBox(JComboBox<Customer> comboBox) {
        comboBox.removeAllItems(); // Clear the existing items
        ArrayList<Customer> customers = CustomerCRUDS.Read(); // Fetch the list of customers
        if (customers != null && !customers.isEmpty()) {
            for (Customer c : customers) {
                comboBox.addItem(c); // Add the Customer object
            }
        }

    }

    private TableActionEvent loadTableEvents() {
        return new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                onEditEvent(row);
            }

            @Override
            public void onDelete(int row) {
                onDeleteEvent(row);
            }

            @Override
            public void onView(int row) {
                onViewEvent(row);
            }

            @Override
            public void onAssign(int row) {
                onAssignEvent(row);
            }
        };
    }

    private void onAssignEvent(int row) {
        try {
            int orderId = (int) OT.getValueAt(row, 0);
            if (DeliveryCRUDS.isOrderAssigned(orderId)) {
                Notifications.getInstance().show(Notifications.Type.WARNING,"This order is already assigned to a delivery staff.");
                return; 
            }
            ArrayList<Long> packages = OrderCRUDS.getPackageIDsByOrderId(orderId);
            ShowDataInTable(row);
            PackagesD1.setTitle("Assign Order");
            PackagesD1.setBounds(500, 360, 900, 380);
            PackagesD1.setAlwaysOnTop(true);
            PackagesD1.setVisible(true);
        } catch (Exception e) {
            Notifications.getInstance().show(Notifications.Type.ERROR,Notifications.Location.TOP_CENTER,"Error during order assignment: " + e.getMessage());
        }
    }

    private void onViewEvent(int row) {
    }

    public void onDeleteEvent(int row) {
        int id = (int) OT.getValueAt(row, 0); // Assuming the first column contains the Order ID
        int confirmation = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this order?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );
        if (confirmation == JOptionPane.YES_OPTION) {
            OrderCRUDS.Remove(id); // Replace with your delete method
            ShowInTable(); // Method to refresh the orders table
        }
    }

    private void onEditEvent(int row) {
        OrderD.setTitle("Update Order");
        OrderD.setBounds(795, 360, 330, 380);
        showUpdateDialog((int) OT.getValueAt(OT.getSelectedRow(), 0));
        x = 'U';
        OrderD.setVisible(true);
        OrderD.setAlwaysOnTop(true);
        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Updated successfully.");
    }

public void ShowInTable() {
    try {
        String selectedFilter = (String) Filter.getSelectedItem(); // Get the selected filter
        DefaultTableModel model = (DefaultTableModel) OT.getModel();
        model.setRowCount(0);

        for (Order o : OrderCRUDS.Read()) {
            int customerId = o.getCustomer().getCustomerID();
            long packageId = o.getPackagepdms().getPackageID();
            Customer c = CustomerCRUDS.SearchByID(customerId);
            ArrayList<Componentes.Packages.Package> p = PackageCRUDS.SearchByID(packageId);

            boolean isAssigned = DeliveryCRUDS.isOrderAssigned(o.getOrderID()); // Check if the order is assigned

            // Filter logic
            if ("Not Assigned".equals(selectedFilter) && isAssigned) {
                continue; // Skip assigned orders if the filter is "Not Assigned"
            }
            if ("Assigned".equals(selectedFilter) && !isAssigned) {
                continue; // Skip not assigned orders if the filter is "Assigned"
            }

            if (p != null && c != null) {
                for (Componentes.Packages.Package p1 : p) {
                    Object[] row = {
                        o.getOrderID(),
                        c.getFirstName() + " " + c.getLastName(),
                        p1.getContentDescription(),
                        o.getOrderDate(),
                        o.getStatus(),
                        o.getTotalCost() + " NIS"
                    };
                    model.addRow(row);
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "The Order or Package does not exist.");
            }
        }
    } catch (Exception e) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Error updating table: " + e.getMessage());
    }
    Table_style(OT);
}


    public void ShowDataInTableSearch() {
        DefaultTableModel model = (DefaultTableModel) OT.getModel();
        model.setRowCount(0);
        for (Order o : OrderCRUDS.Search(Search.getText(), SearchBy)) {
            int CostumerID = o.getCustomer().getCustomerID();
            long PackageID = o.getPackagepdms().getPackageID();
            Customer c = CustomerCRUDS.SearchByID(CostumerID);
            ArrayList<Componentes.Packages.Package> p = PackageCRUDS.SearchByID(PackageID);
            for (Componentes.Packages.Package p1 : p) {

                if (c == null) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "The Ordder Dose not Exist");

                } else {
                    Object[] row = {o.getOrderID(), c.getFirstName() + " " + c.getLastName(), p1.getContentDescription(), o.getOrderDate(), o.getStatus(), o.getTotalCost() + " NIS"};
                    model.addRow(row);
                }
            }
        }
    }

    public void ShowPackagesInTable(javax.swing.JTable PTa, int customerId) {
        DefaultTableModel model = (DefaultTableModel) PTa.getModel();
        model.setRowCount(0);

        // Read all packages
        for (Componentes.Packages.Package p : PackageCRUDS.Read()) {
            // Check if the package belongs to the selected customer and is not in any order
            if (p.getCustomer().getCustomerID() == customerId && !isPackageInOrder((int) p.getPackageID())) {
                model.addRow(new Object[]{
                    p.getPackageID(),
                    p.getWeight(),
                    p.getContentDescription(),
                    p.getReciverName(),
                    p.getReciverPhone(),
                    p.getLocation(),
                    p.getBranch().getBranchName(),
                    p.getReciveraddress(),
                    p.getFragile() ? "Yes" : "No",
                    p.getCustomer()
                });
            }
        }

        Table_style(PTa);
    }

    private boolean isPackageInOrder(int packageID) {
        // Iterate over all orders and check if the package ID is included
        for (Order order : OrderCRUDS.Read()) { // Assuming OrderCRUD.Read() returns all orders
            if (order.getPackagepdms().getPackageID() == packageID) {
                return true;
            }
        }
        return false;
    }

    private void updateOrdersTable() {
        try {
            // Get the selected delivery staff ID
            int selectedStaffId = (int) users1.getValueAt(users1.getSelectedRow(), 0);

            // Retrieve the DeliveryStaff object
            DeliveryStaff deliveryStaff = DeliveryCRUDS.getDeliveryStaffById(selectedStaffId);
            if (deliveryStaff == null) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                        "Delivery staff with ID " + selectedStaffId + " not found.");
                return;
            }

            // Retrieve the assigned orders
            ArrayList<Order> assignedOrders = deliveryStaff.getAssignedOrders();
            if (assignedOrders == null || assignedOrders.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,
                        "No orders assigned to this delivery staff.");
                return;
            }

            // Update the orders table model
            DefaultTableModel orderModel = (DefaultTableModel) OT.getModel();
            orderModel.setRowCount(0); // Clear the existing rows

            // Add the assigned orders to the table
            for (Order order : assignedOrders) {
                String status = order.getStatus();
                String expectedDeliveryDate = order.getOrderDate();
                orderModel.addRow(new Object[]{order.getOrderID(), status, expectedDeliveryDate});
            }

            // Style the table
            Table_style(OT);

            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                    "Orders table updated successfully.");

        } catch (ArrayIndexOutOfBoundsException e) {
            // Handle the case when no delivery staff is selected
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                    "Please select a delivery staff to view assigned orders.");
        } catch (Exception e) {
            // Handle unexpected errors
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                    "Error updating Orders table: " + e.getMessage());
            System.out.println("Error in updateOrdersTable: " + e.getMessage());
        }
    }

    private void ShowDataInTable(int row) {
        try {
            ArrayList<DeliveryStaff> deliveryList = DeliveryCRUDS.readDeliveryStaff();
            DefaultTableModel userModel = (DefaultTableModel) users1.getModel();
            userModel.setRowCount(0);

            for (DeliveryStaff deliveryStaff : deliveryList) {
                // Get the route details for the delivery staff's RoatId
                Route route = RouteCRUD.getRouteById(deliveryStaff.getRoatId());
                if (route == null) {
                    continue;
                }

                Package pkg = ((Order) OrderCRUDS.getById((int) OT.getValueAt(row, 0))).getPackagepdms();

                if (pkg.getBranch().getLocationdata().getCity().equalsIgnoreCase(route.getOrigin())) {
                    // Display the delivery staff in the table
                    int id = deliveryStaff.getId();
                    String firstName = deliveryStaff.getFirstName();
                    String lastName = deliveryStaff.getLastName();
                    String dateOfBirth = deliveryStaff.getDateOfBirth();
                    String gender = deliveryStaff.getGender();
                    String address = deliveryStaff.getAddress();
                    String celPhone = deliveryStaff.getCelPhone();
                    String telPhone = deliveryStaff.getTelPhone();
                    String email = deliveryStaff.getEmail();
                    String username = deliveryStaff.getUsername();
                    String role = deliveryStaff.getRole();
                    String Vehicle = VehicleCRUD.readVehicle(deliveryStaff.getVehichleId()).toString();
                    String Roat = RouteCRUD.getRouteById(deliveryStaff.getRoatId()).toString();
                    userModel.addRow(new Object[]{id, firstName + " " + lastName, dateOfBirth, gender, address, celPhone, telPhone, email, username, role, Roat, Vehicle});
                }

            }

            Table_style(users1);
        } catch (Exception e) {
            System.out.println("Error updating table: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PackagesD = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        users = new javax.swing.JTable();
        select1 = new javax.swing.JButton();
        OrderD = new javax.swing.JDialog();
        Save = new javax.swing.JButton();
        customer = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        packageID = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        Status = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        select3 = new javax.swing.JButton();
        PackagesD1 = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        users1 = new javax.swing.JTable();
        assign1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        OT = new javax.swing.JTable();
        add = new javax.swing.JButton();
        Search = new javax.swing.JTextField();
        SearchBy = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        Filter = new javax.swing.JComboBox<>();

        users.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "PackageID", "Weight", "Content Description", "Reciver Name", "Reciver Phone", "Location", "Reciver Address", "Branch", "Fragile", "Customer"
            })
        );
        jScrollPane2.setViewportView(users);

        select1.setText("Select");
        select1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                select1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PackagesDLayout = new javax.swing.GroupLayout(PackagesD.getContentPane());
        PackagesD.getContentPane().setLayout(PackagesDLayout);
        PackagesDLayout.setHorizontalGroup(
            PackagesDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PackagesDLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(select1)
                .addGap(15, 15, 15))
        );
        PackagesDLayout.setVerticalGroup(
            PackagesDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PackagesDLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(select1)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        jLabel1.setText("Customer");

        jLabel2.setText("package ID");

        jButton1.setText("Select");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pendeng", "In Progress", "Delivered", "" }));

        jLabel3.setText("Status");

        javax.swing.GroupLayout OrderDLayout = new javax.swing.GroupLayout(OrderD.getContentPane());
        OrderD.getContentPane().setLayout(OrderDLayout);
        OrderDLayout.setHorizontalGroup(
            OrderDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrderDLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(OrderDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Save)
                    .addGroup(OrderDLayout.createSequentialGroup()
                        .addGroup(OrderDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(OrderDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Status, 0, 1, Short.MAX_VALUE)
                            .addComponent(customer, 0, 90, Short.MAX_VALUE)
                            .addComponent(packageID))
                        .addGap(10, 10, 10)
                        .addComponent(jButton1)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        OrderDLayout.setVerticalGroup(
            OrderDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OrderDLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(OrderDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(OrderDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(packageID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(OrderDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(Save)
                .addGap(14, 14, 14))
        );

        select3.setText("Select");
        select3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                select3ActionPerformed(evt);
            }
        });

        users1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "id", "Name", "Age", "Gender", "Address", "CelPhone", "TelPhone", "Email", "Username", "Roll", "Roat", "Vehicle"
            })
        );
        jScrollPane3.setViewportView(users1);

        assign1.setText("Assign order");
        assign1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assign1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PackagesD1Layout = new javax.swing.GroupLayout(PackagesD1.getContentPane());
        PackagesD1.getContentPane().setLayout(PackagesD1Layout);
        PackagesD1Layout.setHorizontalGroup(
            PackagesD1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PackagesD1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(assign1)
                .addContainerGap())
        );
        PackagesD1Layout.setVerticalGroup(
            PackagesD1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PackagesD1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(assign1)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        OT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "OrderID", "Customer Name", "Package Description", "Order Date", "Status", "Total Cost", "Actions"
            }
        ));
        OT.setRowHeight(40);
        OT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OTMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(OT);

        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SearchKeyPressed(evt);
            }
        });

        SearchBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Status", "Date" }));
        SearchBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchByActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Orders");

        Filter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Not Assigned", "Assigned" }));
        Filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addComponent(Filter, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(add))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add)
                    .addComponent(Filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void OTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OTMouseClicked
 
    }//GEN-LAST:event_OTMouseClicked

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        OrderD.setTitle("Add Customer");
        OrderD.setBounds(795, 360, 330, 380);
        OrderD.setVisible(true);
        x = 'A';
        OrderD.setAlwaysOnTop(true);
    }//GEN-LAST:event_addActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed

        String status = Status.getSelectedItem().toString();
        String orderDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date()); // Current date as order date
        String deliveryDate = null; // Default delivery date
        float cost;
        if (PackageCRUDS.ReadOne(Long.parseLong(packageID.getText())).getWeight() < 100) {
            cost = 30;
        } else {
            cost = 60;
        }

        // Extract customer information from comboBox
        int selectedCustomerIndex = customer.getSelectedIndex();
        if (selectedCustomerIndex == -1 || customer.getItemAt(selectedCustomerIndex).equals("No customers available")) {
            JOptionPane.showMessageDialog(null, "Please select a valid customer.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer selectedCustomer = (Customer) customer.getSelectedItem();
        if (selectedCustomer == null) {
            JOptionPane.showMessageDialog(null, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate package information
        long PackageID;
        try {
            PackageID = Long.parseLong(packageID.getText()); // Ensure the field ID is correct
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid package ID.", "Error", JOptionPane.ERROR_MESSAGE);

            return;
        }

        Componentes.Packages.Package selectedPackage = PackageCRUDS.ReadOne(PackageID);
        if (selectedPackage == null) {
            JOptionPane.showMessageDialog(null, "Package not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Handle order creation or update logic
        if (x == 'A') { // Add
            OrderCRUDS.create(selectedCustomer, selectedPackage, status, orderDate, deliveryDate, cost);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Order added successfully!");
            RestDialog();
        } else if (x == 'U') { // Update
            int orderID = (int) OT.getValueAt(OT.getSelectedRow(), 0);

            // Check if the status has changed to "Delivered"
            if ("Delivered".equals(status)) {
                deliveryDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date()); // Set delivery date to today            
            }

            String[] data = {status, orderDate, deliveryDate}; // Update fields
            OrderCRUDS.Update(orderID, data);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Order updated successfully!");
            RestDialog();

        }

        OrderD.setVisible(false);
        ShowInTable();
    }//GEN-LAST:event_SaveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Customer C = (Customer) customer.getSelectedItem();

        ShowPackagesInTable(users, C.getCustomerID());
        PackagesD.setTitle("Order Packages");
        PackagesD.setBounds(600, 360, 700, 380);
        PackagesD.setAlwaysOnTop(true);
        PackagesD.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void assign1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assign1ActionPerformed
        try {
            int selectedStaffId = (int) users1.getValueAt(users1.getSelectedRow(), 0);
            int selectedOrderId = (int) OT.getValueAt(OT.getSelectedRow(), 0);
            Order selectedOrder = OrderCRUDS.getById(selectedOrderId);
            if (selectedOrder == null) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                        "Order with ID " + selectedOrderId + " not found.");
                return;
            }

            DeliveryCRUDS.addOrderToAssignedList(selectedStaffId, selectedOrder.getOrderID());

            updateOrdersTable();

        } catch (ArrayIndexOutOfBoundsException e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                    "Please select a delivery staff and an order before assigning.");
        } catch (Exception e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                    "Error assigning order: " + e.getMessage());
            System.out.println("Error in assign1ActionPerformed: " + e.getMessage());
        }
    }//GEN-LAST:event_assign1ActionPerformed

    private void select3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_select3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_select3ActionPerformed

    private void select1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_select1ActionPerformed
        packageID.setText(users.getValueAt(users.getSelectedRow(), 0).toString());
        PackagesD.setVisible(false);

    }//GEN-LAST:event_select1ActionPerformed

    private void SearchByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchByActionPerformed
        Search.setText("");
        ShowInTable();
    }//GEN-LAST:event_SearchByActionPerformed

    private void SearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (OrderCRUDS.Search(Search.getText(), SearchBy) == null) {
                ShowInTable();
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "The Order Dose not Exist");
            } else {
                ShowDataInTableSearch();
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (CustomerCRUDS.Search(Search.getText(), SearchBy) == null) {
                ShowInTable();
            } else {
                ShowDataInTableSearch();
            }
        }
    }//GEN-LAST:event_SearchKeyPressed

    private void FilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilterActionPerformed
        ShowInTable();
    }//GEN-LAST:event_FilterActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Filter;
    private javax.swing.JTable OT;
    private javax.swing.JDialog OrderD;
    private javax.swing.JDialog PackagesD;
    private javax.swing.JDialog PackagesD1;
    private javax.swing.JButton Save;
    private javax.swing.JTextField Search;
    private javax.swing.JComboBox<String> SearchBy;
    private javax.swing.JComboBox<String> Status;
    private javax.swing.JButton add;
    private javax.swing.JButton assign1;
    private javax.swing.JComboBox<Customer> customer;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField packageID;
    private javax.swing.JButton select1;
    private javax.swing.JButton select3;
    private javax.swing.JTable users;
    private javax.swing.JTable users1;
    // End of variables declaration//GEN-END:variables

    private void showUpdateDialog(int orderID) {
        Order selectedOrder = OrderCRUDS.getById(orderID);
        customer.setSelectedItem(selectedOrder.getCustomer());

        packageID.setText(String.valueOf(selectedOrder.getPackagepdms().getPackageID()));
        Status.setSelectedItem(selectedOrder.getStatus());
    }

    private void RestDialog() {
        customer.setSelectedItem(-1);

        packageID.setText("");
        Status.setSelectedItem("");
    }

    private void ShowDataInTable1() {
        try {
            ArrayList<DeliveryStaff> deliveryList = DeliveryCRUDS.readDeliveryStaff();
            DefaultTableModel userModel = (DefaultTableModel) users1.getModel();
            userModel.setRowCount(0);

            for (DeliveryStaff deliveryStaff : deliveryList) {
                int id = deliveryStaff.getId();
                String firstName = deliveryStaff.getFirstName();
                String lastName = deliveryStaff.getLastName();
                String dateOfBirth = deliveryStaff.getDateOfBirth();
                String gender = deliveryStaff.getGender();
                String address = deliveryStaff.getAddress();
                String celPhone = deliveryStaff.getCelPhone();
                String telPhone = deliveryStaff.getTelPhone();
                String email = deliveryStaff.getEmail();
                String username = deliveryStaff.getUsername();
                String role = deliveryStaff.getRole();
                int Vehicle = deliveryStaff.getVehichleId();
                int Roat = deliveryStaff.getRoatId();
                int Schedule = deliveryStaff.getScheduleId();

                userModel.addRow(new Object[]{id, firstName + " " + lastName, dateOfBirth, gender, address, celPhone, telPhone, email, username, role, Roat, Schedule, Vehicle});
                Table_style(users1);
            }
        } catch (Exception e) {
            System.out.println("Error updating table: " + e.getMessage());
        }
    }

}
