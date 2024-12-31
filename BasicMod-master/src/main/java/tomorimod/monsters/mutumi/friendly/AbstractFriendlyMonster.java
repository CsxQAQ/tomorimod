package tomorimod.monsters.mutumi.friendly;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BackAttackPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.DebuffParticleEffect;
import com.megacrit.cardcrawl.vfx.ShieldParticleEffect;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.BuffParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.DeckPoofEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import com.megacrit.cardcrawl.vfx.combat.StunStarEffect;
import com.megacrit.cardcrawl.vfx.combat.UnknownParticleEffect;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractFriendlyMonster extends AbstractCreature {
    private static final Logger logger = LogManager.getLogger(AbstractFriendlyMonster.class.getName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DEATH_TIME = 1.8F;
    private static final float ESCAPE_TIME = 3.0F;
    protected static final byte ESCAPE = 99;
    protected static final byte ROLL = 98;
    public float deathTimer;
    private Color nameColor;
    private Color nameBgColor;
    protected Texture img;
    public boolean tintFadeOutCalled;
    protected HashMap<Byte, String> moveSet;
    public boolean escaped;
    public boolean escapeNext;
    private PowerTip intentTip;
    public EnemyType type;
    private float hoverTimer;
    public boolean cannotEscape;
    public ArrayList<DamageInfo> damage;
    private FriendlyMonsterMoveInfo move;
    private float intentParticleTimer;
    private float intentAngle;
    public ArrayList<Byte> moveHistory;
    private ArrayList<AbstractGameEffect> intentVfx;
    public byte nextMove;
    private static final int INTENT_W = 128;
    private BobEffect bobEffect;
    private static final float INTENT_HB_W;
    public Hitbox intentHb;
    public Intent intent;
    public Intent tipIntent;
    public float intentAlpha;
    public float intentAlphaTarget;
    public float intentOffsetX;
    private Texture intentImg;
    private Texture intentBg;
    private int intentDmg;
    private int intentBaseDmg;
    private int intentMultiAmt;
    private boolean isMultiDmg;
    private Color intentColor;
    public String moveName;
    protected List<Disposable> disposables;
    public static String[] MOVES;
    public static String[] DIALOG;
    public static Comparator<AbstractFriendlyMonster> sortByHitbox;

    public AbstractFriendlyMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        this(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY, false);
    }

    public AbstractFriendlyMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, boolean ignoreBlights) {
        this.deathTimer = 0.0F;
        this.nameColor = new Color();
        this.nameBgColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        this.tintFadeOutCalled = false;
        this.moveSet = new HashMap();
        this.escaped = false;
        this.escapeNext = false;
        this.intentTip = new PowerTip();
        this.type = AbstractFriendlyMonster.EnemyType.NORMAL;
        this.hoverTimer = 0.0F;
        this.cannotEscape = false;
        this.damage = new ArrayList();
        this.intentParticleTimer = 0.0F;
        this.intentAngle = 0.0F;
        this.moveHistory = new ArrayList();
        this.intentVfx = new ArrayList();
        this.nextMove = -1;
        this.bobEffect = new BobEffect();
        this.intent = AbstractFriendlyMonster.Intent.DEBUG;
        this.tipIntent = AbstractFriendlyMonster.Intent.DEBUG;
        this.intentAlpha = 0.0F;
        this.intentAlphaTarget = 0.0F;
        this.intentOffsetX = 0.0F;
        this.intentImg = null;
        this.intentBg = null;
        this.intentDmg = -1;
        this.intentBaseDmg = -1;
        this.intentMultiAmt = 0;
        this.isMultiDmg = false;
        this.intentColor = Color.WHITE.cpy();
        this.moveName = null;
        this.disposables = new ArrayList();
        this.isPlayer = false;
        this.name = name;
        this.id = id;
        this.maxHealth = maxHealth;
        if (!ignoreBlights && Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
            float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
            this.maxHealth = (int)((float)maxHealth * mod);
        }

        if (ModHelper.isModEnabled("MonsterHunter")) {
            this.currentHealth = (int)((float)this.currentHealth * 1.5F);
        }

        if (Settings.isMobile) {
            hb_w *= 1.17F;
        }

        this.currentHealth = this.maxHealth;
        this.currentBlock = 0;
        this.drawX = (float)Settings.WIDTH * 0.75F + offsetX * Settings.xScale;
        this.drawY = AbstractDungeon.floorY + offsetY * Settings.yScale;
        this.hb_w = hb_w * Settings.scale;
        this.hb_h = hb_h * Settings.xScale;
        this.hb_x = hb_x * Settings.scale;
        this.hb_y = hb_y * Settings.scale;
        if (imgUrl != null) {
            this.img = ImageMaster.loadImage(imgUrl);
        }

        this.intentHb = new Hitbox(INTENT_HB_W, INTENT_HB_W);
        this.hb = new Hitbox(this.hb_w, this.hb_h);
        this.healthHb = new Hitbox(this.hb_w, 72.0F * Settings.scale);
        this.refreshHitboxLocation();
        this.refreshIntentHbLocation();
    }

    public AbstractFriendlyMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl) {
        this(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, 0.0F, 0.0F);
    }

    public void refreshIntentHbLocation() {
        this.intentHb.move(this.hb.cX + this.intentOffsetX, this.hb.cY + this.hb_h / 2.0F + INTENT_HB_W / 2.0F);
    }

    @Override
    public boolean isDeadOrEscaped() {
        if (!this.isDying && !this.halfDead) {
            if(this.isEscaping){
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    public void update() {
        Iterator var1 = this.powers.iterator();

        while(var1.hasNext()) {
            AbstractPower p = (AbstractPower)var1.next();
            p.updateParticles();
        }

        this.updateReticle();
        this.updateHealthBar();
        //this.updateAnimations();

        this.refreshHitboxLocation();
        refreshIntentHbLocation();

        this.updateDeathAnimation();
        this.updateEscapeAnimation();
        this.updateIntent();
        this.tint.update();

    }

    public void unhover() {
        this.healthHb.hovered = false;
        this.hb.hovered = false;
        this.intentHb.hovered = false;
    }

    private void updateIntent() {
        this.bobEffect.update();
        if (this.intentAlpha != this.intentAlphaTarget && this.intentAlphaTarget == 1.0F) {
            this.intentAlpha += Gdx.graphics.getDeltaTime();
            if (this.intentAlpha > this.intentAlphaTarget) {
                this.intentAlpha = this.intentAlphaTarget;
            }
        } else if (this.intentAlphaTarget == 0.0F) {
            this.intentAlpha -= Gdx.graphics.getDeltaTime() / 1.5F;
            if (this.intentAlpha < 0.0F) {
                this.intentAlpha = 0.0F;
            }
        }

        if (!this.isDying && !this.isEscaping) {
            this.updateIntentVFX();
        }

        Iterator<AbstractGameEffect> i = this.intentVfx.iterator();

        while(i.hasNext()) {
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            e.update();
            if (e.isDone) {
                i.remove();
            }
        }

    }

    private void updateIntentVFX() {
        if (this.intentAlpha > 0.0F) {
            if (this.intent != AbstractFriendlyMonster.Intent.ATTACK_DEBUFF && this.intent != AbstractFriendlyMonster.Intent.DEBUFF && this.intent != AbstractFriendlyMonster.Intent.STRONG_DEBUFF && this.intent != AbstractFriendlyMonster.Intent.DEFEND_DEBUFF) {
                if (this.intent != AbstractFriendlyMonster.Intent.ATTACK_BUFF && this.intent != AbstractFriendlyMonster.Intent.BUFF && this.intent != AbstractFriendlyMonster.Intent.DEFEND_BUFF) {
                    if (this.intent == AbstractFriendlyMonster.Intent.ATTACK_DEFEND) {
                        this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                        if (this.intentParticleTimer < 0.0F) {
                            this.intentParticleTimer = 0.5F;
                            this.intentVfx.add(new ShieldParticleEffect(this.intentHb.cX, this.intentHb.cY));
                        }
                    } else if (this.intent == AbstractFriendlyMonster.Intent.UNKNOWN) {
                        this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                        if (this.intentParticleTimer < 0.0F) {
                            this.intentParticleTimer = 0.5F;
                            this.intentVfx.add(new UnknownParticleEffect(this.intentHb.cX, this.intentHb.cY));
                        }
                    } else if (this.intent == AbstractFriendlyMonster.Intent.STUN) {
                        this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                        if (this.intentParticleTimer < 0.0F) {
                            this.intentParticleTimer = 0.67F;
                            this.intentVfx.add(new StunStarEffect(this.intentHb.cX, this.intentHb.cY));
                        }
                    }
                } else {
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0F) {
                        this.intentParticleTimer = 0.1F;
                        this.intentVfx.add(new BuffParticleEffect(this.intentHb.cX, this.intentHb.cY));
                    }
                }
            } else {
                this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                if (this.intentParticleTimer < 0.0F) {
                    this.intentParticleTimer = 1.0F;
                    this.intentVfx.add(new DebuffParticleEffect(this.intentHb.cX, this.intentHb.cY));
                }
            }
        }

    }

    public void renderTip(SpriteBatch sb) {
        this.tips.clear();
        if (this.intentAlphaTarget == 1.0F && !AbstractDungeon.player.hasRelic("Runic Dome") && this.intent != AbstractFriendlyMonster.Intent.NONE) {
            this.tips.add(this.intentTip);
        }

        Iterator var2 = this.powers.iterator();

        while(var2.hasNext()) {
            AbstractPower p = (AbstractPower)var2.next();
            if (p.region48 != null) {
                this.tips.add(new PowerTip(p.name, p.description, p.region48));
            } else {
                this.tips.add(new PowerTip(p.name, p.description, p.img));
            }
        }

        if (!this.tips.isEmpty()) {
            if (this.hb.cX + this.hb.width / 2.0F < TIP_X_THRESHOLD) {
                TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0F + TIP_OFFSET_R_X, this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
            } else {
                TipHelper.queuePowerTips(this.hb.cX - this.hb.width / 2.0F + TIP_OFFSET_L_X, this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
            }
        }

    }

    private void updateIntentTip() {
        switch (this.intent) {
            case ATTACK:
                this.intentTip.header = TEXT[0];
                if (this.isMultiDmg) {
                    this.intentTip.body = TEXT[1] + this.intentDmg + TEXT[2] + this.intentMultiAmt + TEXT[3];
                } else {
                    this.intentTip.body = TEXT[4] + this.intentDmg + TEXT[5];
                }

                this.intentTip.img = this.getAttackIntentTip();
                break;
            case ATTACK_BUFF:
                this.intentTip.header = TEXT[6];
                if (this.isMultiDmg) {
                    this.intentTip.body = TEXT[7] + this.intentDmg + TEXT[2] + this.intentMultiAmt + TEXT[8];
                } else {
                    this.intentTip.body = TEXT[9] + this.intentDmg + TEXT[5];
                }

                this.intentTip.img = ImageMaster.INTENT_ATTACK_BUFF;
                break;
            case ATTACK_DEBUFF:
                this.intentTip.header = TEXT[10];
                this.intentTip.body = TEXT[11] + this.intentDmg + TEXT[5];
                this.intentTip.img = ImageMaster.INTENT_ATTACK_DEBUFF;
                break;
            case ATTACK_DEFEND:
                this.intentTip.header = TEXT[0];
                if (this.isMultiDmg) {
                    this.intentTip.body = TEXT[12] + this.intentDmg + TEXT[2] + this.intentMultiAmt + TEXT[3];
                } else {
                    this.intentTip.body = TEXT[12] + this.intentDmg + TEXT[5];
                }

                this.intentTip.img = ImageMaster.INTENT_ATTACK_DEFEND;
                break;
            case BUFF:
                this.intentTip.header = TEXT[10];
                this.intentTip.body = TEXT[19];
                this.intentTip.img = ImageMaster.INTENT_BUFF;
                break;
            case DEBUFF:
                this.intentTip.header = TEXT[10];
                this.intentTip.body = TEXT[20];
                this.intentTip.img = ImageMaster.INTENT_DEBUFF;
                break;
            case STRONG_DEBUFF:
                this.intentTip.header = TEXT[10];
                this.intentTip.body = TEXT[21];
                this.intentTip.img = ImageMaster.INTENT_DEBUFF2;
                break;
            case DEFEND:
                this.intentTip.header = TEXT[13];
                this.intentTip.body = TEXT[22];
                this.intentTip.img = ImageMaster.INTENT_DEFEND;
                break;
            case DEFEND_DEBUFF:
                this.intentTip.header = TEXT[13];
                this.intentTip.body = TEXT[23];
                this.intentTip.img = ImageMaster.INTENT_DEFEND;
                break;
            case DEFEND_BUFF:
                this.intentTip.header = TEXT[13];
                this.intentTip.body = TEXT[24];
                this.intentTip.img = ImageMaster.INTENT_DEFEND_BUFF;
                break;
            case ESCAPE:
                this.intentTip.header = TEXT[14];
                this.intentTip.body = TEXT[25];
                this.intentTip.img = ImageMaster.INTENT_ESCAPE;
                break;
            case MAGIC:
                this.intentTip.header = TEXT[15];
                this.intentTip.body = TEXT[26];
                this.intentTip.img = ImageMaster.INTENT_MAGIC;
                break;
            case SLEEP:
                this.intentTip.header = TEXT[16];
                this.intentTip.body = TEXT[27];
                this.intentTip.img = ImageMaster.INTENT_SLEEP;
                break;
            case STUN:
                this.intentTip.header = TEXT[17];
                this.intentTip.body = TEXT[28];
                this.intentTip.img = ImageMaster.INTENT_STUN;
                break;
            case UNKNOWN:
                this.intentTip.header = TEXT[18];
                this.intentTip.body = TEXT[29];
                this.intentTip.img = ImageMaster.INTENT_UNKNOWN;
                break;
            case NONE:
                this.intentTip.header = "";
                this.intentTip.body = "";
                this.intentTip.img = ImageMaster.INTENT_UNKNOWN;
                break;
            default:
                this.intentTip.header = "NOT SET";
                this.intentTip.body = "NOT SET";
                this.intentTip.img = ImageMaster.INTENT_UNKNOWN;
        }

    }

    public void heal(int healAmount) {
        if (!this.isDying) {
            AbstractPower p;
            for(Iterator var2 = this.powers.iterator(); var2.hasNext(); healAmount = p.onHeal(healAmount)) {
                p = (AbstractPower)var2.next();
            }

            this.currentHealth += healAmount;
            if (this.currentHealth > this.maxHealth) {
                this.currentHealth = this.maxHealth;
            }

            if (healAmount > 0) {
                AbstractDungeon.effectList.add(new HealEffect(this.hb.cX - this.animX, this.hb.cY, healAmount));
                this.healthBarUpdatedEvent();
            }

        }
    }

    public void flashIntent() {
        if (this.intentImg != null) {
            AbstractDungeon.effectList.add(new FriendlyMonsterFlashIntentEffect(this.intentImg, this));
        }

        this.intentAlphaTarget = 0.0F;
    }

    public void createIntent() {
        this.intent = this.move.intent;
        this.intentParticleTimer = 0.5F;
        this.nextMove = this.move.nextMove;
        this.intentBaseDmg = this.move.baseDamage;
        if (this.move.baseDamage > -1) {
            this.calculateDamage(this.intentBaseDmg);
            if (this.move.isMultiDamage) {
                this.intentMultiAmt = this.move.multiplier;
                this.isMultiDmg = true;
            } else {
                this.intentMultiAmt = -1;
                this.isMultiDmg = false;
            }
        }

        this.intentImg = this.getIntentImg();
        this.intentBg = this.getIntentBg();
        this.tipIntent = this.intent;
        this.intentAlpha = 0.0F;
        this.intentAlphaTarget = 1.0F;
        this.updateIntentTip();
    }

    public void setMove(String moveName, byte nextMove, Intent intent, int baseDamage, int multiplier, boolean isMultiDamage) {
        this.moveName = moveName;
        if (nextMove != -1) {
            this.moveHistory.add(nextMove);
        }

        this.move = new FriendlyMonsterMoveInfo(nextMove, intent, baseDamage, multiplier, isMultiDamage);
    }

    public void setMove(byte nextMove, Intent intent, int baseDamage, int multiplier, boolean isMultiDamage) {
        this.setMove((String)null, nextMove, intent, baseDamage, multiplier, isMultiDamage);
    }

    public void setMove(byte nextMove, Intent intent, int baseDamage) {
        this.setMove((String)null, nextMove, intent, baseDamage, 0, false);
    }

    public void setMove(String moveName, byte nextMove, Intent intent, int baseDamage) {
        this.setMove(moveName, nextMove, intent, baseDamage, 0, false);
    }

    public void setMove(String moveName, byte nextMove, Intent intent) {
        if (intent == AbstractFriendlyMonster.Intent.ATTACK || intent == AbstractFriendlyMonster.Intent.ATTACK_BUFF || intent == AbstractFriendlyMonster.Intent.ATTACK_DEFEND || intent == AbstractFriendlyMonster.Intent.ATTACK_DEBUFF) {
            for(int i = 0; i < 8; ++i) {
                AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(MathUtils.random((float)Settings.WIDTH * 0.25F, (float)Settings.WIDTH * 0.75F), MathUtils.random((float)Settings.HEIGHT * 0.25F, (float)Settings.HEIGHT * 0.75F), "ENEMY MOVE " + moveName + " IS SET INCORRECTLY! REPORT TO DEV", Color.RED.cpy()));
            }

            logger.info("ENEMY MOVE " + moveName + " IS SET INCORRECTLY! REPORT TO DEV");
        }

        this.setMove(moveName, nextMove, intent, -1, 0, false);
    }

    public void setMove(byte nextMove, Intent intent) {
        this.setMove((String)null, nextMove, intent, -1, 0, false);
    }

    public void rollMove() {
        this.getMove(AbstractDungeon.aiRng.random(99));
    }

    protected boolean lastMove(byte move) {
        if (this.moveHistory.isEmpty()) {
            return false;
        } else {
            return (Byte)this.moveHistory.get(this.moveHistory.size() - 1) == move;
        }
    }

    protected boolean lastMoveBefore(byte move) {
        if (this.moveHistory.isEmpty()) {
            return false;
        } else if (this.moveHistory.size() < 2) {
            return false;
        } else {
            return (Byte)this.moveHistory.get(this.moveHistory.size() - 2) == move;
        }
    }

    protected boolean lastTwoMoves(byte move) {
        if (this.moveHistory.size() < 2) {
            return false;
        } else {
            return (Byte)this.moveHistory.get(this.moveHistory.size() - 1) == move && (Byte)this.moveHistory.get(this.moveHistory.size() - 2) == move;
        }
    }

    private Texture getIntentImg() {
        switch (this.intent) {
            case ATTACK:
                return this.getAttackIntent();
            case ATTACK_BUFF:
                return this.getAttackIntent();
            case ATTACK_DEBUFF:
                return this.getAttackIntent();
            case ATTACK_DEFEND:
                return this.getAttackIntent();
            case BUFF:
                return ImageMaster.INTENT_BUFF_L;
            case DEBUFF:
                return ImageMaster.INTENT_DEBUFF_L;
            case STRONG_DEBUFF:
                return ImageMaster.INTENT_DEBUFF2_L;
            case DEFEND:
                return ImageMaster.INTENT_DEFEND_L;
            case DEFEND_DEBUFF:
                return ImageMaster.INTENT_DEFEND_L;
            case DEFEND_BUFF:
                return ImageMaster.INTENT_DEFEND_BUFF_L;
            case ESCAPE:
                return ImageMaster.INTENT_ESCAPE_L;
            case MAGIC:
                return ImageMaster.INTENT_MAGIC_L;
            case SLEEP:
                return ImageMaster.INTENT_SLEEP_L;
            case STUN:
                return null;
            case UNKNOWN:
                return ImageMaster.INTENT_UNKNOWN_L;
            default:
                return ImageMaster.INTENT_UNKNOWN_L;
        }
    }

    private Texture getIntentBg() {
        switch (this.intent) {
            case ATTACK_DEFEND:
                return null;
            default:
                return null;
        }
    }

    protected Texture getAttackIntent(int dmg) {
        if (dmg < 5) {
            return ImageMaster.INTENT_ATK_1;
        } else if (dmg < 10) {
            return ImageMaster.INTENT_ATK_2;
        } else if (dmg < 15) {
            return ImageMaster.INTENT_ATK_3;
        } else if (dmg < 20) {
            return ImageMaster.INTENT_ATK_4;
        } else if (dmg < 25) {
            return ImageMaster.INTENT_ATK_5;
        } else {
            return dmg < 30 ? ImageMaster.INTENT_ATK_6 : ImageMaster.INTENT_ATK_7;
        }
    }

    protected Texture getAttackIntent() {
        int tmp;
        if (this.isMultiDmg) {
            tmp = this.intentDmg * this.intentMultiAmt;
        } else {
            tmp = this.intentDmg;
        }

        if (tmp < 5) {
            return ImageMaster.INTENT_ATK_1;
        } else if (tmp < 10) {
            return ImageMaster.INTENT_ATK_2;
        } else if (tmp < 15) {
            return ImageMaster.INTENT_ATK_3;
        } else if (tmp < 20) {
            return ImageMaster.INTENT_ATK_4;
        } else if (tmp < 25) {
            return ImageMaster.INTENT_ATK_5;
        } else {
            return tmp < 30 ? ImageMaster.INTENT_ATK_6 : ImageMaster.INTENT_ATK_7;
        }
    }

    private Texture getAttackIntentTip() {
        int tmp;
        if (this.isMultiDmg) {
            tmp = this.intentDmg * this.intentMultiAmt;
        } else {
            tmp = this.intentDmg;
        }

        if (tmp < 5) {
            return ImageMaster.INTENT_ATK_TIP_1;
        } else if (tmp < 10) {
            return ImageMaster.INTENT_ATK_TIP_2;
        } else if (tmp < 15) {
            return ImageMaster.INTENT_ATK_TIP_3;
        } else if (tmp < 20) {
            return ImageMaster.INTENT_ATK_TIP_4;
        } else if (tmp < 25) {
            return ImageMaster.INTENT_ATK_TIP_5;
        } else {
            return tmp < 30 ? ImageMaster.INTENT_ATK_TIP_6 : ImageMaster.INTENT_ATK_TIP_7;
        }
    }

    public void damage(DamageInfo info) {
        if (info.output > 0 && this.hasPower("IntangiblePlayer")) {
            info.output = 1;
        }

        int damageAmount = info.output;
        if (!this.isDying && !this.isEscaping) {
            if (damageAmount < 0) {
                damageAmount = 0;
            }

            boolean hadBlock = true;
            if (this.currentBlock == 0) {
                hadBlock = false;
            }

            boolean weakenedToZero = damageAmount == 0;
            damageAmount = this.decrementBlock(info, damageAmount);
            Iterator var5;
            AbstractRelic r;
            if (info.owner == AbstractDungeon.player) {
                for(var5 = AbstractDungeon.player.relics.iterator(); var5.hasNext(); damageAmount = r.onAttackToChangeDamage(info, damageAmount)) {
                    r = (AbstractRelic)var5.next();
                }
            }

            AbstractPower p;
            if (info.owner != null) {
                for(var5 = info.owner.powers.iterator(); var5.hasNext(); damageAmount = p.onAttackToChangeDamage(info, damageAmount)) {
                    p = (AbstractPower)var5.next();
                }
            }

            for(var5 = this.powers.iterator(); var5.hasNext(); damageAmount = p.onAttackedToChangeDamage(info, damageAmount)) {
                p = (AbstractPower)var5.next();
            }

            if (info.owner == AbstractDungeon.player) {
                var5 = AbstractDungeon.player.relics.iterator();

                while(var5.hasNext()) {
                    r = (AbstractRelic)var5.next();
                    r.onAttack(info, damageAmount, this);
                }
            }

            var5 = this.powers.iterator();

            while(var5.hasNext()) {
                p = (AbstractPower)var5.next();
                p.wasHPLost(info, damageAmount);
            }

            if (info.owner != null) {
                var5 = info.owner.powers.iterator();

                while(var5.hasNext()) {
                    p = (AbstractPower)var5.next();
                    p.onAttack(info, damageAmount, this);
                }
            }

            for(var5 = this.powers.iterator(); var5.hasNext(); damageAmount = p.onAttacked(info, damageAmount)) {
                p = (AbstractPower)var5.next();
            }

            this.lastDamageTaken = Math.min(damageAmount, this.currentHealth);
            boolean probablyInstantKill = this.currentHealth == 0;
            if (damageAmount > 0) {
                if (info.owner != this) {
                    this.useStaggerAnimation();
                }

                if (damageAmount >= 99 && !CardCrawlGame.overkill) {
                    CardCrawlGame.overkill = true;
                }

                this.currentHealth -= damageAmount;
                if (!probablyInstantKill) {
                    AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, damageAmount));
                }

                if (this.currentHealth < 0) {
                    this.currentHealth = 0;
                }

                this.healthBarUpdatedEvent();
            } else if (!probablyInstantKill) {
                if (weakenedToZero && this.currentBlock == 0) {
                    if (hadBlock) {
                        AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, TEXT[30]));
                    } else {
                        AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, 0));
                    }
                } else if (Settings.SHOW_DMG_BLOCK) {
                    AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, TEXT[30]));
                }
            }

            if (this.currentHealth <= 0) {
                this.die();
                if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.cleanCardQueue();
                    AbstractDungeon.effectList.add(new DeckPoofEffect(64.0F * Settings.scale, 64.0F * Settings.scale, true));
                    AbstractDungeon.effectList.add(new DeckPoofEffect((float)Settings.WIDTH - 64.0F * Settings.scale, 64.0F * Settings.scale, false));
                    AbstractDungeon.overlayMenu.hideCombatPanels();
                }

                if (this.currentBlock > 0) {
                    this.loseBlock();
                    AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0F + BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0F + BLOCK_ICON_Y));
                }
            }

        }
    }

    public void init() {
        this.rollMove();
        this.healthBarUpdatedEvent();
    }

    public abstract void takeTurn();

    public void render(SpriteBatch sb) {
        if (!this.isDead && !this.escaped) {
            if (this.atlas == null) {
                sb.setColor(this.tint.color);
                if (this.img != null) {
                    sb.draw(this.img, this.drawX - (float)this.img.getWidth() * Settings.scale / 2.0F + this.animX, this.drawY + this.animY, (float)this.img.getWidth() * Settings.scale, (float)this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);
                }
            } else {
                this.state.update(Gdx.graphics.getDeltaTime());
                this.state.apply(this.skeleton);
                this.skeleton.updateWorldTransform();
                this.skeleton.setPosition(this.drawX + this.animX, this.drawY + this.animY);
                this.skeleton.setColor(this.tint.color);
                this.skeleton.setFlip(this.flipHorizontal, this.flipVertical);
                sb.end();
                CardCrawlGame.psb.begin();
                sr.draw(CardCrawlGame.psb, this.skeleton);
                CardCrawlGame.psb.end();
                sb.begin();
                sb.setBlendFunction(770, 771);
            }

            //感觉是要的
//            if (this == AbstractDungeon.getCurrRoom().monsters.hoveredMonster && this.atlas == null) {
//                sb.setBlendFunction(770, 1);
//                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));
//                if (this.img != null) {
//                    sb.draw(this.img, this.drawX - (float)this.img.getWidth() * Settings.scale / 2.0F + this.animX, this.drawY + this.animY, (float)this.img.getWidth() * Settings.scale, (float)this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);
//                    sb.setBlendFunction(770, 771);
//                }
//            }

            if (!this.isDying && !this.isEscaping && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && !AbstractDungeon.player.isDead && !AbstractDungeon.player.hasRelic("Runic Dome") && this.intent != AbstractFriendlyMonster.Intent.NONE && !Settings.hideCombatElements) {
                this.renderIntentVfxBehind(sb);
                this.renderIntent(sb);
                this.renderIntentVfxAfter(sb);
                this.renderDamageRange(sb);
            }

            this.hb.render(sb);
            this.intentHb.render(sb);
            this.healthHb.render(sb);
        }

        if (!AbstractDungeon.player.isDead) {
            this.renderHealth(sb);
            this.renderName(sb);
        }

    }

    protected void setHp(int minHp, int maxHp) {
        this.currentHealth = AbstractDungeon.monsterHpRng.random(minHp, maxHp);
        if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
            float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
            this.currentHealth = (int)((float)this.currentHealth * mod);
        }

        if (ModHelper.isModEnabled("MonsterHunter")) {
            this.currentHealth = (int)((float)this.currentHealth * 1.5F);
        }

        this.maxHealth = this.currentHealth;
    }

    protected void setHp(int hp) {
        this.setHp(hp, hp);
    }

    private void renderDamageRange(SpriteBatch sb) {
        if (this.intent.name().contains("ATTACK")) {
            if (this.isMultiDmg) {
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.intentDmg) + "x" + Integer.toString(this.intentMultiAmt), this.intentHb.cX - 30.0F * Settings.scale, this.intentHb.cY + this.bobEffect.y - 12.0F * Settings.scale, this.intentColor);
            } else {
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.intentDmg), this.intentHb.cX - 30.0F * Settings.scale, this.intentHb.cY + this.bobEffect.y - 12.0F * Settings.scale, this.intentColor);
            }
        }

    }

    private void renderIntentVfxBehind(SpriteBatch sb) {
        Iterator var2 = this.intentVfx.iterator();

        while(var2.hasNext()) {
            AbstractGameEffect e = (AbstractGameEffect)var2.next();
            if (e.renderBehind) {
                e.render(sb);
            }
        }

    }

    private void renderIntentVfxAfter(SpriteBatch sb) {
        Iterator var2 = this.intentVfx.iterator();

        while(var2.hasNext()) {
            AbstractGameEffect e = (AbstractGameEffect)var2.next();
            if (!e.renderBehind) {
                e.render(sb);
            }
        }

    }

    private void renderName(SpriteBatch sb) {
        if (!this.hb.hovered) {
            this.hoverTimer = MathHelper.fadeLerpSnap(this.hoverTimer, 0.0F);
        } else {
            this.hoverTimer += Gdx.graphics.getDeltaTime();
        }

        if ((!AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.hoveredCard == null || AbstractDungeon.player.hoveredCard.target == CardTarget.ENEMY) && !this.isDying) {
            if (this.hoverTimer != 0.0F) {
                if (this.hoverTimer * 2.0F > 1.0F) {
                    this.nameColor.a = 1.0F;
                } else {
                    this.nameColor.a = this.hoverTimer * 2.0F;
                }
            } else {
                this.nameColor.a = MathHelper.slowColorLerpSnap(this.nameColor.a, 0.0F);
            }

            float tmp = Interpolation.exp5Out.apply(1.5F, 2.0F, this.hoverTimer);
            this.nameColor.r = Interpolation.fade.apply(Color.DARK_GRAY.r, Settings.CREAM_COLOR.r, this.hoverTimer * 10.0F);
            this.nameColor.g = Interpolation.fade.apply(Color.DARK_GRAY.g, Settings.CREAM_COLOR.g, this.hoverTimer * 3.0F);
            this.nameColor.b = Interpolation.fade.apply(Color.DARK_GRAY.b, Settings.CREAM_COLOR.b, this.hoverTimer * 3.0F);
            float y = Interpolation.exp10Out.apply(this.healthHb.cY, this.healthHb.cY - 8.0F * Settings.scale, this.nameColor.a);
            float x = this.hb.cX - this.animX;
            this.nameBgColor.a = this.nameColor.a / 2.0F * this.hbAlpha;
            sb.setColor(this.nameBgColor);
            TextureAtlas.AtlasRegion img = ImageMaster.MOVE_NAME_BG;
            sb.draw(img, x - (float)img.packedWidth / 2.0F, y - (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, Settings.scale * tmp, Settings.scale * 2.0F, 0.0F);
            Color var10000 = this.nameColor;
            var10000.a *= this.hbAlpha;
            FontHelper.renderFontCentered(sb, FontHelper.tipHeaderFont, this.name, x, y, this.nameColor);
        }

    }

    private void renderIntent(SpriteBatch sb) {
        this.intentColor.a = this.intentAlpha;
        sb.setColor(this.intentColor);
        if (this.intentBg != null) {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.intentAlpha / 2.0F));
            if (Settings.isMobile) {
                sb.draw(this.intentBg, this.intentHb.cX - 64.0F, this.intentHb.cY - 64.0F + this.bobEffect.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale * 1.2F, Settings.scale * 1.2F, 0.0F, 0, 0, 128, 128, false, false);
            } else {
                sb.draw(this.intentBg, this.intentHb.cX - 64.0F, this.intentHb.cY - 64.0F + this.bobEffect.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
            }
        }

        if (this.intentImg != null && this.intent != AbstractFriendlyMonster.Intent.UNKNOWN && this.intent != AbstractFriendlyMonster.Intent.STUN) {
            if (this.intent != AbstractFriendlyMonster.Intent.DEBUFF && this.intent != AbstractFriendlyMonster.Intent.STRONG_DEBUFF) {
                this.intentAngle = 0.0F;
            } else {
                this.intentAngle += Gdx.graphics.getDeltaTime() * 150.0F;
            }

            sb.setColor(this.intentColor);
            if (Settings.isMobile) {
                sb.draw(this.intentImg, this.intentHb.cX - 64.0F, this.intentHb.cY - 64.0F + this.bobEffect.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale * 1.2F, Settings.scale * 1.2F, this.intentAngle, 0, 0, 128, 128, false, false);
            } else {
                sb.draw(this.intentImg, this.intentHb.cX - 64.0F, this.intentHb.cY - 64.0F + this.bobEffect.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, this.intentAngle, 0, 0, 128, 128, false, false);
            }
        }

    }

    protected void updateHitbox(float hb_x, float hb_y, float hb_w, float hb_h) {
        this.hb_w = hb_w * Settings.scale;
        this.hb_h = hb_h * Settings.xScale;
        this.hb_y = hb_y * Settings.scale;
        this.hb_x = hb_x * Settings.scale;
        this.hb = new Hitbox(this.hb_w, this.hb_h);
        this.hb.move(this.drawX + this.hb_x + this.animX, this.drawY + this.hb_y + this.hb_h / 2.0F);
        this.healthHb.move(this.hb.cX, this.hb.cY - this.hb_h / 2.0F - this.healthHb.height / 2.0F);
        this.intentHb.move(this.hb.cX + this.intentOffsetX, this.hb.cY + this.hb_h / 2.0F + 32.0F * Settings.scale);
    }

    protected abstract void getMove(int var1);

    private void updateDeathAnimation() {
        if (this.isDying) {
            this.deathTimer -= Gdx.graphics.getDeltaTime();
            if (this.deathTimer < 1.8F && !this.tintFadeOutCalled) {
                this.tintFadeOutCalled = true;
                this.tint.fadeOut();
            }
        }

        if (this.deathTimer < 0.0F) {
            this.isDead = true;
            if (AbstractDungeon.getMonsters().areMonstersDead() && !AbstractDungeon.getCurrRoom().isBattleOver && !AbstractDungeon.getCurrRoom().cannotLose) {
                AbstractDungeon.getCurrRoom().endBattle();
            }

            this.dispose();
            this.powers.clear();
        }

    }

    public void dispose() {
        if (this.img != null) {
            logger.info("Disposed monster img asset");
            this.img.dispose();
            this.img = null;
        }

        Iterator var1 = this.disposables.iterator();

        while(var1.hasNext()) {
            Disposable d = (Disposable)var1.next();
            logger.info("Disposed extra monster assets");
            d.dispose();
        }

        if (this.atlas != null) {
            this.atlas.dispose();
            this.atlas = null;
            logger.info("Disposed Texture: " + this.name);
        }

    }

    private void updateEscapeAnimation() {
        if (this.escapeTimer != 0.0F) {
            this.flipHorizontal = true;
            this.escapeTimer -= Gdx.graphics.getDeltaTime();
            this.drawX += Gdx.graphics.getDeltaTime() * 400.0F * Settings.scale;
        }

        if (this.escapeTimer < 0.0F) {
            this.escaped = true;
            if (AbstractDungeon.getMonsters().areMonstersDead() && !AbstractDungeon.getCurrRoom().isBattleOver && !AbstractDungeon.getCurrRoom().cannotLose) {
                AbstractDungeon.getCurrRoom().endBattle();
            }
        }

    }

    public void escapeNext() {
        this.escapeNext = true;
    }

    public void deathReact() {
    }

    public void escape() {
        this.hideHealthBar();
        this.isEscaping = true;
        this.escapeTimer = 3.0F;
    }

    public void die() {
        this.die(true);
    }

    public void die(boolean triggerRelics) {
        if (!this.isDying) {
            this.isDying = true;
            Iterator var2;
            if (this.currentHealth <= 0 && triggerRelics) {
                var2 = this.powers.iterator();

                while(var2.hasNext()) {
                    AbstractPower p = (AbstractPower)var2.next();
                    p.onDeath();
                }
            }

//            if (triggerRelics) {
//                var2 = AbstractDungeon.player.relics.iterator();
//
//                while(var2.hasNext()) {
//                    AbstractRelic r = (AbstractRelic)var2.next();
//                    r.onMonsterDeath(this);
//                }
//            }

            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                AbstractDungeon.overlayMenu.endTurnButton.disable();
                var2 = AbstractDungeon.player.limbo.group.iterator();

                while(var2.hasNext()) {
                    AbstractCard c = (AbstractCard)var2.next();
                    AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                }

                AbstractDungeon.player.limbo.clear();
            }

            if (this.currentHealth < 0) {
                this.currentHealth = 0;
            }

            if (!Settings.FAST_MODE) {
                ++this.deathTimer;
            } else {
                ++this.deathTimer;
            }

            StatsScreen.incrementEnemySlain();
        }

    }

    public void usePreBattleAction() {
    }

//    public void useUniversalPreBattleAction() {
//        if (ModHelper.isModEnabled("Lethality")) {
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
//        }
//
//        Iterator var1 = AbstractDungeon.player.blights.iterator();
//
//        while(var1.hasNext()) {
//            AbstractBlight b = (AbstractBlight)var1.next();
//            b.onCreateEnemy(this);
//        }
//
//        if (ModHelper.isModEnabled("Time Dilation") && !this.id.equals("GiantHead")) {
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SlowPower(this, 0)));
//        }
//
//    }

    private void calculateDamage(int dmg) {
        AbstractPlayer target = AbstractDungeon.player;
        float tmp = (float)dmg;
        if (Settings.isEndless && AbstractDungeon.player.hasBlight("DeadlyEnemies")) {
            float mod = AbstractDungeon.player.getBlight("DeadlyEnemies").effectFloat();
            tmp *= mod;
        }

        AbstractPower p;
        Iterator var6;
        for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageGive(tmp, DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        for(var6 = target.powers.iterator(); var6.hasNext(); tmp = p.atDamageReceive(tmp, DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        tmp = AbstractDungeon.player.stance.atDamageReceive(tmp, DamageType.NORMAL);
        if (this.applyBackAttack()) {
            tmp = (float)((int)(tmp * 1.5F));
        }

        for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        for(var6 = target.powers.iterator(); var6.hasNext(); tmp = p.atDamageFinalReceive(tmp, DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        dmg = MathUtils.floor(tmp);
        if (dmg < 0) {
            dmg = 0;
        }

        this.intentDmg = dmg;
    }

    public void applyPowers() {
        boolean applyBackAttack = this.applyBackAttack();
        if (applyBackAttack && !this.hasPower("BackAttack")) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, (AbstractCreature)null, new BackAttackPower(this)));
        }

        Iterator var2 = this.damage.iterator();

        while(var2.hasNext()) {
            DamageInfo dmg = (DamageInfo)var2.next();
            dmg.applyPowers(this, AbstractDungeon.player);
            if (applyBackAttack) {
                dmg.output = (int)((float)dmg.output * 1.5F);
            }
        }

        if (this.move.baseDamage > -1) {
            this.calculateDamage(this.move.baseDamage);
        }

        this.intentImg = this.getIntentImg();
        this.updateIntentTip();
    }

    private boolean applyBackAttack() {
        return AbstractDungeon.player.hasPower("Surrounded") && (AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX < this.drawX || !AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX > this.drawX);
    }

    public void removeSurroundedPower() {
        if (this.hasPower("BackAttack")) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this, (AbstractCreature)null, "BackAttack"));
        }

    }

    public void changeState(String stateName) {
    }

    public void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    protected void onBossVictoryLogic() {
        if (Settings.FAST_MODE) {
            this.deathTimer += 0.7F;
        } else {
            ++this.deathTimer;
        }

        AbstractDungeon.scene.fadeInAmbiance();
        if (AbstractDungeon.getCurrRoom().event == null) {
            ++AbstractDungeon.bossCount;
            StatsScreen.incrementBossSlain();
            if (GameActionManager.turn <= 1) {
                UnlockTracker.unlockAchievement("YOU_ARE_NOTHING");
            }

            if (GameActionManager.damageReceivedThisCombat - GameActionManager.hpLossThisCombat <= 0) {
                UnlockTracker.unlockAchievement("PERFECT");
                ++CardCrawlGame.perfect;
            }
        }

        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        playBossStinger();
        Iterator var1 = AbstractDungeon.player.blights.iterator();

        while(var1.hasNext()) {
            AbstractBlight b = (AbstractBlight)var1.next();
            b.onBossDefeat();
        }

    }

    protected void onFinalBossVictoryLogic() {
        if ((AbstractDungeon.ascensionLevel < 20 || AbstractDungeon.bossList.size() != 2) && !Settings.isEndless) {
            if (!Settings.isFinalActAvailable || !Settings.hasRubyKey || !Settings.hasEmeraldKey || !Settings.hasSapphireKey) {
                CardCrawlGame.stopClock = true;
            }

            if (CardCrawlGame.playtime <= 1200.0F) {
                UnlockTracker.unlockAchievement("SPEED_CLIMBER");
            }

            if (AbstractDungeon.player.masterDeck.size() <= 5) {
                UnlockTracker.unlockAchievement("MINIMALIST");
            }

            boolean commonSenseUnlocked = true;
            Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

            label50: {
                AbstractCard c;
                do {
                    if (!var2.hasNext()) {
                        break label50;
                    }

                    c = (AbstractCard)var2.next();
                } while(c.rarity != CardRarity.UNCOMMON && c.rarity != CardRarity.RARE);

                commonSenseUnlocked = false;
            }

            if (commonSenseUnlocked) {
                UnlockTracker.unlockAchievement("COMMON_SENSE");
            }

            if (AbstractDungeon.player.relics.size() == 1) {
                UnlockTracker.unlockAchievement("ONE_RELIC");
            }

            if (Settings.isDailyRun && !Settings.seedSet) {
                UnlockTracker.unlockLuckyDay();
            }
        }

    }

    public static void playBossStinger() {
        // $FF: Couldn't be decompiled
    }

    public HashMap<String, Serializable> getLocStrings() {
        HashMap<String, Serializable> data = new HashMap();
        data.put("name", this.name);
        data.put("moves", MOVES);
        data.put("dialogs", DIALOG);
        return data;
    }

    public int getIntentDmg() {
        return this.intentDmg;
    }

    public int getIntentBaseDmg() {
        return this.intentBaseDmg;
    }

    public void setIntentBaseDmg(int amount) {
        this.intentBaseDmg = amount;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("AbstractMonster");
        TEXT = uiStrings.TEXT;
        INTENT_HB_W = 64.0F * Settings.scale;
        sortByHitbox = (o1, o2) -> {
            return (int)(o1.hb.cX - o2.hb.cX);
        };
    }

    public static enum EnemyType {
        NORMAL,
        ELITE,
        BOSS;

        private EnemyType() {
        }
    }

    public static enum Intent {
        ATTACK,
        ATTACK_BUFF,
        ATTACK_DEBUFF,
        ATTACK_DEFEND,
        BUFF,
        DEBUFF,
        STRONG_DEBUFF,
        DEBUG,
        DEFEND,
        DEFEND_DEBUFF,
        DEFEND_BUFF,
        ESCAPE,
        MAGIC,
        NONE,
        SLEEP,
        STUN,
        UNKNOWN;

        private Intent() {
        }
    }
}


