package deyki.EBank.domain.model.bindingModel.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransferBindingModel {

    private String senderIban;
    private String receiverIban;
    private Float amount;
}
