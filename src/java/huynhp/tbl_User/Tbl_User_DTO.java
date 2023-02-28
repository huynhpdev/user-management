/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.tbl_User;

import java.io.Serializable;

/**
 *
 * @author MSI
 */
public class Tbl_User_DTO implements Serializable {
    private String userID, username, email, phone, photo, roleName;
    int roleID, statusID;

    public Tbl_User_DTO() {
        
    }

    public Tbl_User_DTO(String userID, String username, String email, String photo, String phone, int roleID, int statusID, String roleName) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.photo = photo;
        this.phone = phone;
        this.roleID = roleID;
        this.statusID = statusID;
        this.roleName = roleName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    } 

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
}
