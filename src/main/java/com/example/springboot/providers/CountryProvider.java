package com.example.springboot.providers;

import com.example.springboot.dao.CountryDao;
import com.example.springboot.exception.CustomExceptionHandler;
import com.example.springboot.exception.MissingParameterException;
import com.example.springboot.model.Country;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class CountryProvider {

    @Autowired
    CountryDao countryDao;

    public ResponseEntity<Object> getObjectResponseEntity(@RequestBody String requestBody, HttpStatus status, Long id) {
        JSONObject requestObject = new JSONObject(requestBody);
        JSONObject response = new JSONObject();
        if (requestObject.has("countryName") && !requestObject.getString("countryName").isEmpty()){
            Country country;
            if (id != null && id > 0){
                country = countryDao.findOne(id);
                country.setCountryName(requestObject.getString("countryName"));
            } else {
                country = new Country(requestObject.getString("countryName"));
            }
            countryDao.save(country);
            response.put("id", country.getId());
            response.put("countryName", country.getCountryName());
            return new ResponseEntity<>(response.toString(), status);
        }

        return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("countryName is required"));
    }
}
