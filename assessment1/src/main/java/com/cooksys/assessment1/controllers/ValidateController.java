package com.cooksys.assessment1.controllers;

import com.cooksys.assessment1.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("validate")
@RequiredArgsConstructor
public class ValidateController {

    private final ValidateService validateService;

}
