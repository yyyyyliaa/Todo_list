package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Task {
    private Integer id;
    private String title;
    private String status;
    private Integer project_id;

}
