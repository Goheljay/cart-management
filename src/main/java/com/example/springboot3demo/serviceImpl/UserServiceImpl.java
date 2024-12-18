package com.example.springboot3demo.serviceImpl;

import com.example.springboot3demo.cutomException.CustomException;
import com.example.springboot3demo.dto.request.SignUpUserDto;
import com.example.springboot3demo.modal.Users;
import com.example.springboot3demo.repository.UserRepository;
import com.example.springboot3demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;

    @Override
    public Long registerUser(SignUpUserDto signUpUserDto) {
        Optional<Users> byEmail = userRepository.findByEmail(signUpUserDto.getEmail());
        if (byEmail.isPresent()) {
            throw new CustomException("User Already exist", HttpStatus.CONFLICT);
        }

        Users user = new Users();
        user.setEmail(signUpUserDto.getEmail());
        user.setName(signUpUserDto.getName());
        user.setPassword(passwordEncoder.encode(signUpUserDto.getPassword()));
        Users save = userRepository.save(user);
        return user.getId();
    }
}
