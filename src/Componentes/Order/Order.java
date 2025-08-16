package Componentes.Order;

import Componentes.Customers.Customer;
import Componentes.Packages.Package;

public class Order {

    private int orderID;
    private Customer customer;
    private Package packagepdms;
    private  String Status;
    private  String DeleveryDate;
    private String OrderDate;
    private float TotalCost;

    public Order(int OrderID, Customer customer, Package packagepdms, String Status, String OrderDate, float TotalCost) {
        this.orderID = OrderID;
        this.customer = customer;
        this.packagepdms = packagepdms;
        this.Status = Status;
        this.OrderDate = OrderDate;
        this.TotalCost = TotalCost;
    }
    public Order(String Status, String OrderDate) {
        this.Status = Status;
        this.OrderDate = OrderDate;
    }

    public void setDeleveryDate(String DeleveryDate) {
        this.DeleveryDate = DeleveryDate;
    }

    public String getDeleveryDate() {
        return DeleveryDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int OrderID) {
        this.orderID = OrderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Package getPackagepdms() {
        return packagepdms;
    }

    public void setPackagepdms(Package packagepdms) {
        this.packagepdms = packagepdms;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public float getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(float TotalCost) {
        this.TotalCost = TotalCost;
    }

    
    
}
