package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Project;
import model.User;
import repository.DbHandler;

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
//        System.out.println(projectId);
        Project project = DbHandler.getProjectById(Integer.parseInt(projectId));
        if (project.getTitle()==null) System.out.println("pizdec");
//        System.out.println(project.getTitle());
        User user = DbHandler.getUserByUsername(session.getAttribute("loggedInUser").toString());

        request.setAttribute("project", project);
        request.setAttribute("userProjects", user.getProjects());
//        response.sendRedirect("../project.jsp");

//        request.setAttribute("session",);

        getServletContext().getRequestDispatcher("/project.jsp").forward(request, response);
    }


}
