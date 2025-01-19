package tomorimod.monsters.uika;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.cards.basic.Strike;
import tomorimod.cards.uikacard.*;
import tomorimod.monsters.BaseMonster;
import tomorimod.patches.MusicPatch;
import tomorimod.util.MonsterUtils;
import tomorimod.vfx.ChangeSceneEffect;

import java.util.ArrayList;
import java.util.Arrays;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;


public class UikaMonster extends BaseMonster {

    public static final String ID = makeID(UikaMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    // 怪物的碰撞箱坐标和大小
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath=imagePath("monsters/"+ UikaMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1350.0F;
    public static final float DRAW_Y=400.0F;

    private ArrayList<UikaCard> showCards=new ArrayList<>();
    private AbstractCard hoveredCard;
    private AbstractCard clickStartedCard;

    public UikaCard cardForShow1;
    public UikaCard cardForShow2;

    public static final float CARDFORSHOW1_X=(DRAW_X-100.0f)*Settings.scale;
    public static final float CARDFORSHOW2_X=(DRAW_X+100.0f)*Settings.scale;
    public static final float CARDFORSHOW_Y=(DRAW_Y+400.0F)*Settings.scale;

    public static final float WAIT_TIME=0.3F;

    //public static boolean damageNumFroze=false;

    private boolean isGravityMode;
    private boolean isDomainExpansionUsed=false;
    public static boolean isTwoMoon=false;

    public static SoulGroup goldenSouls=new SoulGroup();

    public UikaWarningUi uikaWarningUi;

    private int turnNum=0;

    // 怪物血量
    public static final int HP_MIN = 300;
    public static final int HP_MAX = 300;
    public static final int MASK_NUM = 30;

    public static final int HP_MIN_WEAK = 300;
    public static final int HP_MAX_WEAK = 300;
    public static final int MASK_NUM_WEAK = 75;

    private int hpMinVal;
    private int hpMaxVal;
    private int maskVal;

    public UikaMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        if(isHardMode){
            this.hpMinVal = HP_MIN;
            this.hpMaxVal = HP_MAX;
            this.maskVal=MASK_NUM;
        }else{
            this.hpMinVal = HP_MIN_WEAK;
            this.hpMaxVal = HP_MAX_WEAK;
            this.maskVal=MASK_NUM_WEAK;
        }


        setHp(hpMinVal, hpMaxVal);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;

        uikaWarningUi=new UikaWarningUi(this);

        this.damage.add(new DamageInfo(this, 6, DamageInfo.DamageType.NORMAL));

    }

    @Override
    public void usePreBattleAction() {

        addToBot(new PlayBGMAction(MusicPatch.MusicHelper.AVEMUJICA,this));
        AbstractGameEffect effect = new ChangeSceneEffect(ImageMaster.loadImage(imagePath("monsters/scenes/Uika_bg.png")));
        AbstractDungeon.effectList.add(effect);
        AbstractDungeon.scene.fadeOutAmbiance();

        addToBot(new ApplyPowerAction(this,this,
               new UikaMaskPower(this,maskVal),maskVal));
    }

    @Override
    public void takeTurn() {
        cardForShow1.uikaUse(this);
        cardForShow2.uikaUse(this);

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        if (turnNum == 0) {


            if(isHardMode){
                setMove((byte) 0, Intent.BUFF);
                cardForShow1 = new UikaMygoTogether();
                cardForShow2=new UikaConveyFeeling();
            }else{
                setMove((byte) 0, Intent.ATTACK_BUFF,
                        Strike.DAMAGE, 1, false);
//                setMove((byte) 0, Intent.ATTACK_BUFF,
//                        this.damage.get(0).base, 1, false);
                cardForShow1 = new UikaMygoTogether();
                cardForShow2=new UikaStrike();
            }

            int rand = AbstractDungeon.miscRng.random(1);
            isGravityMode = (rand == 0);
            turnNum++;
        } else if (getDebuffNum() >= 10) {
            setMove((byte) 99, Intent.BUFF);
            cardForShow1 = new UikaPoemInsteadOfSong();
            cardForShow2 = new UikaLightAndShadow();
        } else if (turnNum == 4) {
            setMove((byte) 50, Intent.BUFF);
            cardForShow1 = new UikaLightAndShadow();
            cardForShow2 = new UikaLastGentle();
            isGravityMode = !isGravityMode;
            turnNum = 1;
        } else {
            if (isGravityMode) {
                gravityUika();
            } else {
                shineUika();
            }
        }
        if(isHardMode){
            cardForShow1.upgrade();
            cardForShow2.upgrade();
        }
        showCardsDraw();
    }

    private void gravityUika(){
        switch (turnNum){
            case 1:
                if(!isDomainExpansionUsed){
                    setMove((byte) 11, Intent.BUFF);
                    // 先创建卡
                    cardForShow1 = new UikaGravityDomain();
                    cardForShow2 = new UikaLiveForever();
                    isDomainExpansionUsed=true;
                }else{
                    cardForShow1 = new UikaStrike();
                    if(isHardMode){
                        setMove((byte) 11, Intent.ATTACK_BUFF,
                                Strike.DAMAGE+Strike.UPG_DAMAGE, 1, false);
                    }else{
                        setMove((byte) 11, Intent.ATTACK_BUFF,
                                Strike.DAMAGE, 1, false);
                    }
                    cardForShow2 = new UikaLiveForever();
                }

                break;
            case 2:
                setMove((byte) 12, Intent.BUFF);
                // 先创建卡
                cardForShow1 = new UikaLightAndShadow();
                cardForShow2 = new UikaNeedAnon();

                break;
            case 3:
//                setMove((byte) 13, Intent.ATTACK_BUFF,
//                        this.damage.get(0).base, 1, false);
                if(isHardMode){
                    setMove((byte) 13, Intent.ATTACK_BUFF,
                            Strike.DAMAGE+Strike.UPG_DAMAGE, 1, false);
                }else{
                    setMove((byte) 13, Intent.ATTACK_BUFF,
                            Strike.DAMAGE, 1, false);
                }
                cardForShow1 =new UikaDivergeWorld();
                cardForShow2 =new UikaStrike();

                break;
        }
        turnNum++;
    }

    private void shineUika(){
        switch (turnNum){
            case 1:
                setMove((byte) 21, Intent.DEFEND_BUFF);
                // 先创建卡
                cardForShow1 = new UikaLastOne();
                cardForShow2 = new UikaTwoPeopleOneBody();
                break;
            case 2:
                int shineAmount= MonsterUtils.getPowerNum(this,makeID("ShinePower"));

                if(isHardMode){
                    setMove((byte) 22, Intent.ATTACK_DEFEND,
                            shineAmount*(UikaTwoMoon.MAGIC+UikaTwoMoon.UPG_MAGIC), 1, false);
                }else{
                    setMove((byte) 22, Intent.ATTACK_DEFEND,
                            shineAmount*UikaTwoMoon.MAGIC, 1, false);
                }

                cardForShow1 = new UikaTwoMoon();
                cardForShow2 = new UikaLightAndShadow();
                isTwoMoon=true;


                break;
            case 3:
                setMove((byte) 23, Intent.BUFF);
                cardForShow1 =new UikaDoughnut();
                cardForShow2 = new UikaDefend();
                break;
        }
        turnNum++;
    }

    public int getDebuffNum(){
        int amount=0;
        for(AbstractPower power:this.powers){
            if(power.type== AbstractPower.PowerType.DEBUFF){
                amount+=power.amount;
            }
        }
        return amount;
    }

    public void showCardsDraw(){
        showCards.clear();
        showCards.add(cardForShow1);
        showCards.add(cardForShow2);

        float endX1 = CARDFORSHOW1_X;
        float endY = CARDFORSHOW_Y;
        float endX2 = CARDFORSHOW2_X;

        float startX1 = endX1-50.0f*Settings.scale;
        float startX2 = endX2-50.0f*Settings.scale;
        float startY = endY+50.0f*Settings.scale;

        AbstractDungeon.topLevelEffects.add(new UikaDrawCardEffect(
                this, // 当前怪物
                cardForShow1,
                startX1, startY,
                endX1, endY,
                0.5f, // 开始时的 scale
                0.5f,  // 结束时的 scale
                0.5f,1
        ));

        AbstractDungeon.topLevelEffects.add(new UikaDrawCardEffect(
                this, // 当前怪物
                cardForShow2,
                startX2, startY,
                endX2, endY,
                0.5f, // 开始时的 scale
                0.5f,  // 结束时的 scale
                0.5f,2
        ));
    }


    @Override
    public void update(){
        super.update();
        updateCard();
        updateInputLogic();

        uikaWarningUi.update();

        goldenSouls.update();
    }

    private void updateCard() {
        this.hoveredCard = null;
        for (UikaCard c : Arrays.asList(cardForShow1, cardForShow2)) {
            if (c != null) {
                c.update();
                c.updateHoverLogic();
                if (c.hb.hovered) {
                    this.hoveredCard = c;
                }
            }
        }
    }

    public void updateInputLogic(){

        if (this.hoveredCard != null) {
            CardCrawlGame.cursor.changeType(GameCursor.CursorType.INSPECT);
            if (InputHelper.justClickedLeft) {
                this.clickStartedCard = this.hoveredCard;
            }

            if (InputHelper.justReleasedClickLeft && this.clickStartedCard != null && this.hoveredCard != null || this.hoveredCard != null && CInputActionSet.select.isJustPressed()) {
                if (Settings.isControllerMode) {
                    this.clickStartedCard = this.hoveredCard;
                }

                InputHelper.justReleasedClickLeft = false;
                CardCrawlGame.cardPopup.open(this.clickStartedCard);
                this.clickStartedCard = null;
            }
        } else {
            this.clickStartedCard = null;
        }
    }



    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        uikaWarningUi.render(sb);
    }



    @Override
    protected Texture getAttackIntent() {
        if(isTwoMoon){
            return new Texture(imagePath("monsters/intents/attack_singer_heavy.png"));
        }else{
            return new Texture(imagePath("monsters/intents/attack_singer_normal.png"));
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


