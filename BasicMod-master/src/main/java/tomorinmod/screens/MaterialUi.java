package tomorinmod.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

import static tomorinmod.BasicMod.imagePath;

public class MaterialUi implements Renderable{

    private static final Texture TextureStone = new Texture(imagePath("materials/" + "stone.png"));
    private static final Texture TextureBand = new Texture(imagePath("materials/" + "band.png"));
    private static final Texture TextureWatermelonworm = new Texture(imagePath("materials/" + "watermelonworm.png"));

    ArrayList<String> materials=new ArrayList<>();

    private static final float SIZE = 100.0F;
    private static final float X_OFFSET = 115.0F;
    private static final float Y_OFFSET = 400.0F;
    private static final float INTERVAL = 200.0F;

    private MaterialUi(){
    }

    private static MaterialUi instance;

    public static MaterialUi getInstance() {
        if (instance == null) {
            instance = new MaterialUi();
        }
        return instance;

    }

    public void setMaterial(String material){
        materials.add(material);
    }

    public Texture getMaterialTexture(String s){
        switch (s){
            case "stone":
                return TextureStone;
            case "band":
                return TextureBand;
            case "watermelonworm":
                return TextureWatermelonworm;
        }
        return null;
    }

    @Override
    public void render(SpriteBatch sb) {
        float yoffset=Y_OFFSET;
        for(int i=0;i<materials.size();i++){
            sb.draw(getMaterialTexture(materials.get(i)), X_OFFSET*Settings.scale,
                    yoffset*Settings.scale,SIZE*Settings.scale,SIZE*Settings.scale);
            yoffset=yoffset+INTERVAL;
        }
    }

    public void clear(){
        materials.clear();
    }
}
