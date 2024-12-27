package tomorinmod.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import tomorinmod.screens.Renderable;

import java.util.ArrayList;

import static tomorinmod.BasicMod.imagePath;

public class MaterialUi implements Renderable {

    private static final Texture TextureYellowCommon = new Texture(imagePath("materials/card/yellow_common.png"));
    private static final Texture TextureGreenCommon = new Texture(imagePath("materials/card/green_common.png"));
    private static final Texture TextureRedCommon = new Texture(imagePath("materials/card/red_common.png"));
    private static final Texture TextureYellowUncommon = new Texture(imagePath("materials/card/yellow_uncommon.png"));
    private static final Texture TextureGreenUncommon = new Texture(imagePath("materials/card/green_uncommon.png"));
    private static final Texture TextureRedUncommon = new Texture(imagePath("materials/card/red_uncommon.png"));
    private static final Texture TextureYellowRare = new Texture(imagePath("materials/card/yellow_rare.png"));
    private static final Texture TextureBandRare = new Texture(imagePath("materials/card/green_rare.png"));
    private static final Texture TextureRedRare = new Texture(imagePath("materials/card/red_rare.png"));

    ArrayList<MaterialInfo> materials=new ArrayList<>();

    private static final float SIZE = 100.0F;
    private static final float X_OFFSET = FrameUi.X_OFFSET+(FrameUi.SIZE-SIZE)/2;
    private static final float Y_OFFSET = FrameUi.Y_OFFSET+(FrameUi.SIZE-SIZE)/2;
    private static final float INTERVAL = FrameUi.INTERVAL;

    private MaterialUi(){
    }

    private static MaterialUi instance;

    public static MaterialUi getInstance() {
        if (instance == null) {
            instance = new MaterialUi();
        }
        return instance;

    }

    public void setMaterial(String material,int level){
        materials.add(new MaterialInfo(material,level));
    }

    public Texture getMaterialTexture(MaterialInfo info){
        switch (info.getName()) {
            case "yellow":
                if (info.getLevel() == 1) {
                    return TextureYellowCommon;
                } else if (info.getLevel() == 2) {
                    return TextureYellowUncommon;
                } else if (info.getLevel() == 3) {
                    return TextureYellowRare;
                }
                break;

            case "green":
                if (info.getLevel() == 1) {
                    return TextureGreenCommon;
                } else if (info.getLevel() == 2) {
                    return TextureGreenUncommon;
                } else if (info.getLevel() == 3) {
                    return TextureBandRare;
                }
                break;

            case "red":
                if (info.getLevel() == 1) {
                    return TextureRedCommon;
                } else if (info.getLevel() == 2) {
                    return TextureRedUncommon;
                } else if (info.getLevel() == 3) {
                    return TextureRedRare;
                }
                break;
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

    public class MaterialInfo {
        private final String name;
        private final int level;

        public MaterialInfo(String name, int level) {
            this.name = name;
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }
    }
}
