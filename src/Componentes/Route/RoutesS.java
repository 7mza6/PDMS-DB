package Componentes.Route;

import Componentes.Cities.CitiesCRUDS;
import Componentes.Cities.CitiesClass;
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
public class RoutesS extends javax.swing.JPanel {

    public RoutesS() {
        initComponents();
        tblUpdate();
        routeTable.getColumnModel().getColumn(routeTable.getColumnCount() - 1).setCellRenderer(new TableActionCellRender(false,false));
        loadTableEvents();
        routeTable.getColumnModel().getColumn(routeTable.getColumnCount() - 1).setCellEditor(new TableActionCellEditor(loadTableEvents(), false));
        GetDataToComboBox();
    }

    private char x; // Used to identify action type ('U' for update, etc.)

    private TableActionEvent loadTableEvents() {
        return new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                routeDialog.setTitle("Update Route");
                routeDialog.setBounds(600, 360, 660, 380);
                showRouteDataByID((int) routeTable.getValueAt(row, 0));
                routeDialog.setVisible(true);
                x = 'U';
            }

            @Override
            public void onDelete(int row) {
                int id = (int) routeTable.getValueAt(row, 0);
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this route?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    RouteCRUD.deleteRoute(id);
                    tblUpdate();
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Route deleted successfully.");
                }
            }

            @Override
            public void onView(int row) {
            }
             public void onAssign(int row) {
                
            }
        };
    }

    private void tblUpdate() {
        ArrayList<Route> routeList = RouteCRUD.readRoutes();

        DefaultTableModel routeModel = (DefaultTableModel) routeTable.getModel();
        routeModel.setRowCount(0);

        for (Route route : routeList) {
            int id = route.getId();
            String origin = route.getOrigin();
            String destination = route.getDestination();
            float distance = route.getDistance();
            String estimatedTravelTime = route.getEstimatedTravelTime();
            routeModel.addRow(new Object[]{id, origin, destination, distance, estimatedTravelTime});
        }
        Table_style(routeTable);
    }

    public void GetDataToComboBox() {
        for (CitiesClass city : CitiesCRUDS.Read()) {
            cityName.addItem(city.getCityName());
        }
    }

    private void showRouteDataByID(int id) {
        try {
            // Fetch the route data using the ID
            Route route = RouteCRUD.readRoute(id);
            if (route != null) {
                // Populate the input fields with the retrieved data
                destination.setText(route.getDestination());
                distance.setText(String.valueOf(route.getDistance()));
                cityName.setSelectedItem(route.getOrigin());
                estimatedTravelTime.setText(route.getEstimatedTravelTime());
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "No route found with the given ID.");
            }
        } catch (Exception ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "An error occurred: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        routeDialog = new javax.swing.JDialog();
        destination = new javax.swing.JTextField();
        distance = new javax.swing.JTextField();
        cityName = new javax.swing.JComboBox<>();
        estimatedTravelTime = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Save = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        routeTable = new javax.swing.JTable();
        add = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        cityName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityNameActionPerformed(evt);
            }
        });

        jLabel1.setText("Destination");

        jLabel2.setText("Distance");

        jLabel3.setText("City");

        jLabel4.setText("Estimated Travel Time");

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout routeDialogLayout = new javax.swing.GroupLayout(routeDialog.getContentPane());
        routeDialog.getContentPane().setLayout(routeDialogLayout);
        routeDialogLayout.setHorizontalGroup(
            routeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(routeDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(routeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(routeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(destination)
                    .addComponent(distance)
                    .addComponent(estimatedTravelTime)
                    .addComponent(cityName, 0, 281, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, routeDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Save)
                .addContainerGap())
        );
        routeDialogLayout.setVerticalGroup(
            routeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(routeDialogLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(routeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(routeDialogLayout.createSequentialGroup()
                        .addGroup(routeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(destination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(routeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(distance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(routeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cityName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(estimatedTravelTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(Save)
                .addContainerGap())
        );

        routeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Origin", "Destination", "Distance", "Estimated Travel Time", "Actions"
            }

        ));
        routeTable.setRowHeight(40);
        jScrollPane1.setViewportView(routeTable);

        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Routes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(add)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cityNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityNameActionPerformed


    }//GEN-LAST:event_cityNameActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
routeDialog.setTitle("Update Route");
                routeDialog.setBounds(600, 360, 660, 380);
                routeDialog.setVisible(true);
                x = 'A';

    }//GEN-LAST:event_addActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        try {
            String destinationText = destination.getText();
            float distanceValue = Float.parseFloat(distance.getText());
            String citySelected = (String) cityName.getSelectedItem();
            String estimatedTime = estimatedTravelTime.getText();

            if (x == 'A') { // Add new route
                Route newRoute = new Route(
                        0, // Assuming 0, ID will be auto-assigned in CRUD
                        citySelected,
                        destinationText,
                        distanceValue,
                        new CitiesClass(citySelected),
                        estimatedTime
                );
                RouteCRUD.createRoute(newRoute);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "New route added successfully.");
            } else if (x == 'U') { // Update existing route
                int selectedRow = routeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) routeTable.getValueAt(selectedRow, 0);
                    Route updatedRoute = new Route(
                            id,
                            citySelected,
                            destinationText,
                            distanceValue,
                            new CitiesClass(citySelected),
                            estimatedTime
                    );
                    RouteCRUD.updateRoute(id, updatedRoute);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Route updated successfully.");
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "No row selected for update.");
                    return;
                }
            }
            tblUpdate(); // Refresh table after add/update
            routeDialog.setVisible(false); // Close dialog
        } catch (NumberFormatException ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Invalid distance value.");
        } catch (Exception ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "An error occurred while saving: " + ex.getMessage());
        }
    }//GEN-LAST:event_SaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Save;
    private javax.swing.JButton add;
    private javax.swing.JComboBox<String> cityName;
    private javax.swing.JTextField destination;
    private javax.swing.JTextField distance;
    private javax.swing.JTextField estimatedTravelTime;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JDialog routeDialog;
    private javax.swing.JTable routeTable;
    // End of variables declaration//GEN-END:variables
}
