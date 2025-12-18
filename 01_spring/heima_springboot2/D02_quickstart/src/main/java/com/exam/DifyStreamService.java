package com.exam;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class DifyStreamService {

    private final WebClient webClient;

    public DifyStreamService(WebClient difyWebClient) {
        this.webClient = difyWebClient;
    }

    /**
     * 调用Dify流式接口
     * @return 流式响应数据
     */
    public Flux<String> getStreamingResponse() {
        // 构建请求体
        Map<String, Object> requestBody = Map.of(
                "inputs", Map.of("csv", new String[]{}),
                "response_mode", "streaming",
                "query", "多维度分析这个数据",
                "user", "表格助手-测试",
                "conversation_id" , "b18a0ae3-b68a-4c6d-b470-0c9c22b59217"
        );



        // 调用Dify流式API并返回Flux
        return webClient.post()
                .uri("/chat-messages") // 根据实际Dify API路径调整
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(String.class) // 假设Dify返回的是文本流
                .map(this::processDifyResponse); // 处理Dify的响应格式
    }

    /**
     * 处理Dify返回的响应数据
     * 根据实际Dify的响应格式进行调整
     */
    private String processDifyResponse(String response) {
        // 这里根据Dify实际返回的流式数据格式进行解析和处理
        // 例如，如果是SSE格式，可能需要提取data部分
        if (response.startsWith("data:")) {
            return response.substring(5).trim();
        }
        return response;
    }
}
