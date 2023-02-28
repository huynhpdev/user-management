/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.controllers;

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
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

    private final String SEARCH_PAGE = "search.jsp";
    private final String VIEW_PAGE = "search.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        String url = SEARCH_PAGE;

        Tbl_User_DAO userDAO = null;
        
        String chosenRoleName = request.getParameter("chosenRoleName");
        String lastChosenRole = request.getParameter("lastChosenRole");

        HttpSession session = request.getSession();
        Object normalUser = session.getAttribute("USERINFO");
        String getChosenRoleSession = (String) session.getAttribute("MARKED");

        String searchValue = request.getParameter("txtSearch");

        try {
            if (normalUser == null) {
                // Tìm kiếm dành cho Admin
                // Sau khi cập nhật thông tin người dùng:
                if (lastChosenRole == null && chosenRoleName == null) {
                    if (searchValue.equals("") || searchValue == null) {
                        userDAO = new Tbl_User_DAO();
                        userDAO.getUsersAccountByRole(getChosenRoleSession);

                        List<Tbl_User_DTO> result = userDAO.getUserList();

                        session.removeAttribute("RESULT");
                        session.setAttribute("RESULT", result);
                    } else {
                        userDAO = new Tbl_User_DAO();
                        userDAO.getUsersAccount(searchValue, getChosenRoleSession);
                        List<Tbl_User_DTO> result = userDAO.getUserList();

                        session.removeAttribute("RESULT");
                        session.setAttribute("RESULT", result);
                    }

                }

                if (chosenRoleName != null) {
                    // Khi người dùng chọn tab thì mới có giá trị chosenRoleName để set
                    session.setAttribute("MARKED", chosenRoleName);
                }

                if (lastChosenRole == null || lastChosenRole.equals("")) {
                    lastChosenRole = getChosenRoleSession;
                }

                // Tìm kiếm lần đầu sau khi chọn Role
                if (chosenRoleName != null) {
                    if (searchValue == null) {
                        userDAO = new Tbl_User_DAO();
                        userDAO.getUsersAccountByRole(chosenRoleName);

                        List<Tbl_User_DTO> result = userDAO.getUserList();

                        session.removeAttribute("RESULT");
                        session.setAttribute("RESULT", result);
                    }
                }

                // Tìm kiếm lần thứ 2 trở đi sau khi chọn Role
                if (lastChosenRole != null) {
                    if (!searchValue.equals("") && searchValue != null) {
                        
                        userDAO = new Tbl_User_DAO();
                        userDAO.getUsersAccount(searchValue, lastChosenRole);

                        List<Tbl_User_DTO> result = userDAO.getUserList();

                        session.removeAttribute("RESULT");
                        session.setAttribute("RESULT", result);
                    }
                }

            } else {
                // Tìm kiếm dành cho người dùng thông thường
                userDAO = new Tbl_User_DAO();

                String userID = (String) session.getAttribute("USERID");

                Tbl_User_DTO userDTO = userDAO.getAccount(userID);
                session.setAttribute("USERINFO", userDTO);
                
                String name = userDTO.getUsername();
                session.setAttribute("USERNAME", name);
            }

            url = VIEW_PAGE;
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
