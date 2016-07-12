package com.cnu2016.controller;

import com.cnu2016.AWSSQSUtility;
import com.cnu2016.model.LogData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by pranet on 11/07/16.
 */
public class Interceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        DateTimeZone zoneUTC = DateTimeZone.UTC;
        DateTimeZone.setDefault(zoneUTC);
        long startTime = (Long)request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        LogData logData = new LogData(new DateTime(),
                request.getRemoteAddr(),
                request.getRequestURL().toString(),
                request.getParameterMap().toString(),
                response.getStatus(),
                request.getMethod(),
                endTime - startTime
                );
        AWSSQSUtility.getInstance().sendMessageToQueue(new ObjectMapper().writeValueAsString(logData));
    }

}
