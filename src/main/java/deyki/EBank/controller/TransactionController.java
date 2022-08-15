package deyki.EBank.controller;

import deyki.EBank.domain.model.bindingModel.transaction.DepositBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.TransferBindingModel;
import deyki.EBank.domain.model.bindingModel.transaction.WithDrawBindingModel;
import deyki.EBank.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit/{userId}")
    public ResponseEntity<String> deposit(@PathVariable Long userId, @RequestBody DepositBindingModel depositBindingModel) {

        transactionService.deposit(userId, depositBindingModel);

        return ResponseEntity.status(HttpStatus.OK).body("Deposit is done!");
    }

    @PostMapping("/withdraw/{userId}")
    public ResponseEntity<String> withDraw(@PathVariable Long userId, @RequestBody WithDrawBindingModel withDrawBindingModel) throws Exception {

        transactionService.withDraw(userId, withDrawBindingModel);

        return ResponseEntity.status(HttpStatus.OK).body("Withdraw is done!");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferBindingModel transferBindingModel) throws Exception {

        transactionService.transfer(transferBindingModel);

        return ResponseEntity.status(HttpStatus.OK).body("Transfer is done!");
    }
}
