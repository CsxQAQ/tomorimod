package tomorimod.monsters.mutsumi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.patches.MusicPatch;
import tomorimod.util.MonsterUtils;
import tomorimod.vfx.ChangeSceneEffect;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;


public class MutsumiMonster extends SpecialMonster {
    public static final String ID = makeID(MutsumiMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath=imagePath("monsters/"+ MutsumiMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1000.0F;
    public static final float DRAW_Y=450.0F;
    private SoyoMonster soyoMonster;

    private int point=0;

    public MutsumiWarningUi mutsumiWarningUi;
    public SoyoWarningUi soyoWarningUi;


    public static final int HP_MIN       = 3000;
    public static final int HP_MAX       = 3000;
    public static final int DAMAGE_0     = 20;
    public static final int DAMAGE_1     = 12;
    public static final int DAMAGE_2     = 5;
    public static final int DAMAGETIME_0 = 1;
    public static final int DAMAGETIME_1 = 5;
    public static final int DAMAGETIME_2 = 3;
    public static final int CUCUMBER=10;
    public static final int CUCUMBER_UPG=3;
    public static final int HEALNUM=50;
    public static final int STRENGTHNUM=2;
    public static final int POWERNUM=2;

    public static final int HP_MIN_WEAK       = 1500;
    public static final int HP_MAX_WEAK       = 1500;
    public static final int DAMAGE_0_WEAK     = 20;
    public static final int DAMAGE_1_WEAK     = 12;
    public static final int DAMAGE_2_WEAK     = 10;
    public static final int DAMAGETIME_0_WEAK = 1;
    public static final int DAMAGETIME_1_WEAK = 5;
    public static final int DAMAGETIME_2_WEAK = 3;
    public static final int CUCUMBER_WEAK=5;
    public static final int CUCUMBER_UPG_WEAK=3;
    public static final int HEALNUM_WEAK=50;
    public static final int STRENGTHNUM_WEAK=2;
    public static final int POWERNUM_WEAK=2;

    private int hpMinVal;
    private int hpMaxVal;
    private int damageVal0;
    private int damageVal1;
    private int damageVal2;
    private int damageTimeVal0;
    private int damageTimeVal1;
    private int damageTimeVal2;
    private int cucumberVal;
    private int cucumberUpgVal;
    private int powerVal;


    public MutsumiMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        if(isHardMode){
            this.hpMinVal = HP_MIN;
            this.hpMaxVal = HP_MAX;
            this.damageVal0 = DAMAGE_0;
            this.damageVal1 = DAMAGE_1;
            this.damageVal2 = DAMAGE_2;
            this.damageTimeVal0 = DAMAGETIME_0;
            this.damageTimeVal1 = DAMAGETIME_1;
            this.damageTimeVal2 = DAMAGETIME_2;
            this.cucumberVal = CUCUMBER;
            this.cucumberUpgVal = CUCUMBER_UPG;
            this.powerVal = POWERNUM;
        } else{
            this.hpMinVal = HP_MIN_WEAK;
            this.hpMaxVal = HP_MAX_WEAK;
            this.damageVal0 = DAMAGE_0_WEAK;
            this.damageVal1 = DAMAGE_1_WEAK;
            this.damageVal2 = DAMAGE_2_WEAK;
            this.damageTimeVal0 = DAMAGETIME_0_WEAK;
            this.damageTimeVal1 = DAMAGETIME_1_WEAK;
            this.damageTimeVal2 = DAMAGETIME_2_WEAK;
            this.cucumberVal = CUCUMBER_WEAK;
            this.cucumberUpgVal = CUCUMBER_UPG_WEAK;
            this.powerVal = POWERNUM_WEAK;
        }

        setHp(hpMinVal, hpMaxVal);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;


        this.damage.add(new MutsumiDamageInfo(this, damageVal0, DamageInfo.DamageType.NORMAL));
        this.damage.add(new MutsumiDamageInfo(this, damageVal1, DamageInfo.DamageType.NORMAL));
        this.damage.add(new MutsumiDamageInfo(this, damageVal2, DamageInfo.DamageType.NORMAL));
        this.target=soyoMonster;


        mutsumiWarningUi=new MutsumiWarningUi(this);

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
        addToBot(new ApplyPowerAction(this,this,new MutsumiGiveCucumberPower(this,cucumberVal),cucumberVal));
        if(isHardMode){
            addToBot(new ApplyPowerAction(this,this,new MutsumiRealDamagePower(this)));
        }
        addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new BehindAttackPower(AbstractDungeon.player)));
        AbstractDungeon.player.drawY=DRAW_Y*Settings.scale;

        soyoWarningUi=new SoyoWarningUi(this,soyoMonster);
    }

    @Override
    public void takeTurn() {

        switch (this.nextMove) {
            case 0:
                mutsumiAttack(0,damageTimeVal0);
                break;
            case 1:
                addToBot(new ApplyPowerAction(this,this,new MutsumiGiveCucumberPower(this,cucumberUpgVal),cucumberUpgVal));
                break;
            case 2:
                mutsumiAttack(1,damageTimeVal1);
                break;
            case 3:
                addToBot(new ApplyPowerAction(target,this,new VulnerablePower(target,powerVal,true),powerVal));
                addToBot(new ApplyPowerAction(target,this,new WeakPower(target,powerVal,true),powerVal));
                break;
//            case 4:
//                addToBot(new DamageAction(target,
//                        this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
//                break;
            case 5:
                //mutsumiWarningUi.setFrozen();
                //soyoWarningUi.setFrozen();
                for (int i = 0; i < 3; i++) {
                    addToBot(new VFXAction(this, new ShockWaveEffect(
                            this.hb.cX, this.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.75F));

                    addToBot(new MonsterDamageAllAction(this,this.damage.get(2),AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isMultiTarget=false;
                        isDone=true;
                    }
                });
                break;
            case 99:
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void mutsumiAttack(int index,int times){
        for(int i=0;i<times;i++){
            if(target==AbstractDungeon.player){
                addToBot(new DamageAction(target,
                        this.damage.get(index), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }else{
                MutsumiDamageInfo newInfo=new MutsumiDamageInfo(this,this.damage.get(index).base);
                newInfo.applyPowers(this,soyoMonster);
                addToBot(new DamageAction(target,new MutsumiDamageInfo(this,newInfo.output)));
            }
        }
    }

    @SpireOverride
    public void calculateDamage(int dmg) {
        setPrivateField(this, "intentDmg", calculateDamageMulti(dmg));
    }

    @Override
    protected Texture getAttackIntent() {
        if(nextMove==4||nextMove==5){
            return new Texture(imagePath("monsters/intents/attack_guitar_heavy.png"));
        }
        return new Texture(imagePath("monsters/intents/attack_guitar_normal.png"));

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
                            this.damage.get(0).base, damageTimeVal0, false);
                }else{
                    setMove((byte)1,Intent.BUFF);
                }
                point+=2;
                break;

            case 1:
            case 2:
                if(tmp==0){
                    setMove((byte)2,Intent.ATTACK,
                            this.damage.get(1).base,damageTimeVal1,true);
                }else{
                    setMove((byte)3,Intent.DEBUFF);
                }
                point++;
                break;
            case 3:

                isMultiTarget=true;

                setMove((byte)5,Intent.ATTACK,
                            this.damage.get(2).base,damageTimeVal2,true);
                point-=3;
                break;
        }
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

        mutsumiWarningUi.update();
        if(!soyoMonster.isDeadOrEscaped()){
            soyoWarningUi.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        //if(isMultiTarget||mutsumiWarningUi.damageFrozen){
        if(isMultiTarget){
            mutsumiWarningUi.render(sb);
            if(!soyoMonster.isDeadOrEscaped()){
                soyoWarningUi.render(sb);
            }
        }else{
            if(target==AbstractDungeon.player&&isAttack()){
                mutsumiWarningUi.render(sb);
            }else if(target==soyoMonster&&isAttack()){
                if(!soyoMonster.isDeadOrEscaped()){
                    soyoWarningUi.render(sb);
                }
            }
        }
    }

    public boolean isAttack(){
        if(intent.equals(Intent.ATTACK)||intent.equals(Intent.ATTACK_BUFF)
                ||intent.equals(Intent.ATTACK_DEFEND)||intent.equals(Intent.ATTACK_DEBUFF)){
            return true;
        }
        return false;
    }

    @Override
    public void die() {
        super.die();

        if (this.currentHealth <= 0) {
            if(!soyoMonster.isDeadOrEscaped()){
                soyoMonster.escape();
            }
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            onBossVictoryLogic();
        }
    }

    public static boolean isMutsumi(){
        if(AbstractDungeon.getCurrRoom().phase== AbstractRoom.RoomPhase.COMBAT){
            if(MonsterUtils.getPower(makeID("MutsumiMonster"),makeID("MutsumiRealDamagePower"))!=null){
                return true;
            }
        }
        return false;
    }
}


