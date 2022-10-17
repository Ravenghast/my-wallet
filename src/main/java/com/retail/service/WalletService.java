package com.retail.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class WalletService implements IWalletService {

    private final @Getter HashMap<Integer, Integer> wallet = new HashMap<>();
    private int[] keys = new int[0];
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
        keys = wallet.keySet().stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public List<Integer> getNotes(int txnAmount) {
        if (txnAmount <= 0) {
            log.error("invalid amount "+ amount);
            throw new IllegalArgumentException("Invalid amount: "+amount);
        }
        for (int i = 1; i <= keys.length; i++) {
            List<List<Integer>> comboList = getCombinations(i);
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

    private List<List<Integer>> getCombinations(int size) {
        int[] combos=new int[size];
        List<List<Integer>> comboList = new ArrayList<>();
        generateCombinations(comboList, keys, combos, 0, keys.length-1, 0, size);
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

    private void generateCombinations(List<List<Integer>> comboList, int[] keys, int[] combos, int start, int end, int index, int size) {
        if (index == size) {
            List<Integer> list = new ArrayList<>();
            for (int j=0; j<size; j++) {
                list.add(combos[j]);
            }
            list.sort(Collections.reverseOrder());
            comboList.add(list);
            return;
        }
        for (int i=start; i<=end && end-i+1 >= size-index; i++) {
            combos[index] = keys[i];
            generateCombinations(comboList, keys, combos, i+1, end, index+1, size);
        }
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
