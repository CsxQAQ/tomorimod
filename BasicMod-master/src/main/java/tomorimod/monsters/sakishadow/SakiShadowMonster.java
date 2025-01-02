package tomorimod.monsters.sakishadow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.monsters.mutsumi.*;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;
import tomorimod.vfx.DynamicBackgroundContinueEffect;
import tomorimod.vfx.DynamicBackgroundEffect;
import tomorimod.vfx.DynamicBackgroundTestEffect;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;


public class SakiShadowMonster extends SpecialMonster {
    public static final String ID = makeID(SakiShadowMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    private static final int HP_MIN = 1000;
    private static final int HP_MAX = 1000;

    private static final float HB_X = 0F;
    private static final float HB_Y = 50F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath=imagePath("monsters/"+ SakiShadowMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1400.0F;
    public static final float DRAW_Y=450.0F;
    private SoyoMonster soyoMonster;

    private boolean isFirstTurn;
    private boolean isGiveCurse;

    public SakiShadowMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        setHp(HP_MIN, HP_MAX);


        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=-1000.0f;
        this.drawY=-1000.0f;


        this.damage.add(new DamageInfo(this, 999, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 10, DamageInfo.DamageType.NORMAL));
        isFirstTurn=true;
    }


    @Override
    public void usePreBattleAction() {

        AbstractGameEffect effect = new ChangeSceneEffect
                (ImageMaster.loadImage(imagePath("monsters/scenes/frame_29.png")));
        AbstractDungeon.effectList.add(effect);

        AbstractDungeon.scene.fadeOutAmbiance();

        addToBot(new ApplyPowerAction(this,this,new SakiFearlessPower(this)));
        addToBot(new ApplyPowerAction(this,this,new SakiShadowImmunityPower(this)));

        initializeSoyoMonster();
    }

    public void initializeSoyoMonster(){
        SoyoMonster soyoMonster=new SoyoMonster(0f,0f);
        this.soyoMonster=soyoMonster;
        soyoMonster.drawX=AbstractDungeon.player.drawX+300.0F*Settings.scale;
        soyoMonster.drawY=AbstractDungeon.player.drawY;
        soyoMonster.flipHorizontal=true;

        addToBot(new SpawnMonsterAction(soyoMonster,false));
        addToBot(new ApplyPowerAction(soyoMonster,soyoMonster,new FriendlyMonsterPower(soyoMonster)));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                soyoMonster.setMove((byte) 99, Intent.UNKNOWN);
                soyoMonster.createIntent();
                isDone=true;
            }
        });

    }

    @Override
    public void takeTurn() {
        if(isFirstTurn){

            addToTop(new PlayBGMAction(MusicPatch.MusicHelper.KILLKISS,this));

            addToBot(new DamageAction(soyoMonster,
                    this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

            AbstractDungeon.effectList.add(new DynamicBackgroundTestEffect(0.1f));

            drawX=DRAW_X*Settings.scale;
            drawY=DRAW_Y*Settings.scale;

            AbstractDungeon.player.drawPile.initializeDeck(AbstractDungeon.player.masterDeck);
            isFadingIn=true;
            isFirstTurn=false;
            addToBot(new TrueWaitAction(3.0f));

        }else{
            switch (this.nextMove) {
                case 1:
                    for(int i=0;i<2;i++){
                        addToBot(new DamageAction(AbstractDungeon.player,
                                this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    }
                    break;
                case 99:


            }
        }


        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    // 是否正在执行淡入动画
    private boolean isFadingIn = false;
    // 用于渲染时的透明度(0~1)
    private float alpha = 1.0f;

    // 淡入总时长
    private float fadeInDuration = 4.0f;
    // 淡入剩余时长
    private float fadeInTimer = 4.0f;


    @Override
    public void update() {
        super.update();

        if (isFadingIn) {
            fadeInTimer -= Gdx.graphics.getDeltaTime();
            if (fadeInTimer < 0f) {
                fadeInTimer = 0f;
                isFadingIn = false;
            }

            float progress = 1.0f - (fadeInTimer / fadeInDuration);
            alpha = Interpolation.fade.apply(0f, 1f, progress);
            this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, alpha));
            if (!isFadingIn) {
                alpha = 1.0f;
                this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, 1));

            }
        }
    }


    @Override
    public void render(SpriteBatch sb){
        if(alpha<1.0f){

            sb.setColor(this.tint.color);
            sb.draw(this.img, this.drawX - (float)this.img.getWidth() * Settings.scale / 2.0F + this.animX, this.drawY + this.animY,
                    (float)this.img.getWidth() * Settings.scale, (float)this.img.getHeight() * Settings.scale, 0, 0,
                    this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);
        }else{
            super.render(sb);
        }
    }

    @Override
    protected void getMove(int num) {

        if(isGiveCurse){
            setMove((byte)99,Intent.DEBUFF);
        }else{
            setMove( (byte)1, Intent.ATTACK,
                    this.damage.get(1).base, 2, true);
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


