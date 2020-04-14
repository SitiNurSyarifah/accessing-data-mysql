package com.op.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AccessingDataMysqlApplicationTest {
    @Test
    public void main() {
        AccessingDataMysqlApplication.main(new String[] {});
    }
}
