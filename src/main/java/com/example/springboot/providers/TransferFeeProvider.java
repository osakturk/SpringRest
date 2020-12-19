package com.example.springboot.providers;

import com.example.springboot.dao.PlayerDao;
import com.example.springboot.dao.TransferFeeDao;
import com.example.springboot.exception.CustomExceptionHandler;
import com.example.springboot.exception.MissingParameterException;
import com.example.springboot.model.TransferFee;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class TransferFeeProvider {

    @Autowired
    PlayerDao playerDao;

    @Autowired
    TransferFeeDao transferFeeDao;

    public ResponseEntity<Object> getObjectResponseEntity(@RequestBody String requestBody, HttpStatus status, Long id) {
        JSONObject requestObject = new JSONObject(requestBody);
        JSONObject response = new JSONObject();
        if (requestObject.has("playerId") && requestObject.getLong("playerId") > 0){
            if (playerDao.exists(requestObject.getLong("playerId"))){
                TransferFee transferFee;
                if (id != null && id > 0){
                    transferFee = transferFeeDao.findOne(id);
                    transferFee.setFee(requestObject.has("fee") ? requestObject.getDouble("fee") : null);
                    transferFee.setPlayer(playerDao.findOne(requestObject.getLong("playerId")));
                } else {
                    transferFee = new TransferFee();
                    transferFee.setFee(requestObject.has("fee") ? requestObject.getDouble("fee") : null);
                    transferFee.setPlayer(playerDao.findOne(requestObject.getLong("playerId")));
                }
                transferFeeDao.save(transferFee);
                response.put("id", transferFee.getId());
                response.put("fee", transferFee.getFee());
                response.put("player", transferFee.getPlayer().getFullName());
                response.put("team", transferFee.getPlayer().getTeam().getTeamName());
                return new ResponseEntity<>(response.toString(), status);
            }
            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("player id is not exist"));
        }
        return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("player id is required"));
    }
}
