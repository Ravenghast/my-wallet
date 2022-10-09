package com.retail.controller;

import com.retail.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class WalletController {

    @SuppressWarnings("unused")
    @Autowired
    private IWalletService walletService;

    @GetMapping("/processTxn")
    public ResponseEntity<List<Integer>> processTxn(@RequestParam List<Integer> wallet, @RequestParam int amount) {
        try {
            walletService.initialiseWallet(wallet);
            return new ResponseEntity<>(walletService.getNotes(amount), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
