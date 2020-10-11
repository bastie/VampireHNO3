/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */

package biz.ritter.vampire.hno3.community;

import java.io.IOException;
import java.lang.module.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class JavaParty implements Party{

  /**
   * Looking for all loaded modules
   * @return List of Module 
   */
  List<Module> getAllModules() {
    return this.getModules(".*");
  }

  /**
   * Looking for loaded module filtered by regex. 
   * For example use filter "^java" is like name starts with "java" and
   * "^java\\.base$" tells the name is equal "java.base"
   * @param matchingNameRegEx filter
   * @return List of Module
   */
  List<Module> getModules(String matchingNameRegEx) {
    final String filter = null == matchingNameRegEx ? "" : matchingNameRegEx;
    List<Module> modules = 
        ModuleLayer.boot().modules()
        .stream()
        .filter(value -> value.getName().matches(filter))
        .sorted(new Comparator<Module>() {
          public int compare(Module o1, Module o2) {
            return o1.getName().compareTo(o2.getName());
          };
        })
        .collect(Collectors.toList());
    return modules;
  }

  protected void printPackages (Module module) {
      List<String> packages = module.getPackages().stream()
        // today we start with java packages
        //.filter(value -> value.startsWith("java."))
        .sorted()
        .collect(Collectors.toList());
      
      for (String p : packages) {
        System.out.println(p);
      }
  }
  
  /**
   * A member of our community is a Java type and stored in
   * a *.class file.
   * It can be an inner named class (but now we only take
   * parents to our community)
   * Also only classes are welcome.
   * 
   * @param modules list of modules we looking for member
   * @return List of class names
   */
  public List<String> lookingForJavaOuterClassMember(List<Module> modules) {
      List<String> member = new ArrayList<String>();

      for (Module module : modules) {
        
        // Let us looking for all content in our module
        ModuleFinder finder = ModuleFinder.ofSystem();
        ModuleReference ref = finder.find(module.getName()).get();
        
        try {
          List<String> moduleContent = ref.open().list()
            // today we start with java
            //.filter(value -> value.startsWith("java"))
            // no inner classes 
            .filter(value -> !value.matches(".*\\$.*"))
            // today we looking for non class resources
            .filter(value -> value.endsWith(".class"))
            .sorted()
            .collect(Collectors.toList());
          member = moduleContent;
        }
        catch (IOException rethrow) {
          throw new RuntimeException(rethrow);
        }
        
      }
      return member;
    }

  public List<Class<?>> getSpecialClasses(List<String> moduleClassName) {
    // this filter for java.base module minimized the count from 3003 to 1506 at Adopt JDK 15
    List<Class<?>> result = moduleClassName
        // stage 1 filter on string
        .stream()
        .filter(value -> !value.startsWith("sun/"))
        .filter(value -> !value.equals("module-info.class"))
        .filter(value -> !value.startsWith("jdk/"))
        .filter(value -> !value.startsWith("com/sun/"))
        .filter(value -> !value.startsWith("apple/"))
        .map(name -> name.substring(0, name.length()-6))
        .map(name -> name.replace("/", "."))
        .collect(Collectors.toList())
        // stage 2 get class references
        .stream()
        .map(name -> {
          try {
            return Class.forName(name);
          } catch (ClassNotFoundException ignored) {
          }
          return null;
        }) 
        .collect(Collectors.toList())
        // stage 3 - maybe we did not need it but it gives better feeling
        .stream()
        .filter(value -> null != value)
        .collect(Collectors.toList());
    
    return result;
  }

  
  private ArrayList<Member> members = new ArrayList<Member>(); 
  @Override
  public void printMember() {
    /*
    for (String member : lookingForMember()) {
      System.out.printf("Java member: %s%n",member);
    }
    */
  }
  
  @Override
  public void addMember(Member newMember) {
    if (!this.hasMember(newMember))
      this.members.add(newMember);
    
  }

  @Override
  public void removeMember(Member oldMember) {
    this.members.remove(oldMember);
    
  }

  @Override
  public boolean hasMember(Member member) {
    return this.members.contains(member);
  }
}
