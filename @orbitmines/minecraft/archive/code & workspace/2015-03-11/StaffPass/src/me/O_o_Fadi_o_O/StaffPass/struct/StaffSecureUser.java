/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.O_o_Fadi_o_O.StaffPass.struct;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author devan_000
 */
public class StaffSecureUser {

      @Getter
      public String playerName;
      @Getter
      public String playerUUID;
      @Getter
      public StaffSecureUserConfig config;
      @Getter
      @Setter
      public boolean isLoggedIn;

      public StaffSecureUser(String playerName, String playerUUID, StaffSecureUserConfig config) {
            this.playerName = playerName;
            this.playerUUID = playerUUID;
            this.config = config;
            this.isLoggedIn = false;
      }

}
