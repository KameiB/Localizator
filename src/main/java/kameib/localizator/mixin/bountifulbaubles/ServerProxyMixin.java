package kameib.localizator.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.proxy.ISideProxy;
import cursedflames.bountifulbaubles.proxy.ServerProxy;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;

// ServerProxy inherits ISideProxy's defaults, which return the lang key untranslated,
// so shields and modifier books show up as e.g. "item.bountifulbaubles.shieldCobalt.name" on dedicated servers.
@Mixin(ServerProxy.class)
public abstract class ServerProxyMixin implements ISideProxy {
    @Override
    public String translate(String string) {
        return I18n.translateToLocal(string);
    }

    @Override
    public String translateWithArgs(String string, Object... args) {
        return I18n.translateToLocalFormatted(string, args);
    }

    @Override
    public boolean hasTranslationKey(String string) {
        return I18n.canTranslate(string);
    }
}