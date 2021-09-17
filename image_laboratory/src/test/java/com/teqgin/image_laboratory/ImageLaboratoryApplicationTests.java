package com.teqgin.image_laboratory;

import com.teqgin.image_laboratory.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageLaboratoryApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        System.out.println(user);
    }

}
