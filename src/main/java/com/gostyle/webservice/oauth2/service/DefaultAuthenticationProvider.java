package com.gostyle.webservice.oauth2.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.gostyle.webservice.dao.Client_spaceRepository;
import com.gostyle.webservice.entities.Client;
import com.gostyle.webservice.entities.Client_space;
import com.gostyle.webservice.oauth2.entity.AppUser;
import com.gostyle.webservice.oauth2.passwordManager.Argon2id;
import com.gostyle.webservice.oauth2.repository.AppUserRepository;
import com.gostyle.webservice.service.Client_spaceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
public class DefaultAuthenticationProvider implements AuthenticationProvider {

    private AppUserRepository appUserRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<AppUser> appUser = appUserRepository.findById(authentication.getName());
        /*
        Client_spaceService repoCs = new Client_spaceService();
        List<Client_space> csList = repoCs.getLogin(authentication.getName());
        */

        if(appUser.isPresent()) {
            AppUser user = appUser.get();
            String username = authentication.getName();
            String password = (String)authentication.getCredentials();

            Argon2id argon2id = new Argon2id();
            Boolean isCorrectPassword = argon2id.isPasswordCorrect(
                    appUser.get().getPassword(),
                    authentication.getCredentials().toString()
            );

            System.out.println("************* Checking password ****************");
            System.out.println(" |  - Mot de passe entré = : " + authentication.getName());
            System.out.println(" |  - username trouvé en base = " + appUser.get().getUsername());
            System.out.println(" |  - mot de passe trouvé en base = " + appUser.get().getPassword());
            System.out.println(" |  - MATCHING MDP ? " + isCorrectPassword);
            System.out.println("****** ******** ******* ******* ******* *******");

            if(username.equalsIgnoreCase(user.getUsername()) &&
                    isCorrectPassword) {
                return new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(user.getRoles()))
                );
            }
        }

        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
