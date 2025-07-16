package demo;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;
import net.coobird.thumbnailator.Thumbnails;

public class ImageCompressorTest {

    @SneakyThrows
    @Test
    public void compress() {
        // 输入图片路径
        String inputImagePath = "/home/chenwenxi/Downloads/20250515/猫/photo_2025-05-15_17-55-40.jpg";
        // 输出图片路径
        String outputWebpPath = "/home/chenwenxi/Downloads/20250515/猫/1.webp";
        String outputJpgPath = "/home/chenwenxi/Downloads/20250515/猫/1.jpg";

        // 压缩并保存为 WebP 格式
        compressAndSave(inputImagePath, outputWebpPath, "webp", 0.8f);
        System.out.println("WebP 图片已生成: " + outputWebpPath);

        // 压缩并保存为 JPG 格式
        compressAndSave(inputImagePath, outputJpgPath, "jpg", 0.8f);
        System.out.println("JPG 图片已生成: " + outputJpgPath);
    }

    /**
     * 压缩图片并保存为指定格式
     * 
     * @param inputPath 输入图片路径
     * @param outputPath 输出图片路径
     * @param format 输出图片格式（如 "jpg"、"webp" 等）
     * @param quality 图片质量（0.0 - 1.0）
     * @throws IOException 如果发生 I/O 错误
     */
    public static void compressAndSave(String inputPath, String outputPath, String format, float quality)
        throws IOException {
        Thumbnails.of(inputPath)
            // 设置输出格式
            .outputFormat(format).scale(1)
            // 设置图片质量（0.0 - 1.0）
            .outputQuality(quality)
            // 保存到指定路径
            .toFile(new File(outputPath));
    }
}
