package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import handler.DbHandler;


import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/registration.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");
        if(!password.equals(confPassword)){
            PrintWriter out = response.getWriter();
            out.print("Passwords doesn't match");
        } else {
            UserDAO member = new UserDAO(username, password);
            String result = DbHandler.insert(member);
            System.out.println(result);
            response.sendRedirect("login.jsp");
        }

    }
}
