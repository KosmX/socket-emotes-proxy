package io.github.kosmx.nettytest.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.kosmx.nettytest.server.test.StreamedMessage;


public final class Commands {

    public static void registerCommands(CommandDispatcher<ICommandSource> dispatcher){

        dispatcher.register(literal("stop").executes(c -> {
            ICommandSource commandSource = c.getSource();
            if(commandSource.hasAdminRights()){
                commandSource.getServer().close();
                return 1;
            }
            return 0;
        }));

        dispatcher.register(literal("list").executes(c -> {
            ICommandSource commandSource = c.getSource();
            if (commandSource.hasAdminRights()) {
                System.out.println(commandSource.getServer().getConnections());
                return 1;
            }
            return 0;
        }));

        dispatcher.register(literal("msg").then(RequiredArgumentBuilder.argument("text", StringArgumentType.greedyString())).executes(c -> {
            ICommandSource commandSource = c.getSource();
            if (commandSource.hasAdminRights()) {
                var message = new StreamedMessage();
                message.setMsg(c.getArgument("text", String.class));
                for (var connection : commandSource.getServer().getConnections()) {
                    connection.sendMessage(message);
                }
                return 1;
            }
            return 0;
        }));
    }

    private static LiteralArgumentBuilder<ICommandSource> literal(String s){
        return LiteralArgumentBuilder.literal(s);
    }

}
