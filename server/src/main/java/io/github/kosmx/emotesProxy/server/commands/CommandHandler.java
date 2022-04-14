package io.github.kosmx.emotesProxy.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import io.github.kosmx.emotesProxy.server.Server;
import io.github.kosmx.emotesProxy.server.ServerState;

import java.util.Objects;
import java.util.Scanner;

public final class CommandHandler extends Thread {
    final Server server;

    private final Scanner scanner = new Scanner(System.in);
    CommandDispatcher<ICommandSource> dispatcher = new CommandDispatcher<>();

    final ServerCommandSource commandSource;

    public CommandHandler(Server server){
        Objects.requireNonNull(server);
        this.server = server;
        this.commandSource = new ServerCommandSource(server);
    }

    @Override
    public void run(){

        Commands.registerCommands(dispatcher);

        loop();

    }

    public void loop(){
        while(true){
            if(server.getState().getOrder() >= ServerState.CLOSING.getOrder()) return;
            String in = scanner.nextLine();
            try {
                dispatcher.execute(dispatcher.parse(in, commandSource));
            }catch(Throwable exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
