package Componentes.Branchs;

import Componentes.Cities.CitiesCRUDS;
import Componentes.Cities.CitiesClass;
import static PDMS.theme.theams.Table_style;
import javax.swing.table.DefaultTableModel;
import raven.cellButton.TableActionCellEditor;
import raven.cellButton.TableActionCellRender;
import raven.cellButton.TableActionEvent;
import raven.toast.Notifications;



public class BranchScreen extends javax.swing.JPanel {
    
    char x; //save button 

    public BranchScreen() {
        initComponents();
        ShowDataInTable();
        GetDataToComboBox();
        BT.getColumnModel().getColumn(4).setCellRenderer (new TableActionCellRender(false,false));
        loadTableEvents();
        BT.getColumnModel().getColumn (4).setCellEditor (new TableActionCellEditor (loadTableEvents(),false));
       
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
            }
             public void onAssign(int row) {
                
            }
        };
    }
     
     
     private void onDeleteEvent(int row) {
    int branchNumber = Integer.parseInt(String.valueOf(BT.getValueAt(row, 0)));

    int confirmation = javax.swing.JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to delete Branch #" + branchNumber + "?",
        "Confirm Delete", 
        javax.swing.JOptionPane.YES_NO_OPTION);

    if (confirmation == javax.swing.JOptionPane.YES_OPTION) {
        BranchsCRUDS.Delete(branchNumber);
        ShowDataInTable();
    }
}

    
    
    private void onEditEvent(int row) {
    BranchNumber.setText(String.valueOf(BT.getValueAt(row, 0)));
    BranchName.setText((String) BT.getValueAt(row, 1));
    cityName.setSelectedItem((String) BT.getValueAt(row, 2));
    Address.setText((String) BT.getValueAt(row, 3));

    branchForm.setTitle("Edit Branch");
    branchForm.setBounds(795, 360, 330, 380);
    branchForm.setVisible(true);
    x = 'U';
}
    
    
        public void GetDataToComboBox() {
        for (CitiesClass city : CitiesCRUDS.Read()) {
            cityName.addItem(city.getCityName());
        }
    }
        
        
        public void ShowDataInTable() {
        DefaultTableModel model = (DefaultTableModel) BT.getModel();
        model.setRowCount(0);
        for (BranchClass branch : BranchsCRUDS.Read()) {
            Object[] row = {branch.getBranchNumber(), branch.getBranchName(), branch.getLocationdata().getCity(), branch.getLocationdata().getStreet()};
            model.addRow(row);
        }
        Table_style(BT);
    }

  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        branchForm = new javax.swing.JDialog();
        BranchNumber = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cityName = new javax.swing.JComboBox<>();
        Save = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        BranchName = new javax.swing.JTextField();
        Address1 = new javax.swing.JLabel();
        Address = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        BT = new javax.swing.JTable();
        Add = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("City Name");

        cityName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityNameActionPerformed(evt);
            }
        });

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        jLabel2.setText("Branch Name");

        Address1.setText("Address");

        jLabel3.setText("Branch Number");

        javax.swing.GroupLayout branchFormLayout = new javax.swing.GroupLayout(branchForm.getContentPane());
        branchForm.getContentPane().setLayout(branchFormLayout);
        branchFormLayout.setHorizontalGroup(
            branchFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(branchFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(branchFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(branchFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, branchFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, branchFormLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(32, 32, 32)
                                .addGroup(branchFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Address, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cityName, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(branchFormLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BranchNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(Address1)
                        .addComponent(Save, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(branchFormLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(BranchName, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        branchFormLayout.setVerticalGroup(
            branchFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(branchFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(branchFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(BranchNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(branchFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(BranchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(branchFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cityName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(branchFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Address1)
                    .addComponent(Address, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(Save)
                .addGap(19, 19, 19))
        );

        BT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Branch Number", "Branch Name", "City Name", "Address", "Actins"
            }
        ));
        BT.setRowHeight(40);
        BT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BTMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(BT);

        Add.setText("Add");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Branch");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Add)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 941, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Add))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
if (x == 'A') {
    // Collect data from input fields
    String[] data = new String[4];
    data[0] = BranchNumber.getText();      // Branch number
    data[1] = BranchName.getText();        // Branch name
    data[2] = cityName.getSelectedItem().toString(); // City name
    data[3] = Address.getText();           // Address/Street

    // Call the Create method to add a new branch
    BranchsCRUDS.Create(data);

    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "New branch added successfully!");

    ShowDataInTable(); // Refresh the JTable after addition
} else if (x == 'U') {
    // Collect data for updating the branch
    int branchNumber = Integer.parseInt(BranchNumber.getText());
    String[] data = new String[3];
    data[0] = BranchName.getText();        // Branch name
    data[1] = cityName.getSelectedItem().toString(); // City name
    data[2] = Address.getText();           // Address/Street

    // Call the Update method to modify an existing branch
    boolean updated = BranchsCRUDS.Update(branchNumber, data);

    if (updated) {
        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Branch updated successfully!");
    } else {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Branch not found!");
    }

    ShowDataInTable(); // Refresh the JTable after update
} else {
    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Invalid operation type!");
}

branchForm.setVisible(false); // Close the form after saving

    }//GEN-LAST:event_SaveActionPerformed

    private void BTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTMouseClicked
         int ID = (int) BT.getValueAt(BT.getSelectedRow(), 0);
        String Branchname = (String) BT.getValueAt(BT.getSelectedRow(), 1);
        String Cityname = (String) BT.getValueAt(BT.getSelectedRow(), 2);
        String address = (String) BT.getValueAt(BT.getSelectedRow(), 3);
        BranchName.setText(Branchname);
        cityName.setSelectedItem(Cityname);
        Address.setText(address);
    }//GEN-LAST:event_BTMouseClicked

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
            branchForm.setTitle("Add Customer");
            branchForm.setBounds(795, 360, 330, 380);
            branchForm.setVisible(true);
            x = 'A';   
            branchForm.setAlwaysOnTop(true);       
    }//GEN-LAST:event_AddActionPerformed

    private void cityNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cityNameActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JTextField Address;
    private javax.swing.JLabel Address1;
    private javax.swing.JTable BT;
    private javax.swing.JTextField BranchName;
    private javax.swing.JTextField BranchNumber;
    private javax.swing.JButton Save;
    private javax.swing.JDialog branchForm;
    private javax.swing.JComboBox<String> cityName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
