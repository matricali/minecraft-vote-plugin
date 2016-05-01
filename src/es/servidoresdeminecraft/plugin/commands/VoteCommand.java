package es.servidoresdeminecraft.plugin.commands;

import es.servidoresdeminecraft.plugin.Main;
import es.servidoresdeminecraft.plugin.restapi.resources.VoteClaim;
import es.servidoresdeminecraft.plugin.util.RestAPI;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Jorge Matricali <jorgematricali@gmail.com>
 */

public class VoteCommand implements CommandExecutor {
    
    public static Main plugin;
    
    public VoteCommand(Main instance) {
        plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("servidoresdeminecraft.es.voto")) {
            if (sender instanceof Player) { 
                Player player = (Player)sender;
                VoteClaim vote = new VoteClaim(plugin.getAPIToken(), player.getName());

                switch (vote.getStatus()) {
                    case VoteClaim.NO_HA_VOTADO:
                        sender.sendMessage(plugin.getChatPrefix() + plugin.getMensaje("NO_HAS_VOTADO").replace("%LINK_VOTO%", vote.getVoteLink()));
                        break;
                        
                    case VoteClaim.OK:
                        if (plugin.getConfig().getBoolean("broadcast.activado")) {
                            String bc = plugin.getChatPrefix()+plugin.getConfig().getString("broadcast.mensajeBroadcast").replace("{0}", player.getDisplayName());
                            Bukkit.broadcastMessage(plugin.Colorear(bc));
                        }
                        
                        sender.sendMessage(plugin.getChatPrefix() + plugin.getMensaje("GRACIAS_POR_TU_VOTO"));
                        
                        if (plugin.premioFisico) {
                            plugin.getInv().darPremio(player);
                            System.out.println("[%s] %s ha obtenido un premio por votar".format(plugin.getName(), player));
                        }
                        
                        if (plugin.comandosCustom) {
                            for (String cmds : plugin.listaComandos) {
                                String comando = cmds.replace("%NOMBRE_JUGADOR%", player.getName());
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), comando);
                            }
                        }
                        
                        break;
                        
                    case VoteClaim.YA_HA_VOTADO:
                        sender.sendMessage(plugin.getChatPrefix() + plugin.getMensaje("YA_HAS_VOTADO"));
                        
                        break;
                        
                    default:
                        sender.sendMessage(plugin.getChatPrefix() + plugin.Colorear("&7Ha ocurrido un error. Intenta más tarde o da aviso a un adminstrador"));
                        
                        break;    
                }

            } else {
                sender.sendMessage(plugin.getChatPrefix() + plugin.Colorear("&cNo puedes usar este comando si no eres un jugador")); 
            }
        } else {
            sender.sendMessage(plugin.getChatPrefix() + plugin.Colorear("&cNo tienes permiso para ejecutar este comando."));
        }
        return true;
    }
    
}
