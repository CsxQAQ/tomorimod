package tomorimod.monsters.sakishadow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tomorimod.actions.PlayBGMAction;
import tomorimod.cards.notshow.sakishadow.Death;
import tomorimod.cards.notshow.sakishadow.Fear;
import tomorimod.cards.notshow.sakishadow.Love;
import tomorimod.cards.notshow.sakishadow.Sad;
import tomorimod.character.Tomori;
import tomorimod.monsters.BaseMonster;
import tomorimod.monsters.WarningUi;
import tomorimod.monsters.mutsumi.FriendlyMonsterPower;
import tomorimod.monsters.mutsumi.SoyoMonster;
import tomorimod.patches.MusicPatch;
import tomorimod.vfx.ChangeSceneEffect;
import tomorimod.vfx.DynamicBackgroundTestEffect;

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

    public static final int HP_MIN = 200;
    public static final int HP_MAX = 200;
    public static final int DAMAGE_0 = 999;
    public static final int DAMAGE_1 = 10;
    public static final int DAMAGE_2 = 999;
    public static final int DAMAGE_3 = 2;
    public static final int DAMAGE_4 = 40;
    public static final int DAMAGE_5 = 15;
    public static final int DAMAGETIME_0 = 1;
    public static final int DAMAGETIME_1 = 2;
    public static final int DAMAGETIME_2 = 1;
    public static final int DAMAGETIME_3 = 5;
    public static final int DAMAGETIME_4 = 1;
    public static final int DAMAGETIME_5 = 2;
    public static final int REBIRTH_HEALTH = 1000;

    public static final int HP_MIN_WEAK = 200;
    public static final int HP_MAX_WEAK = 200;
    public static final int DAMAGE_0_WEAK = 200;
    public static final int DAMAGE_1_WEAK = 10;
    public static final int DAMAGE_2_WEAK = 200;
    public static final int DAMAGE_3_WEAK = 2;
    public static final int DAMAGE_4_WEAK = 40;
    public static final int DAMAGE_5_WEAK = 15;
    public static final int DAMAGETIME_0_WEAK = 1;
    public static final int DAMAGETIME_1_WEAK = 2;
    public static final int DAMAGETIME_2_WEAK = 1;
    public static final int DAMAGETIME_3_WEAK = 5;
    public static final int DAMAGETIME_4_WEAK = 1;
    public static final int DAMAGETIME_5_WEAK = 2;
    public static final int REBIRTH_HEALTH_WEAK = 200;

    private int hpMinVal, hpMaxVal;
    private int damageVal0, damageVal1, damageVal2, damageVal3, damageVal4, damageVal5;
    private int damageTimeVal0, damageTimeVal1, damageTimeVal2, damageTimeVal3, damageTimeVal4, damageTimeVal5;
    private int rebirthHealth;

    private static final float HB_X = 0F;
    private static final float HB_Y = 50F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;
    private static final String imgPath = imagePath("monsters/" + SakiShadowMonster.class.getSimpleName() + ".png");
    public static final float DRAW_X = 1400.0F;
    public static final float DRAW_Y = 450.0F;

    private SoyoMonster soyoMonster;
    private boolean isFirstTurn = true;
    private boolean isGiveCurse = true;
    private boolean isSecondPhase = false;
    private int curNum = -1;
    private WarningUi warningUi;
    private SakiShadowFadeVFX sakiShadowFadeVFX;
    public boolean isFadingIn = false;
    public boolean isRebirth = false;
    public float alpha = 1.0f;

    private final List<AbstractCard> cards = Arrays.asList(new Death(), new Fear(), new Love(), new Sad());

    public SakiShadowMonster(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        if (isHardMode) {
            hpMinVal = HP_MIN;
            hpMaxVal = HP_MAX;
            damageVal0 = DAMAGE_0;
            damageVal1 = DAMAGE_1;
            damageVal2 = DAMAGE_2;
            damageVal3 = DAMAGE_3;
            damageVal4 = DAMAGE_4;
            damageVal5 = DAMAGE_5;
            damageTimeVal0 = DAMAGETIME_0;
            damageTimeVal1 = DAMAGETIME_1;
            damageTimeVal2 = DAMAGETIME_2;
            damageTimeVal3 = DAMAGETIME_3;
            damageTimeVal4 = DAMAGETIME_4;
            damageTimeVal5 = DAMAGETIME_5;
            rebirthHealth=REBIRTH_HEALTH;
        } else {
            hpMinVal = HP_MIN_WEAK;
            hpMaxVal = HP_MAX_WEAK;
            damageVal0 = DAMAGE_0_WEAK;
            damageVal1 = DAMAGE_1_WEAK;
            damageVal2 = DAMAGE_2_WEAK;
            damageVal3 = DAMAGE_3_WEAK;
            damageVal4 = DAMAGE_4_WEAK;
            damageVal5 = DAMAGE_5_WEAK;
            damageTimeVal0 = DAMAGETIME_0_WEAK;
            damageTimeVal1 = DAMAGETIME_1_WEAK;
            damageTimeVal2 = DAMAGETIME_2_WEAK;
            damageTimeVal3 = DAMAGETIME_3_WEAK;
            damageTimeVal4 = DAMAGETIME_4_WEAK;
            damageTimeVal5 = DAMAGETIME_5_WEAK;
            rebirthHealth=REBIRTH_HEALTH_WEAK;
        }

        setHp(hpMinVal, hpMaxVal);
        this.type = EnemyType.BOSS;
        this.dialogX = this.hb_x - 50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;
        this.drawX = -1000.0f;
        this.drawY = -1000.0f;

        this.damage.add(new DamageInfo(this, damageVal0, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, damageVal1, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, damageVal2, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, damageVal3, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, damageVal4, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, damageVal5, DamageInfo.DamageType.NORMAL));

        warningUi = new WarningUi(this);
        sakiShadowFadeVFX = new SakiShadowFadeVFX(this);
    }

    @Override
    public void usePreBattleAction() {
        AbstractGameEffect effect = new ChangeSceneEffect(ImageMaster.loadImage(imagePath("monsters/scenes/SakiShadow_bg.png")));
        AbstractDungeon.effectList.add(effect);
        AbstractDungeon.scene.fadeOutAmbiance();
        if (AbstractDungeon.player instanceof Tomori) {
            addToBot(new ApplyPowerAction(this, this, new SakiShadowImmunityPower(this)));
        }
        addToBot(new ApplyPowerAction(this, this, new SakiShadowWorldViewPower(this)));
        initializeSoyoMonster();
        AbstractDungeon.getCurrRoom().cannotLose = true;
        setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base, damageTimeVal0, false);
        createIntent();
    }

    private void initializeSoyoMonster() {
        soyoMonster = new SoyoMonster(0f, 0f);
        soyoMonster.drawX = AbstractDungeon.player.drawX + 300.0F * Settings.scale;
        soyoMonster.drawY = AbstractDungeon.player.drawY;
        soyoMonster.flipHorizontal = true;
        addToBot(new SpawnMonsterAction(soyoMonster, false));
        addToBot(new ApplyPowerAction(soyoMonster, soyoMonster, new FriendlyMonsterPower(soyoMonster)));
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
        if (isFirstTurn) {
            addToTop(new PlayBGMAction(MusicPatch.MusicHelper.KILLKISS, this));
            addToBot(new DamageAction(soyoMonster, new DamageInfo(
                    this,damageVal0, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.effectList.add(new DynamicBackgroundTestEffect(0.1f));
            this.drawX = DRAW_X * Settings.scale;
            this.drawY = DRAW_Y * Settings.scale;
            AbstractDungeon.player.drawPile.initializeDeck(AbstractDungeon.player.masterDeck);
            isFadingIn = true;
            isFirstTurn = false;
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if(!soyoMonster.isDeadOrEscaped()){
                        soyoMonster.die();
                    }
                    isDone=true;
                }
            });
            addToBot(new TrueWaitAction(3.0f));

        } else {
            switch (this.nextMove) {
                case 1:
                    damagePlayer(1, damageTimeVal1);
                    break;
                case 2:
                    addToBot(new ApplyPowerAction(this, this, new SakiShadowRightPower(this, 5), 5));
                    break;
                case 3:
                    obtainRandomCard();
                    break;
                case 4:
                    damagePlayer(3, damageTimeVal3);
                    break;
                case 5:
                    damagePlayer(4, damageTimeVal4);
                    addToBot(new ApplyPowerAction(this, this, new SakiShadowRightPower(this, 3), 3));
                    break;
                case 6:
                    damagePlayer(5, damageTimeVal5);
                    obtainRandomCard();
                    break;
                case 50:
                    AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            halfDead = false;
                            isDead = false;
                            isDying = false;
                            AbstractDungeon.getCurrRoom().cannotLose = false;
                            isDone = true;
                        }
                    });
                    setHp(rebirthHealth);
                    addToBot(new VFXAction(new ViceCrushRedEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
                    damagePlayer(2, damageTimeVal2);
                    addToBot(new HealAction(SakiShadowMonster.this, SakiShadowMonster.this, maxHealth));
                    this.drawX = DRAW_X * Settings.scale;
                    this.drawY = DRAW_Y * Settings.scale;
                    alpha = 1.0f;
                    this.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, 1));
                    isSecondPhase = true;
                    break;
                case 99:
                    float startX = (float) Settings.WIDTH / 5.0F;
                    float startY = (float) Settings.HEIGHT / 2.0F;
                    float spacing = (float) Settings.WIDTH / 5.0F;
                    float delay = 0.5F;
                    for (int i = 0; i < cards.size(); i++) {
                        addToBot(new ShowCardAndObtainAction(cards.get(i).makeStatEquivalentCopy(),
                                startX + i * spacing, startY));
                        addToBot(new TrueWaitAction(delay));
                    }
                    isGiveCurse = false;
                    break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected Texture getAttackIntent() {
        return super.getAttackIntent();
    }

    @Override
    protected void getMove(int num) {
        if (!isFirstTurn) {
            if (isGiveCurse) {
                setMove((byte) 99, Intent.DEBUFF);
            } else {
                if (!isSecondPhase) {
                    if (curNum == -1) {
                        setMove((byte) 1, Intent.ATTACK, this.damage.get(1).base, damageTimeVal1, true);
                        curNum = 0;
                    } else {
                        switch (getDifferentNum(curNum)) {
                            case 0:
                                setMove((byte) 1, Intent.ATTACK, this.damage.get(1).base, damageTimeVal1, true);
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
                    switch (getDifferentNum(curNum)) {
                        case 0:
                            setMove((byte) 4, Intent.ATTACK, this.damage.get(3).base, damageTimeVal3, true);
                            break;
                        case 1:
                            setMove((byte) 5, Intent.ATTACK_BUFF, this.damage.get(4).base, damageTimeVal4, false);
                            break;
                        case 2:
                            setMove((byte) 6, Intent.ATTACK_DEBUFF, this.damage.get(5).base, damageTimeVal5, true);
                            break;
                    }
                }
            }
        } else {
            setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base, damageTimeVal0, false);
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
        addToBot(new ShowCardAndObtainAction(c, Settings.WIDTH / 2f, Settings.HEIGHT / 2f));
    }

    @Override
    public void update() {
        super.update();
        if (isFadingIn) {
            sakiShadowFadeVFX.handleFadeIn();
        }
        if (isRebirth) {
            sakiShadowFadeVFX.handleRebirthFadeOut();
        }
        warningUi.update();
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
            super.render(sb);
        }
        if (halfDead || warningUi.damageFrozen) {
            warningUi.render(sb);
        }
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (this.currentHealth <= 0 && !this.halfDead) {
            if (AbstractDungeon.getCurrRoom().cannotLose) {
                this.halfDead = true;
                this.isRebirth = true;
                warningUi.setFrozen();
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
                if (p.type == AbstractPower.PowerType.DEBUFF
                        || p.ID.equals("Curiosity")
                        || p.ID.equals("Unawakened")
                        || p.ID.equals("Shackled")) {
                    s.remove();
                }
            }
            setMove((byte) 50, Intent.ATTACK_BUFF, this.damage.get(2).base, damageTimeVal2, false);
            createIntent();
            applyPowers();
        }
    }

    @Override
    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            if (this.currentHealth <= 0) {
                useFastShakeAnimation(5.0F);
                CardCrawlGame.screenShake.rumble(4.0F);
                onBossVictoryLogic();
            }
        }
    }
}
