package com.example.springboot.providers;

import com.example.springboot.dao.LeagueDao;
import com.example.springboot.dao.TeamDao;
import com.example.springboot.exception.CustomExceptionHandler;
import com.example.springboot.exception.MissingParameterException;
import com.example.springboot.model.Team;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class TeamProvider {

    @Autowired
    LeagueDao leagueDao;

    @Autowired
    TeamDao teamDao;

    public ResponseEntity<Object> getObjectResponseEntity(@RequestBody String requestBody, HttpStatus status, Long id) {
        JSONObject requestObject = new JSONObject(requestBody);
        JSONObject response = new JSONObject();
        if (requestObject.has("teamName") && !requestObject.getString("teamName").isEmpty()) {
            if (requestObject.has("established") && requestObject.getInt("established") > 0) {
                if (requestObject.has("currency") && !requestObject.getString("currency").isEmpty()) {
                    if (requestObject.has("bossName") && !requestObject.getString("bossName").isEmpty()) {
                        if (requestObject.has("leagueId") && requestObject.getLong("leagueId") > 0) {
                            if (leagueDao.exists(requestObject.getLong("leagueId"))) {
                                Team team;
                                if (id != null && id > 0) {
                                    team = teamDao.findOne(id);
                                } else {
                                    team = new Team();
                                }
                                setTeam(requestObject, team);
                                team = teamDao.save(team);
                                response.put("id", team.getId());
                                response.put("name", team.getTeamName());
                                response.put("league", team.getLeague().getLeagueName());
                                response.put("bossName", team.getBossName());
                                return new ResponseEntity<>(response.toString(), status);
                            }
                            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("league is not exist"));
                        }
                        return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("league id is required"));
                    }
                    return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("boss name is required"));
                }
                return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("currency id is required"));
            }
            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("established is required"));
        }
        return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("team name is required"));
    }

    private void setTeam(JSONObject requestObject, Team team) {
        team.setBossName(requestObject.getString("bossName"));
        team.setTeamName(requestObject.getString("teamName"));
        team.setCurrency(requestObject.getString("currency"));
        team.setLeague(leagueDao.findOne(requestObject.getLong("leagueId")));
        team.setEstablished(requestObject.getInt("established"));
        team.setDescription(requestObject.has("description") ? requestObject.getString("description") : null);
    }
}
