/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */

package biz.ritter.vampire.hno3;

import biz.ritter.vampire.hno3.community.Party;
import biz.ritter.vampire.hno3.community.PartyFactory;

/**
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class Community {
  
  public static void main(final String... args) {
    
    Party party = PartyFactory.getParty("Java").orElseThrow();
    party.printMember();
  }

}
