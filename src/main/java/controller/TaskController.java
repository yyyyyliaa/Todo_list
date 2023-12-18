package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.DbHandler;

import java.io.IOException;

@WebServlet(name="tasks", value = "/tasks")
public class TaskController extends HttpServlet {
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
        String taskId = request.getParameter("taskId");
        String taskTitle = request.getParameter("taskTitle");
        String taskStatus = request.getParameter("taskStatus");
//        System.out.println("id: " + taskId);
        String projectId = request.getParameter("projectId");
        if(taskStatus==null || taskTitle==null){
            System.out.println("Some parameter is null");
            return;
        }
        if (taskId==null){
             int newTaskId = DbHandler.addTask(taskTitle, taskStatus, Integer.parseInt(projectId));
             response.getWriter().print(newTaskId);
        } else {
            DbHandler.updTask(Integer.parseInt(taskId), taskTitle, taskStatus);
        }


    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskId = request.getParameter("taskId");
        System.out.println("ID " + taskId);
        if (taskId!=null){
            DbHandler.deleteTask(Integer.parseInt(taskId));
        }
    }
}
