//package com.takealook.takealook;
//
//import com.takealook.takealook.entity.User;
//import com.takealook.takealook.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class TestData implements ApplicationRunner {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        userRepository.save(new User("test123", "12345678")); // me
//        userRepository.save(new User("test456", "12345678"));
//        userRepository.save(new User("test789", "12345678"));
//        userRepository.save(new User("test111", "12345678"));
//        userRepository.save(new User("test222", "12345678"));
//    }
//}
