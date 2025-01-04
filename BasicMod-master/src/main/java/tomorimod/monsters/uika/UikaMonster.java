package tomorimod.monsters.uika;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.cards.customcards.DivergeWorld;
import tomorimod.cards.customcards.LightAndShadow;
import tomorimod.cards.customcards.MygoTogether;
import tomorimod.cards.customcards.NeedAnon;
import tomorimod.cards.forms.DomainExpansion;
import tomorimod.cards.uika.*;
import tomorimod.monsters.BaseMonster;
import tomorimod.monsters.taki.TakiPressurePower;
import tomorimod.patches.MusicPatch;
import tomorimod.powers.GravityPower;
import tomorimod.powers.ShinePower;
import tomorimod.powers.custompowers.DivergeWorldPower;
import tomorimod.powers.custompowers.MygoTogetherPower;
import tomorimod.powers.forms.DomainExpansionPower;
import tomorimod.vfx.ChangeSceneEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;


public class UikaMonster extends BaseMonster {
    public static final String ID = makeID(UikaMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    // 怪物血量
    private static final int HP_MIN = 800;
    private static final int HP_MAX = 800;

    // 怪物的碰撞箱坐标和大小
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    private static final String imgPath=imagePath("monsters/"+ UikaMonster.class.getSimpleName()+".png");

    public static final float DRAW_X=1200.0F;
    public static final float DRAW_Y=400.0F;
    private UikaCard cardForShow1;
    private UikaCard cardForShow2;
    private ArrayList<UikaCard> showCards=new ArrayList<>();
    private AbstractCard hoveredCard;
    private AbstractCard clickStartedCard;

    public static final float CARDFORSHOW1_X=(DRAW_X-100.0f)*Settings.scale;
    public static final float CARDFORSHOW2_X=(DRAW_X+100.0f)*Settings.scale;
    public static final float CARDFORSHOW_Y=(DRAW_Y+400.0F)*Settings.scale;

    public static final float WAIT_TIME=0.3F;

    public UikaMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);


        setHp(HP_MIN, HP_MAX);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;

        this.damage.add(new DamageInfo(this, 6, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public void usePreBattleAction() {

        addToBot(new PlayBGMAction(MusicPatch.MusicHelper.MIXINGJIAO,this));
        AbstractGameEffect effect = new ChangeSceneEffect(ImageMaster.loadImage(imagePath("monsters/scenes/Anon_bg.png")));
        AbstractDungeon.effectList.add(effect);
        AbstractDungeon.scene.fadeOutAmbiance();

        addToBot(new ApplyPowerAction(this,this,new TakiPressurePower(this)));
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                mygoTogether();
                showCardsDiscard(1);
                addToBot(new TrueWaitAction(WAIT_TIME));
                strike();
                showCardsDiscard(2);
                addToBot(new TrueWaitAction(WAIT_TIME));
                break;

            case 11:
                domainExpansion();
                showCardsDiscard(1);
                addToBot(new TrueWaitAction(WAIT_TIME));
                liveForever();
                showCardsDiscard(2);
                addToBot(new TrueWaitAction(WAIT_TIME));

                break;
            case 12:
                lightAndShadow();
                showCardsDiscard(1);
                addToBot(new TrueWaitAction(WAIT_TIME));
                needAnon();
                showCardsDiscard(2);
                addToBot(new TrueWaitAction(WAIT_TIME));

                break;
            case 13:
                divergeWorld();
                showCardsDiscard(1);
                addToBot(new TrueWaitAction(WAIT_TIME));
                strike();
                showCardsDiscard(2);
                addToBot(new TrueWaitAction(WAIT_TIME));

                break;

        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private int turnNum=0;

    public void strike(){
        addToBot(new DamageAction(AbstractDungeon.player,
                this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

    }

    public void mygoTogether(){
        addToBot(new ApplyPowerAction(this,this,new MygoTogetherPower(this)));
        addToBot(new ApplyPowerAction(this,this,
                new GravityPower(this, MygoTogether.MAGIC),MygoTogether.MAGIC));
        addToBot(new ApplyPowerAction(this,this,
                new ShinePower(this,MygoTogether.MAGIC),MygoTogether.MAGIC));
    }

    public void domainExpansion(){
        addToBot(new ApplyPowerAction(this,this,
                new DomainExpansionPower(this, DomainExpansion.MAGIC),DomainExpansion.MAGIC));
    }

    public void liveForever(){
        addToBot(new ApplyPowerAction(this,this,
                new GravityPower(this,UikaLiveForever.MAGIC),UikaLiveForever.MAGIC));
    }

    public void lightAndShadow(){
        int gravityNum=this.hasPower(makeID("GravityPower"))?0:this.getPower(makeID("GravityPower")).amount;
        int shineNum=this.hasPower(makeID("ShinePower"))?0:this.getPower(makeID("ShinePower")).amount;
        if(gravityNum>=shineNum){
            addToBot(new ApplyPowerAction(this,this,new GravityPower(this, LightAndShadow.MAGIC),LightAndShadow.MAGIC));
        }else{
            addToBot(new ApplyPowerAction(this,this,new ShinePower(this,LightAndShadow.MAGIC),LightAndShadow.MAGIC));
        }
    }

    public void needAnon(){
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int gravityNum=UikaMonster.this.hasPower(makeID("GravityPower"))?UikaMonster.this.getPower(makeID("GravityPower")).amount:0;
                if(gravityNum!=0){
                    addToTop(new ApplyPowerAction(UikaMonster.this,UikaMonster.this,new GravityPower
                            (UikaMonster.this,gravityNum* NeedAnon.MAGIC),gravityNum*NeedAnon.MAGIC));
                }
                isDone=true;
            }
        });
    }

    public void divergeWorld(){
        addToBot(new ApplyPowerAction(this,this,
                new DivergeWorldPower(this,1),1));
    }


    @Override
    protected void getMove(int num) {
        if(turnNum==0||turnNum>3) {
            setMove((byte) 0, Intent.ATTACK_BUFF,
                    this.damage.get(0).base, 1, false);
            // 先创建卡
            cardForShow1 = new UikaMygoTogether();
            cardForShow2 = new UikaStrike();
            showCardsDraw();
            turnNum++;
        }else{
            gravityUika();
        }
    }

    private void gravityUika(){
        switch (turnNum){
            case 1:
                setMove((byte) 11, Intent.BUFF);
                // 先创建卡
                cardForShow1 = new UikaDomainExpansion();
                cardForShow2 = new UikaLiveForever();
                break;
            case 2:
                setMove((byte) 12, Intent.BUFF);
                // 先创建卡
                cardForShow1 = new UikaLightAndShadow();
                cardForShow2 = new UikaNeedAnon();

                break;
            case 3:
                setMove((byte) 13, Intent.ATTACK_BUFF,
                        this.damage.get(0).base, 1, false);
                cardForShow1 =new UikaDivergeWorld();
                cardForShow2 =new UikaStrike();
                break;
        }
        showCardsDraw();
        turnNum++;
    }

    public void showCardsDiscard(int pos){
        //ArrayList<UikaCard> tmp=new ArrayList<>(showCards);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(pos==1){
                    cardForShow1.untip();
                    cardForShow1.unhover();
                    cardForShow1.darken(true);
                    cardForShow1.shrink(true);

                    // 创建新的 Soul
                    Soul soul = new Soul();
                    soul.discard(cardForShow1, false);
                    SoulGroup soulGroup = AbstractDungeon.getCurrRoom().souls;
                    try {
                        Field soulsField = SoulGroup.class.getDeclaredField("souls");
                        soulsField.setAccessible(true); // 绕过访问限制
                        ArrayList<Soul> souls = (ArrayList<Soul>) soulsField.get(soulGroup);
                        souls.add(soul); // 修改字段
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }else if(pos==2){
                    cardForShow2.untip();
                    cardForShow2.unhover();
                    cardForShow2.darken(true);
                    cardForShow2.shrink(true);

                    // 创建新的 Soul
                    Soul soul = new Soul();
                    soul.discard(cardForShow2, false);
                    SoulGroup soulGroup = AbstractDungeon.getCurrRoom().souls;
                    try {
                        Field soulsField = SoulGroup.class.getDeclaredField("souls");
                        soulsField.setAccessible(true); // 绕过访问限制
                        ArrayList<Soul> souls = (ArrayList<Soul>) soulsField.get(soulGroup);
                        souls.add(soul); // 修改字段
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                isDone=true;
            }
        });
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
    }

    private void updateCard(){
        this.hoveredCard = null;
        if(cardForShow1!=null){
            cardForShow1.update();
            cardForShow1.updateHoverLogic();
            if(cardForShow1.hb.hovered){
                this.hoveredCard=cardForShow1;
            }
        }
        if(cardForShow2!=null){
            cardForShow2.update();
            cardForShow2.updateHoverLogic();
            if(cardForShow2.hb.hovered){
                this.hoveredCard=cardForShow2;
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
    public void die() {
        super.die();

        if (this.currentHealth <= 0) {
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            onBossVictoryLogic();
        }
    }
}


