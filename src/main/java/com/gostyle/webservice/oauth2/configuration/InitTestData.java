package com.gostyle.webservice.oauth2.configuration;

import com.gostyle.webservice.oauth2.entity.AppUser;
import com.gostyle.webservice.oauth2.passwordManager.Argon2id;
import com.gostyle.webservice.oauth2.repository.AppUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Component
@Log4j2
public class InitTestData implements ApplicationListener<ApplicationContextEvent> {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public void onApplicationEvent(ApplicationContextEvent applicationContextEvent) {
        Argon2id argon2id = new Argon2id();

        AppUser user1 = new AppUser("user1@user.com", argon2id.hashPassword("pass1"), "USER");
        AppUser user2 = new AppUser("user2@user.com", argon2id.hashPassword("pass2"), "USER");
        AppUser user3 = new AppUser("user3@user.com", argon2id.hashPassword("pass3"), "USER");
/*
        appUserRepository.save(user1);
        appUserRepository.save(user2);
        appUserRepository.save(user3);
*/
        log.info("3 Users saved.");
    }
}
