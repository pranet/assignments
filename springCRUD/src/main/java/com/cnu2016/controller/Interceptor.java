package com.cnu2016.controller;

import com.cnu2016.AWSSQSUtility;
import com.cnu2016.model.LogData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pranet on 11/07/16.
 */
public class Interceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LogData logData = new LogData(new DateTime(),
                request.getRemoteAddr(),
                request.getRequestURL().toString(),
                request.getParameterMap().toString(),
                response.getStatus());
        AWSSQSUtility.getInstance().sendMessageToQueue(new ObjectMapper().writeValueAsString(logData));
    }
}
