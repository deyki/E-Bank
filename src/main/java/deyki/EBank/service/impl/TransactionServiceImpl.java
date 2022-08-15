package deyki.EBank.service.impl;

import deyki.EBank.domain.entity.BankAccount;
import deyki.EBank.domain.entity.Transaction;
import deyki.EBank.domain.entity.User;
import deyki.EBank.domain.enums.TransactionType;
import deyki.EBank.domain.model.bindingModel.transaction.DepositBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.TransferBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.WithDrawBindingModel;
import deyki.EBank.repository.BankAccountRepository;
import deyki.EBank.repository.TransactionRepository;
import deyki.EBank.repository.UserRepository;
import deyki.EBank.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, BankAccountRepository bankAccountRepository, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void deposit(Long userId, DepositBindingModel depositBindingModel) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Invalid userId!"));

        BankAccount bankAccount = bankAccountRepository
                .findByIban(depositBindingModel.getIban())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Bank account with iban: %s not found!", depositBindingModel.getIban())));

        Float bankAccountBalance = bankAccount.getBalance();
        bankAccount.setBalance(bankAccountBalance + depositBindingModel.getAmount());

        bankAccountRepository.save(bankAccount);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setSenderUsername(user.getUsername());
        transaction.setReceiverUsername(user.getUsername());
        transaction.setAmount(depositBindingModel.getAmount());

        transactionRepository.save(transaction);
    }

    @Override
    public void withDraw(Long userId, WithDrawBindingModel withDrawBindingModel) throws Exception {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Invalid userId!"));

        BankAccount bankAccount = bankAccountRepository
                .findByIban(withDrawBindingModel.getIban())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Bank account with iban: %s not found!", withDrawBindingModel.getIban())));

        Float bankAccountBalance = bankAccount.getBalance();

        if (bankAccountBalance < withDrawBindingModel.getAmount()) {
            throw new Exception("Your balance is not enough!");
        } else {
            bankAccount.setBalance(bankAccountBalance - withDrawBindingModel.getAmount());
        }

        bankAccountRepository.save(bankAccount);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setSenderUsername(user.getUsername());
        transaction.setReceiverUsername(user.getUsername());
        transaction.setAmount(withDrawBindingModel.getAmount());

        transactionRepository.save(transaction);
    }

    @Override
    public void transfer(TransferBindingModel transferBindingModel) throws Exception {

        BankAccount senderBankAccount = bankAccountRepository
                .findByIban(transferBindingModel.getSenderIban())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Incorrect sender iban: %s", transferBindingModel.getSenderIban())));

        BankAccount receiverBankAccount = bankAccountRepository
                .findByIban(transferBindingModel.getReceiverIban())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Incorrect receiver iban: %s", transferBindingModel.getReceiverIban())));

        Float senderBankAccountBalance = senderBankAccount.getBalance();
        Float receiverBankAccountBalance = receiverBankAccount.getBalance();

        if (senderBankAccountBalance < transferBindingModel.getAmount()) {
            throw new Exception("You don't have enough money for transfer!");
        } else {
            senderBankAccount.setBalance(senderBankAccountBalance - transferBindingModel.getAmount());
            receiverBankAccount.setBalance(receiverBankAccountBalance + transferBindingModel.getAmount());
        }

        bankAccountRepository.save(senderBankAccount);
        bankAccountRepository.save(receiverBankAccount);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setSenderUsername(senderBankAccount.getUser().getUsername());
        transaction.setReceiverUsername(receiverBankAccount.getUser().getUsername());
        transaction.setAmount(transferBindingModel.getAmount());

        transactionRepository.save(transaction);
    }
}
