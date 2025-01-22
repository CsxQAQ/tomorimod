package tomorimod.monsters.mutsumioperator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.monsters.BaseMonster;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;

import java.util.ArrayList;
import java.util.List;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class MutsumiOperatorMonster extends BaseMonster {
    public static final String ID = makeID(MutsumiOperatorMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public static final int HP_MIN         = 1;
    public static final int HP_MAX         = 1;
    public static final int DAMAGE_0       = 8;
    public static final int DAMAGE_1       = 12;
    public static final int DAMAGETIME_0   = 2;
    public static final int DAMAGETIME_1   = 3;


    public static final int HP_MIN_WEAK         = 1;
    public static final int HP_MAX_WEAK         = 1;
    public static final int DAMAGE_0_WEAK       = 8;
    public static final int DAMAGE_1_WEAK       = 12;
    public static final int DAMAGETIME_0_WEAK   = 2;
    public static final int DAMAGETIME_1_WEAK   = 3;


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
    public static final float DRAW_X = 1500.0F;
    public static final float DRAW_Y = 600.0F;

    // 贴图
    private static final String imgPath = imagePath("monsters/" + MutsumiOperatorMonster.class.getSimpleName() + ".png");

    public static final float STANDARDDISTANCE=200.0F;
    public List<SakiMachineMonster> sakiMachineMonsterList=new ArrayList<>();
    //private SakiMachineMonster sakiMachineMonster1;
    private boolean isFirstTurn;

    public MutsumiOperatorMonster(float x, float y) {
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

        for(int i=0;i<4;i++){
            sakiMachineMonsterList.add(new SakiMachineMonster(0f,0f));
        }

        isFirstTurn = true;
    }

    @Override
    public void usePreBattleAction() {
        // 播放BGM & 切场景
        addToBot(new PlayBGMAction(MusicPatch.MusicHelper.MIXINGJIAO, this));
        AbstractGameEffect effect =
                new ChangeSceneEffect(ImageMaster.loadImage(imagePath("monsters/scenes/Taki_bg.png")));
        AbstractDungeon.effectList.add(effect);
        AbstractDungeon.scene.fadeOutAmbiance();

        AbstractDungeon.player.drawX=this.drawX-5*STANDARDDISTANCE*Settings.scale;
        AbstractDungeon.player.drawY = this.drawY;

        addToBot(new ApplyPowerAction(this,this,new CantBeAttackedPower(this)));

    }


    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                damagePlayer(0, damageTimeVal0);
                break;
            case 1:
                damagePlayer(1, damageTimeVal1);
                break;

            case 50:
                if(!sakiMachineMonsterList.get(0).isDeadOrEscaped()){
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            sakiMachineMonsterList.get(0).move(1.0f);
                            isDone=true;
                        }
                    });
                }
                break;
            case 51:
                for(SakiMachineMonster sakiMachineMonster : sakiMachineMonsterList){
                    if(!sakiMachineMonster.isDeadOrEscaped()){
                        addToBot(new AbstractGameAction() {
                            @Override
                            public void update() {
                                sakiMachineMonster.move(1.0f);
                                isDone=true;
                            }
                        });
                    }
                }
                break;

            case 99:
                spawnSaki(0,-STANDARDDISTANCE*Settings.scale,0,4);
                break;

            case 100:
                spawnSaki(1,-STANDARDDISTANCE*Settings.scale,0,4);
                spawnSaki(2,0,-STANDARDDISTANCE*Settings.scale,6);
                spawnSaki(3,0,STANDARDDISTANCE*Settings.scale,6);
                break;


        }
        // 回合结束后，准备下一次动作
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void spawnSaki(int index,float xOffset,float yOffset,int distance){
        SakiMachineMonster sakiMachineMonster = sakiMachineMonsterList.get(index);
        sakiMachineMonster.drawX=this.drawX+xOffset;
        sakiMachineMonster.drawY=this.drawY+yOffset;
        sakiMachineMonster.targetDrawX=sakiMachineMonster.drawX;
        sakiMachineMonster.targetDrawY=sakiMachineMonster.drawY;
        sakiMachineMonster.distance=distance;
        addToBot(new SpawnMonsterAction(sakiMachineMonster,false));
        addToBot(new ApplyPowerAction(sakiMachineMonster,this,new SakiMachineDistancePower(sakiMachineMonster)));
    }


    @Override
    protected void getMove(int num) {
        int rand = AbstractDungeon.miscRng.random(0, 1);
        if(isFirstTurn){
            setMove((byte)100,Intent.UNKNOWN);
            isFirstTurn = false;
        }else{
            setMove((byte)51,Intent.BUFF);
//            if (rand == 0) {
//                setMove((byte) 0, Intent.ATTACK,
//                        this.damage.get(0).base, damageTimeVal0, true);
//            } else {
//                setMove((byte) 1, Intent.ATTACK,
//                        this.damage.get(1).base, damageTimeVal1, true);
//            }
        }
    }

    @Override
    public void showHealthBar(){
        hideHealthBar();
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
