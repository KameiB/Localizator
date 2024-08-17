package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.item.RldItemStack;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import kameib.localizator.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemNovelty.class)
public abstract class ItemNoveltyMixin {
    @Redirect(
            method = "greymerksHatchet()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_greymerksHatchet_Unbreakable(RldItemStack itemStack, String[] lore) {
           return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
    }
    
    @Redirect(
            method = "nebrisCrown()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_nebrisCrown_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalNebrisCrown) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "nullPointer()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_nullPointer_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalNullPointer) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "manPants()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_manPants_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalManPants) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "farlandTravellers()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_farlandTravellers_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalFarlandTravelers) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "lascerator()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_lascerator_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalLascerator) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "bDoubleOspinkSweater()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_bDoubleOspinkSweater_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalPinkSweater) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "bDoubleOsDigJob()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_bDoubleOsDigJob_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalDigJob) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "ethosYourMomJoke()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_ethosYourMomJoke_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalYourMum) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "enikosStringTheory()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_enikosStringTheory_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalEnikoStringTheory) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "enikosEarring()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_enikosEarring_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalEnikoEarring) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "bajsLastResort()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_bajsLastResort_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalBajLastResort) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "docmRodOfCommand()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_docmRodOfCommand_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalRodOfCommand) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "valandrahsKiss()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_valandrahsKiss_Unbreakable(RldItemStack itemStack, String[] lore) {
        if (ForgeConfigHandler.serverConfig.rldEternalValandrahKiss) {
            return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
        }
        else {
            return itemStack.withDisplayLocLore(lore);
        }
    }

    @Redirect(
            method = "kameibShell()Lcom/github/fnar/minecraft/item/RldItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/fnar/minecraft/item/RldItemStack;withDisplayLocLore([Ljava/lang/String;)Lcom/github/fnar/minecraft/item/RldItemStack;",
                    remap = false
            ),
            remap = false
    )
    // Add Unbreakable tag
    private static RldItemStack RLD_ItemNovelty_kameibShell_Unbreakable(RldItemStack itemStack, String[] lore) {
        return itemStack.withDisplayLocLore(lore).withTag("Unbreakable", 1);
    }
}
