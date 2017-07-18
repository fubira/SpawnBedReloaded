package com.xern.jogy34.spawnbed.general;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

public class EventListeners implements Listener
{
    public EventListeners(SpawnBedMain plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void setBed(final PlayerInteractEvent event)
    {
        // SpawnBedMain.logger.info("setBed: " + event.getAction() + " " + event.getClickedBlock().getType());
        try
        {
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.BED_BLOCK)
            {
                Player p = event.getPlayer();
                Block b = event.getClickedBlock();
                Location loc = b.getLocation();
                plugin.getConfig().set((new StringBuilder(String.valueOf(p.getName()))).append(".X").toString(), Integer.valueOf(loc.getBlockX()));
                plugin.getConfig().set((new StringBuilder(String.valueOf(p.getName()))).append(".Y").toString(), Integer.valueOf(loc.getBlockY()));
                plugin.getConfig().set((new StringBuilder(String.valueOf(p.getName()))).append(".Z").toString(), Integer.valueOf(loc.getBlockZ()));
                plugin.getConfig().set((new StringBuilder(String.valueOf(p.getName()))).append(".World").toString(), loc.getWorld().getName());
                plugin.saveConfig();
                p.sendMessage((new StringBuilder()).append(ChatColor.GOLD).append("[Spawn Bed] You will now respawn at this bed when you die").toString());
            }
        }
        catch (Exception e){
            SpawnBedMain.logger.info("setBed: invalid PlayerInteractEvent");
        }
    }

    @EventHandler
    public void respawnAtBed(final PlayerRespawnEvent event)
    {
        if(plugin.getConfig().contains(event.getPlayer().getName()))
        {
            Player p = event.getPlayer();
            int x = plugin.getConfig().getInt((new StringBuilder(String.valueOf(p.getName()))).append(".X").toString());
            int y = plugin.getConfig().getInt((new StringBuilder(String.valueOf(p.getName()))).append(".Y").toString());
            int z = plugin.getConfig().getInt((new StringBuilder(String.valueOf(p.getName()))).append(".Z").toString());
            String w = plugin.getConfig().getString((new StringBuilder(String.valueOf(p.getName()))).append(".World").toString());
            World world = plugin.getServer().getWorld(w);
            Location loc = new Location(world, x, y, z);
            if(loc.getBlock().getType() == Material.BED_BLOCK)
                event.setRespawnLocation(loc.add(0.5D, 1.0D, 0.5D));
            else
                p.sendMessage((new StringBuilder()).append(ChatColor.RED).append("[Spawn Bed] Your bed is either blocked or destroyed").toString());
        }
    }

    private SpawnBedMain plugin;
}
