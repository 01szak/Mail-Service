package mailService.kninit.Enum;

import lombok.Getter;

@Getter
public enum Emails {
    KONTAKT("kontakt@hackarena.pl", "Dupadupa1@"),
    EVENT("event@hackarena.pl", "Dupadupa1@"),
    WSPÓŁPRACA("wspolpraca@hackarena.pl", "Dupadupa1@");

    private final String email;

    private final String password;

    Emails(String email,String password) {
        this.email = email;
        this.password = password;
    }


}
