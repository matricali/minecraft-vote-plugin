/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.servidoresdeminecraft.plugin.restapi.resources;

import es.servidoresdeminecraft.plugin.util.RestAPI;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;

/**
 *
 * @author Jorge Matricali <jorgematricali@gmail.com>
 */
public class VoteClaim {
    private String _playername;
    private int _status = -1;
    private String _vote_link = "https://servidoresdeminecraft.es/";
    
    public static final int NO_HA_VOTADO = 3001;
    public static final int YA_HA_VOTADO = 3002;
    public static final int OK = 0;
    
    public VoteClaim(String api_token, String playername){
        _playername = playername;

        JSONObject r = RestAPI.getJsonResponse("/votes/claim/" + api_token + "/" + playername);
        
        if(r == null){
            Bukkit.getLogger().severe("Error al solicitar informacion de un voto, la respuesta es vacia.");
            return;
        }
        
        try {
            JSONObject response = (JSONObject) r.get("response");
            Long status = (Long) response.get("status");

            _status = status.intValue();
            
            if(_status == VoteClaim.NO_HA_VOTADO){
                try {
                    String vote_link = (String) response.get("vote_link");
                    _vote_link = vote_link;
                } catch (Exception ex){
                    //No hago nada :P
                }
            }
            
        } catch (Exception ex){
            Bukkit.getLogger().severe(ex.getLocalizedMessage());
        }
    }
    
    public int getStatus(){
        return _status;
    }
    
    public String getPlayername(){
        return _playername;
    }
    
    public String getVoteLink(){
        return _vote_link;
    }
}
