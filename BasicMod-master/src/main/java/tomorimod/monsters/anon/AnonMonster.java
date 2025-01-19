package tomorimod.monsters.anon;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.monsters.BaseMonster;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class AnonMonster extends BaseMonster {
    public static final String ID = makeID(AnonMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public static int chordNum=0;
    public static ArrayList<Integer> chordPos=new ArrayList<>(Arrays.asList(0, 0, 0));
    public static ArrayList<String> chordAbsorbed=new ArrayList<>();

    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath = imagePath("monsters/"+AnonMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1400.0F;
    public static final float DRAW_Y=450.0F;

    public static final int HP_MIN       = 360;
    public static final int HP_MAX       = 360;
    public static final int DAMAGE_0     = 10;
    public static final int DAMAGE_1     = 20;
    public static final int DAMAGE_2     = 16;
    public static final int DAMAGE_3     = 15;
    public static final int DAMAGE_4     = 18;
    public static final int DAMAGETIME_0 = 1;
    public static final int DAMAGETIME_1 = 1;
    public static final int DAMAGETIME_2 = 2;
    public static final int DAMAGETIME_3 = 3;
    public static final int DAMAGETIME_4 = 3;

    public static final int HP_MIN_WEAK       = 230;
    public static final int HP_MAX_WEAK       = 230;
    public static final int DAMAGE_0_WEAK     = 6;
    public static final int DAMAGE_1_WEAK     = 12;
    public static final int DAMAGE_2_WEAK     = 16;
    public static final int DAMAGE_3_WEAK     = 15;
    public static final int DAMAGE_4_WEAK     = 18;
    public static final int DAMAGETIME_0_WEAK = 1;
    public static final int DAMAGETIME_1_WEAK = 1;
    public static final int DAMAGETIME_2_WEAK = 2;
    public static final int DAMAGETIME_3_WEAK = 3;
    public static final int DAMAGETIME_4_WEAK = 3;

    private int hpMinVal;
    private int hpMaxVal;
    private int damageVal0;
    private int damageVal1;
    private int damageVal2;
    private int damageVal3;
    private int damageVal4;
    private int damageTimeVal0;
    private int damageTimeVal1;
    private int damageTimeVal2;
    private int damageTimeVal3;
    private int damageTimeVal4;

    // 其他逻辑标记
    private boolean isAllHave=false;
    private boolean isThree=false;
    private boolean isAllSame=false;
    private int turnNum=0;

    public AnonMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        // 1. 根据 isTomori 来初始化数值
        if (isHardMode) {
            this.hpMinVal = HP_MIN;
            this.hpMaxVal = HP_MAX;

            this.damageVal0 = DAMAGE_0;
            this.damageVal1 = DAMAGE_1;
            this.damageVal2 = DAMAGE_2;
            this.damageVal3 = DAMAGE_3;
            this.damageVal4 = DAMAGE_4;

            this.damageTimeVal0 = DAMAGETIME_0;
            this.damageTimeVal1 = DAMAGETIME_1;
            this.damageTimeVal2 = DAMAGETIME_2;
            this.damageTimeVal3 = DAMAGETIME_3;
            this.damageTimeVal4 = DAMAGETIME_4;
        } else {
            this.hpMinVal = HP_MIN_WEAK;
            this.hpMaxVal = HP_MAX_WEAK;

            this.damageVal0 = DAMAGE_0_WEAK;
            this.damageVal1 = DAMAGE_1_WEAK;
            this.damageVal2 = DAMAGE_2_WEAK;
            this.damageVal3 = DAMAGE_3_WEAK;
            this.damageVal4 = DAMAGE_4_WEAK;

            this.damageTimeVal0 = DAMAGETIME_0_WEAK;
            this.damageTimeVal1 = DAMAGETIME_1_WEAK;
            this.damageTimeVal2 = DAMAGETIME_2_WEAK;
            this.damageTimeVal3 = DAMAGETIME_3_WEAK;
            this.damageTimeVal4 = DAMAGETIME_4_WEAK;
        }

        // 2. 设置 HP
        setHp(this.hpMinVal, this.hpMaxVal);
        this.type = AbstractMonster.EnemyType.BOSS;

        // 3. 其他通用初始化
        chordNum = 0;
        chordPos = new ArrayList<>(Arrays.asList(0, 0, 0));
        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;
        this.drawX = DRAW_X * Settings.scale;
        this.drawY = DRAW_Y * Settings.scale;

        // 4. 把伤害Info放进damage列表里
        this.damage.add(new DamageInfo(this, damageVal0, DamageInfo.DamageType.NORMAL)); //index 0
        this.damage.add(new DamageInfo(this, damageVal1, DamageInfo.DamageType.NORMAL)); //index 1
        this.damage.add(new DamageInfo(this, damageVal2, DamageInfo.DamageType.NORMAL)); //index 2
        this.damage.add(new DamageInfo(this, damageVal3, DamageInfo.DamageType.NORMAL)); //index 3
        this.damage.add(new DamageInfo(this, damageVal4, DamageInfo.DamageType.NORMAL)); //index 4
    }

    public void usePreBattleAction() {
        addToBot(new PlayBGMAction(MusicPatch.MusicHelper.BITIANBANZOU, this));
        AbstractGameEffect effect = new ChangeSceneEffect(
                ImageMaster.loadImage(imagePath("monsters/scenes/Anon_bg.png")));
        AbstractDungeon.effectList.add(effect);
        AbstractDungeon.scene.fadeOutAmbiance();

        // 这些是通用的power，都不区分原版或弱版
        addToBot(new ApplyPowerAction(this, this, new AnonCallPower(this)));
        addToBot(new ApplyPowerAction(this, this, new AnonGuitarSingerPower(this)));
    }

    @Override
    protected Texture getAttackIntent() {
        if (isAllHave || isThree || isAllSame) {
            return new Texture(imagePath("monsters/intents/attack_rhythm_guitar_heavy.png"));
        }
        return new Texture(imagePath("monsters/intents/attack_rhythm_guitar_normal.png"));
    }

    @Override
    public void takeTurn() {
        if(nextMove>10){
            playChordVFX();
        }
        switch (this.nextMove) {
            case 0:
                damagePlayer(0, damageTimeVal0);
                break;
            case 1:
                addToBot(new ApplyPowerAction(this, this, new AnonLittlePractisePower(this,1),1));
                addToBot(new GainBlockAction(this, 10));
                break;
            case 2:
                damagePlayer(1, damageTimeVal1);
                break;
            case 11:
                damagePlayer(2, damageTimeVal2);
                break;
            case 12:
                damagePlayer(3, damageTimeVal3);
                break;
            case 13:
                damagePlayer(4, damageTimeVal4);
                break;
        }

        moveLogic(); // 更新 isAllHave, isThree, isAllSame

        ArrayList<ChordMonster> chordMonsters = new ArrayList<>();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m instanceof ChordMonster && !m.isDeadOrEscaped()) {
                chordMonsters.add((ChordMonster) m);
            }
        }

        if (chordMonsters.size() >= 3) {
            AbstractDungeon.actionManager.addToBottom(new AbsorbChordMonstersAction(this, chordMonsters));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void playChordVFX(){
        for(int i=0; i<3; i++) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(
                    new ChordFlyingEffect(
                            hb.cX, hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY,
                            ImageMaster.loadImage(imagePath("monsters/ChordMonster_"+chordAbsorbed.get(i)+".png"))
                    ), 0.0F
            ));
            if(i!=2){
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
            }
        }
        chordAbsorbed.clear();
    }

    public void moveLogic(){
        isAllHave=false;
        isThree=false;
        isAllSame=false;

        ArrayList<ChordMonster> chordMonsters = new ArrayList<>();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m instanceof ChordMonster && !m.isDeadOrEscaped()) {
                chordMonsters.add((ChordMonster) m);
            }
        }

        if (chordMonsters.size() < 3) {
            return;
        }

        HashSet<ChordMonster.ChordType> typeSet = new HashSet<>();
        for (ChordMonster cm : chordMonsters) {
            typeSet.add(cm.chordType);
        }

        if (typeSet.size()==1){
            isAllSame=true;
        } else if (typeSet.size()==2){
            isThree=true;
        } else {
            isAllHave=true;
        }
    }

    @Override
    protected void getMove(int num) {
        if (isThree) {
            setMove(MOVES[0], (byte)11, Intent.ATTACK,
                    this.damage.get(2).base, damageTimeVal2, true);
        } else if (isAllSame) {
            setMove(MOVES[1], (byte)12, Intent.ATTACK,
                    this.damage.get(3).base, damageTimeVal3, true);
        } else if (isAllHave) {
            setMove(MOVES[1], (byte)13, Intent.ATTACK,
                    this.damage.get(4).base, damageTimeVal4, true);
        } else {
            switch (turnNum % 3) {
                case 0:
                    setMove((byte)0, Intent.ATTACK,
                            this.damage.get(0).base, damageTimeVal0, false);
                    break;
                case 1:
                    setMove((byte)1, Intent.DEFEND_BUFF);
                    break;
                case 2:
                    setMove((byte)2, Intent.ATTACK,
                            this.damage.get(1).base, damageTimeVal1, false);
                    break;
            }
            turnNum++;
        }
    }

    @Override
    public void die() {
        super.die();
        CardCrawlGame.sound.playA(makeID("tangcry"), 0.0F);

        ArrayList<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        for(AbstractMonster m : monsters) {
            if(m.currentHealth>0 && m instanceof ChordMonster) {
                m.die();
            }
        }
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        onBossVictoryLogic();
    }
}
