package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.notshow.HuifutuRetainCards;
import tomorimod.cards.notshow.HuifutuRetainEnergy;
import tomorimod.powers.RetainCardsPower;
import tomorimod.powers.RetainEnergyPower;
import tomorimod.util.CardStats;

import java.util.ArrayList;

public class Huifutu extends BaseMusicCard {
    public static final String ID = makeID(Huifutu.class.getSimpleName());
    public static final CardStats info = new CardStats(
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Huifutu() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
    }


    public final static int DAMAGE_COMMON = 10;
    public final static int UPG_DAMAGE_COMMON = 4;
    public final static int BLOCK_COMMON = 0;
    public final static int UPG_BLOCK_COMMON = 0;
    public final static int MAGIC_COMMON = 0;
    public final static int UPG_MAGIC_COMMON = 0;

    public final static int DAMAGE_UNCOMMON = 15;
    public final static int UPG_DAMAGE_UNCOMMON = 6;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 0;
    public final static int UPG_MAGIC_UNCOMMON = 0;

    public final static int DAMAGE_RARE = 15;
    public final static int UPG_DAMAGE_RARE = 6;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 0;
    public final static int UPG_MAGIC_RARE = 0;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, this.damageType),
                AbstractGameAction.AttackEffect.SLASH_VERTICAL));

        if(musicRarity.equals(MusicRarity.RARE)){
            addToBot(new ApplyPowerAction(p,p,new RetainEnergyPower(p)));
            addToBot(new ApplyPowerAction(p,p,new RetainCardsPower(p)));
        }else{
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    ArrayList<AbstractCard> cardGroup=new ArrayList<>();
                    cardGroup.add(new HuifutuRetainCards());
                    cardGroup.add(new HuifutuRetainEnergy());

                    addToBot(new ChooseOneAction(cardGroup));
                    isDone = true;
                }
            });
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Huifutu();
    }


}
