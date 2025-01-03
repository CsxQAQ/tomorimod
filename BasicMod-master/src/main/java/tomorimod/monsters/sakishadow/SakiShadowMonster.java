package tomorimod.monsters.sakishadow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.cards.special.FearlessDeath;
import tomorimod.cards.special.FearlessFear;
import tomorimod.cards.special.FearlessLove;
import tomorimod.cards.special.FearlessSad;
import tomorimod.monsters.mutsumi.*;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;
import tomorimod.vfx.DynamicBackgroundContinueEffect;
import tomorimod.vfx.DynamicBackgroundEffect;
import tomorimod.vfx.DynamicBackgroundTestEffect;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;


public class SakiShadowMonster extends SpecialMonster {
    public static final String ID = makeID(SakiShadowMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    private static final int HP_MIN = 200;
    private static final int HP_MAX = 200;

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
    private boolean isSecondPhase=false;

    List<AbstractCard> cards = Arrays.asList(
            new FearlessDeath(),
            new FearlessFear(),
            new FearlessLove(),
            new FearlessSad()
    );

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
        this.damage.add(new DamageInfo(this, 50, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 2, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 40, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, 15, DamageInfo.DamageType.NORMAL));
        isFirstTurn=true;
        isGiveCurse=true;
    }


    @Override
    public void usePreBattleAction() {

        AbstractGameEffect effect = new ChangeSceneEffect
                (ImageMaster.loadImage(imagePath("monsters/scenes/frame_29.png")));
        AbstractDungeon.effectList.add(effect);

        AbstractDungeon.scene.fadeOutAmbiance();
        //addToBot(new ApplyPowerAction(this,this,new SakiFearlessPower(this)));
        addToBot(new ApplyPowerAction(this,this,new SakiShadowImmunityPower(this)));
        addToBot(new ApplyPowerAction(this,this,new SakiWorldViewPower(this)));

        initializeSoyoMonster();

        (AbstractDungeon.getCurrRoom()).cannotLose = true;

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
                case 2:
                    addToBot(new ApplyPowerAction(this,this,new SakiRightPower(this,5),5));
                    break;
                case 3:
                    addToBot(new ShowCardAndObtainAction(cards.get(AbstractDungeon.miscRng.random(3)).
                            makeStatEquivalentCopy(),Settings.WIDTH/2,Settings.HEIGHT/2));
                    break;
                case 4:
                    for(int i=0;i<5;i++){
                        addToBot(new DamageAction(AbstractDungeon.player,
                                this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    }
                    break;
                case 5:
                    addToBot(new DamageAction(AbstractDungeon.player,
                            this.damage.get(4), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    addToBot(new ApplyPowerAction(this,this,new SakiRightPower(this,3),3));
                    break;
                case 6:
                    for(int i=0;i<2;i++){
                        addToBot(new DamageAction(AbstractDungeon.player,
                                this.damage.get(5), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    }
                    addToBot(new ShowCardAndObtainAction(cards.get(AbstractDungeon.miscRng.random(3)).
                            makeStatEquivalentCopy(),Settings.WIDTH/2,Settings.HEIGHT/2));
                    break;
                case 50:

                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new CanLoseAction());
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            SakiShadowMonster.this.halfDead = false;
                            SakiShadowMonster.this.isDead = false;
                            SakiShadowMonster.this.isDying = false;
                            // 让房间可以真正结束战斗，而不是不能输
                            AbstractDungeon.getCurrRoom().cannotLose = false;
                            this.isDone = true;
                        }
                    });
                    setHp(400);
                    addToBot(new DamageAction(AbstractDungeon.player,
                            this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)
                            new HealAction((AbstractCreature)this, (AbstractCreature)this, this.maxHealth));
                    drawX=DRAW_X*Settings.scale;
                    drawY=DRAW_Y*Settings.scale;
                    alpha = 1.0f;
                    this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, 1));
                    isSecondPhase=true;
                    break;
                case 99:

                    float startX = (float) Settings.WIDTH / 5.0F;
                    float startY = (float) Settings.HEIGHT / 2.0F;
                    float spacing = (float) Settings.WIDTH / 5.0F;
                    float delay = 0.5F;

                    for (int i = 0; i < cards.size(); i++) {
                        addToBot(new ShowCardAndObtainAction(cards.get(i).makeStatEquivalentCopy(),startX+i*spacing,startY));
                        addToBot(new TrueWaitAction(delay));
                    }
                    isGiveCurse=false;
                    break;
            }
        }


        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        if(!isFirstTurn){
            if(isGiveCurse){
                setMove((byte)99,Intent.DEBUFF);
            }else{
                if(!isSecondPhase){
                    if(curNum==-1){
                        setMove( (byte)1, Intent.ATTACK,
                                this.damage.get(1).base, 2, true);
                        curNum=0;
                    }else{
                        switch (getDifferentNum(curNum)){
                            case 0:
                                setMove( (byte)1, Intent.ATTACK,
                                        this.damage.get(1).base, 2, true);
                                break;
                            case 1:
                                setMove((byte)2,Intent.BUFF);
                                break;
                            case 2:
                                setMove((byte)3,Intent.DEBUFF);
                                break;
                        }
                    }
                }else{
                    switch (getDifferentNum(curNum)){
                        case 0:
                            setMove( (byte)4, Intent.ATTACK,
                                    this.damage.get(3).base, 5, true);
                            break;
                        case 1:
                            setMove( (byte)5, Intent.ATTACK_BUFF,
                                    this.damage.get(4).base, 1, false);
                            break;
                        case 2:
                            setMove( (byte)5, Intent.ATTACK_DEBUFF,
                                    this.damage.get(5).base, 2, true);
                            break;
                    }
                }

            }
        }else{
            setMove( (byte)0, Intent.ATTACK,
                    this.damage.get(0).base, 1, false);
        }

    }

    private int curNum=-1;

    public int getDifferentNum(int num){
        int newNum=AbstractDungeon.miscRng.random(2);
        while(newNum==num){
            newNum=AbstractDungeon.miscRng.random(2);
        }
        curNum=newNum;
        return newNum;
    }

    private float alpha = 1.0f;

    private boolean isFadingIn = false;
    private float fadeInDuration = 4.0f;
    private float fadeInTimer = 4.0f;

    private boolean isRebirth = false;
    private float fadeOutDuration = 0.5f;
    private float fadeOutTimer = 0.5f;

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

        if(isRebirth){
            fadeOutTimer -= Gdx.graphics.getDeltaTime();
            if (fadeOutTimer < 0f) {
                fadeOutTimer = 0f;
                isRebirth = false;
            }

            alpha = fadeOutTimer / fadeOutDuration;
            this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, alpha));
            if (!isRebirth) {
                this.drawX=-1000.0f;
                this.drawY=-1000.0f;
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
    public void damage(DamageInfo info) {
        super.damage(info);

        if (this.currentHealth <= 0 && !this.halfDead) {
            if ((AbstractDungeon.getCurrRoom()).cannotLose == true) {
                this.halfDead = true;
                isRebirth=true;
            }
            for (AbstractPower p : this.powers) {
                p.onDeath();
            }
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onMonsterDeath(this);
            }
            addToTop(new ClearCardQueueAction());

            for (Iterator<AbstractPower> s = this.powers.iterator(); s.hasNext(); ) {
                AbstractPower p = s.next();
                if (p.type == AbstractPower.PowerType.DEBUFF || p.ID.equals("Curiosity") || p.ID.equals("Unawakened") || p.ID.equals("Shackled")) {
                    s.remove();
                }
            }

            setMove( (byte)50, Intent.ATTACK_BUFF,
                    this.damage.get(2).base, 1, false);
            createIntent();
            applyPowers();
        }
    }

    @Override
    public void die() {
        if(!AbstractDungeon.getCurrRoom().cannotLose){
            super.die();

            if (this.currentHealth <= 0) {
                useFastShakeAnimation(5.0F);
                CardCrawlGame.screenShake.rumble(4.0F);
                onBossVictoryLogic();
            }
        }
    }
}


