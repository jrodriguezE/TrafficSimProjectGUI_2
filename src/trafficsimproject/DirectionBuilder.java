/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author paleo
 */
public class DirectionBuilder implements Serializable{
    NdBuilder start;
    NdBuilder end;
    ArrayList<Integer> directions = new ArrayList<Integer>();
}
