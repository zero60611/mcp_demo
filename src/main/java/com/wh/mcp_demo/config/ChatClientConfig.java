package com.wh.mcp_demo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    /**
     * 配置ChatClient ： 註冊系統指令和工作函數
     * 這邊也是扮演MCP Client的角色
     * 配置toolCallbackProvider：將UserToolService被標註@Tool工具註冊的方法暴露在MCP 工具上
     * https://docs.spring.io/spring-ai/docs/1.0.0/api/org/springframework/ai/chat/client/ChatClient.Builder.html
     * 使用 defaultToolCallbacks ，其他網站可能版本關係所有會用錯方法
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, @Qualifier("usersToolCallbackProvider") ToolCallbackProvider toolCallbackProvider) {
        toolCallbackProvider.getToolCallbacks();
        return builder
                 .defaultSystem("""
                         你是一個使用者管理工具，可以新增、刪除、查詢使用者，請好好妥善管理使用，生殺大權操之在你
                         """)
                 .defaultToolCallbacks(toolCallbackProvider)
                 .build();
    }
}
