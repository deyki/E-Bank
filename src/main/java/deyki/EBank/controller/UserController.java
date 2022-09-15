package deyki.EBank.controller;

import deyki.EBank.domain.model.bindingModel.user.NewPasswordModel;
import deyki.EBank.domain.model.bindingModel.user.NewUsernameModel;
import deyki.EBank.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {

        return ResponseEntity.status(HttpStatus.OK).body("Hello!");
    }

    @PutMapping("/changeUsername/{userId}")
    public ResponseEntity<String> changeUsername(@PathVariable Long userId, @RequestBody NewUsernameModel newUsernameModel) {

        userService.changeUsernameById(userId, newUsernameModel);

        return ResponseEntity.status(HttpStatus.OK).body("Username changed successfully!");
    }

    @PutMapping("/changePassword/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable Long userId, @RequestBody NewPasswordModel newPasswordModel) throws Exception {

        userService.changePasswordById(userId, newPasswordModel);

        return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully!");
    }
}
