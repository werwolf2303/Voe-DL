package com.voedl;

import com.github.kokorin.jaffree.ffmpeg.*;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import com.github.kokorin.jaffree.ffprobe.Stream;
import me.tongfei.progressbar.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;


public class Test {
    public static void main(String[] args) throws IOException {
        String filename = "eins.mp4";
        final AtomicLong durationMillis = new AtomicLong();
        final AtomicLong duration = new AtomicLong();
        FFmpeg.atPath()
                .addArguments("-i", "\"concat:down\\seg-1-v1-a1.ts|down\\seg-2-v1-a1.ts\"")
                .setOverwriteOutput(true)
                .addOutput(new NullOutput())
                .setProgressListener(new ProgressListener() {
                    @Override
                    public void onProgress(FFmpegProgress progress) {
                        duration.set(progress.getTimeMillis());
                    }
                })
                .execute();
        System.out.println(duration);
        long all = duration.get();
        long count = 0;
        try (ProgressBar pb = new ProgressBar("Converting...",100)) {
            FFmpegResult ffmpegResult = FFmpeg.atPath()
                    .addArguments("-i", "\"concat:down\\seg-1-v1-a1.ts|down\\seg-2-v1-a1.ts\"")
                    .addOutput(UrlOutput.toUrl("Output.mp4"))
                    .setOverwriteOutput(true)
                    .setProgressListener(new ProgressListener() {
                        @Override
                        public void onProgress(FFmpegProgress progress) {
                            double percents = 100. * progress.getTimeMillis() / duration.get();
                            pb.stepTo(Math.round(percents));
                        }
                    })
                    .execute();
        }
    }
}
