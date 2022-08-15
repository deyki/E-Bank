package deyki.EBank.domain.model.bindingModel.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WithDrawBindingModel {

    private String iban;
    private Float amount;
}
