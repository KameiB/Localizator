package kameib.localizator.handlers;

import kameib.localizator.util.ItemStackUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class NoveltyPlacementHandler {
    @SubscribeEvent
    public static void onPlaceBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getEntityPlayer().getHeldItem(event.getHand());
        if (stack.isEmpty()) return;
        Item item = stack.getItem();

        boolean isSkull = item instanceof ItemSkull;
        boolean isSign = item instanceof ItemSign;
        boolean isBlock = item instanceof ItemBlock;
        boolean isPainting = false;
        if (!isSkull && !isSign && !isBlock) {
            //slightly slower so do it later
            isPainting = item.getRegistryName() != null && item.getRegistryName().toString().equals("minecraft:painting"); // use registry name due to Json Paintings override
            if(!isPainting) return;
        }

        if (!ItemStackUtil.isNovelty(stack)) return;
        EntityPlayer player = event.getEntityPlayer();

        if (isBlock) {
            if (!ForgeConfigHandler.serverConfig.minecraftPreventNoveltyBlocksPlacement) return;

            Block block = ForgeRegistries.BLOCKS.getValue(stack.getItem().getRegistryName());
            if (ForgeConfigHandler.getNoveltyBlocksWhitelist().contains(block)) return;

            player.sendStatusMessage(new TextComponentTranslation("message.localizator.dont_place_novelty.block"), true);
        } else if (isSign) {
            if (!ForgeConfigHandler.serverConfig.minecraftPreventNoveltySignsPlacement) return;
            player.sendStatusMessage(new TextComponentTranslation("message.localizator.dont_place_novelty.sign"), true);
        } else if (isSkull) {
            if (!ForgeConfigHandler.serverConfig.minecraftPreventNoveltySkullsPlacement) return;
            player.sendStatusMessage(new TextComponentTranslation("message.localizator.dont_place_novelty.skull"), true);
        } else if (isPainting) {
            if (!ForgeConfigHandler.serverConfig.minecraftPreventNoveltyPaintingsPlacement) return;
            player.sendStatusMessage(new TextComponentTranslation("message.localizator.dont_place_novelty.painting"), true);
        }

        event.setUseItem(Event.Result.DENY); //dont use the held item, but allow interacting with targeted block
    }
}
