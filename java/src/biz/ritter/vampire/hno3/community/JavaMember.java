/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */


package biz.ritter.vampire.hno3.community;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.json.JSONObject;

/**
 * The member in our Java party.
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public final class JavaMember implements Member {
  
  private Class<?> representation;
  
  public JavaMember () {
    this.representation = null;
  }
  
  public JavaMember (Class<?> newRepresentation) {
    this.representation = newRepresentation;
  }
  
  
  public String getCard () {
    if (null == this.representation) return "";
    
    return null;
  }
  
  @Override
  public String toString() {
    if (null == this.representation) return "Java party free ressource";
    return String.format("Java party%nMember \"%s\"", this.representation.getSimpleName());
  }
  
  /**
   * The information
   * <ul>
   * <li> type:   Class, Interface, Abstract Class or Enum - see AbstractMember constants TYPE_* </li>
   * <li> name:   name of type </li>
   * <li> group:  namespace / package information </li>
   * </ul>
   * NOTE: if is an Interface it is called AbstractMember.TYPE_FULL_ABSTRACT even than it has default methods
   * @return
   */
  public String getSign () {
    final String type = 
        this.representation.isInterface() ? AbstractMember.TYPE_FULL_ABSTRACT :
        Modifier.isAbstract(this.representation.getModifiers()) ? AbstractMember.TYPE_PART_ABSTRACT :
        this.representation.isEnum() ? AbstractMember.TYPE_ENUM :
        "unknown"
      ;
    final String name = this.representation.getSimpleName();
    final String group = this.representation.getPackageName();

    
    JSONObject jsonClass = new JSONObject();
    jsonClass.put("stage", "type")
             .put("type", type)
             .put("name", name)
             .put("group", group);

    Method[] declaredMethods = this.representation.getDeclaredMethods();
    for (int i = 0; i < declaredMethods.length; i++) {
      Method m = declaredMethods[i];
      JSONObject jsonMethod = new JSONObject();
      jsonMethod.put("stage", "method")
                .put("name", m.getName())
                .put("result", null != m.getReturnType().getPackage() ?
                    m.getReturnType().getPackage().getName() + "." + m.getReturnType().getSimpleName(): 
                    m.getReturnType().getSimpleName());
      final String publicity = Modifier.isPublic(m.getModifiers()) ? "all" :
                               Modifier.isPrivate(m.getModifiers()) ? "self" :
                               Modifier.isProtected(m.getModifiers()) ? "childs" :
                               "family";
      jsonMethod.put("visible", publicity);
      if (Modifier.isAbstract(m.getModifiers())) {
        jsonMethod.put("type", AbstractMember.TYPE_FULL_ABSTRACT);
      }
      else if (m.isDefault() || true) { // all others incl. default interface methods
        jsonMethod.put("type", AbstractMember.TYPE_FULL_IMPLEMENTED);
      }
      
      // Add to Class
      jsonClass.put("method_"+i, jsonMethod);
    }
    String result = jsonClass.toString();
    
    return result;
  }

}
