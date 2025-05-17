package site.nomoreparties.stellarburgers.user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserRandom {
    public String randomUserEmail(){
        return (RandomStringUtils.randomAlphabetic(10) + "@yandex.ru").toLowerCase();
    }

    public String randomUserPassword() {
        return RandomStringUtils.randomAlphabetic(6);
    }

    public String randomUserName() {
        return RandomStringUtils.randomAlphabetic(6);
    }
}
