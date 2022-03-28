const TransactionsProvider = /** @class */ (function () {

    function TransactionsProvider() {
        this.simpleBaseProvider = new SimpleBaseProvider();
    }

    TransactionsProvider.prototype.getMyDepositTransactions = function ($this, callback, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/transactions/getMyDepositTransactions?pageNo="+pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    TransactionsProvider.prototype.getMyWithdrawTransactions = function ($this, callback, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/transactions/getMyWithdrawTransactions?pageNo="+pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    return TransactionsProvider;
}());

const transactionsProvider = new TransactionsProvider();