const OrdersProvider = /** @class */ (function () {

    function OrdersProvider() {
        this.simpleBaseProvider = new SimpleBaseProvider();
    }

    OrdersProvider.prototype.getOrdersByAssetIdAndStatus = function ($this, callback, assetId, orderStatus) {
        return this.simpleBaseProvider.get("/api/v1/orders/getOrdersByAssetIdAndStatus?assetId=" + assetId + "&orderStatus=" + orderStatus, $this, callback, null);
    };

    OrdersProvider.prototype.getOrdersByAssetId = function ($this, callback, assetId) {
        return this.simpleBaseProvider.get("/api/v1/orders/getOrdersByAssetId?assetId=" + assetId, $this, callback, null);
    };

    return OrdersProvider;
}());

const ordersProvider = new OrdersProvider();