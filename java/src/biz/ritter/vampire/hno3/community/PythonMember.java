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
    // type
    source.append("class "+this.getName()+"():\n");
    stage++;
    source.append(intent(stage)).append("'''\n");
    source.append(intent(stage)).append("Clean room PythonVampire generated source\n");
    source.append(intent(stage)).append("'''\n");
    source.append("\n");
    // interface / abstract methods
    stage++; // GOSUP to inner class definitions
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
    
    // fields 
    
    // methods
    
    
    // last line
    
    stage++; // RETURN from inner class definitions
    assert (0 == stage);
    return source.toString();
  }


  private String intent(final int stage) {
    final int INDENTATION_RANGE = 4;
    return String.format("%"+(stage*INDENTATION_RANGE)+"s" ," ");
  }
}
