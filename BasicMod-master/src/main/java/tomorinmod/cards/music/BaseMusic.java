package tomorinmod.cards.music;

import tomorinmod.cards.BaseCard;
import tomorinmod.cards.rare.TomorinApotheosis;
import tomorinmod.util.CardStats;

public abstract class BaseMusic extends BaseCard {
    public BaseMusic(String ID, CardStats info) {
        super(ID, info);
    }

    protected int musicUpgradeDamage;
    protected int musicUpgradeMagicNumber;

    @Override
    public void upgrade() {
        if(TomorinApotheosis.isTomorinApotheosisUsed){
            this.upgradeDamage(musicUpgradeDamage);
            this.upgradeMagicNumber(musicUpgradeMagicNumber);
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

//        musicsCostHashMap.put("chunriying", rareCost);
//        musicsCostHashMap.put("shichaoban", rareCost);
//        musicsCostHashMap.put("mixingjiao", rareCost);
//        musicsCostHashMap.put("lunfuyu", rareCost);
//        musicsCostHashMap.put("yingsewu", rareCost);
//        musicsCostHashMap.put("yinyihui", uncommonCost);
//        musicsCostHashMap.put("miluri", uncommonCost);
//        musicsCostHashMap.put("wulushi", uncommonCost);
//        musicsCostHashMap.put("bitianbanzou", uncommonCost);
//        musicsCostHashMap.put("yinakong", commonCost);
//        musicsCostHashMap.put("mingwusheng", commonCost);
//        musicsCostHashMap.put("qianzaibiaoming", commonCost);
}
