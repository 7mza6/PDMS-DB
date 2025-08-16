package Componentes.DeliveryStaff;

import Componentes.Order.Order;
import Componentes.Users.User;
import java.util.ArrayList;

public class DeliveryStaff extends User {
    private ArrayList<Order> assignedOrders = new ArrayList<>();
    private int vehicleId;
    private int roatId;
    private int scheduleId;

    public DeliveryStaff(int vehicleId, int roatId, int id, String firstName, String lastName, String dateOfBirth, String gender, String address, String celPhone, String telPhone, String email, String username, String password, String role) {
        super(id, firstName, lastName, dateOfBirth, gender, address, celPhone, telPhone, email, username, password, role);
        this.vehicleId = vehicleId;
        this.roatId = roatId;
    }

    public DeliveryStaff(int id, String firstName, String lastName, String dateOfBirth, String gender, String address, String celPhone, String telPhone, String email, String username, String password, String role) {
        super(id, firstName, lastName, dateOfBirth, gender, address, celPhone, telPhone, email, username, password, role);
    }
    
    
    public ArrayList<Order> getAssignedOrders() {
        
        return assignedOrders;
    }

    public void setAssignedOrders(ArrayList<Order> assignedOrders) {
        this.assignedOrders = assignedOrders;
    }

    public int getVehichleId() {
        return vehicleId;
    }

    public void setVehichleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getRoatId() {
        return roatId;
    }

    public void setRoatId(int roatId) {
        this.roatId = roatId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    // Add an order to the assignedOrders list
    
    
    

}
