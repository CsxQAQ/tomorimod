package tomorimod.monsters.uika;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
import tomorimod.cards.uika.UikaCard;
import tomorimod.cards.uika.UikaMygoTogether;
import tomorimod.cards.uika.UikaStrike;
import tomorimod.monsters.BaseMonster;
import tomorimod.monsters.taki.TakiPressurePower;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;

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
    private AbstractCard cardForShow1;
    private AbstractCard cardForShow2;
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

        this.damage.add(new DamageInfo(this, 5, DamageInfo.DamageType.NORMAL));

        cardForShow1=null;
        cardForShow2=null;
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
                for(int i=0;i<2;i++){
                    addToBot(new DamageAction(AbstractDungeon.player,
                            this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                break;

        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private int turnNum=0;

    @Override
    protected void getMove(int num) {
        if(turnNum==0) {
            setMove((byte) 0, Intent.ATTACK_BUFF);
            // 先创建卡
            cardForShow1 = new UikaMygoTogether();
            cardForShow2 = new UikaStrike();

            // 这里假设怪物头顶要显示的最终坐标是 CARDFORSHOW1_X, CARDFORSHOW_Y
            // 先把它们的目标坐标记录下来
            float endX1 = CARDFORSHOW1_X;
            float endY = CARDFORSHOW_Y;
            float endX2 = CARDFORSHOW2_X;

            // 让它“飞”过去。假设让它从屏幕中央(Settings.WIDTH/2, Settings.HEIGHT/2)飞到怪物头顶
            // 也可以自定义别的起始点，比如怪物自己的“牌堆”位置
            float startX = (float) Settings.WIDTH / 2f;
            float startY = (float) Settings.HEIGHT / 2f;

            // 把动画丢进 effect 列表
            AbstractDungeon.topLevelEffects.add(new MonsterDrawCardEffect(
                    this, // 当前怪物
                    cardForShow1,
                    startX, startY,
                    endX1, endY,
                    0.5f, // 开始时的 scale
                    0.5f,  // 结束时的 scale
                    1
            ));

            // 如果需要第二张卡同样动画，可以再加一个
            AbstractDungeon.topLevelEffects.add(new MonsterDrawCardEffect(
                    this,
                    cardForShow2,
                    startX, startY,
                    endX2, endY,
                    0.5f,
                    0.5f,
                    2
            ));
//            cardForShow1=new UikaMygoTogether();
//            UikaIntentCardPatch.setPosition(cardForShow1,CARDFORSHOW1_X,CARDFORSHOW_Y);
//            UikaIntentCardPatch.AbstractMonsterFieldPatch.intentCard1.set(this,cardForShow1);
//
//            cardForShow2=new UikaStrike();
//            UikaIntentCardPatch.setPosition(cardForShow2,CARDFORSHOW2_X,CARDFORSHOW_Y);
//            UikaIntentCardPatch.AbstractMonsterFieldPatch.intentCard2.set(this,cardForShow2);
        }
//            turnNum++;
//        }else if(turnNum==1){
//            setMove( (byte)0, Intent.ATTACK,
//                    this.damage.get(0).base, 1, false);
//        }
    }

    private void gravityUika(){
        switch (turnNum){
            case 1:

        }
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


