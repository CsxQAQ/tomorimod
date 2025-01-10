package tomorimod.monsters.anon;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sun.security.mscapi.PRNG;
import tomorimod.monsters.BaseMonster;
import tomorimod.util.MonsterUtils;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;


public class ChordMonster extends BaseMonster {
    public static final String ID = makeID(ChordMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    // 怪物的碰撞箱坐标和大小
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 100.0F;
    private static final float HB_H = 100.0F;

    // 定义动作 ID（byte 类型即可）
    private static final String imgPath = imagePath("monsters/" + ChordMonster.class.getSimpleName() + "_c.png");
    private static String gChordURL = imagePath("monsters/" + ChordMonster.class.getSimpleName() + "_g" + ".png");
    private static String fChordURL = imagePath("monsters/" + ChordMonster.class.getSimpleName() + "_f" + ".png");
    private static String cChordURL = imagePath("monsters/" + ChordMonster.class.getSimpleName() + "_c" + ".png");

    public int pos = -1;
    public ChordType chordType;


    // 怪物血量
    public static final int HP_MIN = 5;
    public static final int HP_MAX = 5;
    public static final int HP_INCREASE = 5;
    public static final int DAMAGE_0 = 3;
    public static final int DAMAGE_1 = 6;

    public static final int HP_MIN_WEAK = 3;
    public static final int HP_MAX_WEAK = 3;
    public static final int HP_INCREASE_WEAK = 3;
    public static final int DAMAGE_0_WEAK = 3;
    public static final int DAMAGE_1_WEAK = 3;

    private int hpMinVal;
    private int hpMaxVal;
    private int hpIncreaseVal;
    private int damageVal0;
    private int damageVal1;

    public ChordMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);


        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        if (isTomori) {
            this.hpMinVal = HP_MIN;
            this.hpMaxVal = HP_MAX;
            this.hpIncreaseVal = HP_INCREASE;
            this.damageVal0 = DAMAGE_0;
            this.damageVal1 = DAMAGE_1;
        } else {
            this.hpMinVal = HP_MIN_WEAK;
            this.hpMaxVal = HP_MAX_WEAK;
            this.hpIncreaseVal = HP_INCREASE_WEAK;
            this.damageVal0 = DAMAGE_0_WEAK;
            this.damageVal1 = DAMAGE_1_WEAK;
        }

        setHp(hpMinVal + MonsterUtils.getPowerNum("AnonMonster", "AnonLittlePractisePower") * hpIncreaseVal,
                hpMaxVal + MonsterUtils.getPowerNum("AnonMonster", "AnonLittlePractisePower") * hpIncreaseVal);

        this.damage.add(new DamageInfo(this, damageVal0, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, damageVal1, DamageInfo.DamageType.NORMAL));

        addToBot(new ApplyPowerAction(this, this, new ChordDeathPower(this)));
    }

    public void setChordName(int rand) {
        switch (rand) {
            case 0:
                img = ImageMaster.loadImage(gChordURL);
                name = "G和弦";
                chordType = ChordType.G;
                break;
            case 1:
                img = ImageMaster.loadImage(fChordURL);
                name = "F和弦";
                chordType = ChordType.F;
                break;
            case 2:
                img = ImageMaster.loadImage(cChordURL);
                name = "C和弦";
                chordType = ChordType.C;
                break;
            default:
                img = ImageMaster.loadImage(cChordURL); // 默认值
                name = "C和弦";
                chordType = ChordType.C;
                break;

        }
    }

    public void setDrawPosition(int pos) {
        this.pos = pos;
        AnonMonster.chordPos.set(pos, 1);
        AnonMonster.chordNum++;
        switch (pos) {
            case 0:
                drawX = (AnonMonster.DRAW_X + 200) * Settings.scale;
                drawY = (AnonMonster.DRAW_Y - 50) * Settings.scale;
                break;
            case 1:
                drawX = (AnonMonster.DRAW_X - 200) * Settings.scale;
                drawY = (AnonMonster.DRAW_Y - 50) * Settings.scale;
                break;
            case 2:
                drawX = (AnonMonster.DRAW_X + 0f) * Settings.scale;
                drawY = (AnonMonster.DRAW_Y + 375) * Settings.scale;
                break;
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                damagePlayer(0, 1);
                break;
            case 1:
                damagePlayer(1, 1);
                break;
        }
        // 回合结束后，准备下一次动作
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        int rand = AbstractDungeon.miscRng.random(0, 1);
        if (rand == 0) {
            setMove((byte) 0, Intent.ATTACK,
                    this.damage.get(0).base, 1, false);
        } else {
            setMove((byte) 1, Intent.ATTACK,
                    this.damage.get(1).base, 1, false);
        }
    }

    @Override
    public void die() {
        AnonMonster.chordPos.set(pos, 0);
        AnonMonster.chordNum--;
        super.die();
    }

    public enum ChordType {
        G, F, C;

        @Override
        public String toString() {
            switch (this) {
                case G:
                    return "g";
                case F:
                    return "f";
                case C:
                    return "c";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}





