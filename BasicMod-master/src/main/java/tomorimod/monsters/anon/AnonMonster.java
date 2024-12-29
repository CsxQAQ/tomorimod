package tomorimod.monsters.anon;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static tomorimod.TomoriMod.makeID;
import static tomorimod.TomoriMod.imagePath;


public class AnonMonster extends CustomMonster {
    public static final String ID = makeID(AnonMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public static int chordNum=0;
    public static ArrayList<Integer> chordPos=new ArrayList<>(Arrays.asList(0, 0, 0));

    // 怪物血量
    private static final int HP_MIN = 200;
    private static final int HP_MAX = 200;

    // 怪物的碰撞箱坐标和大小
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath=imagePath("monsters/"+AnonMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1400.0F;
    public static final float DRAW_Y=450.0F;

    private boolean isAllHave=false;
    private boolean isThree=false;
    private boolean isAllSame=false;

    // 给自己做个标记，是否已经使用过 Ritual
    private boolean hasUsedRitual = false;

    public AnonMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        chordNum=0;
        chordPos=new ArrayList<>(Arrays.asList(0, 0, 0));

        // setHp(HP_MAX, HP_MIN); // 你原本写的，建议改为：
        setHp(HP_MIN, HP_MAX);

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;
        // 定义一个伤害动作，这里假设伤害是 6


        this.damage.add(new DamageInfo(this, 6, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 12, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 18, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 30, DamageInfo.DamageType.NORMAL));

    }

    public void usePreBattleAction() {

        addToBot(new ApplyPowerAction(this, this, new AnonCallPower(this)));
        addToBot(new ApplyPowerAction(this, this, new AnonGuitarSingerPower(this)));
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                                this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                break;
            case 1:
                for(int i=0;i<2;i++){
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                            this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                break;
            case 2:
                for(int i=0;i<3;i++){
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                            this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                break;
            case 3:
                for(int i=0;i<5;i++){
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                            this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                break;
        }

        moveLogic();

        ArrayList<ChordMonster> chordMonsters = new ArrayList<>();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m instanceof ChordMonster && !m.isDeadOrEscaped()) {
                chordMonsters.add((ChordMonster) m);
            }
        }

        // （3）如果正好 >= 3，就触发“吸收”效果
        if (chordMonsters.size() >= 3) {
            AbstractDungeon.actionManager.addToBottom(new AbsorbChordMonstersAction(this, chordMonsters));
        }

        // 回合结束后，准备下一次动作
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
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

        if(typeSet.size()==1){
            isAllSame=true;
        }else if (typeSet.size()==2){
            isThree=true;
        }else{
            isAllHave=true;
        }
    }

    @Override
    protected void getMove(int num) {
        if(isThree){
            setMove(MOVES[1], (byte)1, Intent.ATTACK,
                    this.damage.get(1).base, 2, true);
        }else if(isAllSame){
            setMove(MOVES[2], (byte)2, Intent.ATTACK,
                    this.damage.get(2).base, 3, true);
        }else if(isAllHave){
            setMove(MOVES[3], (byte)3, Intent.ATTACK,
                    this.damage.get(3).base, 5, true);
        }else{
            setMove(MOVES[0], (byte)0, Intent.ATTACK,
                    this.damage.get(0).base, 1, false);
        }
    }

    @Override
    public void die() {
        ArrayList<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        for(AbstractMonster m:monsters){
            if(m.currentHealth>0&&m instanceof ChordMonster){
                m.die();
            }
        }
        super.die();
    }
}


