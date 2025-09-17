package colepp.app.wealthwisebackend.users.services;

import colepp.app.wealthwisebackend.common.dtos.JwtResponseDto;
import colepp.app.wealthwisebackend.common.services.EmailService;
import colepp.app.wealthwisebackend.common.services.JwtService;
import colepp.app.wealthwisebackend.common.services.PostmarkEmailService;
import colepp.app.wealthwisebackend.users.dtos.RegisterUserDto;
import colepp.app.wealthwisebackend.users.dtos.UpdateUserDto;
import colepp.app.wealthwisebackend.users.dtos.UserDto;
import colepp.app.wealthwisebackend.users.entities.User;
import colepp.app.wealthwisebackend.users.entities.UserBanking;
import colepp.app.wealthwisebackend.users.excpetions.AccountExistException;
import colepp.app.wealthwisebackend.users.excpetions.UserNotFoundException;
import colepp.app.wealthwisebackend.users.mappers.UserMapper;
import colepp.app.wealthwisebackend.users.repositories.UserBankingRepository;
import colepp.app.wealthwisebackend.users.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collections;


@RequiredArgsConstructor

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final UserBankingRepository userBankingRepository;


    public User getUser(Long id){
        var user =  userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    public UserDto updateUser(Long id, UpdateUserDto updateUserRequest){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        userMapper.updateUser(updateUserRequest,user);
        userRepository.save(user);

        return userMapper.userToUserDto(user);


    }

    @Transactional
    public UserDto registerUser(RegisterUserDto request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AccountExistException("Email already exists");
        }

        var user = userMapper.registerUserDtoToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var userBankingInfo = new UserBanking();
        userBankingInfo.setUser(user);
        user.setUserBanking(userBankingInfo);

        userRepository.save(user);
        userBankingRepository.save(userBankingInfo);

        return userMapper.userToUserDto(user);
    }

    public JwtResponseDto registerSignIn(String email){
        return new JwtResponseDto(jwtService.generateToken(email));
    }

    public void deleteUser(Long id){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        userRepository.delete(user);
    }

    public void sendTestEmail(){
        emailService.sendRegistrationMessage("cole","palmorecp@beloit.edu","palmorecp@beloit.edu","WWReg");
    }

    public void sendRegistrationEmail(String token) {
        var email = jwtService.getEmailFromToken(jwtService.formatToken(token));
        var user = userRepository.findByEmail(email).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        emailService.sendRegistrationMessage(user.getName(),email,"palmorecp@beloit.edu","WWReg");

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), Collections.emptyList());


    }

}
