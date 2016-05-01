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
