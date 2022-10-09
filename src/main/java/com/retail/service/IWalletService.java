package com.retail.service;

import java.util.List;

public interface IWalletService {

    void initialiseWallet(List<Integer> list);
    List<Integer> getNotes(int amount);

}
