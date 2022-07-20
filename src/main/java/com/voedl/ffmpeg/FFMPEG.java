package com.voedl.ffmpeg;

import com.github.kokorin.jaffree.ffmpeg.*;
import com.voedl.Language;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicLong;

public class FFMPEG {
    public FFMPEG(String files) {
        final AtomicLong duration = new AtomicLong();
        FFmpeg.atPath()
                .addArguments("-i", "\"concat:" + files + "\"")
                .setOverwriteOutput(true)
                .addOutput(new NullOutput())
                .setProgressListener(new ProgressListener() {
                    @Override
                    public void onProgress(FFmpegProgress progress) {
                        duration.set(progress.getTimeMillis());
                    }
                })
                .execute();
        try (ProgressBar pb = new ProgressBar("Converting...", 100, 1000, false, System.err, ProgressBarStyle.ASCII, "%", 1L, false, (DecimalFormat)null, ChronoUnit.SECONDS, 0L, Duration.ZERO)) {
                   FFmpegResult ffmpegResult = FFmpeg.atPath()
                    .addArguments("-i", "\"concat:" + files + "\"")
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
