/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.controllers;

import huynhp.tbl_User.Tbl_User_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MSI
 */
@MultipartConfig(
//        fileSizeThreshold = 1024 * 10,  // 10 KB
        maxFileSize = 1024 * 1024 * 2     // 2 MB
//        maxRequestSize = 1024 * 1024    // 1 MB 
)
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private final String LOGIN_PAGE = "login.html";
    private final String LOGIN_CONTROLLER = "LoginController";
    private final String LOGOUT_CONTROLLER = "LogoutController";
    private final String SEARCH_CONTROLLER = "SearchController";
    private final String UPDATE_CONTROLLER = "UpdateController";
    private final String DELETE_CONTROLLER = "DeleteController";
    private final String INSERT_CONTROLLER = "InsertController";
    private final String ADD_PROMOTION_CONTROLLER = "AddPromotionController";
    private final String UPDATE_PROMOTION_CONTROLLER = "UpdatePromotionController";
    private final String DELETE_PROMOTION_CONTROLLER = "DeletePromotionController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("btnAction");
        String url = LOGIN_PAGE;
        
        try {

            if (action == null) {
                url = LOGIN_PAGE;
            } else if (action.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (action.equals("Search")) {
                url = SEARCH_CONTROLLER;
            } else if (action.equals("Update")) {
                url = UPDATE_CONTROLLER;
            } else if (action.equals("Delete")) {
                url = DELETE_CONTROLLER;
            } else if (action.equals("Insert")) {
                url = INSERT_CONTROLLER;
            } else if (action.equals("Logout")) {
                url = LOGOUT_CONTROLLER;
            } else if (action.equals("AddPromotion")) {
                url = ADD_PROMOTION_CONTROLLER;
            } else if (action.equals("UpdatePromotion")) {
                url = UPDATE_PROMOTION_CONTROLLER;
            } else if (action.equals("DeletePromotion")) {
                url = DELETE_PROMOTION_CONTROLLER;
            }

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
