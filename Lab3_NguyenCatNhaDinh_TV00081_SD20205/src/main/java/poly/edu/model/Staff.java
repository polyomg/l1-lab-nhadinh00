package poly.edu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {
    private String id;
    private String fullname;
    private Boolean gender;
    private Date birthday;
    private Double salary;
    private Integer level;
    private String photo;
}