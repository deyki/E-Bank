package deyki.EBank.domain.model.bindingModel.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserBindingModel {

    private String username;
    private String password;
}
