package com.xern.jogy34.spawnbed.general;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnBedMain extends JavaPlugin
{
    public static Logger logger;

    public SpawnBedMain()
    {
        logger = getLogger();
    }

    public void onEnable()
    {
        new EventListeners(this);
        getLogger().info("Spawn Bed Enabled");
    }

    public void onDisable()
    {
        getLogger().info("Spawn Bed Disabled");
    }
}
