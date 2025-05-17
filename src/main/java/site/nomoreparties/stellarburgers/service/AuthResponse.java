package site.nomoreparties.stellarburgers.service;

import lombok.Getter;
import lombok.Setter;
import site.nomoreparties.stellarburgers.user.User;

@Setter
@Getter
public class AuthResponse {
    private boolean success;
    private String accessToken;
    private String refreshToken;
    User user;
}
