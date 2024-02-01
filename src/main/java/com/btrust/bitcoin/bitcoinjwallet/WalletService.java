package com.btrust.bitcoin.bitcoinjwallet;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {
    private final NetworkParameters networkParameters = TestNet3Params.get();
    public WalletModel createWallet() throws MnemonicException.MnemonicLengthException {

        /**
         * Secure random: this gives us a random numbers, it returns a byte array,
         * 1 byte = 8bits
         * 16 bytes = 128
         *  dividing 128 by 12 will give you 12 mnemonic codes
         *  it is also called entropy
         */
        byte[] randomness = SecureRandom.getSeed(16 );

        /**
         * From the documentation of toMnemonic,
         * it picks the words from mnemonic/wordlist/english.txt in the bitcoinj j file
         */
        List<String> mnemonicCode = MnemonicCode.INSTANCE.toMnemonic(randomness);


        /**
         * At this point an Enlongation Algorithm is employed to make it up to 512bits
         */
        DeterministicSeed seed = new DeterministicSeed(mnemonicCode, null, "", System.currentTimeMillis());

        /**
         * Here a wallet is created from the seed
         * a P2PKHWallet is always created when .formseed() method is used
         */
        Wallet wallet = Wallet.fromSeed(networkParameters, seed);


        /**
         * Get the first key in the wallet (you can generate more keys as needed)
         */
        ECKey key = wallet.freshReceiveKey();

        // Create a P2PKH address using the public key
        Address address = LegacyAddress.fromKey(networkParameters, key);

        // Get the private key in Wallet Import Format (WIF)
        String privateKeyWIF = key.getPrivateKeyEncoded(networkParameters).toBase58();

        return new WalletModel(address.toString(),privateKeyWIF,mnemonicCode);
    }

    public WalletModel recoverWallet(ArrayList<String> mnemonicCode) {
        /**
         * At this point an Enlongation Algorithm is employed to make it up to 512bits
         */
        DeterministicSeed seed = new DeterministicSeed(mnemonicCode, null, "", System.currentTimeMillis());

        /**
         * Here a wallet is created from the seed
         * a P2PKHWallet is always created when .formseed() method is used
         */
        Wallet wallet = Wallet.fromSeed(networkParameters, seed);


        /**
         * Get the first key in the wallet (you can generate more keys as needed)
         */
        ECKey key = wallet.freshReceiveKey();

        // Create a P2PKH address using the public key
        Address address = LegacyAddress.fromKey(networkParameters, key);

        // Get the private key in Wallet Import Format (WIF)
        String privateKeyWIF = key.getPrivateKeyEncoded(networkParameters).toBase58();

        return new WalletModel(address.toString(),privateKeyWIF,mnemonicCode);

    }

}
