/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.casino.generalLogic;

import com.approxteam.casino.generalLogic.actions.Action;
import com.approxteam.casino.generalLogic.actions.predicates.NotInState;
import java.io.Serializable;
import java.util.function.BiPredicate;
import javax.websocket.Session;

/**
 *
 * @author adamr
 */
public class PlayerHandler implements Serializable {
            
    private static BiPredicate<PlayerHandler, Action> notInState = new NotInState(PlayerState.MAINMENU);
    
    private static boolean isLogged(PlayerHandler handler) {
        return notInState.test(handler, null) && handler.getNickname() != null;
    }
    
    private PlayerState playerState;
    private Session session;
    private String nickname;
    public PlayerHandler(Session session) {
        this.playerState = PlayerState.MAINMENU;
        this.session = session;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public Session getSession() {
        return session;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    
    
    public void switchState(PlayerState newState) {
        this.playerState = newState;
    }
    
    
    public boolean isLogged() {
        return isLogged(this);
    }
    
    
    
}
