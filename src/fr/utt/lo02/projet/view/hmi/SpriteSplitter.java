package fr.utt.lo02.projet.view.hmi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static fr.utt.lo02.projet.view.hmi.SwingHmiView.*;

public class SpriteSplitter
{
    public java.util.List<Image> splitSprite()
    {
        int[][] spriteSheetCoords = {{0, 0, 32, 32}, {32, 0, 32, 32}, {64, 0, 32, 32},
                {0, 32, 32, 32}, {32, 32, 32, 32}, {64, 32, 32, 32},
                {0, 64, 32, 32}, {32, 64, 32, 32}, {64, 64, 32, 32},

                {0, 96, 32, 32}, {32, 96, 32, 32}, {64, 96, 32, 32},
                {0, 128, 32, 32}, {32, 128, 32, 32}, {64, 128, 32, 32},
                {0, 160, 32, 32}, {32, 160, 32, 32}, {64, 160, 32, 32},

        };

        BufferedImage img = null;
        try
        {
            img = ImageIO.read(getClass().getClassLoader().getResource("cards_rocks_rework.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        List<Image> imgArray = new ArrayList<>();
        for (int[] a : spriteSheetCoords)
        {

            Image img2 = img.getSubimage(a[0], a[1], a[2], a[3]);
            img2 = img2.getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            imgArray.add(img2);
        }

        return imgArray;
    }

    public List<Image> splitSpriteHologram()
    {
        int[][] spriteSheetCoords = {
                {0, 0, 48, 64}, {48, 0, 48, 64}, {96, 0, 48, 64},
                {0, 64, 48, 64}, {48, 64, 48, 64}, {96, 64, 48, 64},
                {0, 128, 48, 64}, {48, 128, 48, 64}, {96, 128, 48, 64},

                {0, 192, 48, 64}, {48, 192, 48, 64}, {96, 192, 48, 64},
                {0, 256, 48, 64}, {48, 256, 48, 64}, {96, 256, 48, 64},
                {0, 320, 48, 64}, {48, 320, 48, 64}, {96, 320, 48, 64},

        };

        BufferedImage img = null;
        try
        {
            img = ImageIO.read(getClass().getClassLoader().getResource("holo_card_final.png"));

//            img = ImageIO.read(new File(getClass().getClassLoader().getResource("holo_card_final.png").getFile()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        List<Image> imgArray = new ArrayList<>();
        for (int[] a : spriteSheetCoords)
        {

            Image img2 = img.getSubimage(a[0], a[1], a[2], a[3]);
            img2 = img2.getScaledInstance(HOLOGRAM_WIDTH, HOLOGRAM_HEIGHT, Image.SCALE_SMOOTH);
            imgArray.add(img2);
        }

        return imgArray;
    }

    public Image[][] splitSpriteGlitchAnimations()
    {
        int[][] spriteSheetCoords = {
                // Triangle filled
                {0, 0, 48, 64}, {48, 0, 48, 64}, {96, 0, 48, 64}, {144, 0, 48, 64}, {192, 0, 48, 64}, {240, 0, 48, 64}, {288, 0, 48, 64}, {336, 0, 48, 64},
                {0, 64, 48, 64}, {48, 64, 48, 64}, {96, 64, 48, 64}, {144, 64, 48, 64}, {192, 64, 48, 64}, {240, 64, 48, 64}, {288, 64, 48, 64}, {336, 64, 48, 64},
                {0, 128, 48, 64}, {48, 128, 48, 64}, {96, 128, 48, 64}, {144, 128, 48, 64}, {192, 128, 48, 64}, {240, 128, 48, 64}, {288, 128, 48, 64}, {336, 128, 48, 64},

                // Triangle hollow
                {0, 192, 48, 64}, {48, 192, 48, 64}, {96, 192, 48, 64}, {144, 192, 48, 64}, {192, 192, 48, 64}, {240, 192, 48, 64}, {288, 192, 48, 64}, {336, 192, 48, 64},
                {0, 256, 48, 64}, {48, 256, 48, 64}, {96, 256, 48, 64}, {144, 256, 48, 64}, {192, 256, 48, 64}, {240, 256, 48, 64}, {288, 256, 48, 64}, {336, 256, 48, 64},
                {0, 320, 48, 64}, {48, 320, 48, 64}, {96, 320, 48, 64}, {144, 320, 48, 64}, {192, 320, 48, 64}, {240, 320, 48, 64}, {288, 320, 48, 64}, {336, 320, 48, 64},

                // Square filled
                {0, 384, 48, 64}, {48, 384, 48, 64}, {96, 384, 48, 64}, {144, 384, 48, 64}, {192, 384, 48, 64}, {240, 384, 48, 64}, {288, 384, 48, 64}, {336, 384, 48, 64},
                {0, 448, 48, 64}, {48, 448, 48, 64}, {96, 448, 48, 64}, {144, 448, 48, 64}, {192, 448, 48, 64}, {240, 448, 48, 64}, {288, 448, 48, 64}, {336, 448, 48, 64},
                {0, 512, 48, 64}, {48, 512, 48, 64}, {96, 512, 48, 64}, {144, 512, 48, 64}, {192, 512, 48, 64}, {240, 512, 48, 64}, {288, 512, 48, 64}, {336, 512, 48, 64},

                // Square hollow
                {0, 576, 48, 64}, {48, 576, 48, 64}, {96, 576, 48, 64}, {144, 576, 48, 64}, {192, 576, 48, 64}, {240, 576, 48, 64}, {288, 576, 48, 64}, {336, 576, 48, 64},

                {0, 640, 48, 64}, {48, 640, 48, 64}, {96, 640, 48, 64}, {144, 640, 48, 64}, {192, 640, 48, 64}, {240, 640, 48, 64}, {288, 640, 48, 64}, {336, 640, 48, 64},

                {0, 704, 48, 64}, {48, 704, 48, 64}, {96, 704, 48, 64}, {144, 704, 48, 64}, {192, 704, 48, 64}, {240, 704, 48, 64}, {288, 704, 48, 64}, {336, 704, 48, 64},

                // Circle filled
                {0, 768, 48, 64}, {48, 768, 48, 64}, {96, 768, 48, 64}, {144, 768, 48, 64}, {192, 768, 48, 64}, {240, 768, 48, 64}, {288, 768, 48, 64}, {336, 768, 48, 64},
                {0, 832, 48, 64}, {48, 832, 48, 64}, {96, 832, 48, 64}, {144, 832, 48, 64}, {192, 832, 48, 64}, {240, 832, 48, 64}, {288, 832, 48, 64}, {336, 832, 48, 64},
                {0, 896, 48, 64}, {48, 896, 48, 64}, {96, 896, 48, 64}, {144, 896, 48, 64}, {192, 896, 48, 64}, {240, 896, 48, 64}, {288, 896, 48, 64}, {336, 896, 48, 64},

                // Circle hollow
                {0, 960, 48, 64}, {48, 960, 48, 64}, {96, 960, 48, 64}, {144, 960, 48, 64}, {192, 960, 48, 64}, {240, 960, 48, 64}, {288, 960, 48, 64}, {336, 960, 48, 64},
                {0, 1024, 48, 64}, {48, 1024, 48, 64}, {96, 1024, 48, 64}, {144, 1024, 48, 64}, {192, 1024, 48, 64}, {240, 1024, 48, 64}, {288, 1024, 48, 64}, {336, 1024, 48, 64},
                {0, 1088, 48, 64}, {48, 1088, 48, 64}, {96, 1088, 48, 64}, {144, 1088, 48, 64}, {192, 1088, 48, 64}, {240, 1088, 48, 64}, {288, 1088, 48, 64}, {336, 1088, 48, 64},


        };
        BufferedImage img = null;
        try
        {
            System.out.println(getClass().getClassLoader().getResource("cards_rocks_rework.png"));
            System.out.println(getClass().getResource("res/holo_card_anims_final.png"));

            img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("anims/holo_card_anims_final.png")));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Image[][] res = new Image[18][8];
        for (int indexRow = 0; indexRow < 18; indexRow++)
        {
            for (int indexCol = 0; indexCol < 8; indexCol++)
            {
                int[] sheetCoord = spriteSheetCoords[indexRow * 8 + indexCol];
                Image subimg = img.getSubimage(sheetCoord[0], sheetCoord[1], sheetCoord[2], sheetCoord[3]);
                subimg = subimg.getScaledInstance(HOLOGRAM_WIDTH, HOLOGRAM_HEIGHT, Image.SCALE_SMOOTH);

                res[indexRow][indexCol] = subimg;
            }
        }
        return res;
    }


}
