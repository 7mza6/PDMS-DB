package Componentes.Vehicles;

import static PDMS.theme.theams.Table_style;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.cellButton.TableActionCellEditor;
import raven.cellButton.TableActionCellRender;
import raven.cellButton.TableActionEvent;
import raven.toast.Notifications;

/**
 *
 * @author Raven
 */
public class VehiclesS extends javax.swing.JPanel {

    private char x; // Used to identify action type ('U' for update, etc.)

    public VehiclesS() {
        initComponents();
        tblupdate();
        vehicleTable.getColumnModel().getColumn(9).setCellRenderer(new TableActionCellRender(false,false));
        vehicleTable.getColumnModel().getColumn(9).setCellEditor(new TableActionCellEditor(loadTableEvents(), false));
    }

    private TableActionEvent loadTableEvents() {
        return new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                vehicleDialog.setTitle("Update Vehicle");
                vehicleDialog.setBounds(600, 360, 660, 500);
                vehicleDialog.setVisible(true);
                x = 'U';
                showVehicleDataByID((int) vehicleTable.getValueAt(row, 0));
            }

            @Override
            public void onDelete(int row) {
                int id = (int) vehicleTable.getValueAt(row, 0);
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this vehicle?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    VehicleCRUD.deleteVehicle(id);
                    tblupdate();
                    JOptionPane.showMessageDialog(null, "Vehicle deleted successfully.");
                }
            }

            @Override
            public void onView(int row) {
            }
            
            public void onAssign(int row) {
                
            }
        };
    }

    public void tblupdate() {
        ArrayList<Vehicle> vehicleList = VehicleCRUD.readVehicles();
        DefaultTableModel tableModel = (DefaultTableModel) vehicleTable.getModel();
        tableModel.setRowCount(0);

        // Populate the table with vehicle data
        for (Vehicle vehicle : vehicleList) {
            tableModel.addRow(new Object[]{
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getType(),
                vehicle.getLicensePlate(),
                vehicle.getChassisNumber(),
                vehicle.getCapacity(),
                vehicle.getStatus(),
                vehicle.getLastServiceDate()
            });

        }
        Table_style(vehicleTable);
    }

    private void showVehicleDataByID(int id) {
        Vehicle vehicle = VehicleCRUD.readVehicle(id);
        if (vehicle != null) {
            // Populate the dialog fields with the vehicle's data
            brand.setText(vehicle.getBrand());
            model.setText(vehicle.getModel());
            type.setText(vehicle.getType());
            licensePlate.setText(vehicle.getLicensePlate());
            chassisNumber.setText(vehicle.getChassisNumber());
            capacity.setText(String.valueOf(vehicle.getCapacity()));
            status.setText(vehicle.getStatus());
            lastServiceDate.setText(vehicle.getLastServiceDate());
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vehicle not found.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vehicleDialog = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        brand = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        model = new javax.swing.JTextField();
        type = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        licensePlate = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        chassisNumber = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        capacity = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        status = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lastServiceDate = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        vehicleTable = new javax.swing.JTable();
        Add = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        jLabel1.setText("Brand");

        jLabel2.setText("Model");

        jLabel3.setText("Type");

        jLabel4.setText("License Plate");

        jLabel5.setText("Chassis Number");

        jLabel6.setText("Capacity");

        jLabel7.setText("Status");

        jLabel8.setText("Last Service Date ");

        save.setText("save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout vehicleDialogLayout = new javax.swing.GroupLayout(vehicleDialog.getContentPane());
        vehicleDialog.getContentPane().setLayout(vehicleDialogLayout);
        vehicleDialogLayout.setHorizontalGroup(
            vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehicleDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lastServiceDate, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                    .addComponent(status)
                    .addComponent(capacity)
                    .addComponent(chassisNumber)
                    .addComponent(type)
                    .addComponent(model)
                    .addComponent(licensePlate)
                    .addComponent(brand))
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vehicleDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(save)
                .addGap(19, 19, 19))
        );
        vehicleDialogLayout.setVerticalGroup(
            vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehicleDialogLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(brand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(licensePlate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chassisNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vehicleDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastServiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(38, 38, 38)
                .addComponent(save)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        vehicleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {},
            new String[] {
                "ID", "Brand", "Model", "Type", "License Plate", "Chassis Number", "Capacity (Tons)", "Status","last Service Date","Actions"
            })
        );
        vehicleTable.setRowHeight(40);
        jScrollPane1.setViewportView(vehicleTable);

        Add.setText("Add");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setText("Coustmer");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Add)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Add))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        vehicleDialog.setTitle("Update Route");
        vehicleDialog.setBounds(600, 360, 660, 500);
        vehicleDialog.setVisible(true);
        x = 'A';    }//GEN-LAST:event_AddActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        try {
            // Create a Vehicle object with data from the dialog fields
            Vehicle vehicle = new Vehicle();
            vehicle.setBrand(brand.getText().trim());
            vehicle.setModel(model.getText().trim());
            vehicle.setType(type.getText().trim());
            vehicle.setLicensePlate(licensePlate.getText().trim());
            vehicle.setChassisNumber(chassisNumber.getText().trim());
            vehicle.setCapacity((float) Double.parseDouble(capacity.getText().trim()));
            vehicle.setStatus(status.getText().trim());
            vehicle.setLastServiceDate(lastServiceDate.getText().trim());

            if (x == 'A') {
                // Add new vehicle
                VehicleCRUD.createVehicle(vehicle);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Vehicle added successfully.");
            } else if (x == 'U') {
                // Update existing vehicle
                int id = (int) vehicleTable.getValueAt(vehicleTable.getSelectedRow(), 0);
                vehicle.setId(id); // Set the ID of the vehicle to update
                VehicleCRUD.updateVehicle(id, vehicle);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Vehicle updated successfully.");
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Invalid action type.");
            }

            // Refresh the table and close the dialog
            tblupdate();
            vehicleDialog.dispose();
        } catch (Exception e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Error: " + e.getMessage());
        }


    }//GEN-LAST:event_saveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JTextField brand;
    private javax.swing.JTextField capacity;
    private javax.swing.JTextField chassisNumber;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastServiceDate;
    private javax.swing.JTextField licensePlate;
    private javax.swing.JTextField model;
    private javax.swing.JButton save;
    private javax.swing.JTextField status;
    private javax.swing.JTextField type;
    private javax.swing.JDialog vehicleDialog;
    private javax.swing.JTable vehicleTable;
    // End of variables declaration//GEN-END:variables
}
