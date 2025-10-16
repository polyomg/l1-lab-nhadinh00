package poly.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import jakarta.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Staff {
    @NotBlank(message = "Chưa nhập email")
    @Email(message = "Email không đúng định dạng")
    private String id;

    @NotBlank(message = "Chưa nhập họ và tên")
    private String fullname;
    @Builder.Default
    private String photo = "photo.jpg";
    @NotNull(message = "Chưa chọn giới tính")
    @Builder.Default
    private Boolean gender = true; // true: male, false: female
    @NotNull(message = "Chưa nhập ngày sinh")
    @Past(message = "Ngày sinh không hợp lệ")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Builder.Default
    private Date birthday = new Date();

    @Min(value = 1000, message = "Lương tối thiểu phải là 1000")
    @NotNull(message = "Chưa nhập lương")
    @Builder.Default
    private Double salary = 12345.6789;
    @Builder.Default
    private Integer level = 0; // 0: Uy, 1: Ta, 2: Tuong
}


