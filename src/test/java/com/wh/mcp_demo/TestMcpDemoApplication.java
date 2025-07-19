package com.wh.mcp_demo;

import org.springframework.boot.SpringApplication;

public class TestMcpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(McpDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
