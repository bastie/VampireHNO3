/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */

package biz.ritter.vampire.hno3.community;

import java.io.File;
import java.io.IOException;
import java.lang.module.*;
import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class JavaParty implements Party{

  @Override
  public void printMember() {
    for (String member : lookingForMember()) {
      System.out.printf("Java member: %s%n",member);
    }
    
  }

  public List<String> lookingForMember() {
      List<String> member = new ArrayList<String>();

      // Look for all module...
      List<Module> modules = 
          ModuleLayer.boot().modules()
          .stream()
          // today we start with java base
          .filter(value -> value.getName().startsWith("java.base"))
          .collect(Collectors.toList());
      
      /*
      modules.sort(new Comparator<Module>() {
        public int compare(Module o1, Module o2) {
          return o1.getName().compareTo(o2.getName());
        };
      });
      
      for (Module m : modules) {
        System.out.printf("%s%n",m.getName());
      }
      
      System.exit(0);
      
      /*
      System.out.printf("%s%n",this.getClass().getModule().getName());
      */
      
      for (Module module : modules) {
        
        List<String> packages = module.getPackages().stream()
          // today we start with java packages
          .filter(value -> value.startsWith("java."))
          .sorted()
          .collect(Collectors.toList());
        
        
        
        for (String p : packages) {
          System.out.println(p);
        }
        
        // Let us looking for all content in our module
        ModuleFinder finder = ModuleFinder.ofSystem();
        ModuleReference ref = finder.find(module.getName()).get();
        
        try {
          List<String> moduleContent = ref.open().list()
            // today we start with java
            .filter(value -> value.startsWith("java"))
            // today we looking for non class resources
            .filter(value -> !value.endsWith(".class"))
            .sorted()
            .collect(Collectors.toList());
            
          System.err.println("===============");
          Thread.yield();
          for (String p : moduleContent) {
            System.out.println(p);
          }
        }
        catch (IOException rethrow) {
          throw new RuntimeException(rethrow);
        }
            
        
        
      }
      return member;
    }
}
