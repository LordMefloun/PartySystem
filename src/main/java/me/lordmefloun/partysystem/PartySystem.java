package me.lordmefloun.partysystem;

import net.md_5.bungee.api.plugin.Plugin;

public final class PartySystem extends Plugin {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new PartyCommand(this));
        getProxy().getPluginManager().registerListener(this, new Listeners());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
