package com.company.test;

import com.company.mybatis.ShiroWebApiApplication;
import com.company.mybatis.facade.HomeFacadeService;
import com.company.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author bin.li
 * @date 2020/10/9
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ShiroWebApiApplication.class)
public class HomeTest {

    @Autowired
    private HomeFacadeService homeFacadeService;


    @Autowired
    private UserService userService;


    @Test
    public void testRegist(){
    }
}
