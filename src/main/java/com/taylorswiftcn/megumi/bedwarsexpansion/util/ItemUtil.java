package com.taylorswiftcn.megumi.bedwarsexpansion.util;

import com.taylorswiftcn.megumi.bedwarsexpansion.BedwarsExpansion;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemUtil {
    private static BedwarsExpansion plugin = BedwarsExpansion.getInstance();

    public static boolean isEmpty(ItemStack item) {
        return item == null || item.getItemMeta() == null;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack createItem(String id, short data, Integer amount, String name, List<String> lore, HashMap<Enchantment, Integer> enchants) {
        ItemStack item;

        if (amount == null || amount == 0) amount = 1;
        if (WeiUtil.isNumber(id)) {
            if (plugin.getVersion().startsWith("V1_13")) return new ItemStack(Material.STONE, 1);
            item = new ItemStack(Material.getMaterial(Integer.parseInt(id)), amount);
        }
        else
            item = new ItemStack(Material.getMaterial(id), amount);
        item.setDurability(data);
        ItemMeta meta = item.getItemMeta();
        if (name != null)
            meta.setDisplayName(WeiUtil.onReplace(name));
        if (lore != null)
            meta.setLore(WeiUtil.onReplace(lore));
        if (enchants != null) {
            for (Map.Entry<Enchantment, Integer> map : enchants.entrySet()) {
                Enchantment enchantment = map.getKey();
                Integer level = map.getValue();
                meta.addEnchant(enchantment, level, false);
            }
        }
        item.setItemMeta(meta);
        return item;
    }
}
