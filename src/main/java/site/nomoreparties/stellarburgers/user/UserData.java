package site.nomoreparties.stellarburgers.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private String email;
    private String password;

    public static UserRandom userRandom = new UserRandom();
    public static String userEmail = userRandom.randomUserEmail();
    public static String userPassword = userRandom.randomUserPassword();
    public static String userName = userRandom.randomUserName();

    public static User userWithoutEmail = new User(userName, "", userPassword);
    public static User userWithoutPassword = new User(userName, userEmail, "");
    public static User userWithoutName = new User("", userEmail, userPassword);
    public static User user = new User(userName, userEmail, userPassword);

    public static String newUserPassword = userRandom.randomUserPassword();
    public static String newUserEmail = userRandom.randomUserEmail();
    public static String newUserName = userRandom.randomUserName();
    public static User newUser = new User(newUserName, newUserEmail, newUserPassword);

    public static UserData getLoginData(User user){
        return new UserData(user.getEmail(), user.getPassword());
    }
}
