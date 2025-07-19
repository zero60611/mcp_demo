package com.wh.mcp_demo.config;

import com.wh.mcp_demo.service.UserServiceInterface;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpServerConfig {

    /**
     *  註冊工具回調提供者，將UserToolService被標註@Tool工具註冊的方法暴露在MCP 工具上
     */
    @Bean
    public ToolCallbackProvider usersToolCallbackProvider(@Qualifier("userToolService") UserServiceInterface userServiceInterface) {
        return MethodToolCallbackProvider
                .builder()
                .toolObjects(userServiceInterface)
                .build();
    }
}
