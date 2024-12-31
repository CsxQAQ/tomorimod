package tomorimod.monsters.mutsumi;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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


    public SoyoMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        // setHp(HP_MAX, HP_MIN); // 你原本写的，建议改为：
        setHp(HP_MIN, HP_MAX);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;


        this.damage.add(new DamageInfo(this, 6, DamageInfo.DamageType.NORMAL));

        addToBot(new ApplyPowerAction(this,this,new FriendlyMonsterPower(this)));
        addToBot(new ApplyPowerAction(this,this,new SoyoMultiChangePower(this)));

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

                AbstractDungeon.actionManager.addToBottom(new DamageAction(target,
                        this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

                break;

        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }




    @Override
    protected void getMove(int num) {

        setMove( (byte)0, Intent.ATTACK,
                this.damage.get(0).base, 1, false);

    }

    @Override
    public void die() {
        super.die();

    }
}


