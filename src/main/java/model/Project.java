package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Project {
    private Integer id;
    private String title;
    private String ownerName;
    private Set<Task> tasks;


    public Project(String title){
        this.title = title;
    }


}
