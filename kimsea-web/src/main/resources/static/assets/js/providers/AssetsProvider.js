const AssetsProvider = /** @class */ (function () {

    function AssetsProvider() {
        this.simpleBaseProvider = new SimpleBaseProvider();
    }

    AssetsProvider.prototype.mintAsset = function ($this, callback, assetId) {
        var params = new Object();
        params.assetId = assetId;
        return this.simpleBaseProvider.post("/api/v1/myassets/mintAsset", $this, callback, params);
    };

    AssetsProvider.prototype.withdraw = function ($this, callback, assetId, toAddress) {
        var params = new Object();
        params.assetId = assetId;
        params.toAddress = toAddress;
        return this.simpleBaseProvider.post("/api/v1/myassets/withdraw", $this, callback, params);
    };

    AssetsProvider.prototype.getMyAssetsByStatus = function ($this, callback, status, query, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/myassets/getMyAssetsByStatus?status=" + status + "&query=" + query + "&pageNo=" + pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    AssetsProvider.prototype.getAssetsByCategory = function ($this, callback, category, query, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/assets/getAssetsByCategory?category=" + category + "&query=" + query + "&pageNo=" + pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    AssetsProvider.prototype.getAssetsByCreator = function ($this, callback, userId, query, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/assets/getAssetsByCreator?userId=" + userId + "&query=" + query + "&pageNo=" + pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    AssetsProvider.prototype.getAssetsByOwner = function ($this, callback, userId, query, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/assets/getAssetsByOwner?userId=" + userId + "&query=" + query + "&pageNo=" + pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    AssetsProvider.prototype.getAssetById = function ($this, callback, assetId) {
        return this.simpleBaseProvider.get("/api/v1/assets/getAssetById?assetId=" + assetId, $this, callback, null);
    };

    AssetsProvider.prototype.getPopularAssets = function ($this, callback, query) {
        return this.simpleBaseProvider.get("/api/v1/assets/getPopularAssets?query=" + query, $this, callback, null);
    };

    AssetsProvider.prototype.getRecentlyAssets = function ($this, callback, query) {
        return this.simpleBaseProvider.get("/api/v1/assets/getRecentlyAssets?query=" + query, $this, callback, null);
    };

    AssetsProvider.prototype.getAssets = function ($this, callback, query) {
        return this.simpleBaseProvider.get("/api/v1/assets/getAssets?query=" + query, $this, callback, null);
    };

    AssetsProvider.prototype.createAsset = function ($this, formData, callback) {
        return this.simpleBaseProvider.form("/api/v1/myassets/createAsset", $this, callback, formData);
    };

    AssetsProvider.prototype.sellAsset = function ($this, callback, assetId, price) {
        var params = new Object();
        params.assetId = assetId;
        params.price = price;
        return this.simpleBaseProvider.post("/api/v1/myassets/sellAsset", $this, callback, params);
    };

    AssetsProvider.prototype.cancelSellAsset = function ($this, callback, assetId) {
        var params = new Object();
        params.assetId = assetId;
        return this.simpleBaseProvider.post("/api/v1/myassets/cancelSellAsset", $this, callback, params);
    };

    AssetsProvider.prototype.modifySellAsset = function ($this, callback, assetId, price) {
        var params = new Object();
        params.assetId = assetId;
        params.price = price;
        return this.simpleBaseProvider.post("/api/v1/myassets/modifySellAsset", $this, callback, params);
    };

    AssetsProvider.prototype.buyAsset = function ($this, callback, assetId, price) {
        var params = new Object();
        params.assetId = assetId;
        params.price = price;
        return this.simpleBaseProvider.post("/api/v1/myassets/buyAsset", $this, callback, params);
    };

    AssetsProvider.prototype.cancelBuyAsset = function ($this, callback, assetId) {
        var params = new Object();
        params.assetId = assetId;
        return this.simpleBaseProvider.post("/api/v1/myassets/cancelBuyAsset", $this, callback, params);
    };

    AssetsProvider.prototype.modifyBuyAsset = function ($this, callback, assetId, price) {
        var params = new Object();
        params.assetId = assetId;
        params.price = price;
        return this.simpleBaseProvider.post("/api/v1/myassets/modifyBuyAsset", $this, callback, params);
    };

    return AssetsProvider;
}());

const assetsProvider = new AssetsProvider();