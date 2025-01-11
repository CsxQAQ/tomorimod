package tomorimod.monsters.mutsumi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.cards.uikacard.UikaCard;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;

import java.lang.reflect.Field;
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


    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath=imagePath("monsters/"+ MutsumiMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1000.0F;
    public static final float DRAW_Y=450.0F;
    private SoyoMonster soyoMonster;

    private int point=0;

    private Hitbox attackIntentHb;
    private Hitbox damageNumberHb;
    private int damageForShow;
    private int damageNum;


    public static final int HP_MIN       = 6000;
    public static final int HP_MAX       = 6000;
    public static final int DAMAGE_0     = 6;
    public static final int DAMAGE_1     = 12;
    public static final int DAMAGE_2     = 15;
    public static final int DAMAGETIME_0 = 1;
    public static final int DAMAGETIME_1 = 1;
    public static final int DAMAGETIME_2 = 3;
    public static final int CUCUMBER=20;
    public static final int CUCUMBER_UPG=3;
    public static final int HEALNUM=50;
    public static final int STRENGTHNUM=2;
    public static final int POWERNUM=2;

    public static final int HP_MIN_WEAK       = 2000;
    public static final int HP_MAX_WEAK       = 2000;
    public static final int DAMAGE_0_WEAK     = 6;
    public static final int DAMAGE_1_WEAK     = 12;
    public static final int DAMAGE_2_WEAK     = 15;
    public static final int DAMAGETIME_0_WEAK = 1;
    public static final int DAMAGETIME_1_WEAK = 1;
    public static final int DAMAGETIME_2_WEAK = 3;
    public static final int CUCUMBER_WEAK=10;
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

        if(isTomori){
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


        this.damage.add(new DamageInfo(this, damageVal0, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, damageVal1, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, damageVal2, DamageInfo.DamageType.NORMAL));
        this.target=soyoMonster;

        // 攻击意图图标的大小
        this.attackIntentHb = new Hitbox(128.0f * Settings.scale, 128.0f * Settings.scale);

        // 伤害数字的大小(这里随意示例了一个 64x32 的范围)
        this.damageNumberHb = new Hitbox(64.0f * Settings.scale, 32.0f * Settings.scale);
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
        addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new BehindAttackPower(AbstractDungeon.player)));
        AbstractDungeon.player.drawY=DRAW_Y*Settings.scale;
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
                for (int i = 0; i < 3; i++) {
                    addToBot(new VFXAction(this, new ShockWaveEffect(
                            this.hb.cX, this.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.75F));

                    addToBot(new MonsterDamageAllAction(this,this.damage.get(2),AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    isMultiTarget=false;
                }
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
                DamageInfo newInfo=new DamageInfo(this,this.damage.get(index).base);
                newInfo.applyPowers(this,soyoMonster);
                addToBot(new DamageAction(target,new DamageInfo(this,newInfo.output)));
            }
        }
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

        damageForShow=calculateDamageSingle(getPublicField(this, "intentBaseDmg", Integer.class),AbstractDungeon.player);
        damageNum=getPublicField(this, "intentMultiAmt", Integer.class);

        // 先确定图标在屏幕上的坐标
        float iconX = AbstractDungeon.player.hb.cX - 96.0f * Settings.scale;
        float iconY = AbstractDungeon.player.hb.cY + 320.0f * Settings.scale;

        // 把 attackIntentHb 的中心移到图标正中央
        // 注意要加上宽/高的一半，以使其对准图标中心
        attackIntentHb.move(iconX + (128.0f * Settings.scale) / 2f,
                iconY + (128.0f * Settings.scale) / 2f);
        attackIntentHb.update(); // 必须调用，才能检测鼠标悬浮

        // 再确定伤害数字的坐标
        float textX = AbstractDungeon.player.hb.cX - 32.0f * Settings.scale;
        float textY = AbstractDungeon.player.hb.cY + 340.0f * Settings.scale;

        // 让 damageNumberHb 跟随伤害数字区域
        damageNumberHb.move(textX + (64.0f * Settings.scale) / 2f,
                textY + (32.0f * Settings.scale) / 2f);
        damageNumberHb.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        if(isMultiTarget){
            renderAttackIntent(sb);
            renderDamageNumber(sb);
            if (this.attackIntentHb.hovered||this.damageNumberHb.hovered) {
                // 这类方法可以渲染一个简单的提示
                // 参数：Tip的左下角X, Tip的左下角Y, 标题, 内容
                TipHelper.renderGenericTip(
                        InputHelper.mX + 50.0F * Settings.scale,  // Tip往右下方一点
                        InputHelper.mY - 50.0F * Settings.scale,
                        "预警",
                        "敌人将使用群体攻击对你造成 #b" + damageForShow+ " 点伤害 #b"+damageNum+" 次。"
                );
            }
        }
    }

    private void renderAttackIntent(SpriteBatch sb) {
        if (damageForShow <= 0) {
            return;
        }

        // 拿到对应的纹理
        Texture intentTex = getAttackIntent(damageForShow);
        if (intentTex == null) {
            return;
        }

        // 设置渲染颜色和位置
        // 注意：玩家的 Hitbox 中心是 hb.cX, hb.cY，可以按需求微调
        sb.setColor(Color.WHITE.cpy());
        float iconX = AbstractDungeon.player.hb.cX - 96.0f*Settings.scale;  // 让图标居中
        float iconY = AbstractDungeon.player.hb.cY + 320.0f*Settings.scale;  // 高度可以根据需求调整

        // 画图标（64 x 64 大小）
        sb.draw(intentTex, iconX, iconY, 128.0f*Settings.scale, 128.0f*Settings.scale);
    }

    private void renderDamageNumber(SpriteBatch sb) {
        if (damageForShow <= 0) {
            return;
        }

        float textX = AbstractDungeon.player.hb.cX-32.0f*Settings.scale;
        float textY = AbstractDungeon.player.hb.cY + 340.0f*Settings.scale;

        // 用红色来渲染伤害数字
        FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N,
                damageForShow+"x"+damageNum, textX, textY, Color.WHITE);
    }


    public static <T> T getPublicField(Object instance, String fieldName, Class<T> fieldType) {
        try {
            Field field = AbstractMonster.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return fieldType.cast(field.get(instance));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
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
}


