package deyki.EBank.service;

import deyki.EBank.domain.model.bindingModel.bankAccount.BankAccountBindingModel;
import deyki.EBank.domain.model.responseModel.BankAccountResponseModel;

public interface BankAccountService {

    void createBankAccount(Long userId, BankAccountBindingModel bankAccountBindingModel);

    BankAccountResponseModel getBankAccountById(Long bankAccountId);

    BankAccountResponseModel getBankAccountByUsername(String username);
}
