/*
 * Copyright (C) 2016 ServidoresDeMinecraft.ES
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
