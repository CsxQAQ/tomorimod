package tomorimod.monsters.saki;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import tomorimod.monsters.BaseMonster;
import tomorimod.monsters.taki.RanaFreeCatPower;
import tomorimod.monsters.taki.TakiMonster;

import static com.badlogic.gdx.Gdx.graphics;
import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class SakiMonster extends BaseMonster {
    public static final String ID = makeID(SakiMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public static final int HP_MIN         = 200;
    public static final int HP_MAX         = 20;
    public static final int DAMAGE_0       = 8;
    public static final int DAMAGE_1       = 12;
    public static final int DAMAGETIME_0   = 2;
    public static final int DAMAGETIME_1   = 3;
    public static final int HEART_WALL   = 20;


    public static final int HP_MIN_WEAK         = 200;
    public static final int HP_MAX_WEAK         = 10;
    public static final int DAMAGE_0_WEAK       = 6;
    public static final int DAMAGE_1_WEAK       = 10;
    public static final int DAMAGETIME_0_WEAK   = 2;
    public static final int DAMAGETIME_1_WEAK   = 2;
    public static final int HEART_WALL_WEAK   = 20;


    private int hpMinVal;
    private int hpMaxVal;
    private int damageVal0;
    private int damageVal1;
    private int damageTimeVal0;
    private int damageTimeVal1;


    // 怪物碰撞箱
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    // 绘制坐标
    public static final float DRAW_X = 1600.0F;
    public static final float DRAW_Y = 400.0F;

    // 贴图
    private static final String imgPath = imagePath("monsters/" + SakiMonster.class.getSimpleName() + ".png");


    public SakiMonster(float x, float y) {
        // 先用原版的 maxHP 初始化 parent
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        if (isHardMode) {
            this.hpMinVal = HP_MIN;
            this.hpMaxVal = HP_MAX;
            this.damageVal0 = DAMAGE_0;
            this.damageVal1 = DAMAGE_1;
            this.damageTimeVal0 = DAMAGETIME_0;
            this.damageTimeVal1 = DAMAGETIME_1;
        } else {
            this.hpMinVal = HP_MIN_WEAK;
            this.hpMaxVal = HP_MAX_WEAK;
            this.damageVal0 = DAMAGE_0_WEAK;
            this.damageVal1 = DAMAGE_1_WEAK;
            this.damageTimeVal0 = DAMAGETIME_0_WEAK;
            this.damageTimeVal1 = DAMAGETIME_1_WEAK;
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

//        addToBot(new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 999), 999));
//        addToBot(new ApplyPowerAction(this, this, new RanaFreeCatPower(this)));
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {

        }
        addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {

    }

    @Override
    protected Texture getAttackIntent() {
        return new Texture(imagePath("monsters/intents/attack_guitar_normal.png"));
    }

    @Override
    public void die() {
        super.die();
        if (this.currentHealth <= 0) {
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            onBossVictoryLogic();
        }
    }
}
