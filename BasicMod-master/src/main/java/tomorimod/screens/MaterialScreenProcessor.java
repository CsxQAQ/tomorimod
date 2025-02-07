package tomorimod.screens;

import basemod.interfaces.ScreenPostProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.patches.SingleRelicViewPopupPatch;
import tomorimod.ui.FrameUi;
import tomorimod.ui.MaterialUi;

import java.util.ArrayList;

public class MaterialScreenProcessor implements ScreenPostProcessor {

    private static MaterialScreenProcessor instance;
    ArrayList<Renderable> renderables=new ArrayList<>();

    private MaterialScreenProcessor(){
        renderables.add(FrameUi.getInstance());
        renderables.add(MaterialUi.getInstance());
    }

    public static MaterialScreenProcessor getInstance() {
        if (instance == null) {
            instance = new MaterialScreenProcessor();
        }
        return instance;

    }

    @Override
    public void postProcess(SpriteBatch sb, TextureRegion textureRegion, OrthographicCamera camera) {
        sb.draw(textureRegion, 0, 0);
        sb.setColor(Color.WHITE);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // 支持透明度

        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.NONE && !SingleRelicViewPopupPatch.isRelicWindowOpened){
            for (Renderable item : renderables) {
                item.render(sb);
            }
        }
    }


}
