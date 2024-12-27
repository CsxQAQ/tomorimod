package tomorimod.effects;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import tomorimod.ui.MaterialUi;

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
