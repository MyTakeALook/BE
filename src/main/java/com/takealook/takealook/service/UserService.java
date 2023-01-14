package com.takealook.takealook.service;

import com.takealook.takealook.dto.JoinRequestDto;
import com.takealook.takealook.dto.LoginRequestDto;
import com.takealook.takealook.entity.User;
import com.takealook.takealook.jwt.JwtUtil;
import com.takealook.takealook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final com.takealook.takealook.jwt.JwtUtil jwtUtil;


    @Transactional
    public void signup(JoinRequestDto joinRequestDto) {
        String username = joinRequestDto.getUsername();
        String password = passwordEncoder.encode(joinRequestDto.getPassword());
        // 아이디 중복 확인
        if (userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("username이 중복됩니다.");
        }

        User user = new User(username, password);
        userRepository.save(user);
    }

    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,
                jwtUtil.createToken(user.getUsername(),user.getRole()));
    }
}