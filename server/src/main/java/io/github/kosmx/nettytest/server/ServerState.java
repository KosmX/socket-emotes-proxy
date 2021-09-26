package io.github.kosmx.nettytest.server;

import lombok.Getter;

public enum ServerState {
    INIT(0),
    RUNNING(10),
    /**
     * Close connections, still running
     */
    CLOSING(20),

    /**
     * All connections are closed, it is safe to shut the server down.
     */
    STOPPING(30);

    @Getter
    private final int order;

    ServerState(int order) {
        this.order = order;
    }
}
