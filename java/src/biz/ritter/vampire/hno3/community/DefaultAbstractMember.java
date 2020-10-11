/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */


package biz.ritter.vampire.hno3.community;

import java.util.LinkedList;

import org.json.JSONObject;

/**
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public abstract class DefaultAbstractMember extends AbstractMember {

  
  protected void parse (String sign) {
    JSONObject input = new JSONObject(sign);
    this.setName(input.getString("name"));
    this.setType(input.getString("type"));
    this.setGroup(input.getString("group"));
    
    int inputActionNumber = 0;
    while (input.has("method_"+inputActionNumber)) {
      JSONObject actionSign = input.getJSONObject("method_"+inputActionNumber ++);
      DefaultAction action = new DefaultAction(this);
      action.parse(actionSign);
      this.add(action);
    }
  }
  
  /**
   * Helper method if programming languages has Interfaces with <code>default</code> methods 
   * @return 
   */
  public boolean isReallyFullAbstract () {
    switch (this.getType()) {
      case TYPE_FULL_ABSTRACT:
        for (DefaultAction implementionCheck : this.actions) {
          if (implementionCheck.isImplemented()) return false;
        }
        return true;
      default: return false;
    }
  }
  
  protected LinkedList<DefaultAction> actions = new LinkedList<DefaultAction>(); // LinkedList because some language needs method before call her
  /*
   * add action and if action has another member, change the member reference in action before added
   */
  public void add (DefaultAction newAction) {
    if (null != newAction) {
      if (!newAction.getMember().equals(this)) {
        newAction.changeMember(this);
      }
      this.actions.add(newAction);
    }
  }
  
  /*
   * remove the action and the reference from it to member
   */
  public void remove (DefaultAction oldAction) {
    if (this.actions.contains(oldAction)) {
      if (this.equals(oldAction.getMember())) {
        oldAction.removeMember();
      }
      this.actions.remove(oldAction);
    }
  }
  
  protected boolean hasActions () {
    return this.actions.isEmpty();
  }
  
  
  
  @Override
  public String getSign() {
    final String type = this.getType();
    final String name = this.getName();
    final String group = this.getGroup();

    
    JSONObject json = new JSONObject();
    json.put("type", type)
        .put("name", name)
        .put("group", group);
    String result = json.toString();
    
    return result;
  }
  
}
