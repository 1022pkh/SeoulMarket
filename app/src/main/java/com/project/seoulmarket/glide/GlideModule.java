package com.project.seoulmarket.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;

/**
 * Created by kh on 2016. 12. 12..
 */
public class GlideModule implements com.bumptech.glide.module.GlideModule {

        private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        private final int cacheSize = maxMemory / 8;
        private final int DISK_CACHE_SIZE = 1024 * 1024 * 10;

        @Override
        public void applyOptions(Context context, GlideBuilder builder) {
                builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)
                        .setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", DISK_CACHE_SIZE))
                        .setMemoryCache(new LruResourceCache(cacheSize));

        }

        @Override
        public void registerComponents(Context context, Glide glide) {
//            glide.register(SelphoneImage.class, InputStream.class, new SelphoneGlideUrlLoader.Factory());
        }


}
