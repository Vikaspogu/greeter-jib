package com.example.demo.controller;

import io.vertx.core.json.JsonObject;
import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.Unleash;
import no.finn.unleash.util.UnleashConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * GreeterController
 */
@RestController
@RequestMapping("/greeter")
public class GreeterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreeterController.class);

    private static final String RESPONSE_STRING_FORMAT = "%s %s greeter => '%s' : %d\n";

    private final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");

    private static final String HOSTNAME =
            parseContainerIdFromHostname(System.getenv().getOrDefault("HOSTNAME", "unknown"));

    static String parseContainerIdFromHostname(String hostname) {
        return hostname.replaceAll("greeter-v\\d+-", "");
    }

    @Value("${MESSAGE_PREFIX:Hi}")
    private String prefix;

    @Value("${spring.application.name}")
    private String appName;

    /**
     * Counter to help us see the lifecycle
     */
    private int count = 0;


    @GetMapping("/")
    public String greet() {
        count++;
        return String.format(RESPONSE_STRING_FORMAT, prefix, "", HOSTNAME, count);
    }

    @PostMapping("/")
    public @ResponseBody
    String eventGreet(@RequestBody String cloudEventJson) {
        count++;
        String greeterHost = String.format(RESPONSE_STRING_FORMAT, "", " Event ", HOSTNAME, count);
        JsonObject response = new JsonObject(cloudEventJson)
                .put("host", greeterHost.replace("\n", "").trim())
                .put("time", SDF.format(new Date()));
        LOGGER.info("Event Message Received \n {}", response.encodePrettily());
        return response.encode();
    }

    @GetMapping("/healthz")
    public String health() {
        return "OK";
    }

    @GetMapping("/awesomefeature")
    public String awesomeFeature() {
        UnleashConfig config = UnleashConfig.builder()
                .appName(appName)
                .instanceId(appName + "-instance")
                .unleashAPI("http://unleash-server:4242/api/")
                .build();
        Unleash unleash = new DefaultUnleash(config);
        if (unleash.isEnabled("AwesomeFeature")) {
            //do some magic
            return "This is an awesome feature!";
        } else {
            //do old boring stuff
            return "Nothing to see here, feature not complete";
        }
    }

}
