package deyki.EBank.service;

import deyki.EBank.domain.entity.Transaction;
import deyki.EBank.domain.model.bindingModel.transaction.DepositBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.TransferBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.WithDrawBindingModel;
import deyki.EBank.domain.model.responseModel.TransactionResponseModel;
import deyki.EBank.repository.TransactionRepository;

import java.util.List;

public interface TransactionService {

    void deposit(DepositBindingModel depositBindingModel);

    void withDraw(WithDrawBindingModel withDrawBindingModel) throws Exception;

    void transfer(TransferBindingModel transferBindingModel) throws Exception;

    List<TransactionResponseModel> getTransactionsBySenderUsername(String username);
}
