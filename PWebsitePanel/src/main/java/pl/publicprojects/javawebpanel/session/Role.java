package pl.publicprojects.javawebpanel.session;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rankId;

    @NotBlank
    @Size(max = 100)
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof String str) {
            return str.equals(roleName);
        }
        if(other instanceof Role otherRole) {
            return otherRole.getRoleName().equals(this.roleName);
        }
        return false;
    }
}
