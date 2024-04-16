
package za.ac.cput.studentenrolmentsystem;

import java.io.Serializable;

/**
 *
 * @author Leonard
 */
public class User implements Serializable
{
    private int usersId;
    private String usersName;
    private String usersLastName;
    private String usersPassword;
    private String role;

    public User() 
    {
    }

    public User(int usersId, String usersName, String usersLastName, String usersPassword, String role) {
        this.usersId = usersId;
        this.usersName = usersName;
        this.usersLastName = usersLastName;
        this.usersPassword = usersPassword;
        this.role = role;
    }

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUsersLastName() {
        return usersLastName;
    }

    public void setUsersLastName(String usersLastName) {
        this.usersLastName = usersLastName;
    }
    
    public String getUsersPassword() {
        return usersPassword;
    }

    public void setUsersPassword(String usersPassword) {
        this.usersPassword = usersPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "usersId : " + usersId + ", usersName : " + usersName + ", usersLastName : " + usersLastName + ", usersPassword : " + usersPassword + ", role : " + role + '}';
    }
    
    

    
    
}
