package edu.jiangxin.zhihu.captchas;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ClearImageHelper {

	public static void main(String[] args) throws IOException {

		File testDataDir = new File("./tmp/IdentifyingCode");
		for (File file : testDataDir.listFiles()) {
			cleanImage(file, "./tmp/IdentifyingCode01");
		}
		
		for(int i=1;i<100;i++) {
			File srcFile = new File("./tmp/IdentifyingCode01/" + i + ".jpg");
			File desFile = new File("./tmp/IdentifyingCode01/" + (i+1) + ".jpg");
			enhance1(srcFile, desFile);
		}
	}

	/**
	 * 
	 * @param sfile
	 *            需要去噪的图像
	 * @param destDir
	 *            去噪后的图像保存地址
	 * @throws IOException
	 */
	public static void cleanImage(File sfile, String destDir) throws IOException {
		File destF = new File(destDir);
		if (!destF.exists()) {
			destF.mkdirs();
		}

		BufferedImage bufferedImage = ImageIO.read(sfile);
		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();

		// 灰度化
		int[][] gray = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int argb = bufferedImage.getRGB(x, y);
				// 图像加亮（调整亮度识别率非常高）
				int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);
				int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);
				int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);
				if (r >= 255) {
					r = 255;
				}
				if (g >= 255) {
					g = 255;
				}
				if (b >= 255) {
					b = 255;
				}
				gray[x][y] = (int) Math.pow(
						(Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2) * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);
			}
		}

		// 二值化
		int threshold = ostu(gray, width, height);
		BufferedImage binaryBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (gray[x][y] > threshold) {
					//gray[x][y] |= 0x00FFFF;
					gray[x][y] = 0xFFFFFF;
				} else {
					//gray[x][y] &= 0xFF0000;
					gray[x][y] = 0x000000;
				}
				binaryBufferedImage.setRGB(x, y, gray[x][y]);
			}
		}

		// 矩阵打印
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (isBlack(binaryBufferedImage.getRGB(x, y))) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		
		String sfile_without_suffix = sfile.getName().replaceFirst("gif", "jpg");
		File desFile = new File(sfile_without_suffix);
		if(desFile.exists()) {
			desFile.delete();
		}
		ImageIO.write(binaryBufferedImage, "jpg", new File(destDir, sfile_without_suffix));
	}
	
	public static void enhance(File sfile, String desDir) throws IOException {
		
		File desFile = new File(desDir);
		if(!desFile.exists()) {
			desFile.mkdirs();
		}
		BufferedImage bufferedImage = ImageIO.read(sfile);
		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();
		
		int[] xx = new int[]{1, 0, -1, 0, 1, -1, 1, -1};
		int[] yy = new int[]{0, 1, 0, -1, -1, 1, 1, -1};
		
		int[] mid = new int[8];
		
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				for(int k=0;k<8;k++) {
					if((i+xx[k]>=0) && (i+xx[k]<width) && (j+yy[k]>=0) && (j+yy[k]<height)) {
						mid[k] = bufferedImage.getRGB(i, j);
					}
				}
				Arrays.sort(mid);
				bufferedImage.setRGB(i, j, mid[3]);
			}
		}
		desFile = new File(desDir, sfile.getName());
		if(desFile.exists()) {
			desFile.delete();
		}
		ImageIO.write(bufferedImage, "jpg", desFile);
	}
	
public static void enhance1(File sfile, File desFile) throws IOException {
		
		BufferedImage bufferedImage = ImageIO.read(sfile);
		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();
		
		int[] xx = new int[]{-2,-1,0,1,2,-2,-1,0,1,2,-2,-1,0,1,2,-2,-1,0,1,2,-2,-1,0,1,2};
		int[] yy = new int[]{2,2,2,2,2,1,1,1,1,1,0,0,0,0,0,-1,-1,-1,-1,-1,-2,-2,-2,-2,-2};
		
		int[] mid = new int[25];
		
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				for(int k=0;k<25;k++) {
					if((i+xx[k]>=0) && (i+xx[k]<width) && (j+yy[k]>=0) && (j+yy[k]<height)) {
						mid[k] = bufferedImage.getRGB(i, j);
					}
				}
				Arrays.sort(mid);
				bufferedImage.setRGB(i, j, mid[12]);
			}
		}
		
		ImageIO.write(bufferedImage, "jpg", desFile);
	}

	public static boolean isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
			return true;
		}
		return false;
	}

	public static boolean isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
			return true;
		}
		return false;
	}

	public static int isBlackOrWhite(int colorInt) {
		if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730) {
			return 1;
		}
		return 0;
	}

	public static int getColorBright(int colorInt) {
		Color color = new Color(colorInt);
		return color.getRed() + color.getGreen() + color.getBlue();
	}

	public static int ostu(int[][] gray, int w, int h) {
		int[] histData = new int[w * h];
		// Calculate histogram
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int red = 0xFF & gray[x][y];
				histData[red]++;
			}
		}

		// Total number of pixels
		int total = w * h;

		float sum = 0;
		for (int t = 0; t < 256; t++)
			sum += t * histData[t];

		float sumB = 0;
		int wB = 0;
		int wF = 0;

		float varMax = 0;
		int threshold = 0;

		for (int t = 0; t < 256; t++) {
			wB += histData[t]; // Weight Background
			if (wB == 0)
				continue;

			wF = total - wB; // Weight Foreground
			if (wF == 0)
				break;

			sumB += (float) (t * histData[t]);

			float mB = sumB / wB; // Mean Background
			float mF = (sum - sumB) / wF; // Mean Foreground

			// Calculate Between Class Variance
			float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

			// Check if new maximum found
			if (varBetween > varMax) {
				varMax = varBetween;
				threshold = t;
			}
		}

		return threshold;
	}
}