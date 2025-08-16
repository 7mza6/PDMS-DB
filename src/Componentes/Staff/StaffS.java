package Componentes.Staff;

import Componentes.Branchs.BranchClass;
import Componentes.Branchs.BranchsCRUDS;
import static Componentes.Branchs.BranchsCRUDS.getBranchNameById;
import Componentes.Users.UserCRUDS;
import static Componentes.Users.UserCRUDS.readUsers;
import static PDMS.theme.theams.Table_style;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import raven.cellButton.TableActionCellEditor;
import raven.cellButton.TableActionCellRender;
import raven.cellButton.TableActionEvent;
import raven.datetime.component.date.DatePicker;
import raven.toast.Notifications;

public class StaffS extends javax.swing.JPanel {

    public StaffS() {
        initComponents();
        Users.getColumnModel().getColumn(11).setCellRenderer(new TableActionCellRender(false,false));
        GetDataToComboBox();
        loadTableEvents();

        Users.getColumnModel().getColumn(11).setCellEditor(new TableActionCellEditor(loadTableEvents(),false));
        updateTable();
    }
    private char x;

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
        OrdersD.setTitle("Update User");
        OrdersD.setBounds(600, 360, 660, 380);
        OrdersD.setVisible(true);
    }

    private void onDeleteEvent(int row) {
        int id = (int) Users.getValueAt(row, 0);
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this Staff?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    StaffCRUDS.deleteStaffById(id);
                    updateTable();
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Staff deleted successfully.");
                }   
                
    }

    private void onEditEvent(int row) {
        addUserD.setTitle("Update User");
        addUserD.setBounds(618, 382, 684, 400);
        addUserD.setVisible(true);
        showUpdateDialogAction((int) Users.getValueAt(Users.getSelectedRow(), 0));
        x = 'U';
    }

    private void updateTable() {
    try {
        ArrayList<Staff> staffList = StaffCRUDS.readStaff();
        DefaultTableModel userModel = (DefaultTableModel) Users.getModel();
        userModel.setRowCount(0);

        for (Staff staff : staffList) {
            int id = staff.getId();
            String firstName = staff.getFirstName();
            String lastName = staff.getLastName();
            String dateOfBirth = staff.getDateOfBirth();
            String gender = staff.getGender();
            String address = staff.getAddress();
            String celPhone = staff.getCelPhone();
            String telPhone = staff.getTelPhone();
            String email = staff.getEmail();
            String username = staff.getUsername();
            String role = staff.getRole();
            int branchId = staff.getBranchId();
            String branchName = getBranchNameById(branchId);

            userModel.addRow(new Object[]{
                id, firstName + " " + lastName, dateOfBirth, gender, address, celPhone,
                telPhone, email, username, role, branchName
            });

            Table_style(Users);
            Table_style(Orders);
        }
    } catch (Exception e) {
        System.out.println("Error updating table: " + e.getMessage());
    }
}


  

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[0-9]{10}$";
        return phoneNumber.matches(phoneRegex);
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}";
        return password.matches(passwordRegex);
    }

    private void resetFields() {
        firstName.setText("");
        lastName.setText("");
        dateOfBirth.setText("");
        gender.setSelectedIndex(0);
        address.setText("");
        celPhone.setText("");
        telPhone.setText("");
        email.setText("");
        username.setText("");
        password.setText("");
        role.setSelectedIndex(0);
        branch.setSelectedIndex(-1);
    }

    public void showUpdateDialogAction(int id) {
        Staff staff = StaffCRUDS.getStaffById(id);

        if (staff != null) {
            firstName.setText(staff.getFirstName());
            lastName.setText(staff.getLastName());
            dateOfBirth.setText(staff.getDateOfBirth());
            gender.setSelectedItem(staff.getGender());
            address.setText(staff.getAddress());
            celPhone.setText(staff.getCelPhone());
            telPhone.setText(staff.getTelPhone());
            email.setText(staff.getEmail());
            username.setText(staff.getUsername());
            role.setSelectedItem(staff.getRole());
            branch.setSelectedItem(getBranchNameById(staff.getBranchId()));
        } else {
            JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void GetDataToComboBox() {
        
        for (BranchClass Branch : BranchsCRUDS.Read()) {
            branch.addItem(Branch);
        }
        AutoCompleteDecorator.decorate(branch);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OrdersD = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        Orders = new javax.swing.JTable();
        addUserD = new javax.swing.JDialog();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        username = new javax.swing.JTextField();
        rePassword = new javax.swing.JPasswordField();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();
        role = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        branch = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        celPhone = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        telPhone = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        gender = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        firstName = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        dateOfBirth = new javax.swing.JFormattedTextField();
        DatePicker datePicker=new DatePicker();
        datePicker.setEditor(dateOfBirth);
        lastName = new javax.swing.JTextField();
        Save = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Users = new javax.swing.JTable();
        Add = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        Orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Traking Number", "Status", "Expected Deleviry Date"
            }
        ));
        jScrollPane2.setViewportView(Orders);

        javax.swing.GroupLayout OrdersDLayout = new javax.swing.GroupLayout(OrdersD.getContentPane());
        OrdersD.getContentPane().setLayout(OrdersDLayout);
        OrdersDLayout.setHorizontalGroup(
            OrdersDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
        );
        OrdersDLayout.setVerticalGroup(
            OrdersDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
        );

        addUserD.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                addUserDWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                addUserDWindowClosing(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel18.setText("Pearsonal Information");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel19.setText("Login Information");

        jLabel12.setText("Conferm Password :");

        jLabel9.setText("User Name");

        jLabel10.setText("Password :");

        jLabel11.setText("Role");

        role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Staff", "Delivery Staff" }));
        role.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleActionPerformed(evt);
            }
        });

        jLabel20.setText("Branch");

        branch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(password)
                            .addComponent(rePassword)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(jLabel20))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(branch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(username)
                            .addComponent(role, 0, 166, Short.MAX_VALUE))))
                .addGap(72, 72, 72))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(branch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(role, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(rePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel13.setText("Cell Phone");

        jLabel14.setText("Tell Phone");

        jLabel15.setText("Email");

        gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        gender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genderActionPerformed(evt);
            }
        });

        jLabel16.setText("FirstName");

        firstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameActionPerformed(evt);
            }
        });

        jLabel17.setText("LastName");

        jLabel23.setText("DateOfBirth");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
        );

        jLabel24.setText("Gender");

        jLabel25.setText("Address");

        dateOfBirth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateOfBirthActionPerformed(evt);
            }
        });

        lastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addGap(73, 73, 73)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lastName)
                            .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel15))))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(telPhone)
                            .addComponent(celPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(email)
                            .addComponent(gender, 0, 159, Short.MAX_VALUE)
                            .addComponent(address)
                            .addComponent(dateOfBirth))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel23))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(celPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(telPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addUserDLayout = new javax.swing.GroupLayout(addUserD.getContentPane());
        addUserD.getContentPane().setLayout(addUserDLayout);
        addUserDLayout.setHorizontalGroup(
            addUserDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addUserDLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(addUserDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(addUserDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addUserDLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(addUserDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addUserDLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel19))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addUserDLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Save)
                        .addGap(69, 69, 69))))
        );
        addUserDLayout.setVerticalGroup(
            addUserDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addUserDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addUserDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addUserDLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(addUserDLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Save)
                        .addGap(18, 18, 18))))
        );

        Users.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Age", "Gender", "Address", "CelPhone", "TelPhone", "Email", "Username", "Roll", "Branch", "Actions"
            }
        ));
        Users.setRowHeight(40);
        Users.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UsersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Users);
        if (Users.getColumnModel().getColumnCount() > 0) {
            Users.getColumnModel().getColumn(0).setMinWidth(0);
            Users.getColumnModel().getColumn(0).setPreferredWidth(0);
            Users.getColumnModel().getColumn(0).setMaxWidth(0);
            Users.getColumnModel().getColumn(11).setResizable(false);
            Users.getColumnModel().getColumn(11).setPreferredWidth(100);
        }

        Add.setText("Add");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Staff");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Add)
                .addGap(15, 15, 15))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Add))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void UsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UsersMouseClicked
        String role = (String) Users.getValueAt(Users.getSelectedRow(), 8);
        //if ("Delevery Staff".equals(role))
        //View.setVisible(true);
        //  else{
        //     View.setVisible(false);
        //  }
    }//GEN-LAST:event_UsersMouseClicked

    private void roleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roleActionPerformed

    private void genderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_genderActionPerformed

    private void firstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameActionPerformed

    private void dateOfBirthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateOfBirthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateOfBirthActionPerformed

    private void lastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        addUserD.setAlwaysOnTop(true);
        String emailText = email.getText();
        String passwordText = password.getText();
        String rePasswordText = rePassword.getText();
        String phoneNumber = celPhone.getText();

        boolean isValid = true;

        if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || dateOfBirth.getText().isEmpty()
                || emailText.isEmpty() || username.getText().isEmpty() || passwordText.isEmpty()
                || role.getSelectedItem() == null || celPhone.getText().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "All fields must be filled in.");
            isValid = false;
        }

        if (!isValidEmail(emailText)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Invalid email format.");
            isValid = false;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Invalid phone number format.");
            isValid = false;
        }

        if (!isValidPassword(passwordText)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Password must contain at least 8 characters, including one uppercase letter and one number.");
            isValid = false;
        }

        if (!passwordText.equals(rePasswordText)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Password and confirm password do not match.");
            isValid = false;
        }

        if (x == 'C') {
            if (UserCRUDS.isDuplicateUserName(username.getText())) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Username already exists!");
                isValid = false;
            }
        }

        if (isValid) {
            if (x == 'U') {
                int id = (int) Users.getValueAt(Users.getSelectedRow(), 0);
                Staff updatedStaff = new Staff(
                        id,
                        firstName.getText(),
                        lastName.getText(),
                        dateOfBirth.getText(),
                        (String) gender.getSelectedItem(),
                        address.getText(),
                        celPhone.getText(),
                        telPhone.getText(),
                        emailText,
                        username.getText(),
                        passwordText,
                        (String) role.getSelectedItem(),
                        ((BranchClass) branch.getSelectedItem()).getBranchNumber()
                );
                StaffCRUDS.updateStaffById(id, updatedStaff);
            } else if (x == 'C') {
                Staff newStaff = new Staff(
                        -1,
                        firstName.getText(),
                        lastName.getText(),
                        dateOfBirth.getText(),
                        (String) gender.getSelectedItem(),
                        address.getText(),
                        celPhone.getText(),
                        telPhone.getText(),
                        emailText,
                        username.getText(),
                        password.getText(),
                        (String) role.getSelectedItem(),
                        ((BranchClass) branch.getSelectedItem()).getBranchNumber());
                newStaff.setId(readUsers().size() > 0 ? readUsers().get(readUsers().size() - 1).getId() + 1 : 1);
                StaffCRUDS.addStaff(newStaff);
            }

            addUserD.setVisible(false);

            resetFields();
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Updated successfully.");
            updateTable();
        }

    }//GEN-LAST:event_SaveActionPerformed

    private void addUserDWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_addUserDWindowClosed

    }//GEN-LAST:event_addUserDWindowClosed

    private void addUserDWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_addUserDWindowClosing
        resetFields();
    }//GEN-LAST:event_addUserDWindowClosing

    private void branchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_branchActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        addUserD.setVisible(true);
        addUserD.setTitle("Add User");
        addUserD.setBounds(618, 382, 684, 400);
        x = 'C';
    }//GEN-LAST:event_AddActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JTable Orders;
    private javax.swing.JDialog OrdersD;
    private javax.swing.JButton Save;
    private javax.swing.JTable Users;
    private javax.swing.JDialog addUserD;
    private javax.swing.JTextField address;
    private javax.swing.JComboBox<BranchClass> branch;
    private javax.swing.JTextField celPhone;
    private javax.swing.JFormattedTextField dateOfBirth;
    private javax.swing.JTextField email;
    private javax.swing.JTextField firstName;
    private javax.swing.JComboBox<String> gender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lastName;
    private javax.swing.JPasswordField password;
    private javax.swing.JPasswordField rePassword;
    private javax.swing.JComboBox<String> role;
    private javax.swing.JTextField telPhone;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
