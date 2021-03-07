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


public class NumberFragment extends Fragment {

    private ArrayList<Word> englishNumbers = new ArrayList<Word>();

    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                mediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
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
        View rootView = inflater.inflate(R.layout.word_list,container,false);

        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        addLibrary();
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        WordAdapter wordAdapter = new WordAdapter(getActivity(), englishNumbers, R.color.category_numbers);

        // Get a reference to the ListView, and attach the adapter to the listView.
        //ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                int audioId = englishNumbers.get(position).getAudioResourceId();
                if(audioId>=0) {
                    releaseMediaPlayer();

                    int request = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if(request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                    {
                        mediaPlayer = MediaPlayer.create(getActivity(), audioId);
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

    private void addLibrary(){
        englishNumbers.add(new Word("one","lutti",R.raw.number_one, R.drawable.number_one));
        englishNumbers.add(new Word("two","otiiko",R.raw.number_two, R.drawable.number_two));
        englishNumbers.add(new Word("three","tolookosu",R.raw.number_three, R.drawable.number_three));
        englishNumbers.add(new Word("four","oyyisa",R.raw.number_four, R.drawable.number_four));
        englishNumbers.add(new Word("five","massokka",R.raw.number_five, R.drawable.number_five));
        englishNumbers.add(new Word("six","temmokka",R.raw.number_six, R.drawable.number_six));
        englishNumbers.add(new Word("seven","kenekaku",R.raw.number_seven, R.drawable.number_seven));
        englishNumbers.add(new Word("eight","kawinta",R.raw.number_eight, R.drawable.number_eight));
        englishNumbers.add(new Word("nine","wo’e",R.raw.number_nine, R.drawable.number_nine));
        englishNumbers.add(new Word("ten","na’aacha",R.raw.number_ten, R.drawable.number_ten));
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