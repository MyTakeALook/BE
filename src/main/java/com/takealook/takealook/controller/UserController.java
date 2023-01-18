package com.takealook.takealook.controller;

import com.takealook.takealook.dto.*;
import com.takealook.takealook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup") //@RequestBody
    public ResponseEntity<AuthMessage> signup(@RequestBody JoinRequestDto joinRequestDto) {
        userService.signup(joinRequestDto);
        AuthMessage authMessage = new AuthMessage("회원가입 성공");
        return new ResponseEntity<>(authMessage, OK);
    }

    @ResponseBody
    @PostMapping("/login") //@RequestBody
    public LoginErrorMessage login(@RequestBody LoginRequestDto loginRequestDto,
                                             HttpServletResponse response) {
        LoginErrorMessage loginErrorMessage = userService.login(loginRequestDto, response);

        //AuthMessage authMessage = new AuthMessage("로그인 성공");
        return loginErrorMessage;
    }

//    @PostMapping("/pw-find")
//    public pwfind(@RequestBody PasswordFindRequestDto passwordFindRequestDto) {
//        userService.findpw(passwordFindRequestDto);
//    }
//
//            if id_receive != "" and code_receive == "" and pw_receive == "" and pw_re_receive == "":
//    result = db.member.find_one({'id': id_receive})
//    print("result :", result)
//
//        if result is not None:
//    email_receive = result['email']
//
//    code = random.randint(100000, 999999)
//
//            db.member.update_one({'id': id_receive}, {'$set': {'code': code}})
//
//    msg = Message('비밀번호 코드', sender='silve45345@gmail.com', recipients=[email_receive])
//    msg.body = '인증코드 : {0}'.format(code)
//            mail.send(msg)
//            return jsonify({'result': 'success1'})
//            else:
//            return jsonify({'result': 'fail1'})
//    elif id_receive != "" and code_receive != "" and pw_receive == "" and pw_re_receive == "":
//    result = db.member.find_one({'id': id_receive})
//    print("3 :", result)
//    print(code_receive)
//    print(type(code_receive))
//    print(result['code'])
//    print(type(result['code']))
//            if result is not None:
//            if int(code_receive) == result['code']:
//            return jsonify({'result': 'success2'})
//            else:
//            return jsonify({'result': 'fail2'})
//            else:
//            return jsonify({'result': 'fail2'})
//    elif id_receive != "" and code_receive != "" and pw_receive != "" and pw_re_receive == "":
//    result = db.member.find_one({'id': id_receive})
//
//    reg_pw = r'^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{8,20}$'
//
//            if not re.search(reg_pw, pw_receive):
//    result = 'fail3'
//            return jsonify({'result': result})
//            return jsonify({'result': 'success3'})
//    elif id_receive != "" and code_receive != "" and pw_receive != "" and pw_re_receive != "":
//    print("4단계 실행중>>>>")
//    print(id_receive)
//    print(code_receive)
//    print(pw_receive)
//    print(pw_re_receive)
//    print("4단계 실행중>>>>")
//    result = db.member.find_one({'id': id_receive})
//    print("result:", result)
//    reg_pw = r'^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{8,20}$'
//
//            if not re.search(reg_pw, pw_re_receive):
//    print("정규식 실행중????")
//    result = 'fail4'
//            return jsonify({'result': result})
//
//            if pw_receive != pw_re_receive:
//    print("일치하지않음")
//    result = 'fail4'
//            return jsonify({'result': result})
//
//    pw_hash = hashlib.sha256(pw_receive.encode('utf-8')).hexdigest()  # 암호화??
//            db.member.update_one({'id': id_receive}, {'$set': {'pw': pw_hash}})
//            # code = random.randint(100000, 999999)
//            #
//            # msg = Message('비밀번호 코드', sender='silve45345@gmail.com', recipients=[id_receive])
//    # msg.body = '인증코드 : {0}'.format(code)
//    # mail.send(msg)
//            return jsonify({'result': 'success4'})
}
