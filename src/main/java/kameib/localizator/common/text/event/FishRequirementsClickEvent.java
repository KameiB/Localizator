package kameib.localizator.common.text.event;

import com.google.common.collect.Maps;
import net.minecraft.util.text.event.ClickEvent;

import javax.annotation.Nonnull;
import java.util.Map;

public class FishRequirementsClickEvent extends ClickEvent {
    private final Action action;
    private final String value;

    public FishRequirementsClickEvent(Action theAction, String theValue) {
        super(ClickEvent.Action.SUGGEST_COMMAND, "");// An empty command, as the action is custom
        this.action = theAction;
        this.value = theValue;
    }
    
    public Action getFishRequirementsAction() {
        return this.action;
    }

    @Override
    @Nonnull
    public String getValue() {
        return action.canonicalName + " " + value;
    }
    
    public String getfishId() {
        return value;
    }

    @Override
    @Nonnull
    public String toString() {
        return "FishRequirementsClickEvent{action=" + action + ", value='" + this.value + "'" + '}';
    }
   
    public enum Action {
        FISH_REQUIREMENTS("fish_requirements", true);

        private static final Map<String, Action> NAME_MAPPING = Maps.newHashMap();
        private final boolean allowedInChat;
        private final String canonicalName;
        
        Action(String canonicalNameIn, boolean allowedInChatIn) {
            this.canonicalName = canonicalNameIn;
            this.allowedInChat = allowedInChatIn;
        }

        public boolean shouldAllowInChat()
        {
            return this.allowedInChat;
        }

        public String getCanonicalName()
        {
            return this.canonicalName;
        }

        public static Action getValueByCanonicalName(String canonicalNameIn)
        {
            return NAME_MAPPING.get(canonicalNameIn);
        }

        static
        {
            for (Action clickevent$action : values())
            {
                NAME_MAPPING.put(clickevent$action.getCanonicalName(), clickevent$action);
            }
        }
    }  
}
