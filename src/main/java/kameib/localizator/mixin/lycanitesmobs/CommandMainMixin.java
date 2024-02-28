package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.command.CommandMain;
import com.lycanitesmobs.core.mobevent.MobEvent;
import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CommandMain.class)
public abstract class CommandMainMixin {
    @Redirect(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/client/localisation/LanguageManager;translate(Ljava/lang/String;)Ljava/lang/String;",
                    ordinal = -1,
                    remap = false
            ),
            remap = Production.inProduction
    )
    // Return lang key instead of translating it server-side
    // All calls
    private String Lycanites_CommandMain_execute_dontTranslate(String key) {
        return key;
    }
    
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.invalid")
    // Line 195: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message0(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.debug.invalid")
    // Line 202: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message2(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @Redirect(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;",
                    ordinal = 0,
                    remap = false
            ),
            remap = Production.inProduction
    )
    // Capture mobEventName
    // Line 206: reply = reply.replace("%debug%", mobEventName);
    private String Lycanites_CommandMain_execute_getMobEventName(String instance, CharSequence debug, CharSequence mobEventName) {
        localizator$arg1 = (String) mobEventName;
        return instance;
    }
    
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 3,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation (lyc.command.debug.set)
    // Line 208: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message3(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText() + "_",
                new TextComponentTranslation(localizator$arg1));
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 4,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.spawners.invalid")
    // Line 214: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message4(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 5,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.spawners.reload")
    // Line 221: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message5(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }

    @Redirect(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;",
                    ordinal = 1,
                    remap = false
            ),
            remap = Production.inProduction
    )
    // Capture mobEventName
    // Line 228: reply = reply.replace("%value%", "" + SpawnerEventListener.testOnCreative);
    private String Lycanites_CommandMain_execute_getTestOnCreative(String instance, CharSequence value, CharSequence testOnCreative) {
        localizator$arg1 = (String) testOnCreative;
        return instance;
    }

    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 6,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.spawners.creative")
    // Line 208: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message6(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText() + "_",
                new TextComponentTranslation(localizator$arg1));
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 7,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.spawners.test" || "lyc.command.spawner.test.unknown")
    // Line 253: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message7(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 8,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.dungeon.invalid")
    // Line 262: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message8(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 9,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.dungeon.reload")
    // Line 269: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message9(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 10,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.dungeon.enable")
    // Line 279: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message10(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 11,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.dungeon.disable")
    // Line 288: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message11(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 12,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.dungeon.locate")
    // Line 294: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message12(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 13,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("common.none")
    // Line 299: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message13(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 14,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation (dungeonInstance.toString())
    // Line 307: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message14(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 15,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("Invalid command arguments, valid arguments are: spirit, focus")
    // Line 319: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message15(ITextComponent component) {
        return new TextComponentTranslation("lyc.command.player.invalidarguments");
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 16,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("Restored Player Spirit.")
    // Line 328: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message16(ITextComponent component) {
        return new TextComponentTranslation("lyc.command.player.spirit");
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 17,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("Restored Player Focus.")
    // Line 336: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message17(ITextComponent component) {
        return new TextComponentTranslation("lyc.command.player.focus");
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 18,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.creatures.invalid")
    // Line 342: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message18(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 19,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.creatures.reload")
    // Line 351: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message19(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 20,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.equipment.invalid")
    // Line 359: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message20(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 21,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.equipment.reload")
    // Line 366: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message21(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 22,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.beastiary.invalid")
    // Line 375: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message22(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 23,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.playeronly")
    // Line 381: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message23(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 24,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.beastiary.add.invalid")
    // Line 395: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message24(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 25,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.beastiary.add.unknown")
    // Line 410: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message25(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 26,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.beastiary.complete")
    // Line 440: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message26(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 27,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.beastiary.clear")
    // Line 448: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message27(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 28,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("Force sent a full Beastiary update packet.")
    // Line 458: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message28(ITextComponent component) {
        return new TextComponentTranslation("lyc.command.beastiary.packet");
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 29,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.invalid")
    // Line 466: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message29(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 30,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.reload")
    // Line 473: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message30(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }

    @Redirect(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;",
                    ordinal = 2,
                    remap = false
            ),
            remap = Production.inProduction
    )
    // Capture testOnCreative
    // Line 480: reply = reply.replace("%value%", "" + MobEventPlayerServer.testOnCreative);
    private String Lycanites_CommandMain_execute_getTestOnCreative2(String instance, CharSequence value, CharSequence testOnCreative) {
        localizator$arg1 = (String) testOnCreative;
        return instance;
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 31,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.creative")
    // Line 481: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message31(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText() + "_",
                localizator$arg1);
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 32,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.start.invalid")
    // Line 488: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message32(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 33,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.start.noworld")
    // Line 509: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message33(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 34,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.enable")
    // Line 515: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message34(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 35,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.start.conditions")
    // Line 537: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message35(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 36,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.start")
    // Line 552: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message36(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 37,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.start.unknown")
    // Line 495: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message37(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 38,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.start.noworld")
    // Line 566: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message38(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 39,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.random")
    // Line 579: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message39(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 40,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.stop")
    // Line 587: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message40(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 41,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.list")
    // Line 594: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message41(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    
    @Redirect(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/mobevent/MobEvent;getTitle()Ljava/lang/String;",
                    remap = false
            ),
            remap = Production.inProduction
    )
    // Capture mobEvent
    // Line 599: eventName = mobEvent.name + " (" + mobEvent.getTitle() + ")";
    private String Lycanites_CommandMain_execute_captureMobEvent(MobEvent instance) {
        localizator$myMobEvent = instance;
        return instance.getTitle();
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 42,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Send a translated mobEvent Title
    // Line 600: commandSender.sendMessage(new TextComponentString(eventName));
    private ITextComponent Lycanites_CommandMain_event_Message42(ITextComponent component) {
        return new TextComponentString(localizator$myMobEvent.name + " (")
                            .appendSibling(new TextComponentTranslation("mobevent." + localizator$myMobEvent.title + ".name"))
                            .appendText(")");
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 43,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.enable.random")
    // Line 610: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message43(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 44,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.enable")
    // Line 618: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message44(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 45,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.disable.random")
    // Line 628: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message45(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 46,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.mobevent.disable")
    // Line 636: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message46(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 47,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with TextComponentTranslation ("lyc.command.invalid" ?)
    // Line 644: commandSender.sendMessage(new TextComponentString(reply));
    private ITextComponent Lycanites_CommandMain_execute_Message47(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    
    @Unique
    private String localizator$arg1;
    @Unique
    private MobEvent localizator$myMobEvent;
}
