package com.example.phemmelliot.chat;

/**
 * Created by phemmelliot on 7/1/18.
 */

public final class CardCheckoutRequest extends CheckoutRequest {

    public PaymentCard paymentCard;
    public String checkoutToken;

    public CardCheckoutRequest(String productName, String amount, String narration) {
        super(productName, amount);
        this.type = TYPE.CARD;
        this.narration = narration;
    }

    public CardCheckoutRequest(String productName, String amount, String narration, PaymentCard paymentCard) {
        this(productName, amount, narration);
        this.paymentCard = paymentCard;
    }


    public CardCheckoutRequest(String productName, String amount, String narration, String checkoutToken) {
        this(productName, amount, narration);
        this.checkoutToken = checkoutToken;
    }

    /**
     * A payment card
     */
    public static final class PaymentCard {

        public String number;
        public int cvvNumber;
        public int expiryMonth;
        public int expiryYear;
        public String countryCode;
        public String authToken;

        /**
         * A payment card
         * @param number Card number (PAN)
         * @param cvvNumber 3-4 Card Verification Value
         * @param expiryMonth Expiration month on the card. e.g. 11 for November
         * @param expiryYear Card expiration year e.g. 2023
         * @param countryCode <a href="https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2ISO">3166-1 alpha-2</a> country code e.g. NG for Nigeria
         * @param authToken PIN from card owner
         */
        public PaymentCard(String number, int cvvNumber, int expiryMonth, int expiryYear, String countryCode, String authToken) {
            this.number = number;
            this.cvvNumber = cvvNumber;
            this.expiryMonth = expiryMonth;
            this.expiryYear = expiryYear;
            this.countryCode = countryCode;
            this.authToken = authToken;
        }
    }
}
