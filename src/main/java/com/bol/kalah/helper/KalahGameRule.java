package com.bol.kalah.helper;


import com.bol.kalah.model.KalahGame;
import com.bol.kalah.model.KalahGamePit;

/**
 * This class is an abstract class to define the rule based on game and pit
 *
 * @author revathik
 */
public abstract class KalahGameRule {

    /**
     * Instantiaties Game Rule
     */
    protected KalahGameRule next;

    /**
     * Abstract Play method
     * @param game KalahGame Object
     * @param currentPit KalahGamePit Object
     */
    public abstract void apply(KalahGame game, KalahGamePit currentPit);

    /**
     * Method to move next
     * @param next
     * @return
     */
    public KalahGameRule setNext(KalahGameRule next) {
        this.next = next;
        return next;
    }
}
