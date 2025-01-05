package tomorimod.monsters.uika;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ExhaustCardDelayEffect extends AbstractGameEffect {

    private AbstractCard card;
    //private float delay;
    public ExhaustCardDelayEffect(AbstractCard card,float delay){
        this.card=card;
        duration=delay;
    }

    @Override
    public void update() {
        if(duration>0){
            duration-= Gdx.graphics.getDeltaTime();
        }else{
            card.current_x = 99999.0F;
            card.current_y = 99999.0F;
            card.target_x  = 99999.0F;
            card.target_y  = 99999.0F;
            isDone=true;
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
