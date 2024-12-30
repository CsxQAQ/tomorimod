//package tomorimod.vfx;
//
//import basemod.animations.AbstractAnimation;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.core.Settings;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.rooms.AbstractRoom;
//import com.megacrit.cardcrawl.rooms.MonsterRoom;
//import com.megacrit.cardcrawl.rooms.RestRoom;
//
//import static com.badlogic.gdx.Gdx.graphics;
//
//public class FreeCatEffect {
//    private float timeCounter = 0f;
//    private float alphaValue = 1.0f;
//    private final float period = 4.0f;
//    private AbstractMonster monster;
//
//    public FreeCatEffect(AbstractMonster monster) {
//        this.monster = monster;
//    }
//
//    /**
//     * 更新透明度的变化
//     */
//    public void update() {
//        timeCounter += graphics.getDeltaTime();
//        alphaValue = 0.1f + 0.75f * (float) Math.abs(Math.sin(timeCounter * Math.PI / period));
//        monster.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, alphaValue));
//
//    }
//
//    /**
//     * 渲染的逻辑
//     */
//    public void render(SpriteBatch sb) {
//        if (!monster.isDead && !monster.escaped) {
//            sb.setColor(monster.tint.color);
//            sb.draw(monster.img, monster.drawX - (float)monster.img.getWidth() * Settings.scale / 2.0F + monster.animX, monster.drawY + monster.animY + AbstractDungeon.sceneOffsetY, (float)monster.img.getWidth() * Settings.scale, (float)monster.img.getHeight() * Settings.scale, 0, 0, monster.img.getWidth(), monster.img.getHeight(), monster.flipHorizontal, monster.flipVertical);
//
//
//            if (monster == AbstractDungeon.getCurrRoom().monsters.hoveredMonster && monster.atlas == null && monster.animation == null) {
//                sb.setBlendFunction(770, 1);
//                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));
//                sb.draw(monster.img, monster.drawX - (float)monster.img.getWidth() * Settings.scale / 2.0F + monster.animX, monster.drawY + monster.animY + AbstractDungeon.sceneOffsetY, (float)monster.img.getWidth() * Settings.scale, (float)monster.img.getHeight() * Settings.scale, 0, 0, monster.img.getWidth(), monster.img.getHeight(), monster.flipHorizontal, monster.flipVertical);
//                sb.setBlendFunction(770, 771);
//            }
//
//            if (!monster.isDying && !monster.isEscaping && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.isDead && !AbstractDungeon.player.hasRelic("Runic Dome") && monster.intent != AbstractMonster.Intent.NONE && !Settings.hideCombatElements) {
//                monster.renderIntentVfxBehind(sb);
//                monster.renderIntent(sb);
//                monster.renderIntentVfxAfter(sb);
//                monster.renderDamageRange(sb);
//            }
//
//            monster.hb.render(sb);
//            monster.intentHb.render(sb);
//            monster.healthHb.render(sb);
//        }
//
//        if (!AbstractDungeon.player.isDead) {
//            monster.renderHealth(sb);
//            monster.renderName(sb);
//        }
//    }
//}