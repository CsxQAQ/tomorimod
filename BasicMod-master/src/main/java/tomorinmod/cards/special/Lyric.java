package tomorinmod.cards.special;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.savedata.CraftingRecipes;
import tomorinmod.savedata.HistoryCraftRecords;
import tomorinmod.savedata.SaveMusicDiscoverd;
import tomorinmod.util.CardStats;

import java.util.ArrayList;

public class Lyric extends BaseCard {
    public static final String ID = makeID(Lyric.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public boolean isCardFliped;
    public static final ArrayList<String> cardsUsed = new ArrayList<>(3);
    private boolean added = false;

    public Lyric() {
        super(ID, info);
        initializeMusicComposition();
        this.selfRetain = true;
    }

    public void initializeMusicComposition() {
        isCardFliped = false;
        added = false;
        cardsUsed.clear();
        updateDescription();
        this.name = "歌词";
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return isCardFliped && !"失败的创作".equals(this.name);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (!added && cardsUsed.size() == 3) {
            added = true;
            isCardFliped = true;

            // 配方匹配
            String music = matchRecipe();

            // 记录配方
            ArrayList<String> records = new ArrayList<>(4);
            for (String card : cardsUsed) {
                AbstractCard cardObject = CardLibrary.getCard(card);
                if(cardObject instanceof BaseCard){
                    BaseCard baseCard=(BaseCard)cardObject;
                    records.add(CraftingRecipes.getInstance().cardMaterialHashMap.get(baseCard.cardID));
                }
            }
            records.add(music);
            HistoryCraftRecords.getInstance().historyCraftRecords.add(records);

            // 设置卡牌名称
            if (!"fail".equals(music)) {
                this.name = music;
                SaveMusicDiscoverd.getInstance().musicDiscovered.add(music);
            } else {
                this.name = "失败的创作";
            }

            updateDescription();
        }
    }

    private void updateDescription() {
        String newDescription;
        if (!this.isCardFliped) {
            newDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        } else {
            newDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }

        if (!newDescription.equals(this.rawDescription)) { // 避免重复设置
            this.rawDescription = newDescription;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        cardsUsed.clear();
        p.hand.addToHand(this.makeCopy());
    }

    private String matchRecipe() {
        for (CraftingRecipes.Recipe recipe : CraftingRecipes.getInstance().recipeArrayList) {
            if (isRecipeMatched(recipe)) {
                return recipe.music;
            }
        }
        return "fail";
    }

    private boolean isRecipeMatched(CraftingRecipes.Recipe recipe) {
        for (int i = 0; i < 3; i++) {
            if (!cardMatch(cardsUsed.get(i), recipe.needs.get(i), recipe.levels.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean cardMatch(String card, String recipe, int rarity) {
        AbstractCard cardObject = CardLibrary.getCard(card);
        if (cardObject == null) return false;

        if(cardObject instanceof BaseCard){
            BaseCard baseCard=(BaseCard) cardObject;
            return card.equals(makeID(recipe)) && baseCard.level >= rarity;
        }
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lyric();
    }

    @Override
    public void setMaterialAndLevel() {
        // Empty implementation, if unused consider removing.
    }
}
