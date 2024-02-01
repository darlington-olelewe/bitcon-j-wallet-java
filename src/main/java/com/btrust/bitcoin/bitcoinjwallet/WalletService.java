package com.btrust.bitcoin.bitcoinjwallet;

import org.bitcoinj.crypto.MnemonicException;

import java.util.ArrayList;

public interface WalletService {
    WalletModel createWallet() throws MnemonicException.MnemonicLengthException;
    WalletModel restoreWallet(ArrayList<String> mnemonicCode);
}
