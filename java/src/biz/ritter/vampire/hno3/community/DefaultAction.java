/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */


package biz.ritter.vampire.hno3.community;

import org.json.JSONObject;

/**
 * 
 * All Members can doing something. And this is a methodic action.
 * note: This is part at the tree (member   1<--->n   action)
 * 
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class DefaultAction {

  private AbstractMember member;
  public DefaultAction (AbstractMember theMember, String sign) {
    if (null == theMember) throw new NullPointerException(); // maybe later make anonymous member
    this.member = theMember;
    this.parse(sign);
  }
  public DefaultAction (AbstractMember theMember) {
    if (null == theMember) throw new NullPointerException(); // maybe later make anonymous member
    this.member = theMember;
  }
  
  
  /**
   * Parse a action
   * @param sign
   */
  protected void parse (String sign) {
    this.parse(new JSONObject(sign));
  }
  
  /**
   * 
   * @param action
   */
  void parse (JSONObject action) {
    this.setName(action.getString("name"));
    this.setResult(action.getString("result"));
    this.setPublicity(action.getString("visible"));
    this.setIntended("fully abstract".equals(action.getString("type")));
  }
  
  ////////// helper methods /////////////
  public AbstractMember getMember () {
    return this.member;
  }
  
  public AbstractMember changeMember (AbstractMember newMember) {
    AbstractMember oldMember = this.member;
    this.member = newMember;
    return oldMember;
  }
  
  void removeMember () {
    this.member = null;
  }
  
  //////////// properties ///////////////
  private String name;
  private String result;
  private String publicity;
  private boolean intended;
  
  public void setName (String newName) {
    this.name = newName;
  }
  /**
   * Return the name or an empty String, if we do our action anonym.
   * @return name or empty string
   */
  public String getName () {
    return null == this.name ? "" : this.name;
  }
  public void setResult (String newResult) {
    this.result = newResult;
  }
  /**
   * Return the result or an empty String, if our action has no result.
   * @return result or empty string
   */
  public String getResult () {
    return null == this.result ? "" : this.result;
  }
  public void setPublicity (String newPublicity) {
    this.publicity = newPublicity;
  }
  /**
   * Return the visibly of our action, called publicity.
   * @return publicity or empty string if no extra publicity required
   */
  public String getPublicity () {
    return null == this.publicity ? "" : this.publicity;
  }
  public void setIntended(boolean isOnlyIntended) {
    this.intended = isOnlyIntended;
  }
  /**
   * Return if action is an abstract idea and only intended and for publicity
   * @return intended or implemented
   */
  public boolean isIntended () {
    return this.intended;
  }
  /**
   * Return if action is implemented and not an abstract idea
   * It is ever ever the oposite to intened
   * @return implemented or intended
   */
  public boolean isImplemented () {
    return !this.isIntended();
  }
}
