package deyki.EBank.service;

import deyki.EBank.domain.model.bindingModel.user.NewPasswordModel;
import deyki.EBank.domain.model.bindingModel.user.NewUsernameModel;
import deyki.EBank.domain.model.bindingModel.user.UserBindingModel;
import deyki.EBank.domain.model.responseModel.SignInResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void signUp(UserBindingModel userBindingModel);

    SignInResponseModel signIn(UserBindingModel userBindingModel);

    void changeUsernameById(Long userId, NewUsernameModel newUsernameModel);

    void changePasswordById(Long userId, NewPasswordModel newPasswordModel) throws Exception;
}
