package com.retail.service;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class WalletService implements IWalletService {

    private final @Getter HashMap<Integer, Integer> wallet = new HashMap<>();
    private final List<Integer> txn = new ArrayList<>();
    private int amount;

    @Override
    public void initialiseWallet(List<Integer> list) {
        if (list == null) {
            log.error("wallet is null");
            throw new IllegalArgumentException("Invalid wallet");
        }
        wallet.clear();
        for (int note : list) {
            if (note <= 0) {
                log.error("invalid denomination "+note);
                throw new IllegalArgumentException("Invalid denomination: " + note);
            }
            wallet.merge(note, 1, Integer::sum);
        }
    }

    @Override
    public List<Integer> getNotes(int txnAmount) {
        if (txnAmount <= 0) {
            log.error("invalid amount "+ amount);
            throw new IllegalArgumentException("Invalid amount: "+amount);
        }
        for (int i = 1; i <= wallet.keySet().size(); i++) {
            List<List<Integer>> comboList = generateCombinations(i);
            verifyCombination(comboList, txnAmount);
            if (amount == 0) {
                break;
            }
        }
        if (amount > 0) {
            log.error("Amount does not match exact wallet");
            txn.clear();
        }
        log.info("Returning notes: "+txn);
        return txn;
    }

    private List<List<Integer>> generateCombinations(int n) {
        Set<Set<Integer>> combinations = Sets.combinations(wallet.keySet(), n);
        List<List<Integer>> comboList = new ArrayList<>();
        combinations.forEach(set -> {
            List<Integer> list = new ArrayList<>(set);
            list.sort(Collections.reverseOrder());
            comboList.add(list);
        });
        comboList.sort((x, y) -> {
            for (int i = 0; i < Math.max(x.size(), y.size()); i++) {
                if (!Objects.equals(x.get(i), y.get(i))) {
                    return y.get(i) - x.get(i);
                }
            }
            return y.size() - x.size();
        });
        return comboList;
    }

    private void verifyCombination(List<List<Integer>> comboList, int txnAmount) {
        for (List<Integer> nextCombo : comboList) {
            amount = txnAmount;
            txn.clear();
            for (int note : nextCombo) {
                if (amount >= note) {
                    extractAmount(note);
                }
            }
            if (amount == 0) {
                break;
            }
        }
    }

    private void extractAmount(int note) {
        int count = wallet.get(note);
        while (count-- > 0) {
            txn.add(note);
            amount -= note;
            if (note > amount) {
                break;
            }
        }
    }

}
