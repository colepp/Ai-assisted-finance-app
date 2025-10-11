package colepp.app.wealthwisebackend.users.services;

import colepp.app.wealthwisebackend.auth.dtos.UserLoginResponse;
import colepp.app.wealthwisebackend.common.dtos.JwtResponse;
import colepp.app.wealthwisebackend.common.services.EmailService;
import colepp.app.wealthwisebackend.common.services.JwtService;
import colepp.app.wealthwisebackend.users.dtos.RegisterUserRequest;
import colepp.app.wealthwisebackend.users.dtos.UpdateUserRequest;
import colepp.app.wealthwisebackend.users.dtos.UserDto;
import colepp.app.wealthwisebackend.users.entities.User;
import colepp.app.wealthwisebackend.users.entities.UserBanking;
import colepp.app.wealthwisebackend.users.excpetions.AccountExistException;
import colepp.app.wealthwisebackend.users.excpetions.PasswordMatchException;
import colepp.app.wealthwisebackend.users.excpetions.UserNotFoundException;
import colepp.app.wealthwisebackend.users.mappers.UserMapper;
import colepp.app.wealthwisebackend.users.repositories.UserBankingRepository;
import colepp.app.wealthwisebackend.users.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public UserDto updateUser(Long id, UpdateUserRequest updateUserRequest){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        userMapper.updateUser(updateUserRequest,user);
        userRepository.save(user);

        return userMapper.userToUserDto(user);


    }

    @Transactional
    public UserDto registerUser(RegisterUserRequest request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AccountExistException("Email already exists");
        }

        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new PasswordMatchException("Passwords do not match");
        }

        var user = userMapper.registerUserDtoToUser(request);
        user.setEmail(request.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var userBankingInfo = new UserBanking();
        userBankingInfo.setUser(user);
        user.setUserBanking(userBankingInfo);

        userRepository.save(user);
        userBankingRepository.save(userBankingInfo);

        return userMapper.userToUserDto(user);
    }

    public UserLoginResponse registerSignIn(String email){
        return new UserLoginResponse(
                jwtService.generateAccessToken(email),
                jwtService.generateRefreshToken(email)
        );
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
