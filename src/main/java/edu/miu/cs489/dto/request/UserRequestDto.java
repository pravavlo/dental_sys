package edu.miu.cs489.dto.request;

import edu.miu.cs489.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String username;
    private String password;
    private Role role;

}
