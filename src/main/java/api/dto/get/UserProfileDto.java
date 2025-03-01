package api.dto.get;

import api.entity.Department;
import api.entity.User;
import java.util.List;

public class UserProfileDto {

    private String email;

    private String firstName;

    private String lastName;

    private Department department;

    private User.Theme theme;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User.Theme getTheme() {
        return theme;
    }

    public void setTheme(User.Theme theme) {
        this.theme = theme;
    }
}
