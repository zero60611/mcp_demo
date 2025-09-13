package com.wh.mcp_demo.controller;

import com.wh.mcp_demo.model.ChatRequest;
import com.wh.mcp_demo.model.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    /**
     * 處理聊天，使用AI和MCP工具進行響應
     */

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            String message = request.getMessage();
            String content = chatClient.prompt()
                    .user(message)
                    .call()
                    .content();

            log.info("查看回復content:{}", content);

            ChatResponse build = ChatResponse.builder().content(content).build();
            log.info("{}", build);
            return ResponseEntity.ok(build);
        } catch (Exception e) {
            log.error("處理請求出錯", e);
            ChatResponse build = ChatResponse.builder().content("處理請求出錯" + e.getMessage()).build();
            return ResponseEntity.ok(build);
        }
    }

}
