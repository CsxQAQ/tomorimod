package tomorinmod.cards.special;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.MemoryInCrychicPower;
import tomorinmod.powers.WeAreMygoPower;
import tomorinmod.savedata.CraftingRecipes;
import tomorinmod.savedata.HistoryCraftRecords;
import tomorinmod.savedata.SaveMusicDiscoverd;
import tomorinmod.savedata.SavePermanentForm;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.AddTagsUtils;
import tomorinmod.util.CardStats;

import java.util.ArrayList;
import java.util.Iterator;

public class Chunriying extends BaseCard {
    public static final String ID = makeID(Chunriying.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    public static boolean isIntensify=false;

    public Chunriying() {
        super(ID, info);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            addToBot(new StunMonsterAction(monster, p, 1));
            if(isIntensify){
                int damage = monster.currentHealth / 2;
                addToBot(new DamageAction(monster, new DamageInfo(p, damage, DamageInfo.DamageType.HP_LOSS),
                        AbstractGameAction.AttackEffect.NONE));
            }
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Chunriying();
    }

    @Override
    public void setMaterialAndLevel() {

    }
}
