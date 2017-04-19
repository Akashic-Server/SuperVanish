/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package de.myzelyam.supervanish.hooks;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import de.myzelyam.supervanish.SuperVanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class EssentialsHook {

    private static SuperVanish plugin = (SuperVanish) Bukkit.getPluginManager()
            .getPlugin("SuperVanish");

    public static void hidePlayer(Player p) {
        try {
            Essentials essentials = (Essentials) Bukkit.getPluginManager()
                    .getPlugin("Essentials");
            if (essentials == null)
                return;
            User u = essentials.getUser(p);
            if (u == null)
                return;
            if (!u.isHidden())
                u.setHidden(true);
        } catch (Exception e) {
            plugin.printException(e);
        }
    }

    public static void showPlayer(Player p) {
        try {
            Essentials essentials = (Essentials) Bukkit.getPluginManager()
                    .getPlugin("Essentials");
            if (essentials == null)
                return;
            User u = essentials.getUser(p);
            if (u == null)
                return;
            if (u.isHidden())
                u.setHidden(false);
        } catch (Exception e) {
            plugin.printException(e);
        }
    }
}
