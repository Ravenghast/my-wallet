package com.retail.controller;

import com.retail.service.IWalletService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WalletControllerTest {

    @Mock
    IWalletService walletService;
    @InjectMocks
    WalletController ctrl = new WalletController();

    @BeforeEach
    void init_mocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessTxnSuccess() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        when(walletService.getNotes(1)).thenReturn(list);
        ResponseEntity<List<Integer>> response = ctrl.processTxn(list, 1);
        int notes = Objects.requireNonNull(response.getBody()).get(0);
        assertEquals(1, notes);
    }

    @Test
    public void testProcessTxnBadRequest() {
        doThrow(IllegalArgumentException.class).when(walletService).initialiseWallet(null);
        assertThrows(ResponseStatusException.class,
                () -> ctrl.processTxn(null, 1));
    }

    @Test
    public void testProcessTxnException() {
        doThrow(RuntimeException.class).when(walletService).initialiseWallet(null);
        assertThrows(ResponseStatusException.class,
                () -> ctrl.processTxn(null, 1));
    }

}
