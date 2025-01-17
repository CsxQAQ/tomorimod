package tomorimod.character;

import basemod.BaseMod;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import tomorimod.cards.basic.Defend;
import tomorimod.cards.basic.MusicComposition;
import tomorimod.cards.basic.Strike;
import tomorimod.cards.basic.Upset;
import tomorimod.monsters.mutsumi.MutsumiMonster;
import tomorimod.monsters.sakishadow.SakiShadowRightPatch;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.ImmunityPower;
import tomorimod.relics.MicrophoneRelic;
import tomorimod.relics.NotebookRelic;
import tomorimod.relics.VocalMicrophoneRelic;
import tomorimod.util.MonsterUtils;
import tomorimod.vfx.StarDustEffect;

import java.util.ArrayList;
import java.util.Iterator;

import static tomorimod.TomoriMod.*;

public class Tomori extends CustomPlayer {

    //Stats
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 80;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    //Strings
    private static final String ID = makeID("Tomori"); //This should match whatever you have in the CharacterStrings.json file
    private static String[] getNames() { return CardCrawlGame.languagePack.getCharacterString(ID).NAMES; }
    private static String[] getText() { return CardCrawlGame.languagePack.getCharacterString(ID).TEXT; }

    //This static class is necessary to avoid certain quirks of Java classloading when registering the character.
    public static class Meta {
        //These are used to identify your character, as well as your character's card color.
        //Library color is basically the same as card color, but you need both because that's how the game was made.
        @SpireEnum
        public static PlayerClass TOMORI;
        @SpireEnum(name = "TOMORI_BLUE") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "TOMORI_BLUE") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;

        //Character select images
        private static final String CHAR_SELECT_BUTTON = characterPath("select/button.png");
        private static final String CHAR_SELECT_PORTRAIT = characterPath("select/tomori_1920.png");

        //Character card images
        private static final String BG_ATTACK = characterPath("cardback/bg_attack.png");
        private static final String BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
        private static final String BG_SKILL = characterPath("cardback/bg_skill.png");
        private static final String BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
        private static final String BG_POWER = characterPath("cardback/bg_power.png");
        private static final String BG_POWER_P = characterPath("cardback/bg_power_p.png");
        private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
        private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
        private static final String SMALL_ORB = characterPath("cardback/small_orb.png");

        //This is used to color *some* images, but NOT the actual cards. For that, edit the images in the cardback folder!
        private static final Color cardColor = new Color(128f/255f, 128f/255f, 128f/255f, 1f);

        //Methods that will be used in the main mod file
        public static void registerColor() {
            BaseMod.addColor(CARD_COLOR, cardColor,
                    BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                    BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                    SMALL_ORB);
        }

        public static void registerCharacter() {
            BaseMod.addCharacter(new Tomori(), CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT);
        }
    }

    public static class MetaMusic {
        //These are used to identify your character, as well as your character's card color.
        //Library color is basically the same as card color, but you need both because that's how the game was made.
        @SpireEnum
        public static PlayerClass TOMORIMUSIC;

        @SpireEnum(name = "音乐牌") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor CARD_COLOR;

        @SpireEnum(name = "音乐牌")
        public static CardLibrary.LibraryType LIBRARY_COLOR;

        //Character select images
        private static final String CHAR_SELECT_BUTTON = characterPath("select/button.png");
        private static final String CHAR_SELECT_PORTRAIT = characterPath("select/tomori_1920.png");

        //Character card images
        private static final String BG_ATTACK = characterPath("cardback/bg_attack.png");
        private static final String BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
        private static final String BG_SKILL = characterPath("cardback/bg_skill.png");
        private static final String BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
        private static final String BG_POWER = characterPath("cardback/bg_power.png");
        private static final String BG_POWER_P = characterPath("cardback/bg_power_p.png");
        private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
        private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
        private static final String SMALL_ORB = characterPath("cardback/small_orb.png");

        //This is used to color *some* images, but NOT the actual cards. For that, edit the images in the cardback folder!
        private static final Color cardColor = new Color(128f/255f, 128f/255f, 128f/255f, 1f);

        //Methods that will be used in the main mod file
        public static void registerColor() {
            BaseMod.addColor(CARD_COLOR, cardColor,
                    BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                    BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                    SMALL_ORB);
        }
    }


    //In-game images
    private static final String SHOULDER_1 = characterPath("shoulder.png"); //Shoulder 1 and 2 are used at rest sites.
    private static final String SHOULDER_2 = characterPath("shoulder2.png");
    private static final String CORPSE = characterPath("tomori/tomori_die.png"); //Corpse is when you die.

    //Textures used for the energy orb
    private static final String[] orbTextures = {
            characterPath("energyorb/layer1.png"), //When you have energy
            characterPath("energyorb/layer2.png"),
            characterPath("energyorb/layer3.png"),
            characterPath("energyorb/layer4.png"),
            characterPath("energyorb/layer5.png"),
            characterPath("energyorb/cover.png"), //"container"
            characterPath("energyorb/layer1d.png"), //When you don't have energy
            characterPath("energyorb/layer2d.png"),
            characterPath("energyorb/layer3d.png"),
            characterPath("energyorb/layer4d.png"),
            characterPath("energyorb/layer5d.png")
    };

    //Speeds at which each layer of the energy orb texture rotates. Negative is backwards.
    private static final float[] layerSpeeds = new float[] {
            -20.0F,
            20.0F,
            -40.0F,
            40.0F,
            360.0F
    };


    //Actual character class code below this point

    public Tomori() {


        super(getNames()[0], Meta.TOMORI,
                new CustomEnergyOrb(orbTextures, characterPath("energyorb/vfx.png"), layerSpeeds), //Energy Orb
                new AbstractAnimation() { //Change the Animation line to this
                    @Override
                    public Type type() {
                        return Type.NONE; //A NONE animation results in the image given in initializeClass being used
                    }
                });

        initializeClass(characterPath("tomori/tomori_alive.png"), //The image to use. The rest of the method is unchanged.
                SHOULDER_2,
                SHOULDER_1,
                CORPSE,
                getLoadout(),
                20.0F, -20.0F, 200.0F, 250.0F, //Character hitbox. x y position, then width and height.
                new EnergyManager(ENERGY_PER_TURN));

        //Location for text bubbles. You can adjust it as necessary later. For most characters, these values are fine.
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);

        //intangibleEffect=new IntangibleEffect(this);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        //List of IDs of cards for your starting deck.
        //If you want multiple of the same card, you have to add it multiple times.
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(MusicComposition.ID);
        retVal.add(Upset.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        //IDs of starting relics. You can have multiple, but one is recommended.
        retVal.add(VocalMicrophoneRelic.ID);
        retVal.add(NotebookRelic.ID);
        UnlockTracker.markRelicAsSeen(MicrophoneRelic.ID);


        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        //This card is used for the Gremlin card matching game.
        //It should be a non-strike non-defend starter card, but it doesn't have to be.
        return new Strike_Red();
    }

    /*- Below this is methods that you should *probably* adjust, but don't have to. -*/

    @Override
    public int getAscensionMaxHPLoss() {
        return 4; //Max hp reduction at ascension 14+
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        //These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    private final Color cardRenderColor = Color.LIGHT_GRAY.cpy(); //Used for some vfx on moving cards (sometimes) (maybe)
    private final Color cardTrailColor = Color.LIGHT_GRAY.cpy(); //Used for card trail vfx during gameplay.
    private final Color slashAttackColor = Color.LIGHT_GRAY.cpy(); //Used for a screen tint effect when you attack the heart.



    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor;
    }

    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        //Font used to display your current energy.
        //energyNumFontRed, Blue, Green, and Purple are used by the basegame characters.
        //It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontRed;
    }



    @Override
    public void doCharSelectScreenSelectEffect() {
        //This occurs when you click the character's button in the character select screen.
        //See SoundMaster for a full list of existing sound effects, or look at BaseMod's wiki for adding custom audio.
        CardCrawlGame.sound.playA("ATTACK_DAGGER_2", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        //Similar to doCharSelectScreenSelectEffect, but used for the Custom mode screen. No shaking.
        return "ATTACK_DAGGER_2";
    }

    //Don't adjust these four directly, adjust the contents of the CharacterStrings.json file.
    @Override
    public String getLocalizedCharacterName() {
        return getNames()[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return getNames()[1];
    }
    @Override
    public String getSpireHeartText() {
        return getText()[1];
    }
    @Override
    public String getVampireText() {
        return getText()[2]; //Generally, the only difference in this text is how the vampires refer to the player.
    }

    /*- You shouldn't need to edit any of the following methods. -*/

    //This is used to display the character's information on the character selection screen.
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(getNames()[0], getText()[0],
                MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Meta.CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        //Makes a new instance of your character class.
        return new Tomori();
    }

    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(imagePath("character/ed/page1.png")));
        panels.add(new CutscenePanel(imagePath("character/ed/page2.png")));
        panels.add(new CutscenePanel(imagePath("character/ed/page3.png")));
        return panels;
    }


    @Override
    public void damage(DamageInfo info) {
        //可以在这里判断伤害来源，让白祥的攻击免疫护甲和无实体
        int damageAmount = info.output;
        boolean hadBlock = true;

        if (this.currentBlock == 0) {
            hadBlock = false;
        }

        if (damageAmount < 0) {
            damageAmount = 0;
        }

        /////

        if (damageAmount > 1 && hasPower("IntangiblePlayer")) {
            if(!MutsumiMonster.isMutsumi()){
                damageAmount = 1;
            }
        }

        /////

        /////

        if(!MutsumiMonster.isMutsumi()){
            damageAmount = decrementBlock(info, damageAmount);
        }

        //////

        if (info.owner == this) {
            for (AbstractRelic r : this.relics) {
                damageAmount = r.onAttackToChangeDamage(info, damageAmount);
            }
        }
        if (info.owner != null) {
            for (AbstractPower p : info.owner.powers) {
                damageAmount = p.onAttackToChangeDamage(info, damageAmount);
            }
        }
        for (AbstractRelic r : this.relics) {
            damageAmount = r.onAttackedToChangeDamage(info, damageAmount);
        }
        for (AbstractPower p : this.powers) {
            damageAmount = p.onAttackedToChangeDamage(info, damageAmount);
        }

        if (info.owner == this) {
            for (AbstractRelic r : this.relics) {
                r.onAttack(info, damageAmount, this);
            }
        }

        if (info.owner != null) {
            for (AbstractPower p : info.owner.powers) {
                p.onAttack(info, damageAmount, this);
            }
            for (AbstractPower p : this.powers) {
                damageAmount = p.onAttacked(info, damageAmount);
            }
            for (AbstractRelic r : this.relics) {
                damageAmount = r.onAttacked(info, damageAmount);
            }
        }

        for (AbstractRelic r : this.relics) {
            damageAmount = r.onLoseHpLast(damageAmount);
        }

        this.lastDamageTaken = Math.min(damageAmount, this.currentHealth);

        if (damageAmount > 0) {
            for (AbstractPower p : this.powers) {
                damageAmount = p.onLoseHp(damageAmount);
            }

            for (AbstractRelic r : this.relics) {
                r.onLoseHp(damageAmount);
            }

            for (AbstractPower p : this.powers) {
                p.wasHPLost(info, damageAmount);
            }

            for (AbstractRelic r : this.relics) {
                r.wasHPLost(damageAmount);
            }

            if (info.owner != null) {
                for (AbstractPower p : info.owner.powers) {
                    p.onInflictDamage(info, damageAmount, this);
                }
            }

            if (info.owner != this) {
                useStaggerAnimation();
            }

            if (info.type == DamageInfo.DamageType.HP_LOSS) {
                GameActionManager.hpLossThisCombat += damageAmount;
            }

            GameActionManager.damageReceivedThisTurn += damageAmount;
            GameActionManager.damageReceivedThisCombat += damageAmount;

            this.currentHealth -= damageAmount;



            if (damageAmount > 0 && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                    Iterator var1 = this.hand.group.iterator();

                    AbstractCard c;
                    while(var1.hasNext()) {
                        c = (AbstractCard)var1.next();
                        c.tookDamage();
                    }

                    var1 = this.discardPile.group.iterator();

                    while(var1.hasNext()) {
                        c = (AbstractCard)var1.next();
                        c.tookDamage();
                    }

                    var1 = this.drawPile.group.iterator();

                    while(var1.hasNext()) {
                        c = (AbstractCard)var1.next();
                        c.tookDamage();
                    }
                }
                this.damagedThisCombat++;
            }

            AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, damageAmount));

            if (this.currentHealth < 0) {
                this.currentHealth = 0;
            } else if (this.currentHealth < this.maxHealth / 4) {
                AbstractDungeon.topLevelEffects.add(new BorderFlashEffect(new Color(1.0F, 0.1F, 0.05F, 0.0F)));
            }

            healthBarUpdatedEvent();

            if (this.currentHealth <= this.maxHealth / 2.0F && !this.isBloodied) {
                this.isBloodied = true;
                for (AbstractRelic r : this.relics) {
                    if (r != null) {
                        r.onBloodied();
                    }
                }
            }

            if (this.currentHealth < 1) {
                if (!hasRelic("Mark of the Bloom")) {
                    if (hasPotion("FairyPotion")) {
                        for (AbstractPotion p : this.potions) {
                            if (p.ID.equals("FairyPotion")) {
                                p.flash();
                                this.currentHealth = 0;
                                p.use(this);
                                AbstractDungeon.topPanel.destroyPotion(p.slot);
                                return;
                            }
                        }
                    } else if (hasRelic("Lizard Tail")) {
                        if (((LizardTail) getRelic("Lizard Tail")).counter == -1) {
                            this.currentHealth = 0;
                            getRelic("Lizard Tail").onTrigger();
                            return;
                        }

                        ////////

                    } else if(hasPower(makeID("StarDustPower"))){
                        getPower(makeID("StarDustPower")).flash();
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new StarDustEffect(this.hb.cX, this.hb.cY), 1.0F));
                        //AbstractDungeon.effectsQueue.add(new StarDustEffect(this.hb.cX, this.hb.cY));

                        // 2. 触发加血
                        AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, 1));
                        //currentHealth=1;
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this,this,makeID("StarDustPower")));
                        AbstractDungeon.actionManager.addToBottom
                                (new ApplyPowerAction(this,this,new ImmunityPower(this,2),2));
                        return;
                    }else if(hasPower(makeID("ImmunityPower"))){
                        getPower(makeID("ImmunityPower")).flash();
                        AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, 1));
                        return;
                    }

                    /////////

                }
                this.isDead = true;
                AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
                this.currentHealth = 0;
                if (this.currentBlock > 0) {
                    loseBlock();
                    AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0F + BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0F + BLOCK_ICON_Y));
                }
            }
        } else if (this.currentBlock > 0) {
            AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, uiStrings.TEXT[0]));
        } else if (hadBlock) {
            AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, uiStrings.TEXT[0]));
            AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0F + BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0F + BLOCK_ICON_Y));
        } else {
            AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, 0));
        }

        ///////

        SakiShadowRightPatch.applyAfterDamage();

        ///////
    }

//    else if(hasPower(makeID("StarDustPower"))){
//        getPower(makeID("StarDustPower")).flash();
//        AbstractDungeon.actionManager.addToBottom(new VFXAction(new StarDustEffect(this.hb.cX, this.hb.cY), 1.0F));
//        //AbstractDungeon.effectsQueue.add(new StarDustEffect(this.hb.cX, this.hb.cY));
//
//        // 2. 触发加血
//        AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, 1));
//        //currentHealth=1;
//        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this,this,makeID("StarDustPower")));
//        AbstractDungeon.actionManager.addToBottom
//                (new ApplyPowerAction(this,this,new ImmunityPower(this,2),2));
//        return;
//    }else if(hasPower(makeID("ImmunityPower"))){
//        getPower(makeID("ImmunityPower")).flash();
//        AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, 1));
//        return;
//    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        if(MonsterUtils.getMonster(makeID("UikaMonster"))!=null){
            AbstractMonster monster=MonsterUtils.getMonster(makeID("UikaMonster"));
            if(monster instanceof UikaMonster){
                ((UikaMonster)monster).uikaWarningUi.setFrozen();
            }
        }
        if(MonsterUtils.getMonster(makeID("MutsumiMonster"))!=null){
            AbstractMonster monster=MonsterUtils.getMonster(makeID("MutsumiMonster"));
            if(monster instanceof MutsumiMonster){
                ((MutsumiMonster)monster).mutsumiWarningUi.setFrozen();
                ((MutsumiMonster)monster).soyoWarningUi.setFrozen();
            }
        }


    }
}
