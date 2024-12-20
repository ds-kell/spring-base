package vn.com.dsk.demo.base.domain.repository;

import vn.com.dsk.demo.base.domain.entities.Account;

public interface AccountRepository {
    Account findByUsernameOrEmail(String username, String email);
}
