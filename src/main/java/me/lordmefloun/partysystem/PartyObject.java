package me.lordmefloun.partysystem;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PartyObject {


    public HashMap<ProxiedPlayer, Roles> players = new HashMap<ProxiedPlayer, Roles>();
    public HashSet<ProxiedPlayer> invited = new HashSet<>();
    public static HashSet<PartyObject> parties = new HashSet<>();
    public int size;



    public PartyObject(ProxiedPlayer leader, int size){
        this.players.put(leader, Roles.LEADER);
        this.size = size;
        parties.add(this);
    }

    public enum Roles {
        LEADER,GUEST
    }

    public void invite(ProxiedPlayer p, ProxiedPlayer inviter){
        if (inviter == getLeader()) {
            if (!players.containsKey(p)) {
                if (!isInvited(p)) {
                    invited.add(p);
                    broadcastParty(Utils.getPrefix() + " &aHráč &d" + p.getDisplayName() + "&a byl pozván");
                } else {
                    broadcastParty(Utils.getPrefix() + " &aHráč &c" + p.getDisplayName() + "&c byl již pozván");
                }
            } else if (players.containsKey(p)) {
                broadcastParty(Utils.getPrefix() + " &cHráč &d" + p.getDisplayName() + "&c je již v party!");
            }
        }

    }

    public void join(ProxiedPlayer p){
        if (invited.contains(p)){
            if (!isInParty(p)) {
                invited.remove(p);
                players.put(p, Roles.GUEST);
                broadcastParty(Utils.getPrefix() + " &aHráč &d" + p.getDisplayName() + "&a se připojil do party");
            } else Utils.sendMessage(p, "&cJiž jsi nějaké party připojen");
        }else {
            Utils.sendMessage(p, "&cDo této party nejsi pozvaný");
        }
    }

    public void broadcastParty(String message) {
        for (Map.Entry<ProxiedPlayer, Roles> entry : players.entrySet()) {
            Utils.sendMessage(entry.getKey(), message);
        }
    }
    public ProxiedPlayer getLeader(){
        for (Map.Entry<ProxiedPlayer, Roles> entry : players.entrySet()) {
            if (entry.getValue() == Roles.LEADER){
                return entry.getKey();
            }
        }
        return null;
    }

    public void leave(ProxiedPlayer p){
        broadcastParty(Utils.getPrefix() + " &a" + p.getDisplayName() + "&c se odpojil z party");
        if (getLeader() == p) {
            destroy();
        } else{
            players.remove(p);
        }
    }

    public boolean isInvited(ProxiedPlayer p){
        if (invited.contains(p)){
            return true;
        }
        return false;
    }

    public static boolean isInParty(ProxiedPlayer player){
        for (PartyObject party: parties){
            for (Map.Entry<ProxiedPlayer, Roles> entry : party.players.entrySet()) {
                if (player == entry.getKey()) return true;
            }
        }

        return false;
    }

    public static PartyObject getPlayerParty(ProxiedPlayer player){
        for (PartyObject party: parties){
            for (Map.Entry<ProxiedPlayer, Roles> entry : party.players.entrySet()) {
                if (player == entry.getKey()) return party;
            }
        }
        return null;
    }

    public void destroy(){
        broadcastParty("&cParty byla zrušena");
        players.clear();
        invited.clear();
        PartyObject.parties.remove(this);
    }

    public void joinServer(ServerInfo server){
        for (Map.Entry<ProxiedPlayer, Roles> entry : players.entrySet()) {
            if (entry.getKey() != getLeader())
                entry.getKey().connect(server);
        }
    }
}
