package controller;

import handler.DbHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebServlet("/changePassword")
public class SettingsController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null){
            response.sendRedirect("/login.jsp");
        }
        String currentPassword = request.getParameter("currentPassword");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        assert session != null;
        User user = DbHandler.getUserByUsername(session.getAttribute("loggedInUser").toString());
        if (!password.equals(confirmPassword) || !user.getPassword().equals(currentPassword)){
            response.getWriter().write("ERROR!");
            return;
        }
        DbHandler.changePassword(session.getAttribute("loggedInUser").toString(), password);
        session.invalidate();
        response.sendRedirect("/login.jsp");
    }
}
