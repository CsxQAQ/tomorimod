package tomorimod.monsters.mutsumi;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;

import java.util.ArrayList;
import java.util.Arrays;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;


public class MutsumiMonster extends SpecialMonster {
    public static final String ID = makeID(MutsumiMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public static int chordNum=0;
    public static ArrayList<Integer> chordPos=new ArrayList<>(Arrays.asList(0, 0, 0));
    public static ArrayList<String> chordAbsorbed=new ArrayList<>();

    private static final int HP_MIN = 2000;
    private static final int HP_MAX = 2000;

    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath=imagePath("monsters/"+ MutsumiMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1000.0F;
    public static final float DRAW_Y=450.0F;
    private SoyoMonster soyoMonster;

    public static final int CUCUMBER=20;
    public static final int CUCUMBER_UPG=3;
    public static final int HEALNUM=50;
    public static final int STRENGTHNUM=2;
    public static final int POWERNUM=2;
    private int point=0;




    public MutsumiMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        chordNum=0;
        chordPos=new ArrayList<>(Arrays.asList(0, 0, 0));

        setHp(HP_MIN, HP_MAX);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;


        this.damage.add(new DamageInfo(this, 20, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 5, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 120, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 18, DamageInfo.DamageType.NORMAL));
        this.target=soyoMonster;
    }

    public void usePreBattleAction() {
        addToBot(new PlayBGMAction(MusicPatch.MusicHelper.HEISESHENGRI,this));
        AbstractGameEffect effect = new ChangeSceneEffect(ImageMaster.loadImage(imagePath("monsters/scenes/Uika_bg.png")));
        AbstractDungeon.effectList.add(effect);
        AbstractDungeon.scene.fadeOutAmbiance();

        SoyoMonster soyoMonster=new SoyoMonster(0f,0f);
        this.soyoMonster=soyoMonster;
        target=soyoMonster;
        addToBot(new ApplyPowerAction(soyoMonster,this,new FriendlyMonsterPower(soyoMonster)));
        addToBot(new ApplyPowerAction(soyoMonster,this,new SoyoMultiChangePower(soyoMonster)));
        addToBot(new SpawnMonsterAction(soyoMonster,false));


        addToBot(new ApplyPowerAction(this,this,new MutsumiOneHeartTwoHurtPower(this,soyoMonster)));
        addToBot(new ApplyPowerAction(this,this,new MutsumiGiveCucumberPower(this,CUCUMBER),CUCUMBER));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new BehindAttackPower(AbstractDungeon.player)));
        AbstractDungeon.player.drawY=DRAW_Y*Settings.scale;
    }

    @Override
    public void takeTurn() {

        switch (this.nextMove) {
            case 0:
                addToBot(new DamageAction(target,
                        this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                break;
            case 1:
                addToBot(new ApplyPowerAction(this,this,new MutsumiGiveCucumberPower(this,CUCUMBER_UPG),CUCUMBER_UPG));
                break;
            case 2:
                for(int i=0;i<5;i++){
                    addToBot(new DamageAction(target,
                            this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                break;
            case 3:
                addToBot(new ApplyPowerAction(target,this,new VulnerablePower(target,POWERNUM,true),POWERNUM));
                addToBot(new ApplyPowerAction(target,this,new WeakPower(target,POWERNUM,true),POWERNUM));
                break;
            case 4:
                addToBot(new DamageAction(target,
                        this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                break;
            case 5:
                for (int i = 0; i < 3; i++) {
                    addToBot(new VFXAction(this, new ShockWaveEffect(
                            this.hb.cX, this.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.75F));

                    addToBot(new MonsterDamageAllAction(this,this.damage.get(3),AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    isMultiTarget=false;
                }
                break;
            case 99:
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void update(){
        super.update();
        if(soyoMonster!=null){
            if(soyoMonster.isDeadOrEscaped()){
                target=AbstractDungeon.player;
                if(AbstractDungeon.player.hasPower(makeID("BehindAttackPower"))){
                    addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,makeID("BehindAttackPower")));
                }
            }
        }

        if (target != AbstractDungeon.player) {
            this.flipHorizontal = false;
        } else {
            this.flipHorizontal = true;
        }
    }

    @Override
    protected void getMove(int num) {
        int rand=AbstractDungeon.miscRng.random(point);
        if(rand>2){
            rand=2;
        }
        int tmp=AbstractDungeon.miscRng.random(1);
        switch (rand){
            case 0:
                if(tmp==0){
                    setMove( (byte)0, Intent.ATTACK,
                            this.damage.get(0).base, 1, false);
                }else{
                    setMove((byte)1,Intent.DEFEND_BUFF);
                }
                point++;
                break;
            case 1:
                if(tmp==0){
                    setMove((byte)2,Intent.ATTACK,
                            this.damage.get(1).base,5,true);
                }else{
                    setMove((byte)3,Intent.DEFEND_DEBUFF);
                }
                point--;
                break;
            case 2:
                if(tmp==0){
                    setMove((byte)4,Intent.ATTACK,
                            this.damage.get(2).base,1,false);
                }else{
                    isMultiTarget=true;
                    setMove((byte)5,Intent.ATTACK,
                            this.damage.get(3).base,3,true);
                }
                point=point-2;
                break;
        }
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


