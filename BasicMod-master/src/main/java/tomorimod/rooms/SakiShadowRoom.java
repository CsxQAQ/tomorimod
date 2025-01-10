package tomorimod.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.events.SakiShadowEvent;

public class SakiShadowRoom extends AbstractRoom {
    public EventType eType;

    public enum EventType {
        HEART, NONE;
    }

    public SakiShadowRoom(EventType type) {
        this.phase = AbstractRoom.RoomPhase.EVENT;
        this.eType = type;
    }

    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();

        switch (this.eType) {
            case HEART:
                this.event = new SakiShadowEvent();
                this.event.onEnterRoom();
                break;
        }
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp) {
            this.event.update();
        }
    }

    public void render(SpriteBatch sb) {
        if (this.event != null) {
            this.event.renderRoomEventPanel(sb);
            this.event.render(sb);
        }
        super.render(sb);
    }

    public void renderAboveTopPanel(SpriteBatch sb) {
        super.renderAboveTopPanel(sb);
        if (this.event != null) {
            this.event.renderAboveTopPanel(sb);
        }
    }
}
