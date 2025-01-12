package tomorimod.monsters.taki;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import tomorimod.monsters.BaseMonster;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;

import static com.badlogic.gdx.Gdx.graphics;
import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

/**
 * RanaMonster 示例
 */
public class RanaMonster extends BaseMonster {
    public static final String ID = makeID(RanaMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public static final int HP_MIN         = 20;
    public static final int HP_MAX         = 20;
    public static final int DAMAGE_0       = 8;  // index=0
    public static final int DAMAGE_1       = 12; // index=1
    public static final int DAMAGETIME_0   = 2;  // Case 0 多段次数
    public static final int DAMAGETIME_1   = 3;  // Case 2 多段次数
    public static final int STRENGTH_INCREASE           = 2; // 加格挡基数
    public static final int DAMAGE_INCREASE           = 2; // 加格挡基数

    public static final int HP_MIN_WEAK         = 10;
    public static final int HP_MAX_WEAK         = 10;
    public static final int DAMAGE_0_WEAK       = 6;
    public static final int DAMAGE_1_WEAK       = 10;
    public static final int DAMAGETIME_0_WEAK   = 2; // 跟原版一样，也可以改1
    public static final int DAMAGETIME_1_WEAK   = 2; // 少打一段
    public static final int STRENGTH_INCREASE_WEAK           = 2; // 加格挡基数
    public static final int DAMAGE_INCREASE_WEAK           = 2; // 加格挡基数

    private int hpMinVal;
    private int hpMaxVal;
    private int damageVal0;
    private int damageVal1;
    private int damageTimeVal0;
    private int damageTimeVal1;
    private int strengthIncreaseVal;
    private int damageIncreaseVal;

    // 怪物碰撞箱
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    // 绘制坐标
    public static final float DRAW_X = 1600.0F;
    public static final float DRAW_Y = 400.0F;

    // 贴图
    private static final String imgPath = imagePath("monsters/" + RanaMonster.class.getSimpleName() + ".png");

    private TakiMonster takiMonster = null;
    private boolean isTakiGet = false;
    private int turnNum = 0;
    private boolean isFirstTurn = true;
    private boolean hasTalked = false;


    // 用于闪烁效果
    private float timeCounter = 0f;
    private float alphaValue = 1.0f;
    private final float period = 4.0f;

    public RanaMonster(float x, float y) {
        // 先用原版的 maxHP 初始化 parent
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        // 根据 isTomori 判断赋值原版还是弱化版
        if (isTomori) {
            this.hpMinVal = HP_MIN;
            this.hpMaxVal = HP_MAX;
            this.damageVal0 = DAMAGE_0;
            this.damageVal1 = DAMAGE_1;
            this.damageTimeVal0 = DAMAGETIME_0;
            this.damageTimeVal1 = DAMAGETIME_1;
            this.strengthIncreaseVal=STRENGTH_INCREASE;
            this.damageIncreaseVal=DAMAGE_INCREASE;
        } else {
            this.hpMinVal = HP_MIN_WEAK;
            this.hpMaxVal = HP_MAX_WEAK;
            this.damageVal0 = DAMAGE_0_WEAK;
            this.damageVal1 = DAMAGE_1_WEAK;
            this.damageTimeVal0 = DAMAGETIME_0_WEAK;
            this.damageTimeVal1 = DAMAGETIME_1_WEAK;
            this.strengthIncreaseVal=STRENGTH_INCREASE_WEAK;
            this.damageIncreaseVal=DAMAGE_INCREASE_WEAK;
        }

        // 设置血量
        setHp(this.hpMinVal, this.hpMaxVal);
        this.type = EnemyType.BOSS;

        // 对话框位置
        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        // 绘制坐标
        this.drawX = DRAW_X * Settings.scale;
        this.drawY = DRAW_Y * Settings.scale;

        // 将伤害Info放进 damage 列表 (index=0,1)
        this.damage.add(new DamageInfo(this, damageVal0, DamageInfo.DamageType.NORMAL)); // index 0
        this.damage.add(new DamageInfo(this, damageVal1, DamageInfo.DamageType.NORMAL)); // index 1

        // 这里是进场就加无敌(999)与 RanaFreeCatPower 的逻辑，可以保留
        addToBot(new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 999), 999));
        addToBot(new ApplyPowerAction(this, this, new RanaFreeCatPower(this)));
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                // 原本是 for (i < 2) 两段攻击 => 用 damagePlayer(0, damageTimeVal0)
                damagePlayer(0, damageTimeVal0);
                break;
            case 1:
                // 加力量(自己 & Taki)
                addToBot(new ApplyPowerAction(this, this,
                        new StrengthPower(this, strengthIncreaseVal), strengthIncreaseVal));
                if (takiMonster != null && !takiMonster.isDeadOrEscaped()) {
                    addToBot(new ApplyPowerAction(takiMonster, this,
                            new StrengthPower(this, strengthIncreaseVal), strengthIncreaseVal));
                }
                break;
            case 2:
                // 原本是 for(i<3) => damagePlayer(1, damageTimeVal1)
                damagePlayer(1, damageTimeVal1);

                // 打完后伤害+2
                this.damage.get(1).base += damageIncreaseVal;
                break;
        }
        addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        if (isFirstTurn) {
            // 第一次 => 加力量
            setMove((byte) 1, Intent.BUFF);
            isFirstTurn = false;
        } else {
            // 如果 Taki 存活 => 在 0(两段攻) 与 1(加力量) 间循环
            if (takiMonster != null && !takiMonster.isDeadOrEscaped()) {
                if (turnNum % 2 == 0) {
                    setMove((byte) 0, Intent.ATTACK,
                            this.damage.get(0).base, damageTimeVal0, true);
                } else {
                    setMove((byte) 1, Intent.BUFF);
                }
                turnNum++;
            } else {
                // Taki 死了 => 放 2 号大招 (三段攻)
                if (!hasTalked) {
                    // 说一次话
                    addToBot(new TalkAction(this, DIALOG[0], 1.0F, 2.0F));
                    hasTalked = true;
                }
                setMove((byte) 2, Intent.ATTACK,
                        this.damage.get(1).base, damageTimeVal1, true);

                // 播放特效
                playFlameVFX();
            }
        }
    }

    public void playFlameVFX(){
        addToBot(new SFXAction("MONSTER_CHAMP_CHARGE"));
        addToBot(new VFXAction(this, new InflameEffect(this), 0.25F));
        addToBot(new VFXAction(this, new InflameEffect(this), 0.25F));
        addToBot(new VFXAction(this, new InflameEffect(this), 0.25F));
    }

    @Override
    public void update() {
        super.update();
        if (!isTakiGet) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m instanceof TakiMonster && !m.isDeadOrEscaped()) {
                    this.takiMonster = (TakiMonster) m;
                    isTakiGet = true;
                }
            }
        }
        if (takiMonster != null) {
            if (!takiMonster.isDeadOrEscaped()) {
                // 闪烁效果
                timeCounter += graphics.getDeltaTime();
                alphaValue = 0.1f + 0.75f * (float) Math.abs(Math.sin(timeCounter * Math.PI / period));
                this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, alphaValue));

                // 改变碰撞箱大小
                this.hb.move(this.drawX, this.drawY + HB_H / 2 * Settings.scale);
                this.hb.height = 0;
            } else {
                // 恢复碰撞箱
                this.hb.move(this.drawX + this.hb_x + this.animX, this.drawY + this.hb_y + this.hb_h / 2.0F);
                this.hb.height = HB_H * Settings.scale;
                this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, 1));
            }
            this.hb.update();
            this.updateHealthBar();
        }
    }

    @Override
    protected Texture getAttackIntent() {
        if (!hasTalked) {
            return new Texture(imagePath("monsters/intents/attack_guitar_normal.png"));
        } else {
            return new Texture(imagePath("monsters/intents/attack_guitar_heavy.png"));
        }
    }

    @Override
    public void die() {
        super.die();
        // 如果自己挂了 + Taki 也挂了 => 触发Boss胜利
        if (this.currentHealth <= 0 && takiMonster != null && takiMonster.isDeadOrEscaped()) {
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            onBossVictoryLogic();
        }
    }
}
