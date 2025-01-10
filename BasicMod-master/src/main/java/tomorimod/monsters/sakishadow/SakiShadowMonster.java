package tomorimod.monsters.sakishadow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.cards.notshow.Death;
import tomorimod.cards.notshow.Fear;
import tomorimod.cards.notshow.Love;
import tomorimod.cards.notshow.Sad;
import tomorimod.character.Tomori;
import tomorimod.monsters.BaseMonster;
import tomorimod.monsters.mutsumi.FriendlyMonsterPower;
import tomorimod.monsters.mutsumi.SoyoMonster;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;
import tomorimod.vfx.DynamicBackgroundTestEffect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class SakiShadowMonster extends BaseMonster {
    public static final String ID = makeID(SakiShadowMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    // 血量相关
    private static final int HP_MIN = 200;
    private static final int HP_MAX = 200;

    // hit box
    private static final float HB_X = 0F;
    private static final float HB_Y = 50F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    // 图片资源
    private static final String imgPath = imagePath("monsters/" + SakiShadowMonster.class.getSimpleName() + ".png");
    public static final float DRAW_X = 1400.0F;
    public static final float DRAW_Y = 450.0F;

    // 小怪
    private SoyoMonster soyoMonster;

    // 状态标记
    private boolean isFirstTurn;
    private boolean isGiveCurse;
    private boolean isSecondPhase = false;

    // 记录用于随机展示的卡牌
    private final List<AbstractCard> cards = Arrays.asList(
            new Death(),
            new Fear(),
            new Love(),
            new Sad()
    );

    // 用于在 getMove 中判断走哪个分支
    private int curNum = -1;

    // 用于处理渐入渐出
    private float alpha = 1.0f;
    private boolean isFadingIn = false;
    private final float fadeInDuration = 4.0f;
    private float fadeInTimer = fadeInDuration;

    private boolean isRebirth = false;
    private final float fadeOutDuration = 0.5f;
    private float fadeOutTimer = fadeOutDuration;

    private Hitbox attackIntentHb;
    private Hitbox damageNumberHb;
    private int damageForShow;
    private int damageNum;

    public SakiShadowMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);
        setHp(HP_MIN, HP_MAX);
        this.type = EnemyType.BOSS;

        // 对话位置微调
        this.dialogX = this.hb_x - 50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        // 先让自己画到屏幕外，看起来是“隐藏”的状态
        this.drawX = -1000.0f;
        this.drawY = -1000.0f;

        // 准备各种伤害（注意顺序和 takeTurn 中 damage.get(i) 的索引对应）
        this.damage.add(new DamageInfo(this, 999, DamageInfo.DamageType.NORMAL)); // index=0
        this.damage.add(new DamageInfo(this, 10, DamageInfo.DamageType.NORMAL));  // index=1
        this.damage.add(new DamageInfo(this, 999, DamageInfo.DamageType.NORMAL));  // index=2
        this.damage.add(new DamageInfo(this, 2, DamageInfo.DamageType.NORMAL));   // index=3
        this.damage.add(new DamageInfo(this, 40, DamageInfo.DamageType.NORMAL));  // index=4
        this.damage.add(new DamageInfo(this, 15, DamageInfo.DamageType.NORMAL));  // index=5

        isFirstTurn = true;
        isGiveCurse = true;

        // 攻击意图图标的大小
        this.attackIntentHb = new Hitbox(128.0f * Settings.scale, 128.0f * Settings.scale);

        // 伤害数字的大小(这里随意示例了一个 64x32 的范围)
        this.damageNumberHb = new Hitbox(64.0f * Settings.scale, 32.0f * Settings.scale);
    }

    @Override
    public void usePreBattleAction() {
        // 切换场景特效
        AbstractGameEffect effect = new ChangeSceneEffect(
                ImageMaster.loadImage(imagePath("monsters/scenes/SakiShadow_bg.png"))
        );
        AbstractDungeon.effectList.add(effect);

        // 关闭原场景音效
        AbstractDungeon.scene.fadeOutAmbiance();

        // 给自己加一些特殊能力
        if(AbstractDungeon.player instanceof Tomori){
            addToBot(new ApplyPowerAction(this, this, new SakiShadowImmunityPower(this)));
        }
        addToBot(new ApplyPowerAction(this, this, new SakiShadowWorldViewPower(this)));

        initializeSoyoMonster();
        // 使房间处于“无法失败”状态
        AbstractDungeon.getCurrRoom().cannotLose = true;

        setMove((byte) 0, Intent.ATTACK,
                this.damage.get(0).base, 1, false);
        createIntent();
    }

    private void initializeSoyoMonster() {
        // 初始化 SoyoMonster
        this.soyoMonster = new SoyoMonster(0f, 0f);
        soyoMonster.drawX = AbstractDungeon.player.drawX + 300.0F * Settings.scale;
        soyoMonster.drawY = AbstractDungeon.player.drawY;
        soyoMonster.flipHorizontal = true;

        // 召唤友方小怪
        addToBot(new SpawnMonsterAction(soyoMonster, false));
        addToBot(new ApplyPowerAction(soyoMonster, soyoMonster, new FriendlyMonsterPower(soyoMonster)));

        // 让 soyoMonster 维持在一个 UNKNOWN 的意图
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                soyoMonster.setMove((byte) 99, Intent.UNKNOWN);
                soyoMonster.createIntent();
                isDone = true;
            }
        });
    }

    @Override
    public void takeTurn() {
        // 第一次回合的特殊处理
        if (isFirstTurn) {
            handleFirstTurn();
        } else {
            // 后续回合根据 nextMove 做动作
            switch (this.nextMove) {
                case 1:
                    // 伤害玩家 2 次
                    damagePlayer(AbstractDungeon.player, 1, 2, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
                    break;
                case 2:
                    // 给自己添加一个 BUFF (SakiRightPower)
                    addToBot(new ApplyPowerAction(this, this, new SakiShadowRightPower(this, 5), 5));
                    break;
                case 3:
                    // 给玩家“随机一张”负面牌（这里 cards 是 FearlessXX）
                    obtainRandomCard();
                    break;
                case 4:
                    // 伤害玩家 5 次
                    damagePlayer(AbstractDungeon.player, 3, 5, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
                    break;
                case 5:
                    // 造成一次大伤害 + 给自己加个 BUFF
                    damagePlayer(AbstractDungeon.player, 4, 1, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
                    addToBot(new ApplyPowerAction(this, this, new SakiShadowRightPower(this, 3), 3));
                    break;
                case 6:
                    // 伤害玩家 2 次 + 获得随机牌
                    damagePlayer(AbstractDungeon.player, 5, 2, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
                    obtainRandomCard();
                    break;
                case 50:
                    // 进入下一阶段逻辑
                    transitionToSecondPhase();
                    break;
                case 99:
                    // 一次性给玩家 4 张 FearlessXX
                    float startX = (float) Settings.WIDTH / 5.0F;
                    float startY = (float) Settings.HEIGHT / 2.0F;
                    float spacing = (float) Settings.WIDTH / 5.0F;
                    float delay = 0.5F;

                    for (int i = 0; i < cards.size(); i++) {
                        addToBot(new ShowCardAndObtainAction(
                                cards.get(i).makeStatEquivalentCopy(),
                                startX + i * spacing, startY
                        ));
                        addToBot(new TrueWaitAction(delay));
                    }
                    isGiveCurse = false;
                    break;
            }
        }

        // 回合结束后，告诉怪物管理器：该轮结束，下一轮 rollMove
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    /**
     * 第一个回合的特殊逻辑。
     */
    private void handleFirstTurn() {
        addToTop(new PlayBGMAction(MusicPatch.MusicHelper.KILLKISS, this));

        // 先砍一下 SoyoMonster（通常是那下大伤害 999）
        addToBot(new DamageAction(
                soyoMonster, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL
        ));

        // 给背景加上一个特效
        AbstractDungeon.effectList.add(new DynamicBackgroundTestEffect(0.1f));

        // 自己出现
        this.drawX = DRAW_X * Settings.scale;
        this.drawY = DRAW_Y * Settings.scale;

        // 重置玩家的抽牌堆
        AbstractDungeon.player.drawPile.initializeDeck(AbstractDungeon.player.masterDeck);

        // 渐入动画开始
        isFadingIn = true;
        // 标记不是第一回合了
        isFirstTurn = false;

        // 等个 3 秒，等过场动画
        addToBot(new TrueWaitAction(3.0f));
    }

    /**
     * 进入下一阶段的逻辑：让怪物可以被真正击败，并恢复满血等。
     */
    private void transitionToSecondPhase() {
        AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
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
        // 先砍一下玩家
        AbstractDungeon.actionManager.addToBottom(new VFXAction
                (new ViceCrushRedEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
        damagePlayer(AbstractDungeon.player, 2, 1, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        //AbstractDungeon.effectList.add(new DynamicBackgroundTestEffect(0.1f));
        // 自己瞬间回满血
        addToBot(new HealAction(this, this, this.maxHealth));

        this.drawX = DRAW_X * Settings.scale;
        this.drawY = DRAW_Y * Settings.scale;
        alpha = 1.0f;
        this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, 1));

        isSecondPhase = true;
    }

    @Override
    protected void getMove(int num) {
        // 如果已经不是第一回合
        if (!isFirstTurn) {
            if (isGiveCurse) {
                setMove((byte) 99, Intent.DEBUFF);
            } else {
                // 一阶段 vs 二阶段的意图逻辑
                if (!isSecondPhase) {
                    // 第一阶段
                    if (curNum == -1) {
                        setMove((byte) 1, Intent.ATTACK, this.damage.get(1).base, 2, true);
                        curNum = 0;
                    } else {
                        switch (getDifferentNum(curNum)) {
                            case 0:
                                setMove((byte) 1, Intent.ATTACK,
                                        this.damage.get(1).base, 2, true);
                                break;
                            case 1:
                                setMove((byte) 2, Intent.BUFF);
                                break;
                            case 2:
                                setMove((byte) 3, Intent.DEBUFF);
                                break;
                        }
                    }
                } else {
                    // 第二阶段
                    switch (getDifferentNum(curNum)) {
                        case 0:
                            setMove((byte) 4, Intent.ATTACK,
                                    this.damage.get(3).base, 5, true);
                            break;
                        case 1:
                            setMove((byte) 5, Intent.ATTACK_BUFF,
                                    this.damage.get(4).base, 1, false);
                            break;
                        case 2:
                            // 注意：此处原代码写的是 (byte)5，但如果你想表现“ATTACK_DEBUFF”，
                            // 需要修改为 (byte)6 或者另一个数字，以免覆盖上一条 case
                            setMove((byte) 6, Intent.ATTACK_DEBUFF,
                                    this.damage.get(5).base, 2, true);
                            break;
                    }
                }
            }
        } else {
            // 第一回合
            setMove((byte) 0, Intent.ATTACK,
                    this.damage.get(0).base, 1, false);
        }
    }

    private int getDifferentNum(int current) {
        int newNum = AbstractDungeon.miscRng.random(2);
        while (newNum == current) {
            newNum = AbstractDungeon.miscRng.random(2);
        }
        curNum = newNum;
        return newNum;
    }




    private void obtainRandomCard() {
        AbstractCard c = cards.get(AbstractDungeon.miscRng.random(3)).makeStatEquivalentCopy();
        addToBot(new ShowCardAndObtainAction(c, Settings.WIDTH / 2, Settings.HEIGHT / 2));
    }

    private void handleFadeIn() {
        if (isFadingIn) {
            fadeInTimer -= Gdx.graphics.getDeltaTime();
            if (fadeInTimer < 0f) {
                fadeInTimer = 0f;
                isFadingIn = false;
            }
            float progress = 1.0f - (fadeInTimer / fadeInDuration);
            alpha = Interpolation.fade.apply(0f, 1f, progress);
            this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, alpha));

            // 若渐入结束，把 alpha 设回 1
            if (!isFadingIn) {
                alpha = 1.0f;
                this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, alpha));
            }
        }
    }

    private void handleRebirthFadeOut() {
        if (isRebirth) {
            fadeOutTimer -= Gdx.graphics.getDeltaTime();
            if (fadeOutTimer < 0f) {
                fadeOutTimer = 0f;
                isRebirth = false;
            }
            alpha = fadeOutTimer / fadeOutDuration;
            this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, alpha));

            if (!isRebirth) {
                // 减到 0 之后，再把怪移到屏幕外
                this.drawX = -1000.0f;
                this.drawY = -1000.0f;
            }
        }
    }

    @Override
    public void update() {
        super.update();
        handleFadeIn();
        handleRebirthFadeOut();

        damageForShow=getPublicField(this, "intentBaseDmg", Integer.class);
        //damageNum=getPublicField(this, "intentMultiAmt", Integer.class);

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
        if (alpha < 1.0f) {
            sb.setColor(this.tint.color);
            sb.draw(
                    this.img,
                    this.drawX - (float) this.img.getWidth() * Settings.scale / 2.0F + this.animX,
                    this.drawY + this.animY,
                    (float) this.img.getWidth() * Settings.scale,
                    (float) this.img.getHeight() * Settings.scale,
                    0, 0, this.img.getWidth(), this.img.getHeight(),
                    this.flipHorizontal, this.flipVertical
            );
        } else {
            // 如果透明度满了，就用默认的父类绘制
            super.render(sb);
        }

        if(halfDead){
            renderAttackIntent(sb);
            renderDamageNumber(sb);
            if (this.attackIntentHb.hovered||this.damageNumberHb.hovered) {
                // 这类方法可以渲染一个简单的提示
                // 参数：Tip的左下角X, Tip的左下角Y, 标题, 内容
                TipHelper.renderGenericTip(
                        InputHelper.mX + 50.0F * Settings.scale,  // Tip往右下方一点
                        InputHelper.mY - 50.0F * Settings.scale,
                        "预警",
                        "敌人将对你造成 #b" + damageForShow+ " 点伤害。"
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
                damageForShow+"", textX, textY, Color.WHITE);
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
    public void damage(DamageInfo info) {
        super.damage(info);
        // 如果血量 <= 0，并且之前没有处于 halfDead
        if (this.currentHealth <= 0 && !this.halfDead) {
            if (AbstractDungeon.getCurrRoom().cannotLose) {
                // 进入“半死”状态
                this.halfDead = true;
                this.isRebirth = true;
            }
            // 处理遗物与光环等
            for (AbstractPower p : this.powers) {
                p.onDeath();
            }
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onMonsterDeath(this);
            }
            addToTop(new ClearCardQueueAction());

            // 清理身上的 DEBUFF
            for (Iterator<AbstractPower> s = this.powers.iterator(); s.hasNext(); ) {
                AbstractPower p = s.next();
                if (p.type == AbstractPower.PowerType.DEBUFF
                        || p.ID.equals("Curiosity")
                        || p.ID.equals("Unawakened")
                        || p.ID.equals("Shackled")) {
                    s.remove();
                }
            }

            // 改变意图为 50（下一回合进入下一阶段）
            setMove((byte) 50, Intent.ATTACK_BUFF,
                    this.damage.get(2).base, 1, false);
            createIntent();
            applyPowers();
        }
    }

    @Override
    public void die() {
        // 如果房间可以输了（已经解除 cannotLose）
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();

            // 这里是 boss 死亡时的特效和结算
            if (this.currentHealth <= 0) {
                useFastShakeAnimation(5.0F);
                CardCrawlGame.screenShake.rumble(4.0F);
                onBossVictoryLogic();
            }
        }
    }
}
