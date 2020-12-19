package com.example.springboot.providers;

import com.example.springboot.dao.PlayerDao;
import com.example.springboot.dao.TeamDao;
import com.example.springboot.exception.CustomExceptionHandler;
import com.example.springboot.exception.MissingParameterException;
import com.example.springboot.model.Player;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class PlayerProvider {

    @Autowired
    TeamDao teamDao;

    @Autowired
    PlayerDao playerDao;

    public ResponseEntity<Object> getObjectResponseEntity(@RequestBody String requestBody, HttpStatus status, Long id) {
        JSONObject requestObject = new JSONObject(requestBody);
        JSONObject response = new JSONObject();
        if (requestObject.has("fullName") && !requestObject.getString("fullName").isEmpty()) {
            if (requestObject.has("position") && !requestObject.getString("position").isEmpty()) {
                if (requestObject.has("age") && requestObject.getInt("age") > 15) {
                    if (requestObject.has("matchCount") && requestObject.getInt("matchCount") > 0) {
                        if (requestObject.has("monthOfExperience") && requestObject.getInt("monthOfExperience") > 0) {
                            if (requestObject.has("teamId") && requestObject.getLong("teamId") > 0) {
                                if (teamDao.exists(requestObject.getLong("teamId"))) {
                                    Player player;
                                    if (id != null && id > 0) {
                                        player = playerDao.findOne(id);
                                    } else {
                                        player = new Player();
                                    }
                                    setPlayer(requestObject, player);
                                    player = playerDao.save(player);
                                    response.put("id", player.getId());
                                    response.put("name", player.getFullName());
                                    response.put("team", player.getTeam().getTeamName());
                                    response.put("league", player.getTeam().getLeague().getLeagueName());
                                    response.put("country", player.getTeam().getLeague().getCountry().getCountryName());
                                    return new ResponseEntity<>(response.toString(), status);
                                }
                                return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("team is not exist"));
                            }
                            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("team id is required"));
                        }
                        return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("month of experience is required"));
                    }
                    return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("match count is required"));
                }
                return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("age is required"));
            }
            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("position is required"));
        }
        return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("full name is required"));
    }

    private void setPlayer(JSONObject requestObject, Player player) {
        player.setAge(requestObject.getInt("age"));
        player.setFullName(requestObject.getString("fullName"));
        player.setFree(requestObject.has("isFree") && requestObject.getBoolean("isFree"));
        player.setMatchCount(requestObject.getInt("matchCount"));
        player.setMonthOfExprience(requestObject.getInt("monthOfExperience"));
        player.setPosition(requestObject.getString("position"));
        player.setTeam(teamDao.findOne(requestObject.getLong("teamId")));
    }
}
