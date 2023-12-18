package repository;

import model.Project;
import model.Task;
import model.User;

import java.util.HashMap;
import java.util.HashSet;

public class Todo_list {
    private static volatile Todo_list instance;
    private final HashSet<User> users = new HashSet<>();
    private final HashSet<Project> projects = new HashSet<>();
    private final HashMap<String, Task> tasks = new HashMap<>();


    public Todo_list() {
    }

    public static Todo_list getInstance(){
        if (instance == null) {
            synchronized (Todo_list.class) {
                if (instance == null) {
                    instance = new Todo_list();
                }
            }
        }
        return instance;
    }

    public User getUserByUsername(String username) {
        for(User user : users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public Project getProjectByTitle(String title){
        for (Project project : projects){
            if(project.getTitle().equals(title))
                return project;
        } return null;
    }

    public boolean checkLogin(String username, String password){
        User user = getUserByUsername(username);
        if (user !=null)
            return user.getPassword().equals(password);
        return false;

    }

    public boolean checkRegistration(String username){
        User user = getUserByUsername(username);
        return user != null;

    }


    public void addUser(String username, String password){
        users.add(new User(username, password));
    }


}
