/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.tbl_User;

import huynhp.utils.DBHelper;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author MSI
 */
public class Tbl_User_DAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    List<Tbl_User_DTO> userList;

    public List<Tbl_User_DTO> getUserList() {
        return userList;
    }

    private void closeDB() throws NamingException, SQLException {
        if (rs != null) {
            rs.close();
        }

        if (stm != null) {
            stm.close();
        }

        if (con != null) {
            con.close();
        }
    }

    public Tbl_User_DTO checkLogin(String userID, String password) throws NamingException, 
            SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT userID, username, roleID, statusID, email, photo, phone, rolename "
                        + "FROM tbl_User "
                        + "WHERE userID = ? AND password = ? AND statusID = 0";

                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, DBHelper.encryptPass(password));
                rs = stm.executeQuery();

                if (rs.next()) {
                    return new Tbl_User_DTO(rs.getString("userID"),
                            rs.getString("username"),
                            rs.getString("email"),
                            DBHelper.parseBase64StringToFile(rs.getBytes("photo")),
                            rs.getString("phone"),
                            rs.getInt("roleID"),
                            rs.getInt("statusID"),
                            rs.getString("rolename"));
                }
            }
        } finally {
            closeDB();
        }
        return null;
    }

    public boolean checkAccountStatus(String userID) throws NamingException, SQLException {
        try {
            int isActive = 0;
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT COUNT(userID) as countUser "
                        + "FROM tbl_User "
                        + "WHERE userID = ? AND statusID = 0";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    isActive = rs.getInt("countUser");
                }

                if (isActive > 0) {
                    return true;
                }
            }

        } finally {
            closeDB();
        }
        return false;
    }

    public List<String> findQuantityRole() throws NamingException, SQLException {
        List<String> roleName = null;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT rolename "
                        + "FROM tbl_User";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    if (roleName == null) {
                        roleName = new ArrayList<>();
                        roleName.add(rs.getString("rolename"));
                    } else {
                        String roleNameSql = rs.getString("rolename");
                        if (!roleName.contains(roleNameSql)) {
                            roleName.add(roleNameSql);
                        }
                    }
                }
            }
        } finally {
            closeDB();
        }
        return roleName;
    }

    public Tbl_User_DTO getAccount(String userID) throws NamingException, SQLException, UnsupportedEncodingException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT userID, username, roleID, statusID, email, photo, phone, rolename "
                        + "FROM tbl_User "
                        + "WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();

                if (rs.next()) {
                    return new Tbl_User_DTO(rs.getString("userID"),
                            rs.getString("username"),
                            rs.getString("email"),
                            DBHelper.parseBase64StringToFile(rs.getBytes("photo")),
                            rs.getString("phone"),
                            rs.getInt("roleID"),
                            rs.getInt("statusID"),
                            rs.getString("rolename"));
                }
            }
        } finally {
            closeDB();
        }
        return null;
    }

    public List<Tbl_User_DTO> getNormalAccounts() throws NamingException, SQLException, UnsupportedEncodingException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT userID, username, roleID, statusID, email, photo, phone, rolename "
                        + "FROM tbl_User "
                        + "WHERE statusID = 0 AND roleID != 0";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                List<Tbl_User_DTO> userDTO = new ArrayList();
                while (rs.next()) {
                    userDTO.add(new Tbl_User_DTO(rs.getString("userID"),
                            rs.getString("username"),
                            rs.getString("email"),
                            DBHelper.parseBase64StringToFile(rs.getBytes("photo")),
                            rs.getString("phone"),
                            rs.getInt("roleID"),
                            rs.getInt("statusID"),
                            rs.getString("rolename")));

                }
                if (userDTO.size() > 0) {
                    return userDTO;
                }
            }
        } finally {
            closeDB();
        }
        return null;
    }

    public void getAllUsersAccount() throws NamingException, SQLException, UnsupportedEncodingException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT userID, username, roleID, statusID, email, photo, phone, rolename "
                        + "FROM tbl_User "
                        + "WHERE statusID = 0";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    Tbl_User_DTO dto = new Tbl_User_DTO(rs.getString("userID"),
                            rs.getString("username"),
                            rs.getString("email"),
                            DBHelper.parseBase64StringToFile(rs.getBytes("photo")),
                            rs.getString("phone"),
                            rs.getInt("roleID"),
                            rs.getInt("statusID"),
                            rs.getString("rolename"));

                    if (this.userList == null) {
                        this.userList = new ArrayList<>();
                    }

                    this.userList.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getUsersAccountByRole(String roleName) throws NamingException, SQLException, UnsupportedEncodingException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                if (roleName.equals("All")) {
                    String sql = "SELECT userID, username, roleID, statusID, email, photo, phone, rolename "
                            + "FROM tbl_User "
                            + "WHERE statusID = 0";
                    stm = con.prepareStatement(sql);
                } else {
                    String sql = "SELECT userID, username, roleID, statusID, email, photo, phone, rolename "
                            + "FROM tbl_User "
                            + "WHERE roleName = ? AND statusID = 0";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, roleName);
                }

                rs = stm.executeQuery();

                while (rs.next()) {

                    Tbl_User_DTO dto = new Tbl_User_DTO(rs.getString("userID"),
                            rs.getString("username"),
                            rs.getString("email"),
                            DBHelper.parseBase64StringToFile(rs.getBytes("photo")),
                            rs.getString("phone"),
                            rs.getInt("roleID"),
                            rs.getInt("statusID"),
                            rs.getString("rolename"));

                    if (this.userList == null) {
                        this.userList = new ArrayList<>();
                    }

                    this.userList.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getUsersAccount(String searchValue, String roleName) throws NamingException, SQLException, UnsupportedEncodingException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                if (roleName.equals("All")) {
                    String sql = "SELECT userID, username, roleID, statusID, email, photo, phone, rolename "
                            + "FROM tbl_User "
                            + "WHERE username LIKE ? AND statusID = 0";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, "%" + searchValue + "%");
                } else {
                    String sql = "SELECT userID, username, roleID, statusID, email, photo, phone, rolename "
                            + "FROM tbl_User "
                            + "WHERE username LIKE ? AND roleName = ? AND statusID = 0";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, "%" + searchValue + "%");
                    stm.setString(2, roleName);
                }
                rs = stm.executeQuery();

                while (rs.next()) {
                    Tbl_User_DTO dto = new Tbl_User_DTO(rs.getString("userID"),
                            rs.getString("username"),
                            rs.getString("email"),
                            DBHelper.parseBase64StringToFile(rs.getBytes("photo")),
                            rs.getString("phone"),
                            rs.getInt("roleID"),
                            rs.getInt("statusID"),
                            rs.getString("rolename"));

                    if (this.userList == null) {
                        this.userList = new ArrayList<>();
                    }

                    this.userList.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public boolean updateAccountByNormalAccount(String userID, String username,
            String email, String phone) throws NamingException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE tbl_User "
                        + "SET username = ? ,email = ? ,phone = ? "
                        + "WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, email);
                stm.setString(3, phone);
                stm.setString(4, userID);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public boolean updateAccountByNormalAccount(String userID, byte[] photo) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE tbl_User "
                        + "SET photo = ? "
                        + "WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setBytes(1, photo);
                stm.setString(2, userID);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }
    
    public boolean updateAccountByNormalAccount(String userID, String password) throws NamingException, SQLException, 
            NoSuchAlgorithmException, UnsupportedEncodingException{
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE tbl_User "
                        + "SET password = ? "
                        + "WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, DBHelper.encryptPass(password));
                stm.setString(2, userID);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public boolean updateAccountByAdminRole(String userID, String username, 
            String email, String phone, String roleName) throws NamingException, SQLException, 
            NoSuchAlgorithmException, UnsupportedEncodingException {
        int roleID = 0;
        if (roleName.equalsIgnoreCase("Employee")) {
            roleID = 1;
        } else if (!roleName.equalsIgnoreCase("Admin")) {
            roleID = 2;
        }
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE tbl_User "
                        + "SET username = ? ,email = ? ,phone = ? ,roleID = ? ,rolename = ? "
                        + "WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, email);
                stm.setString(3, phone);
                stm.setInt(4, roleID);
                stm.setString(5, roleName);
                stm.setString(6, userID);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }
    
    public boolean updateAccountByAdminRole(String userID, String password) throws NamingException, SQLException, 
            NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE tbl_User "
                        + "SET password = ? "
                        + "WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, DBHelper.encryptPass(password));
                stm.setString(2, userID);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public boolean updateAccountByAdminRole(String userID, byte[] photo) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE tbl_User "
                        + "SET photo = ? "
                        + "WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setBytes(1, photo);
                stm.setString(2, userID);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public boolean deleteAccount(String userID) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE tbl_User "
                        + "SET statusID = 1 "
                        + "WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public boolean checkExistedAccount(String userID) throws NamingException, SQLException {
        try {
            int isActive = 0;
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT COUNT(userID) as existedUser "
                        + "FROM tbl_User "
                        + "WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    isActive = rs.getInt("existedUser");
                }

                if (isActive > 0) {
                    return true;
                }
            }

        } finally {
            closeDB();
        }
        return false;
    }

    public boolean insertAccount(String userID, String password, String roleName,
            int roleID, int statusID, String username,
            String email, String phone, byte[] photo) throws NamingException, SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "INSERT INTO tbl_User(userID, roleID, statusID, rolename, username, password, email, photo, phone) "
                        + "VALUES (?,?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setInt(2, roleID);
                stm.setInt(3, statusID);
                stm.setString(4, roleName);
                stm.setString(5, username);
                stm.setString(6, DBHelper.encryptPass(password));
                stm.setString(7, email);
                stm.setBytes(8, photo);
                stm.setString(9, phone);

                int row = stm.executeUpdate();

                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

}
