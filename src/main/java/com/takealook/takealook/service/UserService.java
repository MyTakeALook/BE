package com.takealook.takealook.service;

import com.takealook.takealook.dto.JoinRequestDto;
import com.takealook.takealook.dto.LoginErrorMessage;
import com.takealook.takealook.dto.LoginRequestDto;
import com.takealook.takealook.dto.PasswordFindRequestDto;
import com.takealook.takealook.entity.User;
import com.takealook.takealook.entity.UserRoleEnum;
import com.takealook.takealook.jwt.JwtUtil;
import com.takealook.takealook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

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

        UserRoleEnum userRoleEnum = UserRoleEnum.USER;

        User user = new User(username, password, userRoleEnum);
        userRepository.save(user);
    }

    public LoginErrorMessage login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        if (username == null || password == null || username.equals("") || password.equals("")) {
            return new LoginErrorMessage(400, "아이디 또는 비밀번호가 입력되지 않았습니다.");
        }

        // 사용자 확인
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return new LoginErrorMessage(401, "존재하지 않는 id입니다");
        }
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return new LoginErrorMessage(402, "password가 잘못되었습니다");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,
                jwtUtil.createToken(user.get().getUsername(),user.get().getRole()));
        return new LoginErrorMessage("로그인 성공");
    }

//    public findpw(PasswordFindRequestDto passwordFindRequestDto) {
//        Optional<User> user = userRepository.findByUsername(passwordFindRequestDto.getUsername());
//
//    }
}