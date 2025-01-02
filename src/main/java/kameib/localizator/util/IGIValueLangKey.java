package kameib.localizator.util;

import com.github.lunatrius.ingameinfo.value.ValueSimple;
import net.minecraft.client.resources.I18n;

public class IGIValueLangKey extends ValueSimple {
    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public String getValue() {
        return I18n.format(this.value);
    }
}
