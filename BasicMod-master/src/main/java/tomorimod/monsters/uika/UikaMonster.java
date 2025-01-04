package tomorimod.monsters.uika;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
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
import tomorimod.cards.uika.*;
import tomorimod.monsters.BaseMonster;
import tomorimod.monsters.taki.TakiPressurePower;
import tomorimod.patches.MusicPatch;
import tomorimod.powers.GravityPower;
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


    public UikaMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);


        setHp(HP_MIN, HP_MAX);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;

        this.damage.add(new DamageInfo(this, 6, DamageInfo.DamageType.NORMAL));

//        cardForShow1=null;
//        cardForShow2=null;
//        showCards.add(cardForShow1);
//        showCards.add(cardForShow2);
        //UikaIntentCardPatch.setPosition(cardForShow,drawX,drawY+300*Settings.scale);
        //UikaIntentCardPatch.AbstractMonsterFieldPatch.intentCard.set(this,cardForShow);
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
                addToBot(new DamageAction(AbstractDungeon.player,
                        this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                showCardsDiscard();

                break;


            case 11:
                addToBot(new ApplyPowerAction(this,this,
                        new DomainExpansionPower(this,3),3));
                addToBot(new ApplyPowerAction(this,this,
                        new GravityPower(this,UikaLiveForever.MAGIC),UikaLiveForever.MAGIC));
                showCardsDiscard();

                break;

        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private int turnNum=0;

    @Override
    protected void getMove(int num) {
        if(turnNum==0) {
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
                showCardsDraw();
                break;
        }
        turnNum=0;
    }

    public void showCardsDiscard(){
        //ArrayList<UikaCard> tmp=new ArrayList<>(showCards);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for(UikaCard uikaCard:showCards){
                    uikaCard.untip();
                    uikaCard.unhover();
                    uikaCard.darken(true);
                    uikaCard.shrink(true);

                    // 创建新的 Soul
                    Soul soul = new Soul();
                    soul.discard(uikaCard, false);
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


