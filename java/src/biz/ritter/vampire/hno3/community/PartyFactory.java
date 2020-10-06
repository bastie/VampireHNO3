/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */

package biz.ritter.vampire.hno3.community;

import java.util.Optional;

/**
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class PartyFactory {

  public static Optional<Party> getParty (String id) {
    Optional<Party> result = Optional.empty();
    switch (id) {
      case "Java" : {
        JavaParty coffeeToFly = new JavaParty();
        coffeeToFly.lookingForMember();
        result = Optional.of(coffeeToFly);
        break;
      }
      default :
        throw new UnsupportedOperationException (new IllegalArgumentException("Unexpected value: " + id));
    }
    return result;
  }
}
