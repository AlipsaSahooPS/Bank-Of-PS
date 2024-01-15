package com.example.bankapp.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, String> {
    @Query("""
select t from Token t inner join Customer c on t.customer.customerId = c.customerId
where c.customerId = :customerId and (t.expired = false or t.revoked = false)
""")
    List<Token> findAllValidTokensByCustomer(String customerId);
    Optional<Token> findByToken(String token);
    @Query("""
select t from Token t WHERE t.customer.customerId = :customerId
""")
    List<Token> deleteAllTokensByCustomerId(String customerId);
    @Query("""
select t from Token t where t.customer.customerId = :customerId and (t.expired = false or t.revoked = false)
""")
    Token getTokenFromTokenTable(String customerId);
}
