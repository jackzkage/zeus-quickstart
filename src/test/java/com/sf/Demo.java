package com.sf;

import com.sf.model.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zhonglj on 2017/12/2.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SysBootApplication.class)
public class Demo {




    @Test
    public void t(){


        System.out.println("哈哈哈");
    }
}
