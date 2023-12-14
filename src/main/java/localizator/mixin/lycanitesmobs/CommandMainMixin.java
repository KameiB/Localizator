package localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.ExtendedWorld;
import com.lycanitesmobs.LycanitesMobs;
import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.command.CommandMain;
import com.lycanitesmobs.core.config.ConfigBase;
import com.lycanitesmobs.core.dungeon.DungeonManager;
import com.lycanitesmobs.core.dungeon.instance.DungeonInstance;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.info.Beastiary;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.CreatureKnowledge;
import com.lycanitesmobs.core.info.CreatureManager;
import com.lycanitesmobs.core.item.equipment.EquipmentPartManager;
import com.lycanitesmobs.core.mobevent.MobEvent;
import com.lycanitesmobs.core.mobevent.MobEventListener;
import com.lycanitesmobs.core.mobevent.MobEventManager;
import com.lycanitesmobs.core.mobevent.MobEventPlayerServer;
import com.lycanitesmobs.core.network.MessageSummonSetSelection;
import com.lycanitesmobs.core.spawner.SpawnerEventListener;
import com.lycanitesmobs.core.spawner.SpawnerManager;
import com.lycanitesmobs.core.worldgen.WorldGeneratorDungeon;
import localizator.data.Production;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.apache.commons.lang3.math.NumberUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(CommandMain.class)
public abstract class CommandMainMixin {
    @Shadow(remap = Production.inProduction)
    public String getUsage(ICommandSender commandSender) { return "";}
    /**
     * @author KameiB
     * @reason Translate messages on client side, instead of on server side
     */
    @Overwrite(remap = Production.inProduction)
    public void execute(MinecraftServer server, ICommandSender commandSender, String[] args) {
        String reply = "lyc.command.invalid";
        if(args.length < 1) {
            commandSender.sendMessage(new TextComponentTranslation(reply));
            commandSender.sendMessage(new TextComponentString(this.getUsage(commandSender)));
            return;
        }

        // Debug:
        if("debug".equalsIgnoreCase(args[0])) {
            reply = "lyc.command.debug.invalid";
            if (args.length < 3) {
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            String debugValue = args[1];
            reply = "lyc.command.debug.set_";
            //reply = reply.replace("%debug%", debugValue);
            LycanitesMobs.config.setBool("Debug", debugValue, "true".equalsIgnoreCase(args[2]));
            commandSender.sendMessage(new TextComponentTranslation(reply, debugValue));
            return;
        }

        // Spawner:
        if("spawners".equalsIgnoreCase(args[0]) || "spawner".equalsIgnoreCase(args[0])) {
            reply = "lyc.command.spawners.invalid";
            if (args.length < 2) {
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Reload:
            if("reload".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.spawners.reload";
                SpawnerManager.getInstance().reload();
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Creative Test:
            if("creative".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.spawners.creative_";
                SpawnerEventListener.testOnCreative = !SpawnerEventListener.testOnCreative;
                //reply = reply.replace("%value%", "" + SpawnerEventListener.testOnCreative);
                commandSender.sendMessage(new TextComponentTranslation(reply, SpawnerEventListener.testOnCreative));
                return;
            }

            // Test:
            if("test".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.spawners.test";
                if(!(commandSender instanceof EntityPlayer)) {
                    return;
                }

                if(args.length < 3 || !SpawnerManager.getInstance().spawners.containsKey(args[2])) {
                    reply = "lyc.command.spawner.test.unknown";
                }
                String spawnerName = args[2];
                World world = commandSender.getEntityWorld();
                EntityPlayer player = (EntityPlayer)commandSender;
                BlockPos pos = player.getPosition();
                int level = 1;
                if(args.length > 3) {
                    level = Math.max(1, NumberUtils.isCreatable(args[3]) ? Integer.parseInt(args[3]) : 1);
                }

                SpawnerManager.getInstance().spawners.get(spawnerName).trigger(world, player, null, pos, level, 1, 0);
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }
        }

        // Dungeon:
        if("dungeon".equalsIgnoreCase(args[0]) || "dungeons".equalsIgnoreCase(args[0])) {
            reply = "lyc.command.dungeon.invalid";
            if (args.length < 2) {
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Reload:
            if("reload".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.dungeon.reload";
                DungeonManager.getInstance().reload();
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Enable:
            if("enable".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.dungeon.enable";
                ConfigBase config = ConfigBase.getConfig(LycanitesMobs.modInfo, "general");
                config.setBool("Dungeons", "Dungeons Enabled", true);
                LycanitesMobs.dungeonGenerator.enabled = true;
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Disable:
            if("disable".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.dungeon.disable";
                ConfigBase config = ConfigBase.getConfig(LycanitesMobs.modInfo, "general");
                config.setBool("Dungeons", "Dungeons Enabled", false);
                LycanitesMobs.dungeonGenerator.enabled = false;
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Locate:
            if("locate".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.dungeon.locate";
                commandSender.sendMessage(new TextComponentTranslation(reply));
                World world = commandSender.getEntityWorld();
                ExtendedWorld extendedWorld = ExtendedWorld.getForWorld(world);
                List<DungeonInstance> nearbyDungeons = extendedWorld.getNearbyDungeonInstances(new ChunkPos(commandSender.getPosition()), WorldGeneratorDungeon.DUNGEON_DISTANCE * 2);
                if(nearbyDungeons.isEmpty()) {
                    commandSender.sendMessage(new TextComponentTranslation("common.none"));
                    return;
                }
                for(DungeonInstance dungeonInstance : nearbyDungeons) {
                    commandSender.sendMessage(new TextComponentString(dungeonInstance.toString()));
                }
                return;
            }
        }

        // Player:
        if("player".equalsIgnoreCase(args[0])) {
            reply = "lyc.command.player.invalidarguments";
            if (args.length < 2) {
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }
            EntityPlayer player = (EntityPlayer)commandSender;
            ExtendedPlayer extendedPlayer = ExtendedPlayer.getForPlayer(player);

            // Restore Spirit:
            if("spirit".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.player.spirit";
                extendedPlayer.spirit = extendedPlayer.spiritMax;
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Restore Focus:
            if("focus".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.player.focus";
                extendedPlayer.summonFocus = extendedPlayer.summonFocusMax;
                CreatureManager.getInstance().reload();
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }
        }

        // Spawner:
        if("creature".equalsIgnoreCase(args[0]) || "creatures".equalsIgnoreCase(args[0])) {
            reply = "lyc.command.creatures.invalid";
            if (args.length < 2) {
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Reload:
            if("reload".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.creatures.reload";
                CreatureManager.getInstance().reload();
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }
        }

        // Equipment:
        if("equipment".equalsIgnoreCase(args[0])) {
            reply = "lyc.command.equipment.invalid";
            if (args.length < 2) {
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Reload:
            if("reload".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.equipment.reload";
                EquipmentPartManager.getInstance().reload();
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }
        }

        // Beastiary:
        if("beastiary".equalsIgnoreCase(args[0])) {
            reply = "lyc.command.beastiary.invalid";
            if (args.length < 2) {
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Player Only:
            if(!(commandSender instanceof EntityPlayer)) {
                reply = "lyc.command.playeronly";
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }
            EntityPlayer player = (EntityPlayer)commandSender;
            ExtendedPlayer extendedPlayer = ExtendedPlayer.getForPlayer(player);
            Beastiary beastiary = extendedPlayer.getBeastiary();
            if(extendedPlayer == null || beastiary == null) {
                return;
            }

            // Add:
            if("add".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.beastiary.add.invalid";
                if (args.length < 3) {
                    commandSender.sendMessage(new TextComponentTranslation(reply));
                    return;
                }

                int rank = 3;
                if(args.length >= 4) {
                    rank = NumberUtils.isCreatable(args[3]) ? Integer.parseInt(args[3]) : 3;
                }

                String creatureName = args[2].toLowerCase();
                CreatureInfo creatureInfo = CreatureManager.getInstance().getCreature(creatureName);
                if(creatureInfo == null) {
                    reply = "lyc.command.beastiary.add.unknown";
                    commandSender.sendMessage(new TextComponentTranslation(reply));
                    return;
                }

                CreatureKnowledge creatureKnowledge = new CreatureKnowledge(beastiary, creatureInfo.getName(), rank, 0);
                if(beastiary.addCreatureKnowledge(creatureKnowledge, true)) {
                    beastiary.sendAddedMessage(creatureKnowledge);
                    beastiary.sendToClient(creatureKnowledge);
                }
                else {
                    beastiary.sendKnownMessage(creatureKnowledge);
                }
                return;
            }

            // Complete:
            if("complete".equalsIgnoreCase(args[1])) {
                int rank = 3;
                if(args.length >= 3) {
                    rank = NumberUtils.isCreatable(args[2]) ? Integer.parseInt(args[2]) : 3;
                }

                for(CreatureInfo creatureInfo : CreatureManager.getInstance().creatures.values()) {
                    beastiary.addCreatureKnowledge(new CreatureKnowledge(beastiary, creatureInfo.getName(), rank, 0), true);
                }
                beastiary.sendAllToClient();
                reply = "lyc.command.beastiary.complete";
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Clear:
            if("clear".equalsIgnoreCase(args[1])) {
                beastiary.creatureKnowledgeList.clear();
                beastiary.sendAllToClient();
                reply = "lyc.command.beastiary.clear";
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Packet:
            if("packet".equalsIgnoreCase(args[1])) {
                beastiary.sendAllToClient();
                extendedPlayer.sendAllSummonSetsToPlayer();
                MessageSummonSetSelection message = new MessageSummonSetSelection(extendedPlayer);
                LycanitesMobs.packetHandler.sendToPlayer(message, (EntityPlayerMP)player);
                extendedPlayer.sendPetEntriesToPlayer("");
                commandSender.sendMessage(new TextComponentTranslation("lyc.command.beastiary.packet"));
                return;
            }
        }

        // Mob Event:
        if("mobevent".equalsIgnoreCase(args[0]) || "mobevents".equalsIgnoreCase(args[0])) {
            reply = "lyc.command.mobevent.invalid";
            if(args.length < 2) {
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Reload:
            if("reload".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.mobevent.reload";
                MobEventManager.getInstance().reload();
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Creative Test:
            if("creative".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.mobevent.creative_";
                MobEventPlayerServer.testOnCreative = !MobEventPlayerServer.testOnCreative;
                //reply = reply.replace("%value%", "" + MobEventPlayerServer.testOnCreative);
                commandSender.sendMessage(new TextComponentTranslation(reply, MobEventPlayerServer.testOnCreative));
                return;
            }

            // Start:
            if("start".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.mobevent.start.invalid";
                if(args.length < 3) {
                    commandSender.sendMessage(new TextComponentTranslation(reply));
                    return;
                }

                String mobEventName = args[2].toLowerCase();
                if(MobEventManager.getInstance().mobEvents.containsKey(mobEventName)) {

                    // Get World:
                    World world = null;
                    if(args.length >= 4 && NumberUtils.isNumber(args[3])) {
                        world = DimensionManager.getWorld(Integer.parseInt(args[3]));
                    }
                    else {
                        world = commandSender.getEntityWorld();
                    }

                    // No World:
                    if(world == null) {
                        reply = "lyc.command.mobevent.start.noworld";
                        commandSender.sendMessage(new TextComponentTranslation(reply));
                        return;
                    }

                    ExtendedWorld worldExt = ExtendedWorld.getForWorld(world);

                    // Force Enabled:
                    if(!MobEventManager.getInstance().mobEventsEnabled) {
                        reply = "lyc.command.mobevent.enable";
                        commandSender.sendMessage(new TextComponentTranslation(reply));
                        MobEventManager.getInstance().mobEventsEnabled = true;
                        ConfigBase config = ConfigBase.getConfig(LycanitesMobs.modInfo, "mobevents");
                        config.setBool("Global", "Mob Events Enabled", true);
                    }

                    // Get Player:
                    EntityPlayer player = null;
                    BlockPos pos = new BlockPos(0, 0, 0);
                    if(commandSender instanceof EntityPlayer) {
                        player = (EntityPlayer)commandSender;
                        pos = player.getPosition();
                    }

                    // Check Conditions:
                    boolean forced = false;
                    if (args.length >= 7) {
                        forced = Boolean.parseBoolean(args[6]);
                    }
                    MobEvent mobEvent = MobEventManager.getInstance().getMobEvent(mobEventName);
                    if (!forced && !mobEvent.canStart(world, player)) {
                        reply = "lyc.command.mobevent.start.conditions";
                        commandSender.sendMessage(new TextComponentTranslation(reply));
                        return;
                    }

                    int level = 1;
                    if(args.length >= 5 && NumberUtils.isNumber(args[4])) {
                        level = Integer.parseInt(args[4]);
                    }
                    int subspecies = -1;
                    if(args.length >= 6 && NumberUtils.isNumber(args[5])) {
                        subspecies = Integer.parseInt(args[5]);
                    }
                    reply = "lyc.command.mobevent.start";
                    commandSender.sendMessage(new TextComponentTranslation(reply));
                    worldExt.startMobEvent(mobEventName, player, pos, level, subspecies);
                    return;
                }

                reply = "lyc.command.mobevent.start.unknown";
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            // Get World:
            World world;
            if(args.length >= 3 && NumberUtils.isNumber(args[2])) {
                world = DimensionManager.getWorld(Integer.parseInt(args[2]));
            }
            else {
                world = commandSender.getEntityWorld();
            }

            // No World:
            if(world == null) {
                reply = "lyc.command.mobevent.start.noworld";
                commandSender.sendMessage(new TextComponentTranslation(reply));
                return;
            }

            LycanitesMobs.logDebug("", "Getting Extended World for Dimension: " + world.provider.getDimension() + " World: " + world);
            ExtendedWorld worldExt = ExtendedWorld.getForWorld(world);
            LycanitesMobs.logDebug("", "Got Extended World for Dimension: " + worldExt.world.provider.getDimension() + " World: " + worldExt.world);
            if(worldExt == null) return;

            // Random:
            if("random".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.mobevent.random";
                commandSender.sendMessage(new TextComponentTranslation(reply));
                worldExt.stopWorldEvent();
                MobEventListener.getInstance().triggerRandomMobEvent(world, worldExt);
                return;
            }

            // Stop:
            if("stop".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.mobevent.stop";
                commandSender.sendMessage(new TextComponentTranslation(reply));
                worldExt.stopWorldEvent();
                return;
            }

            // List:
            if("list".equalsIgnoreCase(args[1])) {
                reply = "lyc.command.mobevent.list";
                commandSender.sendMessage(new TextComponentTranslation(reply));
                for(MobEvent mobEvent : MobEventManager.getInstance().mobEvents.values()) {
                    //String eventName = mobEvent.name + " (" + mobEvent.getTitle() + ")";
                    commandSender.sendMessage(new TextComponentString(mobEvent.name + " (")
                            .appendSibling(new TextComponentTranslation("mobevent." + mobEvent.title + ".name"))
                            .appendText(")"));
                }
                return;
            }

            // Enable:
            if("enable".equalsIgnoreCase(args[1])) {
                if(args.length >= 3) {
                    if("random".equalsIgnoreCase(args[2])) {
                        reply = "lyc.command.mobevent.enable.random";
                        commandSender.sendMessage(new TextComponentTranslation(reply));
                        MobEventManager.getInstance().mobEventsRandom = true;
                        ConfigBase config = ConfigBase.getConfig(LycanitesMobs.modInfo, "mobevents");
                        config.setBool("Global", "Random Mob Events", true);
                        return;
                    }
                }
                reply = "lyc.command.mobevent.enable";
                commandSender.sendMessage(new TextComponentTranslation(reply));
                MobEventManager.getInstance().mobEventsEnabled = true;
                ConfigBase config = ConfigBase.getConfig(LycanitesMobs.modInfo, "mobevents");
                config.setBool("Global", "Mob Events Enabled", true);
                return;
            }

            // Disable:
            if("disable".equalsIgnoreCase(args[1])) {
                if(args.length >= 3) {
                    if("random".equalsIgnoreCase(args[2])) {
                        reply = "lyc.command.mobevent.disable.random";
                        commandSender.sendMessage(new TextComponentTranslation(reply));
                        MobEventManager.getInstance().mobEventsRandom = false;
                        ConfigBase config = ConfigBase.getConfig(LycanitesMobs.modInfo, "mobevents");
                        config.setBool("Global", "Random Mob Events", false);
                        return;
                    }
                }
                reply = "lyc.command.mobevent.disable";
                commandSender.sendMessage(new TextComponentTranslation(reply));
                MobEventManager.getInstance().mobEventsEnabled = false;
                ConfigBase config = ConfigBase.getConfig(LycanitesMobs.modInfo, "mobevents");
                config.setBool("Global", "Mob Events Enabled", false);
                return;
            }
        }

        commandSender.sendMessage(new TextComponentTranslation(reply));
        commandSender.sendMessage(new TextComponentString(this.getUsage(commandSender)));
    }
}
