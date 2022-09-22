package com.example.travelservice.service;

import com.example.travelservice.models.Role;
import com.example.travelservice.models.User;
import com.example.travelservice.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public boolean addUser(User user) {
        User userFromDB = userRepository.findByEmail(user.getEmail());

        if (userFromDB != null) {
            return false;
        }

        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        sendActivationMessage(user);
        return true;
    }

    public void sendActivationMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "%s, ласкаво просимо до Залізниці! " +
                            "Для того, щоб активізувати свій обліковий запис перейдіть за посиланням http://localhost:8080/activate/%s. " +
                            "Будь ласка, не пересилайте та не відповідайте на це повідомлення. " +
                            "З повагою, команда Залізниці",
                    user.getFirstName(),
                    user.getActivationCode()
            );
            mailService.send(user.getEmail(), "Код активації", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActive(true);
        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }

    public boolean sendPasswordInstructions(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return false;
        }

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Добрий день, %s!\n" +
                            "Для відновлення пароля перейдіть за посиланням: http://localhost:8080/forgot-password/%s.\n" +
                            "Будь ласка, не пересилайте та не відповідайте на це повідомлення. \n" +
                            "З повагою, команда Залізниці",
                    user.getFirstName(),
                    user.getId()
            );
            mailService.send(user.getEmail(), "Відновлення пароля", message);
            return true;
        }

        return false;
    }

    public boolean setNewPassword(Long id, String password) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return true;
        }
        return false;
    }


    public boolean loadDocs(User user, MultipartFile file, String docs) throws IOException {
        if(file != null) {
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            switch (docs){
                case "Студентський":
                    user.setStudentCard(resultFilename);
                    break;
                case "Пільговий":
                    user.setBenefitCard(resultFilename);
                    break;
            }
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean updateUser(User user, String firstName, String secondName, String studentCard, String benefitCard) {
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setStudentCardNumber(studentCard);
        user.setBenefitCardNumber(benefitCard);

        userRepository.save(user);

        return true;
    }

    public boolean updateEmail(User user, String email) {
        user.setEmail(email);
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        sendActivationMessage(user);
        return true;
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if (passwordEncoder.matches(user.getPassword(), oldPassword)) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
