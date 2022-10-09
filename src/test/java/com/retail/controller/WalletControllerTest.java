package com.retail.controller;

import com.retail.service.IWalletService;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WalletControllerTest {

    @Test
    public void testProcessTxnSuccess() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        IWalletService walletService = mock(IWalletService.class);
        WalletController ctrl = new WalletController(walletService);
        when(walletService.getNotes(1)).thenReturn(list);
        ResponseEntity<List<Integer>> response = ctrl.processTxn(list, 1);
        int notes = Objects.requireNonNull(response.getBody()).get(0);
        assertEquals(1, notes);
    }

    @Test
    public void testProcessTxnBadRequest() {
        IWalletService walletService = mock(IWalletService.class);
        WalletController ctrl = new WalletController(walletService);
        doThrow(IllegalArgumentException.class).when(walletService).initialiseWallet(null);
        assertThrows(ResponseStatusException.class,
                () -> ctrl.processTxn(null, 1));
    }

    @Test
    public void testProcessTxnException() {
        IWalletService walletService = mock(IWalletService.class);
        WalletController ctrl = new WalletController(walletService);
        doThrow(RuntimeException.class).when(walletService).initialiseWallet(null);
        assertThrows(ResponseStatusException.class,
                () -> ctrl.processTxn(null, 1));
    }

}
