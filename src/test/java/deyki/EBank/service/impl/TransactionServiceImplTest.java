package deyki.EBank.service.impl;

import deyki.EBank.domain.entity.BankAccount;
import deyki.EBank.domain.entity.Transaction;
import deyki.EBank.domain.entity.User;
import deyki.EBank.domain.enums.TransactionType;
import deyki.EBank.domain.model.bindingModel.transaction.DepositBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.TransferBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.WithDrawBindingModel;
import deyki.EBank.domain.model.responseModel.TransactionResponseModel;
import deyki.EBank.repository.BankAccountRepository;
import deyki.EBank.repository.TransactionRepository;
import deyki.EBank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceImplTest {

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BankAccountServiceImpl bankAccountService;

    private Transaction transaction;
    private User user;
    private User user2;
    private BankAccount bankAccount;
    private BankAccount bankAccount2;

    @BeforeEach
    void setUp() {

        this.user = User.builder().userId(10L).username("deyki").password("1234").build();
        this.user2 = User.builder().userId(22L).username("kaNNz").password("222222").build();
        this.bankAccount = BankAccount.builder().bankAccountId(20L).iban("1 22 333 4444").balance(50.00F).user(user).build();
        this.bankAccount2 = BankAccount.builder().bankAccountId(31L).iban("3 44 212 5555").balance(100.00F).user(user2).build();
    }

    @Test
    void whenDeposit_thenVerifyCorrectResult() {


        Mockito.when(bankAccountRepository.findByIban(bankAccount.getIban())).thenReturn(Optional.ofNullable(bankAccount));
        Mockito.when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);

        transactionService.deposit(new DepositBindingModel(bankAccount.getIban(), 20.00F));

        assertEquals(bankAccount.getBalance(), 70.00F);
    }

    @Test
    void whenWithDraw_thenVerifyCorrectResult() throws Exception {

        Mockito.when(bankAccountRepository.findByIban(bankAccount.getIban())).thenReturn(Optional.ofNullable(bankAccount));
        Mockito.when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);

        transactionService.withDraw(new WithDrawBindingModel(bankAccount.getIban(), 10.00F));

        assertEquals(bankAccount.getBalance(), 40.00F);
    }

    @Test
    void whenTransfer_thenVerifyCorrectResult() throws Exception {

        Mockito.when(bankAccountRepository.findByIban(bankAccount.getIban())).thenReturn(Optional.ofNullable(bankAccount));
        Mockito.when(bankAccountRepository.findByIban(bankAccount2.getIban())).thenReturn(Optional.ofNullable(bankAccount2));

        Mockito.when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        Mockito.when(bankAccountRepository.save(bankAccount2)).thenReturn(bankAccount2);

        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);

        transactionService.transfer(new TransferBindingModel(bankAccount2.getIban(), bankAccount.getIban(), 20.00F));

        assertEquals(bankAccount2.getBalance(), 80.00F);
        assertEquals(bankAccount.getBalance(), 70.00F);
    }

    @Test
    void whenGetTransactionsBySenderUsername_thenVerifyCorrectResult() {

        Transaction transaction1 = new Transaction(31L, TransactionType.DEPOSIT, user.getUsername(), user.getUsername(), 30.00F);
        Transaction transaction2 = new Transaction(41L, TransactionType.WITHDRAW, user.getUsername(), user.getUsername(), 10.00F);

        Mockito.when(transactionRepository.findAll()).thenReturn(List.of(transaction1, transaction2));

        List<TransactionResponseModel> transactionResponseModels = transactionService.getTransactionsBySenderUsername(user.getUsername());

        assertEquals(transactionResponseModels.size(), 2);
    }
}