package com.exam.controller;

import com.exam.DifyStreamService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/dify")
public class DifyController {

    private final DifyStreamService difyStreamService;

    public DifyController(DifyStreamService difyStreamService) {
        this.difyStreamService = difyStreamService;
    }

    /**
     * 接收前端请求，调用Dify流式接口，并以流式方式返回结果
     */
    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat() {
        // 调用服务获取Dify的流式响应，并直接返回给前端
        return difyStreamService.getStreamingResponse()
                // 可以在这里添加额外的处理，如日志记录
                .doOnNext(chunk -> System.out.println("Sending chunk: " + chunk))
                .doOnError(error -> System.err.println("Error in stream: " + error.getMessage()));
    }
}
