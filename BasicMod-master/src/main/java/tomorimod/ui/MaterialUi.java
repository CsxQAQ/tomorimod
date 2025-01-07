package tomorimod.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import tomorimod.savedata.customdata.CraftingRecipes;
import tomorimod.screens.Renderable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static tomorimod.TomoriMod.imagePath;

public class MaterialUi implements Renderable {

    private static final Map<CraftingRecipes.Material, Map<Integer, Texture>> TEXTURE_MAP = new HashMap<>();

    static {
        // 初始化纹理映射
        for (CraftingRecipes.Material material : CraftingRecipes.Material.values()) {
            Map<Integer, String> levelToPath = new HashMap<>();
            switch (material) {
                case YELLOW:
                case GREEN:
                case RED:
                    levelToPath.put(1, "materials/card/" + material.name().toLowerCase() + "_common.png");
                    levelToPath.put(2, "materials/card/" + material.name().toLowerCase() + "_uncommon.png");
                    levelToPath.put(3, "materials/card/" + material.name().toLowerCase() + "_rare.png");
                    break;
                case AQUARIUMPASS:
                    levelToPath.put(2, "materials/card/aquariumpass_uncommon.png");
                    levelToPath.put(3, "materials/card/aquariumpass_rare.png");
                    break;
            }

            Map<Integer, Texture> levelToTexture = new HashMap<>();
            for (Map.Entry<Integer, String> entry : levelToPath.entrySet()) {
                levelToTexture.put(entry.getKey(), new Texture(imagePath(entry.getValue())));
            }
            TEXTURE_MAP.put(material, levelToTexture);
        }
    }

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

    public void setMaterial(CraftingRecipes.Material material, int level){
        materials.add(new MaterialInfo(material,level));
    }

    private Texture getMaterialTexture(CraftingRecipes.Material material, int level) {
        Map<Integer, Texture> levelMap = TEXTURE_MAP.get(material);
        if (levelMap != null) {
            return levelMap.get(level);
        }
        return null;
    }

    @Override
    public void render(SpriteBatch sb) {
        float yoffset=Y_OFFSET;
        for(int i=0;i<materials.size();i++){
            sb.draw(getMaterialTexture(materials.get(i).name,materials.get(i).level), X_OFFSET*Settings.scale,
                    yoffset*Settings.scale,SIZE*Settings.scale,SIZE*Settings.scale);
            yoffset=yoffset+INTERVAL;
        }
    }

    public void clear(){
        materials.clear();
    }

    public class MaterialInfo {
        private final CraftingRecipes.Material name;
        private final int level;

        public MaterialInfo(CraftingRecipes.Material name, int level) {
            this.name = name;
            this.level = level;
        }

        public CraftingRecipes.Material getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }
    }
}
