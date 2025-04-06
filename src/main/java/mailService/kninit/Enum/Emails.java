package mailService.kninit.Enum;

import lombok.Getter;

@Getter
public enum Emails {
    KONTAKT("kontakt@hackarena.pl", "password"),
    EVENT("event@hackarena.pl", "password"),
    WSPÓŁPRACA("wspolpraca@hackarena.pl", "password");

    private final String email;

    private final String password;

    Emails(String email,String password) {
        this.email = email;
        this.password = password;
    }
}
