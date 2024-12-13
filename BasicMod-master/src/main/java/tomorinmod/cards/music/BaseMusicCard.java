package tomorinmod.cards.music;

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
        tags.add(CustomTags.MUSIC);
        initializeBannerRarity(ID);

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

    protected MusicRarity musicRarity;

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
