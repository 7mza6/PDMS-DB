package Componentes.Customers;

import Componentes.Order.Order;
import Componentes.Order.OrderCRUDS;
import static PDMS.theme.theams.Table_style;
import java.awt.event.KeyEvent;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.cellButton.TableActionCellEditor;
import raven.cellButton.TableActionCellRender;
import raven.cellButton.TableActionEvent;
import raven.datetime.component.date.DatePicker;
import raven.toast.Notifications;



/**
 *
 * @author Raven
 */
public class CustomersS extends javax.swing.JPanel {

    char x; //save button 
    public CustomersS() {
        initComponents();
        ShowDataInTable();
        CT.getColumnModel().getColumn(7).setCellRenderer (new TableActionCellRender());
        loadTableEvents();
        CT.getColumnModel().getColumn (7).setCellEditor (new TableActionCellEditor (loadTableEvents()));
        ID.setVisible(false);
        
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
             public void onAssign(int row) {
                
            }
        };
    }
     
    
     
     
     private void onViewEvent(int row) {
        int customerId = (int) CT.getValueAt(row, 0);
    ArrayList<Order> customerOrders = OrderCRUDS.getOrdersByCustomerId(customerId);
    displayOrdersInTable(customerOrders);
    Orders.setTitle("Customer Orders");
    Orders.setBounds(795, 360, 330, 380);
    Orders.setAlwaysOnTop(true);
    Orders.setVisible(true);
    }
     
     
     
    private void onDeleteEvent(int row) {
      int id = (int) CT.getValueAt(row, 0); // Assuming the first column contains the Customer ID
    int confirmation = JOptionPane.showConfirmDialog(
        null, 
        "Are you sure you want to delete this customer?", 
        "Confirm Deletion", 
        JOptionPane.YES_NO_OPTION
    );
    if (confirmation == JOptionPane.YES_OPTION) {
        CustomerCRUDS.Remove(id); // Replace with your delete method
        ShowDataInTable(); // Method to refresh the customers table
        JOptionPane.showMessageDialog(null, "Customer deleted successfully.");
    }
    }
    
    
    
    private void onEditEvent(int row) {
            customerDialog.setTitle("Update Customer");
            customerDialog.setBounds(795, 360, 330, 380);
            showUpdateDialog((int) CT.getValueAt(CT.getSelectedRow(), 0));
            x = 'U';
            customerDialog.setAlwaysOnTop(true);

    }
    
  

    public void ShowDataInTable() {
        DefaultTableModel model = (DefaultTableModel) CT.getModel();
        model.setRowCount(0);
        for (Customer c : CustomerCRUDS.Read()) {
            Object[] row = {c.getCustomerID(), c.getFirstName(), c.getLastName(), c.getGender(), c.getPhone(), c.getDateOfBirth(), c.getAddress()};
            model.addRow(row);
        }
        Table_style(CT);

    }

    public void ShowDataInTableSearch() {
        DefaultTableModel model = (DefaultTableModel) CT.getModel();
        model.setRowCount(0);
        for (Customer c : CustomerCRUDS.Search(Search.getText(),SearchBy)) {
            Object[] row = {c.getCustomerID(), c.getFirstName(), c.getLastName(), c.getGender(), c.getPhone(), c.getDateOfBirth(), c.getAddress()};
            model.addRow(row);
        }
        Table_style(CT);

    }
    
    
    private void showUpdateDialog(int id) {
    Customer customer = CustomerCRUDS.ReadOne(id); 
    if (customer != null) {
        FirstName.setText(customer.getFirstName());
        LastName.setText(customer.getLastName());
        Gender.setText(customer.getGender());
        Phone.setText(customer.getPhone());
        DataReg.setText(customer.getDateOfBirth()); 
        Address.setText(customer.getAddress());
        customerDialog.setVisible(true);

        ID.setText(String.valueOf(customer.getCustomerID()));
        ID.setEditable(false);
    } else {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Customer not found.");
    }
}

    

    public void ShowDataInTableOne() {
        DefaultTableModel model = (DefaultTableModel) CT.getModel();
        model.setRowCount(0);
        Customer c = CustomerCRUDS.ReadOne(parseInt(Search.getText()));
        Object[] row = {c.getCustomerID(), c.getFirstName(), c.getLastName(), c.getGender(), c.getPhone(), c.getDateOfBirth(),c.getAddress()};
        model.addRow(row);
        Table_style(CT);
    }
    
    
    private void displayOrdersInTable(ArrayList<Order> orders) {
    DefaultTableModel model = (DefaultTableModel) ordersTable.getModel(); // Assuming `ordersTable` is your JTable for orders
    model.setRowCount(0);

    for (Order order : orders) {
        Object[] row = {
            order.getOrderID(),
            order.getStatus(),
            order.getOrderDate(),
            order.getDeleveryDate(),
            order.getTotalCost()
        };
        model.addRow(row);
    }
            Table_style(ordersTable);

}

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customerDialog = new javax.swing.JDialog();
        LastName = new javax.swing.JTextField();
        Save = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        FirstName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Gender = new javax.swing.JTextField();
        Address = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Phone = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ID = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        DataReg = new javax.swing.JFormattedTextField();
        DatePicker datePicker=new DatePicker();
        datePicker.setEditor(DataReg);
        Orders = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        ordersTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        CT = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Search = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        SearchBy = new javax.swing.JComboBox<>();

        customerDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                customerDialogWindowClosing(evt);
            }
        });

        LastName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LastName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        LastName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("First Name");

        FirstName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FirstName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        FirstName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Gender");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Address");

        Gender.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Gender.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Gender.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Address.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Phone");

        Phone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Phone.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Phone.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Date Reg");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Last Name");

        ID.setPreferredSize(new java.awt.Dimension(64, 0));
        ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 185, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        DataReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataRegActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout customerDialogLayout = new javax.swing.GroupLayout(customerDialog.getContentPane());
        customerDialog.getContentPane().setLayout(customerDialogLayout);
        customerDialogLayout.setHorizontalGroup(
            customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerDialogLayout.createSequentialGroup()
                .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customerDialogLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(customerDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addGroup(customerDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FirstName)
                    .addComponent(LastName, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Phone, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(Gender)
                    .addComponent(ID, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(DataReg, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Save)
                        .addComponent(Address, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(customerDialogLayout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(58, 58, 58)))
        );
        customerDialogLayout.setVerticalGroup(
            customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerDialogLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(DataReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(Address, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(Save)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(customerDialogLayout.createSequentialGroup()
                    .addGap(117, 117, 117)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(117, Short.MAX_VALUE)))
        );

        ordersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object[]{"Order ID", "Status", "Order Date", "Delivery Date", "Total Cost"}, 0));
    jScrollPane2.setViewportView(ordersTable);

    javax.swing.GroupLayout OrdersLayout = new javax.swing.GroupLayout(Orders.getContentPane());
    Orders.getContentPane().setLayout(OrdersLayout);
    OrdersLayout.setHorizontalGroup(
        OrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
    );
    OrdersLayout.setVerticalGroup(
        OrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
    );

    CT.setAutoCreateRowSorter(true);
    CT.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null}
        },
        new String [] {
            "ID", "First Name", "Last Name", "Gender", "Phone", "Date Reg", "Address", "Actions"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, false, true
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    CT.setRowHeight(40);
    CT.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            CTMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(CT);

    jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
    jLabel1.setText("Coustmers");

    Search.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            SearchActionPerformed(evt);
        }
    });
    Search.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            SearchKeyPressed(evt);
        }
    });
    Search.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
        public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
            SearchVetoableChange(evt);
        }
    });

    add.setText("add");
    add.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addActionPerformed(evt);
        }
    });

    SearchBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Name", "Address" }));
    SearchBy.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            SearchByActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(SearchBy, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(298, 298, 298)
                    .addComponent(add)
                    .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        .addComponent(jScrollPane1)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(14, 14, 14)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(SearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(add))
            .addGap(18, 18, 18)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
    );
    }// </editor-fold>//GEN-END:initComponents

    private void CTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CTMouseClicked
    }//GEN-LAST:event_CTMouseClicked

    private void SearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            
            
            if (CustomerCRUDS.Search(Search.getText(),SearchBy) == null) {
                ShowDataInTable();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "The Customer Dose not Exist");
            } else {
                ShowDataInTableSearch();
            }
        }
        
        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
              if (CustomerCRUDS.Search(Search.getText(),SearchBy) == null) {
                ShowDataInTable();
            } else {
                ShowDataInTableSearch();
            }
        }
    }//GEN-LAST:event_SearchKeyPressed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
      try {
        String firstName = FirstName.getText();
        String lastName = LastName.getText();
        String address = Address.getText();
        String phone = Phone.getText();
        String gender = Gender.getText(); 
        String dateReg = DataReg.getText();
        
        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || gender.isEmpty() || dateReg.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Please fill in all required fields.");
            return;
        }

        if (phone.length() != 10 || !CustomerCRUDS.IsInt(phone)) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Invalid phone number. It must be 10 digits.");
            return;
        }

        if (x == 'U') { 
            int customerID = Integer.parseInt(ID.getText().trim());
            Customer updatedCustomer = new Customer(customerID, firstName, lastName,gender , phone,dateReg , address);
            CustomerCRUDS.Update(customerID, updatedCustomer);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Customer updated successfully!");
        } else if (x == 'A') { 
            String[] data = {firstName, lastName,gender , phone, dateReg,address};
            CustomerCRUDS.Create(data);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Customer added successfully!");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Unknown operation type.");
        }

        FirstName.setText("");
        LastName.setText("");
        Address.setText("");
        Phone.setText("");
        Gender.setText("");
        DataReg.setText("");
        ID.setText(""); 
        customerDialog.setVisible(false);

    } catch (Exception ex) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "An error occurred during the operation.\n" + ex.getMessage());
    }
      ShowDataInTable();

    }//GEN-LAST:event_SaveActionPerformed

    private void IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
            customerDialog.setTitle("Add Customer");
            customerDialog.setBounds(795, 360, 330, 380);
            customerDialog.setVisible(true);
            x = 'A';   
            customerDialog.setAlwaysOnTop(true);
    }//GEN-LAST:event_addActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchActionPerformed

    private void SearchVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_SearchVetoableChange
          
        int i = Integer.parseInt(Search.getText()) - 1;
            Customer c = CustomerCRUDS.ReadOne(i);
            System.out.println(c);
            if (c == null) {
                ShowDataInTable();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "The Customer Dose not Exist");

            } else {
                ShowDataInTableOne();
            }
        

    }//GEN-LAST:event_SearchVetoableChange

    private void SearchByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchByActionPerformed
        Search.setText("");
        ShowDataInTable();
    }//GEN-LAST:event_SearchByActionPerformed

    private void customerDialogWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_customerDialogWindowClosing
FirstName.setText("");
        LastName.setText("");
        Address.setText("");
        Phone.setText("");
        Gender.setText("");
        DataReg.setText("");
        ID.setText("");    }//GEN-LAST:event_customerDialogWindowClosing

    private void DataRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DataRegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DataRegActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Address;
    private javax.swing.JTable CT;
    private javax.swing.JFormattedTextField DataReg;
    private javax.swing.JTextField FirstName;
    private javax.swing.JTextField Gender;
    private javax.swing.JTextField ID;
    private javax.swing.JTextField LastName;
    private javax.swing.JDialog Orders;
    private javax.swing.JTextField Phone;
    private javax.swing.JButton Save;
    private javax.swing.JTextField Search;
    private javax.swing.JComboBox<String> SearchBy;
    private javax.swing.JButton add;
    private javax.swing.JDialog customerDialog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable ordersTable;
    // End of variables declaration//GEN-END:variables
}
