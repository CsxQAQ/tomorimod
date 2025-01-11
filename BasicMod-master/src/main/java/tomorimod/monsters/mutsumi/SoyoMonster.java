package tomorimod.monsters.mutsumi;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;


public class SoyoMonster extends SpecialMonster {
    public static final String ID = makeID(SoyoMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;



    // 怪物血量
    private static final int HP_MIN = 200;
    private static final int HP_MAX = 200;

    // 怪物的碰撞箱坐标和大小
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath=imagePath("monsters/"+ SoyoMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1600.0F;
    public static final float DRAW_Y=450.0F;
    private boolean isMutumiGet;
    private MutsumiMonster mutsumiMonster;

    private int point=0;


    public SoyoMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        setHp(HP_MIN, HP_MAX);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;


        this.damage.add(new DamageInfo(this, 20, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 5, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 200, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 4, DamageInfo.DamageType.NORMAL));


    }

    @Override
    public void update(){
        super.update();
        if(!isMutumiGet){
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m instanceof MutsumiMonster && !m.isDeadOrEscaped()) {
                    MutsumiMonster mutumi = (MutsumiMonster) m;
                    this.mutsumiMonster = mutumi;
                    isMutumiGet = true;
                    target= mutsumiMonster;
                }
            }
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                soyoAttack(0);
                break;
            case 1:
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,
                        new PlatedArmorPower(AbstractDungeon.player,10),10));
                break;
            case 2:
                for(int i=0;i<5;i++){
                    soyoAttack(1);
                }
                break;
            case 3:
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        increaseMaxHp(40,true);
                        isDone=true;
                    }
                });
                break;
            case 4:
                soyoAttack(2);
                break;
            case 5:
                for(int i=0;i<10;i++){
                    soyoAttack(3);
                }
                break;
            case 99:
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void soyoAttack(int k){
        if(target==AbstractDungeon.player){
            addToBot(new DamageAction(target,
                    this.damage.get(k), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }else{
            DamageInfo newInfo=new DamageInfo(this,this.damage.get(k).base);
            newInfo.applyPowers(this,mutsumiMonster);
            addToBot(new DamageAction(target,new DamageInfo(this,newInfo.output)));
        }
    }

    @Override
    protected Texture getAttackIntent() {
        if(nextMove==4||nextMove==5){
            return new Texture(imagePath("monsters/intents/attack_bass_heavy.png"));
        }
        return new Texture(imagePath("monsters/intents/attack_bass_normal.png"));

    }

    @Override
    protected void getMove(int num) {
        int rand=AbstractDungeon.miscRng.random(point);
        if(rand>3){
            rand=3;
        }
        int tmp=AbstractDungeon.miscRng.random(1);
        switch (rand){
            case 0:
                if(tmp==0){
                    setMove( (byte)0, Intent.ATTACK,
                            this.damage.get(0).base, 1, false);
                }else{
                    setMove((byte)1,Intent.DEFEND);
                }
                point+=2;
                break;
            case 1:
            case 2:
                if(tmp==0){
                    setMove((byte)2,Intent.ATTACK,
                            this.damage.get(1).base,5,true);
                }else{
                    setMove((byte)3,Intent.BUFF);
                }
                point++;
                break;
            case 3:
                if(tmp==0){
                    setMove((byte)4,Intent.ATTACK,
                            this.damage.get(2).base,1,false);
                }else{
                    setMove((byte)5,Intent.ATTACK,
                            this.damage.get(3).base,15,true);
                }
                point=point-2;
                break;
        }
    }

    @Override
    public void die() {
        super.die();

    }
}


