package tomorimod.monsters.uika;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.omg.CORBA.PUBLIC_MEMBER;
import tomorimod.actions.ApplyShineAction;
import tomorimod.actions.PlayBGMAction;
import tomorimod.cards.customcards.LightAndShadow;
import tomorimod.cards.customcards.MygoTogether;
import tomorimod.cards.customcards.NeedAnon;
import tomorimod.cards.forms.DomainExpansion;
import tomorimod.monsters.uika.uikacard.*;
import tomorimod.monsters.BaseMonster;
import tomorimod.monsters.taki.TakiPressurePower;
import tomorimod.patches.MusicPatch;
import tomorimod.powers.GravityPower;
import tomorimod.powers.ShinePower;
import tomorimod.powers.custompowers.ConveyFeelingPower;
import tomorimod.powers.custompowers.DivergeWorldPower;
import tomorimod.powers.custompowers.MygoTogetherPower;
import tomorimod.powers.forms.DomainExpansionPower;
import tomorimod.vfx.ChangeSceneEffect;

import java.lang.reflect.Field;
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

    public static boolean damageNumFroze=false;

    private Hitbox attackIntentHb;

    private Hitbox damageNumberHb;

    private boolean isGravityMode;

    public UikaMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);


        setHp(HP_MIN, HP_MAX);

        this.type = EnemyType.BOSS;

        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        this.drawX=DRAW_X*Settings.scale;
        this.drawY=DRAW_Y*Settings.scale;

        this.damage.add(new DamageInfo(this, 6, DamageInfo.DamageType.NORMAL));

        // 攻击意图图标的大小
        this.attackIntentHb = new Hitbox(128.0f * Settings.scale, 128.0f * Settings.scale);

        // 伤害数字的大小(这里随意示例了一个 64x32 的范围)
        this.damageNumberHb = new Hitbox(64.0f * Settings.scale, 32.0f * Settings.scale);
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
        cardForShow1.uikaUse(this);
        cardForShow2.uikaUse(this);

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
            int rand=AbstractDungeon.miscRng.random(1);
            isGravityMode= rand == 0;
            turnNum++;
        }else{
            if(getDebuffNum()>=10){
                setMove((byte) 99, Intent.BUFF);
                // 先创建卡
                cardForShow1 = new UikaPoemInsteadOfSong();
                cardForShow2 = new UikaLightAndShadow();
            }else{
                if(turnNum==4){
                    setMove((byte) 50, Intent.BUFF);
                    // 先创建卡
                    cardForShow1 = new UikaLightAndShadow();
                    cardForShow2 = new UikaLastGentle();
                    isGravityMode=!isGravityMode;
                    turnNum=1;
                }else{
                    if(isGravityMode){
                        gravityUika();
                    }else{
                        shineUika();
                    }
                }
            }
        }
        showCardsDraw();
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
        turnNum++;
    }

    private void shineUika(){
        switch (turnNum){
            case 1:
                setMove((byte) 21, Intent.DEFEND_BUFF);
                // 先创建卡
                cardForShow1 = new UikaLastOne();
                cardForShow2 = new UikaManaGuard();
                break;
            case 2:
                int shineAmount=UikaMonster.this.hasPower(makeID("ShinePower"))?UikaMonster.this.getPower(makeID("ShinePower")).amount:0;

                setMove((byte) 22, Intent.ATTACK_DEFEND,
                        shineAmount*UikaTwoMoon.MAGIC, 1, false);
                cardForShow1 = new UikaTwoMoon();
                cardForShow2 = new UikaLightAndShadow();

                break;
            case 3:
                setMove((byte) 23, Intent.BUFF);
                cardForShow1 =new UikaDoughnut();
                cardForShow2 =new UikaDefend();
                break;
        }
        turnNum++;
    }

    private int getDebuffNum(){
        int amount=0;
        for(AbstractPower power:this.powers){
            if(power.type== AbstractPower.PowerType.DEBUFF){
                amount+=power.amount;
            }
        }
        return amount;
    }

    public void showCardsDiscard(UikaCard card){
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                card.untip();
                card.unhover();
                card.darken(true);
                card.shrink(true);

                // 创建新的 Soul
                Soul soul = new Soul();
                soul.discard(card, false);
                SoulGroup soulGroup = AbstractDungeon.getCurrRoom().souls;
                try {
                    Field soulsField = SoulGroup.class.getDeclaredField("souls");
                    soulsField.setAccessible(true); // 绕过访问限制
                    ArrayList<Soul> souls = (ArrayList<Soul>) soulsField.get(soulGroup);
                    souls.add(soul); // 修改字段
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
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

    private int damageForShow;
    private int gravityDamageForShow;

    @Override
    public void update(){
        super.update();
        updateCard();
        updateInputLogic();
        if(!damageNumFroze){
            damageForShow =calculate().get(0);
            gravityDamageForShow =calculate().get(1);
        }

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

    public ArrayList<Integer> calculate(){
        int damageNum=0;
        int gravityDamageNum=0;
        int monsterDamage = getPublicField(this, "intentDmg", Integer.class);
        int attackCount = getPublicField(this, "intentMultiAmt", Integer.class);
        if(attackCount==-1){
            attackCount=1;
        }
        int gravityAmount=this.hasPower(makeID("GravityPower"))?this.getPower(makeID("GravityPower")).amount:0;
        int divergeWorldAmount=this.hasPower(makeID("DivergeWorldPower"))?this.getPower(makeID("DivergeWorldPower")).amount:0;
        int shineAmount=this.hasPower(makeID("ShinePower"))?this.getPower(makeID("ShinePower")).amount:0;

        if(cardForShow1!=null){
            if(cardForShow1.cardID.equals(makeID("UikaMygoTogether"))){
                gravityAmount++;
            }else if(cardForShow1.cardID.equals(makeID("UikaLiveForever"))){
                gravityAmount+=UikaLiveForever.MAGIC;
            }else if(cardForShow1.cardID.equals(makeID("UikaLightAndShadow"))){
                if(gravityAmount>=shineAmount){
                    gravityAmount+=LightAndShadow.MAGIC;
                }else{
                    shineAmount+=LightAndShadow.MAGIC;
                }
            }else if(cardForShow1.cardID.equals(makeID("UikaNeedAnon"))){
                gravityAmount+=gravityAmount*NeedAnon.MAGIC;
            }else if(cardForShow1.cardID.equals(makeID("UikaDivergeWorld"))){
                divergeWorldAmount++;
            }else if(cardForShow1.cardID.equals(makeID("UikaLastGendle"))){
                int tmp=gravityAmount;
                gravityAmount=shineAmount;
                shineAmount=tmp;
            }else if(cardForShow1.cardID.equals(makeID("UikaStrike"))){
                damageNum+=monsterDamage;
                damageNum+=divergeWorldAmount*gravityAmount;
                gravityDamageNum+=divergeWorldAmount*gravityAmount;
            }
        }

        if(cardForShow2!=null){
            if(cardForShow2.cardID.equals(makeID("UikaMygoTogether"))){
                gravityAmount++;
            }else if(cardForShow2.cardID.equals(makeID("UikaLiveForever"))){
                gravityAmount+=UikaLiveForever.MAGIC;
            }else if(cardForShow2.cardID.equals(makeID("UikaLightAndShadow"))){
                if(gravityAmount>=shineAmount){
                    gravityAmount+=LightAndShadow.MAGIC;
                }else{
                    shineAmount+=LightAndShadow.MAGIC;
                }
            }else if(cardForShow2.cardID.equals(makeID("UikaNeedAnon"))){
                gravityAmount+=gravityAmount*NeedAnon.MAGIC;
            }else if(cardForShow2.cardID.equals(makeID("UikaDivergeWorld"))){
                divergeWorldAmount++;
            }else if(cardForShow2.cardID.equals(makeID("UikaLastGendle"))){
                int tmp=gravityAmount;
                gravityAmount=shineAmount;
                shineAmount=tmp;
            }else if(cardForShow2.cardID.equals(makeID("UikaStrike"))){
                damageNum+=monsterDamage;
                damageNum+=divergeWorldAmount*gravityAmount;
                gravityDamageNum+=divergeWorldAmount*gravityAmount;
            }
        }

        damageNum+=gravityAmount;
        gravityDamageNum+=gravityAmount;

        return new ArrayList<>(Arrays.asList(damageNum,gravityDamageNum));
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
    public void render(SpriteBatch sb) {
        super.render(sb);

        renderAttackIntent(sb);
        renderDamageNumber(sb);
        if (this.attackIntentHb.hovered||this.damageNumberHb.hovered) {
            // 这类方法可以渲染一个简单的提示
            // 参数：Tip的左下角X, Tip的左下角Y, 标题, 内容
            TipHelper.renderGenericTip(
                    InputHelper.mX + 50.0F * Settings.scale,  // Tip往右下方一点
                    InputHelper.mY - 50.0F * Settings.scale,
                    "预警",
                    "敌人将对你造成 #b" + damageForShow+ " 点伤害。 NL #b"+(damageForShow-gravityDamageForShow) +
                            " 点来自攻击。 NL #b"+gravityDamageForShow+" 点来自 #y重力 。"
            );
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
                Integer.toString(damageForShow), textX, textY, Color.RED);
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


