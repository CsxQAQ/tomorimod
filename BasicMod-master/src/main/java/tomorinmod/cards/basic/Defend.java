package tomorinmod.cards.basic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

import static tomorinmod.BasicMod.imagePath;

public class Defend extends BaseCard {
    public static final String ID = makeID(Defend.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public Defend() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);

        tags.add(CardTags.STARTER_DEFEND);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Defend();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb); // 调用原本的渲染逻辑
        renderCustomIcon(sb); // 添加你自己的渲染逻辑
    }

    private static final Texture FLOWER_ICON = new Texture(imagePath("powers/Shine.png"));

    private void renderCustomIcon(SpriteBatch sb) {
        sb.setColor(Color.WHITE); // 设置颜色

        // 计算图标的宽度和高度（根据卡牌的缩放比例动态调整）
        float iconSize = this.drawScale * 32.0f;

        // 计算图标的位置（根据卡牌中心点和缩放比例动态调整）
        float iconX = this.current_x + this.hb.width * 0.4f; // 卡牌右上角的 X 坐标，0.4f 是偏移系数
        float iconY = this.current_y + this.hb.height * 0.4f; // 卡牌右上角的 Y 坐标

        // 渲染图标
        sb.draw(
                FLOWER_ICON,
                iconX,       // 动态 X 坐标
                iconY,       // 动态 Y 坐标
                iconSize,    // 动态宽度
                iconSize     // 动态高度
        );
    }
}
