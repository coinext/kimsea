const WalletsProvider = /** @class */ (function () {

    function WalletsProvider() {
        this.simpleBaseProvider = new SimpleBaseProvider();
    }

    WalletsProvider.prototype.getWalletByUserId = function ($this, callback, userId) {
        return this.simpleBaseProvider.get("/api/v1/wallets/getWalletByUserId?userId=" + userId, $this, callback, null);
    };

    WalletsProvider.prototype.getMyWallet = function ($this, callback) {
        return this.simpleBaseProvider.get("/api/v1/wallets/getMyWallet", $this, callback, null);
    };

    return WalletsProvider;
}());

const walletsProvider = new WalletsProvider();