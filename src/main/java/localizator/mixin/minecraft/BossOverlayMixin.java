package localizator.mixin.minecraft;

import localizator.handlers.ForgeConfigHandler;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiBossOverlay.class)
public abstract class BossOverlayMixin {
    /**
     * @author KameiB
     * @reason If Boss's "CustomName" tag contains a lang key, translate it at rendering.
     * Should support lang keys surrounded by TextComponent formatting.
     */
    @Redirect(
            method = "renderBossHealth()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/text/ITextComponent;getFormattedText()Ljava/lang/String;")
    )
    // Line 43: String s = bossinfoclient.getName().getFormattedText();
    public String localizatorRenderBossHealth_getFormattedTextName(ITextComponent bossName) {
        StringBuilder stringbuilder = new StringBuilder();

        for (ITextComponent itextcomponent : bossName)
        {
            String s = itextcomponent.getUnformattedComponentText();
            if (!s.isEmpty())
            {
                stringbuilder.append(itextcomponent.getStyle().getFormattingCode());
                if (ForgeConfigHandler.clientConfig.minecraftBossLocCustomNames) {
                    stringbuilder.append(I18n.hasKey(s) ? I18n.format(s) : s);
                }
                else { // getFormattedText default behaviour
                    stringbuilder.append(s);
                }
                stringbuilder.append((Object) TextFormatting.RESET);
            }
        }
        return stringbuilder.toString();
    }
    
            
//    @Final
//    @Shadow
//    private final Map<UUID, BossInfoClient> mapBossInfos = Maps.<UUID, BossInfoClient>newLinkedHashMap();
//    @Final
//    @Shadow
//    private final Minecraft client = Minecraft.getMinecraft();
//    @Final
//    @Shadow
//    private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");
//    @Shadow
//    private void render(int x, int y, BossInfo info) {}
//
//    @Unique private final Map<UUID, BossEntityInfo> localizator$mapBossDisplayNames = Maps.<UUID, BossEntityInfo>newLinkedHashMap();
//
//    /**
//     * @author KameiB
//     * @reason I just wanted boss names from servers to be localized on client side x'c
//     * Tried to map server-side entity UUID and client-side entity UUID, get the LivingEntity from the LoadedEntityList and call its getDisplayName
//     * But the problem is: which name goes with which boss bar? I don't have access to the boss HP or anything from here or BossInfo Client...
//     * Also, when dimension unloads, boss name gets from a full ITextComponent to a flat String,
//     * Therefore when joining again, its name can't be translated anymore.
//     * I thought of changing the way minecraft serializes boss names but I'm afraid of messing that up, so I finally gave up.
//     * - Fonny suggested manually translating the boss name, but idk how that would work for, let's say, Lycanite's bosses
//     *
//     * The only way I can succeed is by translating the "CustomName" NBT tag, but that would mean manually mixin as much bosses as I encounter.
//     * F.
//     */    
//    @Overwrite(remap = Production.inProduction)
//    public void renderBossHealth()
//    {
//        if (!this.mapBossInfos.isEmpty())
//        {
//            ScaledResolution scaledresolution = new ScaledResolution(this.client);
//            int i = scaledresolution.getScaledWidth();
//            int j = 12;
//
//            for (BossInfoClient bossinfoclient : this.mapBossInfos.values())
//            {
//                int k = i / 2 - 91;
//                net.minecraftforge.client.event.RenderGameOverlayEvent.BossInfo event =
//                        net.minecraftforge.client.ForgeHooksClient.bossBarRenderPre(scaledresolution, bossinfoclient, k, j, 10 + this.client.fontRenderer.FONT_HEIGHT);
//                if (!event.isCanceled()) {
//                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//                    this.client.getTextureManager().bindTexture(GUI_BARS_TEXTURES);
//                    this.render(k, j, bossinfoclient);
//
//
//                    String s;
//                    UUID uuid = bossinfoclient.getUniqueId();
//                    if (localizator$mapBossDisplayNames.containsKey(uuid)) {
//                        if (localizator$mapBossDisplayNames.get(uuid) == null) {
//                            localizator$getBossEntities(uuid, localizator$mapBossDisplayNames);
//                        }
//                        if (localizator$mapBossDisplayNames.get(uuid) == null) {
//                            s = bossinfoclient.getName().getFormattedText();
//                        }
//                        else {
//                            if (localizator$mapBossDisplayNames.get(uuid).hasCustomName()) {
//                                s = bossinfoclient.getName().getFormattedText();
//                            }
//                            else {
//                                s = I18n.format(localizator$mapBossDisplayNames.get(uuid).getDisplayName().getFormattedText());
//                            }
//                        }
//                    }
//                    else {
//                        s = bossinfoclient.getName().getFormattedText();
//                    }
//
//                    /*if (!client.isSingleplayer() || !Production.inProduction) {
//                        if (tempBoss == null) {
//                            for (Entity entity : client.world.getLoadedEntityList()) { // Debe ser EntityLivingBase
//                                if (!entity.isNonBoss()) {
//                                    client.world.getEntityByID()
//                                    if (entity.getUniqueID().equals(bossinfoclient.getUniqueId())) {
//                                        tempBoss = entity;
//                                        tempBossID = EntityList.getEntityString(tempBoss);
//                                        //bossinfoclient.get
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }*/
//
//                    /*EntityLiving entity = mapBossDisplayNames.get(bossinfoclient.getUniqueId());
//                    if (entity.hasCustomName()) {
//                        s = bossinfoclient.getName().getFormattedText();
//                    }
//                    else {
//                        s = I18n.format(entity.getDisplayName().getFormattedText());
//                    }*/
//
//
//
//                    this.client.fontRenderer.drawStringWithShadow(s, (float)(i / 2 - this.client.fontRenderer.getStringWidth(s) / 2), (float)(j - 9), 16777215);
//                }
//                j += event.getIncrement();
//
//                net.minecraftforge.client.ForgeHooksClient.bossBarRenderPost(scaledresolution);
//                if (j >= scaledresolution.getScaledHeight() / 3)
//                {
//                    break;
//                }
//            }
//        }
//    }
//
//    @Unique
//    private void localizator$getBossEntities(UUID uuidServer, Map<UUID, BossEntityInfo> map) {
//        for (Entity entity : client.world.getLoadedEntityList()) {
//            if (entity instanceof EntityLiving) {
//                if (!(((EntityLiving) entity).isNonBoss())) {
//                    for (UUID uuid : map.keySet()) {
//                        if (map.get(uuid) == null) {
//                            map.replace(uuidServer, new BossEntityInfo(entity.getUniqueID(), (EntityLiving)entity));                            
//                        }
//                    }                    
//                    /*if (map.isEmpty()) {
//                        map.put(this.mapBossInfos.get(packetIn.getUniqueId()).getUniqueId(), (EntityLiving) entity);
//                    }
//                    else {
//                        for (EntityLiving savedBoss : mapBossDisplayNames.values()) {
//                            if (!savedBoss.equals((EntityLiving) entity))
//                                this.mapBossDisplayNames.put(this.mapBossInfos.get(packetIn.getUniqueId()).getUniqueId(), (EntityLiving) entity);
//                        }
//                    }*/
//                }
//            }
//        }        
//    }
//
//
//    /**
//     * @author KameiB
//     * @reason Add support for my local Entity list
//     */
//    @Overwrite(remap = Production.inProduction)
//    public void read(SPacketUpdateBossInfo packetIn)
//    {
//        if (packetIn.getOperation() == SPacketUpdateBossInfo.Operation.ADD)
//        {
//            this.mapBossInfos.put(packetIn.getUniqueId(), new BossInfoClient(packetIn));
//            localizator$mapBossDisplayNames.put(this.mapBossInfos.get(packetIn.getUniqueId()).getUniqueId(), null);
//
//            //UUID uuid;
//            /*for (Entity entity : client.world.getLoadedEntityList()) {
//                if (entity instanceof EntityLiving) {
//                    //uuid = this.mapBossInfos.get(packetIn.getUniqueId()).getUniqueId();
//                    if (!entity.isNonBoss()) {
//                        if (mapBossDisplayNames.isEmpty()) {
//                            this.mapBossDisplayNames.put(this.mapBossInfos.get(packetIn.getUniqueId()).getUniqueId(), (EntityLiving) entity);
//                        }
//                        else {
//                            for (EntityLiving savedBoss : mapBossDisplayNames.values()) {
//                                if (!savedBoss.equals((EntityLiving) entity))
//                                    this.mapBossDisplayNames.put(this.mapBossInfos.get(packetIn.getUniqueId()).getUniqueId(), (EntityLiving) entity);
//                            }
//                        }
//                    }
//                }
//            }*/
//        }
//        else if (packetIn.getOperation() == SPacketUpdateBossInfo.Operation.REMOVE)
//        {
//            this.localizator$mapBossDisplayNames.remove(this.mapBossInfos.get(packetIn.getUniqueId()).getUniqueId());
//            this.mapBossInfos.remove(packetIn.getUniqueId());
//        }
//        else
//        {
//            ((BossInfoClient)this.mapBossInfos.get(packetIn.getUniqueId())).updateFromPacket(packetIn);
//        }
//    }
//
//    @Inject(
//            method = "clearBossInfos()V",
//            at = @At("TAIL"),
//            remap = Production.inProduction
//    )
//    private void Minecraft_BossOverlay_clearBossInfos(CallbackInfo ci) {
//        this.localizator$mapBossDisplayNames.clear();
//    }
}
