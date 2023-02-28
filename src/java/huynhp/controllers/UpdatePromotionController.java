/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.controllers;

import huynhp.tbl_Promotion.Tbl_Promotion_DAO;
import huynhp.tbl_Promotion.Tbl_Promotion_DTO;
import huynhp.tbl_User.Tbl_User_DAO;
import huynhp.tbl_User.Tbl_User_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;





/**
 *
 * @author MSI
 */
@WebServlet(name = "UpdatePromotionController", urlPatterns = {"/UpdatePromotionController"})
public class UpdatePromotionController extends HttpServlet {

    private final String ERROR_PAGE = "promotionPage.jsp";
    private final String PROMOTION_PAGE = "promotionPage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = ERROR_PAGE;
        String txtUserID = request.getParameter("selectedUserID");
        String txtPromotionID = request.getParameter("selectedPromotionID");
        String txtRank = request.getParameter("txtRank");

        HttpSession session = request.getSession();

        Tbl_User_DAO userDAO = null;
        Tbl_Promotion_DAO promotionDAO = null;
        
        String msg = "";
        try {
            userDAO = new Tbl_User_DAO();
            boolean isActive = userDAO.checkAccountStatus(txtUserID);
            if (isActive) {
                promotionDAO = new Tbl_Promotion_DAO();
                boolean isExistedPromotion = promotionDAO.checkExistedPromotionByPromotionID(Integer.parseInt(txtPromotionID));
                if (isExistedPromotion) {
                    boolean isNumberic = txtRank.chars().allMatch( Character::isDigit );
                    if (isNumberic) {
                        boolean rs = promotionDAO.updatePromotion(Integer.parseInt(txtPromotionID), Integer.parseInt(txtRank));
                        if (rs) {
                            promotionDAO.getAllPromotion();

                            List<Tbl_Promotion_DTO> promotionDTO = promotionDAO.getPromotionList();
                            session.setAttribute("PROMOTIONRESULT", promotionDTO);
                        }
                    } else {
                        msg = "The rank " + txtRank + " is not a number. Please fill out a valid input";
                        request.setAttribute("UPDATEDMESSAGE", msg);
                    }
                } else {
                    promotionDAO = new Tbl_Promotion_DAO();
                    promotionDAO.getAllPromotion();

                    List<Tbl_Promotion_DTO> promotionDTO = promotionDAO.getPromotionList();
                    session.setAttribute("PROMOTIONRESULT", promotionDTO);

                    msg = "The promotionID " + txtPromotionID + " of account "
                            + txtUserID + " is no longer existed";
                    request.setAttribute("UPDATEDMESSAGE", msg);
                }
            } else {
                promotionDAO = new Tbl_Promotion_DAO();
                promotionDAO.getAllPromotion();

                List<Tbl_Promotion_DTO> promotionDTO = promotionDAO.getPromotionList();
                session.setAttribute("PROMOTIONRESULT", promotionDTO);

                List<Tbl_User_DTO> normalAccountResult = userDAO.getNormalAccounts();
                session.setAttribute("NORMALACCOUNTS", normalAccountResult);

                msg = "Account " + txtUserID + " is no longer existed";
                request.setAttribute("UPDATEDMESSAGE", msg);
            }
            String defaultRole = "All";
            session.setAttribute("MARKED", defaultRole);
            url = PROMOTION_PAGE;
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
