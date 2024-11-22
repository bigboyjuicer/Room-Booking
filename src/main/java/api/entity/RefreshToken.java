package api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Cannot be null")
    @Column(name = "refresh_token")
    private String refreshToken;

    @NotNull(message = "Cannot be null")
    @Column(name = "email")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Cannot be null") String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(@NotNull(message = "Cannot be null") String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public @NotNull(message = "Cannot be null") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Cannot be null") String email) {
        this.email = email;
    }


}
