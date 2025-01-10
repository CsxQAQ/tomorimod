package tomorimod.rooms;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import tomorimod.events.EndingEvent;

public class EndingEventRoom extends NeowRoom {
    public EndingEventRoom() {
        super(true);
    }

    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.event = new EndingEvent();
        this.event.onEnterRoom();
    }
}