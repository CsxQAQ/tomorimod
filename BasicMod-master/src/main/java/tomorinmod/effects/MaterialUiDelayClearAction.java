package tomorinmod.effects;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import tomorinmod.screens.FrameUi;
import tomorinmod.screens.MaterialScreenProcessor;
import tomorinmod.screens.MaterialUi;

public class MaterialUiDelayClearAction extends AbstractGameAction {

    public MaterialUiDelayClearAction(){
        duration=1.0f;
    }

    @Override
    public void update() {

        tickDuration();

        if (isDone) {  // 当时间耗尽时执行清理操作
            MaterialUi.getInstance().clear();

        }
    }

}
