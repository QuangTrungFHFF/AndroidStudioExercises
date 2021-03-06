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


public class FamilyFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener mMediaOnCompletetionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.word_list,container, false);

        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> familyList = new ArrayList<Word>();
        familyList.add(new Word("father","әpә",R.raw.family_father, R.drawable.family_father));
        familyList.add(new Word("mother","әṭa",R.raw.family_mother, R.drawable.family_mother));
        familyList.add(new Word("son","angsi",R.raw.family_son, R.drawable.family_son));
        familyList.add(new Word("daughter","tune",R.raw.family_daughter, R.drawable.family_daughter));
        familyList.add(new Word("older brother","taachi",R.raw.family_older_brother, R.drawable.family_older_brother));
        familyList.add(new Word("younger brother","chalitti",R.raw.family_younger_brother, R.drawable.family_younger_brother));
        familyList.add(new Word("older sister","teṭe",R.raw.family_older_sister, R.drawable.family_older_sister));
        familyList.add(new Word("younger sister","kolliti",R.raw.family_younger_sister, R.drawable.family_younger_sister));
        familyList.add(new Word("grandmother","ama",R.raw.family_grandmother, R.drawable.family_grandmother));
        familyList.add(new Word("grandfather","paapa",R.raw.family_grandfather, R.drawable.family_grandfather));

        WordAdapter wordAdapter = new WordAdapter(getActivity(),familyList, R.color.category_family);
        ListView listView = (ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                int audioId = familyList.get(position).getAudioResourceId();
                if(audioId>=0)
                {
                    releaseMediaPlayer();

                    int request = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if(request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                    {
                        mediaPlayer = MediaPlayer.create(getActivity(),audioId);
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