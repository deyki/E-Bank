package deyki.EBank.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @SequenceGenerator(name = "bank_account_sequence", sequenceName = "bank_account_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_sequence")
    private Long bankAccountId;

    @Column(name = "iban", nullable = false, unique = true)
    private String iban;

    @Column(name = "balance", nullable = false)
    private Float balance;

    @OneToOne(mappedBy = "bankAccount")
    @JsonIgnore
    private User user;
}
