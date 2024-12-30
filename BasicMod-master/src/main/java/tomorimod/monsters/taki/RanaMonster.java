package tomorimod.monsters.taki;

import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.monsters.BaseMonster;
import tomorimod.monsters.anon.ChordMonster;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.graphics;
import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;


public class RanaMonster extends BaseMonster {
    public static final String ID = makeID(RanaMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    // 怪物血量
    private static final int HP_MIN = 20;
    private static final int HP_MAX = 20;

    // 怪物的碰撞箱坐标和大小
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath=imagePath("monsters/"+ RanaMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1600.0F;
    public static final float DRAW_Y=400.0F;


    private TakiMonster takiMonster=null;
    private boolean isTakiGet=false;


    public RanaMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);


        setHp(HP_MIN, HP_MAX);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;

        this.damage.add(new DamageInfo(this, 20, DamageInfo.DamageType.NORMAL));

        addToBot(new ApplyPowerAction(this,this,new IntangiblePlayerPower(this,999),999));
        addToBot(new ApplyPowerAction(this,this,new RanaFreeCatPower(this)));
//        this.damage.add(new DamageInfo(this, 12, DamageInfo.DamageType.NORMAL));
//        this.damage.add(new DamageInfo(this, 18, DamageInfo.DamageType.NORMAL));
//        this.damage.add(new DamageInfo(this, 30, DamageInfo.DamageType.NORMAL));
    }

    public void usePreBattleAction() {

//        addToBot(new PlayBGMAction(MusicPatch.MusicHelper.BITIANBANZOU,this));
//        AbstractGameEffect effect = new ChangeSceneEffect(ImageMaster.loadImage(imagePath("monsters/scenes/Anon_bg.png")));
//        AbstractDungeon.effectList.add(effect);
//        AbstractDungeon.scene.fadeOutAmbiance();
    }

    private float timeCounter = 0f;
    private float alphaValue = 1.0f;
    private final float period = 4.0f;
    @Override
    public void update(){
        super.update();

        if(!isTakiGet){
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m instanceof TakiMonster && !m.isDeadOrEscaped()) {
                    TakiMonster taki = (TakiMonster)m;
                    this.takiMonster = taki;
                    isTakiGet = true;
                }
            }
        }
        if(takiMonster != null) {
            if (!takiMonster.isDeadOrEscaped()) {

                timeCounter += graphics.getDeltaTime();
                alphaValue = 0.1f + 0.75f * (float) Math.abs(Math.sin(timeCounter * Math.PI / period));
                this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, alphaValue));

                this.hb.move(this.drawX, this.drawY+HB_H/2*Settings.scale);
                this.hb.height=0;
                //this.hb.width=0.1f;
            } else {
                this.hb.move(this.drawX + this.hb_x + this.animX, this.drawY + this.hb_y + this.hb_h / 2.0F);
                this.hb.height=HB_H*Settings.scale;
                //this.hb.width=HB_W*Settings.scale;

                this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, 1));
            }
            this.hb.update();
            this.updateHealthBar();
        }

    }

//    @Override
//    protected Texture getAttackIntent() {
//        super.getAttackIntent();
//
//
//    }


    @Override
    public void takeTurn() {

        switch (this.nextMove) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
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

//        if (this.currentHealth <= 0) {
//            useFastShakeAnimation(5.0F);
//            CardCrawlGame.screenShake.rumble(4.0F);
//            onBossVictoryLogic();
//        }
    }



}


