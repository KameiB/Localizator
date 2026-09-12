package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemEnderTalisman;

@Mixin(ItemEnderTalisman.class)
public abstract class ItemEnderTalismanMixin {
    @ModifyConstant(
            method = "<init>",
            constant = @Constant(stringValue = "Teleports you where you're looking"),
            remap = false
    )
    // Replace the hardcoded "Teleports you where you're looking" with a lang key, but don't translate it here yet.
    // Line 28: super("ender_talisman", "Teleports you where you're looking", 18, ItemList.enderGem);
    private static String localizator_ForgottenItems_ItemEnderTalisman_constructor(String lore) {
        return "item.ender_talisman.lore";
    }
    
    @ModifyArg(
            method = "onItemRightClick(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/EnumHand;)Lnet/minecraft/util/ActionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the hardcoded "Out of Range" with a TextComponentTranslation that uses a lang key.
    // 49: player.sendMessage(new TextComponentString("Out of Range"));
    private ITextComponent localizator_ForgottenItems_onItemRightClick_sendMessage(ITextComponent par1) {
        return new TextComponentTranslation("message.forgottenitems.ender_talisman.out_of_range");
    }
}
