package com.btrust.bitcoin.bitcoinjwallet;

import lombok.RequiredArgsConstructor;
import org.bitcoinj.crypto.MnemonicException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class controller {

    private final WalletService walletService;


    @PostMapping("create-wallet")
    public ResponseEntity<WalletModel> createAndSaveWallet() throws MnemonicException.MnemonicLengthException {
        return ResponseEntity.ok(walletService.createWallet());
    }

    @PostMapping(value="restore-wallet",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletModel> recoverWallet(@RequestBody ArrayList<String> mnemonicCode){
        return ResponseEntity.ok(walletService.recoverWallet(mnemonicCode));
    }

}
