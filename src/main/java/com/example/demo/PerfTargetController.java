package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PerfTargetController {
    public static final Logger log = LoggerFactory.getLogger(PerfTargetController.class);
    private AtomicInteger count = new AtomicInteger();
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void postInit() {
        executor.scheduleAtFixedRate(() -> {
            log.info("TPS: {}", count.get());
            count.set(0);
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Operation(summary = "Simulate a 401 response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "401 is expected",
                    content = @Content)})
    @RequestMapping(path = "/unauthorized", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void get() {
        count.incrementAndGet();
    }


    @Operation(summary = "Simulate a long running task for a specified amount of time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", content = @Content)
    })
    @RequestMapping(path = "/delayed-task/{sleep}", method = RequestMethod.GET)
    public void delayedTask(@Parameter(description = "Amount of milliseconds") @PathVariable Integer sleep) throws InterruptedException {
        if (sleep < 0 || sleep > 10000) {
            sleep = 0;
        }
        Thread.sleep(sleep);
        count.incrementAndGet();
    }

    @Operation(summary = "Get a variable amount of DummyPayloads as a JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", content = @Content)
    })
    @RequestMapping(path = "/payload-json/{size}", method = RequestMethod.GET)
    public ArrayList<DummyPayload> generatePayloadJson(@Parameter(description = "Amount of items in the response") @PathVariable Integer size) {

        count.incrementAndGet();
        return generateDummy(size);
    }

    @Operation(summary = "Get 100 DummyPayloads as a JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", content = @Content)
    })
    @RequestMapping(path = "/payload-json", method = RequestMethod.GET)
    public ArrayList<DummyPayload> generatePayloadJsonDefault() {
        count.incrementAndGet();
        return generateDummy(100);
    }

    @Operation(summary = "Get a variable amount of DummyPayloads as a XML")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", content = @Content)
    })
    @RequestMapping(path = "/payload-xml/{size}", method = RequestMethod.GET, produces = {"application/xml", "text/xml"})
    public ArrayList<DummyPayload> generatePayloadXML(@Parameter(description = "Amount of items in the response") @PathVariable Integer size) {
        count.incrementAndGet();
        return generateDummy(size);
    }

    @Operation(summary = "Get 100 DummyPayloads as a XML")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", content = @Content)
    })
    @RequestMapping(path = "/payload-xml", method = RequestMethod.GET, produces = {"application/xml", "text/xml"})
    public ArrayList<DummyPayload> generatePayloadXMLDefault() {
        count.incrementAndGet();
        return generateDummy(100);
    }

    private ArrayList<DummyPayload> generateDummy(int size) {
        if (size < 0 || size > 10000) {
            size = 1;
        }
        ArrayList<DummyPayload> result = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            result.add(new DummyPayload("dummyName" + index, index));
        }
        return result;
    }

}
