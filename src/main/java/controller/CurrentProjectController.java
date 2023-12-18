package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Project;
import model.User;
import handler.DbHandler;

import java.io.IOException;

@WebServlet(name = "project", value = "/project/*")
public class CurrentProjectController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null){
            response.sendRedirect("/login.jsp");
        }
        String projectId = request.getPathInfo().substring(1);
        Project project = DbHandler.getProjectById(Integer.parseInt(projectId));
        if (project.getTitle()==null) System.out.println("pizdec");
        User user = DbHandler.getUserByUsername(session.getAttribute("loggedInUser").toString());

        request.setAttribute("project", project);
        request.setAttribute("userProjects", user.getProjects());
        getServletContext().getRequestDispatcher("/project.jsp").forward(request, response);
    }


}
