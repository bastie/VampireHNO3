/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */


package biz.ritter.vampire.hno3.community;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * A good description how to implement a typed interface in Python is
 * written on <a href="https://realpython.com/python-interface/#using-abstract-method-declaration">
 * RealPython</a>.
 * 
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class PythonMember extends DefaultAbstractMember {
  
  public PythonMember() {
  }
  public PythonMember(String sign) {
    this.parse(sign);
  }

  @Override
  public String getCard() {
    int stage = 0;
    // maybe next two line as static part
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date = sdf.format(new Date());
    
    StringBuilder source = new StringBuilder();
    // License
    source.append ("# SPDX-License-Identifier: Apache-2.0\n");
    source.append("\n");
    // Comment
    source.append("'''\n"
        + "Created on "+date+"\n"
        + "\n"
        + "@author: Sͬeͥbͭaͭsͤtͬian\n"
        + "'''\n");
    source.append("\n");
    // imports
    if (TYPE_FULL_ABSTRACT.equals(this.getType()) || TYPE_PART_ABSTRACT.equals(this.getType())) {
      source.append("import abc\n");
      source.append("\n\n");
    }
    final boolean ABSTRACT_METHODS_DETECTED = ! this.actions.stream().filter(value -> value.isIntended()).collect(Collectors.toList()).isEmpty();
    if (ABSTRACT_METHODS_DETECTED) {
      source.append (this.getMetaTypeCard(stage));
      source.append("\n\n");
    }
    source.append (this.getTypeCard(stage, ABSTRACT_METHODS_DETECTED));
    return source.toString();
  }
  /*
   * Meta type have the same name as type but with suffix "_MetaType"
   */
  protected String getMetaTypeCard (int stage) {
    StringBuilder source = new StringBuilder ();
    // type
    source.append("class "+this.getName()+"_MetaType(metaclass=abc.ABCMeta):\n");
    stage++;
    source.append(intent(stage)).append("'''\n");
    source.append(intent(stage)).append("Clean room PythonVampire generated source\n");
    source.append(intent(stage)).append("'''\n");
    source.append("\n");
    // interface / abstract methods
    // GOSUP to inner class definitions
    final boolean INTENDED_ACTION_DETECTED = ! this.actions.stream().filter(value -> value.isIntended()).collect(Collectors.toList()).isEmpty();
    if (INTENDED_ACTION_DETECTED) {
      source.append(intent(stage)).append("@classmethod\n");
      source.append(intent(stage)).append("def __subclasshook__(cls, subclass):\n");
      stage++; // GOSUP to inner method implementations
      final List<DefaultAction> intendedActions = this.actions.stream().filter(value -> value.isIntended()).collect(Collectors.toList());
      final int FIRST = 0;
      final int LAST = intendedActions.size()-1;
      for (int nr = 0; nr < intendedActions.size(); nr++) {
        final String NAME = intendedActions.get(nr).getName();
        if (FIRST == nr) {
          source.append(intent(stage)).append("return (hasattr(subclass, "+NAME+") and\n");
          source.append(intent(stage)).append("        callable(subclass."+NAME+")");
        }
        else {
          source.append(intent(stage)).append("        hasattr(subclass, "+NAME+") and\n");
          source.append(intent(stage)).append("        callable(subclass."+NAME+")");
        }
        if (LAST != nr) {
          source.append(" and\n");
        }
        else {
          source.append(")\n");
        }
      }
      stage--; // RETURN from inner method implementations
    }
    //for (DefaultAction action : this.actions) {
      // in Python interface only the default methods will be implemented
      final List<DefaultAction> abstractInterfaceActions = this.actions.stream().filter(value -> value.isIntended()).collect(Collectors.toList());
      for (DefaultAction abstractAction : abstractInterfaceActions) {
        source.append(intent(stage)).append("@abc.abstractmethod\n");
// FIXME: Java interface class methods - for example CharSequence.compare       
        final String FIRST_PARAM = "self"; 
        source.append(intent(stage)).append("def "+abstractAction.getName()+" ("+FIRST_PARAM+"):\n");
        stage++; // GOSUP in inner method implementation
        source.append(intent(stage)).append("raise NotImplementedError\n");
        stage--; // RETURN from inner method implementation
      }
    //}
    stage--; // RETURN from method implementation
    // last line
    stage--; // RETURN from inner class definitions
    
    return source.toString();
  }
  
  /*
   * Type implementation 
   */
  protected String getTypeCard (int stage, final boolean HAS_ABSTRACT_METHODS) {
    StringBuilder source = new StringBuilder ();
    // type
    source.append("class "+this.getName()+"(");
    source.append(HAS_ABSTRACT_METHODS ? this.getName()+"_MetaType" : ""); //not needed but fine declared
    source.append("):\n");
    stage++;
    source.append(intent(stage)).append("'''\n");
    source.append(intent(stage)).append("Clean room PythonVampire generated source\n");
    source.append(intent(stage)).append("'''\n");
    source.append("\n");
    
    // fields 
    
    // methods
    // GOSUP to method implementation
    //for (DefaultAction action : this.actions) {
      final boolean IS_INTERFACE = TYPE_FULL_ABSTRACT.equals(this.getType());
      if (IS_INTERFACE) {
        // in Python interface only the default methods will be implemented
        final List<DefaultAction> defaultInterfaceActions = this.actions.stream()
            .filter(value -> value.isImplemented())
            .filter(value -> !value.getName().startsWith("lambda$"))
            .collect(Collectors.toList());
        for (DefaultAction defaultImplementation : defaultInterfaceActions) {
          final String FIRST_PARAM = "self"; // Java interface cannot have class methods
          source.append(intent(stage)).append("def "+defaultImplementation.getName()+" ("+FIRST_PARAM+"):\n");
          stage++; // GOSUP in inner method implementation
          source.append(intent(stage)).append("pass\n");
          stage--; // RETURN from inner method implementation
        }
      }
      else { // ! IS_INTERFACE
        
      }
      
    //}
    stage--; // RETURN from method implementation
    
    // last line
    
    stage--; // RETURN from inner class definitions
    
    return source.toString();
  }


  private String intent(final int stage) {
    final int INDENTATION_RANGE = 4;
    return String.format("%"+(stage*INDENTATION_RANGE)+"s" ," ");
  }
}
