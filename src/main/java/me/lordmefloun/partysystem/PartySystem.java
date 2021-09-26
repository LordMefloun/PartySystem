package me.lordmefloun.partysystem;

import net.md_5.bungee.api.plugin.Plugin;

public final class PartySystem extends Plugin {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new PartyCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
