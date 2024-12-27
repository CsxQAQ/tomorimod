package tomorimod.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;

import static com.badlogic.gdx.Gdx.graphics;

public class IntangibleEffect {
    private float timeCounter = 0f;
    private float alphaValue = 1.0f;
    private final float period = 4.0f;
    private AbstractPlayer player;

    public IntangibleEffect(AbstractPlayer player) {
        this.player = player;
    }

    /**
     * 更新透明度的变化
     */
    public void update() {
        timeCounter += graphics.getDeltaTime();
        alphaValue = 0.1f + 0.75f * (float) Math.abs(Math.sin(timeCounter * Math.PI / period));
        player.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, alphaValue));

    }

    /**
     * 渲染的逻辑
     */
    public void render(SpriteBatch sb) {
        player.stance.render(sb);

        if ((AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT ||
                AbstractDungeon.getCurrRoom() instanceof MonsterRoom) &&
                !player.isDead) {

            player.renderHealth(sb);

            if (!player.orbs.isEmpty()) {
                for (int i = 0; i < player.orbs.size(); i++) {
                    player.orbs.get(i).render(sb);
                }
            }
        }

        if (!(AbstractDungeon.getCurrRoom() instanceof RestRoom)) {
            sb.setColor(player.tint.color);
            sb.draw(player.img,
                    player.drawX - player.img.getWidth() * com.megacrit.cardcrawl.core.Settings.scale / 2.0F + player.animX,
                    player.drawY,
                    player.img.getWidth() * com.megacrit.cardcrawl.core.Settings.scale,
                    player.img.getHeight() * com.megacrit.cardcrawl.core.Settings.scale,
                    0, 0,
                    player.img.getWidth(),
                    player.img.getHeight(),
                    player.flipHorizontal,
                    player.flipVertical
            );

            player.hb.render(sb);
            player.healthHb.render(sb);
        } else {
            sb.setColor(Color.WHITE);
            player.renderShoulderImg(sb);
        }
    }
}