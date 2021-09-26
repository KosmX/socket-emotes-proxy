package io.github.kosmx.nettytest.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;


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

    }

    private static LiteralArgumentBuilder<ICommandSource> literal(String s){
        return LiteralArgumentBuilder.literal(s);
    }

}
