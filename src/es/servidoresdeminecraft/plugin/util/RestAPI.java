/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.servidoresdeminecraft.plugin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author Jorge Matricali <jorgematricali@gmail.com>
 */
public class RestAPI {

    static final String endpointAPI = "http://servidoresdeminecraft.es/api/v1";

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    public static JSONObject getJsonResponse(String resource_uri) {
        try {
            InputStream is = new URL(endpointAPI + resource_uri).openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            
            JSONParser parser=new JSONParser();
            
            Object json = parser.parse(jsonText);
            return (JSONObject) json;
            
        } catch (MalformedURLException ex) {
            Bukkit.getLogger().log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Bukkit.getLogger().log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
