/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */


package biz.ritter.vampire.hno3.community;

/**
 * The member of our party / community
 * 
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public interface Member {

  /**
   * The card is the party depend representation.
   * For programming languages it is like source code 
   * @return
   */
  String getCard ();
  
  /**
   * The sign is the party independ representation
   * For programming language it is like meta code, UML and so on
   * @return JSON like string
   */
  String getSign();

  
}
