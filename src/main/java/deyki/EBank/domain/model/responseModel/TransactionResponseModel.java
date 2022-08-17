package deyki.EBank.domain.model.responseModel;

import deyki.EBank.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransactionResponseModel {

    private TransactionType transactionType;
    private String senderUsername;
    private String receiverUsername;
    private Float amount;
}
