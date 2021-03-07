package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ColorsFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT|| focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                mediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> colorList = new ArrayList<Word>();
        colorList.add(new Word("red","weṭeṭṭi",R.raw.color_red, R.drawable.color_red));
        colorList.add(new Word("green","chokokki",R.raw.color_green, R.drawable.color_green));
        colorList.add(new Word("brown","ṭakaakki",R.raw.color_brown, R.drawable.color_brown));
        colorList.add(new Word("gray","ṭopoppi",R.raw.color_gray, R.drawable.color_gray));
        colorList.add(new Word("black","kululli",R.raw.color_black, R.drawable.color_black));
        colorList.add(new Word("white","kelelli",R.raw.color_white, R.drawable.color_white));
        colorList.add(new Word("dusty yellow","ṭopiisә",R.raw.color_dusty_yellow, R.drawable.color_dusty_yellow));
        colorList.add(new Word("mustard yellow","chiwiiṭә",R.raw.color_mustard_yellow, R.drawable.color_mustard_yellow));

        WordAdapter wordAdapter = new WordAdapter(getActivity(),colorList,R.color.category_colors);
        ListView listView = (ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                int audioId = colorList.get(position).getAudioResourceId();
                if(audioId>=0)
                {
                    releaseMediaPlayer();

                    int requestFocus = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if(requestFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                    {
                        mediaPlayer = MediaPlayer.create(getActivity(),audioId);
                        mediaPlayer.start();

                        mediaPlayer.setOnCompletionListener(onCompletionListener);
                    }
                }
            }
        });



        return rootView;
    }

    private void releaseMediaPlayer(){
        if(mediaPlayer!= null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}