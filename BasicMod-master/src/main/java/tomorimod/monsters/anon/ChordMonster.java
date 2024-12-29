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

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;


public class ChordMonster extends CustomMonster {
    public static final String ID = makeID(ChordMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    // 怪物血量
    private static final int HP_MIN = 5;
    private static final int HP_MAX = 5;

    // 怪物的碰撞箱坐标和大小
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 100.0F;
    private static final float HB_H = 100.0F;

    // 定义动作 ID（byte 类型即可）
    private static final byte ATTACK_MOVE = 1;       // 第一回合上仪式
    private static final byte HEAVY_ATTACK_MOVE = 2;       // 第一回合上仪式
    private static final String imgPath=imagePath("monsters/"+ ChordMonster.class.getSimpleName()+"_c.png");
    private static String gChordURL=imagePath("monsters/"+ChordMonster.class.getSimpleName()+"_g"+".png");
    private static String fChordURL=imagePath("monsters/"+ChordMonster.class.getSimpleName()+"_f"+".png");
    private static String cChordURL=imagePath("monsters/"+ChordMonster.class.getSimpleName()+"_c"+".png");

    public int pos=-1;
    public ChordType chordType;

    public ChordMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        setHp(HP_MIN, HP_MAX);

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;


        addToBot(new ApplyPowerAction(this,this,new ChordImmunityPower(this)));
        addToBot(new ApplyPowerAction(this,this,new ChordDeathPower(this)));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m instanceof AnonMonster && !m.isDeadOrEscaped()) {
                if(m.hasPower(makeID("AnonLittlePractisePower"))){
                    setHp(HP_MIN+m.getPower(makeID("AnonLittlePractisePower")).amount*5,
                            HP_MAX+m.getPower(makeID("AnonLittlePractisePower")).amount*5);

                }
            }
        }


        this.damage.add(new DamageInfo(this, 3, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 3, DamageInfo.DamageType.NORMAL));
    }

    public void setChordName(int rand){
        switch (rand){
            case 0:
                img= ImageMaster.loadImage(gChordURL);
                name="G和弦";
                chordType=ChordType.G;
                break;
            case 1:
                img=ImageMaster.loadImage(fChordURL);
                name="F和弦";
                chordType=ChordType.F;
                break;
            case 2:
                img=ImageMaster.loadImage(cChordURL);
                name="C和弦";
                chordType=ChordType.C;
                break;
            default:
                img = ImageMaster.loadImage(cChordURL); // 默认值
                name = "C和弦";
                chordType=ChordType.C;
                break;

        }
    }

    public void setDrawPosition(int pos){
        this.pos=pos;
        AnonMonster.chordPos.set(pos,1);
        AnonMonster.chordNum++;
        switch (pos){
            case 0:
                drawX=(AnonMonster.DRAW_X+200)*Settings.scale;
                drawY=(AnonMonster.DRAW_Y-50)*Settings.scale;
                break;
            case 1:
                drawX=(AnonMonster.DRAW_X-200)*Settings.scale;
                drawY=(AnonMonster.DRAW_Y-50)*Settings.scale;
                break;
            case 2:
                drawX=(AnonMonster.DRAW_X+0f)*Settings.scale;
                drawY=(AnonMonster.DRAW_Y+375)*Settings.scale;
                break;
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case ATTACK_MOVE:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                                this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                break;
            case HEAVY_ATTACK_MOVE:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                        this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                break;
        }
        // 回合结束后，准备下一次动作
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        int rand = AbstractDungeon.miscRng.random(0, 1);
        if (rand==0) {
            setMove( ATTACK_MOVE, Intent.ATTACK,
                    this.damage.get(0).base, 1, false);
        } else {
            setMove( HEAVY_ATTACK_MOVE, Intent.ATTACK,
                    this.damage.get(1).base, 1, false);
        }
    }

    @Override
    public void die() {
        AnonMonster.chordPos.set(pos,0);
        AnonMonster.chordNum--;
        super.die();
    }

    public enum ChordType {
        G, F, C;
        @Override
        public String toString() {
            switch (this) {
                case G: return "g";
                case F: return "f";
                case C: return "c";
                default: throw new IllegalArgumentException();
            }
        }
    }
}





