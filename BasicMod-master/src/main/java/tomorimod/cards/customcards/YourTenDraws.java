//package tomorimod.cards.customcards;
//
//import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import tomorimod.cards.BaseCard;
//import tomorimod.cards.music.BaseMusicCard;
//import tomorimod.cards.music.Chunriying;
//import tomorimod.cards.notshow.SpecialCard;
//import tomorimod.cards.permanentforms.PermanentFrom;
//import tomorimod.character.Tomori;
//import tomorimod.util.CardStats;
//import tomorimod.util.CustomUtils;
//
//import java.util.ArrayList;
//
//public class YourTenDraws extends BaseCard {
//
//    public static final String ID = makeID(YourTenDraws.class.getSimpleName());
//    public static final CardStats info = new CardStats(
//            Tomori.Meta.CARD_COLOR,
//            CardType.SKILL,
//            CardRarity.RARE,
//            CardTarget.SELF,
//            1
//    );
//
//
//    public YourTenDraws() {
//        super(ID, info);
//        exhaust=true;
//    }
//
//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        ArrayList<BaseCard> modCards= new ArrayList<>(CustomUtils.modCardGroup.values());
//        for(int i=0;i<10;i++){
//            int randomResult = AbstractDungeon.miscRng.random(modCards.size()-1);
//            while(modCards.get(randomResult) instanceof SpecialCard||modCards.get(randomResult) instanceof PermanentFrom){
//                randomResult= AbstractDungeon.miscRng.random(modCards.size()-1);
//            }
//            BaseCard card=modCards.get(randomResult).makeStatEquivalentCopy();
//            if(card instanceof BaseMusicCard){
//                int randomRarity = AbstractDungeon.miscRng.random(2);
//                switch (randomRarity){
//                    case 0:
//                        ((BaseMusicCard)card).setMusicRarity(BaseMusicCard.MusicRarity.COMMON);
//                        break;
//                    case 1:
//                        ((BaseMusicCard)card).setMusicRarity(BaseMusicCard.MusicRarity.UNCOMMON);
//                        break;
//                    case 2:
//                        ((BaseMusicCard)card).setMusicRarity(BaseMusicCard.MusicRarity.RARE);
//                        break;
//                }
//                if(card instanceof Chunriying){
//                    ((BaseMusicCard)card).setMusicRarity(BaseMusicCard.MusicRarity.RARE);
//                }
//                card.setDisplayRarity(card.rarity);
//            }
//            if(YourTenDraws.this.upgraded){
//                card.upgrade();
//            }
//            addToBot(new MakeTempCardInHandAction(card, 1));
//        }
//    }
//
//    public void updateDescription() {
//
//        if(upgraded){
//            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
//        }else{
//            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
//        }
//        initializeDescription();
//    }
//
//    @Override
//    public void upgrade() {
//        if (!upgraded) {
//            upgradeName();
//            this.updateDescription();
//        }
//    }
//
//    @Override
//    public AbstractCard makeCopy() {
//        return new YourTenDraws();
//    }
//}
