package kameib.localizator.mixin.mca;

import com.google.common.base.Optional;
import kameib.localizator.data.Production;
import mca.command.CommandAdminMCA;
import mca.entity.EntityVillagerMCA;
import mca.entity.data.PlayerSaveData;
import mca.util.Util;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.UUID;

@Mixin(CommandAdminMCA.class)
public abstract class CommandAdminMCAMixin {
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/command/CommandAdminMCA;sendMessage(Lnet/minecraft/command/ICommandSender;Ljava/lang/String;)V",
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace string with lang key
    // Line 43: this.sendMessage(commandSender, "MCA admin commands have been disabled by the server administrator.");
    private String MCA_CommandAdminMCA_execute_sendMessage_langKey(String message) {
        return "command.mca.admin.disabled";
    }

    @ModifyArg(
            method = "forceFullHearts(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/command/CommandAdminMCA;sendMessage(Lnet/minecraft/command/ICommandSender;Ljava/lang/String;)V",
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace string with lang key
    // Line 111: this.sendMessage(player, "§AForced full hearts on all villagers.");
    private String MCA_CommandAdminMCA_forceFullHearts_sendMessage_langKey(String message) {
        return "command.mca.admin.forceFullHearts";
    }

    @ModifyArg(
            method = "forceBabyGrow(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/command/CommandAdminMCA;sendMessage(Lnet/minecraft/command/ICommandSender;Ljava/lang/String;)V",
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace string with lang key
    // Line 121: this.sendMessage(player, "§AForced any held babies to grow up age.");
    private String MCA_CommandAdminMCA_forceBabyGrow_sendMessage_langKey(String message) {
        return "command.mca.admin.forceBabyGrow";
    }

    @ModifyArg(
            method = "forceChildGrow(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/command/CommandAdminMCA;sendMessage(Lnet/minecraft/command/ICommandSender;Ljava/lang/String;)V",
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace string with lang key
    // Line 126: this.sendMessage(player, "§AForced any children to grow to adults.");
    private String MCA_CommandAdminMCA_forceChildGrow_sendMessage_langKey(String message) {
        return "command.mca.admin.forceChildGrow";
    }

    /**
     * @author KameiB
     * @reason Replace string with lang key
     */
    @Overwrite(remap = false)
    private void clearLoadedVillagers(EntityPlayer player) {
        int n = 0;

        for(Entity entity : player.world.loadedEntityList) {
            if (entity instanceof EntityVillagerMCA) {
                entity.setDead();
                ++n;
            }
        }

        player.sendMessage(new TextComponentString("§6[MCA] §r").appendSibling(new TextComponentTranslation("command.mca.admin.clearLoadedVillagers", n)));
    }

    @ModifyArg(
            method = "incrementHearts(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/command/CommandAdminMCA;sendMessage(Lnet/minecraft/command/ICommandSender;Ljava/lang/String;)V",
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace string with lang key
    // Line 150: this.sendMessage(player, "§AIncreased hearts for all villagers by 10.");
    private String MCA_CommandAdminMCA_incrementHearts_sendMessage_langKey(String message) {
        return "command.mca.admin.incrementHearts";
    }

    @ModifyArg(
            method = "decrementHearts(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/command/CommandAdminMCA;sendMessage(Lnet/minecraft/command/ICommandSender;Ljava/lang/String;)V",
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace string with lang key
    // Line 161: this.sendMessage(player, "§ADecreased hearts for all villagers by 10.");
    private String MCA_CommandAdminMCA_decrementHearts_sendMessage_langKey(String message) {
        return "command.mca.admin.decrementHearts";
    }

    /**
     * @author KameiB
     * @reason Replace plain text with TextComponentTranslation
     */
    @Overwrite(remap = false)
    private void resetVillagerData(EntityPlayer sender, String[] arguments) {
        Optional<EntityVillagerMCA> target = Util.getEntityByUUID(sender.world, UUID.fromString(arguments[0]), EntityVillagerMCA.class);
        if (!target.isPresent()) {
            this.sendMessage(sender, "command.mca.admin.resetVillagerData.notPresent");
        } else {
            target.get().reset();
            sender.sendMessage(new TextComponentTranslation("command.mca.admin.resetVillagerData.present", target.get().getDisplayName()));
        }
    }
    
    /**
     * @author KameiB
     * @reason Replace plain texts with TextComponentTranslation
     */
    @Overwrite(remap = false)
    private void resetPlayerData(EntityPlayer sender, String[] arguments) {
        Optional<Entity> target = Optional.fromJavaUtil(sender.world.loadedEntityList.stream().filter((e) -> e instanceof EntityPlayer && e.getName().equals(arguments[0])).findFirst());
        if (!target.isPresent()) {
            this.sendMessage(sender, "command.mca.admin.resetPlayerData.notPresent");
        } else {
            PlayerSaveData.get((EntityPlayer)target.get()).reset();
            sender.sendMessage(new TextComponentTranslation("command.mca.admin.resetPlayerData.present1", target.get().getName()));
            target.get().sendMessage(new TextComponentTranslation("command.mca.admin.resetPlayerData.present2", sender.getName()));
        }
    }

    @ModifyArg(
            method = "clearVillagerEditors(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/command/CommandAdminMCA;sendMessage(Lnet/minecraft/command/ICommandSender;Ljava/lang/String;)V",
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace string with lang key
    // Line 211: this.sendMessage(sender, "All villager editors cleared from inventories.");
    private String MCA_CommandAdminMCA_clearVillagerEditors_sendMessage_langKey(String message) {
        return "command.mca.admin.clearVillagerEditors";
    }
    
    /**
     * @author KameiB
     * @reason Replace 
     */
    @Overwrite(remap = false)
    private void sendMessage(ICommandSender commandSender, String message) {
        commandSender.sendMessage(new TextComponentString("§6[MCA] §r").appendSibling(new TextComponentTranslation(message)));
    }
    
    @ModifyArg(
            method = "sendMessage(Lnet/minecraft/command/ICommandSender;Ljava/lang/String;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace TextComponentString with TextComponentTranslation
    // Line 224: commandSender.sendMessage(new TextComponentString(message));
    private ITextComponent MCA_CommandAdminMCA_sendMessage2_localize(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    
    /**
     * @author KameiB
     * @reason Replace strings with lang keys
     */
    @Overwrite(remap = false)
    private void displayHelp(ICommandSender commandSender) {
        this.sendMessage(commandSender, "command.mca.admin.help.1", true);
        this.sendMessage(commandSender, "command.mca.admin.help.2", true);
        this.sendMessage(commandSender, "command.mca.admin.help.3", true);
        this.sendMessage(commandSender, "command.mca.admin.help.4", true);
        this.sendMessage(commandSender, "command.mca.admin.help.5", true);
        this.sendMessage(commandSender, "command.mca.admin.help.6", true);
        this.sendMessage(commandSender, "command.mca.admin.help.7", true);
        this.sendMessage(commandSender, "command.mca.admin.help.8", true);
        this.sendMessage(commandSender, "command.mca.admin.help.9", true);
        this.sendMessage(commandSender, "command.mca.admin.help.10", true);
        this.sendMessage(commandSender, "command.mca.admin.help.11", true);
        this.sendMessage(commandSender, "command.mca.admin.help.12", true);
        this.sendMessage(commandSender, "command.mca.admin.help.13", true);
        this.sendMessage(commandSender, "command.mca.admin.help.14", true);
    }
    
    @Shadow(remap = false)
    private void sendMessage(ICommandSender commandSender, String message, boolean noPrefix) {}
}
