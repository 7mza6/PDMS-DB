package PDMS.application.form;

import Componentes.DeliveryStaff.DStaffDashboard;
import static Componentes.Users.PasswordHasher.hashPassword;
import Componentes.Users.User;
import Componentes.Users.UserCRUDS;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import PDMS.application.Application;
import PDMS.menu.MenuItem;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import raven.toast.Notifications;

/**
 *
 * @author PDMS
 */
public class LoginForm extends javax.swing.JPanel {
    private static ArrayList<User> list;

    public static String x;
    public LoginForm() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("al center center"));

        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
        
        txtPass.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0");
        txtUser.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "User Name");
        txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin1 = new PDMS.application.form.PanelLogin();
        lbTitle = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lbPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        Invailed = new javax.swing.JLabel();
        cmdLogin = new javax.swing.JButton();

        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Login");
        panelLogin1.add(lbTitle);

        lbUser.setText("User Name");
        panelLogin1.add(lbUser);
        panelLogin1.add(txtUser);

        lbPass.setText("Password");
        panelLogin1.add(lbPass);
        panelLogin1.add(txtPass);

        Invailed.setForeground(new java.awt.Color(255, 0, 0));
        panelLogin1.add(Invailed);

        cmdLogin.setText("Login");
        cmdLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLoginActionPerformed(evt);
            }
        });
        panelLogin1.add(cmdLogin);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(218, Short.MAX_VALUE)
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(197, 197, 197))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLoginActionPerformed
      
       Invailed.setText(""); 
        list = UserCRUDS.readUsers();
        for (User user : list) {
    System.out.println(user);
}

        String hash = hashPassword(txtPass.getText());
        String passwordByName = getPasswordByName(list,txtUser.getText());
        String Role = UserCRUDS.getRoleByName(txtUser.getText());
        DStaffDashboard.ID = getIDByName(list,txtUser.getText());
        System.out.println(DStaffDashboard.ID);
        MainForm.x = Role.charAt(0);
        MenuItem.x = Role.charAt(0);
        if(hash.equals(passwordByName)){
            System.out.println("okrrr");
            MainForm.menu.setMenuItems(Role.charAt(0));
            MainForm.menu.init();
            MainForm.initMenuEvent();
            MainForm.menu.setSelected(0, 0);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Hello " + txtUser.getText());
            Application.login();
        } else{
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Invailed  User Name or Password");
            Invailed.setText(" Invailed  User Name or Password");
        }
    }//GEN-LAST:event_cmdLoginActionPerformed

        public  String getPasswordByName(List<User> list, String name) {
        for (User u : list) {
            if (u.getUsername().equals(name)) {
                return u.getPassword();
            }
        }
        Invailed.setText("Invailed User");
        return null;
    }
        
        public  int getIDByName(List<User> list, String name) {
        for (User u : list) {
            if (u.getUsername().equals(name)) {
                return u.getId();
            }
        }
        Invailed.setText("Invailed User");
        return -1;
    }
       
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Invailed;
    private javax.swing.JButton cmdLogin;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUser;
    private PDMS.application.form.PanelLogin panelLogin1;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}