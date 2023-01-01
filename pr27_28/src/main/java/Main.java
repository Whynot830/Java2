import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String srcFolder = "pr27_28/src/main/java/imgSrc";
        String dstFolder = "pr27_28/src/main/java/imgDst";
        int threadNumber = 0;
        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        try {
            Path path = Paths.get(dstFolder);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            assert files != null;
            for (File file : files) {
                if (Runtime.getRuntime().availableProcessors() - threadNumber > 0) {
                    threadNumber++;
                    new Thread(
                            () -> {
                                try {
                                    reduce(file, dstFolder);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    ).start();
                    threadNumber--;
                } else
                    reduce(file, dstFolder);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Done\nDuration: " + (System.currentTimeMillis() - start) + " ms");
    }

    public static void reduce(File file, String dstFolder) throws IOException {
        BufferedImage image = ImageIO.read(file);
        if (image != null) {
            int newWidth = image.getWidth() / 2;
            int newHeight = (int) Math.round(
                    image.getHeight() / (image.getWidth() / (double) newWidth)
            );
            BufferedImage newImage = new BufferedImage(
                    newWidth, newHeight, BufferedImage.TYPE_INT_RGB
            );

            int widthStep = image.getWidth() / newWidth;
            int heightStep = image.getHeight() / newHeight;

            for (int x = 0; x < newWidth; x++) {
                for (int y = 0; y < newHeight; y++) {
                    int rgb = image.getRGB(x * widthStep, y * heightStep);
                    newImage.setRGB(x, y, rgb);
                }
            }
            File newFile = new File(dstFolder + "/" + file.getName());
            ImageIO.write(newImage, "jpg", newFile);
        }
    }
}