package org.bear.sso.repository;

import org.bear.sso.bean.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    public User queryUserByUsernameEquals(String username);

    public User queryUserByPhone(String phone);

    public User queryUserByUsernameAndPassword(String username,String password);

}
