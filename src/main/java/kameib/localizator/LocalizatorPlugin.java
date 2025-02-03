package kameib.localizator;

import fermiumbooter.FermiumRegistryAPI;
import kameib.localizator.data.ConfigToMixin;
import kameib.localizator.handlers.ForgeConfigHandler;
import net.minecraftforge.fml.common.Loader;
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
        list.add(new ConfigToMixin("(Minecraft) Mob Custom Names Mixin", "mixins.localizator.core.entityname.json"));
        list.add(new ConfigToMixin("(Minecraft) Boss Custom Names Mixin", "mixins.localizator.core.bossoverlay.json"));
        list.add(new ConfigToMixin("(Minecraft) Localized Lore Mixin", "mixins.localizator.core.loclore.json"));
        list.add(new ConfigToMixin("(Minecraft) Localized Written Book Mixin", "mixins.localizator.core.writtenbooks.json"));
        list.add(new ConfigToMixin("(Minecraft) Better Localized Name Mixin", "mixins.localizator.core.locname.json"));
        list.add(new ConfigToMixin("(Minecraft) Localized Writable Book Mixin", "mixins.localizator.core.writablebooks.json"));
        list.add(new ConfigToMixin("(Minecraft) Localized Container Name Mixin", "mixins.localizator.core.containername.json"));
        list.add(new ConfigToMixin("(Minecraft) Item Names on Kill Command Mixin", "mixins.localizator.core.itementitykill.json"));
        list.add(new ConfigToMixin("(Minecraft) Prevent Novelties Placement Mixin", "mixins.localizator.core.noveltyplacement.json"));
        map.put("minecraft", list);

        return Collections.unmodifiableMap(map);
    }

    private static final Map<String, List<ConfigToMixin>> lateMap = initLateMap();
    private static Map<String, List<ConfigToMixin>> initLateMap() {
        Map<String, List<ConfigToMixin>> map = new HashMap<>();
        // Neat
        List<ConfigToMixin> neatList = new ArrayList<>();
        neatList.add(new ConfigToMixin("(Neat) Health Bar Mixin", "mixins.localizator.neat.healthbar.json"));
        map.put("neat", neatList);
        // ArmorUnderwear
        List<ConfigToMixin> armorunderList = new ArrayList<>();
        armorunderList.add(new ConfigToMixin("(ArmorUnder) Ozzy Liner text Mixin", "mixins.localizator.armorunder.ozzyliner.json"));
        map.put("armorunder", armorunderList);
        // SetBonus
        List<ConfigToMixin> setbonusList = new ArrayList<>();
        setbonusList.add(new ConfigToMixin("(SetBonus) Tooltip Mixin", "mixins.localizator.setbonus.tooltip.json"));
        map.put("setbonus", setbonusList);
        // ForgottenItems
        List<ConfigToMixin> forgottenitemsList = new ArrayList<>();
        forgottenitemsList.add(new ConfigToMixin("(ForgottenItems) Tooltips Mixin", "mixins.localizator.forgottenitems.descriptions.json"));
        map.put("forgottenitems", forgottenitemsList);
        // SRParasites
        List<ConfigToMixin> srparasitesList = new ArrayList<>();
        srparasitesList.add(new ConfigToMixin("(SRParasites) Armor Tooltips Mixin", "mixins.localizator.srparasites.armortooltip.json"));
        srparasitesList.add(new ConfigToMixin("(SRParasites) Localized Messages Mixin", "mixins.localizator.srparasites.messages.json"));
        srparasitesList.add(new ConfigToMixin("(SRParasites) Custom Adventurer Names Mixin", "mixins.localizator.srparasites.adventurernames.json"));
        map.put("srparasites", srparasitesList);
        // Better Survival
        List<ConfigToMixin> bettersurvivalList = new ArrayList<>();
        bettersurvivalList.add(new ConfigToMixin("(BetterSurvival) Potion-Imbued Weapons Tooltip Mixin", "mixins.localizator.bettersurvival.tooltip.json"));
        map.put("mujmajnkraftsbettersurvival", bettersurvivalList);
        // Bountiful Baubles
        List<ConfigToMixin> bountifulbaublesList = new ArrayList<>();
        bountifulbaublesList.add(new ConfigToMixin("(BountifulBaubles) Tooltip fixes Mixin", "mixins.localizator.bountifulbaubles.tooltips.json"));
        bountifulbaublesList.add(new ConfigToMixin("(BountifulBaubles) Ankh Charm Web Immune Mixin", "mixins.localizator.bountifulbaubles.ankhcharmcobweb.json"));
        bountifulbaublesList.add(new ConfigToMixin("(BountifulBaubles) Ankh Shield Web Immune Mixin", "mixins.localizator.bountifulbaubles.ankhshieldcobweb.json"));
        bountifulbaublesList.add(new ConfigToMixin("(BountifulBaubles) Localized Wormhole Messages Mixin", "mixins.localizator.bountifulbaubles.wormholemessages.json"));
        map.put("bountifulbaubles", bountifulbaublesList);
        // Rough Tweaks
        List<ConfigToMixin> roughtweaksList = new ArrayList<>();
        roughtweaksList.add(new ConfigToMixin("(RoughTweaks) Localized Names and Better Tooltip Mixin", "mixins.localizator.roughtweaks.langkeys.json"));
        map.put("roughtweaks", roughtweaksList);
        // Serene Seasons
        List<ConfigToMixin> sereneseasonsList = new ArrayList<>();
        sereneseasonsList.add(new ConfigToMixin("(SereneSeasons) Localized Crop Fertility Tooltip Mixin", "mixins.localizator.sereneseasons.tooltip.json"));
        map.put("sereneseasons", sereneseasonsList);
        // Charm
        List<ConfigToMixin> charmList = new ArrayList<>();
        charmList.add(new ConfigToMixin("(Charm) Localized Cake Name Mixin", "mixins.localizator.charm.cakename.json"));
        charmList.add(new ConfigToMixin("(Charm) Localized Composter JEI Mixin", "mixins.localizator.charm.compostergui.json"));
        charmList.add(new ConfigToMixin("(Charm) Localized Container Name Mixin", "mixins.localizator.charm.containername.json"));
        charmList.add(new ConfigToMixin("(Charm) Localized World Crates Name Mixin", "mixins.localizator.charm.worldcratesname.json"));
        map.put("charm", charmList);
        // Dynamic Trees
        List<ConfigToMixin> dynamictreesList = new ArrayList<>();
        dynamictreesList.add(new ConfigToMixin("(DyamicTrees) Staff Mixin", "mixins.localizator.dynamictrees.staff.json"));
        map.put("dynamictrees", dynamictreesList);
        // WAILA
        List<ConfigToMixin> wailaList = new ArrayList<>();
        wailaList.add(new ConfigToMixin("(WAILA) Entity Name Mixin", "mixins.localizator.waila.entityname.json"));
        map.put("waila", wailaList);
        // Corpse Complex
        List<ConfigToMixin> corpsecomplexList = new ArrayList<>();
        corpsecomplexList.add(new ConfigToMixin("(CorpseComplex) Grave Scroll Description Mixin", "mixins.localizator.corpsecomplex.scroll.json"));
        map.put("corpsecomplex", corpsecomplexList);
        // Minecraft Forge FML
        List<ConfigToMixin> forgeList = new ArrayList<>();
        forgeList.add(new ConfigToMixin("(FML) Localized Mod List GUI Mixin", "mixins.localizator.forge.modlist.json"));
        map.put("FML", forgeList);
        // Lycanites
        List<ConfigToMixin> lycanitesList = new ArrayList<>();
        lycanitesList.add(new ConfigToMixin("(Lycanites) Client-side Translated Messages Mixin", "mixins.localizator.lycanites.messages.json"));
        map.put("lycanitesmobs", lycanitesList);
        // Battle Towers
        List<ConfigToMixin> battletowersList = new ArrayList<>();
        battletowersList.add(new ConfigToMixin("(BattleTowers) Golem Defeated Message Mixin", "mixins.localizator.battletowers.messages.json"));
        map.put("battletowers", battletowersList);
        // Callable Horses
        List<ConfigToMixin> callablehorsesList = new ArrayList<>();
        callablehorsesList.add(new ConfigToMixin("(Callable Horses) Horse Messages Mixin", "mixins.localizator.callablehorses.messages.json"));
        map.put("callablehorses", callablehorsesList);
        // Item Physic
        List<ConfigToMixin> itemphysicList = new ArrayList<>();
        itemphysicList.add(new ConfigToMixin("(ItemPhysic) Power: Status Message Mixin", "mixins.localizator.itemphysic.message.json"));
        itemphysicList.add(new ConfigToMixin("(ItemPhysic) ERRORED patch Mixin", "mixins.localizator.itemphysic.erroredpatch.json"));
        itemphysicList.add(new ConfigToMixin("(ItemPhysic) Reverse Description Mixin", "mixins.localizator.itemphysic.reversedescription.json"));
        map.put("itemphysic", itemphysicList);
        // Recurrent Complex
        List<ConfigToMixin> reccomplexList = new ArrayList<>();
        reccomplexList.add(new ConfigToMixin("(RecComplex) Artifact Names Mixin", "mixins.localizator.reccomplex.artifacts.json"));
        map.put("reccomplex", reccomplexList);
        // iChunUtil
        List<ConfigToMixin> ichunutilList = new ArrayList<>();
        ichunutilList.add(new ConfigToMixin("(iChunUtil) Fix Patrons and Version URL Mixin", "mixins.localizator.ichunutil.urls.json"));
        map.put("ichunutil", ichunutilList);
        // Varied Commodities
        List<ConfigToMixin> variedCommoditiesList = new ArrayList<>();
        variedCommoditiesList.add(new ConfigToMixin("(VariedCommodities) Localized Book Mixin", "mixins.localizator.variedcommodities.book.json"));
        map.put("variedcommodities", variedCommoditiesList);
        // Fishing Made Better
        List<ConfigToMixin> FMBList = new ArrayList<>();
        FMBList.add(new ConfigToMixin("(FishingMadeBetter) Localized Fishes Mixin", "mixins.localizator.fmb.fishes.etc.json"));
        FMBList.add(new ConfigToMixin("(FishingMadeBetter) Instructions on Minigame Mixin", "mixins.localizator.fmb.minigame.json"));
        map.put("fishingmadebetter", FMBList);
        // Fancy Block Particles
        List<ConfigToMixin> FBPList = new ArrayList<>();
        FBPList.add(new ConfigToMixin("(FBP) Localized Keybinds Mixin", "mixins.localizator.fbp.keybinds.json"));
        map.put("fbp", FBPList);
        // LevelUp2
        List<ConfigToMixin> levelUp2List = new ArrayList<>();
        levelUp2List.add(new ConfigToMixin("(LevelUp2) Localized Keybinds Mixin", "mixins.localizator.levelup2.keybinds.json"));
        map.put("levelup2", levelUp2List);
        // Mo'Bends
        List<ConfigToMixin> mobendsList = new ArrayList<>();
        mobendsList.add(new ConfigToMixin("(Mo'Bends) Localized Keybinds Mixin", "mixins.localizator.mobends.keybinds.json"));
        map.put("mobends", mobendsList);
        // Scaling Health
        List<ConfigToMixin> scalingHealthList = new ArrayList<>();
        scalingHealthList.add(new ConfigToMixin("(ScalingHealth) Localized Keybinds Mixin", "mixins.localizator.scalinghealth.keybinds.json"));
        scalingHealthList.add(new ConfigToMixin("(ScalingHealth) Localized Messages Mixin", "mixins.localizator.scalinghealth.messages.json"));
        map.put("scalinghealth", scalingHealthList);
        // Spartan Weaponry
        List<ConfigToMixin> spartanWeaponryList = new ArrayList<>();
        spartanWeaponryList.add(new ConfigToMixin("(SpartanWeaponry) Localized Material Bonus Tooltip Mixin", "mixins.localizator.spartanweaponry.tooltip.json"));
        map.put("spartanweaponry", spartanWeaponryList);
        // Trinkes & Baubles
        List<ConfigToMixin> trinketsList = new ArrayList<>();
        trinketsList.add(new ConfigToMixin("(T&B) Localized Ender Queen messages Mixin", "mixins.localizator.xat.messages.json"));
        trinketsList.add(new ConfigToMixin("(T&B) Localized Armor Weight Tooltip Mixin", "mixins.localizator.xat.weighttooltip.json"));
        map.put("xat", trinketsList);
        // Dynamic Raw Ores
        List<ConfigToMixin> dynaoresList = new ArrayList<>();
        dynaoresList.add(new ConfigToMixin("(DynaOres) Correctly Localized Ore Names", "mixins.localizator.dynaores.orenames.json"));
        map.put("dynaores", dynaoresList);
        // Roguelike Dungeons
        List<ConfigToMixin> rldList = new ArrayList<>();
        rldList.add(new ConfigToMixin("(RLD) Unbreakable Novelties Mixin", "mixins.localizator.rld.eternalnovelties.json"));
        rldList.add(new ConfigToMixin("(RLD) Extended Novelty Pool Mixin", "mixins.localizator.rld.extendednovelties.json"));
        rldList.add(new ConfigToMixin("(RLD) Spawn Johnny Mixin", "mixins.localizator.rld.enablejohnny.json"));
        map.put("roguelike", rldList);
        // Morpheus
        List<ConfigToMixin> morpheusList = new ArrayList<>();
        morpheusList.add(new ConfigToMixin("(Morpheus) Localized Messages Mixin", "mixins.localizator.morpheus.messages.json"));
        map.put("morpheus", morpheusList);
        // InGame Info XML
        List<ConfigToMixin> igiList = new ArrayList<>();
        igiList.add(new ConfigToMixin("(IGI) Add lang Tag in XML Mixin", "mixins.localizator.igi.xml_langkey.json"));
        map.put("ingameinfoxml", igiList);
        // Minecraft Comes Alive
        List<ConfigToMixin> mcaList = new ArrayList<>();
        mcaList.add(new ConfigToMixin("(MCA) Localized Texts Mixin", "mixins.localizator.mca.localized.json"));
        map.put("mca", mcaList);
        // Dynamic Surroundings HUD
        List<ConfigToMixin> dshudsList = new ArrayList<>();
        dshudsList.add(new ConfigToMixin("(DSHUDs) Localized Biome Name Mixin", "mixins.localizator.dshuds.biomename.json"));
        map.put("dshuds", dshudsList);

        return Collections.unmodifiableMap(map);
    }

    public LocalizatorPlugin(){
        MixinBootstrap.init();
        // ========== Early enqueing ==========
        Localizator.LOGGER.info("[Localizator] Early Enqueue Start!");        
        for (Map.Entry<String, List<ConfigToMixin>> entry : earlyMap.entrySet()) {
            for (ConfigToMixin config : entry.getValue()) {
                if (config.isEnabled()) {
                    Localizator.LOGGER.info("[Localizator] Early Enqueue: {}", config.getName());
                    FermiumRegistryAPI.enqueueMixin(false, config.getJson());
                }
            }
        }
        if (ForgeConfigHandler.getBoolean("(BountifulBaubles) Ankh Charm Web Immune Mixin") 
                || ForgeConfigHandler.getBoolean("(BountifulBaubles) Ankh Shield Web Immune Mixin")) {
            Localizator.LOGGER.info("[Localizator] Early Enqueue: " + "(Minecraft) Entity.isInWeb Accessor for BountifulBaubles Mixins");
            FermiumRegistryAPI.enqueueMixin(false, "mixins.localizator.core.entityaccessor.json");
        }

        // ========== Late enqueing ==========
        Localizator.LOGGER.info("[Localizator] Late Enqueue Start!");
        for (Map.Entry<String, List<ConfigToMixin>> entry : lateMap.entrySet()) {
            for (ConfigToMixin config : entry.getValue()) {
                if (config.isEnabled()) {
                    Localizator.LOGGER.info("[Localizator] Late Enqueue: {}", config.getName());
                    FermiumRegistryAPI.enqueueMixin(true, config.getJson(), () -> Loader.isModLoaded(entry.getKey()));
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
