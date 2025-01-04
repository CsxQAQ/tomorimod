package tomorimod.cards.music;

import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import tomorimod.TomoriMod;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.savedata.customdata.CraftingRecipes;
import tomorimod.util.CardStats;
import tomorimod.util.CustomUtils;

import static tomorimod.TomoriMod.imagePath;

public abstract class BaseMusicCard extends BaseCard implements WithoutMaterial {

    //protected int musicUpgradeDamage;
    //protected int musicUpgradeBlock;
    //protected int musicUpgradeMagic;

    public String idForShow;

    public NumsInfo numsInfo;

    public MusicRarity musicRarity;

    public BaseMusicCard(String ID, CardStats info, NumsInfo numsInfo) {
        super(ID, info);
        //tags.add(CustomTags.MUSIC);
        this.idForShow=ID;
        this.numsInfo = numsInfo;
        musicRarity=MusicRarity.DEFAULT;

        setBackgroundTexture(imagePath("character/specialcardback/music_cardback.png"),
                imagePath("character/specialcardback/music_cardback_p.png"));

        this.damageTypeForTurn = MusicDamageInfo.DamageType.NORMAL;
        this.damageType = MusicDamageInfo.DamageType.NORMAL;

        //只是为了正确显示
//        setDamage(numsInfo.commonDamage, numsInfo.commonUpgDamage);
//        setBlock(numsInfo.commonBlock, numsInfo.commonUpgBlock);
//        setMagic(numsInfo.commonMagic, numsInfo.commonUpgMagic);
        dataInfoInitialize();
        updateDescription();
    }

    public BaseMusicCard(String ID, CardStats info) {
        super(ID, info);
        this.idForShow=ID;
    }

    public void setMusicRarity(MusicRarity musicRarity) {
        this.musicRarity=musicRarity;
        setDisplayRarity(rarity); //调用setMusicRarity似乎一定会调用setDisPlayRarity，所以就放过来了
        dataInfoInitialize();  //理论上不用吧，因为构造方法里调用了
    }

    public void setMusicRarityByCommond(int level) {
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
                this.musicRarity=MusicRarity.DEFAULT;
        }
        setDisplayRarity(rarity);
    }

    public static MusicRarity getMusicRarityByCost(String ID) {
        int cost = -1;
        MusicRarity musicRarity = null;
        if(CraftingRecipes.getInstance().musicsCostHashMap.isEmpty()){
            return null;
        }
        if (CraftingRecipes.getInstance().musicsCostHashMap.containsKey(CustomUtils.idToName(ID))) {
            cost = CraftingRecipes.getInstance().musicsCostHashMap.get(CustomUtils.idToName(ID));
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

    public void updateDescription(){
        if (idForShow != null && musicRarity != null) {
            String newDescription = null;
            switch (musicRarity) {
                case COMMON:
                case DEFAULT:
                case UNCOMMON:
                    newDescription = CardCrawlGame.languagePack.getCardStrings(idForShow).DESCRIPTION;
                    break;
                case RARE:
                    newDescription = CardCrawlGame.languagePack.getCardStrings(idForShow).EXTENDED_DESCRIPTION[0];
                    break;
            }
            this.rawDescription = newDescription;
        }
        if (this.upgradesDescription)
        {
            if (cardStrings.UPGRADE_DESCRIPTION == null)
            {
                TomoriMod.logger.error("Card " + cardID + " upgrades description and has null upgrade description.");
            }
            else
            {
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION; //应该是这个方法导致更新变绿
            }
        }
        initializeDescription();
    }


    @Override
    public BaseMusicCard makeStatEquivalentCopy(){
        BaseCard card= super.makeStatEquivalentCopy();
        BaseMusicCard musicCard=(BaseMusicCard)card;
        //musicCard.musicRarity=getMusicRarityByCost(musicCard.cardID);
        if(CardCrawlGame.mode== CardCrawlGame.GameMode.CHAR_SELECT){
            musicCard.musicRarity=MusicRarity.DEFAULT;
        }else{
            if(this.musicRarity==MusicRarity.DEFAULT){
                if(getMusicRarityByCost(musicCard.cardID)!=null){
                    musicCard.musicRarity=getMusicRarityByCost(musicCard.cardID);
                }
            }else{
                musicCard.musicRarity=this.musicRarity;
            }
        }
        musicCard.dataInfoInitialize();
        musicCard.upgraded=false;
        for(int i = 0; i < this.timesUpgraded; ++i) {
            musicCard.upgrade();
        }

        musicCard.setDisplayRarity(rarity);
        musicCard.idForShow=this.idForShow;
        musicCard.updateDescription();
        return musicCard;
    }

    @Override
    public void upgrade() {
        if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasPower(makeID("TomoriApotheosisPower"))) {
//            this.upgradeDamage(musicUpgradeDamage);
//            this.upgradeBlock(musicUpgradeBlock);
//            this.upgradeMagicNumber(musicUpgradeMagic);
            this.upgradeDamage(damageUpgrade);
            this.upgradeBlock(blockUpgrade);
            this.upgradeMagicNumber(magicUpgrade);
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }else{
            if (!this.upgraded) {
//                this.upgradeDamage(musicUpgradeDamage);
//                this.upgradeBlock(musicUpgradeBlock);
//                this.upgradeMagicNumber(musicUpgradeMagic);
                this.upgradeDamage(damageUpgrade);
                this.upgradeBlock(blockUpgrade);
                this.upgradeMagicNumber(magicUpgrade);
                upgradeName();
            }
        }

        updateDescription();
    }



    public enum MusicRarity {
        COMMON(3),
        UNCOMMON(2),
        RARE(1),
        DEFAULT(0);

        public final int value;

        MusicRarity(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public void dataInfoInitialize() { //这东西只能在构造方法里调用，因为会重置baseDamage
        if(musicRarity==null||numsInfo==null){
            return;
        }
        switch (musicRarity) {
            case COMMON:
            case DEFAULT:
                setDamage(numsInfo.commonDamage, numsInfo.commonUpgDamage);
                setBlock(numsInfo.commonBlock, numsInfo.commonUpgBlock);
                setMagic(numsInfo.commonMagic, numsInfo.commonUpgMagic);
//                musicUpgradeDamage = numsInfo.commonUpgDamage;
//                musicUpgradeBlock = numsInfo.commonUpgBlock;
//                musicUpgradeMagic = numsInfo.commonUpgMagic;

                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(idForShow).DESCRIPTION;
                break;
            case UNCOMMON:
                setDamage(numsInfo.unCommonDamage, numsInfo.unCommonUpgDamage);
                setBlock(numsInfo.unCommonBlock, numsInfo.unCommonUpgBlock);
                setMagic(numsInfo.unCommonMagic, numsInfo.unCommonUpgMagic);
//                musicUpgradeDamage = numsInfo.unCommonUpgDamage;
//                musicUpgradeBlock = numsInfo.unCommonUpgBlock;
//                musicUpgradeMagic = numsInfo.unCommonUpgMagic;

                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(idForShow).DESCRIPTION;
                break;
            case RARE:
                setDamage(numsInfo.rareDamage, numsInfo.rareUpgDamage);
                setBlock(numsInfo.rareBlock, numsInfo.rareUpgBlock);
                setMagic(numsInfo.rareMagic, numsInfo.rareUpgMagic);
//                musicUpgradeDamage = numsInfo.rareUpgDamage;
//                musicUpgradeBlock = numsInfo.rareUpgBlock;
//                musicUpgradeMagic = numsInfo.rareUpgMagic;

                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(idForShow).EXTENDED_DESCRIPTION[0];
                break;
            default:
                throw new IllegalArgumentException("Invalid rarity: " + musicRarity);
        }
        initializeDescription();
    }

    @Override
    public void setDisplayRarity(AbstractCard.CardRarity rarity){ //没用的参数，但得继承
        super.setDisplayRarity(rarity);
        if(rarity.equals(CardRarity.SPECIAL)){
            if(musicRarity!=null){
                switch (musicRarity){
                    case COMMON:
                    case DEFAULT:
                        this.bannerSmallRegion = ImageMaster.CARD_BANNER_COMMON;
                        this.bannerLargeRegion = ImageMaster.CARD_BANNER_COMMON_L;
                        switch (this.type) {
                            case ATTACK:
                                this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_COMMON;
                                this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_COMMON_L;
                                break;
                            case POWER:
                                this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_COMMON;
                                this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_COMMON_L;
                                break;
                            default:
                                this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_COMMON;
                                this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_COMMON_L;
                        }

                        this.frameMiddleRegion = ImageMaster.CARD_COMMON_FRAME_MID;
                        this.frameLeftRegion = ImageMaster.CARD_COMMON_FRAME_LEFT;
                        this.frameRightRegion = ImageMaster.CARD_COMMON_FRAME_RIGHT;
                        this.frameMiddleLargeRegion = ImageMaster.CARD_COMMON_FRAME_MID_L;
                        this.frameLeftLargeRegion = ImageMaster.CARD_COMMON_FRAME_LEFT_L;
                        this.frameRightLargeRegion = ImageMaster.CARD_COMMON_FRAME_RIGHT_L;
                        break;
                    case UNCOMMON:
                        this.bannerSmallRegion = ImageMaster.CARD_BANNER_UNCOMMON;
                        this.bannerLargeRegion = ImageMaster.CARD_BANNER_UNCOMMON_L;
                        switch (this.type) {
                            case ATTACK:
                                this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_UNCOMMON;
                                this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_UNCOMMON_L;
                                break;
                            case POWER:
                                this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_UNCOMMON;
                                this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_UNCOMMON_L;
                                break;
                            default:
                                this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_UNCOMMON;
                                this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_UNCOMMON_L;
                        }

                        this.frameMiddleRegion = ImageMaster.CARD_UNCOMMON_FRAME_MID;
                        this.frameLeftRegion = ImageMaster.CARD_UNCOMMON_FRAME_LEFT;
                        this.frameRightRegion = ImageMaster.CARD_UNCOMMON_FRAME_RIGHT;
                        this.frameMiddleLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_MID_L;
                        this.frameLeftLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_LEFT_L;
                        this.frameRightLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_RIGHT_L;
                        break;
                    case RARE:
                        this.bannerSmallRegion = ImageMaster.CARD_BANNER_RARE;
                        this.bannerLargeRegion = ImageMaster.CARD_BANNER_RARE_L;
                        switch (this.type) {
                            case ATTACK:
                                this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_RARE;
                                this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_RARE_L;
                                break;
                            case POWER:
                                this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_RARE;
                                this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_RARE_L;
                                break;
                            default:
                                this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_RARE;
                                this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_RARE_L;
                        }

                        this.frameMiddleRegion = ImageMaster.CARD_RARE_FRAME_MID;
                        this.frameLeftRegion = ImageMaster.CARD_RARE_FRAME_LEFT;
                        this.frameRightRegion = ImageMaster.CARD_RARE_FRAME_RIGHT;
                        this.frameMiddleLargeRegion = ImageMaster.CARD_RARE_FRAME_MID_L;
                        this.frameLeftLargeRegion = ImageMaster.CARD_RARE_FRAME_LEFT_L;
                        this.frameRightLargeRegion = ImageMaster.CARD_RARE_FRAME_RIGHT_L;
                        break;
                }
            }
        }
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


