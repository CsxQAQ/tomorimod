package tomorinmod.screens;

import basemod.interfaces.ScreenPostProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.core.Settings;

import static tomorinmod.BasicMod.imagePath;

public class FrameUi implements Renderable{

    private static final Texture TextureFrame = new Texture(imagePath("materials/" + "frame.png"));

    private static final float SIZE=80.0F;
    private static final float X0FFSET=138.0F;
    private static final float Y0FFSET=400.0F;
    private static final float INTERVAL=200.0F;

    private FrameUi(){
    }

    private static FrameUi instance;

    public static FrameUi getInstance() {
        if (instance == null) {
            instance = new FrameUi();
        }
        return instance;

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(TextureFrame, X0FFSET*Settings.scale, Y0FFSET*Settings.scale,SIZE,SIZE);
        sb.draw(TextureFrame, X0FFSET*Settings.scale, (Y0FFSET+INTERVAL)*Settings.scale,SIZE,SIZE);
        sb.draw(TextureFrame, X0FFSET*Settings.scale, (Y0FFSET+2*INTERVAL)*Settings.scale,SIZE,SIZE);
    }
}
