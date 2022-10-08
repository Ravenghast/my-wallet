package com.retail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class WalletController {

    @Autowired
    IWalletManager walletManager;

    @GetMapping("/processTxn")
    public ResponseEntity<List<Integer>> processTxn(@RequestParam List<Integer> wallet, @RequestParam int amount) {
        try {
            walletManager.initialiseWallet(wallet);
            return new ResponseEntity<>(walletManager.getNotes(amount), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
