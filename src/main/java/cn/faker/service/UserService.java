package cn.faker.service;

import cn.faker.entity.User;

public interface UserService {

    /**
     * 查找用户
     * @param id 用户ID
     * @return 用户
     */
    User getUserById(Long id);

}
