package tomorimod.monsters.mutsumi;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class SoyoMonster extends SpecialMonster {
    public static final String ID = makeID(SoyoMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;



    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;
    private static final String imgPath = imagePath("monsters/" + SoyoMonster.class.getSimpleName() + ".png");
    public static final float DRAW_X = 1600.0F;
    public static final float DRAW_Y = 450.0F;

    private boolean isMutumiGet;
    private MutsumiMonster mutsumiMonster;
    private int point = 0;

    public static final int HP_MIN = 200;
    public static final int HP_MAX = 200;
    public static final int DAMAGE_0 = 20;
    public static final int DAMAGE_1 = 5;
    public static final int DAMAGE_2 = 200;
    public static final int DAMAGE_3 = 4;
    public static final int DAMAGETIME_0 = 1;
    public static final int DAMAGETIME_1 = 5;
    public static final int DAMAGETIME_2 = 1;
    public static final int DAMAGETIME_3 = 15;
    public static final int MULTIBLOCK=10;
    public static final int INCREASE_HEALTH=40;

    public static final int HP_MIN_WEAK = 200;
    public static final int HP_MAX_WEAK = 200;
    public static final int DAMAGE_0_WEAK = 20;
    public static final int DAMAGE_1_WEAK = 5;
    public static final int DAMAGE_2_WEAK = 200;
    public static final int DAMAGE_3_WEAK = 4;
    public static final int DAMAGETIME_0_WEAK = 1;
    public static final int DAMAGETIME_1_WEAK = 5;
    public static final int DAMAGETIME_2_WEAK = 1;
    public static final int DAMAGETIME_3_WEAK = 15;
    public static final int MULTIBLOCK_WEAK=10;
    public static final int INCREASE_HEALTH_WEAK=40;

    private int hpMinVal, hpMaxVal;
    private int damageVal0, damageVal1, damageVal2, damageVal3;
    private int damageTimeVal0, damageTimeVal1, damageTimeVal2, damageTimeVal3;
    private int multiBlockVal,increaseHealthVal;

    public SoyoMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        if (isTomori) {
            hpMinVal = HP_MIN;
            hpMaxVal = HP_MAX;
            damageVal0 = DAMAGE_0;
            damageVal1 = DAMAGE_1;
            damageVal2 = DAMAGE_2;
            damageVal3 = DAMAGE_3;
            damageTimeVal0 = DAMAGETIME_0;
            damageTimeVal1 = DAMAGETIME_1;
            damageTimeVal2 = DAMAGETIME_2;
            damageTimeVal3 = DAMAGETIME_3;
            multiBlockVal=MULTIBLOCK;
            increaseHealthVal=INCREASE_HEALTH;
        } else {
            hpMinVal = HP_MIN_WEAK;
            hpMaxVal = HP_MAX_WEAK;
            damageVal0 = DAMAGE_0_WEAK;
            damageVal1 = DAMAGE_1_WEAK;
            damageVal2 = DAMAGE_2_WEAK;
            damageVal3 = DAMAGE_3_WEAK;
            damageTimeVal0 = DAMAGETIME_0_WEAK;
            damageTimeVal1 = DAMAGETIME_1_WEAK;
            damageTimeVal2 = DAMAGETIME_2_WEAK;
            damageTimeVal3 = DAMAGETIME_3_WEAK;
            multiBlockVal=MULTIBLOCK_WEAK;
            increaseHealthVal=INCREASE_HEALTH_WEAK;
        }

        setHp(hpMinVal, hpMaxVal);
        this.type = EnemyType.BOSS;
        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;
        this.drawX = DRAW_X * Settings.scale;
        this.drawY = DRAW_Y * Settings.scale;

        this.damage.add(new MutsumiDamageInfo(this, damageVal0, DamageInfo.DamageType.NORMAL));
        this.damage.add(new MutsumiDamageInfo(this, damageVal1, DamageInfo.DamageType.NORMAL));
        this.damage.add(new MutsumiDamageInfo(this, damageVal2, DamageInfo.DamageType.NORMAL));
        this.damage.add(new MutsumiDamageInfo(this, damageVal3, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public void update() {
        super.update();
        if (!isMutumiGet) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m instanceof MutsumiMonster && !m.isDeadOrEscaped()) {
                    mutsumiMonster = (MutsumiMonster) m;
                    isMutumiGet = true;
                    target = mutsumiMonster;
                }
            }
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                soyoAttack(0, damageTimeVal0);
                break;
            case 1:
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this,
                        new PlatedArmorPower(AbstractDungeon.player, multiBlockVal), multiBlockVal));
                break;
            case 2:
                soyoAttack(1, damageTimeVal1);
                break;
            case 3:
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        increaseMaxHp(increaseHealthVal, true);
                        isDone = true;
                    }
                });
                break;
            case 4:
                soyoAttack(2, damageTimeVal2);
                break;
            case 5:
                addToBot(new VFXAction(new BloodShotEffect
                        (this.hb.cX, this.hb.cY, mutsumiMonster.hb.cX, mutsumiMonster.hb.cY, damageTimeVal3), 0.25F));
                soyoAttack(3,damageTimeVal3,AbstractGameAction.AttackEffect.BLUNT_HEAVY);
                break;
            case 99:
                break;
        }
        addToBot(new RollMoveAction(this));
    }

    private void soyoAttack(int index, int times) {
        for (int i = 0; i < times; i++) {
            if (target == AbstractDungeon.player) {
                addToBot(new DamageAction(target, this.damage.get(index), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            } else {
                MutsumiDamageInfo newInfo = new MutsumiDamageInfo(this, this.damage.get(index).base);
                newInfo.applyPowers(this, mutsumiMonster);
                addToBot(new DamageAction(target, new MutsumiDamageInfo(this, newInfo.output)));
            }
        }
    }

    private void soyoAttack(int index, int times,AbstractGameAction.AttackEffect effect) {
        for (int i = 0; i < times; i++) {
            if (target == AbstractDungeon.player) {
                addToBot(new DamageAction(target, this.damage.get(index), effect));
            } else {
                MutsumiDamageInfo newInfo = new MutsumiDamageInfo(this, this.damage.get(index).base);
                newInfo.applyPowers(this, mutsumiMonster);
                addToBot(new DamageAction(target, new MutsumiDamageInfo(this, newInfo.output)));
            }
        }
    }

    @Override
    protected Texture getAttackIntent() {
        if (this.nextMove == 4 || this.nextMove == 5) {
            return new Texture(imagePath("monsters/intents/attack_bass_heavy.png"));
        }
        return new Texture(imagePath("monsters/intents/attack_bass_normal.png"));
    }

    @Override
    protected void getMove(int num) {
        int rand = AbstractDungeon.miscRng.random(point);
        if (rand > 3) rand = 3;
        int tmp = AbstractDungeon.miscRng.random(1);
        switch (rand) {
            case 0:
                if (tmp == 0) {
                    setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base, damageTimeVal0, false);
                } else {
                    setMove((byte) 1, Intent.DEFEND);
                }
                point += 2;
                break;
            case 1:
            case 2:
                if (tmp == 0) {
                    setMove((byte) 2, Intent.ATTACK, this.damage.get(1).base, damageTimeVal1, true);
                } else {
                    setMove((byte) 3, Intent.BUFF);
                }
                point++;
                break;
            case 3:
                if (tmp == 0) {
                    setMove((byte) 4, Intent.ATTACK, this.damage.get(2).base, damageTimeVal2, false);
                } else {
                    setMove((byte) 5, Intent.ATTACK, this.damage.get(3).base, damageTimeVal3, true);
                }
                point -= 2;
                break;
        }
    }

    @Override
    public void die() {
        super.die();
    }
}
