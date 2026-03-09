package com.investment.funds;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.LoggerFactory;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class StreamLambdaHandler implements RequestStreamHandler {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            // Forzar logs de Spring Web a TRACE programáticamente
            Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.INFO);

            Logger springWeb = (Logger) LoggerFactory.getLogger("org.springframework.web");
            springWeb.setLevel(Level.TRACE);

            Logger dispatcher = (Logger) LoggerFactory.getLogger("org.springframework.web.servlet.DispatcherServlet");
            dispatcher.setLevel(Level.TRACE);

            Logger mapping = (Logger) LoggerFactory
                    .getLogger("org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping");
            mapping.setLevel(Level.TRACE);

            // Forzar inicialización como aplicación web servlet
            handler = new SpringBootProxyHandlerBuilder<AwsProxyRequest>()
                    .defaultProxy()
                    .asyncInit(System.currentTimeMillis())
                    .springBootApplication(InvestmentFundsApplication.class)
                    .buildAndInitialize();
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        System.out.println(">>> DEBUG: StreamLambdaHandler received request");
        handler.proxyStream(inputStream, outputStream, context);
    }
}
