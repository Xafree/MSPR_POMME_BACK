package com.gostyle.webservice.oauth2.passwordManager;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.bouncycastle.crypto.params.ParametersWithSalt;

import java.nio.charset.Charset;

public class Argon2id {

    private Argon2 argon2;

    public Argon2id() {
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    public String hashPassword(String password) {
        String hash = argon2.hash(1, 37, 1, password);
        System.out.println("Le hash a cette tête : " + hash);
        return hash;
    }

    public Boolean isPasswordCorrect(String correctHashedPassword, String passwordToCheck) {
        Boolean isPasswordCorrect = argon2.verify(correctHashedPassword, passwordToCheck, Charset.forName("utf-8"));
        System.out.println("Hash matches password ? : " + isPasswordCorrect);
        return isPasswordCorrect;
    }
}

