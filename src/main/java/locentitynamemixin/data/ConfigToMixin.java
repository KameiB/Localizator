package locentitynamemixin.data;


public class ConfigToMixin {
    // "Localized Custom Names Mixin (Neat)", neatLocCustomNamesMixin, "mixins.neat.healthbar.json"
    private final String name;
    private final boolean enabled;
    private final String json;

    public ConfigToMixin(String mixinName, boolean configKey, String associatedJson) {
        name = mixinName;
        enabled = configKey;
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
