package com.example.springboot.providers;

import com.example.springboot.dao.CountryDao;
import com.example.springboot.dao.LeagueDao;
import com.example.springboot.exception.CustomExceptionHandler;
import com.example.springboot.exception.MissingParameterException;
import com.example.springboot.model.League;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class LeagueProvider {

    @Autowired
    CountryDao countryDao;

    @Autowired
    LeagueDao leagueDao;

    public ResponseEntity<Object> getObjectResponseEntity(@RequestBody String requestBody, HttpStatus status, Long id) {
        JSONObject requestObject = new JSONObject(requestBody);
        JSONObject response = new JSONObject();
        if (requestObject.has("countryId") && requestObject.has("leagueName")
                && !requestObject.getString("leagueName").isEmpty() && requestObject.getLong("countryId") > 0) {
            if (countryDao.exists(requestObject.getLong("countryId"))) {
                League league;
                if (id != null && id > 0){
                    league = leagueDao.findOne(id);
                    league.setCountry(countryDao.findOne(requestObject.getLong("countryId")));
                    league.setLeagueName(requestObject.getString("leagueName"));
                } else {
                    league = new League(requestObject.getString("leagueName"),
                            countryDao.findOne(requestObject.getLong("countryId")));
                }
                league = leagueDao.save(league);
                response.put("id",league.getId());
                response.put("leagueName", league.getLeagueName());
                response.put("country", league.getCountry().getCountryName());
                return new ResponseEntity<>(response.toString(), status);
            }
            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("country is not exist"));
        }
        return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("leagueName and countryId is required"));
    }
}
