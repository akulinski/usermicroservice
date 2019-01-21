package com.msi.usermicroservice;

import com.msi.usermicroservice.core.entites.Authority;
import com.msi.usermicroservice.core.entites.AuthorityEntity;
import com.msi.usermicroservice.core.entites.HistoryEntity;
import com.msi.usermicroservice.core.entites.UserEntity;
import com.msi.usermicroservice.core.repositories.AuthorityRepository;
import com.msi.usermicroservice.core.repositories.HistoryRepository;
import com.msi.usermicroservice.core.repositories.UserRepository;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Mock {

    private final UserRepository userRepository;

    private final HistoryRepository historyRepository;

    private final SecureRandom secureRandom;

    private final AuthorityRepository authorityRepository;

    private Lorem lorem;

    private static Authority[] authorities = new Authority[]{Authority.ADMIN,Authority.DOCTOR,Authority.PATIENT};

    @Autowired
    public Mock(UserRepository userRepository, HistoryRepository historyRepository, AuthorityRepository authorityRepository) {
        this.lorem = LoremIpsum.getInstance();
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.secureRandom = new SecureRandom();
        this.authorityRepository = authorityRepository;
    }

    private void mock() {

        for (int i = 0; i < 100; i++) {
            UserEntity userEntity = new UserEntity();
            setUpUser(i, userEntity);
            setUpRandomAmountOfHistoryEntries(userEntity);
        }
    }

    private void setUpRandomAmountOfHistoryEntries(UserEntity userEntity) {
        int historyEntries = secureRandom.nextInt(10);

        for (int i = 0; i < historyEntries; i++) {
            setUpHistoryOfUser(userEntity);
        }
    }

    private void setUpHistoryOfUser(UserEntity userEntity) {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setUserEntity(userEntity);
        historyEntity.setValue(lorem.getWords(30));
        historyRepository.save(historyEntity);
    }

    private void setUpUser(int i, UserEntity userEntity) {
        if (i % 2 == 0) {
            userEntity.setUsername(lorem.getFirstNameMale());
            userEntity.setGender("M");
        } else {
            userEntity.setUsername(lorem.getFirstNameFemale());
            userEntity.setGender("F");
        }
        userRepository.save(userEntity);

        AuthorityEntity authorityEntity = getAuthorityEntity(userEntity);
        authorityRepository.save(authorityEntity);
    }

    private AuthorityEntity getAuthorityEntity(UserEntity userEntity) {
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setAuthority(authorities[secureRandom.nextInt(authorities.length)]);
        authorityEntity.setUserEntity(userEntity);
        return authorityEntity;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void readyListener() {
        mock();
    }
}
