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
package es.servidoresdeminecraft.plugin;

import es.servidoresdeminecraft.plugin.commands.ReloadCommand;
import es.servidoresdeminecraft.plugin.commands.VoteCommand;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin de votación
 * @link https://servidoresdeminecraft.es/
 * @see https://servidoresdeminecraft.es/plugin
 * @author Jorge Matricali <jorgematricali@gmail.com>
 */
public class Main extends JavaPlugin {

    private final String prefijo_chat = "&a&l[&bServidoresDeMinecraft.ES&a&l] ";
    private boolean debug = false;
    private boolean comandosPersonalizados;
    private List<String> listaComandos;

    @Override
    public void onEnable() {
        /**
         * Configuración
         */
        log("Cargando configuración...");
        cargarConfiguracion();
        
        /**
         * Comandos
         */
        log("Registrando comandos...");
        this.getCommand("srvmc").setExecutor(new VoteCommand(this));
        this.getCommand("srvmc-reload").setExecutor(new ReloadCommand(this));

        log("Plugin de servidoresdeminecraft.es v" + this.getVersion() + " cargado correctamente.");
    }

    public void cargarConfiguracion() {
        File file = new File(getDataFolder() + File.separator + "config.yml");

        if (!file.exists()) {
            try {
                getConfig().options().copyDefaults(true);
                saveConfig();
                log("Configuración por defecto generada correctamente");
            } catch (Exception e) {
                log("Error al generar la configuración por defecto!");
                debugLog("Causa: " + e.toString());
            }
        }

        debug = this.getConfig().getBoolean("debug", debug);
        comandosPersonalizados = this.getConfig().getBoolean("comandosPersonalizados.activado", comandosPersonalizados);

        if (comandosPersonalizados) {
            try {
                listaComandos = this.getConfig().getStringList("comandosPersonalizados.comandos");
            } catch (NullPointerException e) {
                log(Level.WARNING, "No se ha podido cargar los premios de comandos customizados! (Error Config)");
                comandosPersonalizados = false;
            }
        }
    }

    public boolean isDebug() {
        return debug;
    }

    public void debugLog(String s) {
        if (isDebug()) {
            getLogger().log(Level.INFO, "[Debug] {0}", s);
        }
    }

    public void log(String s) {
        getLogger().log(Level.INFO, s);
    }

    public void log(Level l, String s) {
        getLogger().log(l, s);
    }

    public String getVersion() {
        return this.getDescription().getVersion();
    }

    public String getChatPrefix(boolean force) {
        if (force || getConfig().getBoolean("prefijo-chat", true)) {
            return ChatColor.translateAlternateColorCodes('&', prefijo_chat);
        }

        return "";
    }
    
    public boolean enviarComandoActivado() {
        return comandosPersonalizados;
    }
    
    public List<String> getComandosPersonalizados() {
        return listaComandos;
    }

    public String getChatPrefix() {
        return getChatPrefix(false);
    }

    public String Colorear(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String getMensaje(String key) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("mensajes." + key, key));
    }
    
    public String buildMessage(String message) {
        return getChatPrefix() + Colorear(message);
    }

    public String getAPIToken() {
        return getConfig().getString("token", "");
    }

}
