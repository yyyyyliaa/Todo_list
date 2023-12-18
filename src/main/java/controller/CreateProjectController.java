//package controller;
//
//import repository.Todo_list;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.io.PrintWriter;
//@WebServlet("/api/addProject")
//public class CreateProjectController extends HttpServlet {
//
////    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session  = request.getSession();
//        String username = request.getParameter("username");
//        String password = request.getParameter("projectTitle");
//
//        if(session.getAttribute("LoggedInUser").equals(username)){
//
//        }
//        boolean valid = Todo_list.getInstance().checkLogin(username, password);
//
//        if (valid) {
//            HttpSession session = request.getSession();
//            session.setAttribute("loggedInUser", username);
//            PrintWriter out = response.getWriter();
//            out.print("Authorization successful");
//        } else {
//            PrintWriter out = response.getWriter();
//            out.print("User doesn't exist");
//        }
//    }
//}
