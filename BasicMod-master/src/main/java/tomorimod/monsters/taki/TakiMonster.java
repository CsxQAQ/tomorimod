package tomorimod.monsters.taki;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
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
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.monsters.BaseMonster;
import tomorimod.patches.MusicPatch;
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


    // 怪物血量
    private static final int HP_MIN = 260;
    private static final int HP_MAX = 260;

    // 怪物的碰撞箱坐标和大小
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath=imagePath("monsters/"+ TakiMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1200.0F;
    public static final float DRAW_Y=400.0F;
    private RanaMonster ranaMonster;

    private int turnNum=1;
    private boolean isFirstTurn=true;
    private boolean hasTalked=false;

    public TakiMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);


        setHp(HP_MIN, HP_MAX);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;

        this.damage.add(new DamageInfo(this, 5, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 8, DamageInfo.DamageType.NORMAL));




//        this.damage.add(new DamageInfo(this, 12, DamageInfo.DamageType.NORMAL));
//        this.damage.add(new DamageInfo(this, 18, DamageInfo.DamageType.NORMAL));
//        this.damage.add(new DamageInfo(this, 30, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public void usePreBattleAction() {
        RanaMonster ranaMonster=new RanaMonster(0f,0f);
        this.ranaMonster=ranaMonster;
        addToBot(new SpawnMonsterAction(ranaMonster,false));

        addToBot(new PlayBGMAction(MusicPatch.MusicHelper.MIXINGJIAO,this));
        AbstractGameEffect effect = new ChangeSceneEffect(ImageMaster.loadImage(imagePath("monsters/scenes/Taki_bg.png")));
        AbstractDungeon.effectList.add(effect);
        AbstractDungeon.scene.fadeOutAmbiance();

        //addToBot(new ApplyPowerAction(this,this,new TakiPressurePower(this)));

    }


    @Override
    protected Texture getAttackIntent() {
        //super.getAttackIntent();
        if(ranaMonster!=null&&!ranaMonster.isDeadOrEscaped()){
            return new Texture(imagePath("monsters/intents/attack_drum_normal.png"));
        }else{
            return new Texture(imagePath("monsters/intents/attack_drum_heavy.png"));
        }
    }

    private final int BLOCK=20;

    @Override
    public void takeTurn() {
        int block=0;
        if(this.hasPower(makeID("TakiProtectPower"))){
            block=block+this.getPower(makeID("TakiProtectPower")).amount;
        }
        switch (this.nextMove) {
            case 0:
                for(int i=0;i<2;i++){
                    addToBot(new DamageAction(AbstractDungeon.player,
                            this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }

                break;

            case 1:

                addToBot(new GainBlockAction(this,this,BLOCK+block));
                if(ranaMonster!=null&&!ranaMonster.isDeadOrEscaped()){
                    addToBot(new GainBlockAction(ranaMonster,this,BLOCK+block));
                }
                addToBot(new ApplyPowerAction(this,this,new TakiProtectPower(this,2),2));
                break;
            case 2:

                for(int i=0;i<3;i++){
                    addToBot(new DamageAction(AbstractDungeon.player,
                            this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    addToBot(new GainBlockAction(this,this.damage.get(1).base+block));
                }
                this.damage.get(1).base+=2;
                break;

        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        if(isFirstTurn){
            setMove( (byte)0, Intent.ATTACK,
                    this.damage.get(0).base, 2, true);
            isFirstTurn=false;
        }else {
            if(ranaMonster!=null&&!ranaMonster.isDeadOrEscaped()){
                if(turnNum%2==0){
                    setMove( (byte)0, Intent.ATTACK,
                            this.damage.get(0).base, 2, true);
                }else{
                    setMove( (byte)1, Intent.DEFEND_BUFF);
                }
                turnNum++;
            }else{
                if(!hasTalked){
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 1.0F, 2.0F));
                    hasTalked=true;
                }
                addToBot(new SFXAction("MONSTER_CHAMP_CHARGE"));
                addToBot(new VFXAction(this, new InflameEffect(this), 0.25F));
                addToBot(new VFXAction(this, new InflameEffect(this), 0.25F));
                addToBot(new VFXAction(this, new InflameEffect(this), 0.25F));
                setMove( (byte)2, Intent.ATTACK_DEFEND,
                        this.damage.get(1).base, 3, true);
            }
        }

    }

    @Override
    public void die() {
        super.die();

        if (this.currentHealth <= 0&&ranaMonster.isDeadOrEscaped()) {
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            onBossVictoryLogic();
        }
    }
}


