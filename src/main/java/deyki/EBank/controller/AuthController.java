package deyki.EBank.controller;

import deyki.EBank.domain.model.bindingModel.user.UserBindingModel;
import deyki.EBank.domain.model.responseModel.SignInResponseModel;
import deyki.EBank.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserServiceImpl userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserBindingModel userBindingModel) {

        userService.signUp(userBindingModel);

        return ResponseEntity.status(HttpStatus.CREATED).body("Registration completed!");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseModel> signIn(@RequestBody UserBindingModel userBindingModel) {

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userBindingModel.getUsername(), userBindingModel.getPassword()));

        return ResponseEntity.status(HttpStatus.OK).body(userService.signIn(userBindingModel));
    }
}
