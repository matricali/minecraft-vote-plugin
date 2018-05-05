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
                    plugin.getChatPrefix(true) + plugin.Colorear("&aConfiguración recargada correctamente\n")
                    + plugin.getChatPrefix(true) + plugin.Colorear("&aFuncionando con la versión " + plugin.getVersion())
            );

            System.out.println("[%s] Configuracion recargada".format(plugin.getName()));
        } else {
            sender.sendMessage(plugin.getChatPrefix() + plugin.Colorear("&cNo tienes permiso para ejecutar este comando."));
        }

        return true;
    }

}
