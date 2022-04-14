package io.github.kosmx.emotesProxy.server.commands;

import io.github.kosmx.emotesProxy.server.Server;

public interface ICommandSource {
    Server getServer();

    boolean hasAdminRights();
}
