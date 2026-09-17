package App.Domain;

public class Center {

    private String centerId;
    private String name;
    private String address;

    //constructor
    public Center(String centerId, String name, String address) {
        this.centerId = centerId;
        this.name = name;
        this.address = address;
    }

    public String getCenterId() {
        return centerId;
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCenterId(String centerId) {

    }

    @Override
    public String toString() {
        return "Center{" +
                "centerId='" + centerId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
