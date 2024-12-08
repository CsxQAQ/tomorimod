package tomorinmod.cards.basic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.monitor.CountUsedCardMonitor;
import tomorinmod.savedata.CraftingRecipes;
import tomorinmod.util.CardStats;

import java.util.ArrayList;

public class MusicComposition extends BaseCard {
    public static final String ID = makeID(MusicComposition.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );

    private boolean isFliped=false;
    public static ArrayList<String> cardsUsed=new ArrayList<>();

    public MusicComposition() {
        super(ID, info);
        this.exhaust=true;
        this.selfRetain = true;
        this.isInnate = true;

    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return this.isFliped;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(cardsUsed.size()==3){
            this.isFliped=true;
            String music=matchRecipe();
            if(music!=""){
                this.name=music;
            }else{
                this.name="失败的创作";
            }
        }
        updateDescription();
    }

    private void updateDescription() {
        if (!this.isFliped) {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        } else {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0]+this.cardsUsed.toString();
        }
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        cardsUsed.clear();
        p.hand.addToHand(this.makeCopy());

    }

    //recipeHashSet
    public String matchRecipe(){
        for(CraftingRecipes.Recipe recipe:CraftingRecipes.recipeHashSet){
            boolean matched=true;
            for(int i=0;i<3;i++){
                if(!cardMatch(cardsUsed.get(i),recipe.needs.get(i),recipe.levels.get(i))){
                    matched=false;
                    break;
                }
            }
            if(matched){
                return recipe.music;
            }
        }
        return "";
    }

    public boolean cardMatch(String card,String recipe,int rarity){
        CardRarity cardRarity= CardLibrary.getCard(card).rarity;
        int r=-1;
        if(cardRarity==CardRarity.BASIC||cardRarity==CardRarity.COMMON){
            r=1;
        }else if(cardRarity==CardRarity.UNCOMMON){
            r=2;
        }else if(cardRarity==CardRarity.RARE){
            r=3;
        }
        if(card.equals(makeID(recipe))&&r>=rarity){
            return true;
        }
        return false;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MusicComposition();
    }
}
