package com.example.locaquest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.locaquest.constant.Route;
import com.example.locaquest.dto.activity.InitRequest;
import com.example.locaquest.dto.activity.InitResponse;
import com.example.locaquest.service.ActivityService;
import com.example.locaquest.util.LogUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Route.ACTIVITY)
public class ActivityController {

    private final ActivityService activityService;
    private final String filePath = "controller.ActivityController";

    @Value("${kafka.topic.user-param-gain}")
    private String KAFKA_TOPIC_USER_PARAM_GAIN;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping(Route.ACTIVITY_INIT)
    public ResponseEntity<?> init(@RequestBody InitRequest initRequest, HttpServletRequest request) {
        InitResponse response = new InitResponse();
        response.setLoginTokenKey(activityService.getLoginTokenKey(initRequest.getRsaPublicKey()));
        response.setKafkaTopicUserParamGain(KAFKA_TOPIC_USER_PARAM_GAIN);
        LogUtil.info(String.format("successfully"), filePath, Route.ACTIVITY_INIT, request);
        return ResponseEntity.ok(response);
    }
}
