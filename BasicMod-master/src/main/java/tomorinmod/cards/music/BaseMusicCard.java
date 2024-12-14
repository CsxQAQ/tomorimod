package tomorinmod.cards.music;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.TomorinApotheosis;
import tomorinmod.savedata.CraftingRecipes;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.CardStats;
import tomorinmod.util.CustomUtils;

import static tomorinmod.BasicMod.imagePath;

public abstract class BaseMusicCard extends BaseCard {
    public BaseMusicCard(String ID, CardStats info) {
        super(ID, info);
        idForShow=ID;
        tags.add(CustomTags.MUSIC);
        initializeBannerRarity(ID);

    }

    public void setRarity(int level) {
        switch (level){
            case 1:
                this.musicRarity=MusicRarity.COMMON;
                break;
            case 2:
                this.musicRarity=MusicRarity.UNCOMMON;
                break;
            case 3:
                this.musicRarity=MusicRarity.RARE;
                break;
            default:
                this.musicRarity=MusicRarity.COMMON;
        }
    }

    public enum MusicRarity {
        COMMON(3),
        UNCOMMON(2),
        RARE(1);

        private final int value;

        MusicRarity(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    protected int musicUpgradeDamage;
    protected int musicUpgradeBlock;
    protected int musicUpgradeMagic;

    private String idForShow;

    public MusicRarity musicRarity;

    public void initializeBannerRarity(String ID) {
        int cost = -1;
        if(CraftingRecipes.getInstance().musicsCostHashMap.isEmpty()){
            return;
        }
        if (CraftingRecipes.getInstance().musicsCostHashMap.containsKey(CustomUtils.idToName(ID).toLowerCase())) {
            cost = CraftingRecipes.getInstance().musicsCostHashMap.get(CustomUtils.idToName(ID).toLowerCase());
        }

        if (cost >= CraftingRecipes.COMMONCOST_MIN && cost <= CraftingRecipes.COMMONCOST_MAX) {
            musicRarity = MusicRarity.COMMON;
            setBannerTexture(imagePath("banners/banner_common_512.png"), imagePath("banners/banner_common_1024.png"));
        } else if (cost >= CraftingRecipes.UNCOMMONCOST_MIN && cost <= CraftingRecipes.UNCOMMONCOST_MAX) {
            musicRarity = MusicRarity.UNCOMMON;
            setBannerTexture(imagePath("banners/banner_uncommon_512.png"), imagePath("banners/banner_uncommon_1024.png"));
        } else if (cost >= CraftingRecipes.RARECOST_MIN && cost <= CraftingRecipes.RARECOST_MAX) {
            musicRarity = MusicRarity.RARE;
            setBannerTexture(imagePath("banners/banner_rare_512.png"), imagePath("banners/banner_rare_1024.png"));
        }
    }

    public void setBanner(){
        if(musicRarity==null){
            return;
        }
        switch (musicRarity){
            case COMMON:
                setBannerTexture(imagePath("banners/banner_common_512.png"), imagePath("banners/banner_common_1024.png"));
                break;
            case UNCOMMON:
                setBannerTexture(imagePath("banners/banner_uncommon_512.png"), imagePath("banners/banner_uncommon_1024.png"));
                break;
            case RARE:
                setBannerTexture(imagePath("banners/banner_rare_512.png"), imagePath("banners/banner_rare_1024.png"));
                break;
            default:
                setBannerTexture(imagePath("banners/banner_common_512.png"), imagePath("banners/banner_common_1024.png"));
                break;
        }
    }

    @Override
    public void update() {
        super.update();
        if (idForShow != null && musicRarity != null) {
            String newDescription = null;
            switch (musicRarity) {
                case COMMON:
                case UNCOMMON:
                    newDescription = CardCrawlGame.languagePack.getCardStrings(idForShow).DESCRIPTION;
                    break;
                case RARE:
                    newDescription = CardCrawlGame.languagePack.getCardStrings(idForShow).EXTENDED_DESCRIPTION[0];
                    break;
            }
            if (newDescription != null && !newDescription.equals(this.rawDescription)) {
                this.rawDescription = newDescription;
                initializeDescription();
            }
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy(){
        AbstractCard card= super.makeStatEquivalentCopy();
        BaseMusicCard musicCard=(BaseMusicCard)card;

        //初始化在cardLibrary中的卡牌不会有musicRarity，而有些方法会在cardLibrary中复制，所以要重置一遍musicCard
        //会在cardLibrary中复制的行为：
        //1. 游戏开始时masterDeck加载
        //2. MakeTempCardInHandAction，控制台hand add用到这个方法
        if(this.musicRarity==null){
            //musicCard.initializeBannerRarity(musicCard.cardID);
        }else{
            musicCard.musicRarity=this.musicRarity;
            musicCard.setBanner();
        }
        musicCard.idForShow=this.idForShow;
        return musicCard;

    }

    @Override
    public void upgrade() {
        if(TomorinApotheosis.isTomorinApotheosisUsed){
            this.upgradeDamage(musicUpgradeDamage);
            this.upgradeBlock(musicUpgradeBlock);
            this.upgradeMagicNumber(musicUpgradeMagic);

            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }else{
            if(!this.upgraded){
                this.upgradeDamage(musicUpgradeDamage);
                this.upgradeBlock(musicUpgradeBlock);
                this.upgradeMagicNumber(musicUpgradeMagic);
                upgradeName();
            }
        }
    }

    @Override
    public void setMaterialAndLevel() {

    }

}
