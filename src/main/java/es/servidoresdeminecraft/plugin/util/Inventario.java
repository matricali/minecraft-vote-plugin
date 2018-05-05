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
package es.servidoresdeminecraft.plugin.util;

import es.servidoresdeminecraft.plugin.Main;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Jorge Matricali <jorgematricali@gmail.com>
 */
public class Inventario {

    public static Main plugin;
    public Inventory[] invOriginal = new Inventory[9999999];
    public Inventory[] invPremio = new Inventory[9999999];
    public Inventory[] invFinal = new Inventory[9999999];

    public Inventario(Main instance) {
        plugin = instance;
    }

    public Inventory getKit(Player player) {
        invOriginal[player.getEntityId()] = Bukkit.createInventory(player, 36);
        invPremio[player.getEntityId()] = Bukkit.createInventory(player, 36);
        invFinal[player.getEntityId()] = Bukkit.createInventory(player, 36);

        invOriginal[player.getEntityId()].setContents(player.getInventory().getContents());
        invFinal = invOriginal;

        @SuppressWarnings("unchecked")
        List<String> kitlist = plugin.listaItems;
        Iterator<String> iterator = kitlist.iterator();
        while (iterator.hasNext()) {
            ItemStack is = Inventario.setItemStack(iterator.next());
            invFinal[player.getEntityId()].addItem(is);
        }
        return invFinal[player.getEntityId()];
    }

    public void darPremio(Player player) {
        player.getInventory().setContents(getKit(player).getContents());
    }

    @SuppressWarnings("deprecation")
    public static ItemStack setItemStack(String string) {
        ItemStack is;
        try {
            String values[] = string.split(",");
            if (values.length > 2) {
                if (values.length > 8) {
                    is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Short.parseShort(values[2]));
                    is = addEnchantment(is, values[3], values[4]);
                    is = addEnchantment(is, values[5], values[6]);
                    is = addEnchantment(is, values[7], values[8]);
                } else if (values.length > 6) {
                    is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Short.parseShort(values[2]));
                    is = addEnchantment(is, values[3], values[4]);
                    is = addEnchantment(is, values[5], values[6]);
                } else if (values.length > 4) {
                    is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Short.parseShort(values[2]));
                    is = addEnchantment(is, values[3], values[4]);
                } else {
                    is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Short.parseShort(values[2]));
                }
            } else {
                is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            }
        } catch (NumberFormatException e) {
            is = new ItemStack(0, 1);
            System.out.println("No se ha podido parsear un ItemStack! es.servidoresdeminecraft.plugin.util.Metodos.setItemStack(String string)");
        }
        return is;
    }

    public static ItemStack addEnchantment(ItemStack is, String name, String level) {
        switch (name.toLowerCase()) {
            case "power":
                is.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, Integer.parseInt(level));
                break;
            case "flame":
                is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, Integer.parseInt(level));
                break;
            case "infinity":
                is.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, Integer.parseInt(level));
                break;
            case "punch":
                is.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, Integer.parseInt(level));
                break;
            case "sharpness":
                is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, Integer.parseInt(level));
                break;
            case "arthropods":
                is.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, Integer.parseInt(level));
                break;
            case "smite":
                is.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, Integer.parseInt(level));
                break;
            case "efficiency":
                is.addUnsafeEnchantment(Enchantment.DIG_SPEED, Integer.parseInt(level));
                break;
            case "unbreaking":
                is.addUnsafeEnchantment(Enchantment.DURABILITY, Integer.parseInt(level));
                break;
            case "fireaspect":
                is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, Integer.parseInt(level));
                break;
            case "knockback":
                is.addUnsafeEnchantment(Enchantment.KNOCKBACK, Integer.parseInt(level));
                break;
            case "fortune":
                is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, Integer.parseInt(level));
                break;
            case "looting":
                is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, Integer.parseInt(level));
                break;
            case "respiration":
                is.addUnsafeEnchantment(Enchantment.OXYGEN, Integer.parseInt(level));
                break;
            case "protection":
                is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Integer.parseInt(level));
                break;
            case "blastresistance":
                is.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, Integer.parseInt(level));
                break;
            case "featherfalling":
                is.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, Integer.parseInt(level));
                break;
            case "fireprotection":
                is.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, Integer.parseInt(level));
                break;
            case "projectileprotection":
                is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, Integer.parseInt(level));
                break;
            case "silktouch":
                is.addUnsafeEnchantment(Enchantment.SILK_TOUCH, Integer.parseInt(level));
                break;
            case "thorns":
                is.addUnsafeEnchantment(Enchantment.THORNS, Integer.parseInt(level));
                break;
            case "aquaaffinity":
                is.addUnsafeEnchantment(Enchantment.WATER_WORKER, Integer.parseInt(level));
                break;
        }
        return is;
    }
}
