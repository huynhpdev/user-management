package huynhp.tbl_Promotion;

import huynhp.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MSI
 */
public class Tbl_Promotion_DAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    List<Tbl_Promotion_DTO> promotionList;

    public List<Tbl_Promotion_DTO> getPromotionList() {
        return promotionList;
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

    public void getAllPromotion() throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT promotionID, promotionUser.userID, rank "
                        + "FROM tbl_Promotion as promotionUser "
                        + "INNER JOIN tbl_User as inputUser ON promotionUser.userID = inputUser.userID AND inputUser.statusID = 0";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Tbl_Promotion_DTO dto = new Tbl_Promotion_DTO(rs.getInt("promotionID"),
                            rs.getString("userID"),
                            rs.getInt("rank"));

                    if (this.promotionList == null) {
                        this.promotionList = new ArrayList<>();
                    }

                    this.promotionList.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public boolean checkExistedPromotionByUserID(String userID) throws NamingException, SQLException {
        int isExist = 0;
        con = DBHelper.getConnect();
        try {
            if (con != null) {
                String sql = "SELECT COUNT(userID) as existedPromotion "
                        + "FROM tbl_Promotion "
                        + "WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();

                if (rs.next()) {
                    isExist = rs.getInt("existedPromotion");
                }

                if (isExist > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public boolean checkExistedPromotionByPromotionID(int promotionID) throws NamingException, SQLException {
        int isExist = 0;
        con = DBHelper.getConnect();
        try {
            if (con != null) {
                String sql = "SELECT COUNT(userID) as existedPromotion "
                        + "FROM tbl_Promotion "
                        + "WHERE promotionID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, promotionID);
                rs = stm.executeQuery();

                if (rs.next()) {
                    isExist = rs.getInt("existedPromotion");
                }

                if (isExist > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public boolean addPromotion(String userID) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "INSERT INTO tbl_Promotion (userID, rank) "
                        + "VALUES (?,5)";
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

    public boolean updatePromotion(int promotionID, int rank) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "Update tbl_Promotion "
                        + "SET rank = ? "
                        + "Where promotionID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, rank);
                stm.setInt(2, promotionID);
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

    public boolean deletePromotion(int promotionID) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "Delete From tbl_Promotion "
                        + "Where promotionID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, promotionID);

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
