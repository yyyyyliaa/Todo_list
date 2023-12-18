package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import handler.DbHandler;

import java.io.IOException;

@WebServlet(name = "projects", value = "/projects")
public class ProjectsController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null){
            response.sendRedirect("/login.jsp");
        }
        if (request.getParameter("_method") != null && request.getParameter("_method").equals("DELETE")) {
            doDelete(request, response);
            return;
        }
        String projectTitle = request.getParameter("projectTitle");
        String owner = request.getParameter("owner");
        int newTaskId = DbHandler.addProject(projectTitle, owner);
        response.getWriter().print(newTaskId);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectId = request.getParameter("projectId");
        System.out.println("ID " + projectId);
        if (projectId!=null){
            DbHandler.deleteProject(Integer.parseInt(projectId));
        }
    }
}
