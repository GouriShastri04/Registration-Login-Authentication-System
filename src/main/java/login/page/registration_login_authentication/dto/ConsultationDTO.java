package login.page.registration_login_authentication.dto;

public class ConsultationDTO {

    private String name;
    private String email;
    private String company;
    private String phone;
    private String preferredDate;  // Format: dd-MM-yyyy
    private String preferredTime;  // Format: HH:mm
    private String message;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public String getPhone() {
        return phone;
    }

    public String getPreferredDate() {
        return preferredDate;
    }

    public String getPreferredTime() {
        return preferredTime;
    }

    public String getMessage() {
        return message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPreferredDate(String preferredDate) {
        this.preferredDate = preferredDate;
    }

    public void setPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
