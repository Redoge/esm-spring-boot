package com.epam.esm.util.mappers;

import com.epam.esm.entities.User;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.pojo.UserSaveRequestPojo;
import com.epam.esm.util.enums.UserRole;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Component
public class UserMapper {
    public User mapUserPojoToUSer(UserSaveRequestPojo userPojo) throws BadRequestException {
        if(isEmpty(userPojo.getUsername()) || isEmpty(userPojo.getPassword()))
            throw new BadRequestException();
        var user = new User();
        user.setUsername(userPojo.getUsername());
        user.setPassword(userPojo.getPassword());
        user.setMoney(BigDecimal.valueOf(0));
        user.setRole(UserRole.USER);
        user.setGiftCertificates(new ArrayList<>());
        user.setOrders(new ArrayList<>());
        return user;
    }
}
