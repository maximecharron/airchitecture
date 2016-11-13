package ca.ulaval.glo4003.air.infrastructure.transaction;

import ca.ulaval.glo4003.air.domain.transaction.Transaction;
import ca.ulaval.glo4003.air.domain.transaction.TransactionRepository;

import java.util.HashSet;
import java.util.Set;

public class TransactionRepositoryInMemory implements TransactionRepository {

    private Set<Transaction> transactions = new HashSet<>();

    @Override
    public void save(Transaction transaction) {
        transactions.add(transaction);
    }
}
