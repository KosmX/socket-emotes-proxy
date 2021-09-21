package io.github.kosmx.nettytest.server.commands;

import io.github.kosmx.nettytest.server.Server;

public class ServerCommandSource implements ICommandSource{
    final Server server;

    public ServerCommandSource(Server server){
        this.server = server;
    }

    @Override
    public Server getServer() {
        return server;
    }
}
