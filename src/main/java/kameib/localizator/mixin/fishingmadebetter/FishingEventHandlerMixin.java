package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.common.text.event.FishRequirementsClickEvent;
import kameib.localizator.data.Production;
import kameib.localizator.util.FMB_BetterFishUtil;
import kameib.localizator.util.LocLoreUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.theawesomegem.fishingmadebetter.BetterFishUtil;
import net.theawesomegem.fishingmadebetter.common.capability.world.PopulationData;
import net.theawesomegem.fishingmadebetter.common.configuration.CustomConfigurationHandler;
import net.theawesomegem.fishingmadebetter.common.data.FishCaughtData;
import net.theawesomegem.fishingmadebetter.common.data.FishData;
import net.theawesomegem.fishingmadebetter.common.event.FishingEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(FishingEventHandler.class)
public abstract class FishingEventHandlerMixin {
    // *********** checkForFishInventory ***********
    @ModifyArg(
            method = "checkForFishInventory(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/theawesomegem/fishingmadebetter/util/ItemStackUtil;appendToolTip(Lnet/minecraft/item/ItemStack;Ljava/lang/Iterable;)Lnet/minecraft/item/ItemStack;",
                    remap = false
            ),
            index = 0,
            remap = false
    )
    // Add LocLore and LocLoreArg
    // Line 422: itemStack = ItemStackUtil.appendToolTip(itemStack, tooltipList);
    private ItemStack FMB_FishingEventHandler_checkForFishInventory_appendLocLore(ItemStack itemStack) {
        List<String> locLoreList = new ArrayList<>();
        List<String> argList = new ArrayList<>();
        
        locLoreList.add("tooltip.fishingmadebetter.fish.weight");
        argList.add(String.valueOf(BetterFishUtil.getFishWeight(itemStack)));
        
        if (CustomConfigurationHandler.fishDataMap.get(BetterFishUtil.getFishId(itemStack)).allowScaling) {
            if (BetterFishUtil.doesFishHasScale(itemStack)) {
                locLoreList.add("tooltip.fishingmadebetter.fish.scale_attached");                
            }
            else {
                locLoreList.add("tooltip.fishingmadebetter.fish.scale_detached");
            }
            argList.add("");
        }
        locLoreList.add(BetterFishUtil.isDead(itemStack, localizator$myCurrentTime) ? 
                "tooltip.fishingmadebetter.fish.dead" : 
                "tooltip.fishingmadebetter.fish.alive");
        argList.add("");
        
        return LocLoreUtil.appendLocLore(itemStack, locLoreList, argList);
    }
    
    @Redirect(
            method = "checkForFishInventory(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getTotalWorldTime()J",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Capture "currentTime"
    // Line 410: long currentTime = player.world.getTotalWorldTime();
    private long FMB_FishingEventHandler_checkForFishInventory_getCurrentTime(World world) {
        localizator$myCurrentTime = world.getTotalWorldTime();
        return world.getTotalWorldTime();
    }
    
    // *********** getTrackingFish ***********
    @Redirect(
            method = "getTrackingFish(Lnet/minecraft/entity/player/EntityPlayer;Lnet/theawesomegem/fishingmadebetter/common/item/tracker/ItemFishTracker;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/theawesomegem/fishingmadebetter/common/capability/world/PopulationData;getFishType()Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    // Capture populationData
    // Line 501: fishData = (FishData)CustomConfigurationHandler.fishDataMap.get(populationData.getFishType());
    private String FMB_FishingMadeBetter_getTrackingFish_getPopulationData(PopulationData populationData) {
        localizator$myPopulationData = populationData;
        return populationData.getFishType();
    }
    
    @Redirect(
            method = "getTrackingFish(Lnet/minecraft/entity/player/EntityPlayer;Lnet/theawesomegem/fishingmadebetter/common/item/tracker/ItemFishTracker;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;",
                    remap = false
            ),
            remap = false
    )
    // Capture FishData
    // Line 501: fishData = (FishData)CustomConfigurationHandler.fishDataMap.get(populationData.getFishType());
    private Object FMB_FishingEventHandler_getTrackingFish_getFishData(Map<String, net.theawesomegem.fishingmadebetter.common.data.FishData> fishDataMap, Object fishType) {
        localizator$myFishData = fishDataMap.get((String)fishType);
        return fishDataMap.get((String)fishType);
    }
    
    @ModifyArg(
            method = "getTrackingFish(Lnet/minecraft/entity/player/EntityPlayer;Lnet/theawesomegem/fishingmadebetter/common/item/tracker/ItemFishTracker;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Send a localized message (when in Creative)
    // Line 505: player.sendMessage(new TextComponentString(String.format("%s %s in %s at Y%s-%s", populationData.getQuantity(), fishData.fishId, fishData.liquid.toString(), fishData.minYLevel, fishData.maxYLevel)));
    private ITextComponent FMB_FishingEventHandler_getTrackingFish_sendCreativeMessage1(ITextComponent par1) {
        return new TextComponentTranslation("notif.fishingmadebetter.fish_tracker.creative.fishQty_Y_levels", // Message
            localizator$myPopulationData.getQuantity(), // Quantity
                new TextComponentTranslation(FMB_BetterFishUtil.getFishDataUnlocalizedName(localizator$myFishData)) // Fish Name
                        .setStyle(new Style().setColor(TextFormatting.YELLOW).setBold(true)
                                .setClickEvent(new FishRequirementsClickEvent(FishRequirementsClickEvent.Action.FISH_REQUIREMENTS, localizator$myFishData.fishId))
                                .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("jei.fishingmadebetter.category.fish_requirements")))),
                new TextComponentTranslation("notif.fishingmadebetter.fish_tracker.creative.liquid." + localizator$myFishData.liquid.toString()), // Liquid
                localizator$myFishData.minYLevel, // Min Y Level
                localizator$myFishData.maxYLevel); // Max Y Level
    }

    @ModifyArg(
            method = "getTrackingFish(Lnet/minecraft/entity/player/EntityPlayer;Lnet/theawesomegem/fishingmadebetter/common/item/tracker/ItemFishTracker;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Send a localized message (when in Creative)
    // Line 506: player.sendMessage(new TextComponentString(String.format("MinLine %sm, Time %s, MaxLight %s, Rain %s, Thunder %s", fishData.minDeepLevel, fishData.time.toString(), fishData.maxLightLevel, fishData.rainRequired, fishData.thunderRequired)));
    private ITextComponent FMB_FishingEventHandler_getTrackingFish_sendCreativeMessage2(ITextComponent par1) {
        return new TextComponentTranslation("notif.fishingmadebetter.fish_tracker.creative.fishEnvironmentConditions", // Message
                    localizator$myFishData.minDeepLevel, // MinLine
                        new TextComponentTranslation("notif.fishingmadebetter.fish_tracker.creative.time." + localizator$myFishData.time.toString()), // Time
                        localizator$myFishData.maxLightLevel, // MaxLight
                        new TextComponentTranslation("notif.fishingmadebetter.fish_tracker.creative.rain." + localizator$myFishData.rainRequired), // Rain
                        new TextComponentTranslation("notif.fishingmadebetter.fish_tracker.creative.thunder." + localizator$myFishData.thunderRequired)); // Thunder
    }

    @ModifyArg(
            method = "getTrackingFish(Lnet/minecraft/entity/player/EntityPlayer;Lnet/theawesomegem/fishingmadebetter/common/item/tracker/ItemFishTracker;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 3,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Send a localized message (when in Survival, limited)
    // Line 541: player.sendMessage((new TextComponentTranslation("notif.fishingmadebetter.fish_tracker.detected", new Object[0])).appendText(" " + fishData.fishId + "."));
    private ITextComponent FMB_FishingEventHandler_getTrackingFish_sendSurvivalMessageLimited(ITextComponent par1) {
        return new TextComponentTranslation("notif.fishingmadebetter.fish_tracker.survival.limited", // Message
                    new TextComponentTranslation(FMB_BetterFishUtil.getFishDataUnlocalizedName(localizator$myFishData))
                            .setStyle(new Style().setColor(TextFormatting.YELLOW).setBold(true)
                                    .setClickEvent(new FishRequirementsClickEvent(FishRequirementsClickEvent.Action.FISH_REQUIREMENTS, localizator$myFishData.fishId))
                                    .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("jei.fishingmadebetter.category.fish_requirements")))));
    }

    @ModifyArg(
            method = "getTrackingFish(Lnet/minecraft/entity/player/EntityPlayer;Lnet/theawesomegem/fishingmadebetter/common/item/tracker/ItemFishTracker;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 4,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Send a localized message (when in Survival, detailed)
    // Line 543: player.sendMessage((new TextComponentTranslation("notif.fishingmadebetter.fish_tracker.detected", new Object[0])).appendText(" " + fishData.fishId + ", " + fishData.description + " in ").appendSibling(new TextComponentTranslation(quantity, new Object[0])));
    private ITextComponent FMB_FishingEventHandler_getTrackingFish_sendSurvivalMessageDetailed(ITextComponent par1) {
        int pop = localizator$myPopulationData.getQuantity();
        String quantity;
        if (pop > 50) {
            quantity = "notif.fishingmadebetter.fish_tracker.quantity_immense_";
        } else if (pop > 40) {
            quantity = "notif.fishingmadebetter.fish_tracker.quantity_abundant_";
        } else if (pop > 30) {
            quantity = "notif.fishingmadebetter.fish_tracker.quantity_ample_";
        } else if (pop > 20) {
            quantity = "notif.fishingmadebetter.fish_tracker.quantity_substantial_";
        } else if (pop > 10) {
            quantity = "notif.fishingmadebetter.fish_tracker.quantity_numerous_";
        } else if (pop > 3) {
            quantity = "notif.fishingmadebetter.fish_tracker.quantity_light_";
        } else if (pop > 1) {
            quantity = "notif.fishingmadebetter.fish_tracker.quantity_sparse_";
        } else {
            quantity = "notif.fishingmadebetter.fish_tracker.quantity_meager_";
        }
        
        return new TextComponentTranslation("notif.fishingmadebetter.fish_tracker.survival.detailed", // Message
                new TextComponentTranslation(FMB_BetterFishUtil.getFishDataUnlocalizedName(localizator$myFishData))// Fish Name
                        .setStyle(new Style().setColor(TextFormatting.YELLOW).setBold(true)
                                .setClickEvent(new FishRequirementsClickEvent(FishRequirementsClickEvent.Action.FISH_REQUIREMENTS, localizator$myFishData.fishId))
                                .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("jei.fishingmadebetter.category.fish_requirements")))),
                    new TextComponentTranslation(FMB_BetterFishUtil.getFishDataUnlocalizedDesc(localizator$myFishData)), // Fish Description
                    new TextComponentTranslation(quantity)); // Detailed quantity
    }

    // *********** getFishItemStack ***********
    @Inject(
            method = "getFishItemStack(Lnet/theawesomegem/fishingmadebetter/common/data/FishCaughtData;JI)Lnet/minecraft/item/ItemStack;",
            at = @At(
                    value = "RETURN",
                    ordinal = 1
            ),
            cancellable = true,
            remap = false
    )
    // Add LocName, LocLore & LocLoreArg to the freshly caught fish on return
    // Line 558: private ItemStack getFishItemStack(FishCaughtData fishCaughtData, long currentTime, int weightModifier) {
    private void FMB_FishingEventHandler_getFishItemStack_addLocalizedNBT(FishCaughtData fishCaughtData, long currentTime, int weightModifier, CallbackInfoReturnable<ItemStack> cir) {
        if (ItemStack.EMPTY.equals(cir.getReturnValue())) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
        else {
            ItemStack newItem = cir.getReturnValue();
            newItem.clearCustomName(); // Remove Name, to add LocName
            newItem.setTranslatableName(FMB_BetterFishUtil.getFishCaughtDataUnlocalizedName(fishCaughtData));
            
            List<String> locLoreList = new ArrayList<>();
            List<String> argList = new ArrayList<>();
            locLoreList.add("tooltip.fishingmadebetter.fish.weight");
            argList.add(String.valueOf(fishCaughtData.weight));
            if (newItem.getTagCompound().hasKey("FishScale")) {
                locLoreList.add("tooltip.fishingmadebetter.fish.scale_attached");
                argList.add("");
            }
            locLoreList.add("tooltip.fishingmadebetter.fish.alive");
            argList.add("");
            
            cir.setReturnValue(LocLoreUtil.appendLocLore(newItem, locLoreList, argList));
        }        
    }

    @Unique
    long localizator$myCurrentTime;
    @Unique
    FishData localizator$myFishData;
    @Unique
    PopulationData localizator$myPopulationData;
}
