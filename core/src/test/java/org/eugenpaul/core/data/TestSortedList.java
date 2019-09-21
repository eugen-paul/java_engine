package org.eugenpaul.core.data;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.eugenpaul.javaengine.core.data.SimpleSortedList;
import org.junit.Test;

public class TestSortedList {
  @Test
  public void testSortList1() {
    SimpleSortedList<Integer> testList = new SimpleSortedList<Integer>();

    testList.add(1);
    testList.add(2);
    testList.add(3);

    assertEquals(1, testList.get(0).intValue());
    assertEquals(2, testList.get(1).intValue());
    assertEquals(3, testList.get(2).intValue());
  }

  @Test
  public void testSortList2() {
    SimpleSortedList<Integer> testList = new SimpleSortedList<Integer>();

    testList.add(3);
    testList.add(2);
    testList.add(1);

    assertEquals(1, testList.get(0).intValue());
    assertEquals(2, testList.get(1).intValue());
    assertEquals(3, testList.get(2).intValue());
  }

  @Test
  public void testSortList3() {
    SimpleSortedList<Integer> testList = new SimpleSortedList<Integer>();

    testList.add(3);
    testList.add(3);
    testList.add(1);

    assertEquals(1, testList.get(0).intValue());
    assertEquals(3, testList.get(1).intValue());
    assertEquals(3, testList.get(2).intValue());
  }

  @Test
  public void testSortListRandomTest() {
    SimpleSortedList<Integer> testList = new SimpleSortedList<Integer>();
    List<Integer> unsortedList = new LinkedList<>();

    Random ran = new Random();

    for (int i = 0; i < 20; i++) {
      int random = ran.nextInt(10);
      testList.add(random);
      unsortedList.add(random);
    }
    Collections.sort(unsortedList);

    for (int i = 0; i < unsortedList.size(); i++) {
      assertEquals(testList.get(i), unsortedList.get(i));
    }
  }
}
