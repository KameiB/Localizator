package kameib.localizator.util.text.event;

import net.minecraft.util.text.event.ClickEvent;

import javax.annotation.Nonnull;

public class FishRequirementsClickEvent extends ClickEvent {
    private final FishRequirementsAction fishRequirementsAction;
    private final String fishId;
    
    

    public FishRequirementsClickEvent(FishRequirementsAction theAction, String theValue) {
        super(Action.RUN_COMMAND, "");// An empty command, as the action is custom
        this.fishRequirementsAction = theAction;
        this.fishId = theValue;
    }

    public FishRequirementsAction getFishRequirementsAction() {
        return fishRequirementsAction;
    }
    
    public String getFishId() {
        return fishId;
    }

    @Override
    @Nonnull
    public String toString() {
        return "FishRequirementsClickEvent{action=" + fishRequirementsAction + ", fishId='" + this.fishId + "'" + '}';
    }
   
    public enum FishRequirementsAction {
        FISH_REQUIREMENTS("fish_requirements", true);
        
        private final boolean allowedInChat;
        private final String canonicalName;
        
        FishRequirementsAction(String canonicalNameIn, boolean allowedInChatIn) {
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
    }  
}
