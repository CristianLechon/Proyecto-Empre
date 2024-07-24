package ec.edu.uce.model;

public class Client {
    private String name;
    private String lastName;
    private String plate;
    private String CI;

    public Client(String name, String lastName, String plate, String CI) {
        this.name = name;
        this.lastName = lastName;
        this.plate = plate;
        this.CI = CI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getCI() {
        return CI;
    }

    public void setCI(String CI) {
        this.CI = CI;
    }
}


