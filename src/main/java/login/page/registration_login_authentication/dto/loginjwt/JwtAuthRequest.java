package login.page.registration_login_authentication.dto.loginjwt;

public class JwtAuthRequest {
    private String email;
    private String password;
    private String name;
    private String number;

    public JwtAuthRequest() {
    }

    public JwtAuthRequest(String email, String password, String name, String number) {
        this.email = email;
        this.password = password;
        this.name=name;
        this.number=number;
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    
}
