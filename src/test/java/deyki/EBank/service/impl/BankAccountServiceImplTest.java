package deyki.EBank.service.impl;

import deyki.EBank.domain.entity.BankAccount;
import deyki.EBank.domain.entity.User;
import deyki.EBank.domain.model.bindingModel.bankAccount.BankAccountBindingModel;
import deyki.EBank.domain.model.responseModel.BankAccountResponseModel;
import deyki.EBank.repository.BankAccountRepository;
import deyki.EBank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankAccountServiceImplTest {

    @MockBean
    private BankAccountRepository bankAccountRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BankAccountServiceImpl bankAccountService;

    @Autowired
    private ModelMapper modelMapper;

    private BankAccount bankAccount;
    private User user;

    @BeforeEach
    void setUp() {

        this.user = User.builder().userId(10L).username("deyki").password("1234").build();
        this.bankAccount = BankAccount.builder().bankAccountId(5L).iban("123 555 888 99").balance(50.00F).user(user).build();
    }

    @Test
    void whenCreateBankAccount_thenVerifyCorrectResult() {

        BankAccount newBankAccount = new BankAccount();
        newBankAccount.setBankAccountId(66L);
        newBankAccount.setIban("2233 4444 2111 9912");
        newBankAccount.setBalance(200.00F);

        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(user));
        Mockito.when(bankAccountRepository.findByIban(bankAccount.getIban())).thenReturn(Optional.of(newBankAccount));
        Mockito.when(bankAccountRepository.save(newBankAccount)).thenReturn(newBankAccount);

        bankAccountService.createBankAccount(user.getUserId(), modelMapper.map(newBankAccount, BankAccountBindingModel.class));

        assertNotNull(bankAccountRepository.findByIban(newBankAccount.getIban()));
    }

    @Test
    void whenGetBankAccountById_thenReturnCorrectResult() {

        Mockito.when(bankAccountRepository.findById(bankAccount.getBankAccountId())).thenReturn(Optional.ofNullable(bankAccount));

        BankAccountResponseModel bankAccountResponseModel = bankAccountService.getBankAccountById(bankAccount.getBankAccountId());

        assertEquals(bankAccountResponseModel.getUser(), user);
    }

    @Test
    void whenGetBankAccountByUsername_thenReturnCorrectResult() {

        Mockito.when(bankAccountRepository.findByUsername(bankAccount.getUser().getUsername())).thenReturn(Optional.ofNullable(bankAccount));

        BankAccountResponseModel bankAccountResponseModel = bankAccountService.getBankAccountByUsername(bankAccount.getUser().getUsername());

        assertEquals(bankAccountResponseModel.getUser().getUsername(), user.getUsername());
    }
}