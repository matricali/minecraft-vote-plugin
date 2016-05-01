package es.servidoresdeminecraft.plugin;

import es.servidoresdeminecraft.plugin.commands.ReloadCommand;
import es.servidoresdeminecraft.plugin.commands.UpdateCommand;
import es.servidoresdeminecraft.plugin.commands.VoteCommand;
import es.servidoresdeminecraft.plugin.util.Inventario;
import es.servidoresdeminecraft.plugin.util.Version;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.ChatColor;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Jorge Matricali <jorgematricali@gmail.com>
 */

public class Main extends JavaPlugin {
    
    private Version updater;
    private final Inventario inv = new Inventario(this);
    
    private final String prefijo_chat = "&a&l[&bServidoresDeMinecraft.ES&a&l] ";
    public int configVer = 0;
    public final int configActual = 2;
    public boolean comandosCustom = true;
    public boolean premioFisico = true;
    public List<String> listaComandos;
    public List<String> listaItems;
    
    @Override
    public void onEnable() {
        debugLog("Modo debug: activado");
        /*
         * Generar y cargar Config.yml
         */
        debugLog("Cargando configuración...");
        cargarConfig();
        /*
         * Comandos y eventos
         */
        PluginManager pluginManager = this.getServer().getPluginManager();
        debugLog("Registrando comandos...");
        
        this.getCommand("srvmc").setExecutor(new VoteCommand(this));
        this.getCommand("srvmc-update").setExecutor(new UpdateCommand(this));
        this.getCommand("srvmc-reload").setExecutor(new ReloadCommand(this));
        
        /*
         * Finalizar...
         */
        updater = new Version(this, this.getVersion());
        String actualizacion = updater.checkearVersion();
        if (actualizacion != null) {
            if (!actualizacion.equalsIgnoreCase("version actualizada")) { 
                log(actualizacion);
            }
        }
        log("Plugin de servidoresdeminecraft.es v"+this.getVersion()+" cargado correctamente.");
    }
    
    public void cargarConfig() {
        File file = new File(getDataFolder() + File.separator + "config.yml");
        if (!file.exists()) {
            try {
                getConfig().options().copyDefaults(true);
                saveConfig();
                log("Configuración por defecto generada correctamente");
            } catch (Exception e) {
                this.getLogger().info("Error al generar la configuración por defecto!");
                debugLog("Causa: "+e.toString());
            }
        }
        configVer = this.getConfig().getInt("configVer", configVer);
        if (configVer == 0) { 
        } else if (configVer < configActual) {
            log(Level.SEVERE, "Tu configuración es de una versión más antigua a la de este plugin!"
                + "Corrigelo o podrás tener errores..." );
        }
        comandosCustom = this.getConfig().getBoolean("comandosCustom.activado", comandosCustom);
        premioFisico = this.getConfig().getBoolean("premioFisico.activado", premioFisico);
        
        if (comandosCustom) {
            try {
                listaComandos = this.getConfig().getStringList("comandosCustom.comandos");
            } catch (NullPointerException e) {
                log(Level.WARNING, "No se ha podido cargar los premios de comandos customizados! (Error Config)");
                comandosCustom = false;
            }    
        }
        
        if (premioFisico) {
            try {
                listaItems = this.getConfig().getStringList("premioFisico.items");
            } catch (NullPointerException e) {
                log(Level.WARNING, "No se ha podido cargar los premios fisicos! (Error Config)");
                premioFisico = false;
            }
        }
        
    }
    
    
    public boolean isDebug() {
        return this.getConfig().getBoolean("debug", false);
    }
    
    public void debugLog(String s) {
        if (isDebug()){
            getLogger().log(Level.INFO, "[Debug] {0}", s);
        }
    }
    
    public void log(String s) {
        getLogger().log(Level.INFO, s);
    }
    
    public void log(Level l, String s){
        getLogger().log(l, s);
    }

    public String getVersion(){
        PluginDescriptionFile f = this.getDescription();
        return f.getVersion();
    }
    
    public String getChatPrefix(boolean force) {
        if(force || getConfig().getBoolean("prefijo-chat", true)){
            return ChatColor.translateAlternateColorCodes('&', prefijo_chat);
        }
        
        return "";
    }
    
    public String getChatPrefix() {
        return getChatPrefix(false);
    }
    
    public Version getUpdater() {
        return this.updater;
    }
    
    public Inventario getInv() {
        return this.inv;
    }
    
    public String Colorear(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    
    public String getMensaje(String key){
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("mensajes." + key, key));
    }
    
    public String getAPIToken(){        
        return getConfig().getString("token", "");
    }
    
}
