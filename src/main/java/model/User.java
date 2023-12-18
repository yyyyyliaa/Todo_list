package model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;
    private String password;
    private Set<Project> projects = new HashSet<>();
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }




}
