package com.github.khornya.useyourwords.utils;

import com.github.khornya.useyourwords.model.Player;

public class GameUtils {

	public int getLastPlayerIndex(Player[] players) {
		int j = 0;
		for (; j < players.length; j++)
			if (players[j] != null)
				break;
		return j >= players.length ? -1 : j;
	}

}
