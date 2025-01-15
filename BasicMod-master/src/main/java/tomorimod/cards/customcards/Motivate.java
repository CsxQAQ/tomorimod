package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.savedata.customdata.CraftingRecipes;
import tomorimod.savedata.customdata.SaveMusicDiscoverd;
import tomorimod.util.CardStats;

public class Motivate extends BaseCard {
    public static final String ID = makeID(Motivate.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    public static final int DAMAGE=7;
    public static final int UPG_DAMAGE=4;


    public Motivate() {
        super(ID, info);
        setDamage(DAMAGE,UPG_DAMAGE);
    }

    @Override
    public void updateDescription(){
        if(CardCrawlGame.mode!= CardCrawlGame.GameMode.CHAR_SELECT){
            rawDescription= CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION+
                    "（额外攻击："+ calculateAttackNum()+"）";
        }
        initializeDescription();
    }

    public int calculateAttackNum(){
        CraftingRecipes.Material currentMaterial = AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(this);

        int consecutiveSameMaterialCount = 0;
        for (int i = AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1; i >= 0; i--) {
            AbstractCard card = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i);
            CraftingRecipes.Material materialOfPlayedCard = AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card);

            if (materialOfPlayedCard == currentMaterial||materialOfPlayedCard.equals(CraftingRecipes.Material.AQUARIUMPASS)) {
                consecutiveSameMaterialCount++;
            } else {
                break;
            }
        }
        return consecutiveSameMaterialCount;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < calculateAttackNum(); i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void update(){
        super.update();
        updateDescription();
    }

    @Override
    public void triggerOnGlowCheck() {
        if (glowCheck()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            super.triggerOnGlowCheck();
            //this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    private boolean glowCheck(){
        if(AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()){
            return false;
        }
        if(AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(AbstractDungeon.actionManager.cardsPlayedThisCombat
                .get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1))
                == AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(this)||
        AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(AbstractDungeon.actionManager.cardsPlayedThisCombat
                .get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1))== CraftingRecipes.Material.AQUARIUMPASS){
            return true;
        }
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Motivate();
    }

}
