package tomorimod.monsters.taki;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.monsters.BaseMonster;
import tomorimod.monsters.taki.RanaMonster;
import tomorimod.patches.MusicPatch;
import tomorimod.util.MonsterUtils;
import tomorimod.vfx.ChangeSceneEffect;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class TakiMonster extends BaseMonster {
    public static final String ID = makeID(TakiMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    public static final int HP_MIN           = 460;
    public static final int HP_MAX           = 460;
    public static final int DAMAGE_0         = 7;  // 伤害index=0
    public static final int DAMAGE_1         = 7;  // 伤害index=1
    public static final int DAMAGETIME_0     = 2;  // 第0招多段次数
    public static final int DAMAGETIME_1     = 3;  // 第2招多段次数(三段)
    public static final int BLOCK           = 20; // 加格挡基数
    public static final int PROTECT_INCREASE           = 2; // 加格挡基数
    public static final int DAMAGE_INCREASE           = 2; // 加格挡基数



    public static final int HP_MIN_WEAK           = 260;
    public static final int HP_MAX_WEAK           = 260;
    public static final int DAMAGE_0_WEAK         = 5;
    public static final int DAMAGE_1_WEAK         = 5;
    public static final int DAMAGETIME_0_WEAK     = 2; // 可同原版，也可不同
    public static final int DAMAGETIME_1_WEAK     = 2; // 这里演示弱化版少打一段
    public static final int BLOCK_WEAK           = 20; // 加格挡基数
    public static final int PROTECT_INCREASE_WEAK           = 2; // 加格挡基数
    public static final int DAMAGE_INCREASE_WEAK           = 2;

    // 怪物碰撞箱
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    // 绘制坐标
    public static final float DRAW_X = 1200.0F;
    public static final float DRAW_Y = 400.0F;

    // 怪物贴图
    private static final String imgPath = imagePath("monsters/" + TakiMonster.class.getSimpleName() + ".png");

    private int hpMinVal;
    private int hpMaxVal;
    private int damageVal0;
    private int damageVal1;
    private int damageTimeVal0;
    private int damageTimeVal1;
    private int blockVal;
    private int protectIncreaseVal;
    private int damageIncreaseVal;

    private int turnNum     = 1;
    private boolean isFirstTurn = true;
    private boolean hasTalked   = false;

    // 假设在 BaseMonster 中已有的布尔变量
    // 如果没有，可以改成构造参数传进来
    // protected boolean isTomori = false; // (可选)

    // 辅助怪
    private RanaMonster ranaMonster;

    public TakiMonster(float x, float y) {
        // 先写上一个默认的 HP 值(会在下面根据 isTomori 再次改)
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        // 根据 isTomori 来决定用原版常量，还是弱化版常量
        if (isTomori) {
            this.hpMinVal = HP_MIN;
            this.hpMaxVal = HP_MAX;

            this.damageVal0 = DAMAGE_0;
            this.damageVal1 = DAMAGE_1;
            this.damageTimeVal0 = DAMAGETIME_0;
            this.damageTimeVal1 = DAMAGETIME_1;
            this.blockVal=BLOCK;
            this.protectIncreaseVal=PROTECT_INCREASE;
            this.damageIncreaseVal=DAMAGE_INCREASE;
        } else {
            this.hpMinVal = HP_MIN_WEAK;
            this.hpMaxVal = HP_MAX_WEAK;

            this.damageVal0 = DAMAGE_0_WEAK;
            this.damageVal1 = DAMAGE_1_WEAK;
            this.damageTimeVal0 = DAMAGETIME_0_WEAK;
            this.damageTimeVal1 = DAMAGETIME_1_WEAK;
            this.blockVal=BLOCK_WEAK;
            this.protectIncreaseVal=PROTECT_INCREASE_WEAK;
            this.damageIncreaseVal=DAMAGE_INCREASE_WEAK;
        }

        // 设置 HP
        setHp(this.hpMinVal, this.hpMaxVal);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX = DRAW_X * Settings.scale;
        this.drawY = DRAW_Y * Settings.scale;

        // 把伤害Info放进 damage 列表 (index:0,1)
        this.damage.add(new DamageInfo(this, damageVal0, DamageInfo.DamageType.NORMAL)); // index 0
        this.damage.add(new DamageInfo(this, damageVal1, DamageInfo.DamageType.NORMAL)); // index 1
    }

    @Override
    public void usePreBattleAction() {
        // 召唤一个拉娜怪
        RanaMonster ranaMonster = new RanaMonster(0f, 0f);
        this.ranaMonster = ranaMonster;
        addToBot(new SpawnMonsterAction(ranaMonster, false));

        // 播放BGM & 切场景
        addToBot(new PlayBGMAction(MusicPatch.MusicHelper.MIXINGJIAO, this));
        AbstractGameEffect effect =
                new ChangeSceneEffect(ImageMaster.loadImage(imagePath("monsters/scenes/Taki_bg.png")));
        AbstractDungeon.effectList.add(effect);
        AbstractDungeon.scene.fadeOutAmbiance();

        if(isTomori){
            addToBot(new ApplyPowerAction(this, this, new TakiPressurePower(this)));
        }
        // 如果要给自己上个 Debuff 或别的 Power，也可在此添加
        // addToBot(new ApplyPowerAction(this, this, new TakiPressurePower(this)));
    }

    @Override
    protected Texture getAttackIntent() {
        // 根据拉娜怪是否存活来切换意图贴图
        if (ranaMonster != null && !ranaMonster.isDeadOrEscaped()) {
            return new Texture(imagePath("monsters/intents/attack_drum_normal.png"));
        } else {
            return new Texture(imagePath("monsters/intents/attack_drum_heavy.png"));
        }
    }

    @Override
    public void takeTurn() {
        // 额外的防御力（由 TakiProtectPower 等提供）
        int extraBlock = MonsterUtils.getPowerNum(this,"TakiProtectPower");

        switch (this.nextMove) {
            case 0:
                // 原本循环打2下 => 直接用多段打：damagePlayer(index=0, times=damageTimeVal0)
                damagePlayer(0, damageTimeVal0);
                break;

            case 1:
                // 加格挡(自己 + 拉娜) + 给自己叠加保护力
                addToBot(new GainBlockAction(this, this, blockVal + extraBlock));
                if (ranaMonster != null && !ranaMonster.isDeadOrEscaped()) {
                    addToBot(new GainBlockAction(ranaMonster, this, blockVal + extraBlock));
                }
                addToBot(new ApplyPowerAction(this, this,
                        new TakiProtectPower(this, protectIncreaseVal), protectIncreaseVal));
                break;

            case 2:
                // 三段攻击 => damagePlayer(index=1, times=damageTimeVal1)
                damagePlayer(1, damageTimeVal1);

                // 每段攻击完都加伤害值+额外防御力 => 如果要在每次攻击完即时加格挡，也可手动循环
                for (int i = 0; i < damageTimeVal1; i++) {
                    addToBot(new GainBlockAction(this, this, this.damage.get(1).base + extraBlock));
                }

                // 原代码：打完后让伤害+2，保持原有习惯
                this.damage.get(1).base += damageIncreaseVal;
                break;
        }

        // 行动结束后进入下一回合
        addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        // 第一回合 => 两段攻击
        if (isFirstTurn) {
            setMove((byte) 0, Intent.ATTACK,
                    this.damage.get(0).base, damageTimeVal0, true);
            isFirstTurn = false;
        } else {
            // 如果拉娜怪还活着，则在 0 和 1 之间循环
            if (ranaMonster != null && !ranaMonster.isDeadOrEscaped()) {
                if (turnNum % 2 == 0) {
                    setMove((byte) 0, Intent.ATTACK,
                            this.damage.get(0).base, damageTimeVal0, true);
                } else {
                    setMove((byte) 1, Intent.DEFEND_BUFF);
                }
                turnNum++;
            } else {
                // 拉娜死了 => 用 2 号大招
                if (!hasTalked) {
                    // 只说一次台词
                    addToBot(new TalkAction(this, DIALOG[0], 1.0F, 2.0F));
                    hasTalked = true;
                }
                // 播放特效与音效
                playFlameVFX();

                setMove((byte) 2, Intent.ATTACK_DEFEND,
                        this.damage.get(1).base, damageTimeVal1, true);
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
    public void die() {
        super.die();
        // 如果拉娜也死了，就执行 Boss 胜利逻辑
        if (this.currentHealth <= 0 && (ranaMonster == null || ranaMonster.isDeadOrEscaped())) {
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            onBossVictoryLogic();
        }
    }
}
