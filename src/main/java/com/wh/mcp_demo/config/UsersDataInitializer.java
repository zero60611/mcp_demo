package com.wh.mcp_demo.config;

import com.wh.mcp_demo.entity.User;
import com.wh.mcp_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsersDataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UsersDataInitializer.class);

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User();
        u1.setName("大谷");
        u1.setEmail("otani@example.com");
        u1.setPhone("0939789001");

        User u2 = new User();
        u2.setName("翔平");
        u2.setEmail("shohei@example.com");
        u2.setPhone("0939789002");

        User u3 = new User();
        u3.setName("楊泰");
        u3.setEmail("yangtai@example.com");
        u3.setPhone("0939789003");

        User u4 = new User();
        u4.setName("糾察隊隊長");
        u4.setEmail("captain@example.com");
        u4.setPhone("0939789003");

        User u5 = new User();
        u5.setName("廖小羊");
        u5.setEmail("lamb@example.com");
        u5.setPhone("0939789003");

        User u6 = new User();
        u6.setName("迷你羊");
        u6.setEmail("mini@example.com");
        u6.setPhone("0939789003");

        userRepository.saveAll(List.of(u1, u2, u3, u4, u5, u6));

        log.info("初始化數據完成");
        List<User> all = userRepository.findAll();
        all.forEach(user -> log.info("{}", user));
    }
}
