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
import huynhp.utils.MyConstants;
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
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private final String INVALID_PAGE = "invalid.html";
    private final String SEARCH_PAGE = "search.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String url = INVALID_PAGE;
        Tbl_User_DTO userDTO = null;
        Tbl_User_DAO userDAO = null;
        try {
            String userID = request.getParameter("txtUserID").trim();
            String pwd = request.getParameter("txtPassword").trim();

            userDAO = new Tbl_User_DAO();
            userDTO = userDAO.checkLogin(userID, pwd);
            if (userDTO != null) {
                if (userDTO.getStatusID() == MyConstants.STATUS_ACTIVE) {
                   
                    HttpSession session = request.getSession(true);
                    
                    String name = userDTO.getUsername();
                    session.setAttribute("USERNAME", name);
                    
                    String userLogin = userDTO.getUserID();
                    session.setAttribute("USERID", userLogin);

                    int roleID = userDTO.getRoleID();
                    if (roleID == MyConstants.ROLE_ADMIN) {
                        session.setAttribute("ADMIN", roleID);
                        List<String> roleName, defaultRoleName;
                        
                        // RoleName mặc định
                        defaultRoleName = userDAO.findQuantityRole();
                        session.setAttribute("DEFAULTROLENAME", defaultRoleName);
                        
                        // Thêm "All" cho Tab RoleName
                        roleName = userDAO.findQuantityRole();                   
                        roleName.add(0, "All");
                        session.setAttribute("ROLENAME", roleName);
                                                      
                        userDAO.getAllUsersAccount();      
                        
                        List<Tbl_User_DTO> result = userDAO.getUserList();                                                
                        session.setAttribute("RESULT", result);
                        
                        // Lấy toàn bộ tài khoản có role không phải là Admin
                        List<Tbl_User_DTO> normalAccountResult = userDAO.getNormalAccounts();                             
                        session.setAttribute("NORMALACCOUNTS", normalAccountResult);
                        
                        // Set role mặc định khi vào trang Search là All
                        String defaultRole = "All";                   
                        session.setAttribute("MARKED", defaultRole);
                         
                        // Gọi danh sách Promotion
                        Tbl_Promotion_DAO promotionDAO = new Tbl_Promotion_DAO();
                        promotionDAO.getAllPromotion();
                                          
                        List<Tbl_Promotion_DTO> promotionDTO = promotionDAO.getPromotionList();
                        session.setAttribute("PROMOTIONRESULT", promotionDTO);
                                            
                        
                    } else {
                        session.setAttribute("USERINFO", userDTO);
                    }
                    url = SEARCH_PAGE;
                } 
            }
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
            response.sendRedirect(url);
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
