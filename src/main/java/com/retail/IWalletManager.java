package com.retail;

import java.util.List;

public interface IWalletManager {

    void initialiseWallet(List<Integer> list);
    List<Integer> getNotes(int amount);

}
