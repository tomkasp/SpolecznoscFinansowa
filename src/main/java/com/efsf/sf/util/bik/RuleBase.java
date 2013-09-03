/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.util.bik;

/**
 *
 * @author EI GLOBAL
 */
public class RuleBase {

private String rule;
private String name;
private int type;
    
public static int T_ONE=0;
public static int T_MANY=1;
public static int T_ONE_TABLE=2;
public static int T_MANY_TABLE=3;

    RuleBase(String rule, String name) {
        this.rule=rule;
        this.name=name;
        type=0;
    }
    
    RuleBase(String rule, String name, int type) {
       this(rule, name);
       this.type=type;
    }

    /**
     * @return the rule
     */
    public String getRule() {
        return rule;
    }

    /**
     * @param rule the rule to set
     */
    public void setRule(String rule) {
        this.rule = rule;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }
    
}
