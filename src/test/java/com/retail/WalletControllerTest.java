package com.retail;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WalletControllerTest {

    WalletController ctrl = new WalletController();

    @Test
    public void testProcessTxnSuccess() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        ctrl.walletManager = mock(WalletManager.class);
        when(ctrl.walletManager.getNotes(1)).thenReturn(list);
        ResponseEntity<List<Integer>> response = ctrl.processTxn(list, 1);
        int notes = response.getBody().get(0);
        assertEquals(1, notes);
    }

    @Test
    public void testProcessTxnBadRequest() {
        ctrl.walletManager = mock(WalletManager.class);
        doThrow(IllegalArgumentException.class).when(ctrl.walletManager).initialiseWallet(null);
        assertThrows(ResponseStatusException.class,
                () -> ctrl.processTxn(null, 1));
    }

    @Test
    public void testProcessTxnException() {
        ctrl.walletManager = mock(WalletManager.class);
        doThrow(RuntimeException.class).when(ctrl.walletManager).initialiseWallet(null);
        assertThrows(ResponseStatusException.class,
                () -> ctrl.processTxn(null, 1));
    }

}
