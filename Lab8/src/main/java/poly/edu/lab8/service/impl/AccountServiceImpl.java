package poly.edu.lab8.service.impl;


import poly.edu.lab8.entity.Account;
import poly.edu.lab8.service.AccountService;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    private final Map<String, Account> data = new HashMap<>();

    @PostConstruct
    public void init() {
        // Sample accounts
        data.put("user", new Account("user", "123", "Người dùng", false));
        data.put("admin", new Account("admin", "123", "Quản trị viên", true));
    }

    @Override
    public Account findById(String username) {
        return data.get(username);
    }
}

