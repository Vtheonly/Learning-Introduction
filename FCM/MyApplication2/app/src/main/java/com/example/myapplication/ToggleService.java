package com.example.myapplication;

import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

public class ToggleService extends TileService {
    @Override
    public void onClick() {
        Tile tile = getQsTile();

        if (tile.getState() == Tile.STATE_ACTIVE) {
            tile.setState(Tile.STATE_INACTIVE);
            tile.setLabel(getString(R.string.wifi_off));
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_wifi_off));
            Toast.makeText(this, "WiFi Turned OFF", Toast.LENGTH_SHORT).show();
        } else {
            tile.setState(Tile.STATE_ACTIVE);
            tile.setLabel(getString(R.string.wifi_on));
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_wifi));
            Toast.makeText(this, "WiFi Turned ON", Toast.LENGTH_SHORT).show();
        }

        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        Tile tile = getQsTile();
        if (tile != null) {
            tile.setState(Tile.STATE_INACTIVE);
            tile.setLabel(getString(R.string.wifi_off));
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_wifi_off));
            tile.updateTile();
        }
    }
}