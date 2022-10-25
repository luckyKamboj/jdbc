package com.company.database.dao;

import com.company.database.annotations.UnitTest;
import com.company.database.annotations.UnitTester;
import com.company.database.model.ApplicationUser;
import org.assertj.core.api.Assertions;

import java.util.Collection;
import java.util.List;

@UnitTester(beanName = "GenericDao")
public class GenericDaoImplTest {
    GenericDaoImpl<ApplicationUser> genericDao = new GenericDaoImpl<>();

    @UnitTest
    public void testGetPaginatedRecords() throws ClassNotFoundException {
        Collection<ApplicationUser> userList = genericDao.getPaginatedRecords(ApplicationUser.class, 0, 10);
        Assertions.assertThat(userList.size()).as("User list should not be empty.").isNotZero();
    }

    @UnitTest
    public void testGetAllByIdIn(){
        Collection<ApplicationUser> applicationUsers = genericDao.getAllByIdIn(ApplicationUser.class, List.of(1));
        Assertions.assertThat(applicationUsers.size()).as("User list should not be empty.").isNotZero();
    }

    @UnitTest
    public void testGetById(){
        ApplicationUser applicationUser = genericDao.getById(ApplicationUser.class, 1);
        Assertions.assertThat(applicationUser).as("User should not be null.").isNotNull();
    }

    @UnitTest
    public void testDeleteById(){
        Assertions.assertThat(genericDao.deleteById(ApplicationUser.class, 2)).as("User should be null.").isNull();
    }

    @UnitTest
    public void testDeleteByIdIn(){
        Assertions.assertThat(genericDao.deleteAllByIdIn(ApplicationUser.class, List.of(2))).as("User should be null.").isTrue();
    }

    @UnitTest
    public void testDeleteAll(){
        Assertions.assertThat(genericDao.deleteAll(ApplicationUser.class)).as("User should not be null.").isTrue();
    }
}
