/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.tbl_Promotion;

import java.io.Serializable;

/**
 *
 * @author MSI
 */
public class Tbl_Promotion_DTO implements Serializable {

    private int promotionID, rank;
    private String userID;

    public Tbl_Promotion_DTO() {
    }

    public Tbl_Promotion_DTO(int promotionID, String userID, int rank) {
        this.promotionID = promotionID;
        this.userID = userID;
        this.rank = rank;
    }

    public int getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
