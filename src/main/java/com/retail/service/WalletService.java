package com.retail.service;

import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class WalletService implements IWalletService {

    private final HashMap<Integer, Integer> wallet = new HashMap<>();
    private final List<Integer> denominations  = new ArrayList<>();

    @Override
    public void initialiseWallet(List<Integer> list) {
        if (list == null) {
            throw new IllegalArgumentException("Invalid wallet");
        }
        wallet.clear();
        denominations.clear();
        for (int note : list) {
            if (note <= 0) {
                throw new IllegalArgumentException("Invalid denomination: " + note);
            }
            wallet.merge(note, 1, Integer::sum);
        }
        denominations.addAll(wallet.keySet());
        denominations.sort(Collections.reverseOrder());
    }

    @Override
    public List<Integer> getNotes(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount: "+amount);
        }
        List<Integer> txn = new ArrayList<>();
        for (int note : denominations) {
            if (amount >= note) {
                amount = extractAmount(amount, note, txn);
            }
        }
        if (amount > 0) {
            txn.clear();
        }
        return txn;
    }

    private int extractAmount(int value, int note, List<Integer> txn) {
        int count = wallet.get(note);
        while (count-- > 0) {
            txn.add(note);
            value -= note;
            if (note > value) {
                break;
            }
        }
        return value;
    }

    public HashMap<Integer, Integer> getWallet() {
        return wallet;
    }

    public List<Integer> getDenominations() {
        return denominations;
    }

}
