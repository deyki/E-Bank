package deyki.EBank.service;

import deyki.EBank.domain.model.bindingModel.transaction.DepositBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.TransferBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.WithDrawBindingModel;

public interface TransactionService {

    void deposit(Long userId, DepositBindingModel depositBindingModel);

    void withDraw(Long userId, WithDrawBindingModel withDrawBindingModel) throws Exception;

    void transfer(TransferBindingModel transferBindingModel) throws Exception;
}
