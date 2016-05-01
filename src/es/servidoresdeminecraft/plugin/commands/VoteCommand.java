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

package es.servidoresdeminecraft.plugin.commands;

import es.servidoresdeminecraft.plugin.Main;
import es.servidoresdeminecraft.plugin.restapi.resources.VoteClaim;

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
                Player player = (Player) sender;
                VoteClaim vote = new VoteClaim(plugin.getAPIToken(), player.getName());

                switch (vote.getStatus()) {
                    case VoteClaim.NO_HA_VOTADO:
                        sender.sendMessage(plugin.getChatPrefix() + plugin.getMensaje("NO_HAS_VOTADO").replace("%LINK_VOTO%", vote.getVoteLink()));
                        break;

                    case VoteClaim.OK:
                        if (plugin.getConfig().getBoolean("broadcast.activado")) {
                            String bc = plugin.getChatPrefix() + plugin.getConfig().getString("broadcast.mensajeBroadcast").replace("{0}", player.getDisplayName());
                            Bukkit.broadcastMessage(plugin.Colorear(bc));
                        }

                        sender.sendMessage(plugin.buildMessage(plugin.getMensaje("GRACIAS_POR_TU_VOTO")));

                        if (plugin.enviarComandoActivado()) {
                            for (String cmds : plugin.getComandosPersonalizados()) {
                                String comando = cmds.replace("%NOMBRE_JUGADOR%", player.getName());
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), comando);
                            }
                        }

                        break;

                    case VoteClaim.YA_HA_VOTADO:
                        sender.sendMessage(plugin.buildMessage(plugin.getMensaje("YA_HAS_VOTADO")));

                        break;

                    default:
                        sender.sendMessage(plugin.buildMessage("&7Ha ocurrido un error. Intenta más tarde o da aviso a un adminstrador"));

                        break;
                }

            } else {
                sender.sendMessage(plugin.buildMessage("&cNo puedes usar este comando si no eres un jugador"));
            }
        } else {
            sender.sendMessage(plugin.buildMessage("&cNo tienes permiso para ejecutar este comando."));
        }
        return true;
    }

}
