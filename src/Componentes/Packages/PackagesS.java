package Componentes.Packages;

import Componentes.Branchs.BranchClass;
import Componentes.Branchs.BranchsCRUDS;
import Componentes.Cities.CitiesCRUDS;
import Componentes.Cities.CitiesClass;
import Componentes.Customers.Customer;
import Componentes.Customers.CustomerCRUDS;
import static PDMS.theme.theams.Table_style;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static net.miginfocom.layout.UnitValue.PT;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import raven.cellButton.TableActionCellEditor;
import raven.cellButton.TableActionCellRender;
import raven.cellButton.TableActionEvent;
import raven.toast.Notifications;

/**
 *
 * @author Raven
 */
public class PackagesS extends javax.swing.JPanel {

    char x; //save button 

    public PackagesS() {
        initComponents();
        ShowDataInTable(PTa);
        PTa.getColumnModel().getColumn(10).setCellRenderer(new TableActionCellRender(false,false));
        loadTableEvents();
        PTa.getColumnModel().getColumn(10).setCellEditor(new TableActionCellEditor(loadTableEvents(), false));
        GetDataToComboBox();
        fillComboBox(customer);

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

    }

    private void onDeleteEvent(int row) {
    long id =  (long) PTa.getValueAt(row, 0); // Assuming the first column contains the Package ID
    int confirmation = JOptionPane.showConfirmDialog(
        null, 
        "Are you sure you want to delete this package?", 
        "Confirm Deletion", 
        JOptionPane.YES_NO_OPTION
    );
    if (confirmation == JOptionPane.YES_OPTION) {
        PackageCRUDS.Remove(id); // Replace with your delete method
        ShowDataInTable(PTa); // Method to refresh the packages table
        JOptionPane.showMessageDialog(null, "Package deleted successfully.");
    }
    }

    private void onEditEvent(int row) {
        formD.setTitle("Update Package");
        formD.setBounds(630, 300, 679, 520);
        showPackageDataByID((Long) PTa.getValueAt(row, 0));
        x = 'U';
        formD.setVisible(true);
        formD.setAlwaysOnTop(true);

    }

    public void ShowDataInTable(javax.swing.JTable PTa) {
        DefaultTableModel model = (DefaultTableModel) PTa.getModel();
        model.setRowCount(0);
        for (Package p : PackageCRUDS.Read()) {
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
        Table_style(PTa);
    }

    private void showPackageDataByID(Long packageID) {
        // Retrieve the package based on the provided packageID
        Package p = PackageCRUDS.ReadOne(packageID);

        if (p != null) {
            // If package is found, populate the fields
            ID.setText(packageID.toString());
            weight.setText(String.valueOf(p.getWeight()));
            ContentDescription.setText(p.getContentDescription());
            RN.setText(p.getReciverName());
            RP.setText(p.getReciverPhone());
            RA.setText(p.getReciveraddress());
            City.setSelectedItem(p.getLocation());
            Branch.setSelectedItem(p.getBranch());
            Fragile.setSelected(p.getFragile()); // Checkbox
            customer.setSelectedItem(p.getCustomer());
        } else {
            // If package is not found, show a notification
            Notifications.getInstance().show(
                    Notifications.Type.ERROR,
                    Notifications.Location.TOP_CENTER,
                    "Package Not Found!"
            );
        }
    }

    public void GetDataToComboBox() {
        for (CitiesClass city : CitiesCRUDS.Read()) {
            City.addItem(city.getCityName());
        }
        for (BranchClass branch : BranchsCRUDS.Read()) {
            Branch.addItem(branch);
        }
        AutoCompleteDecorator.decorate(Branch);
        AutoCompleteDecorator.decorate(City);

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        formD = new javax.swing.JDialog();
        ID = new javax.swing.JTextField();
        RN = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        weight = new javax.swing.JTextField();
        RP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        RA = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        City = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        Fragile = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        Create = new javax.swing.JButton();
        Branch = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ContentDescription = new javax.swing.JTextArea();
        customer = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        add = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        PTa = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();

        ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Reciver Information");

        jLabel6.setText("Branch");

        jLabel8.setText("Description");

        jLabel9.setText("Weight");

        jLabel3.setText("Reciver Name");

        RA.setColumns(20);
        RA.setRows(5);
        jScrollPane2.setViewportView(RA);

        jLabel11.setText("Reciver Adrees");

        City.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CityActionPerformed(evt);
            }
        });

        jLabel4.setText("Reciver Phone Number");

        Fragile.setText("Fragile");

        jLabel10.setText("Package Number");

        Create.setText("Save");
        Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateActionPerformed(evt);
            }
        });

        Branch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BranchActionPerformed(evt);
            }
        });

        jLabel5.setText("City");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Package Information");

        ContentDescription.setColumns(20);
        ContentDescription.setRows(5);
        jScrollPane3.setViewportView(ContentDescription);

        jLabel7.setText("Customer");

        javax.swing.GroupLayout formDLayout = new javax.swing.GroupLayout(formD.getContentPane());
        formD.getContentPane().setLayout(formDLayout);
        formDLayout.setHorizontalGroup(
            formDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formDLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(formDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Fragile, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weight)
                    .addComponent(ID)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(Branch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addComponent(customer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(formDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(formDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, formDLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(187, 187, 187))
                        .addComponent(RN, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addGroup(formDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(Create, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(RP, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5)
                    .addGroup(formDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(City, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)))
                .addGap(37, 37, 37))
        );
        formDLayout.setVerticalGroup(
            formDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formDLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(formDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(25, 25, 25)
                .addGroup(formDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(formDLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(City, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Create))
                    .addGroup(formDLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Branch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(Fragile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        PTa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PackageID", "Weight", "Content Description", "Reciver Name", "Reciver Phone", "Location", "Reciver Address", "Branch", "Fragile", "Customer", "Actions"
            }
        ));
        PTa.setRowHeight(40);
        jScrollPane1.setViewportView(PTa);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setText("Package");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(add)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(add))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        formD.setTitle("Add Package");
        formD.setBounds(630, 300, 679, 520);
        x = 'A';
        formD.setVisible(true);
        formD.setAlwaysOnTop(true);

    }//GEN-LAST:event_addActionPerformed


    private void IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDActionPerformed

    private void CityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CityActionPerformed

    private void CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateActionPerformed
        try {
            // Check the operation type
            if (x == 'A') { // Add operation
                // Generate a unique ID
                long packageID = System.currentTimeMillis(); // unique ID generation

                // Get values from the form
                float packageWeight = Float.parseFloat(weight.getText().trim()); // Ensure weight is a float
                String description = ContentDescription.getText().trim();
                String receiverName = RN.getText().trim();
                String receiverPhone = RP.getText().trim();
                String receiverAddress = RA.getText().trim();
                String location = (String) City.getSelectedItem();
                BranchClass branch =  (BranchClass) Branch.getSelectedItem();
                boolean isFragile = Fragile.isSelected();
                Customer Cus = (Customer) customer.getSelectedItem();

                // Validate input fields
                if (receiverName.isEmpty() || receiverPhone.isEmpty() || receiverAddress.isEmpty()) {
                    Notifications.getInstance().show(
                            Notifications.Type.WARNING,
                            Notifications.Location.TOP_CENTER,
                            "Please fill in all required fields!"
                    );
                    return;
                }

                if (receiverPhone.length() != 10 || !PackageCRUDS.IsInt(receiverPhone)) {
                    Notifications.getInstance().show(
                            Notifications.Type.WARNING,
                            Notifications.Location.TOP_CENTER,
                            "Invalid phone number! Must be 10 digits."
                    );
                    return;
                }

                // Create a new Package object using the constructor
                Package packageData = new Package(
                        packageID, // Automatically generated ID
                        packageWeight, // Weight as float
                        description, // Content description
                        isFragile, // Fragile checkbox value
                        receiverName, // Receiver's name
                        receiverPhone, // Receiver's phone
                        location, // Location (from combo box)
                        receiverAddress, // Receiver's address
                        branch, // Branch (from combo box)
                        Cus
                );

                // Add the package
                boolean isAdded = PackageCRUDS.Create(packageData);
                if (isAdded) {
                    Notifications.getInstance().show(
                            Notifications.Type.SUCCESS,
                            Notifications.Location.TOP_CENTER,
                            "Package added successfully!"
                    );
                    formD.setVisible(false);

                    ShowDataInTable(PTa); // Refresh the table
                } else {
                    Notifications.getInstance().show(
                            Notifications.Type.ERROR,
                            Notifications.Location.TOP_CENTER,
                            "Failed to add package. Please try again."
                    );
                }
            } else if (x == 'U') { // Update operation
                // Retrieve the ID from the form (non-editable field)
                long packageID = Long.parseLong(ID.getText().trim());
                float packageWeight = Float.parseFloat(weight.getText().trim());
                String description = ContentDescription.getText().trim();
                String receiverName = RN.getText().trim();
                String receiverPhone = RP.getText().trim();
                String receiverAddress = RA.getText().trim();
                String location = (String) City.getSelectedItem();
                BranchClass branch =  (BranchClass) Branch.getSelectedItem();
                boolean isFragile = Fragile.isSelected();
                Customer Cus = (Customer) customer.getSelectedItem();

                // Validate input fields
                if (receiverName.isEmpty() || receiverPhone.isEmpty() || receiverAddress.isEmpty()) {
                    Notifications.getInstance().show(
                            Notifications.Type.WARNING,
                            Notifications.Location.TOP_CENTER,
                            "Please fill in all required fields!"
                    );
                    return;
                }

                if (receiverPhone.length() != 10 || !PackageCRUDS.IsInt(receiverPhone)) {
                    Notifications.getInstance().show(
                            Notifications.Type.WARNING,
                            Notifications.Location.TOP_CENTER,
                            "Invalid phone number! Must be 10 digits."
                    );
                    return;
                }

                // Update the existing Package object
                Package packageData = new Package(
                        packageID, // Keep the existing ID
                        packageWeight, // Weight as float
                        description, // Content description
                        isFragile, // Fragile checkbox value
                        receiverName, // Receiver's name
                        receiverPhone, // Receiver's phone
                        location, // Location (from combo box)
                        receiverAddress, // Receiver's address
                        branch, // Branch (from combo box)
                        Cus
                );

                // Update the package
                boolean isUpdated = PackageCRUDS.Update(packageData);
                if (isUpdated) {
                    Notifications.getInstance().show(
                            Notifications.Type.SUCCESS,
                            Notifications.Location.TOP_CENTER,
                            "Package updated successfully!"
                    );

                    formD.setVisible(false);
                    ShowDataInTable(PTa); // Refresh the table
                } else {
                    Notifications.getInstance().show(
                            Notifications.Type.ERROR,
                            Notifications.Location.TOP_CENTER,
                            "Failed to update package. Please try again."
                    );
                }
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(
                    Notifications.Type.WARNING,
                    Notifications.Location.TOP_CENTER,
                    "Invalid input! Check numeric fields."
            );
        } catch (Exception e) {
            Notifications.getInstance().show(
                    Notifications.Type.ERROR,
                    Notifications.Location.TOP_CENTER,
                    "An error occurred: " + e.getMessage()
            );
        }


    }//GEN-LAST:event_CreateActionPerformed

    private void BranchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BranchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BranchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<BranchClass> Branch;
    private javax.swing.JComboBox<String> City;
    private javax.swing.JTextArea ContentDescription;
    private javax.swing.JButton Create;
    private javax.swing.JCheckBox Fragile;
    private javax.swing.JTextField ID;
    private javax.swing.JTable PTa;
    private javax.swing.JTextArea RA;
    private javax.swing.JTextField RN;
    private javax.swing.JTextField RP;
    private javax.swing.JButton add;
    private javax.swing.JComboBox<Customer> customer;
    private javax.swing.JDialog formD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField weight;
    // End of variables declaration//GEN-END:variables
}
