package colepp.app.wealthwisebackend.users.services;

import colepp.app.wealthwisebackend.auth.dtos.JwtResponseDto;
import colepp.app.wealthwisebackend.auth.services.JwtService;
import colepp.app.wealthwisebackend.users.dtos.RegisterUserDto;
import colepp.app.wealthwisebackend.users.dtos.UpdateUserDto;
import colepp.app.wealthwisebackend.users.dtos.UserDto;
import colepp.app.wealthwisebackend.users.entities.User;
import colepp.app.wealthwisebackend.users.excpetions.AccountExistException;
import colepp.app.wealthwisebackend.users.excpetions.UserNotFoundException;
import colepp.app.wealthwisebackend.users.mappers.UserMapper;
import colepp.app.wealthwisebackend.users.repositories.UserRepository;
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

    public UserDto registerUser(RegisterUserDto request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AccountExistException("Email already exists");
        }

        var user = userMapper.registerUserDtoToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

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
        var email = jwtService.getEmailFromToken(token);
        var user = userRepository.findByEmail(email).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        emailService.sendRegistrationMessage(user.getName(),email,"","WWReg");

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), Collections.emptyList());


    }

}
