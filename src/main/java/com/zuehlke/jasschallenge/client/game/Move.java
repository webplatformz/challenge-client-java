package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.game.cards.Card;

public class Move {
    private final Player player;
    private final Card playedCard;

    public Move(Player player, Card playedCard) {
        this.player = player;
        this.playedCard = playedCard;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getPlayedCard() {
        return playedCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (player != null ? !player.equals(move.player) : move.player != null) return false;
        return playedCard == move.playedCard;

    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (playedCard != null ? playedCard.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Move{" +
                "player=" + player +
                ", playedCard=" + playedCard +
                '}';
    }
}
