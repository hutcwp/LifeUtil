package com.hutcwp.srw.music;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * author : kevin
 * date : 2022/3/20 8:01 PM
 * description :
 */
public class AudioMusic {

    private static AudioMusic AudioMusic = null;
    private static final String TAG = "Bg_Music";
    private float mLeftVolume;
    private float mRightVolume;
    private Context mContext;
    private MediaPlayer mBackgroundMediaPlayer;
    private boolean mIsPaused;
    private String mCurrentPath;

    private AudioMusic(Context context) {
        this.mContext = context;
        initData();
    }

    public static AudioMusic getInstance(Context context) {
        if (AudioMusic == null) {
            AudioMusic = new AudioMusic(context);
        }
        return AudioMusic;
    }

    // 初始化一些数据
    private void initData() {
        mLeftVolume = 0.5f;
        mRightVolume = 0.5f;
        mBackgroundMediaPlayer = null;
        mIsPaused = false;
        mCurrentPath = null;
    }

    /**
     * 根据path路径播放背景音乐
     *
     * @param path   :assets中的音频路径
     * @param isLoop :是否循环播放
     */
    public void playAudioMusic(String path, boolean isLoop) {
        if (mCurrentPath == null) {
            mBackgroundMediaPlayer = createMediaplayerFromAssets(path);
            mCurrentPath = path;
        } else {
            if (!mCurrentPath.equals(path)) {
                if (mBackgroundMediaPlayer != null) {
                    mBackgroundMediaPlayer.release();
                }
                mBackgroundMediaPlayer = createMediaplayerFromAssets(path);

                mCurrentPath = path;
            }
        }
        if (mBackgroundMediaPlayer == null) {
            Log.e(TAG, "playAudioMusic: background media player is null");
        } else {
            mBackgroundMediaPlayer.stop();
            mBackgroundMediaPlayer.setLooping(isLoop);
            try {
                mBackgroundMediaPlayer.prepare();
                mBackgroundMediaPlayer.seekTo(0);
                mBackgroundMediaPlayer.start();
                this.mIsPaused = false;
            } catch (Exception e) {
                Log.e(TAG, "playAudioMusic: error state");
            }
        }
    }

    /**
     * 停止播放背景音乐
     */
    public void stopAudioMusic() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.stop();
            this.mIsPaused = false;
        }
    }

    /**
     * 暂停播放背景音乐
     */
    public void pauseAudioMusic() {
        if (mBackgroundMediaPlayer != null
                && mBackgroundMediaPlayer.isPlaying()) {
            mBackgroundMediaPlayer.pause();
            this.mIsPaused = true;
        }
    }

    /**
     * 继续播放背景音乐
     */
    public void resumeAudioMusic() {
        if (mBackgroundMediaPlayer != null && this.mIsPaused) {
            mBackgroundMediaPlayer.start();
            this.mIsPaused = false;
        }
    }

    /**
     * 重新播放背景音乐
     */
    public void rewindAudioMusic() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.stop();
            try {
                mBackgroundMediaPlayer.prepare();
                mBackgroundMediaPlayer.seekTo(0);
                mBackgroundMediaPlayer.start();
                this.mIsPaused = false;
            } catch (Exception e) {
                Log.e(TAG, "rewindAudioMusic: error state");
            }
        }
    }

    /**
     * 判断背景音乐是否正在播放
     *
     * @return：返回的boolean值代表是否正在播放
     */
    public boolean isAudioMusicPlaying() {
        boolean ret = false;
        if (mBackgroundMediaPlayer == null) {
            ret = false;
        } else {
            ret = mBackgroundMediaPlayer.isPlaying();
        }
        return ret;
    }

    /**
     * 结束背景音乐，并释放资源
     */
    public void end() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.release();
        }
// 重新“初始化数据”
        initData();
    }

    /**
     * 得到背景音乐的“音量”
     *
     * @return
     */
    public float getBackgroundVolume() {
        if (this.mBackgroundMediaPlayer != null) {
            return (this.mLeftVolume + this.mRightVolume) / 2;
        } else {
            return 0.0f;
        }
    }

    /**
     * 设置背景音乐的音量
     *
     * @param volume ：设置播放的音量，float类型
     */
    public void setBackgroundVolume(float volume) {
        this.mLeftVolume = this.mRightVolume = volume;
        if (this.mBackgroundMediaPlayer != null) {
            this.mBackgroundMediaPlayer.setVolume(this.mLeftVolume,
                    this.mRightVolume);
        }
    }

    /**
     * create mediaplayer for music
     *
     * @param path the path relative to assets
     * @return
     */
    private MediaPlayer createMediaplayerFromAssets(String path) {
        MediaPlayer mediaPlayer = null;
        try {
            AssetFileDescriptor assetFileDescritor = mContext.getAssets()
                    .openFd(path);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(assetFileDescritor.getFileDescriptor(),
                    assetFileDescritor.getStartOffset(),
                    assetFileDescritor.getLength());
            mediaPlayer.prepare();
//            mediaPlayer.setVolume(mLeftVolume, mRightVolume);
        } catch (Exception e) {
            mediaPlayer = null;
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
        return mediaPlayer;
    }
}
