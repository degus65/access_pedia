package com.example.degus.accesspedia.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.degus.accesspedia.R;

/**
 * Created by Dominik Nowak on 09/03/2018.
 */

public abstract class ContextMenuMainActivity extends AbstractMainActivity {

    private boolean isMuted = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_mute:
                if (this instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) this;
                    if (isMuted) {
                        isMuted = false;
                        mainActivity.unMuteTextToSpeech();
                        item.setTitle(getString(R.string.mute));
                    } else {
                        isMuted = true;
                        mainActivity.muteTextToSpeech();
                        item.setTitle(getString(R.string.un_mute));
                    }
                }
                break;

            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;

            default:
                break;
        }
        return true;
    }
}
