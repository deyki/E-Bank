package deyki.EBank.domain.model.responseModel;

import deyki.EBank.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BankAccountResponseModel {

    private User user;
}
