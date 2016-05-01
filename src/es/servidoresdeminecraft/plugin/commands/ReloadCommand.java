package es.servidoresdeminecraft.plugin.commands;

import es.servidoresdeminecraft.plugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Jorge Matricali <jorgematricali@gmail.com>
 */

public class ReloadCommand implements CommandExecutor {
    
    public static Main plugin;
    
    public ReloadCommand(Main instance) {
        plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("servidoresdeminecraft.es.recargar")) {

            plugin.reloadConfig();

            sender.sendMessage(
                plugin.getChatPrefix(true) + plugin.Colorear("&aConfiguración recargada correctamente\n") +
                plugin.getChatPrefix(true) + plugin.Colorear("&aFuncionando con la versión " + plugin.getVersion())
            );

            System.out.println("[%s] Configuracion recargada".format(plugin.getName()));
        } else {
            sender.sendMessage(plugin.getChatPrefix() + plugin.Colorear("&cNo tienes permiso para ejecutar este comando."));
        }
        
        return true;
    }
    
}
