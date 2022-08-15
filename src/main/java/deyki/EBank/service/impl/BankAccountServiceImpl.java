package deyki.EBank.service.impl;

import deyki.EBank.domain.entity.BankAccount;
import deyki.EBank.domain.entity.User;
import deyki.EBank.domain.model.bindingModel.bankAccount.BankAccountBindingModel;
import deyki.EBank.domain.model.responseModel.BankAccountResponseModel;
import deyki.EBank.repository.BankAccountRepository;
import deyki.EBank.repository.UserRepository;
import deyki.EBank.service.BankAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createBankAccount(Long userId, BankAccountBindingModel bankAccountBindingModel) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d not found!", userId)));

        bankAccountRepository
                .findByIban(bankAccountBindingModel.getIban())
                .ifPresent(bankAccount -> {
                    throw new EntityExistsException(String.format("Bank account with iban: %s already exist!", bankAccountBindingModel.getIban()));
                });

        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban(bankAccountBindingModel.getIban());
        bankAccount.setBalance(0.0F);
        bankAccount.setUser(user);
        bankAccountRepository.save(bankAccount);

        user.setBankAccount(bankAccount);
        userRepository.save(user);
    }

    @Override
    public BankAccountResponseModel getBankAccountById(Long bankAccountId) {

        BankAccount bankAccount = bankAccountRepository
                .findById(bankAccountId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Bank account with id: %d not found!", bankAccountId)));

        return modelMapper.map(bankAccount, BankAccountResponseModel.class);
    }
}
