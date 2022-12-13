package mockpj.upload.service;

import mockpj.upload.model.Account;
import mockpj.upload.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    public Optional<Account> findAccountById(Long id){
        return accountRepository.findById(id);
    }
}
