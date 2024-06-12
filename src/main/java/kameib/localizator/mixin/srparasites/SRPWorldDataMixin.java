package kameib.localizator.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.world.SRPWorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SRPWorldData.class)
public abstract class SRPWorldDataMixin {
    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 0,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Phase 0 Warning Message" with a lang key
    // Line 497: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningZero, 0);
    private String SRParasites_SRPWorldData_checkKills_message0(String message) {
        return "message.srparasites.phase_0_warning";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 1,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Phase 1 Warning Message" with a lang key
    // Line 502: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningOne, 1);
    private String SRParasites_SRPWorldData_checkKills_message1(String message) {
        return "message.srparasites.phase_1_warning";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 2,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Phase 2 Warning Message" with a lang key
    // Line 507: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningTwo, 2);
    private String SRParasites_SRPWorldData_checkKills_message2(String message) {
        return "message.srparasites.phase_2_warning";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 3,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded "Phase decreased" with a lang key
    // Line 511: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPWorldData_checkKills_messagePhaseDecreased1(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 4,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Phase 3 Warning Message" with a lang key
    // Line 517: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningThree, 3);
    private String SRParasites_SRPWorldData_checkKills_message3(String message) {
        return "message.srparasites.phase_3_warning";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 5,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded "Phase decreased" with a lang key
    // Line 521: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPWorldData_checkKills_messagePhaseDecreased2(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 6,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Phase 4 Warning Message" with a lang key
    // Line 528: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningFour, 4);
    private String SRParasites_SRPWorldData_checkKills_message4(String message) {
        return "message.srparasites.phase_4_warning";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 7,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded "Phase decreased" with a lang key
    // Line 531: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPWorldData_checkKills_messagePhaseDecreased3(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 8,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Phase 5 Warning Message" with a lang key
    // Line 537: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningFive, 5);
    private String SRParasites_SRPWorldData_checkKills_message5(String message) {
        return "message.srparasites.phase_5_warning";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 9,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded "Phase decreased" with a lang key
    // Line 541: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPWorldData_checkKills_messagePhaseDecreased4(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 10,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Phase 6 Warning Message" with a lang key
    // Line 547: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningSix, 6);
    private String SRParasites_SRPWorldData_checkKills_message6(String message) {
        return "message.srparasites.phase_6_warning";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 11,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded "Phase decreased" with a lang key
    // Line 551: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPWorldData_checkKills_messagePhaseDecreased5(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 12,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Phase 7 Warning Message" with a lang key
    // Line 557: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningSeven, 7);
    private String SRParasites_SRPWorldData_checkKills_message7(String message) {
        return "message.srparasites.phase_7_warning";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 13,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded "Phase decreased" with a lang key
    // Line 561: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPWorldData_checkKills_messagePhaseDecreased6(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 14,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Phase 8 Warning Message" with a lang key
    // Line 567: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningEight, 8);
    private String SRParasites_SRPWorldData_checkKills_message8(String message) {
        return "message.srparasites.phase_8_warning";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 15,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded "Phase decreased" with a lang key
    // Line 571: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPWorldData_checkKills_messagePhaseDecreased7(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(ILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 16,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded "Phase decreased" with a lang key
    // Line 577: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPWorldData_checkKills_messagePhaseDecreased8(String message) {
        return "message.srparasites.phasedecreased";
    }
}
