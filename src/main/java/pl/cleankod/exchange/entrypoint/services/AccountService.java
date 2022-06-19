package pl.cleankod.exchange.entrypoint.services;

import org.springframework.stereotype.Service;
import pl.cleankod.exchange.core.domain.Account;
import pl.cleankod.exchange.core.usecase.FindAccountAndConvertCurrencyUseCase;
import pl.cleankod.exchange.core.usecase.FindAccountUseCase;

import java.util.Currency;
import java.util.Optional;

@Service
public class AccountService {

    private final FindAccountAndConvertCurrencyUseCase findAccountAndConvertCurrencyUseCase;
    private final FindAccountUseCase findAccountUseCase;

    public AccountService(FindAccountAndConvertCurrencyUseCase findAccountAndConvertCurrencyUseCase, FindAccountUseCase findAccountUseCase) {
        this.findAccountAndConvertCurrencyUseCase = findAccountAndConvertCurrencyUseCase;
        this.findAccountUseCase = findAccountUseCase;
    }

    public Optional<Account> execute(Account.Number accountNumber, String currency) {
        return Optional.ofNullable(currency)
                .map(c -> findAccountAndConvertCurrencyUseCase.execute(accountNumber, Currency.getInstance(c)))
                .orElseGet(() -> findAccountUseCase.execute(accountNumber));
    }

    public Optional<Account> execute(Account.Id id, String currency) {
        return Optional.ofNullable(currency)
                .map(c -> findAccountAndConvertCurrencyUseCase.execute(id, Currency.getInstance(c)))
                .orElseGet(() -> findAccountUseCase.execute(id));
    }
}
