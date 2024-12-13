package tomorinmod.cards.music;

import tomorinmod.cards.BaseCard;
import tomorinmod.cards.TomorinApotheosis;
import tomorinmod.savedata.CraftingRecipes;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.CardStats;
import tomorinmod.util.CustomUtils;

import static tomorinmod.BasicMod.imagePath;

public abstract class BaseMusic extends BaseCard {
    public BaseMusic(String ID, CardStats info) {
        super(ID, info);
        tags.add(CustomTags.MUSIC);
        initializeBannerRarity(ID);
    }

    protected int musicUpgradeBlock;
    protected int musicUpgradeDamage;
    protected int musicUpgradeMagicNumber=0;

    public void initializeBannerRarity(String ID){
        int cost=-1;
        if(CraftingRecipes.getInstance().musicsCostHashMap.containsKey(CustomUtils.idToName(ID).toLowerCase())){
            cost=CraftingRecipes.getInstance().musicsCostHashMap.get(CustomUtils.idToName(ID).toLowerCase());
        }
        switch (cost){
            case CraftingRecipes.commonCost:
                setBannerTexture(imagePath("banners/banner_common_512.png"),imagePath("banners/banner_common_1024.png"));
                break;
            case CraftingRecipes.uncommonCost:
                setBannerTexture(imagePath("banners/banner_uncommon_512.png"),imagePath("banners/banner_uncommon_1024.png"));
                break;
            case  CraftingRecipes.rareCost:
                setBannerTexture(imagePath("banners/banner_rare_512.png"),imagePath("banners/banner_rare_1024.png"));
                break;
            default:
                break;
        }
    }

    @Override
    public void upgrade() {
        if(TomorinApotheosis.isTomorinApotheosisUsed){
            this.upgradeDamage(musicUpgradeDamage);
            this.upgradeMagicNumber(musicUpgradeMagicNumber);
            this.upgradeBlock(musicUpgradeBlock);
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }else{
            if(!this.upgraded){
                this.upgradeDamage(musicUpgradeDamage);
                this.upgradeMagicNumber(musicUpgradeMagicNumber);
                upgradeName();
            }
        }
    }
    @Override
    public void setMaterialAndLevel() {

    }

}
