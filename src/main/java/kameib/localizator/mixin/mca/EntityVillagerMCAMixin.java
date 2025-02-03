package kameib.localizator.mixin.mca;
import com.google.common.base.Optional;
import kameib.localizator.data.Production;
import mca.api.API;
import mca.core.MCA;
import mca.core.minecraft.ProfessionsMCA;
import mca.entity.EntityVillagerMCA;
import mca.entity.data.PlayerHistory;
import mca.enums.EnumAgeState;
import mca.enums.EnumGender;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import static mca.entity.EntityVillagerMCA.*;

@Mixin(EntityVillagerMCA.class)
public abstract class EntityVillagerMCAMixin extends EntityVillager {
    @Inject(
            method = "onDeath(Lnet/minecraft/util/DamageSource;)V",
            at = @At("HEAD"),
            remap = false
    )
    // Get causeName
    // Line 342: String causeName = cause.getImmediateSource() == null ? cause.getDamageType() : cause.getImmediateSource().getName();
    private void MCA_EntityVillagerMCA_onDeath_getCauseName(DamageSource cause, CallbackInfo ci) {
        if (!this.world.isRemote) {
            localizator$myCauseName = cause.getImmediateSource() == null ? cause.getDamageType() : cause.getImmediateSource().getName();
        }
    }
    
    @ModifyArg(
            method = "onDeath(Lnet/minecraft/util/DamageSource;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Correctly localize "notify.spousedied" message
    // Line 358: player.sendMessage(new TextComponentString("§C" + MCA.getLocalizer().localize("notify.spousedied", new String[]{(String)this.get(VILLAGER_NAME), causeName})));
    private ITextComponent MCA_EntityVillagerMCA_onDeath_sendMessage_spousedied(ITextComponent message) {
        return new TextComponentTranslation("notify.spousedied_", this.get(VILLAGER_NAME), localizator$myCauseName).setStyle(new Style().setColor(TextFormatting.RED));
    }

    @ModifyArg(
            method = "lambda$onDeath$3(Ljava/lang/String;Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Correctly localize "notify.childdied" message
    // Line 366: player.sendMessage(new TextComponentString("§C" + MCA.getLocalizer().localize("notify.childdied", new String[]{(String)this.get(VILLAGER_NAME), causeName})));
    private ITextComponent MCA_EntityVillagerMCA_onDeath_sendMessage_childdied(ITextComponent message) {
        return new TextComponentTranslation("notify.childdied_", this.get(VILLAGER_NAME), localizator$myCauseName).setStyle(new Style().setColor(TextFormatting.RED));
    }
    
    @ModifyArg(
            method = "lambda$onGrowingAdult$5(Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Correctly localize "notify.child.grownup" message
    // Line 379: e.sendMessage(new TextComponentString(MCA.getLocalizer().localize("notify.child.grownup", new String[]{(String)this.get(VILLAGER_NAME)})));
    private ITextComponent MCA_EntityVillagerMCA_onGrowingAdult_sendMessage_growUp(ITextComponent component) {
        return new TextComponentTranslation("notify.child.grownup_", this.get(VILLAGER_NAME));
    }

    /**
     * @author KameiB
     * @reason Make the villager's Display, localizable according to Localizator's framework.
     */
    @Nonnull
    @Overwrite(remap = false)
    public ITextComponent getDisplayName() {
        ITextComponent careerName = new TextComponentTranslation("entity.Villager." + this.getVanillaCareer().getName());
        EnumAgeState age = EnumAgeState.byId(this.get(AGE_STATE));
        //String professionName = age != EnumAgeState.ADULT ? age.localizedName() : careerName.getUnformattedText();
        ITextComponent professionName = age != EnumAgeState.ADULT ? new TextComponentTranslation(age.localizedName()) : careerName;
        TextFormatting color = this.getProfessionForge() == ProfessionsMCA.bandit ? TextFormatting.RED : (this.getProfessionForge() == ProfessionsMCA.guard ? TextFormatting.GREEN : TextFormatting.RESET);
        
        return (new TextComponentString(MCA.getConfig().villagerChatPrefix)
                .appendSibling(new TextComponentString(String.format("%s (", this.get(VILLAGER_NAME)))
                .appendSibling(professionName))
                .appendText(")")).setStyle(new Style().setColor(color));
        //return new TextComponentString(String.format("%1$s%2$s%3$s (%4$s)", color, MCA.getConfig().villagerChatPrefix, this.get(VILLAGER_NAME), professionName));
    }

    /**
     * @author KameiB
     * @reason Localize messages on client side, not server side.
     */
    @Overwrite(remap = false)
    public void say(Optional<EntityPlayer> player, String phraseId, @Nullable String... params) {
        ArrayList<String> paramsList = new ArrayList<>();
        if (params != null) Collections.addAll(paramsList, params);

        if (player.isPresent()) {
            EntityPlayer thePlayer = player.get();

            // Provide player as the first param, always
            paramsList.add(0, thePlayer.getName());

            // Infected villagers do not speak.
            if (get(IS_INFECTED)) {
                //thePlayer.sendMessage(new TextComponentString(getDisplayName().getFormattedText() + ": " + "???"));
                thePlayer.sendMessage(getDisplayName().appendText(": ???"));
                this.playSound(SoundEvents.ENTITY_ZOMBIE_AMBIENT, 0.5F, rand.nextFloat() + 0.5F);
            } else {
                String dialogueType = getPlayerHistoryFor(player.get().getUniqueID()).getDialogueType().getId();
                //String phrase = MCA.getLocalizer().localize(dialogueType + "." + phraseId, paramsList);
                String langKey = MCA.getLocalizer().localize(dialogueType + "." + phraseId, paramsList);
                
                // Dirty trick to get random names to work for some dialogs. Sorry.
                paramsList.add(API.getRandomName(EnumGender.getRandom()));
                paramsList.add(API.getRandomName(EnumGender.getRandom()));
                
                TextComponentTranslation phrase = new TextComponentTranslation(langKey, paramsList.toArray());
                //thePlayer.sendMessage(new TextComponentString(String.format("%1$s: %2$s", getDisplayName().getFormattedText(), phrase)));
                thePlayer.sendMessage(getDisplayName().appendText(": ").appendSibling(phrase));
                //thePlayer.sendMessage((new TextComponentString(phrase.toString())).setStyle(new Style().setColor(TextFormatting.GRAY)));
            }
        } else {
            MCA.getLog().warn(new Throwable("Say called on player that is not present!"));
        }
    }
    
    @ModifyArg(
            method = "handleButtonClick(Lnet/minecraft/entity/player/EntityPlayerMP;Ljava/lang/String;Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayerMP;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Localize the "info.trading.disabled" message on client side, not server side
    // Line 660: player.sendMessage(new TextComponentString(MCA.getLocalizer().localize("info.trading.disabled", new String[0])));
    private ITextComponent MCA_EntityVillagerMCA_handleButtonClick_sendMessage_localize(ITextComponent component) {
        return new TextComponentTranslation("info.trading.disabled");
    }
    
    @Unique
    private String localizator$myCauseName;

    @Shadow(remap = false)
    public <T> T get(DataParameter<T> key) {
        return this.dataManager.get(key);
    }
    
    @Shadow(remap = false)
    public VillagerRegistry.VillagerCareer getVanillaCareer() {
        return null;
    }
    
    @Shadow(remap = false)
    public PlayerHistory getPlayerHistoryFor(UUID uuid) {
        return null;
    }

    public EntityVillagerMCAMixin(World worldIn) {
        super(worldIn);
    }
}
