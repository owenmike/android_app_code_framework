package com.owen.codeframework.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.owen.codeframework.R;

/**
 * UIL管理类
 *
 * Created by Owen on 2015/11/3.
 */
public class ImageLoaderMgr {

    private static ImageLoaderMgr sInstance = new ImageLoaderMgr();

    private ImageLoader mImageLoader;
    private DisplayImageOptions optionsUserAvatar;

    private ImageLoaderMgr() {
        mImageLoader = ImageLoader.getInstance();
    }

    public static ImageLoaderMgr getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
        L.writeDebugLogs(false);
        L.writeLogs(false);
    }

    /**
     * 清除缓存
     */
    public void clear() {
        mImageLoader.clearDiskCache();
    }

    /**
     * 显示用户头像
     */
    public void displayUserAvatar(String url, ImageView imageView) {
        mImageLoader.displayImage(url, imageView, getOptionsAvatar());
    }

    private DisplayImageOptions getOptionsAvatar() {
        if (optionsUserAvatar == null) {
            optionsUserAvatar = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.default_user_avatar)
                    .showImageForEmptyUri(R.drawable.default_user_avatar)
                    .showImageOnFail(R.drawable.default_user_avatar)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }

        return optionsUserAvatar;
    }

    // TODO 进度条

}
