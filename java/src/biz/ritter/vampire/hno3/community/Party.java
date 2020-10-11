/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */

package biz.ritter.vampire.hno3.community;

/**
 * The concrete Party
 * 
 * <br/>
 * Note: looks like a collection!?!
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public interface Party {

  void printMember ();
  
  /**
   * Add a new member maybe from other Party
   * 
   * <br/>
   * Note: implementation has to check member is in our party
   * @param newMember
   */
  void addMember (Member newMember);
  
  void removeMember (Member oldMember);
  
  boolean hasMember (Member member);
  
  /**
   * Copy party
   * @param goingTo
   * @param newParty
   * @return
   */
  default boolean copyMember (Member goingTo, Party newParty) {
    newParty.addMember(goingTo);
    return newParty.hasMember(goingTo);
  }
  
  /**
   * Move member from one party to other
   * @param goingTo
   * @param newParty
   * @return
   */
  default boolean moveMember (Member goingTo, Party newParty) {
    if (this.copyMember(goingTo, newParty)) {
      this.removeMember(goingTo);
      if (this.hasMember(goingTo)) {
        // in result of not remove member in this party rollback
        newParty.removeMember(goingTo);
        if (newParty.hasMember(goingTo)) {
          throw new IllegalStateException(String.format("Member %2 is in both parties (%s,%s).", goingTo.getCard(), this.toString(), newParty.toString()));
        }
        else return false; // rollback, member only in this party 
      }
      else return true; // member only in new party
    }
    else return false; // member not going, member only in this party
  }
}
