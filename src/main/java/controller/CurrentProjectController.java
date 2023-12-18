package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Project;
import repository.DbHandler;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "project", value = "/project/*")
public class CurrentProjectController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectId = request.getPathInfo().substring(1);
        System.out.println(projectId);
        Project project = DbHandler.getProjectById(Integer.parseInt(projectId));
        request.setAttribute("project", project);
    }


}
