package com.example.springmvc.basic.request;

import com.example.springmvc.basic.HelloData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    // 잭슨 주입 받기
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {


        // 데이터 읽는 방법
        // getInputStream()는. message body 부분을 bytecode로 받아냄.
        ServletInputStream inputStream = request.getInputStream();

        // StreamUtils.copyToString()는. bytecode를 문자열로 변환.
        // 단, 인코딩 형태를 항상 명시.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        // 객체로 뽑아낸 것처럼 동일하게 ObjectMapper 를 통해 객체를 담아서 전송한다.
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        // 객체로 변환 한것이다.
        System.out.println("helloData.username = " + helloData.getUsername());
        System.out.println("helloData.age = " + helloData.getAge());

        response.getWriter().write("ok");
    }
}