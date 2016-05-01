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

package es.servidoresdeminecraft.plugin.util;

import es.servidoresdeminecraft.plugin.Main;
import org.json.simple.JSONObject;

/**
 *
 * @author Jorge Matricali <jorgematricali@gmail.com>
 */
public class Version {
    private final Main _plugin;
    private final String _versionInstalada;
    private final String _versionServidor;

    public Version(Main instance, String version) {
        _plugin = instance;
        _versionInstalada = version;
        _versionServidor = _plugin.getServer().getBukkitVersion().substring(0, _plugin.getServer().getBukkitVersion().indexOf("-"));
    }


    public String checkearVersion() {
        String a = null;
        try {
            
            JSONObject r = RestAPI.getJsonResponse("/plugin/version/" + _versionServidor);
            
            JSONObject response = (JSONObject) r.get("response");
        
//            if(r == null){
//                Bukkit.getLogger().severe("Error al solicitar informacion de un voto, la respuesta es vacia.");
//                return;
//            }

            String version = (String) response.get("version");
            
            if (_versionInstalada.matches(version)) {
                a = "Ya tienes la última versión del plugin.";
            } else {
                a = "Version desactualizada. Nueva versión: [{0}] v{1}".format(_versionServidor, version);
            }
            
            
        } catch (Exception ex) {
            a = "Error al comprobar actualizaciones.";
            _plugin.debugLog("Error obteniendo la versión. Causa: ");
            _plugin.debugLog(ex.getMessage());
        }
        return a;
    }

}
