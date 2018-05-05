/*
 * Copyright (C) 2016-2018 Jorge Matricali
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
            String msg4 = plugin.getChatPrefix() + "&cNo tienes permiso para ejecutar este comando.";
            sender.sendMessage(plugin.Colorear(msg4));
        }
        return true;
    }

}
