package repository;

import dao.UserDAO;
import model.Project;
import model.Task;
import model.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DbHandler {

    private static final String dbUrl = "jdbc:mysql://localhost:3306/my_db?characterEncoding=utf8";
    private static final String dbUsername = "root";
    private static final String dbPassword = "Minorthreat1994!";

    private static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static String insert(UserDAO member){
        String result = "Data entered successfully!";
        Connection connection = getConnection();
        String sql = "INSERT INTO users VALUES(?, ?)";
        if(!checkUserExist(member.getUsername())){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, member.getUsername());
                preparedStatement.setString(2, member.getPassword());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                result = "Data not entered";
            }
        }
        return result;
    }

    public static boolean checkUserExist(String username){
        Connection connection = getConnection();
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static boolean checkCorrectPassword(String username, String password){
        Connection connection = getConnection();
        String sql = "SELECT password FROM users WHERE username = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String passwordFromDb = resultSet.getString("password");
                System.out.println(passwordFromDb);
                return passwordFromDb.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static User getUserByUsername(String username){
        User user = new User();
        Connection connection = getConnection();
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setUsername(username);
                user.setPassword(resultSet.getString("password"));
                user.setProjects(getUserProjects(username));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static Set<Project> getUserProjects(String username){
        Set<Project> projects = new HashSet<>();

        Connection connection = getConnection();
        String sql = "SELECT * FROM projects WHERE owner = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String owner = resultSet.getString("owner");
                Set<Task> tasks = getProjectTasks(id);
                Project project = new Project(id, title, owner, tasks);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;

    }

    public static Set<Task> getProjectTasks(Integer projectId){
        Set<Task> tasks = new HashSet<>();

        Connection connection = getConnection();
        String sql = "SELECT * FROM tasks WHERE project_id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String status = resultSet.getString("status");
                Integer project_id = resultSet.getInt("project_id");
                Task task = new Task(id, title, status, project_id);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public static Project getProjectById(Integer id){
        Project project = new Project();
        Connection connection = getConnection();
        String sql = "SELECT * FROM projects WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String owner = resultSet.getString("owner");
                project.setId(id);
                project.setTitle(title);
                project.setOwnerName(owner);
                project.setTasks(getProjectTasks(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    public static void updTask(Integer id, String title, String status){
        System.out.println(id + " " + title + " " + status);
        Connection connection = getConnection();
        String sql = "UPDATE tasks SET title = ?, status = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Integer addTask(String title, String status, int project_id){
        Connection connection = getConnection();
        String sql = "INSERT INTO tasks(title, status, project_id) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3, project_id);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else return -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteTask(Integer taskId){
        Connection connection = getConnection();
        String sql = "DELETE FROM tasks WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, taskId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int addProject(String title, String owner){
        Connection connection = getConnection();
        String sql = "INSERT INTO projects(title, owner) VALUES (?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, owner);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else return -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteProject(Integer projectId){
        Connection connection = getConnection();
        String sql = "DELETE FROM projects WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
