package ec.edu.uce.model;

public class Dispatcher {
    private String lastName;
    private  String username;
    private String password;


    public Dispatcher(String username, String password, String lastName) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
