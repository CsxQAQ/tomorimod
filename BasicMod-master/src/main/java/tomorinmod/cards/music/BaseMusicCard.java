package tomorinmod.cards.music;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.customcards.TomorinApotheosis;
import tomorinmod.savedata.customdata.CraftingRecipes;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.CardStats;
import tomorinmod.util.CustomUtils;

import static tomorinmod.BasicMod.imagePath;

public abstract class BaseMusicCard extends BaseCard {
    public BaseMusicCard(String ID, CardStats info, NumsInfo numsInfo) {
        super(ID, info);
        tags.add(CustomTags.MUSIC);
        this.idForShow=ID;
        this.numsInfo = numsInfo;

        //只是为了正确显示
        setDamage(numsInfo.commonDamage, numsInfo.commonUpgDamage);
        setBlock(numsInfo.commonBlock, numsInfo.commonUpgBlock);
        setMagic(numsInfo.commonMagic, numsInfo.commonUpgMagic);
    }

    public BaseMusicCard(String ID, CardStats info) {
        super(ID, info);
        tags.add(CustomTags.MUSIC);
        this.idForShow=ID;
    }

    protected int musicUpgradeDamage;
    protected int musicUpgradeBlock;
    protected int musicUpgradeMagic;

    private String idForShow;

    private NumsInfo numsInfo;

    public MusicRarity musicRarity;


    public void setRarity(MusicRarity musicRarity) {
        this.musicRarity=musicRarity;

    }

    public void setRarityByCommond(int level) {
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

        dataInfoInitialize(); //暂时先放在这里
    }

    public static MusicRarity getMusicRarityByCost(String ID) {
        int cost = -1;
        MusicRarity musicRarity = null;
        if(CraftingRecipes.getInstance().musicsCostHashMap.isEmpty()){
            return null;
        }
        if (CraftingRecipes.getInstance().musicsCostHashMap.containsKey(CustomUtils.idToName(ID).toLowerCase())) {
            cost = CraftingRecipes.getInstance().musicsCostHashMap.get(CustomUtils.idToName(ID).toLowerCase());
        }

        if (cost >= CraftingRecipes.COMMONCOST_MIN && cost <= CraftingRecipes.COMMONCOST_MAX) {
            musicRarity = MusicRarity.COMMON;
        } else if (cost >= CraftingRecipes.UNCOMMONCOST_MIN && cost <= CraftingRecipes.UNCOMMONCOST_MAX) {
            musicRarity = MusicRarity.UNCOMMON;
        } else if (cost >= CraftingRecipes.RARECOST_MIN && cost <= CraftingRecipes.RARECOST_MAX) {
            musicRarity = MusicRarity.RARE;
        }
        return musicRarity;
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
        if(this.musicRarity!=null){
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

    public void dataInfoInitialize() {
        if(musicRarity==null||numsInfo==null){
            return;
        }
        switch (musicRarity) {
            case COMMON:
                setDamage(numsInfo.commonDamage, numsInfo.commonUpgDamage);
                setBlock(numsInfo.commonBlock, numsInfo.commonUpgBlock);
                setMagic(numsInfo.commonMagic, numsInfo.commonUpgMagic);
                musicUpgradeDamage = numsInfo.commonUpgDamage;
                musicUpgradeBlock = numsInfo.commonUpgBlock;
                musicUpgradeMagic = numsInfo.commonUpgMagic;

                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(idForShow).DESCRIPTION;
                break;
            case UNCOMMON:
                setDamage(numsInfo.unCommonDamage, numsInfo.unCommonUpgDamage);
                setBlock(numsInfo.unCommonBlock, numsInfo.unCommonUpgBlock);
                setMagic(numsInfo.unCommonMagic, numsInfo.unCommonUpgMagic);
                musicUpgradeDamage = numsInfo.unCommonUpgDamage;
                musicUpgradeBlock = numsInfo.unCommonUpgBlock;
                musicUpgradeMagic = numsInfo.unCommonUpgMagic;

                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(idForShow).DESCRIPTION;
                break;
            case RARE:
                setDamage(numsInfo.rareDamage, numsInfo.rareUpgDamage);
                setBlock(numsInfo.rareBlock, numsInfo.rareUpgBlock);
                setMagic(numsInfo.rareMagic, numsInfo.rareUpgMagic);
                musicUpgradeDamage = numsInfo.rareUpgDamage;
                musicUpgradeBlock = numsInfo.rareUpgBlock;
                musicUpgradeMagic = numsInfo.rareUpgMagic;

                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(idForShow).EXTENDED_DESCRIPTION[0];
                break;
            default:
                throw new IllegalArgumentException("Invalid rarity: " + musicRarity);
        }
        initializeDescription();
    }
}

class NumsInfo {
    int commonDamage;
    int commonUpgDamage;
    int unCommonDamage;
    int unCommonUpgDamage;
    int rareDamage;
    int rareUpgDamage;

    int commonBlock;
    int commonUpgBlock;
    int unCommonBlock;
    int unCommonUpgBlock;
    int rareBlock;
    int rareUpgBlock;

    int commonMagic;
    int commonUpgMagic;
    int unCommonMagic;
    int unCommonUpgMagic;
    int rareMagic;
    int rareUpgMagic;

    public NumsInfo(
            int commonDamage, int commonUpgDamage, int unCommonDamage, int unCommonUpgDamage, int rareDamage, int rareUpgDamage,
            int commonBlock, int commonUpgBlock, int unCommonBlock, int unCommonUpgBlock, int rareBlock, int rareUpgBlock,
            int commonMagic, int commonUpgMagic, int unCommonMagic, int unCommonUpgMagic, int rareMagic, int rareUpgMagic) {

        // 初始化damage相关的成员
        this.commonDamage = commonDamage;
        this.commonUpgDamage = commonUpgDamage;
        this.unCommonDamage = unCommonDamage;
        this.unCommonUpgDamage = unCommonUpgDamage;
        this.rareDamage = rareDamage;
        this.rareUpgDamage = rareUpgDamage;

        // 初始化block相关的成员
        this.commonBlock = commonBlock;
        this.commonUpgBlock = commonUpgBlock;
        this.unCommonBlock = unCommonBlock;
        this.unCommonUpgBlock = unCommonUpgBlock;
        this.rareBlock = rareBlock;
        this.rareUpgBlock = rareUpgBlock;

        // 初始化magic相关的成员
        this.commonMagic = commonMagic;
        this.commonUpgMagic = commonUpgMagic;
        this.unCommonMagic = unCommonMagic;
        this.unCommonUpgMagic = unCommonUpgMagic;
        this.rareMagic = rareMagic;
        this.rareUpgMagic = rareUpgMagic;
    }
}


