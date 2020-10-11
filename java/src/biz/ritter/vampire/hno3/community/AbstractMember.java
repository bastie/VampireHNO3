/**
 * SPDX-FileCopyrightText: 2020 - Sebastian Ritter <bastie@users.noreply.github.com>
 * SPDX-License-Identifier: Apache-2.0 
 */


package biz.ritter.vampire.hno3.community;

/**
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public abstract class AbstractMember implements Member{
  
  public static final String TYPE_FULL_IMPLEMENTED = "implemented";
  public static final String TYPE_FULL_ABSTRACT = "fully abstract";
  public static final String TYPE_PART_ABSTRACT = "parts abstract";
  public static final String TYPE_ENUM = "enumeration";
      
      
      
      
      
  //////// Properties ///////////
      
  private String type = TYPE_FULL_IMPLEMENTED;
  private String name = "HNO3";
  private String group = "silver";
  
  public void setType (String newType) {
    this.type = newType;
  }
  /**
   * Get one of the Type
   * <ul>
   * <li>TYPE_FULL_IMPLEMENTED</li>
   * <li>TYPE_FULL_ABSTRACT</li>
   * <li>TYPE_PART_ABSTRACT</li>
   * <li>TYPE_ENUM</li>
   * </ul>
   * @return
   */
  public String getType () {
    return (null == this.type) ? "Null" : this.type; 
  }

  public void setName (String newName) {
    this.name = newName;
  }
  public String getName () {
    return (null == this.name) ? "Null" : this.name; 
  }

  public void setGroup (String newGroup) {
    this.group = newGroup;
  }
  public String getGroup () {
    return (null == this.group) ? "Null" : this.group; 
  }
}
