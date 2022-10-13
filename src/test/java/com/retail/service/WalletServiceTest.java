package com.retail.service;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class WalletServiceTest {

    private final WalletService mgr = new WalletService();

    @Test
    public void testInitialiseWallet() {
        int[] test = {20, 1, 10, 1, 50, 5, 50, 1, 20};
        HashMap<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(20, 2);
        expectedMap.put(1, 3);
        expectedMap.put(10,1);
        expectedMap.put(50, 2);
        expectedMap.put(5, 1);
        List<Integer> testList = convertToList(test);
        Set<Map.Entry<Integer, Integer>> expectedEntries = expectedMap.entrySet();
        mgr.initialiseWallet(testList);
        HashMap<Integer, Integer> wallet = mgr.getWallet();
        Set<Map.Entry<Integer, Integer>> walletSet = wallet.entrySet();
        assertIterableEquals(expectedEntries, walletSet);
    }

    @Test
    public void testInitialiseWalletInvalidWallet() {
        assertThrows(IllegalArgumentException.class,
                () -> mgr.initialiseWallet(null),
        "Invalid wallet");
    }

    @Test
    public void testInitialiseWalletInvalidDenomination() {
        int[] test = {5, -1, 10};
        List<Integer> testList = convertToList(test);
        assertThrows(IllegalArgumentException.class,
                () -> mgr.initialiseWallet(testList),
                "Invalid denomination: -1");
    }

    @Test
    public void testGetNotesSuccess() {
        int[] test = {1, 5, 10, 1};
        int[] expected = {5, 1, 1};
        List<Integer> testList = convertToList(test);
        List<Integer> expectedList = convertToList(expected);
        mgr.initialiseWallet(testList);
        List<Integer> notes = mgr.getNotes(7);
        assertIterableEquals(expectedList, notes);
    }

    @Test
    public void testGetNotesSuccess1() {
        int[] test = {2, 2, 2, 5, 10};
        int[] expected = {5, 2, 2, 2};
        List<Integer> testList = convertToList(test);
        List<Integer> expectedList = convertToList(expected);
        mgr.initialiseWallet(testList);
        List<Integer> notes = mgr.getNotes(11);
        assertIterableEquals(expectedList, notes);
    }

    @Test
    public void testGetNotesSuccess2() {
        int[] test = {2, 1, 1, 2, 7, 10};
        int[] expected = {10, 2, 2, 1, 1};
        List<Integer> testList = convertToList(test);
        List<Integer> expectedList = convertToList(expected);
        mgr.initialiseWallet(testList);
        List<Integer> notes = mgr.getNotes(16);
        assertIterableEquals(expectedList, notes);
    }

    @Test
    public void testGetNotesSuccess3() {
        int[] test = {2, 2, 2, 5, 7, 10};
        int[] expected = {10, 2, 2, 2};
        List<Integer> testList = convertToList(test);
        List<Integer> expectedList = convertToList(expected);
        mgr.initialiseWallet(testList);
        List<Integer> notes = mgr.getNotes(16);
        assertIterableEquals(expectedList, notes);
    }

    @Test
    public void testGetNotesSuccess4() {
        int[] test = {1, 50, 5, 10, 1, 20, 20, 5, 1, 50};
        int[] expected = {50, 50, 20, 1, 1, 1};
        List<Integer> testList = convertToList(test);
        List<Integer> expectedList = convertToList(expected);
        mgr.initialiseWallet(testList);
        List<Integer> notes = mgr.getNotes(123);
        assertIterableEquals(expectedList, notes);
    }

    @Test
    public void testGetNotesNotMatching() {
        int[] test = {1, 5, 10, 1};
        List<Integer> testList = convertToList(test);
        mgr.initialiseWallet(testList);
        List<Integer> notes = mgr.getNotes(8);
        assertIterableEquals(new ArrayList<>(), notes);
    }

    @Test
    public void testGetNotesInvalidAmount() {
        int[] test = {1};
        List<Integer> testList = convertToList(test);
        mgr.initialiseWallet(testList);
        assertThrows(IllegalArgumentException.class,
                () -> mgr.getNotes(-2),
                "Invalid amount: -2");
    }

    private List<Integer> convertToList(int[] n) {
        return Arrays.stream(n).boxed().collect(Collectors.toList());
    }

}
