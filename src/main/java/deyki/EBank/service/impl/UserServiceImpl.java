package deyki.EBank.service.impl;

import deyki.EBank.domain.entity.User;
import deyki.EBank.domain.model.bindingModel.user.UserBindingModel;
import deyki.EBank.domain.model.responseModel.SignInResponseModel;
import deyki.EBank.repository.UserRepository;
import deyki.EBank.security.JWTUtil;
import deyki.EBank.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userRes = userRepository.findByUsername(username);

        if (userRes.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Could not find User with username: %s", username));
        }

        User user = userRes.get();

        return new org.springframework
                .security
                .core
                .userdetails
                .User(username, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public void signUp(UserBindingModel userBindingModel) {

        userRepository.findByUsername(userBindingModel.getUsername()).ifPresent(user1 -> {
                    throw new EntityExistsException(String.format("User with username: %s already exist!", userBindingModel.getUsername()));
                });

        User user = modelMapper.map(userBindingModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userBindingModel.getPassword()));

        userRepository.save(user);
    }

    @Override
    public SignInResponseModel signIn(UserBindingModel userBindingModel) {

        User user = userRepository
                .findByUsername(userBindingModel.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Could not find User with username: %s", userBindingModel.getUsername())));

        final String token = jwtUtil.generateToken(user.getUsername());

        return new SignInResponseModel(token);
    }
}
