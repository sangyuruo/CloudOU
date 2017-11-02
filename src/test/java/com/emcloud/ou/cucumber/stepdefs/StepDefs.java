package com.emcloud.ou.cucumber.stepdefs;

import com.emcloud.ou.EmCloudOuApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = EmCloudOuApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
