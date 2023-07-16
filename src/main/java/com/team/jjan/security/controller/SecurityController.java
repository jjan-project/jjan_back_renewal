package com.team.jjan.security.controller;

import com.team.jjan.common.ResponseCode;
import com.team.jjan.common.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.team.jjan.common.ResponseCode.AUTHENTICATION_FAIL;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    @GetMapping("/authentication")
    public ResponseMessage authenticateMessage() {
        return ResponseMessage.of(AUTHENTICATION_FAIL);
    }

}
