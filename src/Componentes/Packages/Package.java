package Componentes.Packages;

import Componentes.Branchs.BranchClass;
import Componentes.Customers.Customer;

public class Package {

    
    private long PackageID;
    private float weight ;
    private String ContentDescription;
    private String ReciverName;
    private String ReciverPhone;
    private BranchClass branch;
    private String Location;
    private String Reciveraddress;
    private boolean fragile;
    private Customer customer; // Add a Customer field



    public Package(long PackageID, float weight, String ContentDescription, boolean fragile,String ReciverName, String ReciverPhone,String Location, String Reciveraddress,  BranchClass branch,Customer customer) {
        this.PackageID = PackageID;
        this.weight = weight;
        this.ContentDescription = ContentDescription;
        this.ReciverName = ReciverName;
        this.ReciverPhone = ReciverPhone;
        this.branch = branch;
        this.Reciveraddress = Reciveraddress;
        this.fragile = fragile;
        this.Location=Location;
        this.customer = customer;

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public Package(long PackageID) {
        this.PackageID = PackageID;
        
    }

    

    public long getPackageID() {
        return PackageID;
    }

    public void setPackageID(long PackageID) {
        this.PackageID = PackageID;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getContentDescription() {
        return ContentDescription;
    }

    public void setContentDescription(String ContentDescription) {
        this.ContentDescription = ContentDescription;
    }

    public String getReciverName() {
        return ReciverName;
    }

    public void setReciverName(String ReciverName) {
        this.ReciverName = ReciverName;
    }

    public String getReciverPhone() {
        return ReciverPhone;
    }

    public void setReciverPhone(String ReciverPhone) {
        this.ReciverPhone = ReciverPhone;
    }

   

    public BranchClass getBranch() {
        return branch;
    }

    public void setBranch(BranchClass branch) {
        this.branch = branch;
    }

    public String getReciveraddress() {
        return Reciveraddress;
    }

    public void setReciveraddress(String Reciveraddress) {
        this.Reciveraddress = Reciveraddress;
    }

    public boolean getFragile() {
        return fragile;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    } 
    
}