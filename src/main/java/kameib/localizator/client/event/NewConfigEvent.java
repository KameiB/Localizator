package kameib.localizator.client.event;

import kameib.localizator.Localizator;
import kameib.localizator.data.Production;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

@Mod.EventBusSubscriber
public class NewConfigEvent {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPlayerJoin (PlayerEvent.PlayerLoggedInEvent event) {
        if (Production.migratedCfg) {
            EntityPlayer player = event.player;
            String oldCfgName = Localizator.MODID + "_old.cfg";
            File oldCfgFile = new File("config", oldCfgName);
            
            Style style = new Style();
            ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_FILE, oldCfgFile.getAbsolutePath());
            style.setClickEvent(click);

            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                    new TextComponentTranslation("message.localizator.newconfig.hovertext", "§r[§b" + oldCfgFile.getAbsolutePath() + "§r]"));
            style.setHoverEvent(hoverEvent);

            player.sendMessage(new TextComponentTranslation("message.localizator.newconfig.0", "§aKameiB§r"));
            player.sendMessage(new TextComponentTranslation("message.localizator.newconfig.1"));
            player.sendMessage(new TextComponentTranslation("message.localizator.newconfig.2", new TextComponentString("§b§n" + oldCfgName + "§r").setStyle(style)));
            player.sendMessage(new TextComponentTranslation("message.localizator.newconfig.3"));
            player.sendMessage(new TextComponentTranslation("message.localizator.newconfig.4"));
            
            player.sendMessage(new TextComponentTranslation("message.localizator.newconfig.5").setStyle(style));
            
            Production.migratedCfg = false;
        }
    }
}
