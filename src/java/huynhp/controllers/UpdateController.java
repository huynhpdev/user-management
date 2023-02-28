/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.controllers;

import huynhp.tbl_User.Tbl_User_DAO;
import huynhp.tbl_User.Tbl_User_DTO;
import huynhp.utils.DBHelper;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author MSI
 */
@MultipartConfig(
        //        fileSizeThreshold = 1024 * 10,  // 10 KB
        maxFileSize = 1024 * 1024 * 2 // 2 MB
//        maxRequestSize = 1024 * 1024    // 1 MB 
)
@WebServlet(name = "UpdateController", urlPatterns = {"/UpdateController"})
public class UpdateController extends HttpServlet {

    private final String ERROR_PAGE = "error.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        String txtUserID = request.getParameter("txtUserID");
        String txtUsername = request.getParameter("txtUsername").trim();
        String txtPassword = request.getParameter("txtPassword").trim();
        String txtPhone = request.getParameter("txtPhone").trim();
        String txtEmail = request.getParameter("txtEmail").trim();
        String txtRoleName = request.getParameter("txtRolename");

        InputStream inputStream = null;

        Part fileUser = request.getPart("fileUser");

        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("USERID");
        Object normalUser = session.getAttribute("USERINFO");

        String lastSearchValue = request.getParameter("lastSearchValue");

        String urlRewriting = ERROR_PAGE;

        try {

            Tbl_User_DAO userDAO = new Tbl_User_DAO();
            boolean isExist = userDAO.checkAccountStatus(txtUserID);

            if (isExist) {

                // Kiểm tra xem người dùng có chọn ảnh mới không
                boolean isthereafile = false;
                if (fileUser.getSize() > 0) {
                    isthereafile = true;
                }
                if (fileUser.getSize() <= 0) {
                    isthereafile = false;
                }

                // Kiểm tra xem người dùng là Admin hay là người dùng thông thường
                if (normalUser == null) {

                    boolean rs = false;

                    inputStream = fileUser.getInputStream();
                    byte[] userPhoto = DBHelper.parseInputStreamToByteArray(inputStream);

                    // Kiểm tra xem người dùng có phải đang thay đổi thông tin của tài khoản đang sử dụng 
                    if (currentUser.equals(txtUserID)) {
                        txtRoleName = "Admin";

                        userDAO = new Tbl_User_DAO();
                        rs = userDAO.updateAccountByAdminRole(txtUserID, txtUsername,
                                txtEmail, txtPhone, txtRoleName);

                        Tbl_User_DTO userDTO = userDAO.getAccount(txtUserID);

                        String name = userDTO.getUsername();
                        session.setAttribute("USERNAME", name);

                        if (txtPassword.length() > 0) {
                            rs = userDAO.updateAccountByAdminRole(txtUserID, txtPassword);
                        }

                        if (isthereafile) {
                            rs = userDAO.updateAccountByAdminRole(txtUserID, userPhoto);
                        }
                    } else {
                        userDAO = new Tbl_User_DAO();
                        rs = userDAO.updateAccountByAdminRole(txtUserID, txtUsername,
                                txtEmail, txtPhone, txtRoleName);

                        if (txtPassword.length() > 0) {
                            rs = userDAO.updateAccountByAdminRole(txtUserID, txtPassword);
                        }

                        if (isthereafile) {
                            rs = userDAO.updateAccountByAdminRole(txtUserID, userPhoto);
                        }
                    }

                    if (rs) {
                        urlRewriting = "MainController"
                                + "?btnAction=Search"
                                + "&txtSearch=" + lastSearchValue;
                    }
                } else {
                    boolean rs = false;
                    rs = userDAO.updateAccountByNormalAccount(txtUserID, txtUsername, txtEmail, txtPhone);

                    if (txtPassword.length() > 0) {
                        rs = userDAO.updateAccountByNormalAccount(txtUserID, txtPassword);
                    }

                    if (isthereafile) {
                        inputStream = fileUser.getInputStream();
                        byte[] userPhoto = DBHelper.parseInputStreamToByteArray(inputStream);
                        userDAO = new Tbl_User_DAO();
                        rs = userDAO.updateAccountByNormalAccount(txtUserID, userPhoto);
                    }

                    if (rs) {
                        urlRewriting = "MainController"
                                + "?btnAction=Search";
                    }
                }
            }

        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
            response.sendRedirect(urlRewriting);
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
