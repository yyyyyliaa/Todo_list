package model;

import lombok.*;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private Integer id;
    private String title;
    private String ownerName;
    private Set<Task> tasks;


    public Project(String title){
        this.title = title;
    }


}
