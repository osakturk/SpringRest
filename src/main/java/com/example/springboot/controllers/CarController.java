package com.example.springboot.controllers;

import com.example.springboot.enumerations.CarTypeEnum;
import com.example.springboot.exception.CustomExceptionHandler;
import com.example.springboot.exception.MissingParameterException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    @GetMapping("/")
    public String index() {
        return "Hello Pia/Vodafone";
    }

    @PostMapping(path = "/car", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json")
    public ResponseEntity<Object> getType(@RequestHeader(name = "X-COM-LOCATION", required = false, defaultValue = "ASIA")
                                                  String headerLocation,
                                          @RequestBody String requestBody) {
        JSONObject request = new JSONObject(requestBody);
        if (request.has("type")) {
            if (CarTypeEnum.fromKey(request.getString("type")) != null) {
                JSONObject response = new JSONObject();
                response.put("response", CarTypeEnum.fromKey(request.getString("type").toLowerCase()).getResponse());
                return new ResponseEntity<>(response.toString(), HttpStatus.OK);
            }
            return new CustomExceptionHandler().handleUnexpectedField(new MissingParameterException("unexpected type"));
        }
        return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("type is required"));
    }
}
