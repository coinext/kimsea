const AssetHistoriesProvider = /** @class */ (function () {

    function AssetHistoriesProvider() {
        this.simpleBaseProvider = new SimpleBaseProvider();
    }

    AssetHistoriesProvider.prototype.getAssetHistoriesByAssetId = function ($this, callback, assetId, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/assetHistories/getAssetHistoriesByAssetId?assetId=" + assetId + "&pageNo=" + pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    return AssetHistoriesProvider;
}());

const assetHistoriesProvider = new AssetHistoriesProvider();