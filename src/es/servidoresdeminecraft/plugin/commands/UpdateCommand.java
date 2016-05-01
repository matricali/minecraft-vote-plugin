package es.servidoresdeminecraft.plugin.commands;

import es.servidoresdeminecraft.plugin.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Jorge Matricali <jorgematricali@gmail.com>
 */

public class UpdateCommand implements CommandExecutor {
    
    public static Main plugin;
    
    public UpdateCommand(Main instance) {
        plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("servidoresdeminecraft.es.actualizar")) { 
            String act = plugin.getUpdater().checkearVersion();
            if (act != null) {
                sender.sendMessage(plugin.getChatPrefix() + plugin.Colorear("&a" + act));
                
                System.out.println("[%s] Version: %s".format(plugin.getName(), act));

            }
        } else {
            String msg4 = plugin.getChatPrefix()+"&cNo tienes permiso para ejecutar este comando.";
            sender.sendMessage(plugin.Colorear(msg4));
        }
        return true;
    }
    
}
