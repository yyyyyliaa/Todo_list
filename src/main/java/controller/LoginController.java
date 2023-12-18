package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import handler.DbHandler;


import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean valid = DbHandler.checkUserExist(username) && DbHandler.checkCorrectPassword(username, password);

        if (valid) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", username);
            response.sendRedirect("home.jsp");
        } else {
            PrintWriter out = response.getWriter();
            out.print("User doesn't exist");
        }
    }
}
