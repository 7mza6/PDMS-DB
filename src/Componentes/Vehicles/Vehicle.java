package Componentes.Vehicles;

public class Vehicle {
    private int id;
    private String brand;
    private String model;
    private String type;
    private String licensePlate;
    private String chassisNumber;
    private float capacity;
    private String status;
    private String lastServiceDate;

    // Constructor
    public Vehicle(int id, String brand, String model, String type, String licensePlate, 
                   String chassisNumber, float capacity, String status, String lastServiceDate) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.licensePlate = licensePlate;
        this.chassisNumber = chassisNumber;
        this.capacity = capacity;
        this.status = status;
        this.lastServiceDate = lastServiceDate;
    }

    Vehicle() {
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getChassisNumber() { return chassisNumber; }
    public void setChassisNumber(String chassisNumber) { this.chassisNumber = chassisNumber; }

    public float getCapacity() { return capacity; }
    public void setCapacity(float capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLastServiceDate() { return lastServiceDate; }
    public void setLastServiceDate(String lastServiceDate) { this.lastServiceDate = lastServiceDate; }

    @Override
    public String toString() {
        return brand + " - " + licensePlate;
    }
    
    
    
}
