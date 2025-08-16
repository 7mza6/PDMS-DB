package Componentes.Branchs;

public class BranchClass {
    int BranchNumber;
    String BranchName;
    LocationData Locationdata;

    public BranchClass(int BranchNumber, String BranchName, LocationData Locationdata) {
        this.BranchNumber = BranchNumber;
        this.BranchName = BranchName;
        this.Locationdata = Locationdata;
    }

    public int getBranchNumber() {
        return BranchNumber;
    }

    public void setBranchNumber(int BranchNumber) {
        this.BranchNumber = BranchNumber;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public LocationData getLocationdata() {
        return Locationdata;
    }

    public void setLocationdata(LocationData Locationdata) {
        this.Locationdata = Locationdata;
    }

    @Override
    public String toString() {
        return BranchName;
    }
    
    
   
    
    
}
