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


public class PhraseFragment extends Fragment {

    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                mediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener mMediaOnCompletetionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> phraseList = new ArrayList<Word>();
        phraseList.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        phraseList.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        phraseList.add(new Word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        phraseList.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        phraseList.add(new Word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        phraseList.add(new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        phraseList.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        phraseList.add(new Word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        phraseList.add(new Word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        phraseList.add(new Word("Come here.","әnni'nem",R.raw.phrase_come_here));

        WordAdapter wordAdapter = new WordAdapter(getActivity(),phraseList,R.color.category_phrases);
        ListView listView = (ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                int audioId = phraseList.get(position).getAudioResourceId();
                if(audioId>=0){
                    releaseMediaPlayer();
                    int request = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if(request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                    {
                        mediaPlayer =MediaPlayer.create(getActivity(),audioId);
                        mediaPlayer.start();

                        mediaPlayer.setOnCompletionListener(mMediaOnCompletetionListener);
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