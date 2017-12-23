/*
 * Copyright © 2015, Leon Mangler and the SuperVanish contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package de.myzelyam.supervanish.events;

import de.myzelyam.supervanish.SuperVanish;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Collection;
import java.util.UUID;

public class PlayerBlockModifyEventHandler implements Listener {

    private final SuperVanish plugin;

    public PlayerBlockModifyEventHandler(SuperVanish plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent e) {
        try {
            if (!plugin.getVanishStateMgr().isVanished(e.getPlayer().getUniqueId()))
                return;
            if (e.getAction().equals(Action.PHYSICAL)) {
                if (!plugin.getSettings().getBoolean("InvisibilityFeatures.DisablePressurePlates"))
                    return;
                Material blockType = e.getClickedBlock().getType();
                if (blockType == Material.STONE_PLATE
                        || blockType == Material.GOLD_PLATE
                        || blockType == Material.IRON_PLATE
                        || blockType == Material.WOOD_PLATE
                        || blockType == Material.TRIPWIRE) {
                    e.setCancelled(true);
                }
            }
        } catch (Exception er) {
            plugin.logException(er);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent e) {
        try {
            if (plugin.getSettings().getBoolean("RestrictiveOptions.PreventBlockPlacing")) {
                Player p = e.getPlayer();
                Collection<UUID> vanishedPlayers = plugin.getVanishStateMgr().getOnlineVanishedPlayers();
                if (vanishedPlayers.contains(p.getUniqueId()) && !p.hasPermission("sv.placeblocks")) {
                    e.setCancelled(true);
                    plugin.sendMessage(e.getPlayer(), "BlockPlaceDenied", e.getPlayer());
                }
            }
        } catch (Exception er) {
            plugin.logException(er);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent e) {
        try {
            if (plugin.getSettings().getBoolean("RestrictiveOptions.PreventBlockBreaking")) {
                Player p = e.getPlayer();
                if (plugin.getVanishStateMgr().isVanished(p.getUniqueId()) && !p.hasPermission("sv.breakblocks")) {
                    e.setCancelled(true);
                    plugin.sendMessage(e.getPlayer(), "BlockBreakDenied", e.getPlayer());
                }
            }
        } catch (Exception er) {
            plugin.logException(er);
        }
    }
}
