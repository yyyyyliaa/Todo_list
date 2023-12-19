package controller;

import handler.DbHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/shareProject")
public class ShareController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null){
            response.sendRedirect("/login.jsp");
        }
        String recipientName = request.getParameter("recipientName");
        String projectId = request.getParameter("projectId");
        if(!DbHandler.checkUserExist(recipientName)){
            response.getWriter().write("User-recipient doesn't exist");
            response.sendRedirect("/home.jsp");
        } else {
            DbHandler.addExistProject(Integer.parseInt(projectId), recipientName);
            response.sendRedirect("/home.jsp");
        }


    }
}
