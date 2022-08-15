package deyki.EBank.controller;

import deyki.EBank.domain.model.bindingModel.bankAccount.BankAccountBindingModel;
import deyki.EBank.domain.model.responseModel.BankAccountResponseModel;
import deyki.EBank.service.impl.BankAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bankAccount")
public class BankAccountController {

    private final BankAccountServiceImpl bankAccountService;

    @Autowired
    public BankAccountController(BankAccountServiceImpl bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createBankAccount(@PathVariable Long userId, @RequestBody BankAccountBindingModel bankAccountBindingModel) {

        bankAccountService.createBankAccount(userId, bankAccountBindingModel);

        return ResponseEntity.status(HttpStatus.CREATED).body("Bank account created!");
    }

    @GetMapping("/get/{bankAccountId}")
    public ResponseEntity<BankAccountResponseModel> getBankAccountById(@PathVariable Long bankAccountId) {

        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.getBankAccountById(bankAccountId));
    }
}
