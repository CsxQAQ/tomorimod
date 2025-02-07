package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.cardactions.LastGentalAction;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.util.CardStats;
import tomorimod.util.MonsterUtils;

import java.lang.reflect.Field;

import static tomorimod.TomoriMod.imagePath;

public class UikaTwoMoon extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaTwoMoon.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC=2;
    public final static int UPG_MAGIC=1;


    public UikaTwoMoon() {
        super(ID, info);
        this.setMagic(MAGIC,UPG_MAGIC);
        setBackgroundTexture(imagePath("character/specialcardback/mujica_cardback.png"),
                imagePath("character/specialcardback/mujica_cardback_p.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LastGentalAction(p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaTwoMoon();
    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int shineAmount= MonsterUtils.getPowerNum(uikaMonster,makeID("ShinePower"));
                int intentDamage = getPublicField(uikaMonster, "intentDmg", Integer.class);
                addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(uikaMonster,
                        intentDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToTop(new GainBlockAction(uikaMonster,shineAmount*magicNumber));
                isDone=true;
            }
        });
        UikaMonster.isTwoMoon=false;
        super.uikaUse(uikaMonster);
    }

    public static <T> T getPublicField(Object instance, String fieldName, Class<T> fieldType) {
        try {
            Field field = AbstractMonster.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return fieldType.cast(field.get(instance));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
