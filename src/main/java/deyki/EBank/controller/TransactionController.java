package deyki.EBank.controller;

import deyki.EBank.domain.model.bindingModel.transaction.DepositBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.TransferBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.WithDrawBindingModel;
import deyki.EBank.domain.model.responseModel.TransactionResponseModel;
import deyki.EBank.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositBindingModel depositBindingModel) {

        transactionService.deposit(depositBindingModel);

        return ResponseEntity.status(HttpStatus.OK).body("Deposit is done!");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withDraw(@RequestBody WithDrawBindingModel withDrawBindingModel) throws Exception {

        transactionService.withDraw(withDrawBindingModel);

        return ResponseEntity.status(HttpStatus.OK).body("Withdraw is done!");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferBindingModel transferBindingModel) throws Exception {

        transactionService.transfer(transferBindingModel);

        return ResponseEntity.status(HttpStatus.OK).body("Transfer is done!");
    }

    @GetMapping("/listOfTransactions/{username}")
    public ResponseEntity<List<TransactionResponseModel>> listOfTransactionsBySenderUsername(@PathVariable String username) {

        List<TransactionResponseModel> transactions = transactionService.getTransactionsBySenderUsername(username);

        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }
}
