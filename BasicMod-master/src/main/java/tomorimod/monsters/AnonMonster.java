package tomorimod.monsters;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;


import static tomorimod.TomoriMod.makeID;
import static tomorimod.TomoriMod.imagePath;


public class AnonMonster extends CustomMonster {
    public static final String ID = makeID(AnonMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    // 怪物血量
    private static final int HP_MIN = 200;
    private static final int HP_MAX = 200;

    // 怪物的碰撞箱坐标和大小
    private static final float HB_X = -8.0F;
    private static final float HB_Y = 10.0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    // 定义动作 ID（byte 类型即可）
    private static final byte RITUAL_MOVE = 1;       // 第一回合上仪式
    private static final byte STRENGTH_ATTACK = 2;   // 之后的回合，加力量并攻击
    private static final String img=imagePath("monsters/"+AnonMonster.class.getSimpleName()+".png");


    // 给自己做个标记，是否已经使用过 Ritual
    private boolean hasUsedRitual = false;

    public AnonMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, img, x, y);

        // setHp(HP_MAX, HP_MIN); // 你原本写的，建议改为：
        setHp(HP_MIN, HP_MAX);

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        // 定义一个伤害动作，这里假设伤害是 6
        this.damage.add(new DamageInfo(this, 6, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case RITUAL_MOVE:
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(this, this, new RitualPower(this, 1, false), 1)
                );
                // 标记已经使用过 Ritual
                this.hasUsedRitual = true;
                break;

            case STRENGTH_ATTACK:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,
                        this, new StrengthPower(this, 2), 2));

                // 再对玩家进行一次攻击
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                                this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                break;
        }

        // 回合结束后，准备下一次动作
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        if (!this.hasUsedRitual) {
            setMove(MOVES[0], RITUAL_MOVE, Intent.BUFF);
        } else {
            setMove(MOVES[1], STRENGTH_ATTACK, Intent.ATTACK_BUFF,
                    this.damage.get(0).base, 1, false);
        }
    }

    @Override
    public void die() {
        super.die();

    }
}


