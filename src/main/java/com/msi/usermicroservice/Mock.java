package com.msi.usermicroservice;

import com.msi.usermicroservice.core.entites.HistoryEntity;
import com.msi.usermicroservice.core.entites.UserEntity;
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

    private Lorem lorem;

    @Autowired
    public Mock(UserRepository userRepository, HistoryRepository historyRepository) {
        this.lorem = LoremIpsum.getInstance();
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.secureRandom = new SecureRandom();
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
    }

    @EventListener(ApplicationReadyEvent.class)
    public void readyListener() {
        mock();
    }
}
