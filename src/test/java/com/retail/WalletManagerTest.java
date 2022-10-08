package com.retail;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WalletManagerTest {

    private WalletManager mgr = new WalletManager();

    @Test
    public void testInitialiseWallet() {
        int[] test = {20, 1, 10, 1, 50, 5, 50, 1, 20};
        int[] expected = {50, 20, 10, 5, 1};
        HashMap<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(20, 2);
        expectedMap.put(1, 3);
        expectedMap.put(10,1);
        expectedMap.put(50, 2);
        expectedMap.put(5, 1);
        List<Integer> testList = convertToList(test);
        List<Integer> expectedList = convertToList(expected);
        Set<Map.Entry<Integer, Integer>> expectedEntries = expectedMap.entrySet();
        mgr.initialiseWallet(testList);
        List<Integer> denominations = mgr.getDenominations();
        HashMap<Integer, Integer> wallet = mgr.getWallet();
        Set<Map.Entry<Integer, Integer>> walletSet = wallet.entrySet();
        assertIterableEquals(expectedList, denominations);
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
        List<Integer> list = new ArrayList<>();
        for (int i : n) {
            list.add(i);
        }
        return list;
    }

}
