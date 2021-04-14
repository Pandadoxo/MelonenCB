package de.meloneoderso.melonencb.commands;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandEditor {

    private static final HashMap<String, String> blockedCommands;

    static {
        blockedCommands = new HashMap<>();
    }

    private final String command;

    public CommandEditor(String command) {
        this.command = command;
    }

    public void add(String msg) {
        blockedCommands.put(this.command, msg);
    }

    public void send(Player p) {
        p.sendMessage(blockedCommands.get(this.command));
    }

    public boolean contains() {
        return blockedCommands.containsKey(this.command);
    }
}
