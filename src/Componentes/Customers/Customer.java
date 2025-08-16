package Componentes.Customers;

import Componentes.Order.Order;
import java.util.ArrayList;
public class Customer {

private int CustomerID;
    private String FirstName;
    private String LastName;
    private String Gender;
    private String Phone;
    private String DateOfBirth;
    private String Address;
    private ArrayList<Order> Order;

    public Customer(int CustomerID, String FirstName, String LastName, String Gender, String Phone, String DateOfBirth, String Address) {
        this.CustomerID = CustomerID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Gender = Gender;
        this.Phone = Phone;
        this.DateOfBirth = DateOfBirth;
        this.Address = Address;
    }

    public Customer(int CustomerID, String FirstName, String LastName, String Phone) {
        this.CustomerID = CustomerID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Phone = Phone;
    }

    public Customer(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    
    
    

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
    
    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String Date) {
        this.DateOfBirth = Date;
    }

    public ArrayList<Order> getOrder() {
        return Order;
    }

    public void setOrder(ArrayList<Order> Order) {
        this.Order = Order;
    }
    
    
    @Override //for compobox fill
public String toString() {
    return getFirstName() + " " + getLastName();
}

}


