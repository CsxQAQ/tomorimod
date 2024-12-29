package tomorimod.monsters.taki;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
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
import tomorimod.monsters.anon.*;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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
    private static final int HP_MIN = 40;
    private static final int HP_MAX = 40;

    // 怪物的碰撞箱坐标和大小
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath=imagePath("monsters/"+ TakiMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1200.0F;
    public static final float DRAW_Y=400.0F;



    public TakiMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);


        setHp(HP_MIN, HP_MAX);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;

        this.damage.add(new DamageInfo(this, 5, DamageInfo.DamageType.NORMAL));
//        this.damage.add(new DamageInfo(this, 12, DamageInfo.DamageType.NORMAL));
//        this.damage.add(new DamageInfo(this, 18, DamageInfo.DamageType.NORMAL));
//        this.damage.add(new DamageInfo(this, 30, DamageInfo.DamageType.NORMAL));
    }

    public void usePreBattleAction() {
        addToBot(new SpawnMonsterAction(new RanaMonster(0f,0f),false));

        addToBot(new PlayBGMAction(MusicPatch.MusicHelper.BITIANBANZOU,this));
        AbstractGameEffect effect = new ChangeSceneEffect(ImageMaster.loadImage(imagePath("monsters/scenes/Anon_bg.png")));
        AbstractDungeon.effectList.add(effect);
        AbstractDungeon.scene.fadeOutAmbiance();

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


