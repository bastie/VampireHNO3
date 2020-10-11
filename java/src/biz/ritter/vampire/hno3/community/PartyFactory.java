/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */

package biz.ritter.vampire.hno3.community;

import java.lang.reflect.Modifier;
import java.util.List;
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
        
        List<Module> module = coffeeToFly.getModules("^java\\.base$");
        List<String> moduleClassName = coffeeToFly.lookingForJavaOuterClassMember(module);
        List<Class<?>> classes = coffeeToFly.getSpecialClasses (moduleClassName);
        
        
        // java.io.FileCleanable ist package protected
        for (Class<?> c : classes) {
          if (c.isInterface()) {
            System.err.println("= Interface "+c.getName()+" =");
            JavaMember member = new JavaMember(c);
            System.out.println(member.getSign());
            
            PythonMember pm = new PythonMember(member.getSign());
            System.out.println(pm.getSign());
            System.out.println(pm.getCard());
          }
          else if (Modifier.isAbstract(c.getModifiers())) {
/*            JavaMember member = new JavaMember(c);
            System.out.println(member.getSign());
            System.err.printf("Abstract class %s%n",c.getName());
*/          }
          else if (c.isEnum()) {
/*            JavaMember member = new JavaMember(c);
            System.out.println(member.getSign());
            System.err.printf("Enum %s%n",c.getName());
*/          }
          else {
/*            System.out.printf("Class %s%n",c.getName());
*/          }
          Thread.yield();
          
          c.getDeclaredConstructors(); // needed because protected protected constructors not in getConstructors() inkluded
        }

        result = Optional.of(coffeeToFly);
        break;
      }
      default :
        throw new UnsupportedOperationException (new IllegalArgumentException("Unexpected value: " + id));
    }
    return result;
  }
}
