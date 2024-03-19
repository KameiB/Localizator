package kameib.localizator.data;

import kameib.localizator.handlers.ForgeConfigHandler;

public class ConfigToMixin {
    private final String name;
    private final boolean enabled;
    private final String json;

    public ConfigToMixin(String mixinName, String associatedJson) {
        name = mixinName;
        enabled = ForgeConfigHandler.getBoolean(mixinName);
        json = associatedJson;
    }

    public ConfigToMixin(String mixinName, String associatedJson, boolean enableMixin) {
        name = mixinName;
        enabled = enableMixin;
        json = associatedJson;
    }

    public String getName() {
        return name;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public String getJson() {
        return json;
    }
}
