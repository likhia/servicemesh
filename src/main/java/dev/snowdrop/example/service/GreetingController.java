/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.snowdrop.example.service;

import java.util.Objects;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@RestController
public class GreetingController {

    private GreetingProperties properties;

    private static boolean THROW_ERROR=false;

    @Autowired
    public GreetingController(GreetingProperties properties) {
        this.properties = properties;
    }



    @RequestMapping("/backend")
    public ResponseEntity<String> greeting(@RequestParam(value = "name", defaultValue = "Backend") String name) {
        Objects.requireNonNull(properties.getMessage(), "Greeting message was not set in the properties");

        String message = String.format(properties.getMessage(), name);

        if(THROW_ERROR) {
            return new ResponseEntity("Unexpected Error", HttpStatus.SERVICE_UNAVAILABLE);
        }

        int delay = Integer.valueOf(properties.getDelay());
        System.out.println("start : " + new Date());
        try {
            Thread.sleep(delay*1000);
        } catch (Exception e) {

        }
        
        System.out.println("end : " + new Date());
        
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @RequestMapping("/backend/reset")
    public void reset() {
        THROW_ERROR=false;
    }

    @RequestMapping("/backend/error")
    public void error() {
        THROW_ERROR=true;
    }
}
