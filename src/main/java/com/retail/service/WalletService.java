package com.retail.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class WalletService implements IWalletService {

    private final @Getter HashMap<Integer, Integer> wallet = new HashMap<>();
    private final @Getter List<Integer> denominations  = new ArrayList<>();

    @Override
    public void initialiseWallet(List<Integer> list) {
        if (list == null) {
            log.error("wallet is null");
            throw new IllegalArgumentException("Invalid wallet");
        }
        wallet.clear();
        denominations.clear();
        for (int note : list) {
            if (note <= 0) {
                log.error("invalid denomination "+note);
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
            log.error("invalid amount "+ amount);
            throw new IllegalArgumentException("Invalid amount: "+amount);
        }
        List<Integer> txn = new ArrayList<>();
        for (int note : denominations) {
            if (amount >= note) {
                amount = extractAmount(amount, note, txn);
            }
        }
        if (amount > 0) {
            log.error("Amount does not match exact wallet");
            txn.clear();
        }
        log.info("Returning notes: "+txn);
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

}
