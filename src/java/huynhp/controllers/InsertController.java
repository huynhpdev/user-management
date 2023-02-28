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
        maxFileSize = 1024 * 1024 * 5 // 5 MB
//        maxRequestSize = 1024 * 1024    // 1 MB 
)
@WebServlet(name = "InsertController", urlPatterns = {"/InsertController"})
public class InsertController extends HttpServlet {

    private final String ERROR_INSERT = "insertAccount.jsp";
    private final String INSERT_PAGE = "insertAccount.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        String url = ERROR_INSERT;

        String txtUserID = request.getParameter("txtUserIDUp").trim();
        String txtPassword = request.getParameter("txtPasswordUp").trim();
        String txtRoleName = request.getParameter("txtRolenameUp");
        String txtUsername = request.getParameter("txtUsernameUp").trim();
        String txtEmail = request.getParameter("txtEmailUp").trim();
        String txtPhone = request.getParameter("txtPhoneUp").trim();

        Part fileUser = request.getPart("fileUp");

        InputStream inputStream = null;

        HttpSession session = request.getSession();

        int roleID = 0;
        int statusID = 0;
        String msg = "";
        try {
            if (txtRoleName.equals("Employee")) {
                roleID = 1;
            } else if (!txtRoleName.equals("Admin")) {
                roleID = 2;
            }

            inputStream = fileUser.getInputStream();
            byte[] userPhoto = DBHelper.parseInputStreamToByteArray(inputStream);

            Tbl_User_DAO userDAO = new Tbl_User_DAO();
            boolean isExisted = userDAO.checkExistedAccount(txtUserID);

            if (!isExisted) {
                boolean rs = userDAO.insertAccount(txtUserID, txtPassword, txtRoleName, roleID, statusID,
                        txtUsername, txtEmail, txtPhone, userPhoto);
                if (rs) {

                    userDAO.getAllUsersAccount();

                    List<Tbl_User_DTO> result = userDAO.getUserList();
                    session.removeAttribute("RESULT");
                    session.setAttribute("RESULT", result);
                    
                    List<Tbl_User_DTO> normalAccountResult = userDAO.getNormalAccounts();
                    session.removeAttribute("NORMALACCOUNTS");
                    session.setAttribute("NORMALACCOUNTS", normalAccountResult);

                    msg = "Inserted account successfully";
                    request.setAttribute("INSERTEDACCOUNT", msg);
                }
            } else {
                msg = "This userID " + txtUserID + " is existed in the system";
                request.setAttribute("INSERTEDACCOUNT", msg);
            }
            String defaultRole = "All";
            session.setAttribute("MARKED", defaultRole);
            url = INSERT_PAGE;
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
