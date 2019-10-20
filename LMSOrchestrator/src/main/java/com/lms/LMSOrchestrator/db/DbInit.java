package com.lms.LMSOrchestrator.db;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.LMSOrchestrator.POJO.User;
import com.lms.LMSOrchestrator.db.*;

@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Delete all
        this.userRepository.deleteAll();

        // Crete users
		User lib = new User("librarian",passwordEncoder.encode("librarian123"),"Librarian","ACCESS_LIBRARIAN");
		User adm = new User("admin",passwordEncoder.encode("admin123"),"Admin","ACCESS_LIBRARIAN,ACCESS_BORROWER,ACCESS_ADMIN");
		User bor = new User("borrower",passwordEncoder.encode("borrower123"),"Borrower","ACCESS_BORROWER");
		
		List<User> users = Arrays.asList(lib,adm,bor);
		// Save to db
        this.userRepository.saveAll(users);
    }
}
