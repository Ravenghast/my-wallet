package com.retail.controller;

import com.retail.service.IWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WalletController {

    private final IWalletService walletService;

    @GetMapping("/processTxn")
    public ResponseEntity<List<Integer>> processTxn(@RequestParam List<Integer> wallet, @RequestParam int amount) {
        try {
            log.info("Received request for processTxn");
            walletService.initialiseWallet(wallet);
            return new ResponseEntity<>(walletService.getNotes(amount), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            log.error("Invalid params "+ e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch (Exception e) {
            log.error("Request Failed: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
