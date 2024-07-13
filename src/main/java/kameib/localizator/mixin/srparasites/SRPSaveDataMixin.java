package kameib.localizator.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.world.SRPSaveData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SRPSaveData.class)
public abstract class SRPSaveDataMixin {
    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 449: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningZero, 0);
    private String SRParasites_SRPSaveData_checkKills_phase0(String message) {
        return "message.srparasites.phase_0_warning";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 454: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningOne, 1);
    private String SRParasites_SRPSaveData_checkKills_phase1(String message) {
        return "message.srparasites.phase_1_warning";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 459: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningTwo, 2);
    private String SRParasites_SRPSaveData_checkKills_phase2(String message) {
        return "message.srparasites.phase_2_warning";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 463: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPSaveData_checkKills_messagePhase2Decreased(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 469: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningThree, 3);
    private String SRParasites_SRPSaveData_checkKills_phase3(String message) {
        return "message.srparasites.phase_3_warning";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 473: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPSaveData_checkKills_messagePhase3Decreased(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 480: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningFour, 4);
    private String SRParasites_SRPSaveData_checkKills_phase4(String message) {
        return "message.srparasites.phase_4_warning";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 483: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPSaveData_checkKills_messagePhase4Decreased(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 489: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningFive, 5);
    private String SRParasites_SRPSaveData_checkKills_phase5(String message) {
        return "message.srparasites.phase_5_warning";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 493: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPSaveData_checkKills_messagePhase5Decreased(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 499: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningSix, 6);
    private String SRParasites_SRPSaveData_checkKills_phase6(String message) {
        return "message.srparasites.phase_6_warning";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 503: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPSaveData_checkKills_messagePhase6Decreased(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 509: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningSeven, 7);
    private String SRParasites_SRPSaveData_checkKills_phase7(String message) {
        return "message.srparasites.phase_7_warning";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 513: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPSaveData_checkKills_messagePhase7Decreased(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 519: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningEight, 8);
    private String SRParasites_SRPSaveData_checkKills_phase8(String message) {
        return "message.srparasites.phase_8_warning";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
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
    // Line 523: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPSaveData_checkKills_messagePhase8Decreased(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 16,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Phase 9 Warning Message" with a lang key
    // Line 529: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningNine, 9);
    private String SRParasites_SRPSaveData_checkKills_phase9(String message) {
        return "message.srparasites.phase_9_warning";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 17,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded "Phase decreased" with a lang key
    // Line 533: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPSaveData_checkKills_messagePhase9Decreased(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 18,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Phase 10 Warning Message" with a lang key
    // Line 539: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigSystems.phaseWarningTen, 10);
    private String SRParasites_SRPSaveData_checkKills_phase10(String message) {
        return "message.srparasites.phase_10_warning";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 19,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded "Phase decreased" with a lang key
    // Line 543: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPSaveData_checkKills_messagePhase10Decreased(String message) {
        return "message.srparasites.phasedecreased";
    }

    @ModifyArg(
            method = "checkKills(IILnet/minecraft/world/World;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 20,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded "Phase decreased" with a lang key
    // Line 549: ParasiteEventEntity.alertAllPlayerDim(worldIn, "Phase decreased", -7);
    private String SRParasites_SRPSaveData_checkKills_messageLastPhaseDecreased(String message) {
        return "message.srparasites.phasedecreased";
    }
}
