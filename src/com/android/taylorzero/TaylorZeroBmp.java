package com.android.taylorzero;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

public class TaylorZeroBmp {

	/**
	 * @describe loading bitmap auto resize this bitmap size by file
	 * 
	 * @param filePath
	 *            : laoding bitmap file path
	 * @param outWidth
	 *            : auto resize width
	 * @param outHeight
	 *            : auto resize height
	 * @return Bitmap : return auto resize of new Bitmap
	 */
	public static Bitmap loadBitmapAutoSize(String filePath, int outWidth,
			int outHeight) {
		// outWidth和outHeight是目标图片的最大宽度和高度，用作限制
		FileInputStream fs = null;
		BufferedInputStream bs = null;
		try {
			fs = new FileInputStream(filePath);
			bs = new BufferedInputStream(fs);
			BitmapFactory.Options options = setBitmapOption(filePath, outWidth,
					outHeight);
			return BitmapFactory.decodeStream(bs, null, options);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bs.close();
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @describe loading bitmap auto resize this bitmap size by R.drawable
	 *           resource
	 * 
	 * @param context
	 *            : caller context
	 * @param resource
	 *            : R.drawable.* of ID
	 * @param outWidth
	 *            : resize bitmap width
	 * @param outHeight
	 *            : resize bitmap height
	 * @return Bitmap : return rezie already bitmap
	 */
	public static Bitmap loadBitmapAutoSize(Context context, int resource,
			int outWidth, int outHeight) {
		// outWidth和outHeight是目标图片的最大宽度和高度，用作限制
		Bitmap ret = ((BitmapDrawable) context.getResources().getDrawable(
				resource)).getBitmap();
		ret = TaylorZeroBmp.BitmapMatrix(ret, outWidth, outHeight);
		return ret;
	}

	/**
	 * @describe get one bitmap scale options
	 * 
	 * @param file
	 *            : loading bmp file path
	 * @param width
	 *            : compute scale width
	 * @param height
	 *            : compute scale height
	 * @return Options : return bitmap setting options
	 */
	private static BitmapFactory.Options setBitmapOption(String file,
			int width, int height) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		// 设置只是解码图片的边距，此操作目的是度量图片的实际宽度和高度
		BitmapFactory.decodeFile(file, opt);

		int outWidth = opt.outWidth; // 获得图片的实际高和宽
		int outHeight = opt.outHeight;
		opt.inDither = false;
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		// 设置加载图片的颜色数为16bit，默认是RGB_8888，表示24bit颜色和透明通道，但一般用不上
		opt.inSampleSize = 1;
		// 设置缩放比,1表示原比例，2表示原来的四分之一....
		// 计算缩放比
		if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
			int sampleSize = (outWidth / width + outHeight / height) / 2;
			opt.inSampleSize = sampleSize;
		}

		opt.inJustDecodeBounds = false;// 最后把标志复原
		return opt;
	}

	/**
	 * @describe Matrix bmp with the ratio of width and height
	 * 
	 * @param bmp
	 *            : target matrix bitmap
	 * @param width
	 *            : matrix width
	 * @param height
	 *            : matrix height
	 * @return Bitmap : result matrix bitmap
	 */
	public static Bitmap BitmapRatioMatrix(Bitmap bmp, float width, float height) {
		Bitmap ret = null;
		int oraginWidth = bmp.getWidth();
		int oraginHeight = bmp.getHeight();
		// full screen
		float scaleWidth = width / oraginWidth;
		float scaleHeight = height / oraginHeight;
		// show with ratio width and height
		if (scaleWidth < scaleHeight) {
			scaleHeight = scaleWidth;
		} else {
			scaleWidth = scaleHeight;
		}
		Matrix mMatrix = new Matrix();
		mMatrix.postScale(scaleWidth, scaleHeight);
		ret = Bitmap.createBitmap(bmp, 0, 0, oraginWidth, oraginHeight,
				mMatrix, true);
		return ret;
	}

	/**
	 * @describe matrix bitmap with no ratio, this means using width and height
	 *           to scale bitmap
	 * 
	 * @param bmp
	 *            : for matrix bitmap
	 * @param width
	 *            : matrix width
	 * @param height
	 *            : matrix height
	 * @return Bitmap : result new matrix bitmap
	 */
	public static Bitmap BitmapMatrix(Bitmap bmp, float width, float height) {
		Bitmap ret = null;
		int oraginWidth = bmp.getWidth();
		int oraginHeight = bmp.getHeight();
		// full screen
		float scaleWidth = width / oraginWidth;
		float scaleHeight = height / oraginHeight;
		Matrix mMatrix = new Matrix();
		mMatrix.postScale(scaleWidth, scaleHeight);
		ret = Bitmap.createBitmap(bmp, 0, 0, oraginWidth, oraginHeight,
				mMatrix, true);
		return ret;
	}

	/**
	 * 将从WebView中获得的Pic存储到sdcard
	 * 
	 * @param path
	 *            存儲的路徑
	 * @param fileName
	 *            存儲的文件名
	 * @param srcBmp
	 *            存儲的目標bitmap
	 * @param width
	 *            在反饋給調用者的時候返回的html片段設置的圖片的寬高
	 * @param height
	 */
	public void SaveResultPicBuffer(String path, String fileName,
			Bitmap srcBmp, int width, int height, int now_count) {
		String tmp_name = new String(path + fileName);
		File tmp_file = new File(tmp_name);
		if (tmp_file.exists()) {
			tmp_file.delete();
		}
		File dir = new File(path);
		dir.mkdirs();
		try {
			FileOutputStream out = new FileOutputStream(tmp_file);
			srcBmp.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @describe calculate loading bmp scale size
	 * 
	 * @param options
	 *            : setting config
	 * @param reqWidth
	 *            : request width
	 * @param reqHeight
	 *            : request height
	 * @return int : return matrix bitmap size for example 2 is 1/2 of bitmap
	 *         size
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		int outWidth = options.outWidth; // 获得图片的实际高和宽
		int outHeight = options.outHeight;
		int width = reqWidth;
		int height = reqHeight;
		int retSampleSize = 1;

		if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
			int sampleSize = (outWidth / width + outHeight / height) / 2;
			retSampleSize = sampleSize;
		}
		return retSampleSize;
	}
}
