package me.weekbelt.apiserver.auth.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequest {

    @NotBlank
    @Range(min = 4, max = 20)
    private String username;

    @NotBlank
    @Range(min = 4, max = 20)
    private String password;

    @NotBlank
    @Range(min = 2, max = 10)
    private String name;

    private List<String> roles;
}
