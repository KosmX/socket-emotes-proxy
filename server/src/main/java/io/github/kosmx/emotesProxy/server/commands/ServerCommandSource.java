package io.github.kosmx.emotesProxy.server.commands;

import io.github.kosmx.emotesProxy.server.Server;

public class ServerCommandSource implements ICommandSource{
    final Server server;

    public ServerCommandSource(Server server){
        this.server = server;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public boolean hasAdminRights() {
        return true;
    }
}
