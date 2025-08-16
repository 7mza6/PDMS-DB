package Componentes.Staff;

import Componentes.Users.User;

public class Staff extends User {
    private int branchId;

    public Staff(int id, String firstName, String lastName, String dateOfBirth, String gender, String address,
                 String celPhone, String telPhone, String email, String username, String password, String role,
                 int branchId) {
        super(id, firstName, lastName, dateOfBirth, gender, address, celPhone, telPhone, email, username, password, role);
        this.branchId = branchId;
    }

    public Staff(int id, String firstName, String lastName, String dateOfBirth, String gender, String address, String celPhone, String telPhone, String email, String username, String password, String role) {
    super(id, firstName, lastName, dateOfBirth, gender, address, celPhone, telPhone, email, username, password, role);
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}
