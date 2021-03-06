package io.github.kosmx.emotesProxy.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;


public class FabricLoader implements ClientModInitializer {
    public static EmoteProxy proxy = new EmoteProxy();

    @Override
    public void onInitializeClient() {
        io.github.kosmx.emotes.api.proxy.EmotesProxyManager.registerProxyInstance(proxy);

        ClientCommandRegistrationCallback.EVENT.register(FabricLoader.this::addCommands);

        ClientTickEvents.END_CLIENT_TICK.register(client -> proxy.tick());

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> proxy.disconnect());
    }

    public void addCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {

        dispatcher.register(literal("emoteProxy")
                .then(literal("state")
                        .executes(context->{
                            var message = "wat?";

                            if(proxy.getServerData() != null) {
                                if(proxy.isActive()) {
                                    message = "EmoteX Proxy connected to " + proxy.getServerData();
                                } else {
                                    message = "Trying to connect to " + proxy.getServerData();
                                }

                            } else {
                                message = "EmoteX Proxy is not active";
                            }

                            context.getSource().sendFeedback(Text.literal(message));
                            return 0;
                        }))
                .then(literal("connect")
                        .then(literal("clear")
                                .executes(context->{
                                    proxy.disconnect();
                                    return 0;
                                }))
                        .then(argument("server", StringArgumentType.word())
                                .then(argument("port", IntegerArgumentType.integer(0))
                                        .executes(context->{
                                            var server = StringArgumentType.getString(context, "server");
                                            var port = IntegerArgumentType.getInteger(context, "port");

                                            proxy.setServerData(new EmoteProxy.ServerData(server, port));
                                            return 0;
                                        })
                                )
                        )
                )
        );
    }

}
