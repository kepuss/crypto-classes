package com.aesgen.modes;

/**
 * @author Michał Kępkowski
 *         Created by kepuss on 3/29/15.
 */
public class ModeSelector{
    public static WorkMode select(String mode) throws NoSuchModeException{
        switch(mode){
            case"OR": return new Oracle();
            case"CH": return new Challenge();
            default : throw new NoSuchModeException("No such mode");
        }
    }
}
