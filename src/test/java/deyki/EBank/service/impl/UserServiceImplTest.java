package deyki.EBank.service.impl;

import deyki.EBank.domain.entity.User;
import deyki.EBank.domain.model.bindingModel.user.NewUsernameModel;
import deyki.EBank.domain.model.bindingModel.user.UserBindingModel;
import deyki.EBank.domain.model.responseModel.SignInResponseModel;
import deyki.EBank.repository.UserRepository;
import deyki.EBank.security.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private JWTUtil jwtUtil;
    private User user;

    @BeforeEach
    void setUp() {

        this.user = User.builder().userId(10L).username("deyki").password("1234").build();
    }

    @Test
    void whenLoadUserByUsername_thenReturnCorrectResult() {

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        UserDetails testUserDetails = userService.loadUserByUsername(user.getUsername());

        assertEquals(testUserDetails.getUsername(), user.getUsername());
    }

    @Test
    void whenSignIn_thenReturnCorrectResult() {

        String token = "testJWToken";

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        Mockito.when(jwtUtil.generateToken(user.getUsername())).thenReturn(token);

        SignInResponseModel testSignInResponseModel = userService.signIn(modelMapper.map(user, UserBindingModel.class));

        assertEquals(testSignInResponseModel.getJWToken(), token);
    }

    @Test
    void whenChangeUsernameById_thenVerifyCorrectResult() {

        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        userService.changeUsernameById(user.getUserId(), new NewUsernameModel("deykioveca"));

        assertEquals(user.getUsername(), "deykioveca");
    }
}