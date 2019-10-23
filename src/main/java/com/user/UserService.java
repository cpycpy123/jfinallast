package com.user;

import com.demo.common.model.User;
import com.jfinal.plugin.activerecord.Page;

public class UserService {
    private static final User dao = new User().dao();

    public Page<User> paginate(int pageNumber, int pageSize) {
        /**
         * //pageNumber 第几页
         *
         * //pageSize 一页几条记录
         */
        return dao.paginate(pageNumber, pageSize, "select *", "from user order by id asc");

    }

    public User findById(int id) {
        return dao.findById(id);
    }

    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
