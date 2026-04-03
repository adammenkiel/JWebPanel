package pl.publicprojects.javawebpanel.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SessionInfoResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
