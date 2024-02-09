package kameib.localizator;

import fermiumbooter.FermiumRegistryAPI;
import kameib.localizator.data.ConfigToMixin;
import kameib.localizator.handlers.ForgeConfigHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;

import javax.annotation.Nullable;
import java.util.*;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(-5000)
public class LocalizatorPlugin implements IFMLLoadingPlugin {
    public static final Map<String, List<ConfigToMixin>> earlyMap = initEarlyMap();
    private static Map<String, List<ConfigToMixin>> initEarlyMap() {
        Map<String, List<ConfigToMixin>> map = new HashMap<>();
        List<ConfigToMixin> list = new ArrayList<>();
        list.add(new ConfigToMixin("(Minecraft) Mob Custom Names Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Mob Custom Names Mixin"), "mixins.core.entityname.json"));
        list.add(new ConfigToMixin("(Minecraft) Boss Custom Names Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Boss Custom Names Mixin"), "mixins.core.bossoverlay.json"));
        list.add(new ConfigToMixin("(Minecraft) Localized Lore Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Localized Lore Mixin"), "mixins.core.loclore.json"));
        list.add(new ConfigToMixin("(Minecraft) Localized Written Book Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Localized Written Book Mixin"), "mixins.core.writtenbooks.json"));
        list.add(new ConfigToMixin("(Minecraft) Better Localized Name Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Better Localized Name Mixin"), "mixins.core.locname.json"));
        list.add(new ConfigToMixin("(Minecraft) Localized Biome Name Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Localized Biome Name Mixin"), "mixins.core.biomename.json"));
        list.add(new ConfigToMixin("(Minecraft) Localized Writable Book Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Localized Writable Book Mixin"), "mixins.core.writablebooks.json"));
        list.add(new ConfigToMixin("(Minecraft) Localized Container Name Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Localized Container Name Mixin"), "mixins.core.containername.json"));
        map.put("minecraft", list);

        return Collections.unmodifiableMap(map);
    }

    private static final Map<String, List<ConfigToMixin>> lateMap = initLateMap();
    private static Map<String, List<ConfigToMixin>> initLateMap() {
        Map<String, List<ConfigToMixin>> map = new HashMap<>();
        // Neat
        List<ConfigToMixin> neatList = new ArrayList<>();
        neatList.add(new ConfigToMixin("(Neat) Health Bar Mixin", ForgeConfigHandler.getBoolean("(Neat) Health Bar Mixin"), "mixins.neat.healthbar.json"));
        map.put("neat", neatList);
        // ArmorUnderwear
        List<ConfigToMixin> armorunderList = new ArrayList<>();
        armorunderList.add(new ConfigToMixin("(ArmorUnder) Ozzy Liner text Mixin", ForgeConfigHandler.getBoolean("(ArmorUnder) Ozzy Liner text Mixin"), "mixins.armorunder.ozzyliner.json"));
        map.put("armorunder", armorunderList);
        // SetBonus
        List<ConfigToMixin> setbonusList = new ArrayList<>();
        setbonusList.add(new ConfigToMixin("(SetBonus) Tooltip Mixin", ForgeConfigHandler.getBoolean("(SetBonus) Tooltip Mixin"), "mixins.setbonus.tooltip.json"));
        map.put("setbonus", setbonusList);
        // ForgottenItems
        List<ConfigToMixin> forgottenitemsList = new ArrayList<>();
        forgottenitemsList.add(new ConfigToMixin("(ForgottenItems) Tooltips Mixin", ForgeConfigHandler.getBoolean("(ForgottenItems) Tooltips Mixin"), "mixins.forgottenitems.descriptions.json"));
        map.put("forgottenitems", forgottenitemsList);
        // SRParasites
        List<ConfigToMixin> srparasitesList = new ArrayList<>();
        srparasitesList.add(new ConfigToMixin("(SRParasites) Armor Tooltips Mixin", ForgeConfigHandler.getBoolean("(SRParasites) Armor Tooltips Mixin"), "mixins.srparasites.armortooltip.json"));
        map.put("srparasites", srparasitesList);
        // Better Survival
        List<ConfigToMixin> bettersurvivalList = new ArrayList<>();
        bettersurvivalList.add(new ConfigToMixin("(BetterSurvival) Potion-Imbued Weapons Tooltip Mixin", ForgeConfigHandler.getBoolean("(BetterSurvival) Potion-Imbued Weapons Tooltip Mixin"), "mixins.bettersurvival.tooltip.json"));
        map.put("mujmajnkraftsbettersurvival", bettersurvivalList);
        // Bountiful Baubles
        List<ConfigToMixin> bountifulbaublesList = new ArrayList<>();
        bountifulbaublesList.add(new ConfigToMixin("(BountifulBaubles) Tooltip fixes Mixin", ForgeConfigHandler.getBoolean("(BountifulBaubles) Tooltip fixes Mixin"), "mixins.bountifulbaubles.tooltips.json"));
        bountifulbaublesList.add(new ConfigToMixin("(BountifulBaubles) Ankh Charm Web Immune Mixin", ForgeConfigHandler.getBoolean("(BountifulBaubles) Ankh Charm Web Immune Mixin"), "mixins.bountifulbaubles.ankhcharmcobweb.json"));
        bountifulbaublesList.add(new ConfigToMixin("(BountifulBaubles) Ankh Shield Web Immune Mixin", ForgeConfigHandler.getBoolean("(BountifulBaubles) Ankh Shield Web Immune Mixin"), "mixins.bountifulbaubles.ankhshieldcobweb.json"));
        map.put("bountifulbaubles", bountifulbaublesList);
        // Rough Tweaks
        List<ConfigToMixin> roughtweaksList = new ArrayList<>();
        roughtweaksList.add(new ConfigToMixin("(RoughTweaks) Localized Names and Better Tooltip Mixin", ForgeConfigHandler.getBoolean("(RoughTweaks) Localized Names and Better Tooltip Mixin"), "mixins.roughtweaks.langkeys.json"));
        map.put("roughtweaks", roughtweaksList);
        // Serene Seasons
        List<ConfigToMixin> sereneseasonsList = new ArrayList<>();
        sereneseasonsList.add(new ConfigToMixin("(SereneSeasons) Localized Crop Fertility Tooltip Mixin", ForgeConfigHandler.getBoolean("(SereneSeasons) Localized Crop Fertility Tooltip Mixin"), "mixins.sereneseasons.tooltip.json"));
        map.put("sereneseasons", sereneseasonsList);
        // Charm
        List<ConfigToMixin> charmList = new ArrayList<>();
        charmList.add(new ConfigToMixin("(Charm) Localized Cake Name Mixin", ForgeConfigHandler.getBoolean("(Charm) Localized Cake Name Mixin"), "mixins.charm.cakename.json"));
        charmList.add(new ConfigToMixin("(Charm) Localized Composter JEI Mixin", ForgeConfigHandler.getBoolean("(Charm) Localized Composter JEI Mixin"), "mixins.charm.compostergui.json"));
        charmList.add(new ConfigToMixin("(Charm) Localized Container Name Mixin", ForgeConfigHandler.getBoolean("(Charm) Localized Container Name Mixin"), "mixins.charm.containername.json"));
        map.put("charm", charmList);
        // Dynamic Trees
        List<ConfigToMixin> dynamictreesList = new ArrayList<>();
        dynamictreesList.add(new ConfigToMixin("(DyamicTrees) Staff Mixin", ForgeConfigHandler.getBoolean("(DyamicTrees) Staff Mixin"), "mixins.dynamictrees.staff.json"));
        map.put("dynamictrees", dynamictreesList);
        // WAILA
        List<ConfigToMixin> wailaList = new ArrayList<>();
        wailaList.add(new ConfigToMixin("(WAILA) Entity Name Mixin", ForgeConfigHandler.getBoolean("(WAILA) Entity Name Mixin"), "mixins.waila.entityname.json"));
        map.put("waila", wailaList);
        // Corpse Complex
        List<ConfigToMixin> corpsecomplexList = new ArrayList<>();
        corpsecomplexList.add(new ConfigToMixin("(CorpseComplex) Grave Scroll Description Mixin", ForgeConfigHandler.getBoolean("(CorpseComplex) Grave Scroll Description Mixin"), "mixins.corpsecomplex.scroll.json"));
        map.put("corpsecomplex", corpsecomplexList);
        // Minecraft Forge FML
        List<ConfigToMixin> forgeList = new ArrayList<>();
        forgeList.add(new ConfigToMixin("(FML) Localized Mod List GUI Mixin", ForgeConfigHandler.getBoolean("(FML) Localized Mod List GUI Mixin"), "mixins.forge.modlist.json"));
        map.put("FML", forgeList);
        // Lycanites
        List<ConfigToMixin> lycanitesList = new ArrayList<>();
        lycanitesList.add(new ConfigToMixin("(Lycanites) Client-side Translated Messages Mixin", ForgeConfigHandler.getBoolean("(Lycanites) Client-side Translated Messages Mixin"), "mixins.lycanites.messages.json"));
        map.put("lycanitesmobs", lycanitesList);
        // Battle Towers
        List<ConfigToMixin> battletowersList = new ArrayList<>();
        battletowersList.add(new ConfigToMixin("(BattleTowers) Golem Defeated Message Mixin", ForgeConfigHandler.getBoolean("(BattleTowers) Golem Defeated Message Mixin"), "mixins.battletowers.messages.json"));
        map.put("battletowers", battletowersList);
        // Callable Horses
        List<ConfigToMixin> callablehorsesList = new ArrayList<>();
        callablehorsesList.add(new ConfigToMixin("(Callable Horses) Horse Messages Mixin", ForgeConfigHandler.getBoolean("(Callable Horses) Horse Messages Mixin"), "mixins.callablehorses.messages.json"));
        map.put("callablehorses", callablehorsesList);
        // Item Physic
        List<ConfigToMixin> itemphysicList = new ArrayList<>();
        itemphysicList.add(new ConfigToMixin("(ItemPhysic) Power: Status Message Mixin", ForgeConfigHandler.getBoolean("(ItemPhysic) Power: Status Message Mixin"), "mixins.itemphysic.message.json"));
        itemphysicList.add(new ConfigToMixin("(ItemPhysic) ERRORED patch Mixin", ForgeConfigHandler.getBoolean("(ItemPhysic) ERRORED patch Mixin"), "mixins.itemphysic.erroredpatch.json"));
        itemphysicList.add(new ConfigToMixin("(ItemPhysic) Reverse Description Mixin", ForgeConfigHandler.getBoolean("(ItemPhysic) Reverse Description Mixin"), "mixins.itemphysic.reversedescription.json"));
        map.put("itemphysic", itemphysicList);
        // Recurrent Complex
        List<ConfigToMixin> reccomplexList = new ArrayList<>();
        reccomplexList.add(new ConfigToMixin("(RecComplex) Artifact Names Mixin", ForgeConfigHandler.getBoolean("(RecComplex) Artifact Names Mixin"), "mixins.reccomplex.artifacts.json"));
        map.put("reccomplex", reccomplexList);
        // iChunUtil
        List<ConfigToMixin> ichunutilList = new ArrayList<>();
        ichunutilList.add(new ConfigToMixin("(iChunUtil) Fix Patrons and Version URL Mixin", ForgeConfigHandler.getBoolean("(iChunUtil) Fix Patrons and Version URL Mixin"), "mixins.ichunutil.urls.json"));
        map.put("ichunutil", ichunutilList);
        // Varied Commodities
        List<ConfigToMixin> variedCommoditiesList = new ArrayList<>();
        variedCommoditiesList.add(new ConfigToMixin("(VariedCommodities) Localized Book Mixin", ForgeConfigHandler.getBoolean("(VariedCommodities) Localized Book Mixin"), "mixins.variedcommodities.book.json"));
        map.put("variedcommodities", variedCommoditiesList);
        // Fishing Made Better
        List<ConfigToMixin> FMBList = new ArrayList<>();
        FMBList.add(new ConfigToMixin("(FishingMadeBetter) Localized Fishes Mixin", ForgeConfigHandler.getBoolean("(FishingMadeBetter) Localized Fishes Mixin"), "mixins.fmb.fishes.etc.json"));
        FMBList.add(new ConfigToMixin("(FishingMadeBetter) Instructions on Minigame Mixin", ForgeConfigHandler.getBoolean("(FishingMadeBetter) Instructions on Minigame Mixin"), "mixins.fmb.minigame.json"));
        map.put("fishingmadebetter", FMBList);
        // Fancy Block Particles
        List<ConfigToMixin> FBPList = new ArrayList<>();
        FBPList.add(new ConfigToMixin("(FBP) Localized Keybinds Mixin", ForgeConfigHandler.getBoolean("(FBP) Localized Keybinds Mixin"), "mixins.fbp.keybinds.json"));
        map.put("fbp", FBPList);
        // LevelUp2
        List<ConfigToMixin> levelUp2List = new ArrayList<>();
        levelUp2List.add(new ConfigToMixin("(LevelUp2) Localized Keybinds Mixin", ForgeConfigHandler.getBoolean("(LevelUp2) Localized Keybinds Mixin"), "mixins.levelup2.keybinds.json"));
        map.put("levelup2", levelUp2List);
        // Mo'Bends
        List<ConfigToMixin> mobendsList = new ArrayList<>();
        mobendsList.add(new ConfigToMixin("(Mo'Bends) Localized Keybinds Mixin", ForgeConfigHandler.getBoolean("(Mo'Bends) Localized Keybinds Mixin"), "mixins.mobends.keybinds.json"));
        map.put("mobends", mobendsList);
        // Scaling Health
        List<ConfigToMixin> scalingHealthList = new ArrayList<>();
        scalingHealthList.add(new ConfigToMixin("(ScalingHealth) Localized Keybinds Mixin", ForgeConfigHandler.getBoolean("(ScalingHealth) Localized Keybinds Mixin"), "mixins.scalinghealth.keybinds.json"));
        map.put("scalinghealth", scalingHealthList);

        return Collections.unmodifiableMap(map);
    }

    public LocalizatorPlugin(){
        MixinBootstrap.init();
        Localizator.LOGGER.info("[Localizator] Early Enqueue Start!");        
        for (Map.Entry<String, List<ConfigToMixin>> entry : earlyMap.entrySet()) {
            for (ConfigToMixin config : entry.getValue()) {
                if (config.isEnabled()) {
                    Localizator.LOGGER.info("[Localizator] Early Enqueue: " + config.getName());
                    FermiumRegistryAPI.enqueueMixin(false, config.getJson(), config.isEnabled());
                }
            }
        }
        if (ForgeConfigHandler.getBoolean("(BountifulBaubles) Ankh Charm Web Immune Mixin") 
                || ForgeConfigHandler.getBoolean("(BountifulBaubles) Ankh Shield Web Immune Mixin")) {
            Localizator.LOGGER.info("[Localizator] Early Enqueue: " + "(Minecraft) Entity.isInWeb Accessor for BountifulBaubles Mixins");
            FermiumRegistryAPI.enqueueMixin(false, "mixins.core.entityaccessor.json", true);
        }

        Localizator.LOGGER.info("[Localizator] Late Enqueue Start!");
        //List<String> activeModList = ModListGetter.getModList();
        //activeModList.add("FML");
        for (Map.Entry<String, List<ConfigToMixin>> entry : lateMap.entrySet()) {
            for (ConfigToMixin config : entry.getValue()) {
                if (config.isEnabled()) {
                    Localizator.LOGGER.info("[Localizator] Late Enqueue: " + config.getName());
                    FermiumRegistryAPI.enqueueMixin(true, config.getJson(), config.isEnabled());
                }
            }
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
